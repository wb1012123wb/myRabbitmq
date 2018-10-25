/**
 * Copyright (C), 2018-2018, XXX有限公司
 * FileName: Send
 * Author:   18636
 * Date:     2018/10/24 17:23
 * Description: 消息发送
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.weiboo.rabbit.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.weiboo.rabbit.util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    private static final String QUEUE_NAME = "test_simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 获取一个链接
        Connection connection = ConnectionUtils.getConnection();

        // 从链接中获取一个通道
        Channel channel = connection.createChannel();

        // 创建队列声明
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        String msg = "hello simple";
        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());

        System.out.println("--send msg: " + msg);

        channel.close();
        connection.close();
    }
}
