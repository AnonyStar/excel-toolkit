package org.toolkit.core;


import java.util.List;

/**
 * excel 导出分页获取数据.
 * @param <ParamType> 请求参数.
 * @param <ExceleClass> excele 填充数据类型
 */
@FunctionalInterface
public interface IExportFunction<ParamType, ExceleClass> {

    /**
     *
     * @param param
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<ExceleClass> pageQuery(ParamType param, int pageNum, int pageSize);

}
