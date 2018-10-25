/**
 * Copyright (C), 2018-2018, XXX有限公司
 * FileName: Send
 * Author:   18636
 * Date:     2018/10/24 19:32
 * Description: 工作队列消息生产者
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.weiboo.rabbit.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.weiboo.rabbit.util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    /**
     *               |----C1
     * P----Queue----|
     *               |----C2
     */

    private static final String QUEUE_NAME = "test_work_queue";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        // 获取连接
        Connection connection = ConnectionUtils.getConnection();
        // 获取通道
        Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 模拟批量数据，发送50条消息
        for (int i = 0; i < 50; i++) {
            String msg = "hello-" + i;
            System.out.println("[WQ] send " + msg);
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            Thread.sleep(i * 20);
        }
        // 关闭资源
        channel.close();
        connection.close();
    }

}
