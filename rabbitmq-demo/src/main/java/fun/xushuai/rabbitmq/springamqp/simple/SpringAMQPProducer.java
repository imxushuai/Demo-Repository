package fun.xushuai.rabbitmq.springamqp.simple;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Spring amqp的简单使用
 * 生产者
 */
@Component
public class SpringAMQPProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.simpleQueue}")
    public String simpleQueue;


    @Value("${rabbitmq.simpleExchange}")
    public String simpleExchange;

    /**
     * 定时发送消息
     * 每隔十秒发送一条消息
     */
    @Scheduled(fixedRate = 10000)
    public void sendMessageToQueue() {
        String msg = "hello spring amqp! 现在时间是:" + System.currentTimeMillis();
        rabbitTemplate.convertAndSend(simpleQueue, msg);
        System.out.println("发送消息到 simpleQueue, message = " + msg);
    }

    /**
     * 定时发送消息
     * 每隔十秒发送一条消息
     */
    @Scheduled(fixedRate = 10000)
    public void sendMessageToExchange() {
        String msg = "hello spring amqp! 现在时间是:" + System.currentTimeMillis();
        // 发送消息到 simpleExchange
        rabbitTemplate.convertAndSend(simpleExchange, "", msg);
        System.out.println("发送消息到 simpleExchange, message = " + msg);
    }
}
