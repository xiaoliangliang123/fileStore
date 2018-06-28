package com.fs.service;

import dto.DataBean;
import dto.TotalBean;

import java.sql.SQLException;
import java.util.List;

/**
 * @Auther: WANG_LIANG(XIAO)
 * @Date: 2018/6/28 09:35
 * @Description:数据执行 接口类
 */
public interface ConvertService {

    //执行数据处理
    public Result convertDatas(String dir, String file, List<DataBean> dblist) throws Exception;

    //返回能执行的bean
    public <T> TotalBean<T> doFormat(Object object);

    //检查文件格式是否正确
    boolean checkFileFormatIsRight(String prefix);


}
