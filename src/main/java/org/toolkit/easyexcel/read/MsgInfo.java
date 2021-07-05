package org.toolkit.easyexcel.read;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;

/**
 * @author: zhoucx
 * @time: 2021-06-21
 */
public class MsgInfo {

    @ExcelProperty(value = "处理结果信息", index = -2)
    @HeadStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND, fillForegroundColor = 10)
    @ContentFontStyle(color = Font.COLOR_RED, fontHeightInPoints = 11)
    private String message;
    @ExcelProperty(value = "处理状态", index = -1)
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
