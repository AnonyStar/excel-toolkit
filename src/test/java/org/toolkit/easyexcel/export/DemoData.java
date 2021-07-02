package org.toolkit.easyexcel.export;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.annotation.write.style.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.toolkit.core.annotation.ExcelFirstMainly;
import org.toolkit.easyexcel.read.MsgInfo;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@HeadRowHeight(50)
@ColumnWidth(20)
@ContentRowHeight(30)
@ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
@HeadStyle(horizontalAlignment = HorizontalAlignment.CENTER, fillPatternType = FillPatternType.NO_FILL)
public class DemoData extends MsgInfo {

    @ExcelProperty("字符串标题")
    private String string;

    @ExcelProperty("日期标题")
    @ColumnWidth(50)
    @NotNull(message = "不可为空")
    private Date date;
    @ExcelProperty("数字标题")
    private Double doubleData;

    @ExcelProperty("标题")
    @NotNull(message = "不可为空")
    private String name;

    @ExcelProperty("年龄")
    @ExcelFirstMainly(key = "name")
    @Max(value = 100, message = "年龄过大")
    @Min( value = 1, message = "过小")
    @NotNull(message = "不可为空年龄")
    private Integer age;

    @ExcelProperty("地址")
    private String address;
    @ExcelProperty("价格")
    @NumberFormat("###,###.##")
    private Double price;

    @ExcelProperty("商品描述")
    private String descmarks;

    @ExcelProperty("商品有效期")
    private String validPeriod;

    @ExcelProperty("测试字段1")
    private String test1;
    @ExcelProperty("测试字段2")
    private String test2;
    @ExcelProperty("测试字段3")
    private String test3;
    @ExcelProperty("测试字段4")
    private String test4;
    @ExcelProperty("测试字段5")
    private String test5;
    @ExcelProperty("测试字段6")
    private String test6;
    @ExcelProperty("测试字段7")
    private String test7;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescmarks() {
        return descmarks;
    }

    public void setDescmarks(String descmarks) {
        this.descmarks = descmarks;
    }

    public String getValidPeriod() {
        return validPeriod;
    }

    public void setValidPeriod(String validPeriod) {
        this.validPeriod = validPeriod;
    }

    public String getTest1() {
        return test1;
    }

    public void setTest1(String test1) {
        this.test1 = test1;
    }

    public String getTest2() {
        return test2;
    }

    public void setTest2(String test2) {
        this.test2 = test2;
    }

    public String getTest3() {
        return test3;
    }

    public void setTest3(String test3) {
        this.test3 = test3;
    }

    public String getTest4() {
        return test4;
    }

    public void setTest4(String test4) {
        this.test4 = test4;
    }

    public String getTest5() {
        return test5;
    }

    public void setTest5(String test5) {
        this.test5 = test5;
    }

    public String getTest6() {
        return test6;
    }

    public void setTest6(String test6) {
        this.test6 = test6;
    }

    public String getTest7() {
        return test7;
    }

    public void setTest7(String test7) {
        this.test7 = test7;
    }

    @Override
    public String toString() {
        return "DemoData{" +
                "string='" + string + '\'' +
                ", date=" + date +
                ", doubleData=" + doubleData +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", price=" + price +
                ", ignore='" + ignore + '\'' +
                '}';
    }
}