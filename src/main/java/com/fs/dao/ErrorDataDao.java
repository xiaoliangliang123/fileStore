package com.fs.dao;

import config.DBTransaction;
import config.Util;
import dto.DataBean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @Auther: WANG_LIANG(XIAO)
 * @Date: 2018/6/27 15:51
 * @Description:
 */
public class ErrorDataDao {

    public static final String TABLE_NAME = "fs_rhcl_error_data";
    public static final String rbbh = "rbbh";
    public static final String fmh = "fmh";
    public static final String cbsj = "cbsj";
    public static final String bs = "bs";
    public static final String wbts = "wbts";
    public static final String fmzt = "fmzt";
    public static final String cjsj = "cjsj";
    public static final String syrl = "syrl";
    public static final String yh = "yh";
    public static final String grnd = "grnd";
    public static final String tjbh = "tjbh";
    public static final String czsj = "czsj";
    public static final String bz = "bz";
    public static final String yl = "yl";
    public static final String bcyl = "bcyl";
    public static final String yhbh = "yhbh";
    public static final String cjsj_char = "cjsj_char";


    public void initSQLTable() {

        Connection conn = null;
        Statement stat = null;
        try {
            conn = DBTransaction.getQueryModelConnection();
            stat = conn.createStatement();
            String sql = " create table "  + TABLE_NAME +  " ( " +
                    " " + yh + " varchar(32) not NULL , " +
                    " " + grnd + " varchar(9) , " +
                    " " + rbbh + " varchar(20) ," +
                    " " + fmh + " varchar(50) , " +
                    " " + cbsj + " date , " +
                    " " + bs + " number(18,4) ," +
                    " " + yl + " number(18,4) ," +
                    " " + wbts + " number(30,0), " +
                    " " + fmzt + " VARCHAR(20) ," +
                    " " + cjsj + " date ," +
                    " " + czsj + " TIMESTAMP(6) ," +
                    " " + tjbh + " varchar(20) ," +
                    " " + bcyl + " number(18,4) ," +
                    " " + syrl + " number(18,4) ," +
                    " " + bz + " varchar(100) ," +
                    " " + yhbh + " varchar(20) ," +
                    " " + cjsj_char + " varchar(20) " +
                    " ) " ;
            stat.execute(sql);
            DBTransaction.close(stat, conn);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBTransaction.close(stat, conn);
        }

    }


    public void addData(DataBean db) throws Exception {
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null ;
        try {

            conn = DBTransaction.getQueryModelConnection();



            stat = conn.createStatement();
            String d = Util.convJsonDateToString(db.cbsj, "yyyy-MM-dd hh:mm:ss");
            String cd = Util.convJsonDateToString(db.cjsj, "yyyy-MM-dd hh:mm:ss");
            String  sql = " insert into " + TABLE_NAME + "  (rbbh,fmh,cbsj,bs,wbts,fmzt,cjsj,syrl,yh,grnd,tjbh,czsj,bz,yl,bcyl,yhbh,cjsj_char)" + "  values ('" + db.bh + "','" + db.fmh + "'," + "to_date('" + d + "','YYYY-MM-DD HH24:MI:SS')" + "," + db.bs + "," + db.wbts + ",'" + db.fmzt + "'," + "to_date('" + cd + "','YYYY-MM-DD HH24:MI:SS') ," + db.syrl + ",'" + db.yhbh + "','" + db.dir + "','TJNYTZJT'," + "to_timestamp('" + cd + "','YYYY-MM-DD HH24:MI:SS')" + ",'" + 1 + "',null," + db.yl + ",'" + db.yhbh + "','" + db.cjsj + "')";

            stat.execute(sql);
        }catch (Exception e){
            System.out.println("插入数据出错 cjsj :"+db.cjsj +"yhbh:");
        } finally {
            //DBTransaction.close(stat, conn,rs);
        }

    }
}
