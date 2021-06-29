package org.toolkit.easyexcel.read.context;

import org.toolkit.easyexcel.read.RowReadStatus;

import java.io.OutputStream;

/**
 * 读取时的上下文环境.
 *
 * @author: zhoucx
 * @time: 2021-06-18
 */
public interface ReadContext<T> {


    /**
     * 获取当前文件中 sheet 数量.
     *
     * @return
     */
    int getSheetCounts();

    void setReadIndex(int readIndex);

    public int getReadIndex();

    void setSheetCounts(int sheetCounts);

    /**
     * 是否可删除该上下文
     *
     * @return
     */
    boolean removeable();

    RowReadStatus.Status getReadSheetStatus();

    void setReadSheetStatus(RowReadStatus.Status status, String message);

    void setStatus(RowReadStatus.Status status, String message);

    RowReadStatus.Status getStatus();

    void setFileSystem(FileSystem fileSystem);

    FileSystem getFileSystem();

    public String getResultMessage();

    long executeTimeMilliseconds();


}
