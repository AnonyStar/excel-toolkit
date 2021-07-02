package org.toolkit.easyexcel.write;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.toolkit.GeneratorApp;
import org.toolkit.easyexcel.ExcelKit;
import org.toolkit.easyexcel.export.DemoData;
import org.toolkit.easyexcel.export.ExportTest;
import org.toolkit.easyexcel.read.RowReadStatus;
import org.toolkit.easyexcel.read.context.*;
import org.toolkit.easyexcel.read.serializer.SerializeUtil;
import org.toolkit.model.Demo2Entity;
import org.toolkit.repository.Demo2EntityRepository;
import org.toolkit.model.DemoEntity;
import org.toolkit.repository.DemoEntityRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author: zhoucx
 * @time: 2021-06-21
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {GeneratorApp.class})
public class ImportTest {


    @Autowired
    private DemoEntityRepository demoEntityRepository;
    @Autowired
    private Demo2EntityRepository demo2EntityRepository;

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
        String read = ExcelKit.asyncRead(new DefaultFileSystem(new File(fileName)), DemoData.class, true,(t, analysisContext) -> {
            Optional<DemoEntity> demoEntity1 = demoEntityRepository.findById((long) Math.round(10000));
//            demoEntityRepository.deleteById((long) Math.round(99999));
            System.out.println("业务处理....." + t);
            DemoEntity demoEntity = new DemoEntity();
            BeanUtils.copyProperties(t, demoEntity);
            Demo2Entity demo2Entity = new Demo2Entity();
            BeanUtils.copyProperties(t, demo2Entity);
            demoEntityRepository.save(demoEntity);
            demo2EntityRepository.save(demo2Entity);
        });

        new Thread(() -> {
            while (true) {
                ReadContext readContext = ReadContextHolder.get(read);
                RowReadStatus.Status sheetStatus = readContext.getReadSheetStatus();
                int readIndex = readContext.getReadIndex();
                int sheetCounts = readContext.getSheetCounts();
                System.out.println("解析处理完：" + read + "状态" + sheetStatus.getStatus() +
                        "，当前读取行：" + readIndex +" sheetcount" + sheetCounts +
                        " 当前行状态：" + readContext.getCurrentRowstatus().getStatus() +
                        " , 耗费时间：" + readContext.executeTimeMilliseconds());
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
    public void remoteContextTest() throws FileNotFoundException {
        String fileName = ExportTest.class.getResource("/").getPath() + "simpleWrite1624264473759.xlsx";
        FileSystem<File> fileSystem = new DefaultFileSystem(new File(fileName));
        ReadContext readContext = new SimpleReadContext(fileSystem);
        byte[] serialize = SerializeUtil.serialize(readContext);
        String key  = ReadContextHolder.initContext(readContext);
        IReadContextStrategy instance = RedisReadContextStrategy.getInstance();
        System.out.println(key);
        ReadContext context = ReadContextHolder.get(key);
        System.out.println(context);

    }

    @Test
    public void readTest() throws FileNotFoundException {
        String fileName = ExportTest.class.getResource("/").getPath() + "simpleWrite1624264473759.xlsx";
        List<DemoData> demoData = ExcelKit.syncRead(new DefaultFileSystem(new File(fileName)), DemoData.class);
        DemoData demoData1 = demoData.get(0);
        DemoEntity demoEntity = new DemoEntity();
        BeanUtils.copyProperties(demoData1, demoEntity);
        Demo2Entity demo2Entity = new Demo2Entity();
        BeanUtils.copyProperties(demoData1, demo2Entity);
        demoEntityRepository.save(demoEntity);
        demo2EntityRepository.save(demo2Entity);
    }



}
