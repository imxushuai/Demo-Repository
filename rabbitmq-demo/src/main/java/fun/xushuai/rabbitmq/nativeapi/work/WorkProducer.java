package fun.xushuai.rabbitmq.nativeapi.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import fun.xushuai.rabbitmq.nativeapi.util.ConnectionUtil;

import java.io.IOException;

/**
 * Work(竞争消费者模式) - 生产者
 */
public class WorkProducer {
    private final static String QUEUE_NAME = "Work_Queue";

    public static void main(String[] args) {
        try {
            Connection connection = ConnectionUtil.getConnection();
            // 声明队列，存在不做改变，不存在则创建topic
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            doWork(channel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void doWork(Channel channel) throws IOException {
        for (int i = 0; i < 20; i++) {
            //发送消息
            String message = i + " : 发送消息";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        }
    }
}
