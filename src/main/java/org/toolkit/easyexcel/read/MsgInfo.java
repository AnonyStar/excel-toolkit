package org.toolkit.easyexcel.read;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * @author: zhoucx
 * @time: 2021-06-21
 */
public class MsgInfo {

    @ExcelProperty(value = "信息", index = -2)
    private String message;
    @ExcelProperty(value = "状态", index = -1)
    private String status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
