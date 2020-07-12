package com.tiantian.springintejms.test;

import com.tiantian.springintejms.entity.Email;
import com.tiantian.springintejms.entity.TestMqBean;
import com.tiantian.springintejms.service.ProducerService;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class ProducerSendTest {

	@Test
	public static void main(String[] args) {
		ConnectionFactory connectionFactory;
		Connection connection;
		Session session;
		Destination destination_request,destination_response;
		MessageProducer producer;
		MessageConsumer consumer;
		connectionFactory = new ActiveMQConnectionFactory("admin", "admin", "tcp://192.168.210.128:61616");
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			//��һ���������Ƿ�����������Ϣ������Ϊtrue,�ڶ���������Ч
			//�ڶ���������
			//Session.AUTO_ACKNOWLEDGEΪ�Զ�ȷ�ϣ��ͻ��˷��ͺͽ�����Ϣ����Ҫ������Ĺ������쳣Ҳ��ȷ����Ϣ��Ӧ������ִ��֮ǰȷ�ϵ�
			//Session.CLIENT_ACKNOWLEDGEΪ�ͻ���ȷ�ϡ��ͻ��˽��յ���Ϣ�󣬱������javax.jms.Message��acknowledge������jms�������Ż�ɾ����Ϣ��������ʧ�ܵ�
			//ʱ��ȷ����Ϣ,��ȷ�ϵĻ������Ƴ����У�һֱ���ڣ��´������������ܡ�������Ϣ�����Ӳ��Ͽ���������������Ҳ������ܣ���������¶���ģʽ���������������ߣ�
			//DUPS_OK_ACKNOWLEDGE��������ȷ��ģʽ��һ�����շ�Ӧ�ó���ķ������ôӴ�����Ϣ�����أ��Ự����ͻ�ȷ����Ϣ�Ľ��գ����������ظ�ȷ�ϡ�����Ҫ������Դʹ��ʱ������ģʽ�ǳ���Ч��
			//������
			session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
			destination_request = session.createQueue("request-queue");
			destination_response = session.createQueue("response-queue");
			producer = session.createProducer(destination_request);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

			consumer = session.createConsumer(destination_response);
			//���ȼ�����Ӱ���Ƚ��ȳ�������������ô�������ʲô��������
			TestMqBean bean = new TestMqBean();
			bean.setAge(13);
			for (int i = 0; i < 10; i++) {
				bean.setName("send to data -" + i);
				producer.send(session.createObjectMessage(bean));
			}
			producer.close();
			System.out.println("��Ϣ���ͳɹ�...");

			consumer.setMessageListener(new MessageListener() {
				public void onMessage(Message message) {
					try {
						if (null != message) {
							TextMessage textMsg = (TextMessage) message;
							System.out.println("�յ�������Ϣ" +textMsg.getText());
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			});

		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}