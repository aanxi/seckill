package com.practice.seckill.api.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.practice.seckill.api.dto.SeckillOrderDTO;
import com.practice.seckill.common.entity.Order;
import org.springframework.stereotype.Service;

public interface OrderService extends BaseMapper<Order> {
    /**
     * 秒杀业务快速下单发过来的消息
     *
     * @param seckillOrderDTO
     * @return
     */
    boolean createSeckillOrder(SeckillOrderDTO seckillOrderDTO);
}
