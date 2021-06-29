package org.toolkit.easyexcel.read;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
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

    protected ExcelWriter excelWriter;

    protected String readContextKey;

    /**
     * 同步读取返回数据.
     * <p>
     * 不推荐使用
     * 如果数据量大会导致大量数据在内存中，导致内存占用过高
     * </p>
     *
     * @param <T>
     * @return
     */
    public <T> List<T> synchronousRead() {
        return readerBuilder.doReadAllSync();
    }


    /**
     * 无映射实体导入.
     * @param sheetIndex
     * @return
     */
    public List<Map<Integer, String>> synchronousRead(Integer sheetIndex) {
        return readerBuilder.sheet(sheetIndex).doReadSync();
    }



}
