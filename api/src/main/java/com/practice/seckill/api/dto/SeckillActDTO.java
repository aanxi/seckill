package com.practice.seckill.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@ApiModel("秒杀活动dto")
@Builder
@Data
public class SeckillActDTO {
    /**
     * 秒杀活动ID
     */
    @ApiModelProperty(value = "秒杀活动ID")
    private Long id;

    /**
     * 商品ID
     */
    @ApiModelProperty(value = "商品ID")
    private Long goodsId;

    /**
     * 秒杀开始时间
     */
    @ApiModelProperty(value = "秒杀开始时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  //,timezone=”GMT+8”
    private LocalDateTime beginTime;

    /**
     * 秒杀结束时间
     */
    @ApiModelProperty(value = "秒杀结束时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  //,timezone=”GMT+8”
    private LocalDateTime endTime;

    /**
     * 启用状态,0:未启用，1：已启用
     */
    @ApiModelProperty(value = "启用状态")
    private Integer status;

}
