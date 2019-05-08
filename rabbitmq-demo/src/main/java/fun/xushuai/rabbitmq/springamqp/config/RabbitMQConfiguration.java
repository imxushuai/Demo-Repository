package fun.xushuai.rabbitmq.springamqp.config;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ配置类
 */
//@Configuration
public class RabbitMQConfiguration {

    @Value("${rabbitmq.simpleQueue}")
    public String simpleQueue;

    @Value("${rabbitmq.simpleExchange}")
    public String simpleExchange;

    /**
     * 声明队列
     */
    @Bean
    public Queue simpleQueue() {
        return new Queue(simpleQueue, true);
    }

    /**
     * 声明交换器(Exchange)
     *
     * Exchange是个接口，拥有六个实现类，分别是：
     * AbstractExchange(抽象实现), CustomExchange, DirectExchange
     * FanoutExchange, HeadersExchange, TopicExchange
     */
    @Bean
    public Exchange simpleExchange() {
        return new FanoutExchange(simpleExchange, true, false);
    }
}
