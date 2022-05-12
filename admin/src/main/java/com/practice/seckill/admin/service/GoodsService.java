package com.practice.seckill.admin.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.practice.seckill.admin.dto.GoodsDTO;
import com.practice.seckill.admin.dto.QueryConditionDTO;
import com.practice.seckill.admin.vo.GoodsRowVO;
import com.practice.seckill.admin.vo.PageVO;
import com.practice.seckill.common.bo.GoodsCacheBO;
import com.practice.seckill.common.entity.Goods;


public interface GoodsService extends BaseMapper<Goods> {
    /**
     * 获取商品缓存
     *
     * @param goodsId goodsId
     * @return bo
     */
    GoodsCacheBO getCachedGoods(Long goodsId);

    /**
     * 重建商品缓存
     *
     * @param goodsId goodsId
     * @return bo
     */
    GoodsCacheBO buildGoodsCacheBO(Long goodsId);

    /**
     * 删除商品缓存
     *
     * @param goodsId goodsId
     * @return 
     */
    void deleteGoodsCacheBO(Long goodsId);

    /**
     * 变更商品信息并更新缓存
     *
     * @param goodsId goodsId
     * @param dto     dto
     * @return dto
     */
    GoodsDTO modifyGoods(Long goodsId, GoodsDTO dto);

    /**
     * 创建商品
     *
     * @param dto dto
     * @return dto with id filled
     */
    GoodsDTO createGoods(GoodsDTO dto);
    /**
     * page goods
     *
//     * @param categoryId      商品分类
//     * @param goodsName       商品名称(模糊)
//     * @param priceLowerBound 价格下界
//     * @param priceUpperBound 价格上界
//     * @param startTime       时间段左界（发布时间不早于）
//     * @param endTime         时间段右界（发布时间晚于）
//     * @param goodsStatus     发布状态,0:未上架,1:已上架
//     * @param recommended     推荐状态,0:未推荐,1:已推荐
//     * @param pageNo          第几页
//     * @param pageSize        pageSize
     * @return page
     */
    PageVO<GoodsRowVO> pageGoods(QueryConditionDTO dto);

   /**
    * 删除商品
    *
    * @param goodsId
    * @return Boolean
    */
    Boolean deleteGoodsById(Long goodsId);
    /**
     * 秒杀更新商品库存,扣减count
     *
     * @param goodsId
     * @return Boolean
     */
    void updateGoodsInfo(Long goodsId, Integer count);

}
