/**
 * Copyright (C), 2018-2018, XXX有限公司
 * FileName: Txsend
 * Author:   18636
 * Date:     2018/10/25 19:50
 * Description: 事务机制——生产者
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.weiboo.rabbit.tx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.weiboo.rabbit.util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Txsend {

    private static final String QUEUE_NAME = "test_queue_tx";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String msg = "hello tx message!!";

        try {
            // 开启事务
            channel.txSelect();
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());

            //模拟报错
            int test = 1 / 0;

            System.out.println("send " + msg);
            // 提交事务
            channel.txCommit();
        } catch (IOException e) {
            channel.txRollback();
            e.printStackTrace();
        }
        // 会降低消息的吞吐量，通信太多，会产生大量的请求
        channel.close();
        connection.close();

    }
}
