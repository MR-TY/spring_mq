package com.tiantian.springintejms.test;

import com.tiantian.springintejms.entity.TestMqBean;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class TopicProducerSendTest {

	private static String user = "admin";
	private static String password = "admin";
	private static String url = "tcp://192.168.210.128:61616";

	public static void main(String[] args)throws Exception {
		// ConnectionFactory �����ӹ�����JMS ������������
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user,password,url);
		// Connection ��JMS �ͻ��˵�JMS Provider ������
		Connection connection = connectionFactory.createConnection();
		// Connection ����
		connection.start();
		System.out.println("Connection is start...");
		// Session�� һ�����ͻ������Ϣ���߳�
		Session session = connection.createSession(Boolean.TRUE,Session.AUTO_ACKNOWLEDGE);
		// Topicr ����Ϣ��Ŀ�ĵ�;��Ϣ���͸�˭.
		Topic  destination = session.createTopic("example.A");
		// MessageProducer����Ϣ������
		MessageProducer producer = session.createProducer(destination);
		// ���ò��־û����˴�ѧϰ��ʵ�ʸ�����Ŀ����
		producer.setDeliveryMode(DeliveryMode.PERSISTENT);
		// ������Ϣ���˴�д������Ŀ���ǲ��������߷�����ȡ
		sendMessage(session, producer);
		session.commit();

		connection.close();
		System.out.println("send text ok.");
	}

	public static void sendMessage(Session session, MessageProducer producer)
			throws Exception {
		for (int i = 1; i <= 10; i++) {//������
			TextMessage message = session.createTextMessage("ActiveMq ���͵���Ϣ" + i);
			// ������Ϣ��Ŀ�ĵط�
			System.out.println("������Ϣ��" + "ActiveMq ���͵���Ϣ" + i);
			producer.send(message);
		}
	}

}