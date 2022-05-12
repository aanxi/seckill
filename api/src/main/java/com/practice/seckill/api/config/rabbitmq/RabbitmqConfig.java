package com.practice.seckill.api.config.rabbitmq;


import com.practice.seckill.api.constant.WareConstant;
import com.practice.seckill.common.constant.OrderConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * 首先开启，@EnableRabbit
 * 可以 @Autowired AmqpAdmin手动创建，也可以@Bean自动创建
 * 只有在真正监听某个队列的时候，或者发送消息，或者使用amqpadmin创建别的队列，这些不存在的交换机和队列以及绑定关系就会被自动创建
 * 如果已有，则不会重复创建，哪怕自己指定了别的参数，也不会创建一个新的去覆盖
 */
@EnableRabbit
@Configuration
@Slf4j
public class RabbitmqConfig implements RabbitListenerConfigurer {

    @Autowired
    RabbitTemplate rabbitTemplate;
    // 提供自定义RabbitTemplate,将对象序列化为json串
    @Bean
    public RabbitTemplate jacksonRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }
    // 可以将json串反序列化为对象
    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {
        rabbitListenerEndpointRegistrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }

    @Bean
    MessageHandlerMethodFactory messageHandlerMethodFactory(){
        DefaultMessageHandlerMethodFactory messageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
        messageHandlerMethodFactory.setMessageConverter(mappingJackson2MessageConverter());
        return messageHandlerMethodFactory;
    }

    @Bean
    public MappingJackson2MessageConverter mappingJackson2MessageConverter(){
        return  new MappingJackson2MessageConverter();
    }

    /**
     * 如果发送的消息是一个对象，会使用序列化机制，由MessageConverter转换器处理，
     * 默认是WhiteListDeserializingMessageConverter，使用jdk序列化，所以这些bean必须实现Serializable接口
     *
     * 为了使用json序列化，我们需要往容器中添加一个使用json格式的MessageConverter，发送的消息会标记这个对象的全类名
     *
     * 监听队列的方法参数，可以使用Object obj来接收消息内容，通过obj.getClass()能看到真正类型是 org.springframework.amqp.core.Message
     * 所以也可以直接使用 Message me来接收，通过me.getBody()能够得到消息体
     * 如果知道消息体的本质类型，也可以直接使用 XXXEntity 来接收，
     * @return
     */
    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @PostConstruct
    public void setCallback() {
        /**
         * 消息由生产者投递到Broker/Exchange回调
         */
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                log.info("消息投递到交换机成功：[correlationData={}]",correlationData);
            } else {
                log.error("消息投递到交换机失败：[correlationData={}，原因：{}]", correlationData, cause);
            }
        });
//        /**
//         * 消息由Exchange路由到Queue失败回调
//         */
//        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
//            log.error("路由到队列失败，消息内容：{}，交换机：{}，路由件：{}，回复码：{}，回复文本：{}", message, exchange, routingKey, replyCode, replyText);
//        });
    }

    @Bean
    public Exchange orderEventExchange() {
        // String name, boolean durable, boolean autoDelete, Map<String, Object> arguments
        return new TopicExchange(OrderConstant.ORDER_EVENT_EXCHANGE,
                true,
                false);
    }

//    @Bean
//    public Queue orderReleaseOrderQueue() {
//        //String name, boolean durable, boolean exclusive, boolean autoDelete,
//        // 			@Nullable Map<String, Object> arguments
//        return new Queue(OrderConstant.ORDER_RELEASE_ORDER_QUEUE,
//                true,
//                false,
//                false);
//    }

//    // 暂时未使用，订单取消时，要回滚扣减的优惠
//    @Bean
//    public Queue orderReleaseCouponQueue() {
//        //String name, boolean durable, boolean exclusive, boolean autoDelete,
//        // 			@Nullable Map<String, Object> arguments
//        return new Queue(OrderConstant.ORDER_RELEASE_COUPON_QUEUE,
//                true,
//                false,
//                false);
//    }

//    /**
//     * 死信队列/延时队列
//     * @return
//     *
//     * x-dead-letter-exchange="stock-event-exchange"
//     * x-dead-letter-routing-key="stock.release"
//     * x-message-ttl="60000"
//     */
//    @Bean
//    public Queue orderDelayQueue() {
//        //String name, boolean durable, boolean exclusive, boolean autoDelete,
//        // 			@Nullable Map<String, Object> arguments
//        Map<String, Object> arguments = new HashMap<>();
//        arguments.put("x-dead-letter-exchange", OrderConstant.DEAD_LETTER_EXCHANGE);
//        arguments.put("x-dead-letter-routing-key", OrderConstant.ORDER_DEAD_LETTER_ROUTING_KEY);
//        arguments.put("x-message-ttl", OrderConstant.DEAD_LETTER_TTL);
//        return new Queue(OrderConstant.ORDER_DELAY_ORDER_QUEUE,
//                true,
//                false,
//                false,
//                arguments);
//    }

    /**
     * 绑定关系
     */
//    @Bean
//    public Binding orderCreateBinding() {
//        // String destination, DestinationType destinationType, String exchange, String routingKey,
//        // 			@Nullable Map<String, Object> arguments
//        return new Binding(OrderConstant.ORDER_DELAY_ORDER_QUEUE,
//                Binding.DestinationType.QUEUE,
//                OrderConstant.ORDER_EVENT_EXCHANGE,
//                OrderConstant.ORDER_CREATE_ROUTING_KEY,
//                null);
//    }

    @Bean
    public Binding inventoryDealBinding() {
        // String destination, DestinationType destinationType, String exchange, String routingKey,
        // 			@Nullable Map<String, Object> arguments
        return new Binding(OrderConstant.ORDER_SECKILL_INVENTORY_DEAL_QUEUE,
                Binding.DestinationType.QUEUE,
                OrderConstant.ORDER_EVENT_EXCHANGE,
                OrderConstant.ORDER_SECKILL_INVENTORY_DEAL_QUEUE_ROUTING_KEY,
                null);
    }

    /**
     * 秒杀消息队列和交换机的绑定关系
     * @return
     */
    @Bean
    public Binding seckillOrderDealBinding() {
        // String destination, DestinationType destinationType, String exchange, String routingKey,
        // 			@Nullable Map<String, Object> arguments
        return new Binding(OrderConstant.ORDER_SECKILL_DEAL_QUEUE,
                Binding.DestinationType.QUEUE,
                OrderConstant.ORDER_EVENT_EXCHANGE,
                OrderConstant.ORDER_SECKILL_DEAL_QUEUE_ROUTING_KEY,
                null);
    }

}

