package org.toolkit.easyexcel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.toolkit.core.IExportFunction;
import org.toolkit.exception.ExcelKitException;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Objects;

/**
 * @author: zhoucx
 * @time: 2021/6/16 22:49
 */
public class StreamExportBuilder extends AbstractExportBuilder{

    private static final Logger logger = LoggerFactory.getLogger(StreamExportBuilder.class);

    private HttpServletResponse httpServletResponse;
    private OutputStream outputStream;
    private String fileName;
    private Class excelClass;
    private Integer pageSize;
    private Integer rowAccessWindowSize;
    private Integer recordCountPerSheet;
    private Boolean openAutoColumWidth;

    private StreamExportBuilder(OutputStream outputStream, String fileName, Class excelClass) {
        this.outputStream = outputStream;
        this.fileName = fileName;
        this.excelClass = excelClass;
    }

    public static StreamExportBuilder builder(OutputStream outputStream, String fileName, Class excelClass) {
        return new StreamExportBuilder(outputStream, fileName, excelClass);
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

}
