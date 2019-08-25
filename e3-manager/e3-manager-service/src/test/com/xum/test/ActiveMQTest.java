package com.xum.test;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


import javax.annotation.Resource;
import javax.jms.*;
import java.io.IOException;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext-activemq.xml")
public class ActiveMQTest {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Resource
    private Destination destinationQueue;


    @Test
    public void testQueueProducer() throws JMSException {
//        创建一个工厂
        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.100.100:61616");
        Connection connection = factory.createConnection();

        connection.start();
                                //是否开启事务,应答模式:自动应答,手动应答
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //创建一个队列
        Queue queue = session.createQueue("test-queue");
        //创建一个生产者
        MessageProducer producer = session.createProducer(queue);
//        TextMessage textMessage = new ActiveMQTextMessage();
//        textMessage.setText("hello world");

        TextMessage message = session.createTextMessage("hello world1111111111");
        producer.send(message);

        producer.close();
        session.close();
        connection.close();
    }


    @Test
    public void testQueueConsumer() throws JMSException, IOException {

//        创建一个工厂
        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.100.100:61616");
        Connection connection = factory.createConnection();

        connection.start();
        //是否开启事务,应答模式:自动应答,手动应答
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Queue queue = session.createQueue("spring-queue");
        MessageConsumer consumer = session.createConsumer(queue);

        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;

                try {
                    String text = ((TextMessage) message).getText();
                    System.out.println(text);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        System.in.read();

    }


    @Test
    public void springmqProducer(){

        jmsTemplate.send(destinationQueue, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("spring mq sendmessage");
            }
        });


    }


    }

