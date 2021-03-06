/**
 * Copyright (C), 2018-2018, XXX有限公司
 * FileName: Receive1
 * Author:   18636
 * Date:     2018/10/25 16:36
 * Description: 路由模型——消费者
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.weiboo.rabbit.routing;

import com.rabbitmq.client.*;
import com.weiboo.rabbit.util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Receive2 {

    private static final String EXCHANGE_NAME = "test_exchange_direct";
    private static final String QUEUE_NAME = "test_queue_direct_2";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "error");
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "info");
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "warning");

        channel.basicQos(1);

        // 定义一个消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            // 消息到达，触发这个消息
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body);
                System.out.println("[2] reseive msg: " + msg);
                try {
                    // 处理时间2秒
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("[2] done!");
                    // 手动回执消息接收
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        // 自动应答改为false
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);



    }
}
