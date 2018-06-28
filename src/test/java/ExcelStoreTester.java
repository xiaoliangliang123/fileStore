import config.excel.ExcelUtil;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @Auther: WANG_LIANG(XIAO)
 * @Date: 2018/6/28 12:34
 * @Description:
 */
public class ExcelStoreTester {


    public void testReadExcelStore() throws IOException {
        File file = new File("D:\\guide.csv");
        String[][] result = ExcelUtil.getData(file, 1);
        int rowLength = result.length;
        for (int i = 0; i < rowLength; i++) {
            System.out.println(result[i]);
            for (int j = 0; j < result[i].length; j++) {
                System.out.print(result[i][j] + "\t\t");
            }
            System.out.println();
        }

    }

}
