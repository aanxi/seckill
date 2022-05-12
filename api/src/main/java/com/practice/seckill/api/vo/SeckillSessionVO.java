package com.practice.seckill.api.vo;

import com.practice.seckill.api.bo.SeckillGoodsBO;
import com.practice.seckill.common.entity.Goods;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class SeckillSessionVO {

    /**
     * id
     */
    private Long id;
    /**
     * 场次名称
     */
    private String name;
    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
    /**
     * 启用状态
     */
    private Integer status;

    // 相关联的商品
    SeckillGoodsBO relationGoods;
}

