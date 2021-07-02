package org.toolkit.easyexcel.read.context;

import java.util.Map;
import java.util.UUID;

/**
 * @author: zhoucx
 * @time: 2021-06-18
 */
public class ReadContextHolder {

    private ReadContextHolder() {
    }
    private static IReadContextStrategy readContextStrategy;

    public static ReadContext get(String key) {
        return readContextStrategy.getReadContext(key);
    }

    private static void set(String key, ReadContext context) {
        context.setContexttKey(key);
        readContextStrategy.setReadContext(key, context);
    }

    public static void removeReadContext(String key) {
        readContextStrategy.removeReadContext(key);
    }

    public static void refreshReadContext(ReadContext value) {
        readContextStrategy.refreshReadContext(value);
    }

    public static String initContext(ReadContext readContext) {
        String uid = UUID.randomUUID().toString();
        set(uid, readContext);
        return uid;
    }

    public static void init(IReadContextStrategy instance) {
        readContextStrategy = instance;
    }
}
