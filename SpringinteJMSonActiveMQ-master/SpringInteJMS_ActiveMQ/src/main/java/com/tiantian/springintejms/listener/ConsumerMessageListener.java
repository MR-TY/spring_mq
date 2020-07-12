package com.tiantian.springintejms.listener;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import com.tiantian.springintejms.entity.Email;

public class ConsumerMessageListener implements MessageListener {

	private MessageConverter messageConverter;
	
	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			//��������֪�������߷��͵ľ���һ�����ı���Ϣ�������������ֱ�ӽ���ǿ��ת��������ֱ�Ӱ�onMessage�����Ĳ����ĳ�Message������TextMessage
			TextMessage textMsg = (TextMessage) message;
			System.out.println("���յ�һ�����ı���Ϣ��");
			try {
				System.out.println("��Ϣ�����ǣ�" + textMsg.getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		} else if (message instanceof MapMessage) {
			MapMessage mapMessage = (MapMessage) message;
		} else if (message instanceof ObjectMessage) {
			ObjectMessage objMessage = (ObjectMessage) message;
			try {
				/*Object obj = objMessage.getObject();
				Email email = (Email) obj;*/
				Email email = (Email) messageConverter.fromMessage(objMessage);
				System.out.println("���յ�һ��ObjectMessage������Email����");
				System.out.println(email);
			} catch (JMSException e) {
				e.printStackTrace();
			}
			
		}
	}

	public MessageConverter getMessageConverter() {
		return messageConverter;
	}

	public void setMessageConverter(MessageConverter messageConverter) {
		this.messageConverter = messageConverter;
	}

}
