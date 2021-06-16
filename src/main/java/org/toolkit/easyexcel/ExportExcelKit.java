package org.toolkit.easyexcel;

import com.alibaba.excel.EasyExcel;
import org.toolkit.core.IExportFunction;
import org.toolkit.exception.ExcelKitException;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

public class ExportExcelKit extends EasyExcel {


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

    public ExportExcelKit(){
        super();
    }


    public <ParamType, ExceleClass> void exportResponse(ParamType params,
                                                        IExportFunction<ParamType, ExceleClass> exportFunction) {
        if (Objects.isNull(httpServletResponse)) {
            throw new ExcelKitException("httpServletResponse 参数为空");
        }
    }



}
