/**
 * Copyright (C), 2018-2018, XXX有限公司
 * FileName: Receive
 * Author:   18636
 * Date:     2018/10/24 17:52
 * Description: 消息接收
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.weiboo.rabbit.simple;

import com.rabbitmq.client.*;
import com.weiboo.rabbit.util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者获取消息
 */
public class Receive {

    private static final String QUEUE_NAME = "test_simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        // 获取链接
        Connection connection = ConnectionUtils.getConnection();
        // 创建频道
        Channel channel = connection.createChannel();
        // 队列声明，不声明也不会报错，因为在生产者中已经声明过
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 定义消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            // 获取到达消息 —— 一旦有消息进入声明的队列，就会触发改方法
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "UTF-8");
                System.out.println("new API receive: " + msg);
            }
        };
        // 监听队列     类似Android的事件编程
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }

    /**
     * 老版本，不建议使用
     * @throws IOException
     * @throws TimeoutException
     * @throws InterruptedException
     */
    private static void oldReceive() throws IOException, TimeoutException, InterruptedException {
        // 获取链接
        Connection connection = ConnectionUtils.getConnection();
        // 创建频道
        Channel channel = connection.createChannel();

        // 定义队列的消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        // 监听队列
        channel.basicConsume(QUEUE_NAME, true, consumer);
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String msgStr = new String(delivery.getBody());
            System.out.println("[receive]msg: " + msgStr);
        }
    }
}
