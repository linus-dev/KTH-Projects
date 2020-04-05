'use strict';
var amqp = require('amqplib/callback_api');
var host = 'amqp://szjbyoed:GzlJdcWQTZ7HTtmKBVknLDoTGWUIBWTw@hawk.rmq.cloudamqp.com/szjbyoed';

amqp.connect(host, function(error0, connection) {
  console.log("Connected");
  /* RECV */ 
  connection.createChannel(function(error1, channel) {
    if (error1) {
      throw error1;
    }
    var queue = 'applicant';
    var msg = 'Hello world';

    channel.assertQueue(queue, {
      durable: false
    });
    channel.consume(queue, function(msg) {
      var secs = msg.content.toString().split('.').length - 1;

      console.log(" [x] Received %s", msg.content.toString());
      setTimeout(function() {
        console.log(" [x] Done");
        channel.ack(msg);
      }, secs * 1000);
    }, {
      // manual acknowledgment mode,
      // see https://www.rabbitmq.com/confirms.html for details
      noAck: false
    });
  });
});
