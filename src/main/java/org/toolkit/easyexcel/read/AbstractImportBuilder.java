package org.toolkit.easyexcel.read;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author: zhoucx
 * @time:
 */
public abstract class AbstractImportBuilder {

    protected ExcelReaderBuilder readerBuilder;

    /**
     * 同步读取返回数据.
     * <p>
     * 不推荐使用
     * 如果数据量大会导致大量数据在内存中，导致内存占用过高
     * </p>
     *
     * @param inputStream
     * @param headClass
     * @param sheetIndex
     * @param <T>
     * @return
     */
    public <T> List<T> synchronousRead(InputStream inputStream, Class<T> headClass, Integer sheetIndex) {
        return EasyExcel.read(inputStream).head(headClass).sheet(sheetIndex).doReadSync();
    }

    public <T> List<T> synchronousRead(InputStream inputStream, Class<T> headClass) {
        return synchronousRead(inputStream, headClass, 0);
    }

    public List<Map<Integer, String>> synchronousRead(InputStream inputStream) {
        return synchronousRead(inputStream, 0);
    }

    /**
     * 无映射实体导入.
     * @param inputStream
     * @param sheetIndex
     * @return
     */
    public List<Map<Integer, String>> synchronousRead(InputStream inputStream, Integer sheetIndex) {
        return EasyExcel.read(inputStream).sheet(sheetIndex).doReadSync();
    }



}
