package org.toolkit.exception;

public enum ErrorInfo {

    MISSING_PARAMETERS(10001,"模板参数缺失异常！"),
    READCONTEXTSTRATEGY_NOT_FOUND(10002,"IReadContextStrategy 未实例化！"),
    HANDLER_EXCEPTION(99999,"业务处理异常");

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
