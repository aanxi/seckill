package com.practice.seckill.api.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 秒杀商品在redis中的存储对象
 */
@Data
public class SeckillGoodsRedisBO {

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
//    /**
//     * 秒杀总量,另外保存
//     */
//    private Integer seckillCount;

    /**
     * 秒杀令牌，只有在秒杀活动开始时才会暴露出来，秒杀期间以令牌为准，防止提前消耗
     */
    private String seckillToken;


    // 秒杀开始时间
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime beginTime;

    // 秒杀结束时间
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

}
