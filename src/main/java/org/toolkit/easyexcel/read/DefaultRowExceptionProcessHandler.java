package org.toolkit.easyexcel.read;

import com.alibaba.excel.EasyExcel;

import java.io.OutputStream;
import java.util.Arrays;

/**
 * 每行异常处理异常处理.
 * @author: zhoucx
 * @time: 2021-06-19
 */
public class DefaultRowExceptionProcessHandler<T> implements ExceptionProcessHandler<T> {


    private OutputStream outputStream;

    public DefaultRowExceptionProcessHandler(OutputStream outputStream) {
        this.outputStream = outputStream;
    }


    @Override
    public void handlerException(T data) {
        EasyExcel.write(outputStream,data.getClass()).sheet().doWrite(Arrays.asList(data));
    }
}
