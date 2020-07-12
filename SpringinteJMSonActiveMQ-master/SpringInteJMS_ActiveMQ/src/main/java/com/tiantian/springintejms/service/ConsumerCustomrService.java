package com.tiantian.springintejms.service;

import javax.jms.Destination;

/**
 * @author tangyu
 * @date 2020/7/5
 */
public interface ConsumerCustomrService {

    public void receiveMessage(Destination destination);
}
