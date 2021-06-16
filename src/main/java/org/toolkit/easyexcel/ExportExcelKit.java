package org.toolkit.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.util.StringUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.toolkit.core.IExportFunction;
import org.toolkit.exception.ExcelKitException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Objects;

public class ExportExcelKit extends EasyExcel {

    private static final Logger logger = LoggerFactory.getLogger(ExportExcelKit.class);

    private HttpServletResponse httpServletResponse;
    private OutputStream outputStream;
    private String fileName;
    private Class excelClass;
    private Integer pageSize;
    private Integer rowAccessWindowSize;
    private Integer recordCountPerSheet;
    private Boolean openAutoColumWidth;

    public ExportExcelKit(HttpServletResponse httpServletResponse, String fileName, Class excelClass) {
        this.httpServletResponse = httpServletResponse;
        this.fileName = fileName;
        this.excelClass = excelClass;
    }

    public ExportExcelKit() {
        super();
    }

    public ExportExcelKit(OutputStream outputStream, String fileName, Class excelClass) {
        this.outputStream = outputStream;
        this.fileName = fileName;
        this.excelClass = excelClass;
    }


    /**
     * web导出.
     * <p>
     * 可分页获取数据进行导出.
     * </p>
     *
     * @param params
     * @param exportFunction
     * @param <ParamType>
     * @param <ExceleClass>
     */
    public <ParamType, ExceleClass> void exportResponse(ParamType params,
                                                        IExportFunction<ParamType, ExceleClass> exportFunction) throws IOException {
        if (Objects.isNull(httpServletResponse) || StringUtils.isEmpty(fileName)) {
            throw new ExcelKitException("httpServletResponse 或 fileName 参数为空");
        }

        httpServletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        httpServletResponse.setHeader("Content-disposition",
                String.format("attachment; filename=%s", fileName));
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        doProcess(params, exportFunction, outputStream);
    }


    /**
     * 按输出流导出excel.
     * @param params
     * @param exportFunction
     * @param <ParamType>
     * @param <ExceleClass>
     */
    public <ParamType, ExceleClass> void exportStram(ParamType params,
                                                     IExportFunction<ParamType, ExceleClass> exportFunction) {
        if (Objects.isNull(outputStream)) {
            throw new ExcelKitException("outputStream 参数为空");
        }
        doProcess(params,exportFunction,outputStream);
    }



    private <ParamType, ExceleClass> void doProcess(ParamType params, IExportFunction<ParamType, ExceleClass> exportFunction, OutputStream outputStream) {
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
