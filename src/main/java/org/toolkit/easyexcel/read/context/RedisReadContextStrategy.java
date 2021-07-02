package org.toolkit.easyexcel.read.context;

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


    public RedisReadContextStrategy(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    public static IReadContextStrategy getInstance() {

        //创建配置
        Config config = new Config();
        //指定使用单节点部署方式
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        //创建客户端(发现创建RedissonClient非常耗时，基本在2秒-4秒左右)
        RedissonClient redisson = Redisson.create(config);
        return new RedisReadContextStrategy(redisson);

    }

    @Override
    public void setReadContext(String key, ReadContext value) {
        RBucket<Object> bucket = redissonClient.getBucket(key);
        bucket.set(SerializeUtil.serialize(value));
    }

    @Override
    public ReadContext getReadContext(String key) {
        RBucket<Object> bucket = redissonClient.getBucket(key);
        ReadContext deserialize = (ReadContext) SerializeUtil.unserialize((byte[]) bucket.get());
        return deserialize;
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
