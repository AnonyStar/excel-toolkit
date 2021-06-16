package org.toolkit.exception;

public class ExcelKitException extends RuntimeException {

    public ExcelKitException(ErrorInfo errorInfo) {
        super(errorInfo.getMsg());
    }
    public ExcelKitException(String msg) {
        super(msg);
    }

}
