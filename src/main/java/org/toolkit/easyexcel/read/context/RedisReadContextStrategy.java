package org.toolkit.easyexcel.read.context;

import org.redisson.api.RedissonClient;

/**
 * @author: zhoucx
 * @time: 2021-06-29
 */
public class RedisReadContextStrategy implements IReadContextStrategy{


    private final RedissonClient redissonClient;


    public RedisReadContextStrategy(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public void setReadContext(String key, ReadContext value) {

    }

    @Override
    public ReadContext getReadContext(String key) {
        return null;
    }

    @Override
    public void removeReadContext(String key) {

    }
}
