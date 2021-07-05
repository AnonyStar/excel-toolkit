package org.toolkit.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import org.toolkit.easyexcel.read.ReadProcessHandler;
import org.toolkit.easyexcel.read.StreamImportBuilder;
import org.toolkit.easyexcel.read.context.FileSystem;
import org.toolkit.easyexcel.write.StreamExportBuilder;
import org.toolkit.easyexcel.write.WebExportBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
    public static WebExportBuilder webExportBuilder(HttpServletResponse httpServletResponse, String fileName, Class excelClass) {
        return WebExportBuilder.builder(httpServletResponse, fileName, excelClass);
    }


    /**
     * 流输出
     * @param outputStream
     * @param excelClass
     * @return
     */
    public static StreamExportBuilder sreamExportBuilder(OutputStream outputStream, Class excelClass) {
        return StreamExportBuilder.builder(outputStream, excelClass);
    }




    public static ExcelWriterBuilder getEasyExcel(OutputStream outputStream, Class excelClass) {
        return EasyExcel.write(outputStream, excelClass);
    }
    public static ExcelWriterBuilder getEasyExcel(String filePath, Class excelClass) {
        return EasyExcel.write(filePath, excelClass);
    }


    public static <T> List<T> syncRead(FileSystem fileSystem, Class<T> headClass) {
        StreamImportBuilder builder = StreamImportBuilder.builderSysnc(fileSystem, headClass);
        return builder.synchronousRead();
    }

    public static <T> String asyncRead(FileSystem fileSystem, Class<T> headClass, ReadProcessHandler<T> processHandler) {
       return asyncRead(fileSystem, headClass,false, processHandler);
    }

    public static <T> String asyncRead(FileSystem fileSystem, Class<T> headClass,boolean remoteContext, ReadProcessHandler<T> processHandler) {
        StreamImportBuilder builder = StreamImportBuilder.builder(fileSystem, headClass, processHandler, remoteContext);
        ExecutorService service = new ThreadPoolExecutor(
                1,
                1,
                1000*60,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>(1),
                new ThreadPoolExecutor.DiscardPolicy());
        service.submit(() -> {
            builder.doRead();
        });
        return builder.getReadContextKey();
    }



}
