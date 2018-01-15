package com.ear.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * JMS通用的规则：
	    获取连接工厂
		使用连接工厂创建连接
		启动连接
		从连接创建会话
		获取 Destination
		创建 Producer，或
		创建 Producer
		创建 message
		创建 Consumer，或发送或接收message发送或接收 message
		创建 Consumer
		注册消息监听器（可选）
		发送或接收 message
		关闭资源（connection, session, producer, consumer 等)
	 * <p>Title:JMSDemo</p>
	 * <p>Description: </p>
	 * <p>Company: </p> 
	 * @author ecar 
	 * @date 2017-2-21 下午06:06:36
 */
public class JMSDemo {

	ConnectionFactory connectionFactory;
    Connection connection;
    Session session;
    Destination destination;
    MessageProducer producer;
    MessageConsumer consumer;
    Message message;
    boolean useTransaction = false;
    
    public static void main(String[] args) {
    	JMSDemo demo = new JMSDemo();
    	try {
			demo.sendMessge();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    private void sendMessge() throws Exception {
        try {
                Context ctx = new InitialContext();    //初始化上下文
                connectionFactory = (ConnectionFactory) ctx.lookup("ConnectionFactoryName");    //获取连接工厂
                //使用ActiveMQ时：connectionFactory = new ActiveMQConnectionFactory(user, password, getOptimizeBrokerUrl(broker));
                connection = connectionFactory.createConnection();   //使用连接工厂创建连接
                connection.start();   //启动连接
                session = connection.createSession(useTransaction, Session.AUTO_ACKNOWLEDGE);   //从连接创建会话
                destination = session.createQueue("TEST.QUEUE");    //创建Destination
                //生产者发送消息
                producer = session.createProducer(destination);    //创建Producer
                message = session.createTextMessage("this is a test");   //创建Message

                //消费者同步接收
                consumer = session.createConsumer(destination);     //创建Consumer
                message = (TextMessage) consumer.receive(1000);
                System.out.println("Received message: " + message);
                //消费者异步接收
                consumer.setMessageListener(new MessageListener() {     //注册消息监听器
                        @Override
                        public void onMessage(Message message) {
                                if (message != null) {
                                        //doMessageEvent(message);      //发送消息事件
                                	try {
										producer.send(message);
									} catch (JMSException e) {
										e.printStackTrace();
									}
                                }
                        }
                });
        } catch (JMSException e) {
        	
        } finally {   //关闭资源
                producer.close();
                session.close();
                connection.close();
        }
    }
	
}
