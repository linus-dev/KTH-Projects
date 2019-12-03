'use strict';
var express = require('express');
var io = require('socket.io');
var http = require('http');

/* IBM Stuff. */
var watson = require('ibm-watson/assistant/v2');
const { IamAuthenticator } = require('ibm-watson/auth');

var app = express();
var srv = http.createServer(app);
var socket = io(srv);
const PORT = 3000;

app.use(express.static('./public'));

var assistant = new watson({
  version: '2019-11-29',
  authenticator: new IamAuthenticator({
    apikey: 'Y8jC3QFuawVziXQ6d9ogKnpuTNM_v-t2RRkxelmgY_Tf'
  }),
  url: 'https://gateway-lon.watsonplatform.net/assistant/api'
});

const ASSISTANT_ID = '2e082f30-919a-4901-bd6e-51a7b0e7551f';

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
  assistant.message(payload, (err, data) => client.emit('WATSON::ASSISTANT::MESSAGE::ANSWER', data));
}

socket.on('connection', function(client) {
  client.on('WATSON::ASSISTANT::SESSION::CREATE', () => CreateSession(client));
  client.on('WATSON::ASSISTANT::MESSAGE::SEND', (params) => SendToAssistant(client, params));
})

srv.listen(PORT, function(){
  console.log('Server started on *:' + PORT);
});