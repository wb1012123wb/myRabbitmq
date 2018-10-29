/**
 * Copyright (C), 2018-2018, XXX有限公司
 * FileName: Send_common
 * Author:   18636
 * Date:     2018/10/26 10:19
 * Description: confirm模式 — 普通模式，一次只能发一条
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.weiboo.rabbit.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.weiboo.rabbit.util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send_common {

    private static final String QUEUE_NAME = "test_queue_confirm_common";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 生产者调用confirmSelect 将信道设置成confirm模式，
        // 注：如果已经设置成txSelect模式，将不能在设置成confirm模式
        channel.confirmSelect();

        String msg = "hello confirm message!!";
        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());

        // 发一条
        if (!channel.waitForConfirms()) {
            // 如果没有confirm，发送失败
            System.out.println("message send faild!");
        } else {
            System.out.println("message send ok!");
        }

        channel.close();
        connection.close();
    }
}
