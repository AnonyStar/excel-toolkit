package org.toolkit.easyexcel;

import com.alibaba.excel.EasyExcel;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

public class ExcelKitFactory {


    private ExcelKitFactory() {
    }


    /**
     * web 端直接导出 excel 文件.
     * @param httpServletResponse
     * @param fileName
     * @param excelClass
     * @return
     */
    public static ExportExcelKit WebExportBuilder(HttpServletResponse httpServletResponse, String fileName, Class excelClass) {
        return new ExportExcelKit(httpServletResponse, fileName, excelClass);
    }

    public static ExportExcelKit ExportBuilder() {
        return new ExportExcelKit();
    }

}
