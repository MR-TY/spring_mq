package com.tiantian.springintejms.test;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class TopicSubscriberTest {
	private static String user = "admin";
	private static String password = "admin";
	private static String url = "tcp://192.168.210.128:61616";
	public static void main(String[] args) throws Exception{
		// ConnectionFactory �����ӹ�����JMS ������������
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user,password,url);
		// Connection ��JMS �ͻ��˵�JMS Provider ������
		Connection connection = connectionFactory.createConnection();
		connection.start();
		// Session�� һ�����ͻ������Ϣ���߳�
		final Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
		// Destination ����Ϣ��Ŀ�ĵ�;��Ϣ���͸�˭.
		Topic destination=session.createTopic("example.A");
		// �����ߣ���Ϣ������
		MessageConsumer consumer = session.createConsumer(destination);
		consumer.setMessageListener(new MessageListener(){//����������
			public void onMessage(Message message) {
				try {
					TextMessage textMessage=(TextMessage)message;
					System.out.println("���յ���Ϣ��"+textMessage.getText());
				} catch (JMSException e1) {
					e1.printStackTrace();
				}
				try {
					session.commit();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
	}
}