package org.toolkit.easyexcel.read.context;

import org.toolkit.easyexcel.read.RowReadStatus;

import java.io.OutputStream;

/**
 * 读取时的上下文环境.
 * @author: zhoucx
 * @time: 2021-06-18
 */
public interface ReadContext<T> {


    /**
     * 获取当前文件中 sheet 数量.
     * @return
     */
    int getSheetCounts();

    /**
     * 是否可删除该上下文
     * @return
     */
    boolean removeable();

    RowReadStatus.Status readSheetStatus();


    public void setStatus(Integer rowIndex, boolean status, String message);

    void setFileSystem(FileSystem fileSystem);

    FileSystem getFileSystem();




}
