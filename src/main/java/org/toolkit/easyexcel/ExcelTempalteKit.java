package org.toolkit.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.StringUtils;
import org.toolkit.exception.ErrorInfo;
import org.toolkit.exception.ExcelKitException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Objects;

public class ExcelTempalteKit {


    /**
     * @param outputStream  {@link OutputStream} 输出流
     * @param templateClass {@link Class} 输出模板实体
     */
    public static void createTemplate(OutputStream outputStream, Class templateClass) {
        createTemplate(outputStream, templateClass, null);
    }

    /**
     * 创建模板导出.
     *
     * @param outputStream  {@link OutputStream} 输出流
     * @param templateClass {@link Class} 输出模板实体
     * @param sheetName     {@link String} 工作簿名称
     */
    public static void createTemplate(OutputStream outputStream, Class templateClass, String sheetName) {
        if (Objects.isNull(outputStream) || Objects.isNull(templateClass)) {
            throw new ExcelKitException(ErrorInfo.MISSING_PARAMETERS);
        }
        EasyExcel.write(outputStream, templateClass).sheet(Objects.isNull(sheetName) ? null : sheetName).doWrite(null);
    }


    public static void createTemplate(HttpServletResponse response, Class templateClass, String fileName) throws IOException {
        createTemplate(response, templateClass, fileName, null);
    }

    /**
     * 通过 web 导出模板文件.
     * @param response
     * @param templateClass
     * @param fileName
     * @param sheetName
     * @throws IOException
     */
    public static void createTemplate(HttpServletResponse response, Class templateClass, String fileName, String sheetName) throws IOException {
        if (Objects.isNull(response) || Objects.isNull(templateClass) || StringUtils.isEmpty(fileName)) {
            throw new ExcelKitException(ErrorInfo.MISSING_PARAMETERS);
        }
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("UTF-8");
        fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        createTemplate(response.getOutputStream(), templateClass, sheetName);
    }

}
