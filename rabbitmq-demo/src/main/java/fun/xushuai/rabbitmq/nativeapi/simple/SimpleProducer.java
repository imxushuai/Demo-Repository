package fun.xushuai.rabbitmq.nativeapi.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import fun.xushuai.rabbitmq.nativeapi.util.ConnectionUtil;

/**
 * 消息生产者
 */
public class SimpleProducer {
    private final static String QUEUE_NAME = "Simple_Queue";

    public static void main(String[] args) {
        try {
            Connection connection = ConnectionUtil.getConnection();
            // 声明队列，存在不做改变，不存在则创建topic
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            //发送消息
            String message = "[Hello World] hello, rabbit!";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());

            System.out.println(" [Hello World] Sent '" + message + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
