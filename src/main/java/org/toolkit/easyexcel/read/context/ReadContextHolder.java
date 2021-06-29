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
        readContextStrategy.setReadContext(key, context);
    }

    public static void removeReadContext(String key) {
        readContextStrategy.removeReadContext(key);
    }

    public static String init(ReadContext readContext, boolean remoteContext) {
        String uid = UUID.randomUUID().toString();
        if (remoteContext) {

        }else {
            readContextStrategy = LocalReadContextStrategy.getInstance();
        }
        set(uid, readContext);
        return uid;
    }
}
