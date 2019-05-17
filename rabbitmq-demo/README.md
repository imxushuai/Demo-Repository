## RabbitMQ Demo
> 本次项目中包含了RabbitMQ Java API的使用以及使用Spring boot 整合 RabbitMQ的使用。

### RabbitMQ Java API
> 内容和官方教程中类似，总共有五个模块，分别对应官方教程中的前五个。
>
> 官方教程链接：[https://www.rabbitmq.com/getstarted.html](https://www.rabbitmq.com/getstarted.html)

#### simple

![](https://raw.githubusercontent.com/imxushuai/ForPicGo/master/006ifTg0gy1g2qt642cnfj30aw01nwec.jpg)

`Hello World`，使用`RabbitMQ`进行简单的消息发送和接收。

#### work

![](https://raw.githubusercontent.com/imxushuai/ForPicGo/master/006ifTg0gy1g2qut29j13j3098033a9z.jpg)

`Work`，Work模式为竞争消费者模式，多个消费者绑定在同一个队列中，实现一个队列中的消息被多个消费者消费。

#### Fanout

![](https://raw.githubusercontent.com/imxushuai/ForPicGo/master/006kPtiPgy1g2rmvbxzjrj30c2033q2x.jpg)

`发布/订阅`模型中的`Fanout`，即通过广播的方式将消息发送到多个队列。

#### Direct

![](https://raw.githubusercontent.com/imxushuai/ForPicGo/master/direct-exchange.png)

`发布/订阅`模型中的`Direct`，将消息发送给对应`RoutingKey`的队列。

#### Topic
`发布/订阅`模型中的`Topic`，和`Direct`类似，也是发送给对应的`RoutingKey`，区别在于`Direct`中的`RoutingKey`为确切的值，而`Topic`中的`RoutingKey`为通配符。

### Spring AMQP
使用`Spring Boot`整合`RabbitMQ`，使用纯注解的方式，完成消息的发送和接收。



[![](https://img.shields.io/badge/SpringBoot-2.0.4.RELEASE-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![](https://img.shields.io/badge/RabbitMQ-3.6.10-brightgreen.svg)](https://www.rabbitmq.com)


博客文章：

[RabbitMQ详解](https://www.imxushuai.com/2019/05/08/RabbitMQ%E8%AF%A6%E8%A7%A3/)