package com.practice.seckill.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("GoodsVO")
@Builder
public class SeckillGoodsVO {
    /**
     * 商品ID
     */
    @ApiModelProperty(value = "商品ID")
    private Long id;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String name;

    /**
     * 商品价格
     */
    @ApiModelProperty(value = "商品价格")
    private Double price;

    /**
     * 商品图片
     */
    @ApiModelProperty(value = "商品图片url")
    private String imageUrl;
    /**
     * 秒杀开始时间
     */
    @ApiModelProperty(value = "秒杀开始时间")
    private LocalDateTime beginTime;

    /**
     * 秒杀开始时间
     */
    @ApiModelProperty(value = "秒杀结束时间")
    private LocalDateTime endTime;

    /**
     * 秒杀 开始 结束状态
     */
    @ApiModelProperty(value = "秒杀状态")
    private Integer seckillActStatus;
    public static final Integer STATUS_NOT_START = 0;
    public static final Integer STATUS_IN_PROGRESS = 1;
    public static final Integer STATUS_END = 2;

}

