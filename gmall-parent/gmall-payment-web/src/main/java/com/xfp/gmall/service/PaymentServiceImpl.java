package com.xfp.gmall.service;

import com.xfp.gmall.entity.Payment;
import com.xfp.gmall.mapper.PaymentMapper;
import com.xfp.gmall.mq.ActiveMQUtil;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.jms.*;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentMapper paymentMapper;
    @Autowired
    private ActiveMQUtil activeMQUtil;

    @Override
    public void updatePaymentStatus(Payment payment) {
        String orderSn = payment.getOrderSn();
        Example example=new Example(Payment.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderSn",orderSn);
        paymentMapper.updateByExampleSelective(payment,example);
        ConnectionFactory connectionFactory = activeMQUtil.getConnectionFactory();
        Connection connection=null;
        Session session=null;
        try {
            connection = connectionFactory.createConnection();
            session = connection.createSession(true, Session.SESSION_TRANSACTED);
            Queue payment_success_queue = session.createQueue("PAYMENT_SUCCESS_QUEUE");
            MessageProducer producer = session.createProducer(payment_success_queue);
            ActiveMQMapMessage activeMQMapMessage = new ActiveMQMapMessage();
            activeMQMapMessage.setString("out_trade_no",orderSn);
            producer.send(activeMQMapMessage);
            session.commit();
        }catch (Exception e){
            e.printStackTrace();
            try {
                session.rollback();
            } catch (JMSException ex) {
                ex.printStackTrace();
            }
        }finally {
            try {
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
