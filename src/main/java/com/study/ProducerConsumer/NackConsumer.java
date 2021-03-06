package com.study.ProducerConsumer;


import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
public class NackConsumer extends DefaultConsumer implements BasicConsumer {
    private static String latestMessage = null;
    private final boolean requeue;

    public NackConsumer(Channel channel, boolean requeue) {
        super(channel);
        this.requeue = requeue;
    }
    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        latestMessage = new String(body, "UTF-8");
        System.out.println(" [x] Received '" + new String(body, "UTF-8") + "'");
        getChannel().basicNack(envelope.getDeliveryTag(),false,requeue);
    }

    public String getLatestMessage() {
        return latestMessage;
    }

}
