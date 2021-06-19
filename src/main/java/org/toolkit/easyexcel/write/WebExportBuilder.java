package org.toolkit.easyexcel.write;

import com.alibaba.excel.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.toolkit.core.IExportFunction;
import org.toolkit.exception.ExcelKitException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author: zhoucx
 * @time: 2021/6/16 22:48
 */
public class WebExportBuilder extends AbstractExportBuilder {

    private static final Logger logger = LoggerFactory.getLogger(WebExportBuilder.class);

    private HttpServletResponse httpServletResponse;
    private String fileName;

    protected WebExportBuilder() {
    }

    protected WebExportBuilder(HttpServletResponse httpServletResponse, String fileName, Class excelClass) {
        this.httpServletResponse = httpServletResponse;
        this.fileName = fileName;
        this.excelClass = excelClass;
    }

    public static WebExportBuilder builder(HttpServletResponse httpServletResponse, String fileName, Class excelClass) {
        return new WebExportBuilder(httpServletResponse, fileName, excelClass);
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
    @Override
    public <ParamType, ExceleClass> void export(ParamType params,
                                                IExportFunction<ParamType, ExceleClass> exportFunction) {
        settingResponse();
        doProcess(params, exportFunction, true);
    }


    @Override
    public <ParamType, ExceleClass> WebExportBuilder exportMultiSheet(ParamType params,
                                                                      IExportFunction<ParamType, ExceleClass> exportFunction)  {
        settingResponse();
        doProcess(params, exportFunction, false);
        return this;
    }

    private void settingResponse() {
        if (Objects.isNull(httpServletResponse) || StringUtils.isEmpty(fileName)) {
            throw new ExcelKitException("httpServletResponse 或 fileName 参数为空");
        }

        httpServletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        httpServletResponse.setHeader("Content-disposition",
                String.format("attachment; filename=%s", fileName));
        try {
            this.outputStream = httpServletResponse.getOutputStream();
        } catch (IOException e) {
            logger.error("获取 outputStream 失败： {}", e);
            throw new ExcelKitException("获取 outputStream 对象失败！");
        }
    }
}
