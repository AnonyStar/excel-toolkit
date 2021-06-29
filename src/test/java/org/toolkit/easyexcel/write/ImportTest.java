package org.toolkit.easyexcel.write;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import org.apache.poi.ss.formula.functions.T;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.toolkit.GeneratorApp;
import org.toolkit.easyexcel.ExcelKit;
import org.toolkit.easyexcel.export.DemoData;
import org.toolkit.easyexcel.export.ExportTest;
import org.toolkit.easyexcel.read.ReadProcessHandler;
import org.toolkit.easyexcel.read.RowReadStatus;
import org.toolkit.easyexcel.read.context.DefaultFileSystem;
import org.toolkit.easyexcel.read.context.ReadContext;
import org.toolkit.easyexcel.read.context.ReadContextHolder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author: zhoucx
 * @time: 2021-06-21
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {GeneratorApp.class})
public class ImportTest {




    @Test
    public void simplateWrite() throws FileNotFoundException {
        String fileName = ExportTest.class.getResource("/").getPath() + "simpleWrite1624256744704.xlsx";

        ExcelReaderBuilder read = EasyExcel.read(new FileInputStream(fileName));
        read.registerReadListener(new Demo1EventListener<DemoData>())
                .registerReadListener(new Demo2EventListener<DemoData>()).head(DemoData.class).doReadAll();

    }

    @Test
    public void simplateWrite2() throws FileNotFoundException {
        String fileName = ExportTest.class.getResource("/").getPath() + "simpleWrite1624264473759.xlsx";

        ExcelReaderBuilder read = EasyExcel.read(new FileInputStream(fileName));
        read.registerReadListener(new Demo1EventListener<DemoData>())
                .registerReadListener(new Demo2EventListener<DemoData>()).head(DemoData.class).sheet().doRead();

    }


    @Test
    public void tstWrite() throws FileNotFoundException {
        String fileName = ExportTest.class.getResource("/").getPath() + "simpleWrite1624264473759.xlsx";
        String read = ExcelKit.asyncRead(new DefaultFileSystem(new File(fileName)), DemoData.class, (t, analysisContext) -> {
            System.out.println("业务处理....." + t);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        new Thread(() -> {
            while (true) {
                ReadContext readContext = ReadContextHolder.get(read);
                RowReadStatus.Status sheetStatus = readContext.getReadSheetStatus();
                int readIndex = readContext.getReadIndex();
                int sheetCounts = readContext.getSheetCounts();
                re
                System.out.println("解析处理完：" + read + "状态" + sheetStatus.getStatus() + "，当前读取行：" + readIndex +" sheetcount" + sheetCounts);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        while (true) {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


    @Test
    public void readTest() throws FileNotFoundException {
        String fileName = ExportTest.class.getResource("/").getPath() + "simpleWrite1624264473759.xlsx";
        List<DemoData> demoData = ExcelKit.syncRead(new DefaultFileSystem(new File(fileName)), DemoData.class);
        System.out.println(demoData);
    }



}
