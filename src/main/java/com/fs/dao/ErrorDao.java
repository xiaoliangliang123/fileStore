package com.fs.dao;

import config.DBTransaction;
import config.Util;
import dto.DataBean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ErrorDao {

    public static final String TABLE_NAME = "fs_err_data";
    public static final String error_txt = "txt_name";
    public static final String cjsj = "fmh";
    public static final String yhbh = "yhbh";




    public void initSQLTable() {

        Connection conn = null;
        Statement stat = null;
        try {
            conn = DBTransaction.getQueryModelConnection();
            stat = conn.createStatement();
            String sql = " create table " + TABLE_NAME + " ( " +
                    " " + error_txt + " varchar(50) not NULL , " +
                    " " + cjsj + " varchar(30) ," +
                    " " + yhbh + " varchar(30) " +
                    " )";
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

    public void addErrorData(String file) {


        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null ;
        try {
            conn = DBTransaction.getQueryModelConnection();
            stat = conn.createStatement();
            String sql = " insert into " + TABLE_NAME + "(txt_name)" + "  values ('"+file+"')";
            stat.execute(sql);

        }catch (Exception e){
            e.printStackTrace();
        } finally {
            DBTransaction.close(stat, conn,rs);
        }
    }

    public void addErrorData(DataBean db) {
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null ;
        try {
            conn = DBTransaction.getQueryModelConnection();
            stat = conn.createStatement();
            String sql = " insert into " + TABLE_NAME + "(txt_name ,cjsj , yhbh)" + "  values ('"+db.getFile()+"','" + db.cjsj + "','" + db.yhbh + "')";
            stat.execute(sql);

        }catch (Exception e){
            e.printStackTrace();
        } finally {
            DBTransaction.close(stat, conn,rs);
        }

    }
}
