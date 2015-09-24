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
    private boolean durable = false;
    private boolean exclusive = false;
    private boolean autoDelete = false;

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

    public void durable(boolean durable) throws Exception {
        // 再起動後もキューが残り続ける
        this.durable = durable;
        if (this.queueName != null) {
            changeQueue();
        }
    }

    public void exclusive(boolean exclusive) throws Exception {
        // 排他的なキューの宣言
        this.exclusive = exclusive;
        if (this.queueName != null) {
            changeQueue();
        }
    }

    public void autoDelete(boolean autoDelete) throws Exception {
        // 自動削除
        this.autoDelete = autoDelete;
        if (this.queueName != null) {
            changeQueue();
        }
    }

    public void queue(String queueName) throws Exception {
        this.queueName = queueName;
        changeQueue();
    }

    public void changeQueue() throws Exception {
        channel.queueDeclare(queueName, durable, exclusive, autoDelete, null);
    }

    public void set(String queueName, String message) throws Exception {
        if (this.durable) {
            channel.basicPublish("", queueName, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        } else {
            channel.basicPublish("", queueName, null, message.getBytes());
        }

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
