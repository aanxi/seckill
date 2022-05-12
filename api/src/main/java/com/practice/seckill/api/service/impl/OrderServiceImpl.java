package com.practice.seckill.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.practice.seckill.admin.mapper.GoodsMapper;
import com.practice.seckill.api.dto.SeckillInventoryDealDTO;
import com.practice.seckill.api.dto.SeckillOrderDTO;
import com.practice.seckill.api.mapper.OrderMapper;
import com.practice.seckill.api.service.OrderService;
import com.practice.seckill.common.constant.OrderConstant;
import com.practice.seckill.common.entity.Goods;
import com.practice.seckill.common.entity.Member;
import com.practice.seckill.common.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private OrderMapper orderMapper;
    @Transactional
    @Override
    public boolean createSeckillOrder(SeckillOrderDTO seckillOrderDTO) {
        // 快速构建订单项并保存
        Order order = Order.builder()
                .memberId(seckillOrderDTO.getMemberId())
                .goodsId(seckillOrderDTO.getGoodsId())
                .goodsNumber(seckillOrderDTO.getGoodsNumber())
                .consumption(seckillOrderDTO.getConsumption())
                .createAt(LocalDateTime.now())
                .build();
        orderMapper.insert(order);
        //通过消息队列完成库存扣减和销量更新，防止拖慢创建订单的速度,使用悲观锁更新，防止出现数据不一致
        SeckillInventoryDealDTO inventoryDealDTO = SeckillInventoryDealDTO.builder()
                .goodsId(seckillOrderDTO.getGoodsId())
                .count(seckillOrderDTO.getGoodsNumber())
                .build();
        try {
            rabbitTemplate.convertAndSend(OrderConstant.ORDER_EVENT_EXCHANGE, OrderConstant.ORDER_SECKILL_INVENTORY_DEAL_QUEUE_ROUTING_KEY, inventoryDealDTO);
            //向mq中发送订单创建完成消息
        } catch (AmqpException e) {
            //自动重试一次
            rabbitTemplate.convertAndSend(OrderConstant.ORDER_EVENT_EXCHANGE, OrderConstant.ORDER_SECKILL_INVENTORY_DEAL_QUEUE_ROUTING_KEY, inventoryDealDTO);
            log.warn("发送库存扣减消息到交换机失败");
        }
        return true;
    }

    @Override
    public int insert(Order entity) {
        return 0;
    }

    @Override
    public int deleteById(Serializable id) {
        return 0;
    }

    @Override
    public int deleteByMap(Map<String, Object> columnMap) {
        return 0;
    }

    @Override
    public int delete(Wrapper<Order> queryWrapper) {
        return 0;
    }

    @Override
    public int deleteBatchIds(Collection<? extends Serializable> idList) {
        return 0;
    }

    @Override
    public int updateById(Order entity) {
        return 0;
    }

    @Override
    public int update(Order entity, Wrapper<Order> updateWrapper) {
        return 0;
    }

    @Override
    public Order selectById(Serializable id) {
        return null;
    }

    @Override
    public List<Order> selectBatchIds(Collection<? extends Serializable> idList) {
        return null;
    }

    @Override
    public List<Order> selectByMap(Map<String, Object> columnMap) {
        return null;
    }

    @Override
    public Order selectOne(Wrapper<Order> queryWrapper) {
        return null;
    }

    @Override
    public Integer selectCount(Wrapper<Order> queryWrapper) {
        return null;
    }

    @Override
    public List<Order> selectList(Wrapper<Order> queryWrapper) {
        return null;
    }

    @Override
    public List<Map<String, Object>> selectMaps(Wrapper<Order> queryWrapper) {
        return null;
    }

    @Override
    public List<Object> selectObjs(Wrapper<Order> queryWrapper) {
        return null;
    }

    @Override
    public <E extends IPage<Order>> E selectPage(E page, Wrapper<Order> queryWrapper) {
        return null;
    }

    @Override
    public <E extends IPage<Map<String, Object>>> E selectMapsPage(E page, Wrapper<Order> queryWrapper) {
        return null;
    }
}
