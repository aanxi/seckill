package com.practice.seckill.api.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class SeckillGoodsBO {
    /**
     * 活动id
     */
    private Long id;

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 秒杀价格
     */
    private Double seckillPrice;
    /**
     * 秒杀总量
     */
    private Integer seckillCount;

    /**
     * 秒杀令牌，只有在秒杀活动开始时才会暴露出来，秒杀期间以令牌为准，防止提前消耗
     */
    private String seckillToken;

    /**
     * 商品库存
     */
    private Integer inventory;

    // 秒杀开始时间
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    // 秒杀结束时间
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
}
