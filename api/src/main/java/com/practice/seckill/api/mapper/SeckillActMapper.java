package com.practice.seckill.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.practice.seckill.common.entity.SeckillAct;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface SeckillActMapper extends BaseMapper<SeckillAct> {

    List<Long> selectSeckillGoodsIds();

}
