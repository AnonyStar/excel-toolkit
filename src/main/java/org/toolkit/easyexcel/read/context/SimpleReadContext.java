package org.toolkit.easyexcel.read.context;

import jdk.nashorn.internal.objects.annotations.Getter;
import org.toolkit.easyexcel.read.RowReadStatus;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author: zhoucx
 * @time: 2021-06-18
 */
public class SimpleReadContext implements ReadContext<RowReadStatus> {

    /**
     * 执行超时时间 单位秒
     */
    private static final long EXECUTION_TIMEOUT_SECOND = 1800L;

    private RowReadStatus.Status status = RowReadStatus.Status.NOT_STARTED;

    private RowReadStatus.Status excelStatus = RowReadStatus.Status.NOT_STARTED;

    private volatile int readIndex = 0;
    private long createTime;
    /** Excel中Sheet个数 */

    private int sheetCounts;

    /** 对于当前Sheet所有的行的读取状态记录 */
    private List<RowReadStatus> readStatuses = new CopyOnWriteArrayList<>();

    @Override
    public int getSheetCounts() {
        return 0;
    }

    @Override
    public boolean removeable() {
        return false;
    }

    @Override
    public RowReadStatus.Status readSheetStatus() {
        return null;
    }

    @Override
    public void setStatus(Integer rowIndex, boolean status, String message) {

    }

    @Override
    public <T> void setFileSystem(T fileSystem) {

    }

    @Override
    public <T> T getFileSystem() {
        return null;
    }
}
