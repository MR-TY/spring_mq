package com.tiantian.springintejms.service.impl;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;

import com.tiantian.springintejms.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

public class ConsumerServiceImpl implements ConsumerService {

	public void receiveMessage(String message) throws JMSException {
		System.out.println("-----------------开始接收消息----------------");
		System.out.println("-----------------接收的消息是：" + message);
	}
}
