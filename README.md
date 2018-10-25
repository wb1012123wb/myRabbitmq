1.1 搭建WEB项目框架 — 将Maven项目调整为Web目录结构：

    消息持久化
    channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
    durable: 持久化参数
    
    channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
    第一个参数：""表示匿名转发
    
    