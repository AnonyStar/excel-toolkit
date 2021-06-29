package org.toolkit.core.annotation;

import java.lang.annotation.*;

/**
 * @author: zhoucx
 * @time: 2021-06-29
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ExcelFirstMainly {

    /**
     * 标记以那个属性为主.
     * @return
     */
    String key();
}
