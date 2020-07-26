package com.tiantian.springintejms.test;

import com.tiantian.springintejms.service.impl.ConsumerCustomrServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.Destination;

/**
 * @author tangyu
 * @date 2020/7/5
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class ConsumerCustomerServiceTest {

    @Autowired
    private ConsumerCustomrServiceImpl consumerCustomrService;

    @Autowired
    @Qualifier("queueDestination")
    private Destination destination;

    @Autowired
    @Qualifier("topicDestination")
    private Destination topicDestination;

    @Test
    public void testReceiveMessage(){
        consumerCustomrService.receiveMessage(destination);
    }

    @Test
    public void testTopicReceiveMessage(){
        consumerCustomrService.receiveTopciMessage();
    }
}
