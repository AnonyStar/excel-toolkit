package org.toolkit.easyexcel;

import com.alibaba.excel.EasyExcel;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

public class ExcelKit {


    private ExcelKit() {
    }


    /**
     * web 端直接导出 excel 文件.
     * @param httpServletResponse
     * @param fileName
     * @param excelClass
     * @return
     */
    public static WebExportBuilder WebExportBuilder(HttpServletResponse httpServletResponse, String fileName, Class excelClass) {
        return WebExportBuilder.builder(httpServletResponse, fileName, excelClass);
    }


    /**
     * 流输出
     * @param outputStream
     * @param fileName
     * @param excelClass
     * @return
     */
    public static StreamExportBuilder SreamExportBuilder(OutputStream outputStream, String fileName, Class excelClass) {
        return StreamExportBuilder.builder(outputStream, fileName, excelClass);
    }
    public static ExportExcelKit ExportBuilder() {
        return new ExportExcelKit();
    }

}
