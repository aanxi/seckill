package com.practice.seckill.admin.dto;

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
@ApiModel("新建及修改时id均无意义，传null")
@Builder
@Data
public class GoodsDTO {
    /**
     * 商品ID
     */
    @ApiModelProperty(value="商品ID")
    private Long id;

    /**
     * 商品名称
     */
    @ApiModelProperty(value="商品名称")
    private String name;

    /**
     * 商品价格
     */
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
    @ApiModelProperty(value="商品库存")
    private Integer inventory;

    /**
     * 商品销量
     */
    @ApiModelProperty(value="商品销量")
    private Integer sales;

    /**
     * 上架状态,0:未上架,1:上架
     */
    @ApiModelProperty(value="上架状态,0:未上架,1:上架")
    private Integer status;

    /**
     * 推荐状态,0:未推荐,1:推荐
     */
    @ApiModelProperty(value="推荐状态,0:未推荐,1:推荐")
    private Integer recommended;

    /**
     * 创建人用户名
     */
    @ApiModelProperty(value="创建人用户名")
    private String createdBy;

    /**
     * 商品发布时间
     */
    @ApiModelProperty(value="商品发布时间")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  //,timezone=”GMT+8”
    private LocalDateTime createTime;

}
