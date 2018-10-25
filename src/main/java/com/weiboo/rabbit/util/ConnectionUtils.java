/**
 * Copyright (C), 2018-2018, XXX有限公司
 * FileName: ConnectionUtils
 * Author:   18636
 * Date:     2018/10/24 17:15
 * Description: mq工具类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.weiboo.rabbit.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionUtils {

    /**
     * 获取MQ的链接
     * @return
     * @throws IOException
     * @throws TimeoutException
     */
    public static Connection getConnection() throws IOException, TimeoutException {
        // 定义一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置服务地址
        factory.setHost("127.0.0.1");
        // AMQP 5672
        factory.setPort(5672);
        // vhost
        factory.setVirtualHost("/vhost_1");
        // 用户名
        factory.setUsername("admin");
        // 密码
        factory.setPassword("admin");
        return factory.newConnection();
    }
}
