package org.toolkit.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.toolkit.easyexcel.read.context.LocalReadContextStrategy;
import org.toolkit.easyexcel.read.context.ReadContextHolder;
import org.toolkit.easyexcel.read.context.RedisReadContextStrategy;

@Component
@EnableConfigurationProperties(ExcelConfigProperties.class)
public class InitListener  implements ApplicationListener {

    private ExcelConfigProperties properties;

    public InitListener(ExcelConfigProperties properties) {
        this.properties = properties;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {

        if (properties.isRemote()) {
            ReadContextHolder.init(RedisReadContextStrategy.getInstance());
        } else {
            ReadContextHolder.init(LocalReadContextStrategy.getInstance());
        }
    }
}