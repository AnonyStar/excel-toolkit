package org.toolkit.core.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

/**
 * @author: zhoucx
 * @time: 2021-06-29
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ExcelPrimaryKey {

    String key();

}
