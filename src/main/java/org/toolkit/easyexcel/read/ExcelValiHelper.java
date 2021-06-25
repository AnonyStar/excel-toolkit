package org.toolkit.easyexcel.read;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.util.StringUtils;
import com.alibaba.fastjson.JSON;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ExcelValiHelper {

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static <T> RowReadStatus validate(T obj) throws NoSuchFieldException, SecurityException {
        Map<String,String> resultMap = new HashMap<>();
        Set<ConstraintViolation<T>> set = validator.validate(obj, Default.class);
        if (set != null && set.size() != 0) {
            int i =0;

            for (ConstraintViolation<T> cv : set) {
                Field declaredField = obj.getClass().getDeclaredField(cv.getPropertyPath().toString());
                ExcelProperty annotation = declaredField.getAnnotation(ExcelProperty.class);
                // TODO 增加自定义注解
                //拼接错误信息，包含当前出错数据的标题名字+错误信息
                String[] strings = annotation.value();
                String string = strings[annotation.value().length - 1];
                if (StringUtils.isEmpty(string)){
                    string = declaredField.getName();
                }
                resultMap.put(string,cv.getMessage());
                i++;
            }
        }
       if (!resultMap.isEmpty()){
           RowReadStatus readStatus = new RowReadStatus();
           readStatus.setMessage(JSON.toJSONString(resultMap));
           readStatus.setStatus(false);
           return readStatus;
       }
        return null;
    }
}