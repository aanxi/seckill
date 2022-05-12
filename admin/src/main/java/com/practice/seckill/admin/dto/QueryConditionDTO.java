package com.practice.seckill.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@ApiModel("查询条件DTO")
@Builder
@Data
public class QueryConditionDTO {
    @ApiModelProperty(value="分类")
    private Long categoryId;
    @ApiModelProperty(value="商品名称")
    private String goodsName;
    @ApiModelProperty(value="价格下界")
    private Integer priceLowerBound;
    @ApiModelProperty(value="价格上界")
    private Integer priceUpperBound;
    @ApiModelProperty(value="时间左界")
    private LocalDateTime startTime;
    @ApiModelProperty(value="时间右界")
    private LocalDateTime endTime;
    @ApiModelProperty(value="发布状态")
    private Integer goodsStatus;
    @ApiModelProperty(value="推荐状态")
    private Integer recommended;
    @ApiModelProperty(value="pageNo")
    private Integer pageNo;
    @ApiModelProperty(value="pageSize")
    private Integer pageSize;
}
