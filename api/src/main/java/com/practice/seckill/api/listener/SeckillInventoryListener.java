package com.practice.seckill.api.listener;

import com.practice.seckill.admin.service.GoodsService;
import com.practice.seckill.api.dto.SeckillInventoryDealDTO;
import com.practice.seckill.common.constant.OrderConstant;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RabbitListener(queues = {OrderConstant.ORDER_SECKILL_INVENTORY_DEAL_QUEUE})
public class SeckillInventoryListener {
    @Autowired
    GoodsService goodsService;

    @RabbitHandler
    public void handleSeckillInventoryMessage(SeckillInventoryDealDTO inventoryDealDTO, Message message, Channel channel) throws IOException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            goodsService.updateGoodsInventory(inventoryDealDTO.getGoodsId(), inventoryDealDTO.getCount());
            // 消费成功，手动ack
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            // 消费失败，消息重新入队
            channel.basicReject(deliveryTag, true);
            log.error("消息队列手动ack失败：com.practice.seckill.api.listener.SeckillInventoryListener.handleSeckillInventoryMessage, Error: {}", e);
        }
    }
}
