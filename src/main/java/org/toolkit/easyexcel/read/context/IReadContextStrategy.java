package org.toolkit.easyexcel.read.context;

/**
 * 读取上下文管理.
 *
 * @author: zhoucx
 * @time: 2021-06-18
 */
public interface IReadContextStrategy {


    /**
     * 设置读取上下文
     * @param key
     * @param value
     */
    void  setReadContext(String key, ReadContext value);

    /**
     * 获取读取上下文.
     * @param key
     * @return
     */
    ReadContext getReadContext(String key);

    /**
     * 删除上下文.
     */
    void removeReadContext(String key);


}
