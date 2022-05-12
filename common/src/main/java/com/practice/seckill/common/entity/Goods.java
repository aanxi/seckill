package com.practice.seckill.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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

@ApiModel(value="商品表")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "goods")
public class Goods {
    /**
     * 商品ID
     */
//    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="商品ID")
    private Long id;

    /**
     * 商品名称
     */
    @TableField(value = "`name`")
    @ApiModelProperty(value="商品名称")
    private String name;

    /**
     * 商品价格
     */
    @TableField(value = "price")
    @ApiModelProperty(value="商品价格")
    private Double price;

    /**
     * 商品图片
     */
    @ApiModelProperty(value="商品图片Id")
    private Long imageId;

    /**
     * 商品类型
     */
    @ApiModelProperty(value="商品类型Id")
    private Long categoryId;

    /**
     * 商品库存
     */
    @TableField(value = "`inventory`")
    @ApiModelProperty(value="商品库存")
    private Integer inventory;

    /**
     * 商品销量
     */
    @TableField(value = "sales")
    @ApiModelProperty(value="商品销量")
    private Integer sales;

    /**
     * 上架状态,0:未上架,1:上架
     */
    @TableField(value = "status")
    @ApiModelProperty(value="上架状态,0:未上架,1:上架")
    private Integer status;
    public final static Integer STATUS_OFF = 0;
    public final static Integer STATUS_ON = 1;

    /**
     * 推荐状态,0:未推荐,1:推荐
     */
    @TableField(value = "recommended")
    @ApiModelProperty(value="推荐状态,0:未推荐,1:推荐")
    private Integer recommended;
    public final static Integer RECOMMENDED_OFF = 0;
    public final static Integer RECOMMENDED_ON = 1;

    /**
     * 创建人用户名
     */
    @TableField(value = "created_by")
    @ApiModelProperty(value="创建人用户名")
    private String createdBy;

    /**
     * 商品创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value="商品发布时间")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  //,timezone=”GMT+8”
    private LocalDateTime createTime;

    /**
     * 逻辑删除 - 0:未删除;1:已删除
     */
    @TableField(value = "deleted")
    @ApiModelProperty(value="逻辑删除 - 0:未删除;1:已删除")
    private Integer deleted;

}
