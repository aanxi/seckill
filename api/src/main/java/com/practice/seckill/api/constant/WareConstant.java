package com.practice.seckill.api.constant;

public class WareConstant {

    // 消息队列，交换机和队列
    public static final String STOCK_EVENT_EXCHANGE = "stock-event-exchange";
    public static final String STOCK_DELAY_QUEUE = "stock.delay.queue";
    public static final String STOCK_RELEASE_QUEUE = "stock.release.queue";
    public static final String STOCK_RELEASE_ROUTING_KEY = "stock.release.#";
    public static final String STOCK_LOCKED_ROUTING_KEY = "stock.locked.#";
    public static final String DEAD_LETTER_EXCHANGE = "stock-event-exchange";
    public static final String DEAD_LETTER_ROUTING_KEY = "stock.release";
    public static final Integer DEAD_LETTER_TTL = 3 * 60 * 1000; // 单位是ms

    /**
     * 商品库存锁定状态
     */
    public enum StockLockStatus {
        LOCKED(1, "已锁定"),
        RELEASED(2, "已释放"),
        DEDUCTED(3, "已扣减");

        private int value;
        private String desc;

        StockLockStatus(int value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public int getValue() {
            return value;
        }

        public String getDesc() {
            return desc;
        }
    }
}
