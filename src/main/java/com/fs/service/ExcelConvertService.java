package com.fs.service;

import com.fs.dao.ExcelDataDao;
import config.DBTransaction;
import config.excel.ExcelUtil;
import config.utl.Util;
import dto.DataBean;
import dto.ExcelDataBean;
import dto.TotalBean;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * @Auther: WANG_LIANG(XIAO)
 * @Date: 2018/6/28 09:57
 * @Description: excel 数据执行类
 */
public class ExcelConvertService  implements ConvertService{

    ExcelDataDao edd = new ExcelDataDao();

    public Result convertDatas(String dir, String file, List dblist) throws Exception {

        Connection conn =  DBTransaction.getQueryModelConnection();
        Result result = null;
        try {
            dblist = dblist.subList(0,1000);
            result = edd.addDatas(dir,file,dblist);
        }catch (Exception e){
            e.printStackTrace();
            throw  new Exception("更新异常",e);
        }finally {
            DBTransaction.close(conn);
        }
        return result;
    }



    public   TotalBean<ExcelDataBean> doFormat(Object object) {


        String path = (String)object;
        Workbook wb =null;
        Sheet sheet = null;
        Row row = null;
        List<Map<String,String>> list = null;
        String cellData = null;
        String filePath = path;
        String columns[] = { ExcelDataBean.YHBH,ExcelDataBean.YHDZ,ExcelDataBean.YHMC,ExcelDataBean.BYYYRL,ExcelDataBean.BJJYYRL,ExcelDataBean.JJFS,ExcelDataBean.CS};
        wb = ExcelUtil.readExcel(filePath);
        if(wb != null){
            //用来存放表中数据
            list = new ArrayList<Map<String,String>>();
            //获取第一个sheet


            int sheetNum =  wb.getNumberOfSheets();

            for(int i = 0 ; i <sheetNum ; i ++) {

                sheet = wb.getSheetAt(i);

                String name = sheet.getSheetName();
                name = Util.converToStringNumber(name);
                //获取最大行数
                int rownum = sheet.getPhysicalNumberOfRows();
                //获取第一行
                row = sheet.getRow(i);
                //获取最大列数
                int colnum = row.getPhysicalNumberOfCells();
                for (int x = 1; x < rownum; x++) {
                    Map<String, String> map = new LinkedHashMap<String, String>();
                    row = sheet.getRow(x);
                    if (row != null) {
                        for (int j = 0; j < colnum; j++) {
                            cellData = (String) ExcelUtil.getCellFormatValue(row.getCell(j));
                            map.put(columns[j], cellData);
                        }

                        map.put(ExcelDataBean.GRND,name);
                    } else {
                        break;
                    }
                    list.add(map);

                }
                System.out.println("sheet" + i+ "name " + name + "：解析完毕 ");

            }
            System.out.println("全部解析sheet完毕 数据共有 ："+list.size());
        }
        List<ExcelDataBean> excelDataBeans = listTobeans(list);
        TotalBean<ExcelDataBean> totalBean = new TotalBean<ExcelDataBean>();
        totalBean.setJls(excelDataBeans.size());
        totalBean.setDatalist(excelDataBeans);
        return totalBean;
    }

    private List<ExcelDataBean> listTobeans(List<Map<String,String>> list) {

        List<ExcelDataBean> excelDataBeans = new ArrayList<ExcelDataBean>();
        if(list.isEmpty())
            return new ArrayList<ExcelDataBean>();
        Iterator<Map<String,String>> lmapIterator = list.iterator();
        while (lmapIterator.hasNext()){
            ExcelDataBean excelDataBean = new ExcelDataBean();
            Map<String,String> map = lmapIterator.next();
            excelDataBean.setYhbh(map.get(ExcelDataBean.YHBH));
            excelDataBean.setYhmc(map.get(ExcelDataBean.YHMC));
            excelDataBean.setBjjyyrl(map.get(ExcelDataBean.BJJYYRL));
            excelDataBean.setBjjyyrl(map.get(ExcelDataBean.BYYYRL));
            excelDataBean.setCs(map.get(ExcelDataBean.CS));
            excelDataBean.setJjfs(map.get(ExcelDataBean.JJFS));
            excelDataBean.setYhdz(map.get(ExcelDataBean.YHDZ));
            excelDataBeans.add(excelDataBean);
        }
        return excelDataBeans;
    }

    public boolean checkFileFormatIsRight(String file) {
         return file.endsWith(".xlsx")||file.endsWith(".xls");
    }
}
