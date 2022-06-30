package com.practice.seckill.api.constant;

public class SeckillConstant {

    //    public static final String UPLOAD_SKU_LOCK_KEY = "upload_seckill_goods";
    public static final String REDIS_SECKILL_ACT_PREFIX = "seckill:act:";
    public static final String REDIS_SECKILL_GOODS_INFO_KEY = "seckill:goods:";
    // redisson信号量形式保存库存
    public static final String SEMAPHORE_SECKILL_GOODS_STOCK_PREFIX = "seckill:goods:stock:";
    // 用户是否已秒杀此商品
    public static final String SECKILL_USER_ALREADY_EXISTS_PREFIX = "seckill:user:goods:";
    public static final String LOCK_ORDER_KEY_PREFIX = "lock:order:";
}