package fun.xushuai.rabbitmq.nativeapi.topics;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import fun.xushuai.rabbitmq.nativeapi.util.ConnectionUtil;

import java.nio.charset.StandardCharsets;

/**
 * 发布/订阅模型 - Topic
 * 消费者
 */
public class TopicsConsumer2 {
    private final static String EXCHANGE_NAME = "Topics_Exchange";

    private final static String ROUTING_KEY_END_WITH_NEWS = "#.news";

    public static void main(String[] args) {
        try {
            // 获取连接
            Connection connection = ConnectionUtil.getConnection();
            // 声明队列，存在不做改变，不存在则创建topic
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
            String queueName = channel.queueDeclare().getQueue();
            // 绑定 Exchange 并且指定routingKey
            channel.queueBind(queueName, EXCHANGE_NAME, ROUTING_KEY_END_WITH_NEWS);

            System.out.println("[消费者2] RoutingKey = [usa.#]");

            // 获取消息
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                // 获取消息
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(ROUTING_KEY_END_WITH_NEWS + " [消费者2] 收到消息, message= [" + message + "]");
                // 消息应答
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            };
            // 第二个参数改为false，表示不启用自动应答
            channel.basicConsume(queueName, false, deliverCallback, consumerTag -> {});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
