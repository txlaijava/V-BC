package com.shopping.starter.mq;

/**
 * RocketMQ的自定义异常
 */
public class MQException extends RuntimeException {
    public MQException(String msg) {
        super(msg);
    }
}
