/**
 * Copyright (C), 2018-2018, XXX有限公司
 * FileName: Send
 * Author:   18636
 * Date:     2018/10/25 14:51
 * Description: 发布订阅模式——生产者
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.weiboo.rabbit.ps;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.weiboo.rabbit.util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    private static final String EXCHANGE_NAME = "test_exchange_fanout";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        //声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");   // 分发类型

        // 发送消息
        String msg = "hello pub/sub";
        channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());
        System.out.println("Send : " + msg);

        channel.close();
        connection.close();
    }
}
