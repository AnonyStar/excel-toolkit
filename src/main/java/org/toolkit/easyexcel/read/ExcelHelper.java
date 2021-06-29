package org.toolkit.easyexcel.read;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.util.StringUtils;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.toolkit.core.annotation.ExcelFirstMainly;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class ExcelHelper {

    private static final Logger logger = LoggerFactory.getLogger(ExcelHelper.class);

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();


    public static <T> RowReadStatus validate(T obj) throws NoSuchFieldException, SecurityException {
        RowReadStatus readStatus = new RowReadStatus();
        readStatus.setStatus(true);
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
           readStatus.setMessage(JSON.toJSONString(resultMap));
           readStatus.setStatus(false);
           return readStatus;
       }
        return readStatus;
    }


    public static <T> void firstMainlyHandler(T data) {
        //获取字段
        Field[] declaredFields = data.getClass().getDeclaredFields();
        Map<String, Field> fieldMap = Arrays.stream(declaredFields).collect(Collectors.toMap(Field::getName, a -> a, (k1, k2) -> k1));
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(ExcelFirstMainly.class)) {
                field.setAccessible(true);
                ExcelFirstMainly firstMainly = field.getAnnotation(ExcelFirstMainly.class);
                String key = firstMainly.key();
                Field tempField = fieldMap.get(key);
                if (Objects.nonNull(tempField)) {
                    try {
                        tempField.setAccessible(true);
                        ExcelCacheMap.processField(tempField.get(data), field, data);
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.debug("处理已首次出现数据为主异常，异常数据：{}, 异常：{}", data, e);
                    }

                }
            }
        }
    }
    public static void removeFirstMainlyCache() {
        ExcelCacheMap.remove();
    }

    private static class ExcelCacheMap {
        private static ThreadLocal<Map<Object,Map<String,Object>>> cacheMap = new ThreadLocal<>();


        public static void init() {
            if (cacheMap.get() == null) {
                cacheMap.set(new HashMap<>());
            }
        }

        public static <T> void processField(Object key, Field targetField, T data) throws IllegalAccessException {
            init();
            Map<String, Object> mubao  = cacheMap.get().get(key);
            if (Objects.isNull(mubao) || mubao.isEmpty()) {
                //空直接存放
                Map<String,Object> valueMap = new HashMap<>();
                valueMap.put(targetField.getName(), targetField.get(data));
                cacheMap.get().put(key,valueMap);
            } else {
                Object o = mubao.get(targetField.getName());
                if (Objects.isNull(o)) {
                    mubao.put(targetField.getName(), targetField.get(data));
                } else {
                    targetField.set(data, o);
                }
            }
        }


        public static void remove() {
            cacheMap.remove();
        }
    }


}