package org.toolkit.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import org.toolkit.easyexcel.read.ReadProcessHandler;
import org.toolkit.easyexcel.read.StreamImportBuilder;
import org.toolkit.easyexcel.read.context.FileSystem;
import org.toolkit.easyexcel.write.StreamExportBuilder;
import org.toolkit.easyexcel.write.WebExportBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
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
     * @param excelClass
     * @return
     */
    public static StreamExportBuilder SreamExportBuilder(OutputStream outputStream, Class excelClass) {
        return StreamExportBuilder.builder(outputStream, excelClass);
    }




    public static ExcelWriterBuilder getEasyExcel(OutputStream outputStream, Class excelClass) {
        return EasyExcel.write(outputStream, excelClass);
    }
    public static ExcelWriterBuilder getEasyExcel(String filePath, Class excelClass) {
        return EasyExcel.write(filePath, excelClass);
    }


    public static <T> StreamImportBuilder StreamImportBuilder(FileSystem fileSystem, Class<T> headClass, ReadProcessHandler<T> processHandler) {
        return StreamImportBuilder.builder(fileSystem, headClass, processHandler);
    }


}
