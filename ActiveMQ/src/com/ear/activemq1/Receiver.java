package com.ear.activemq1;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * <p>Title:Receiver</p>
 * <p>Description: JMS接收消息</p>
 * <p>Company: ecar</p> 
 * @author hexiaoyun 
 * @date 2017-2-22 下午03:01:55
*/
public class Receiver {
	    public static void main(String[] args) {  
	        ConnectionFactory connectionFactory; // ConnectionFactory ：连接工厂，JMS 用它创建连接  
	        Connection connection = null;      // Connection ：JMS 客户端到JMS  
	        Session session; // Session： 一个发送或接收消息的线程    
	        Destination destination;// Destination ：消息的目的地;消息发送给谁. 
	        MessageConsumer  consumer ;   // MessageConsumer：消息接收者 
	        // 构造ConnectionFactory实例对象，此处采用ActiveMq的实现jar  
	        connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, "tcp://localhost:61616");  
	        try { // 构造从工厂得到连接对象  
	            connection = connectionFactory.createConnection();  
	            // 启动连接
	            connection.start();  
	            // 获取操作连接  
	            session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);  
	            // 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置  
	            destination = session.createQueue("FirstQueue");  
	            // 得到消息生成者【发送者】  
	            consumer = session.createConsumer(destination);  
	            while(true){
	            	//设置接收者接收消息的时间，为了便于测试，这里定为100s
	            	TextMessage message = (TextMessage) consumer.receive(90000000);
	            	 if (null != message) {
	            		  System.out.println("consumer收到消息：" + message.getText());
	                 } else {
	                     break;
	                 }
	            }
         		  session.commit();     //只有session执行commit后才能成功消费消息
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        } finally {  
	            try {  
	                if (null != connection)  
	                    connection.close();  
	            } catch (Throwable ignore) {  
	            }  
	        }  
	    }  
	    
}
