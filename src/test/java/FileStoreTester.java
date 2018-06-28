import com.fs.dao.DataDao;
import com.fs.dao.ErrorDao;
import com.fs.dao.ErrorDataDao;
import com.fs.service.ConvertService;
import com.fs.service.ExcelConvertService;
import com.fs.service.FileDataConvertService;
import com.fs.service.Result;
import config.QueryModelConfig;
import config.utl.Util;
import dto.DataBean;
import dto.ExcelDataBean;
import dto.TotalBean;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;

import java.io.File;
import java.sql.SQLException;
import java.util.List;


public class FileStoreTester {

    private static Logger log = Logger.getLogger(FileStoreTester.class.getClass());


    public void testCreateTable(){

        DataDao dd = new DataDao();
        dd.initSQLTable();

    }



    public void testLog(){
        String filePath = QueryModelConfig.class.getClassLoader()
                .getResource("logger.properties").getPath();
        PropertyConfigurator.configure( filePath );


    }


    public void testQuery(){

        DataDao dd = new DataDao();
        dd.queryCount();

    }


    public void testDateConvert(){

         String name = Util.converToStringNumber("2012年4月");
         System.out.println(name);

    }


    public void testGetFileNameNoPrefix(){

        String name = Util.getFileNameNoPrefix("2012-2018.xlsx");
        System.out.println(name);

    }


    public void testInitErrorDataDao(){

        ErrorDataDao dd = new ErrorDataDao();
        dd.initSQLTable();

    }

    public void testCreateErrorTable(){

        ErrorDao ed = new ErrorDao();
        ed.initSQLTable();

    }




    /*
    执行txt文件类型数据处理
    */

    public void executeDirFiles() throws SQLException, ClassNotFoundException {

        ConvertService dcs = new FileDataConvertService();
        File file = new File("E:\\data\\2016-2017\\2016-2017");
        File[] files =  file.listFiles();
        Result r = new Result();

        for(File f:files){

            try {

                if(!dcs.checkFileFormatIsRight(f.getName()))
                    throw  new Exception("文件格式不正确");
                log.info("开始执行"+f.getPath() +",开始时间"+ Util.getCurrentTimeOfYYYYMMDDHHMMSS());
                Long  start = System.currentTimeMillis();
                String json =  Util.readFile(f.getPath());
                TotalBean<DataBean> totalBean = dcs.doFormat(json);
                List<DataBean> dblist = totalBean.getDatalist();
                if(dblist.isEmpty())
                    break ;
                Result result = dcs.convertDatas(f.getParentFile().getName(),f.getPath(),dblist);
                r.totalCount = r.totalCount + dblist.size();
                r.successCount = r.successCount + result.successCount;
                r.failedCount = r.failedCount + result.failedCount;
                f.delete();
                Long  end = System.currentTimeMillis();
                Long useTime = end - start;
                log.info(f.getPath()+"执行完成,该文件已删除，共 "+ dblist.size() +"条"+"，成功："+result.successCount +"条，失败"+result.failedCount+"条");
                log.info("本次用时"+useTime +" ,结束时间："+Util.getCurrentTimeOfYYYYMMDDHHMMSS());
            }catch (Exception e){
                log.error("文件" +f.getPath()+"执行出错  ,本文件终止执行，继续执行下一个文件 ，错误时间："+Util.getCurrentTimeOfYYYYMMDDHHMMSS());
                log.error("异常信息"+e.getMessage());
            }

        }
        log.info( "全部执行完成，共 "+ r.totalCount +"条"+"，成功："+r.successCount +"条，失败"+r.failedCount+"条");

    }


}
