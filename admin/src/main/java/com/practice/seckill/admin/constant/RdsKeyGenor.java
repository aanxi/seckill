package com.practice.seckill.admin.constant;

import java.time.Duration;

public class RdsKeyGenor {

    public static final String GAB_ACCESS_TOKEN_PREFIX = "access_token:";

    public static String getAccessTokenKey(String token) {
        return GAB_ACCESS_TOKEN_PREFIX + token;
    }

    public static String rdsKey(Class<?> clazz, String... key) {
        return clazz.getName() + "." + String.join("_", key);
    }

    public static String rdsKey(String... key) {
        return String.join("_", key);
    }

    /**
     * 商品缓存
     * %s: goodsId
     */
    public static final String GOODS_KEY_FORM = "goods_%s:goodsBO";

    public static String goodsKey(Long goodsId) {
        return String.format(GOODS_KEY_FORM, goodsId);
    }

    public static Duration goodsExpire() {
        return Duration.ofDays(1);
    }

    /**
     * 会员缓存
     * %s: memberId
     */
    public static final String MEMBER_KEY_FORM = "member_%s:memberBO";

    public static String memberKey(Long memberId) {
        return String.format(MEMBER_KEY_FORM, memberId);
    }

    public static Duration memberExpire() {
        return Duration.ofDays(1);
    }
}
