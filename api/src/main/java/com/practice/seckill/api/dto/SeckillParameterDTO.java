package com.practice.seckill.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@ApiModel("秒杀请求参数dto")
@Builder
@Data
public class SeckillParameterDTO {
    @ApiModelProperty(value = "用户Id")
    private Long memberId;

    @ApiModelProperty(value = "秒杀活动Id")
    private Long seckillId;

    @ApiModelProperty(value = "秒杀令牌")
    private String seckillToken;

    @ApiModelProperty(value = "商品数量")
    private Integer count;
}
