package com.practice.seckill.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("图片表")
public class Image {
    /**
     * 图片ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "图片ID")
    private Long id;

    /**
     * 图片链接
     */
    @TableField(value = "link")
    @ApiModelProperty(value = "图片链接")
    private String link;

}
