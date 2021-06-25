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

    public static ReadContext get(String key) {
        return LocalReadContextStrategy.getInstance().getReadContext(key);
    }

    public static void set(String key, ReadContext context) {
        LocalReadContextStrategy.getInstance().setReadContext(key, context);
    }

    public static void removeReadContext(String key) {
        LocalReadContextStrategy.getInstance().removeReadContext(key);
    }

    public static String set(ReadContext readContext) {
        String uid = UUID.randomUUID().toString();
        set(uid, readContext);
        return uid;
    }
}
