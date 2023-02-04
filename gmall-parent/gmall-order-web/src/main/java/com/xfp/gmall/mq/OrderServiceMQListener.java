package com.xfp.gmall.mq;


import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;

@Component
public class OrderServiceMQListener {

    @JmsListener(destination = "PAYMENT_SUCCESS_QUEUE",containerFactory = "jmsQueueListener")
    public void updateOrderProcessStatus(MapMessage mapMessage){
        //使用JMS模块进行处理
        try {
            String out_trade_no = mapMessage.getString("out_trade_no");
            //更新订单状态(根据订单交易码)
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
