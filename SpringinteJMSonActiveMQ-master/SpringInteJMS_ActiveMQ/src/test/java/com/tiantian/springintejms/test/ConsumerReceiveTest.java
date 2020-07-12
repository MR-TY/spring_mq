package com.tiantian.springintejms.test;

import com.tiantian.springintejms.entity.TestMqBean;
import com.tiantian.springintejms.service.ConsumerService;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.*;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
public class ConsumerReceiveTest {

	@Value("${active.mq.url}")
	private static String activeMqUrl;

	@Test
	public static void main(String[] args) {
		ConnectionFactory connectionFactory;
		// Connection ��JMS �ͻ��˵�JMS Provider ������
		Connection connection = null;
		// Session�� һ�����ͻ������Ϣ���߳�
		final Session session;
		// Destination ����Ϣ��Ŀ�ĵ�;��Ϣ���͸�˭.
		Destination destination_request,destination_response;
		// �����ߣ���Ϣ������
		MessageConsumer consumer;
		//�ظ����յ�����Ϣ
		final MessageProducer producer;
		connectionFactory = new ActiveMQConnectionFactory("admin", "admin", activeMqUrl);
		try {
			// ����ӹ����õ����Ӷ���
			connection = connectionFactory.createConnection();
			// ����
			connection.start();
			// ��ȡ��������
			//�����û���������
			session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
			// ��ȡsessionע�����ֵxingbo.xu-queue��һ����������queue��������ActiveMq��console����
			destination_request = session.createQueue("request-queue");
			destination_response = session.createQueue("response-queue");
			consumer = session.createConsumer(destination_request);

			producer= session.createProducer(destination_response);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

			consumer.setMessageListener(new MessageListener() {
				public void onMessage(Message message) {
					try {
						TestMqBean bean = (TestMqBean) ((ObjectMessage) message).getObject();
						System.out.println(bean);
						if (null != message) {
							System.out.println("�յ���Ϣ" + bean.getName());
							Message textMessage = session.createTextMessage("�Ѿ��ɹ��յ���Ϣ�����ڿ�ʼ�ظ�"+new Date().toString());
							producer.send(textMessage);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}