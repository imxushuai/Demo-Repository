package fun.xushuai.rabbitmq.nativeapi.fanout;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import fun.xushuai.rabbitmq.nativeapi.util.ConnectionUtil;

import java.nio.charset.StandardCharsets;

/**
 * 发布/订阅模型 - Fanout
 * 生产者
 */
public class FanoutProducer {
    private final static String EXCHANGE_NAME = "Fanout_Exchange";

    public static void main(String[] args) {
        try {
            Connection connection = ConnectionUtil.getConnection();
            // 声明队列，存在不做改变，不存在则创建topic
            Channel channel = connection.createChannel();
            // 声明 Exchange
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

            // 发送消息到 Exchange
            String message = "[Fanout] Sent Message to Exchange";
            channel.basicPublish(EXCHANGE_NAME, "",null, message.getBytes(StandardCharsets.UTF_8));

            System.out.println(" [Fanout] Sent '" + message + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
