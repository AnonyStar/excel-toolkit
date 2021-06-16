package org.toolkit.exception;

public enum ErrorInfo {

    MISSING_PARAMETERS(10001,"模板参数缺失异常！");

    ErrorInfo(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;
    private String msg;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
