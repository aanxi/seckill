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

@ApiModel(value="会员表")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "member")
public class Member {
    /**
     * 会员ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="会员ID")
    private Long id;

    /**
     * 会员头像
     */
    @TableField(value = "head_image_url")
    @ApiModelProperty(value="会员头像url")
    private String headImageUrl;

    /**
     * 会员名称
     */
    @TableField(value = "nickname")
    @ApiModelProperty(value="会员名称")
    private String nickname;

    /**
     * 会员手机号
     */
    @TableField(value = "phone_Number")
    @ApiModelProperty(value="会员手机号")
    private String phoneNumber;


    /**
     * 会员区域
     */
    @TableField(value = "district")
    @ApiModelProperty(value="会员区域")
    private String district;


    /**
     * 锁定状态,0:未锁定,1:锁定
     */
    @TableField(value = "lock_status")
    @ApiModelProperty(value="锁定状态,0:未锁定,1:锁定")
    private Integer lockStatus;
    public final static Integer LOCK_STATUS_OFF = 0;
    public final static Integer LOCK_STATUS_ON = 1;


    /**
     * 注册时间
     */
    @TableField(value = "create_at")
    @ApiModelProperty(value="会员注册时间")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  //,timezone=”GMT+8”
    private LocalDateTime createAt;

}
