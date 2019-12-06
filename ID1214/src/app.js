'use strict';
var express = require('express');
var io = require('socket.io');
var http = require('http');
const yelp = require('yelp-fusion');

/* IBM Stuff. */
var watson = require('ibm-watson/assistant/v2');
const { IamAuthenticator } = require('ibm-watson/auth');

var app = express();
var srv = http.createServer(app);
var socket = io(srv);

const PORT = 3000;
const YELP_API_KEY = 'XlsN8jm5cW1mO9_aKUYA1oQGETGKXlpBRX1XgvMKtw3Gq1zGNc3ZDfpQ1D2kxxVVU8U0sHtij5U6GOhcAg8YnFkUD6MuZJLPeIYTjSM3wu-az_e3LqoWDrEc2QvpXXYx';
const YELP_API_URL =  'https://api.yelp.com/v3/businesses/search';
const YELP_CLIENT_API_KEY = 'mdHl41_d7kGemONLVbACkQ';
const ASSISTANT_ID = '2e082f30-919a-4901-bd6e-51a7b0e7551f';
const YELP_CLIENT = yelp.client(YELP_API_KEY);

app.use(express.static('./public'));
var assistant = new watson({
  version: '2019-11-29',
  authenticator: new IamAuthenticator({
    apikey: 'Y8jC3QFuawVziXQ6d9ogKnpuTNM_v-t2RRkxelmgY_Tf'
  }),
  url: 'https://gateway-lon.watsonplatform.net/assistant/api'
});


var Payload = function(session, msg) {
  this.assistantId = ASSISTANT_ID;
  this.sessionId = session;
  this.input = {
    message_type: 'text',
    text: msg,
    options: {
      return_context: true
    }
  }
}

function CreateSession(client) {
  assistant.createSession(
    {
      assistantId: ASSISTANT_ID || '{assistant_id}',
    },
    function(error, response) {
      return client.emit('WATSON::ASSISTANT::SESSION::RESPONSE', (error ? error : response));
    }
  );
}

function SendToAssistant(client, params) {
  var payload = new Payload(params.session, params.message);
  console.log(payload);
  assistant.message(payload, function(err, data) {
    console.log(err);
    console.log(data);
    var skills = data.result.context.skills["main skill"].user_defined;
    if (typeof skills != 'undefined' &&
        'location' in skills &&
        'time' in skills &&
        'type_of_food' in skills) {

      const searchRequest = {
        term: skills.type_of_food + ' restaurant',
        location: skills.location,
        //open_at: new Date(skills.time).getTime()
      };
      console.log(new Date(skills.time));
      YELP_CLIENT.search(searchRequest).then(response => {
        const businesses = response.jsonBody.businesses;
        const result = [businesses[0], businesses[1], businesses[2]];
        client.emit('BENBOT::SUGGESTIONS', result);
      }).catch(e => {
        console.log(e);
      });
    }
    
    client.emit('WATSON::ASSISTANT::MESSAGE::ANSWER', data);
  });
}

socket.on('connection', function(client) {
  client.on('WATSON::ASSISTANT::SESSION::CREATE', () => CreateSession(client));
  client.on('WATSON::ASSISTANT::MESSAGE::SEND', (params) => SendToAssistant(client, params));

  client.on('disconnect', (reason) => {
    
  });
})

srv.listen(PORT, function(){
  console.log('Server started on *:' + PORT);
});

