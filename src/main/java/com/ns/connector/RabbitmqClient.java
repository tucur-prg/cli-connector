package com.ns.connector;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.MessageProperties;

import static java.lang.System.out;

public class RabbitmqClient {
    private Connection connection;
    private Channel channel;

    private String hostname;

    private String username;
    private String password;

    private String queueName;

    public void open(String host) throws Exception {
        hostname = host;

        connect();
    }

    public void settings(String username, String password) {
        this.username = username;
        this.password = password;
    }

    protected void connect() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(hostname);
        if (username != null) {
            factory.setUsername(username);
        }
        if (password != null) {
            factory.setPassword(password);
        }

        connection = factory.newConnection();
        channel = connection.createChannel();
    }

    public void close() throws Exception {
        if (channel != null) {
            channel.close();
        }
        if (connection != null) {
            connection.close();
        }
    }

    public void queue(String queueName) throws Exception {
        this.queueName = queueName;
    }

    public void createQueue(String queueName, boolean durable, boolean exclusive, boolean autoDelete) throws Exception {
        channel.queueDeclare(queueName, durable, exclusive, autoDelete, null);
    }

    public void set(String queueName, String message) throws Exception {
        channel.basicPublish("", queueName, null, message.getBytes());

        out.println("Send: " + message);
    }

    public void dset(String queueName, String message) throws Exception {
        channel.basicPublish("", queueName, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());

        out.println("Send: " + message);
    }

    public void get(String queueName) throws Exception {
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, consumer);

        QueueingConsumer.Delivery delivery = consumer.nextDelivery(3);
        if (delivery != null) {
            String message = new String(delivery.getBody());

            out.println("Received: " + message);
        } else {
            throw new Exception("No message");
        }
    }
}
