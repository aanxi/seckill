package com.practice.seckill.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="分类表")
public class Category {
    /**
     * 分类ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="分类ID")
    private Long id;

    /**
     * 分类名称
     */
    @TableField(value = "`name`")
    @ApiModelProperty(value="分类名称")
    private String name;

    /**
     * 分类描述
     */
    @TableField(value = "`description`")
    @ApiModelProperty(value="分类描述")
    private String description;

}
