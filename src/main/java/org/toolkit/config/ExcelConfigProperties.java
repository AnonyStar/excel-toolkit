package org.toolkit.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: zhoucx
 * @time: 2021-07-02
 */
@ConfigurationProperties(prefix = "excel.config")
public class ExcelConfigProperties {


    private boolean remote = false;

    public boolean isRemote() {
        return remote;
    }

    public void setRemote(boolean remote) {
        this.remote = remote;
    }
}
