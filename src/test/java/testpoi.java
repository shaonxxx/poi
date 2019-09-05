import com.woniu.serviceImpl.PoiServiceImpl;
import com.woniu.serviceImpl.WritePoi;
import org.junit.Test;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class testpoi {


    @Test
    public void testpoi01() throws IOException {
        File file=new File("E:\\web\\workspace\\poi\\poi.xls");
        FileInputStream fileInputStream=new FileInputStream(file);

         List<String[]> list=new  PoiServiceImpl().parsExcel(fileInputStream,"xls",1);

         for(String[] strings:list){
             System.out.println(Arrays.toString(strings));
         }

        System.out.println("======================================");

        WritePoi writePoi=new WritePoi();
        FileOutputStream fileOutputStream=new FileOutputStream("E:\\web\\workspace\\poi\\write.xls");
        writePoi.parsExcel(fileOutputStream);
    }
}
