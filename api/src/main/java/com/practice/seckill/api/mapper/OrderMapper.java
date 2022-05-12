package com.practice.seckill.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.practice.seckill.common.entity.Order;
import org.mapstruct.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    Long getMaxId();
}
