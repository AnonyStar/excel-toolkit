package org.toolkit.easyexcel.export;

import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import org.junit.Test;
import org.toolkit.easyexcel.ExcelKit;
import org.toolkit.easyexcel.write.StreamExportBuilder;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: zhoucx
 * @time: ${{
 */
public class ExportTest {

    /**
     * 简单的使用 easyexcel 写.
     * 通过注解方式添加样式.
     */
    @Test
    public void simpleWrite() {
        long timeMillis = System.currentTimeMillis();

        String fileName = ExportTest.class.getResource("/").getPath() + "simpleWrite1624264473759.xlsx";
        ExcelWriterBuilder easyExcel = ExcelKit.getEasyExcel(fileName, DemoData.class);
      //  LoopMergeStrategy strategy = new LoopMergeStrategy(2, 0);
       // easyExcel.registerWriteHandler(strategy);
        easyExcel.sheet().doWrite(dataList());
        System.out.println("耗时：" + (System.currentTimeMillis() - timeMillis));

    }

    @Test
    public void streamPageWrite() throws FileNotFoundException {
        long timeMillis = System.currentTimeMillis();
        String fileName = ExportTest.class.getResource("/").getPath() + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
        StreamExportBuilder streamExportBuilder = ExcelKit.sreamExportBuilder(new FileOutputStream(fileName), DemoData.class);
        Param param = new Param();
        streamExportBuilder.pageSize(209715);
        streamExportBuilder.export(param, (param1, pageNum, pageSize) -> dataList(param1, pageNum, pageSize));
        System.out.println("耗时：" + (System.currentTimeMillis() - timeMillis));
    }

    private List<DemoData> dataList(Param param1, int pageNum, int pageSize) {
        List<DemoData> list = new ArrayList<>();
        if (pageNum <= 5) {
            for (int i = 0; i < pageSize; i++) {
                DemoData data = new DemoData();
                data.setString("字符串" + i);
                data.setDate(new Date());
                data.setDoubleData(0.56);
                data.setAddress("福建省福州市仓山区金桔路公园道永辉超市");
                data.setName("永辉超市");
                data.setPrice(1000D);
                data.setAge(11);
                list.add(data);
            }
        }
        return list;
    }


    private class Param {

    }

    private List<DemoData> dataList() {
        List<DemoData> list = new ArrayList<DemoData>();
        for (int i = 0; i < 100; i++) {
            DemoData data = new DemoData();
            if (i % 5 != 0) {
                data.setString("字符串" + i);
                data.setName("标题-" + i);
            }
            data.setAge(i % 120);
            data.setDate(new Date());
            data.setDoubleData(Math.random());
            data.setPrice(Math.random() * 7);
            data.setAddress("福建省福州市仓山区公园道-1永辉超市");
            data.setDescmarks(" 直接写，这里注意，finish的时候会自动关闭OutputStream,当然你外面再关闭流问题不大");
            data.setValidPeriod(new Date().toString());
            data.setTest1(".导入Excel文件, 单元格格式使用文本或者常规, 防止出现不可预测异常");
            data.setTest2(".导入Excel文件, 单元格格式使用文本或者常规, 防止出现不可预测异常");
            data.setTest3(".导入Excel文件, 单元格格式使用文本或者常规, 防止出现不可预测异常");
            data.setTest4(".导入Excel文件, 单元格格式使用文本或者常规, 防止出现不可预测异常");
            data.setTest5(".导入Excel文件, 单元格格式使用文本或者常规, 防止出现不可预测异常");
            data.setTest6(".导入Excel文件, 单元格格式使用文本或者常规, 防止出现不可预测异常");
            data.setTest6("第一行有效单元格内必须包含内容并且以第一行为依据, 导入Excel文件列数必须等于标注注解的属性数量");
            list.add(data);
        }
        return list;
    }

}
