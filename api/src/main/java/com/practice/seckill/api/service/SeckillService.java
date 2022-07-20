package com.practice.seckill.api.service;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.practice.seckill.api.bo.SeckillGoodsBO;
import com.practice.seckill.api.dto.SeckillOrderDTO;
import com.practice.seckill.api.dto.SeckillParameterDTO;
import com.practice.seckill.api.vo.SeckillGoodsVO;
import com.practice.seckill.common.entity.SeckillAct;

public interface SeckillService extends BaseMapper<SeckillAct> {
    /**
     * upload近期
     *
     * @param
     * @return
     */
    boolean uploadLatest3DaysSeckillGoods() throws Exception;

    /**
     * 实际秒杀
     *
     * @param parameterDTO
     * @return
     */
    SeckillOrderDTO seckillItem(SeckillParameterDTO parameterDTO) throws Exception;

    /**
    * @Description 获取未开始的和正在进行的秒杀商品列表，利用定时任务将秒杀活动的状态改变
    * @Param
    * @return
    */
    SeckillGoodsVO SeckillGoodsList();
}
