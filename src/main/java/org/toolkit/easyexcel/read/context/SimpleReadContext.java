package org.toolkit.easyexcel.read.context;

import org.toolkit.easyexcel.read.RowReadStatus;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author: zhoucx
 * @time: 2021-06-18
 */
public class SimpleReadContext<T> implements ReadContext<T> {

    /**
     * 执行超时时间 单位秒
     */
    private static final long EXECUTION_TIMEOUT_SECOND = 1800L;

    private RowReadStatus.Status status = RowReadStatus.Status.NOT_STARTED;

    private RowReadStatus.Status sheetStatus = RowReadStatus.Status.NOT_STARTED;

    private RowReadStatus.Status excelStatus = RowReadStatus.Status.NOT_STARTED;

    private volatile int readIndex = 0;

    private long createTime;
    /**
     * Excel中Sheet个数
     */

    private int sheetCounts;

    private FileSystem fileSystem;

    private String resultMessage;

    /**
     * 对于当前Sheet所有的行的读取状态记录
     */
    private List<RowReadStatus> readStatuses = new CopyOnWriteArrayList<>();

    public SimpleReadContext(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
        this.createTime = System.currentTimeMillis();
    }

    @Override
    public int getSheetCounts() {
        return 0;
    }
    @Override
    public void setReadIndex(int readIndex) {
        this.readIndex = readIndex;
    }
    @Override
    public void setSheetCounts(int sheetCounts) {
        this.sheetCounts = sheetCounts;
    }

    @Override
    public boolean removeable() {
        // 读取完成或者超时将自动移除
        return (this.status == RowReadStatus.Status.FINISH && this.readIndex == this.readStatuses.size())
                || (((System.currentTimeMillis() - this.createTime) / (60 * 1000)) > EXECUTION_TIMEOUT_SECOND);
    }

    @Override
    public RowReadStatus.Status getReadSheetStatus() {
        return sheetStatus;
    }

    @Override
    public void setReadSheetStatus(RowReadStatus.Status status, String message) {
        sheetStatus = status;
        resultMessage = message;
    }
    @Override
    public int getReadIndex() {
        return readIndex;
    }

    @Override
    public void setStatus(RowReadStatus.Status status, String message) {
        this.status = status;
        resultMessage = message;

    }
    @Override
    public String getResultMessage() {
        return resultMessage;
    }

    @Override
    public RowReadStatus.Status getStatus() {
        return status;
    }

    @Override
    public void setFileSystem(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    @Override
    public FileSystem getFileSystem() {
        return this.fileSystem;
    }

    @Override
    public long executeTimeMilliseconds() {
        return System.currentTimeMillis() - createTime;
    }
}
