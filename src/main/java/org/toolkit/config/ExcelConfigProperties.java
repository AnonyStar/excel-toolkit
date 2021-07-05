package org.toolkit.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: zhoucx
 * @time: 2021-07-02
 */
@ConfigurationProperties(prefix = "excel.config")
public class ExcelConfigProperties {

    private boolean enabled = false;

    private boolean remote = false;

    private RedissonProperties redisson = new RedissonProperties();

    public RedissonProperties getRedisson() {
        return redisson;
    }

    public void setRedisson(RedissonProperties redisson) {
        this.redisson = redisson;
    }

    public boolean isRemote() {
        return remote;
    }

    public void setRemote(boolean remote) {
        this.remote = remote;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    enum RedissonNodeType {
        Single,
        Sentinel,
        Cluster
    }

    public static class RedissonProperties {

        public String getNodes() {
            return nodes;
        }

        public void setNodes(String nodes) {
            this.nodes = nodes;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public RedissonNodeType getNodeType() {
            return nodeType;
        }

        public void setNodeType(
                RedissonNodeType nodeType) {
            this.nodeType = nodeType;
        }

        public String getMasterName() {
            return masterName;
        }

        public void setMasterName(String masterName) {
            this.masterName = masterName;
        }

        private String masterName;
        private String nodes;
        private String password;
        private RedissonNodeType nodeType;
    }
}
