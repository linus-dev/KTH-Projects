'use strict';
var amqp = require('amqplib/callback_api');
var host = 'amqp://szjbyoed:GzlJdcWQTZ7HTtmKBVknLDoTGWUIBWTw@hawk.rmq.cloudamqp.com/szjbyoed';

amqp.connect(host, function(error0, connection) {
  console.log("Connected");
  /* SEND */
  connection.createChannel(function(error1, channel) {
    if (error1) {
      throw error1;
    }
    var queue = 'applicant_queue';
    var msg = 'Hello world';

    channel.assertQueue(queue, {
      durable: false
    });

    channel.sendToQueue(queue, Buffer.from(msg));
    console.log(" [x] Sent %s", msg);
  });
});
