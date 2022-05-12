package com.practice.seckill.common.constant;

public class OrderConstant {

    // 消息队列，交换机和队列
    public static final String ORDER_EVENT_EXCHANGE = "order-event-exchange";
    public static final String ORDER_SECKILL_DEAL_QUEUE = "order.seckill.deal.queue";
    public static final String ORDER_SECKILL_DEAL_QUEUE_ROUTING_KEY = "order.seckill.deal.#";
    public static final String ORDER_SECKILL_INVENTORY_DEAL_QUEUE = "order.seckill.inventory.deal.queue";
    public static final String ORDER_SECKILL_INVENTORY_DEAL_QUEUE_ROUTING_KEY = "order.seckill.inventory.deal.#";

}
