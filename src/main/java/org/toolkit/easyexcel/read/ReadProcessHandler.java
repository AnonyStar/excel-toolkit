package org.toolkit.easyexcel.read;

import com.alibaba.excel.context.AnalysisContext;

/**
 * @author: zhoucx
 * @time: 2021-06-18
 */
@FunctionalInterface
public interface ReadProcessHandler<T> {

    void doprocess(T t, AnalysisContext analysisContext);
}
