package org.toolkit.exception;

import org.toolkit.easyexcel.read.RowReadStatus;

/**
 * @author: zhoucx
 * @time: 2021-06-28
 */
public class RowHandlerException extends ExcelKitException{

    private RowReadStatus readStatus;

    public RowHandlerException(RowReadStatus readStatus) {
        this.readStatus = readStatus;
    }


    public RowReadStatus getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(RowReadStatus readStatus) {
        this.readStatus = readStatus;
    }
}
