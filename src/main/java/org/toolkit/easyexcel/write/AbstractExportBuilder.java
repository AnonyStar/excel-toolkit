package org.toolkit.easyexcel.write;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.util.StringUtils;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.toolkit.core.IExportFunction;
import org.toolkit.exception.ExcelKitException;

import java.io.OutputStream;
import java.util.List;
import java.util.Objects;

/**
 * @author: zhoucx
 * @time: 2021/6/16 22:48
 */
public abstract class AbstractExportBuilder {
    private static final Logger logger = LoggerFactory.getLogger(AbstractExportBuilder.class);
    /**
     * 默认分页条数
     */
    private static final int DEFAULT_PAGE_SIZE = 3000;

    protected ExcelWriterBuilder writerBuilder;

    protected Class excelClass;
    protected Integer pageSize;
    protected OutputStream outputStream;
    private String sheetName;
    private Integer sheetIndex;
    private ExcelWriter excelWriter;


    public AbstractExportBuilder registerWriteHandler(WriteHandler writeHandler) {
        writerBuilder.registerWriteHandler(writeHandler);
        return this;
    }

    public AbstractExportBuilder sheetIndex(Integer sheetIndex){
        this.sheetIndex = sheetIndex;
        return this;
    }
    public AbstractExportBuilder sheetName(String sheetName){
        this.sheetName = sheetName;
        return this;
    }
    public AbstractExportBuilder excelClass(Class excelClass){
        this.excelClass = excelClass;
        return this;
    }
    public AbstractExportBuilder pageSize(Integer pageSize){
        if (Objects.isNull(pageSize) || pageSize < 1) {
            this.pageSize = DEFAULT_PAGE_SIZE;
        }
        this.pageSize = pageSize;
        return this;
    }

    public AbstractExportBuilder() {
        this.pageSize = DEFAULT_PAGE_SIZE;
        this.sheetIndex = 0;
        this.writerBuilder = new ExcelWriterBuilder();
    }

    /**
     * 单 sheet 导出
     * @param params
     * @param exportFunction
     * @param <ParamType>
     * @param <ExceleClass>
     */
    public abstract  <ParamType, ExceleClass> void export(ParamType params,
                                                IExportFunction<ParamType, ExceleClass> exportFunction);


    /**
     * 多 sheet 导出.
     * @param params
     * @param exportFunction
     * @param <ParamType>
     * @param <ExceleClass>
     * @return
     */
    public abstract  <ParamType, ExceleClass> AbstractExportBuilder exportMultiSheet(ParamType params,
                                                                      IExportFunction<ParamType, ExceleClass> exportFunction);


    protected  <ParamType, ExceleClass> void doProcess(ParamType params, IExportFunction<ParamType, ExceleClass> exportFunction, boolean isFinish) {
        if (Objects.isNull(excelClass)) {
            throw new ExcelKitException("excelClass 参数为空");
        }
        if (Objects.isNull(outputStream)) {
            throw new ExcelKitException("outputStream 参数为空");
        }
        try {

            excelWriter = Objects.isNull(excelWriter) ?  writerBuilder.file(outputStream).head(excelClass).build() : excelWriter;

            WriteSheet writeSheet = EasyExcel.writerSheet(sheetIndex,StringUtils.isEmpty(sheetName) ? null : sheetName).build();
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
            if (excelWriter != null && isFinish) {
                excelWriter.finish();
            }
        }
    }
}
