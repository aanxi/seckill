package com.practice.seckill.api.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@ApiModel("秒杀库存处理dto")
@Builder
@Data
public class SeckillInventoryDealDTO {
    //商品id
    private Long goodsId;
    //库存扣减数
    private Integer count;
}
