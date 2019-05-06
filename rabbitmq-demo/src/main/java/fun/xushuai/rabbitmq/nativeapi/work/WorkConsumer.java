package fun.xushuai.rabbitmq.nativeapi.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import fun.xushuai.rabbitmq.nativeapi.util.ConnectionUtil;

import java.nio.charset.StandardCharsets;

/**
 * Work(竞争消费者模式) - 消费者
 */
public class WorkConsumer {
    private final static String QUEUE_NAME = "Work_Queue";

    public static void main(String[] args) {
        try {
            Connection connection = ConnectionUtil.getConnection();
            Channel channel = connection.createChannel();
            // 设置每次只接收一条消息处理
            channel.basicQos(1);
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                // 获取消息
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [消费者2] 收到消息, message = [" + message + "]");
                /*//模拟耗时
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }*/
                // 消息应答
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            };
            // 第二个参数改为false，表示不启用自动应答
            channel.basicConsume(QUEUE_NAME, false, deliverCallback, consumerTag -> {});
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
