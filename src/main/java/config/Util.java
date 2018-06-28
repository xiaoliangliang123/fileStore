package config;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @Auther: WANG_LIANG(XIAO)
 * @Date: 2018/6/27 08:21
 * @Description:
 */
public class Util {

    public static String readFile(String Path){
        BufferedReader reader = null;
        String laststr = "";
        try{
            FileInputStream fileInputStream = new FileInputStream(Path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "GBK");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while((tempString = reader.readLine()) != null){
                laststr += tempString;
            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return laststr;
    }

    public static Calendar convToCalender(String str,String template){

        SimpleDateFormat sdf;
        Date date;
        Calendar cltResult = Calendar.getInstance();

        sdf = new SimpleDateFormat(template, Locale.getDefault());
        try {
            date = sdf.parse(str);

            cltResult.setTime(date);

        } catch (Exception ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
        }

        return cltResult;
    }

    // 把日期转指定格式字符串
    public static String convToString(Calendar cld,String template){
        String resultString=null;
        try {
            Date date=cld.getTime();
            SimpleDateFormat sdf=new SimpleDateFormat(template,Locale.getDefault());
            resultString=sdf.format(date);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return resultString;
    }

    public static String convJsonDateToString(String jsonDate,String stringTemplate){

        String resultString=null;
        try {
            Calendar cldCalendar=convToCalender(jsonDate, "yyyyMMddhhmmss");
            resultString=convToString(cldCalendar, stringTemplate);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return resultString;
    }

    public static String convertStringDataToFormat(String text) throws ParseException {
        SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
        return sdf.parse(text).toString();
    }

    public static String getCurrentTimeOfYYYYMMDDHHMMSS() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return df.format(new Date());
    }
}
