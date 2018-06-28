package com.fs.dao;

 import com.fs.service.Result;
 import config.DBTransaction;
import config.QueryModelConfig;
import config.utl.Util;
import dto.DataBean;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

public class DataDao {

    public static final String TABLE_NAME = "fs_rhcl_data";
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
    public static final String jtbh = "tjbh";
    public static final String czsj = "czsj";
    public static final String bz = "bz";
    public static final String yl = "yl";
    public static final String bcyl = "bcyl";
    public static final String yhbh = "yhbh";
    public static final String cjsj_char = "cjsj_char";

    ErrorDataDao edd = new ErrorDataDao();
    private static Logger log = Logger.getLogger(DataDao.class.getClass());


    public DataDao(){
        String filePath = QueryModelConfig.class.getClassLoader()
                .getResource("logger.properties").getPath();
        PropertyConfigurator.configure( filePath );
    }
    public void queryCount(){

        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null ;
        try {
            conn = DBTransaction.getQueryModelConnection();
            stat = conn.createStatement();
            String sql = " select count(*) as count from "  + "sfyh_yh" ;
            rs = stat.executeQuery(sql);
            if(rs.next()){
                int count =  rs.getInt("count");
                System.out.println("succes"+count);
            }
            DBTransaction.close(stat, conn);
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            DBTransaction.close(stat, conn);
        }

    }

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
                    " " + jtbh + " number(18,4) ," +
                    " " + bcyl + " number(18,4) ," +
                    " " + syrl + " number(18,4) ," +
                    " " + bz + " varchar(100) " +
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


    public boolean checkDataIsExit(DataBean db) throws Exception {
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null ;
        try {
            conn = DBTransaction.getQueryModelConnection();
            stat = conn.createStatement();
            String sql = " select  yh   from "  + TABLE_NAME  +" where " +yhbh +" = '" +db.yhbh +"' and "+cjsj_char +" = '"+db.cjsj+"'";
            rs = stat.executeQuery(sql);
            if(rs.next()){
                return true ;
            }

        }catch (Exception e){
           throw  new Exception("查询数据是否存在出错 cjsj :"+db.cjsj +"yhbh:"+yhbh);
        } finally {
            DBTransaction.close(stat, conn,rs);
        }
        return false;
    }

    public void addData(DataBean db) throws Exception {
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null ;
        try {

            conn = DBTransaction.getQueryModelConnection();
            stat = conn.createStatement();
            String sql = " select  id   from sfyh_yh  where yhbh = '" +db.yhbh+"'";
            rs = stat.executeQuery(sql);
            if(!rs.next()){
                throw new Exception("从sfyh_yh表 查询不到 id ，yhbh："+db.yhbh);
            }else {

                String yhbhId =  rs.getString("id");
                stat = conn.createStatement();
                System.out.println(conn.getAutoCommit());
                String d =  Util.convJsonDateToString(db.cbsj,"yyyy-MM-dd hh:mm:ss");
                String cd =  Util.convJsonDateToString(db.cjsj,"yyyy-MM-dd hh:mm:ss");
                sql = " insert into " + TABLE_NAME + "  (rbbh,fmh,cbsj,bs,wbts,fmzt,cjsj,syrl,yh,grnd,jtbh,czsj,bz,yl,bcyl,yhbh,cjsj_char)" + "  values ('" + db.bh + "','" + db.fmh + "'," + "to_date('"+d+"','YYYY-MM-DD HH24:MI:SS')" + "," + db.bs + "," + db.wbts + ",'" + db.fmzt + "'," + "to_date('"+cd + "','YYYY-MM-DD HH24:MI:SS') ," + db.syrl + ",'" + yhbhId + "','" + db.dir + "','TJNYTZJT'," + "to_timestamp('"+cd+"','YYYY-MM-DD HH24:MI:SS')" + ",'" + 1 + "',null," + db.yl + ",'" + db.yhbh + "','"+db.cjsj+"')";
                System.out.println(sql);

                stat.execute(sql);
            }
        }catch (Exception e){
            throw  new Exception("插入数据出错 cjsj :"+db.cjsj +"yhbh:",e);
        } finally {
            //DBTransaction.close(stat, conn,rs);
        }

    }

    public Result addDatas(String dir, String file, List<DataBean> dblist) throws Exception {

        Result result = new Result();
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null ;
        DataBean db = null;
        try {
           result.successCount = dblist.size();
            conn = DBTransaction.getQueryModelConnection();
            conn.setAutoCommit(false);
            stat = conn.createStatement();
            Iterator<DataBean> dbIterator = dblist.iterator();

            stat = conn.createStatement();
            dbIterator = dblist.iterator();
            String sql = null;

            while(dbIterator.hasNext()) {

                db = dbIterator.next();
                String d = Util.convJsonDateToString(db.cbsj, "yyyy-MM-dd hh:mm:ss");
                String cd = Util.convJsonDateToString(db.cjsj, "yyyy-MM-dd hh:mm:ss");

                if (null == db.yhbh || "".equals(db.yhbh.trim())) {
                    result.failedCount ++;
                    result.successCount --;
                    edd.addData(db);
                    log.error("error-----------------------------> file :"+file +", yhbh"+db.yhbh +"，cjsj："+db.cjsj +",yhmc: "+db.yhmc);
                }
                //if(db.yhbh == null || db.yhbh=="" ||db.yhbh.trim().equals("")||db.yhbh.length() <1)
                else {
                    sql = " insert into " + TABLE_NAME + "  (rbbh,fmh,cbsj,bs,wbts,fmzt,cjsj,syrl,yh,grnd,jtbh,czsj,bz,yl,bcyl,yhbh,cjsj_char)" + "  values ('" + db.bh + "','" + db.fmh + "'," + "to_date('" + d + "','YYYY-MM-DD HH24:MI:SS')" + "," + db.bs + "," + db.wbts + ",'" + db.fmzt + "'," + "to_date('" + cd + "','YYYY-MM-DD HH24:MI:SS') ," + db.syrl + ",'" + db.yhbh+ "','" + db.dir + "','TJNYTZJT'," + "to_timestamp('" + cd + "','YYYY-MM-DD HH24:MI:SS')" + ",'" + 1 + "',null," + db.yl + ",'" + db.yhbh + "','" + db.cjsj + "')";
                    stat.addBatch(sql);
                }
            }
            stat.executeBatch();
            conn.commit();

        }catch (Exception e){
            System.out.println("插入数据出错 file :"+file);
            edd.addData(db);
            throw  new Exception(e);
        } finally {
            DBTransaction.close(stat, conn,rs);
        }
        return  result;
    }
}
