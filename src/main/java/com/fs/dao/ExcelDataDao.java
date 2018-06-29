package com.fs.dao;

import com.fs.service.Result;
import config.DBTransaction;
import config.utl.Util;
import dto.DataBean;
import dto.ExcelDataBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

/**
 * @Auther: WANG_LIANG(XIAO)
 * @Date: 2018/6/28 14:46
 * @Description:
 */
public class ExcelDataDao {

    private static Logger log = Logger.getLogger(ExcelDataDao.class.getClass());


    public static final String TABLE_NAME = "jl_fk_ryl";
    public static final String YHBH = "YHBH";
    public static final String GRND = "GRND";
    public static final String YF = "YF";
    public static final String BJYL = "BJYL";
    public static final String BH = "BH";
    public static final String FMH = "FMH";
    public static final String DYYL = "DYYL";

    public Result addDatas(String dir, String file, List<ExcelDataBean> dblist) throws Exception {
        Result result = new Result();
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null ;
        ExcelDataBean db = null;
        try {
            result.successCount = dblist.size();
            conn = DBTransaction.getQueryModelConnection();
            conn.setAutoCommit(false);
            stat = conn.createStatement();
            Iterator<ExcelDataBean> dbIterator = dblist.iterator();

            stat = conn.createStatement();
            dbIterator = dblist.iterator();
            String sql = null;

            while(dbIterator.hasNext()) {

                 db = dbIterator.next();


                if (null == db.getYhbh() || StringUtils.isEmpty(db.getYhbh())) {
                    result.failedCount ++;
                    result.successCount --;
                     log.error(" error 该条插入失败 用户编号为空 ，所在文件  :"+file +" , ");
                }
                 else {
                    sql = " insert into " + TABLE_NAME + "  (YHBH,GRND,YF,BJYL,DYYL)" + "  values ('" + db.getYhbh() + "','" + dir+ "','"+db.getYf()+"',"+db.getBjjyyrl()+","+db.getByyyrl()+")";
                    stat.addBatch(sql);
                }
            }
            stat.executeBatch();
            conn.commit();

        }catch (Exception e){
            System.out.println("插入数据出错 file :"+file);
            throw  new Exception(e);
        } finally {
            DBTransaction.close(stat, conn,rs);
        }
        return  result;
    }
}
