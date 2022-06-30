package com.practice.seckill.api.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class SeckillOrderDTO implements Serializable {
    /**
     * 订单Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "订单Id")
    private Long id;

    /**
     * 用户Id
     */
    @TableField(value = "member_id")
    @ApiModelProperty(value = "用户Id")
    private Long memberId;

    /**
     * 商品Id
     */
    @TableField(value = "goods_id")
    @ApiModelProperty(value = "商品Id")
    private Long goodsId;

    /**
     * 商品数量
     */
    @TableField(value = "goods_number")
    @ApiModelProperty(value = "商品数量")
    private Integer goodsNumber;

    /**
     * 消费金额
     */
    @ApiModelProperty(value = "消费金额")
    private Double consumption;

    /**
     * 订单创建时间
     */
    @TableField(value = "create_at")
    @ApiModelProperty(value = "订单创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createAt;
}
