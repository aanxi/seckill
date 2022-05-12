package com.practice.seckill.api.service;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.practice.seckill.api.bo.SeckillGoodsBO;
import com.practice.seckill.api.dto.SeckillOrderDTO;
import com.practice.seckill.api.dto.SeckillParameterDTO;
import com.practice.seckill.common.entity.SeckillAct;

public interface SeckillService extends BaseMapper<SeckillAct> {
    /**
     * upload近期
     * @param
     * @return
     */
     boolean uploadLatest3DaysSeckillGoods() throws Exception;

    /**
     * 实际秒杀
     * @param parameterDTO
     * @return
     */
    SeckillOrderDTO seckillItem(SeckillParameterDTO parameterDTO) throws Exception;
}
