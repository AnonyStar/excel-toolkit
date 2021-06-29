package org.toolkit.easyexcel.read.context;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地上下文管理策略.
 * @author: zhoucx
 * @time: 2021-06-18
 */
public class LocalReadContextStrategy implements IReadContextStrategy {

    private static final Map<String, ReadContext> READ_CONTEXT = new ConcurrentHashMap<>();

    private LocalReadContextStrategy() {}

    @Override
    public void setReadContext(String key, ReadContext value) {
        READ_CONTEXT.put(key, value);
    }

    @Override
    public ReadContext getReadContext(String key) {
        return READ_CONTEXT.get(key);
    }

    @Override
    public void removeReadContext(String key) {
        ReadContext context = READ_CONTEXT.remove(key);
        if (Objects.nonNull(context)) {
            // todo 如果上下文存在则与要同步删除原始文件.
            context.getFileSystem().removeSourcess();
        }
    }



    public static LocalReadContextStrategy getInstance() {
        return Single.getInstance();
    }


    private static class Single{
        private static LocalReadContextStrategy localReadContextStrategy = new LocalReadContextStrategy();

        public static LocalReadContextStrategy getInstance() {
            return localReadContextStrategy;
        }
    }
}
