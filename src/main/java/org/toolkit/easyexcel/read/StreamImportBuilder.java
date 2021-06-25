package org.toolkit.easyexcel.read;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.sun.istack.internal.Nullable;
import org.toolkit.SpringConfig;
import org.toolkit.easyexcel.read.context.FileSystem;
import org.toolkit.easyexcel.read.context.ReadContext;
import org.toolkit.easyexcel.read.context.ReadContextHolder;
import org.toolkit.easyexcel.read.context.SimpleReadContext;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author: zhoucx
 * @time: 2021-06-17
 */
public class StreamImportBuilder<T> extends AbstractImportBuilder{


    private  ExcelWriter excelWriter;

    private String readContextKey;

    private <T> StreamImportBuilder (FileSystem fileSystem, Class<T> headClass, ReadProcessHandler<T> processHandler) {
        ReadContext readContext = new SimpleReadContext(fileSystem);
        readContextKey = ReadContextHolder.set(readContext);

        excelWriter = EasyExcel.write(fileSystem.getOutputStream(), headClass).build();
        readerBuilder = EasyExcel.read(fileSystem.getInputStream(), headClass, new ExcelReadAnalysisListener(
                new SpringTransactionExecutor(),
                processHandler,
                readContext,
                excelWriter));


    }

    public static <T> StreamImportBuilder builder(FileSystem fileSystem, Class<T> headClass, ReadProcessHandler<T> processHandler) {

        return new StreamImportBuilder(fileSystem, headClass, processHandler);
    }

    public StreamImportBuilder registerReadListener(ReadListener readListener) {
        readerBuilder.registerReadListener(readListener);
        return this;
    }
    public ExcelReaderSheetBuilder sheet(@Nullable Integer sheetIndex, @Nullable String sheetName) {
        return readerBuilder.sheet(sheetIndex, sheetName);

    }

    public String doRead() {
        readerBuilder.doReadAll();
        return this.readContextKey;
    }
}
