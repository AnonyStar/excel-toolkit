package org.toolkit.easyexcel.export;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.annotation.write.style.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.bouncycastle.cms.PasswordRecipient;

import java.util.Date;

@HeadRowHeight(50)
@ColumnWidth(20)
@ContentRowHeight(30)
@ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
@HeadStyle(horizontalAlignment = HorizontalAlignment.CENTER, fillPatternType = FillPatternType.NO_FILL)
public class DemoData {
    @ExcelProperty("字符串标题")
    private String string;

    @ExcelProperty("日期标题")
    @ColumnWidth(50)
    private Date date;
    @ExcelProperty("数字标题")
    private Double doubleData;

    @ExcelProperty("标题")
    private String name;

    @ExcelProperty("年龄")
    private Integer age;

    @ExcelProperty("地址")
    private String address;
    @ExcelProperty("价格")
    @NumberFormat("#.##")
    private Long price;
    /**
     * 忽略这个字段
     */
    @ExcelIgnore
    private String ignore;

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getDoubleData() {
        return doubleData;
    }

    public void setDoubleData(Double doubleData) {
        this.doubleData = doubleData;
    }

    public String getIgnore() {
        return ignore;
    }

    public void setIgnore(String ignore) {
        this.ignore = ignore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}