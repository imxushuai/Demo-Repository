package fun.xushuai.rabbitmq.springamqp.simple;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class SpringAMQPConsumer2 {

    @RabbitListener(queues = "simpleQueue")
    private void process(String msg) {
        System.out.println("[消费者2#process] simpleQueue 收到消息：" + msg);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(),
            exchange = @Exchange(name = "simpleExchange", type = ExchangeTypes.FANOUT)))
    public void processMessageFromExchange1(String msg) {
        System.out.println("[消费者2#processMessageFromExchange1] simpleExchange 收到消息：" + msg);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(),
            exchange = @Exchange(name = "simpleExchange", type = ExchangeTypes.FANOUT)))
    public void processMessageFromExchange2(String msg) {
        System.out.println("[消费者2#processMessageFromExchange2] simpleExchange 收到消息：" + msg);
    }
}
