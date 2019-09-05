package com.woniu.serviceImpl;


import jdk.internal.util.xml.impl.Input;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PoiServiceImpl {
    /**
     * 解析文件的方法
     * @param inputStream 输入流
     * @param suffix 文件后缀名
     * @param starRow 第几行开始读取数据
     * @return List<Stringp[]> 集合中的一个元素对应一行解析的数据。
     */
    public List<String[]> parsExcel(InputStream inputStream,String suffix,int starRow) throws IOException {

        Workbook workbook=null;
        if(suffix.equals("xls")){
            //2003
            workbook=new HSSFWorkbook(inputStream);
        }else{
            return null;
        }
        //获取工作表 Excel分为若干个表。sheet
        Sheet sheetAt = workbook.getSheetAt(0);

        if(sheetAt == null){
            return null;
        }
        //获取最后一行行号
        int lastRow=sheetAt.getLastRowNum();
        //最后一行行号大于starRow
        if(lastRow<=starRow){
            return null;
        }
        Row row=null;
        Cell cell=null;
        List<String[]> list=new ArrayList<String[]>();
        //读取数据
        for(int rowNum=starRow;rowNum<=lastRow;rowNum++){
            //标记第一行与最后一行
            row=sheetAt.getRow(rowNum);
            short firstCellNum=row.getFirstCellNum();
            short lastCellNum=row.getLastCellNum();
            if(lastCellNum!=0){
                String[] rowArray=new String[lastCellNum];
                //写入每一行数据
                for(int cellNum=firstCellNum;cellNum<lastCellNum;cellNum++){
                    cell=row.getCell(cellNum);
                     if(cell == null){
                         rowArray[cellNum]=null;
                     }else{
                         rowArray[cellNum]=parseCell(cell);
                     }
                }
                list.add(rowArray);
            }

        }


        return list;
    }

    private String parseCell(Cell cell) {
        String cellStr=null;
        CellStyle cellStyle= cell.getCellStyle();
        switch (cell.getCellTypeEnum()){
            case STRING:
                //字符串数据
                cellStr=cell.getRichStringCellValue().toString();

                break;
            case BLANK:
                //空数据
                cellStr="";
                break;
            case NUMERIC:
                //数字类型,包含日期，时间，数字

                if(HSSFDateUtil.isCellDateFormatted(cell)){
                    SimpleDateFormat simpleDateFormat=null;
                    if(cell.getCellType() == HSSFDataFormat.getBuiltinFormat("h:mm")){
                        //时间
                        simpleDateFormat = new SimpleDateFormat("HH:mm");
                    }else{
                        //日期
                        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    }
                    Date temp=cell.getDateCellValue();
                     cellStr=simpleDateFormat.format(temp);
                }else {
                    double d=cell.getNumericCellValue();
                     cellStr = Double.toString(d);
                }
                break;
            default:
                cellStr = "";
                break;
        }
        return cellStr;

    }


}
