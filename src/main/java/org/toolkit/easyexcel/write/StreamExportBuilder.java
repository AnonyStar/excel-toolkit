package org.toolkit.easyexcel.write;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.toolkit.core.IExportFunction;

import java.io.OutputStream;

/**
 * @author: zhoucx
 * @time: 2021/6/16 22:49
 */
public class StreamExportBuilder extends AbstractExportBuilder {

    private static final Logger logger = LoggerFactory.getLogger(StreamExportBuilder.class);



    protected StreamExportBuilder(OutputStream outputStream, Class excelClass) {
        this.outputStream = outputStream;
        this.excelClass = excelClass;

    }

    public static StreamExportBuilder builder(OutputStream outputStream, Class excelClass) {
        return new StreamExportBuilder(outputStream, excelClass);
    }


    /**
     * 按输出流导出excel.
     *
     * @param params
     * @param exportFunction
     * @param <ParamType>
     * @param <ExceleClass>
     */
    @Override
    public <ParamType, ExceleClass> void export(ParamType params,
                                                IExportFunction<ParamType, ExceleClass> exportFunction) {

        doProcess(params, exportFunction, true);
    }
    @Override
    public <ParamType, ExceleClass> StreamExportBuilder exportMultiSheet(ParamType params,
                                                                         IExportFunction<ParamType, ExceleClass> exportFunction) {

        doProcess(params, exportFunction, false);
        return this;
    }


}
