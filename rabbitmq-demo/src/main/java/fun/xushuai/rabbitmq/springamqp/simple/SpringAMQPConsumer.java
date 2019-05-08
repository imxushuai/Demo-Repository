package fun.xushuai.rabbitmq.springamqp.simple;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Spring amqp的简单使用
 * 生产者
 * 若要该类生效，需要打开 @Component 的注解
 */
//@Component
@RabbitListener(queues = "simpleQueue")
public class SpringAMQPConsumer {

    @RabbitHandler
    public void process(String msg) {
        System.out.println("[消费者1] simpleQueue 收到消息：" + msg);
    }
}
