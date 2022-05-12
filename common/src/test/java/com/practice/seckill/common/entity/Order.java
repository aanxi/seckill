package com.practice.seckill.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@ApiModel(value="订单表")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "order")
public class Order {
    /**
     * 订单ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="订单ID")
    private Long id;

    /**
     * 会员Id
     */
    @TableField(value = "member_id")
    @ApiModelProperty(value="会员Id")
    private Long memberId;

    /**
     * 商品Id
     */
    @TableField(value = "goods_id")
    @ApiModelProperty(value="商品Id")
    private Long goodsId;

    /**
     * 商品数量
     */
    @TableField(value = "goods_number")
    @ApiModelProperty(value="商品数量")
    private Integer goodsNumber;

    /**
     * 订单金额
     */
    @TableField(value = "consumption")
    @ApiModelProperty(value="订单金额")
    private Double consumption;

    /**
     * 订单创建时间
     */
    @TableField(value = "create_at")
    @ApiModelProperty(value="订单创建时间")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  //,timezone=”GMT+8”
    private LocalDateTime createAt;

}
