package com.practice.seckill.admin.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "member")
public class MemberRowVO {
    /**
     * 会员ID
     */
    @ApiModelProperty(value = "会员ID")
    private Long id;

    /**
     * 会员头像
     */
    @ApiModelProperty(value = "会员头像url")
    private String headImageUrl;

    /**
     * 会员名称
     */
    @ApiModelProperty(value = "会员名称")
    private String nickname;

    /**
     * 会员手机号
     */
    @ApiModelProperty(value = "会员手机号")
    private String phoneNumber;


    /**
     * 会员区域
     */
    @ApiModelProperty(value = "会员区域")
    private String district;


    /**
     * 锁定状态,0:未锁定,1:锁定
     */
    @ApiModelProperty(value = "锁定状态,0:未锁定,1:锁定")
    private Integer lockStatus;
    public final static Integer LOCK_STATUS_OFF = 0;
    public final static Integer LOCK_STATUS_ON = 1;

    /**
     * 消费金额
     */
    @ApiModelProperty(value = "消费金额")
    private Double consumptionSum;

    /**
     * 订单数量
     */
    @ApiModelProperty(value = "订单数量")
    private Integer orderNumber;

    /**
     * 注册时间
     */
    @ApiModelProperty(value = "会员注册时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  //,timezone=”GMT+8”
    private LocalDateTime createAt;

}
