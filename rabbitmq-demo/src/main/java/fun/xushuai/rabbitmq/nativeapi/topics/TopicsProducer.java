package fun.xushuai.rabbitmq.nativeapi.topics;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import fun.xushuai.rabbitmq.nativeapi.util.ConnectionUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 发布/订阅模型 - Topic
 * 生产者
 */
public class TopicsProducer {
    private final static String EXCHANGE_NAME = "Topics_Exchange";

    private final static String[] routingKeys = new String[]{"usa.news", "usa.weather", "europe.news", "europe.weather"};

    public static void main(String[] args) {
        try {
            Connection connection = ConnectionUtil.getConnection();
            // 声明队列，存在不做改变，不存在则创建topic
            Channel channel = connection.createChannel();
            // 声明 Exchange
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

            sendMessage(channel);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sendMessage(Channel channel) throws IOException {
        for (String routingKey : routingKeys) {
            // 发送消息到 Exchange
            String message = "[Topics] " + routingKey;
            // 指定RoutingKey为 'black'
            channel.basicPublish(EXCHANGE_NAME, routingKey,null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("[Topics] message = " + routingKey);
        }
    }
}
