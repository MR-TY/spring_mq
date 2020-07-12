package com.tiantian.springintejms.service.impl;

import com.tiantian.springintejms.service.ConsumerCustomrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import javax.jms.TextMessage;

/**
 * @author tangyu
 * @date 2020/7/5
 */
@Service
public class ConsumerCustomrServiceImpl implements ConsumerCustomrService {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void receiveMessage(Destination destination) {
        String textMessage = (String) jmsTemplate.receiveAndConvert(destination);
        System.out.println( "消费者收到的消息："+textMessage );
    }
}
