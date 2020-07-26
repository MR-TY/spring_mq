package com.tiantian.springintejms.listener;

import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author tangyu
 * @date 2020/7/26
 */
@Component(value = "messageListener")
public class MyMessageListener implements MessageListener {

    public void onMessage(Message message) {
        if (message != null && message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            try {
                System.out.println( "消费者收到的消息：" + textMessage.getText() );
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
