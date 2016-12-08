package com.baidu.unbiz.redis.exception;

/**
 * redis操作异常
 *
 * @author zhangxu04
 */
public class RedisOperationException extends RuntimeException {

    private static final long serialVersionUID = 3222573813262320183L;

    public RedisOperationException() {
    }

    public RedisOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public RedisOperationException(String message) {
        super(message);
    }

    public RedisOperationException(Throwable cause) {
        super(cause);
    }

}
