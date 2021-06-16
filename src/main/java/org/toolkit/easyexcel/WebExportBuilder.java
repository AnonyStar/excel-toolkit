package org.toolkit.easyexcel;

import com.alibaba.excel.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.toolkit.core.IExportFunction;
import org.toolkit.exception.ExcelKitException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

/**
 * @author: zhoucx
 * @time: 2021/6/16 22:48
 */
public class WebExportBuilder extends AbstractExportBuilder{

    private static final Logger logger = LoggerFactory.getLogger(WebExportBuilder.class);

    private HttpServletResponse httpServletResponse;
    private OutputStream outputStream;
    private String fileName;
    private Class excelClass;
    private Integer pageSize;
    private Integer rowAccessWindowSize;
    private Integer recordCountPerSheet;
    private Boolean openAutoColumWidth;

    private WebExportBuilder(){}

    public WebExportBuilder(HttpServletResponse httpServletResponse, String fileName, Class excelClass) {
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
}
