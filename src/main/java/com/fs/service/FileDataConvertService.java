package com.fs.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fs.dao.DataDao;
import com.fs.dao.ErrorDao;
import config.DBTransaction;
import config.QueryModelConfig;
import dto.DataBean;
import dto.TotalBean;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

/**
 * @Auther: WANG_LIANG(XIAO)
 * @Date: 2018/6/27 09:50
 * @Description: 文件数据类型处理类
 */
public class FileDataConvertService  implements ConvertService{



    DataDao dd = new DataDao();
    ErrorDao ed = new ErrorDao();

    public FileDataConvertService(){
        String filePath = QueryModelConfig.class.getClassLoader()
                .getResource("logger.properties").getPath();
        PropertyConfigurator.configure( filePath );
    }

    public Result convertDatas(String dir, String file, List<DataBean>  dblist) throws Exception {
        Connection conn =  DBTransaction.getQueryModelConnection();
        Result result = null;
        try {
            result = dd.addDatas(dir,file,dblist);
         }catch (Exception e){

            e.printStackTrace();
            ed.addErrorData(file);
            throw  new Exception("更新异常",e);
        }finally {
            DBTransaction.close(conn);
        }
        return result;
    }

    public boolean checkFileFormatIsRight(String prefix) {

        return true;
    }

    public TotalBean doFormat(Object obj) {

        String json = (String)obj;
        json = json.replace("\"[{","[{");
        json = json.replace("]\"}","]}");
        TotalBean<DataBean> totalBean =   JSON.parseObject(json, new TypeReference<TotalBean<DataBean>>() {});
        return totalBean;
    }

    public Result convert(String dir, String file, List<DataBean> dblist) throws SQLException, ClassNotFoundException {
        Connection conn =  DBTransaction.getQueryModelConnection();
        Result result = new Result();
        Iterator<DataBean> dbIterator = dblist.iterator();
        while(dbIterator.hasNext()) {

            DataBean db = dbIterator.next();
            try {
               // if(dd.checkDataIsExit(db))
                //    continue;
                db.setDir(dir);
                dd.addData(db);
                result.successCount ++;
            }catch (Exception e){
                e.printStackTrace();
                result.failedCount ++;
                db.setFile(file);
                ed.addErrorData(db);
            }finally {
                DBTransaction.close(conn);
            }
        }
        return result;
    }
}
