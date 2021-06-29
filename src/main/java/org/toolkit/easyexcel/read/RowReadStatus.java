package org.toolkit.easyexcel.read;

import java.io.Serializable;

/**
 * 行数据的读取状态.
 *
 * @author: zhoucx
 * @time: 2021-06-18
 */
public class RowReadStatus implements Serializable {
    /**
     * 数据的行号.
     */
    private int rowIndex;

    /**
     * 读取的成功/失败状态.
     */
    private boolean status;

    /**
     * 错误消息.
     */
    private String message;

    /**
     * 错误行.
     */
    private boolean blank;


    private Class headClass;



    public enum Status {
        /**
         * 未开始 ：初始化状态
         */
        NOT_STARTED("未开始"),

        /**
         * 已完成 ： ☞指一个sheet 读取完成.
         */
        FINISH("已完成"),
        /**
         * 读取中
         */
        READING("读取中"),
        /**
         * 未完成，出现错误终止
         */
        UNFINISHED("未完成");

        private String status;
        Status(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isBlank() {
        return blank;
    }

    public void setBlank(boolean blank) {
        this.blank = blank;
    }

    public Class getHeadClass() {
        return headClass;
    }

    public void setHeadClass(Class headClass) {
        this.headClass = headClass;
    }
}
