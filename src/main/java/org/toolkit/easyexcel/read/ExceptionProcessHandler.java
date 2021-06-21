package org.toolkit.easyexcel.read;

import com.alibaba.excel.event.AnalysisEventListener;
import org.apache.poi.ss.formula.functions.T;

/**
 * 异常处理
 * @author: zhoucx
 * @time: 2021-06-19
 */
public  interface ExceptionProcessHandler<T> {


    void handlerException(T data);
}
