package com.practice.seckill.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.practice.seckill.admin.constant.RdsKeyGenor;
import com.practice.seckill.admin.dto.GoodsDTO;
import com.practice.seckill.admin.dto.QueryConditionDTO;
import com.practice.seckill.admin.mapper.GoodsMapper;
import com.practice.seckill.admin.service.GoodsService;
import com.practice.seckill.admin.vo.GoodsRowVO;
import com.practice.seckill.admin.vo.PageVO;
import com.practice.seckill.common.bo.GoodsCacheBO;
import com.practice.seckill.common.entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public GoodsCacheBO getCachedGoods(Long goodsId) {
        GoodsCacheBO GoodsCacheBO = (GoodsCacheBO) redisTemplate.opsForValue().get(RdsKeyGenor.goodsKey(goodsId));
        if (GoodsCacheBO != null) {
            return GoodsCacheBO;
        }
        return buildGoodsCacheBO(goodsId);
    }

    @Override
    public GoodsCacheBO buildGoodsCacheBO(Long goodsId) {
        Goods goods = goodsMapper.selectById(goodsId);
        return buildGoodsCacheBO(goods);
    }

    private GoodsCacheBO buildGoodsCacheBO(Goods goods) {
        GoodsCacheBO goodsCache = GoodsCacheBO.builder()
                .name(goods.getName())
                .price(goods.getPrice())
                .inventory(goods.getInventory())
                .sales(goods.getSales())
                .status(goods.getStatus())
                .recommended(goods.getRecommended())
                .createTime(goods.getCreateTime())
                .build();
        redisTemplate.opsForValue().set(
                RdsKeyGenor.goodsKey(goods.getId()),
                goodsCache,
                RdsKeyGenor.goodsExpire());
        return goodsCache;
    }

    @Override
    @Transactional
    public GoodsDTO modifyGoods(Long goodsId, GoodsDTO dto) {
        Goods goods = Goods.builder()
                .id(goodsId)
                .name(dto.getName())
                .price(dto.getPrice())
                .imageId(dto.getImageId())
                .categoryId(dto.getCategoryId())
                .inventory(dto.getInventory())
                .sales(dto.getSales())
                .status(dto.getStatus())
                .recommended(dto.getRecommended())
                .createdBy(dto.getCreatedBy())
                .createTime(dto.getCreateTime())
                .deleted(0)
                .build();
        if (goodsMapper.updateById(goods) > 0) {
            //更新缓存
            buildGoodsCacheBO(goods);
            return dto;
        }
        return null;
    }

    @Override
    public void deleteGoodsCacheBO(Long goodsId) {
        String redisKey = RdsKeyGenor.goodsKey(goodsId);
        redisTemplate.delete(redisKey);
    }

    /**
     * 删除商品
     *
     * @param goodsId goodsId
     * @return boolean
     */
    @Override
    public Boolean deleteGoodsById(Long goodsId) {
        Goods goods = Goods.builder()
                .id(goodsId)
                .deleted(1)
                .build();
        if (goodsMapper.updateById(goods) > 0) {
            deleteGoodsCacheBO(goodsId);
            return true;
        }
        return false;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public void updateGoodsInfo(Long goodsId, Integer count) {
        int newInventory = goodsMapper.selectGoodsInventoryAndLock(goodsId) - count;
        int newSales = goodsMapper.selectGoodsSalesAndLock(goodsId) + count;
        goodsMapper.updateInventoryAndSalesById(goodsId, newInventory, newSales);
    }

    @Override
    public PageVO<GoodsRowVO> pageGoods(QueryConditionDTO dto) {
        List<GoodsRowVO> goodsList = goodsMapper.listGoods(dto.getCategoryId(),
                dto.getGoodsName(),
                dto.getPriceLowerBound(),
                dto.getPriceUpperBound(),
                dto.getStartTime(),
                dto.getEndTime(),
                dto.getGoodsStatus(),
                dto.getRecommended()
        );
        PageVO<GoodsRowVO> page = new PageVO(dto.getPageNo(), dto.getPageSize(), (long) goodsList.size());
        page.setRecords(goodsList);
        return page;
    }

    @Override
    public GoodsDTO createGoods(GoodsDTO dto) {
        Goods newGoods = Goods.builder()
                .id(goodsMapper.getMaxId() + 1)
                .name(dto.getName())
                .price(dto.getPrice())
                .imageId(dto.getImageId())
                .categoryId(dto.getCategoryId())
                .inventory(dto.getInventory())
                .sales(dto.getSales())
                .status(dto.getStatus())
                .recommended(dto.getRecommended())
                .createdBy(dto.getCreatedBy())
                //发布时间
                .createTime(dto.getCreateTime())
                //新建商品时设置删除状态为0
                .deleted(0)
                .build();
        goodsMapper.insert(newGoods);
        dto.setId(newGoods.getId());
        return dto;
    }

    @Override
    public int insert(Goods entity) {
        return 0;
    }

    @Override
    public int deleteById(Serializable id) {
        return 0;
    }

    @Override
    public int deleteByMap(Map<String, Object> columnMap) {
        return 0;
    }

    @Override
    public int delete(Wrapper<Goods> queryWrapper) {
        return 0;
    }

    @Override
    public int deleteBatchIds(Collection<? extends Serializable> idList) {
        return 0;
    }

    @Override
    public int updateById(Goods entity) {
        return 0;
    }

    @Override
    public int update(Goods entity, Wrapper<Goods> updateWrapper) {
        return 0;
    }

    @Override
    public Goods selectById(Serializable id) {
        return null;
    }

    @Override
    public List<Goods> selectBatchIds(Collection<? extends Serializable> idList) {
        return null;
    }

    @Override
    public List<Goods> selectByMap(Map<String, Object> columnMap) {
        return null;
    }

    @Override
    public Goods selectOne(Wrapper<Goods> queryWrapper) {
        return null;
    }

    @Override
    public Integer selectCount(Wrapper<Goods> queryWrapper) {
        return null;
    }

    @Override
    public List<Goods> selectList(Wrapper<Goods> queryWrapper) {
        return null;
    }

    @Override
    public List<Map<String, Object>> selectMaps(Wrapper<Goods> queryWrapper) {
        return null;
    }

    @Override
    public List<Object> selectObjs(Wrapper<Goods> queryWrapper) {
        return null;
    }

    @Override
    public <E extends IPage<Goods>> E selectPage(E page, Wrapper<Goods> queryWrapper) {
        return null;
    }

    @Override
    public <E extends IPage<Map<String, Object>>> E selectMapsPage(E page, Wrapper<Goods> queryWrapper) {
        return null;
    }
}
