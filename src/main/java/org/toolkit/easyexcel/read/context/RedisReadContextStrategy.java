package org.toolkit.easyexcel.read.context;

import com.alibaba.fastjson.JSON;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.toolkit.easyexcel.read.serializer.ProtostuffUtils;
import org.toolkit.easyexcel.read.serializer.SerializeUtil;

/**
 * @author: zhoucx
 * @time: 2021-06-29
 */
public class RedisReadContextStrategy implements IReadContextStrategy {


    private final RedissonClient redissonClient;


    private RedisReadContextStrategy(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    public static IReadContextStrategy getInstance(RedissonClient redissonClient) {
        return new RedisReadContextStrategy(redissonClient);

    }

    @Override
    public void setReadContext(String key, ReadContext value) {
        RBucket<Object> bucket = redissonClient.getBucket(key);
        bucket.set(SerializeUtil.serialize(value));
       // bucket.set(value);
    }

    @Override
    public ReadContext getReadContext(String key) {
        RBucket<Object> bucket = redissonClient.getBucket(key);
        //ReadContext readContext = (ReadContext) bucket.get();
       ReadContext readContext = (ReadContext) SerializeUtil.unserialize((byte[]) bucket.get());
        return readContext;
    }

    @Override
    public void removeReadContext(String key) {
        RBucket<Object> bucket = redissonClient.getBucket(key);
        boolean delete = bucket.delete();
    }

    @Override
    public void refreshReadContext(ReadContext value) {
        setReadContext(value.getContexttKey(), value);
    }
}
