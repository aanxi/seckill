package com.practice.seckill.api.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class SeckillActBO {
    /**
     * 秒杀活动ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "秒杀活动ID")
    private Long id;

    /**
     * 商品ID
     */
    @TableField(value = "goods_id")
    @ApiModelProperty(value = "商品ID")
    private Long goodsId;

    /**
     * 秒杀开始时间
     */
    @TableField(value = "begin_time")
    @ApiModelProperty(value = "秒杀开始时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  //,timezone=”GMT+8”
    private LocalDateTime beginTime;

    /**
     * 秒杀结束时间
     */
    @TableField(value = "end_time")
    @ApiModelProperty(value = "秒杀结束时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  //,timezone=”GMT+8”
    private LocalDateTime endTime;


    /**
     * 启用状态,0:未启用，1：已启用
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "启用状态")
    private Integer status;

}
