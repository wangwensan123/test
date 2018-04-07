package crawler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Excel组件
 * 
 * @author wangws
 * @version 1.0
 * @since 1.0
 */
public class ExcelHelper {

  /**
   * Excel 2003
   */
  private final static String XLS       = "xls";
  /**
   * Excel 2007
   */
  private final static String XLSX      = "xlsx";


  protected static Log        log       = LogFactory.getLog(ExcelHelper.class);

  /**
   * 获取导入数据批次号
   * 
   * @param file
   * @param sheetNum
   * @return
   */
  public static String getBatchNo() {
    String str = "";
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
    str = sdf.format(date);
    return str;
  }

  /**
   * 由Excel文件的Sheet导出至List
   * 
   * @param file
   * @param sheetNum
   * @return
   */
  public static List<String[]> exportListFromExcel(File file, int sheetNum) throws Exception {
    return exportListFromExcel(new FileInputStream(file), FilenameUtils.getExtension(file.getName()), sheetNum);
  }

  /**
   * 由Excel流的Sheet导出至List
   * 
   * @param is
   * @param extensionName
   * @param sheetNum
   * @return
   * @throws IOException
   */
  public static List<String[]> exportListFromExcel(InputStream is, String extensionName, int sheetNum) throws Exception {

    Workbook workbook = null;

    if (extensionName.toLowerCase().equals(XLS)) {
      workbook = new HSSFWorkbook(is);
    } else if (extensionName.toLowerCase().equals(XLSX)) {
      workbook = new XSSFWorkbook(is);
          }
    if (workbook != null) {
      return exportListFromExcel(workbook, sheetNum);
    } else {
      return new ArrayList<String[]>();
          }
  }

  /**
   * 由指定的Sheet解析至List，第一行不解析
   * 
   * @param workbook
   * @param sheetNum
   * @return
   * @throws IOException
   */
  private static List<String[]> exportListFromExcel(Workbook workbook, int sheetNum) {
    return exportListFromExcel(workbook, sheetNum, 1);
  }

  /**
   * 由指定的Sheet解析至List
   * 
   * @param workbook
   * @param sheetNum
   *          指定sheet页
   * @param startrow
   *          　指定开始行(0是第一行)
   * @param separator返回list字符串中的分隔符
   * @return
   * @throws IOException
   */
  private static List<String[]> exportListFromExcel(Workbook workbook, int sheetNum, int startrow) {

    Sheet sheet = workbook.getSheetAt(sheetNum);

    // 解析公式结果
    FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

    List<String[]> list = new ArrayList<String[]>();
    int minRowIx = sheet.getFirstRowNum() + startrow;
    int maxRowIx = sheet.getLastRowNum();
    for (int rowIx = minRowIx; rowIx <= maxRowIx; rowIx++) {
      try {
        Row row = sheet.getRow(rowIx);
        if (row == null) {
          continue;
                  }
//        short minColIx = row.getFirstCellNum();
        short maxColIx = row.getLastCellNum();
        if (maxColIx == -1) {
          continue;
                    }
        String[] array = new String[maxColIx];
        for (short colIx = 0; colIx <= maxColIx; colIx++) {
          Cell cell = row.getCell(new Integer(colIx));
          if (null == cell) {
            continue;
                        }
          boolean flag = true;
          if (0 == cell.getCellType()) {
                              // 判断是否为日期类型
            if (DateUtil.isCellDateFormatted(cell)) {
              flag = false;
                                  // 用于转化为日期格式
              Date d = cell.getDateCellValue();
              SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//              sb.append(separator + format.format(d));
              array[colIx]=format.format(d);
                            }
                        }
          if (flag) {
            CellValue cellValue = evaluator.evaluate(cell);
            if (cellValue == null) {
              if (colIx == 0) {
                break;
                                  }
              //sb.append(" "+separator);
              array[colIx]="";
              continue;
                            }
                            // 经过公式解析，最后只存在Boolean、Numeric和String三种数据类型，此外就是Error了
                            // 其余数据类型，根据官方文档，完全可以忽略http://poi.apache.org/spreadsheet/eval.html
            log.debug("colIx=" + colIx);
            log.debug("colIx=" + cellValue.getCellType());
            switch (cellValue.getCellType()) {
              case Cell.CELL_TYPE_BOOLEAN:
//                sb.append(cellValue.getBooleanValue()+separator);
                array[colIx]=cellValue.getBooleanValue()+"";
                break;
              case Cell.CELL_TYPE_NUMERIC:
                                      // 这里的日期类型会被转换为数字类型，需要判别后区分处理
//                sb.append(cellValue.getNumberValue()+separator);
                array[colIx]=cellValue.getNumberValue()+"";
                break;
              case Cell.CELL_TYPE_STRING:
//                sb.append(cellValue.getStringValue().trim()+separator);
                array[colIx]=cellValue.getStringValue().trim();
                break;
              case Cell.CELL_TYPE_FORMULA:
                break;
              case Cell.CELL_TYPE_BLANK:
                break;
              case Cell.CELL_TYPE_ERROR:
                break;
              default:
                          }

          }

        }

        list.add(array);
      } catch (Exception e) {
        e.printStackTrace();
            }

    }
    return list;
  }

  /**
   *  将List数据导出至Excel文件的Sheet
   * 
   * @param File
   * @param header
   * @param datas
   * @param height
   * @param width
   * @return
   * @throws IOException
   */
  public static void exportExcelFromList(File f,List<String> header,List<String[]> datas,String sheetName,int height,int width){

    if (f.exists()) {
        f.delete();
    } else {
        f.getParentFile().mkdirs();
            }    
    PrintStream out = null;
    try {
        out = new PrintStream(new FileOutputStream(f), true, "utf-8");
                      // 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
                      // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet(sheetName);
        
                      // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        HSSFRow row = sheet.createRow((int) 0);
        row.setHeight((short)height);
        HSSFCell cell = row.createCell((short) 0);
        row.setHeight((short)height);
        HSSFCellStyle style1 = wb.createCellStyle();
        style1.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style1.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style1.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式 
        style1.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.index);
        style1.setFillPattern(CellStyle.SOLID_FOREGROUND);

        for(int i=0;i<header.size();i++){
          cell.setCellValue(header.get(i));  
          cell.setCellStyle(style1);  
          cell = row.createCell((short) i+1);
          sheet.setColumnWidth(i, width); 
                      }
        HSSFCellStyle style2 = wb.createCellStyle();
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        for (int i=0;i<datas.size();i++) {
          String[] rowdata = datas.get(i);
          row = sheet.createRow((int) i + 1);
          row.setHeight((short)400);
          int j = 0;
          for (String cellstr : rowdata) {                      
              cell = row.createCell((short) j);
              if(cellstr==null){
                cellstr="";
                                  }
              cell.setCellValue(cellstr);
              cell.setCellStyle(style2);
              j++;
                          }
                      }
         wb.write(out);  
    } catch (IOException e) {
        log.error("", e);
    } finally {
        if (out != null) {
            out.close();
                    }
          }
  
    
    
  }
  
  /**
   *  将List数据导出至Excel文件的Sheet
   * 
   * @param File
   * @param header
   * @param datas
   * @return
   * @throws IOException
   */
  public static void exportExcelFromList(File f,List<String> header,List<String[]> datas,String sheetName){
    exportExcelFromList(f,header,datas,sheetName,400,3766);
  }
  
  
}