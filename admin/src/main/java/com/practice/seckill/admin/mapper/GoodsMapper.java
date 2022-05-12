package com.practice.seckill.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;


import com.practice.seckill.admin.vo.GoodsRowVO;
import com.practice.seckill.common.entity.Goods;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {

    List<GoodsRowVO> listGoods(
            @Param("categoryId") Long categoryId,
            @Param("goodsName") String goodsName,
            @Param("priceLowerBound") Integer priceLowerBound,
            @Param("priceUpperBound") Integer priceUpperBound,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("goodsStatus") Integer goodsStatus,
            @Param("recommended") Integer recommended);
    Integer selectGoodsInventoryAndLock(@Param("goodsId") Long goodsId);
    Integer selectGoodsSalesAndLock(@Param("goodsId") Long goodsId);
    Integer updateInventoryAndSalesById(@Param("goodsId") Long goodsId, @Param("newInventory") Integer newInventory, @Param("newSales") Integer newSales);
    Long getMaxId();
}
