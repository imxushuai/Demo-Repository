package fun.xushuai.rabbitmq.nativeapi.direct;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import fun.xushuai.rabbitmq.nativeapi.util.ConnectionUtil;

import java.nio.charset.StandardCharsets;

/**
 * 发布/订阅模型 - Direct (Routing)
 * 生产者
 */
public class DirectProducer {
    private final static String EXCHANGE_NAME = "Direct_Exchange";

    private final static String[] ROUTING_KEYS = new String[]{"orange", "black", "green"};

    public static void main(String[] args) {
        try {
            Connection connection = ConnectionUtil.getConnection();
            // 声明队列，存在不做改变，不存在则创建topic
            Channel channel = connection.createChannel();
            // 声明 Exchange
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

            // 发送消息到 Exchange
            String message = "[Direct] Sent Message to ";
            for (String routingKey : ROUTING_KEYS) {
                channel.basicPublish(EXCHANGE_NAME, routingKey,null, (message + routingKey).getBytes(StandardCharsets.UTF_8));
                System.out.println(" [Direct] Sent '" + message + routingKey + "'");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
