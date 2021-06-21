package org.toolkit.easyexcel.write;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

/**
 * @author: zhoucx
 * @time: 2021-06-21
 */
public class Demo2EventListener<T> extends AnalysisEventListener<T> {
    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        System.out.println("Demo2EventListener 监听到的数据" + t);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
