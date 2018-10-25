/**
 * Copyright (C), 2018-2018, XXX有限公司
 * FileName: Send
 * Author:   18636
 * Date:     2018/10/25 18:22
 * Description: 主题模式——生产者
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.weiboo.rabbit.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.weiboo.rabbit.util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    private static final String EXCHANGE_NAME = "test_exchange_topic";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        String msg = "商品...";
        channel.basicPublish(EXCHANGE_NAME, "goods.add", null, msg.getBytes());
        System.out.println("-----send " + msg);

        channel.close();
        connection.close();
    }

}
