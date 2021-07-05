package org.toolkit.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.toolkit.easyexcel.read.context.IReadContextStrategy;
import org.toolkit.easyexcel.read.context.LocalReadContextStrategy;
import org.toolkit.easyexcel.read.context.ReadContextHolder;
import org.toolkit.easyexcel.read.context.RedisReadContextStrategy;

import java.util.Arrays;

@Configuration
@ConditionalOnProperty(prefix = "excel.config", value = "enabled", havingValue = "true", matchIfMissing = false)
@EnableConfigurationProperties(ExcelConfigProperties.class)
public class ExcelAutoConfiguration implements ApplicationListener {

    private ExcelConfigProperties properties;

    public ExcelAutoConfiguration(ExcelConfigProperties properties) {
        this.properties = properties;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {

        if (properties.isRemote()) {
            ReadContextHolder.init(redisReadContextStrategy());
        } else {
            ReadContextHolder.init(localReadContextStrategy());
        }
    }


    @Bean
    public IReadContextStrategy localReadContextStrategy() {
        return LocalReadContextStrategy.getInstance();
    }

    @Bean
    public IReadContextStrategy redisReadContextStrategy() {
        return RedisReadContextStrategy.getInstance(redissonClient());
    }

    @Bean
    @ConditionalOnMissingBean(RedissonClient.class)
    public RedissonClient redissonClient() {
        ExcelConfigProperties.RedissonProperties redissonProperties = properties.getRedisson();
        String password = redissonProperties.getPassword();
        String[] nodes = StringUtils.tokenizeToStringArray(
                redissonProperties
                        .getNodes(), ",;");
        //创建配置
        Config configuration = new Config();
        //指定使用单节点部署方式
        //configuration.useSingleServer().setAddress("redis://127.0.0.1:6379");
        //configuration.setCodec(new JsonJacksonCodec());

        if (redissonProperties.getNodeType() == ExcelConfigProperties.RedissonNodeType.Single) {
            final SingleServerConfig singleServerConfig = configuration
                    .useSingleServer()
                    .setAddress(nodes[0]);
            if (StringUtils.hasLength(password)) {
                singleServerConfig.setPassword(password);
            }
        } else if (redissonProperties.getNodeType() == ExcelConfigProperties.RedissonNodeType.Cluster) {
            ClusterServersConfig clusterServersConfig =
                    configuration
                            .useClusterServers()
                            .addNodeAddress(nodes)
                            .setScanInterval(2000);
            if (StringUtils.hasLength(password)) {
                clusterServersConfig.setPassword(password);
            }
        } else if (redissonProperties.getNodeType() == ExcelConfigProperties.RedissonNodeType.Sentinel) {
            SentinelServersConfig sentinelServersConfig = configuration
                    .useSentinelServers()
                    .setCheckSentinelsList(false)
                    .setMasterName(redissonProperties.getMasterName());
            sentinelServersConfig.setSentinelAddresses(Arrays.asList(nodes));
            if (StringUtils.hasLength(password)) {
                sentinelServersConfig.setPassword(password);
            }
        }
        //创建客户端(发现创建RedissonClient非常耗时，基本在2秒-4秒左右)
        RedissonClient redisson = Redisson.create(configuration);
        return redisson;
    }
}