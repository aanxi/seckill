package com.practice.seckill.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.practice.seckill.admin.mapper.GoodsMapper;
import com.practice.seckill.api.constant.SeckillConstant;
import com.practice.seckill.api.bo.SeckillGoodsBO;
import com.practice.seckill.api.bo.SeckillGoodsRedisBO;

import com.practice.seckill.api.dto.SeckillActDTO;
import com.practice.seckill.api.dto.SeckillOrderDTO;

import com.practice.seckill.api.dto.SeckillParameterDTO;
import com.practice.seckill.api.mapper.OrderMapper;
import com.practice.seckill.api.mapper.SeckillActMapper;
import com.practice.seckill.api.service.SeckillService;
import com.practice.seckill.common.constant.OrderConstant;

import com.practice.seckill.common.entity.Goods;
import com.practice.seckill.common.entity.SeckillAct;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.Throw;
import org.redisson.api.RLock;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class SeckillServiceImpl implements SeckillService {
    
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    SeckillActMapper seckillActMapper;

    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    OrderMapper orderMapper;

    /**
     * 上架最近三天参与秒杀的活动以及商品
     * @return
     */
    @Override
    public boolean uploadLatest3DaysSeckillGoods() throws Exception {
        // 查询当前时间的未来三天内开始的秒杀活动和商品
        List<SeckillAct> actList = seckillActMapper.selectList(new QueryWrapper<SeckillAct>().between("begin_time",LocalDateTime.now(),LocalDateTime.now().plusDays(3)));
        
        if (actList.size() == 0) {
            log.error("最近三天无秒杀活动");
            throw new Exception("上架秒杀活动信息失败");
        }
        List<SeckillActDTO> actDTOList = new ArrayList<>();
        for(SeckillAct act: actList) {
            SeckillActDTO actDTO = new SeckillActDTO();
            BeanUtils.copyProperties(act, actDTO);
            actDTOList.add(actDTO);
        }
        // 保存活动场次信息
        saveSeckillAct(actDTOList);
        // 保存活动商品和库存信息
        saveSeckillGoods(actDTOList);
        return true;
    }

    /**
     * 秒杀
     * @param parameterDTO
     */
    @Override
    public SeckillOrderDTO seckillItem(SeckillParameterDTO parameterDTO) throws Exception {
        // 校验秒杀活动和商品在redis中是否存在
        if (!stringRedisTemplate.hasKey(SeckillConstant.REDIS_SECKILL_ACT_PREFIX + parameterDTO.getSeckillId())) {
            throw new Exception("秒杀活动不存在");
        }
        BoundHashOperations<String, String, String> ops = stringRedisTemplate.boundHashOps(SeckillConstant.REDIS_SECKILL_GOODS_INFO_KEY);
        //获取秒杀活动相关商品
        String goodsId = stringRedisTemplate.opsForValue().get(SeckillConstant.REDIS_SECKILL_ACT_PREFIX + parameterDTO.getSeckillId());
        String inventoryKey = SeckillConstant.REDIS_SECKILL_ACT_PREFIX + parameterDTO.getSeckillId() + "_" + goodsId;
        if (!ops.hasKey(parameterDTO.getSeckillId() + "_" + goodsId) || !stringRedisTemplate.hasKey(inventoryKey)) {
            throw new Exception("秒杀商品不存在");
        }
        // 校验令牌是否一致
        String s = ops.get(parameterDTO.getSeckillId() + "_" + goodsId);
        SeckillGoodsRedisBO redisBO = JSON.parseObject(s, SeckillGoodsRedisBO.class);
        if (!parameterDTO.getSeckillToken().equals(redisBO.getSeckillToken())) {
            throw new Exception("秒杀失败，商品秒杀令牌校验失败");
        }

//        // 校验该用户是否已秒杀过此商品 seckill:user:sku:场次id_goodsid:用户id
//        String key = SeckillConstant.SECKILL_USER_ALREADY_EXISTS_PREFIX + parameterDTO.getSeckillId() + "_" + goodsId + ":" + parameterDTO.getMemberId();
//        String val = parameterDTO.getCount().toString();
//        // 活动结束自动释放锁
//        long expire = Duration.between(LocalDateTime.now(),redisBO.getEndTime()).toMillis();
//        // res为false,用户秒杀过此商品，否则没有秒杀过，保存此用户到redis中，此场活动不能再秒杀
//        Boolean res = stringRedisTemplate.opsForValue().setIfAbsent(key, val, expire, TimeUnit.MILLISECONDS);
//        if (!res) {
//            throw new Exception("秒杀失败，您已秒杀过此商品");
//        }
//        // 秒杀
//        RSemaphore semaphore = redissonClient.getSemaphore(SeckillConstant.SEMAPHORE_SECKILL_GOODS_STOCK_PREFIX + parameterDTO.getSeckillToken());
//        // 5ms内扣减库存
//        try {
//            //尝试获取锁，5MILLISECONDS未获取到则返回false
//            boolean acquire = semaphore.tryAcquire(parameterDTO.getCount(), 5, TimeUnit.MILLISECONDS);
//            // 获取到锁，秒杀成功，快速创建订单
//            if(acquire){
//                System.out.println("秒杀成功！即将为您创建订单");
//                return createSeckillOrder(parameterDTO.getMemberId(), redisBO, parameterDTO.getCount());
//            }else{
//                throw new Exception("秒杀失败！当前抢购人数过多");
//            }
//        } catch (InterruptedException e) {
//            log.error("秒杀出错：{}", e);
//            throw new Exception("秒杀失败");
//        }
        String lockKey = SeckillConstant.LOCK_ORDER_KEY_PREFIX + parameterDTO.getMemberId();
        RLock lock = redissonClient.getLock(lockKey);
        boolean success = lock.tryLock(10, TimeUnit.SECONDS);
        if(!success) {
            throw new Exception("秒杀失败，获取锁超时");
        }
        try {
            // 校验数量是否合法
            Integer inventory = Integer.valueOf(stringRedisTemplate.opsForValue().get(inventoryKey));
            log.info("redis库存量："+inventory);
            //扣减redis库存
            Long res = stringRedisTemplate.opsForValue().decrement(inventoryKey, parameterDTO.getCount());
            if(res >= 0) {
                return createSeckillOrder(parameterDTO.getMemberId(), redisBO, parameterDTO.getCount());
            }
            else {
                throw new Exception("秒杀失败，购买数量超出数量限制");
            }
        } finally {
            if(lock.isLocked() && lock.isHeldByCurrentThread()){ // 是否还是锁定状态
                lock.unlock(); // 释放锁
            }
        }
    }

    /**
     * 快速创建秒杀订单
     * @param redisBO
     * @param count
     * @return
     */
    private SeckillOrderDTO createSeckillOrder(Long memberId,SeckillGoodsRedisBO redisBO, Integer count) {
        SeckillOrderDTO orderDTO = new SeckillOrderDTO();
        orderDTO.setId(Long.valueOf(orderMapper.selectCount(new QueryWrapper<>())+1));
        orderDTO.setMemberId(memberId);
        orderDTO.setGoodsId(redisBO.getGoodsId());
        orderDTO.setGoodsNumber(count);
        orderDTO.setConsumption(count*redisBO.getSeckillPrice());
        orderDTO.setCreateAt(LocalDateTime.now());
        // 发送消息到订单处理mq，订单服务后台处理
        try {
            rabbitTemplate.convertSendAndReceive(OrderConstant.ORDER_EVENT_EXCHANGE, OrderConstant.ORDER_SECKILL_DEAL_QUEUE_ROUTING_KEY, orderDTO);
        } catch (AmqpException e) {
            rabbitTemplate.convertSendAndReceive(OrderConstant.ORDER_EVENT_EXCHANGE, OrderConstant.ORDER_SECKILL_DEAL_QUEUE_ROUTING_KEY, orderDTO);
            log.warn("秒杀后发送下单消息到order交换机失败");
        }
        return orderDTO;
    }

    /**
     * 保存秒杀活动库存信息
     * key:actId+goodsId  
     * value:inventory
     */
    private void saveSeckillAct(List<SeckillActDTO> actList) {
        for (SeckillActDTO seckillActDTO : actList) {
            LocalDateTime endTime = seckillActDTO.getEndTime();
            // seckillactid为key
            String key1 = SeckillConstant.REDIS_SECKILL_ACT_PREFIX + seckillActDTO.getId();
            String key2 = SeckillConstant.REDIS_SECKILL_ACT_PREFIX + seckillActDTO.getId() + "_" + seckillActDTO.getGoodsId();
            // 避免重复上架
            if (stringRedisTemplate.hasKey(key1) || stringRedisTemplate.hasKey(key2)) {
                continue;
            }
            stringRedisTemplate.opsForValue().set(key1,seckillActDTO.getGoodsId().toString());
            // 值为当前场次下goodsid，有的sku在多个场次中都有，所以保存成 场次id_goodsid
            String inventory = goodsMapper.selectGoodsInventoryAndLock(seckillActDTO.getGoodsId()).toString();
            stringRedisTemplate.opsForValue().set(key2,inventory);
            // 设置过期时间，活动结束自动过期,ms
            stringRedisTemplate.expireAt(key1,Date.from(endTime.atZone(ZoneId.systemDefault()).toInstant()));
            stringRedisTemplate.expireAt(key2,Date.from(endTime.atZone(ZoneId.systemDefault()).toInstant()));
        }
    }

    /**
     * 提前锁定库存。
     * 保存每个秒杀活动中商品详细信息以及库存(信号量形式)
     *
     * hash结构 ：seckill:goods: 场次id_goodsid->goodsinfo
     *
     * 库存以信号量形式保存
     */
    private void saveSeckillGoods(List<SeckillActDTO> actDTOList) throws InterruptedException {
        BoundHashOperations<String, String, String> ops = stringRedisTemplate.boundHashOps(SeckillConstant.REDIS_SECKILL_GOODS_INFO_KEY);
        for (SeckillActDTO actDTO : actDTOList) {
            String key = actDTO.getId() + "_" + actDTO.getGoodsId();;
            // 当前商品信息已上架
            if (ops.hasKey(key)) {
                continue;
            }
            // 构建seckillGoodsredisBO
            Goods goods = goodsMapper.selectById(actDTO.getGoodsId());
            SeckillGoodsRedisBO redisBO = new SeckillGoodsRedisBO();
            redisBO.setId(actDTO.getId());
            redisBO.setGoodsId(actDTO.getGoodsId());
//            redisBO.setSeckillCount(goods.getInventory());
            redisBO.setSeckillPrice(goods.getPrice());
            // 保存开始结束时间
            redisBO.setBeginTime(actDTO.getBeginTime());
            redisBO.setEndTime(actDTO.getEndTime());
            // 为当前商品生成唯一令牌,持有正确令牌的人才能进行秒杀
            String token = UUID.randomUUID().toString();
            redisBO.setSeckillToken(token);
            // 上架当前商品，保存到redis
            ops.put(key, JSON.toJSONString(redisBO));
//            // 库存信号量key
//            String semaphoreKey = SeckillConstant.SEMAPHORE_SECKILL_GOODS_STOCK_PREFIX + token;
//            RSemaphore semaphore = redissonClient.getSemaphore(semaphoreKey);
//            // 设置秒杀库存量为信号量的值，即将信号量同时能够允许获取锁的客户端的数量设置为库存量
//            semaphore.trySetPermits(goods.getInventory());
//            //设置库存信号量过期时间
//            Long expire = Duration.between(LocalDateTime.now(),actDTO.getEndTime()).toMillis();
//            semaphore.expire(expire, TimeUnit.MILLISECONDS);
        }

    }

    @Override
    public int insert(SeckillAct entity) {
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
    public int delete(Wrapper<SeckillAct> queryWrapper) {
        return 0;
    }

    @Override
    public int deleteBatchIds(Collection<? extends Serializable> idList) {
        return 0;
    }

    @Override
    public int updateById(SeckillAct entity) {
        return 0;
    }

    @Override
    public int update(SeckillAct entity, Wrapper<SeckillAct> updateWrapper) {
        return 0;
    }

    @Override
    public SeckillAct selectById(Serializable id) {
        return null;
    }

    @Override
    public List<SeckillAct> selectBatchIds(Collection<? extends Serializable> idList) {
        return null;
    }

    @Override
    public List<SeckillAct> selectByMap(Map<String, Object> columnMap) {
        return null;
    }

    @Override
    public SeckillAct selectOne(Wrapper<SeckillAct> queryWrapper) {
        return null;
    }

    @Override
    public Integer selectCount(Wrapper<SeckillAct> queryWrapper) {
        return null;
    }

    @Override
    public List<SeckillAct> selectList(Wrapper<SeckillAct> queryWrapper) {
        return null;
    }

    @Override
    public List<Map<String, Object>> selectMaps(Wrapper<SeckillAct> queryWrapper) {
        return null;
    }

    @Override
    public List<Object> selectObjs(Wrapper<SeckillAct> queryWrapper) {
        return null;
    }

    @Override
    public <E extends IPage<SeckillAct>> E selectPage(E page, Wrapper<SeckillAct> queryWrapper) {
        return null;
    }

    @Override
    public <E extends IPage<Map<String, Object>>> E selectMapsPage(E page, Wrapper<SeckillAct> queryWrapper) {
        return null;
    }
}
