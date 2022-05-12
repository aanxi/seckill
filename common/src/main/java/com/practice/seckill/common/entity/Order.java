package com.practice.seckill.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@ApiModel(value="订单表")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "`order`")
public class Order {
    /**
     * 订单Id
     */
    @TableId(value = "`id`", type = IdType.AUTO)
    @ApiModelProperty(value="订单Id")
    private Long id;

    /**
     * 用户Id
     */
    @TableField(value = "member_id")
    @ApiModelProperty(value="用户Id")
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
     * 消费金额
     */
    @TableField(value = "`consumption`")
    @ApiModelProperty(value="消费金额")
    private Double consumption;

    /**
     * 订单创建时间
     */
    @TableField(value = "create_at")
    @ApiModelProperty(value="订单创建时间")
    private LocalDateTime createAt;

}
