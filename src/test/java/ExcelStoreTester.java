import com.fs.service.ConvertService;
import com.fs.service.ExcelConvertService;
import com.fs.service.Result;
import com.sun.javafx.collections.MappingChange;
import config.QueryModelConfig;
import config.excel.ExcelUtil;
import config.utl.Util;
import dto.ExcelDataBean;
import dto.TotalBean;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.io.File;
import  java.util.*;
import java.util.Map;
import java.util.Map.Entry;
import java.io.IOException;

/**
 * @Auther: WANG_LIANG(XIAO)
 * @Date: 2018/6/28 12:34
 * @Description:
 */
public class ExcelStoreTester {


    private static Logger log = Logger.getLogger(ExcelStoreTester.class.getClass());

    /*
      执行excel文件类型数据处理
      */



    public void testLog(){
        String filePath = QueryModelConfig.class.getClassLoader()
                .getResource("logger.properties").getPath();
        PropertyConfigurator.configure( filePath );


    }

    @Test
    public void executeDirExcels(){
        testLog();

        ConvertService dcs = new ExcelConvertService();
        File file = new File("E:\\data\\excels");
        File[] files =  file.listFiles();
        Result r = new Result();

        for(File f:files){

            try {
                if(!dcs.checkFileFormatIsRight(f.getName()))
                     continue;
                log.info("开始执行"+f.getPath() +" ,开始时间"+ Util.getCurrentTimeOfYYYYMMDDHHMMSS());
                Long  start = System.currentTimeMillis();
                TotalBean<ExcelDataBean> totalBean = dcs.doFormat(f.getPath());
                List<ExcelDataBean> dblist = totalBean.getDatalist();
                if(dblist.isEmpty())
                    break ;
                String  name = Util.getFileNameNoPrefix(f.getName());
                Result result = dcs.convertDatas(name,f.getPath(),dblist);
                r.totalCount = r.totalCount + dblist.size();
                r.successCount = r.successCount + result.successCount;
                r.failedCount = r.failedCount + result.failedCount;
                f.delete();
                Long  end = System.currentTimeMillis();
                Long useTime = end - start;
                log.info(f.getPath()+"执行完成,该文件已删除，共 "+ dblist.size() +"条"+"，成功："+result.successCount +"条，失败"+result.failedCount+"条");
                log.info("本次用时"+useTime +" ,结束时间："+Util.getCurrentTimeOfYYYYMMDDHHMMSS());
            }catch (Exception e){
                e.printStackTrace();
                log.error("文件" +f.getPath()+"执行出错  ,本文件终止执行，继续执行下一个文件 ，错误时间："+Util.getCurrentTimeOfYYYYMMDDHHMMSS());

            }



        }
    }

}
