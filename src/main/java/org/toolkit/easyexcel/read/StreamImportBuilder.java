package org.toolkit.easyexcel.read;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.alibaba.excel.read.listener.ReadListener;
import com.sun.istack.internal.Nullable;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author: zhoucx
 * @time: 2021-06-17
 */
public class StreamImportBuilder<T> extends AbstractImportBuilder{



    private <T> StreamImportBuilder (InputStream inputStream, Class<T> headClass, ReadProcessHandler<T> processHandler,ExceptionProcessHandler exceptionProcessHandler) {
        readerBuilder = EasyExcel.read(inputStream, headClass, new ExcelReadAnalysisListener(
                new SpringTransactionExecutor(),
                processHandler,
                exceptionProcessHandler));
    }

    public static <T> StreamImportBuilder builder(InputStream inputStream, Class<T> headClass, ReadProcessHandler<T> processHandler, OutputStream outputStream) {

        return new StreamImportBuilder(inputStream, headClass, processHandler, new DefaultRowExceptionProcessHandler(outputStream));
    }

    public StreamImportBuilder registerReadListener(ReadListener readListener) {
        readerBuilder.registerReadListener(readListener);
        return this;
    }
    public ExcelReaderSheetBuilder sheet(@Nullable Integer sheetIndex, @Nullable String sheetName) {
        return readerBuilder.sheet(sheetIndex, sheetName);

    }

    public void doRead() {
        readerBuilder.doReadAll();
    }
}
