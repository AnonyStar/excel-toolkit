package org.toolkit.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.toolkit.core.IExportFunction;

import java.io.OutputStream;
import java.util.List;

/**
 * @author: zhoucx
 * @time: 2021/6/16 22:48
 */
public abstract class AbstractExportBuilder {
    private static final Logger logger = LoggerFactory.getLogger(AbstractExportBuilder.class);

    protected Class excelClass;
    protected Integer pageSize;


    protected  <ParamType, ExceleClass> void doProcess(ParamType params, IExportFunction<ParamType, ExceleClass> exportFunction, OutputStream outputStream) {
        ExcelWriter excelWriter = null;
        try {
            excelWriter = EasyExcel.write(outputStream, excelClass).build();
            WriteSheet writeSheet = EasyExcel.writerSheet().build();
            int pageNo = 1;
            while (true) {
                List<ExceleClass> dataList = exportFunction.pageQuery(params, pageNo, pageSize);
                if (null == dataList || dataList.isEmpty()) {
                    logger.warn("查询结果集为空，结束查询！");
                    break;
                }
                excelWriter.write(dataList, writeSheet);

                if (dataList.size() < pageSize) {
                    logger.warn("查询结果集小于 pageSize ，结束查询!");
                    break;
                }
                pageNo++;
            }
        } finally {
            logger.info("关闭excel流资源");
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }
}
