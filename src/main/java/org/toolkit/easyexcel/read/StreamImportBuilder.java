package org.toolkit.easyexcel.read;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.alibaba.excel.read.listener.ReadListener;
import com.sun.istack.internal.Nullable;
import org.toolkit.easyexcel.read.context.FileSystem;
import org.toolkit.easyexcel.read.context.ReadContext;
import org.toolkit.easyexcel.read.context.ReadContextHolder;
import org.toolkit.easyexcel.read.context.SimpleReadContext;



/**
 * @author: zhoucx
 * @time: 2021-06-17
 */
public class StreamImportBuilder<T> extends AbstractImportBuilder{



    private <T> StreamImportBuilder(FileSystem fileSystem, Class<T> headClass, ReadProcessHandler<T> processHandler, boolean remoteContext) {
        ReadContext readContext = new SimpleReadContext();
        readContext.setFileSystem(fileSystem);
        readContextKey = ReadContextHolder.initContext(readContext);
        excelWriter = EasyExcel.write(fileSystem.getOutputStream(), headClass).build();
        readerBuilder = EasyExcel.read(fileSystem.getInputStream(), headClass, new ExcelReadAnalysisListener(
                new SpringTransactionExecutor(),
                processHandler,
                readContext,
                excelWriter));
    }

    public <T> StreamImportBuilder(FileSystem fileSystem, Class<T> headClass) {
        readerBuilder = EasyExcel.read(fileSystem.getInputStream()).head(headClass);
    }

    public static <T> StreamImportBuilder builder(FileSystem fileSystem, Class<T> headClass, ReadProcessHandler<T> processHandler, boolean remoteContext) {
        return new StreamImportBuilder(fileSystem, headClass, processHandler, remoteContext);
    }

    public static <T> StreamImportBuilder builderSysnc(FileSystem fileSystem, Class<T> headClass) {
        return new StreamImportBuilder(fileSystem, headClass);
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

    public String getReadContextKey() {
        return readContextKey;
    }
}
