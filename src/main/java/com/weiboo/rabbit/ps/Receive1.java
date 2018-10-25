/**
 * Copyright (C), 2018-2018, XXX有限公司
 * FileName: Receive1
 * Author:   18636
 * Date:     2018/10/25 15:11
 * Description: 发布订阅模式—消费者
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.weiboo.rabbit.ps;

import com.rabbitmq.client.*;
import com.weiboo.rabbit.util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Receive1 {

    private static final String QUEUE_NAME = "test_exchange_fanout_email";
    private static final String EXCHANGE_NAME = "test_exchange_fanout";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        final Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 绑定队列
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");

        // 保证一次只分发一个
        channel.basicQos(1);

        // 定义一个消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            // 消息到达，触发这个消息
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body);
                System.out.println("[1] reseive msg: " + msg);
                try {
                    // 处理时间2秒
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("[1] done!");
                    // 手动回执消息接收
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        // 自动应答为false
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);

    }
}
