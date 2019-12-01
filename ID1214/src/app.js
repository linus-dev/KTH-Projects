'use strict';

var bodyParser = require('body-parser'); // parser for post requests
var express = require('express'); // app server
var watson = require('ibm-watson/assistant/v2');
const { IamAuthenticator } = require('ibm-watson/auth');

var assistant = new watson({
  version: '2019-11-29',
  authenticator: new IamAuthenticator({
    apikey: 'Y8jC3QFuawVziXQ6d9ogKnpuTNM_v-t2RRkxelmgY_Tf'
  }),
  url: 'https://gateway-lon.watsonplatform.net/assistant/api'
});

var app = express();
// Bootstrap application settings
app.use(express.static('./public')); // load UI from public folder
app.use(bodyParser.json());
const ASSISTANT_ID = '2e082f30-919a-4901-bd6e-51a7b0e7551f';
// Endpoint to be call from the client side
app.post('/api/message', function(req, res) {
  let assistantId = ASSISTANT_ID || '<assistant-id>';
  if (!assistantId || assistantId === '<assistant-id>') {
    return res.json({
      output: {
        text:
          'The app has not been configured with a <b>ASSISTANT_ID</b> environment variable. Please refer to the ' +
          '<a href="https://github.com/watson-developer-cloud/assistant-simple">README</a> documentation on how to set this variable. <br>' +
          'Once a workspace has been defined the intents may be imported from ' +
          '<a href="https://github.com/watson-developer-cloud/assistant-simple/blob/master/training/car_workspace.json">here</a> in order to get a working application.',
      },
    });
  }

  var textIn = '';

  if (req.body.input) {
    textIn = req.body.input.text;
  }

  var payload = {
    assistantId: assistantId,
    sessionId: req.body.session_id,
    input: {
      message_type: 'text',
      text: textIn,
    },
  };

  // Send the input to the assistant service
  assistant.message(payload, function(err, data) {
    if (err) {
      const status = err.code !== undefined && err.code > 0 ? err.code : 500;
      return res.status(status).json(err);
    }

    return res.json(data);
  });
});

app.get('/api/session', function(req, res) {
  assistant.createSession(
    {
      assistantId: ASSISTANT_ID || '{assistant_id}',
    },
    function(error, response) {
      if (error) {
        return res.send(error);
      } else {
        return res.send(response);
      }
    }
  );
});

app.listen(3000);
