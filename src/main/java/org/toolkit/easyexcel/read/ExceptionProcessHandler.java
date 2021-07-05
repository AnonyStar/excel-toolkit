package org.toolkit.easyexcel.read;

/**
 * 异常处理
 * @author: zhoucx
 * @time: 2021-06-19
 */
public  interface ExceptionProcessHandler<T> {


    void handlerException(T data);
}
