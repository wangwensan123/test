package crawler;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import json.JSON;

import com.alibaba.fastjson.JSONObject;

/**
 *@auth wws
 *@date 2018年4月5日---下午11:40:11
 *https://blog.csdn.net/xp5xp6/article/details/53121481
 **/
public class CrawlerTest {
  
      //public static String URL1 = "http://q.stock.sohu.com/hisHq?code=cn_300228&start=20180404&end=20180404&stat=1&order=D&period=d&callback=historySearchHandler&rt=jsonp";
      //public static String URL = "http://money.finance.sina.com.cn/corp/go.php/vMS_MarketHistory/stockid/601006.phtml?year=2018&jidu=2";
      public static String URL2 ="http://cq.ssajax.cn/interact/getTradedata.ashx?pic=qlpic_600963_1_6";
      public static String URL3 ="http://img1.money.126.net/data/hs/kline/day/history/2018/0600963.json";
      public static String URL4 ="http://img1.money.126.net/data/hs/kline/day/history/2018/1000001.json";
      public static String URL5 ="http://img1.money.126.net/data/hs/kline/day/history/2018/1300202.json";
      
      public static String TYPE_6 = "6";
      public static String TYPE_0 = "0";
      public static String TYPE_3 = "3";
  
    public static void main(String[] args) throws Exception {
    
//      insertDataByYear(TYPE_3,"2017");
      //copytable();
      //truncatetable();

          }
    
    public static void copytable(){
      String sql = "INSERT INTO stock_info_date (code,date,open,close,high,low,volume,rate)"
          + " SELECT code,date,open,close,high,low,volume,rate FROM stock_info_date_temp";
      JdbcUtils.excute(sql);
      System.out.println("----finish----copytable");
              }
    public static void truncatetable(){
      String sql = "TRUNCATE TABLE stock_info_date_temp";
      JdbcUtils.excute(sql);
      System.out.println("----finish----truncatetable");
          }
    
    public static void insertDataByYear(String type,String year) throws InterruptedException, IOException{

      String sql = "select * from stock_info WHERE  code LIKE \""+type+"%\" ";
      List<Map<String, Object>> list = JdbcUtils.read(sql);
      for(Map<String, Object> map:list){
                //1.拼接接口url
       String url = "http://img1.money.126.net/data/hs/kline/day/history/";
       url += year+"/";
       if(type.equals(TYPE_6)){
         url += "0";
       }else{
         url += "1";
                 }
       url += map.get("code").toString();
       url += ".json";
                 //2.调用接口返回json数组
       String jsonstr = HTTPUtils.getRawHtml(url);
                 //3.解析数据并插入数据库
       JSON json = JSON.fromObject(jsonstr);
       if(null!=json){
         String code = json.getString("symbol");
         List data = json.getList("data");          
         if(null!=data&&data.size()>0){
           String insertsql = "INSERT INTO  stock_info_date_temp";
           insertsql+="(code,date,open,close,high,low,volume,rate)VALUE";
           insertsql+="(?,?,?,?,?,?,?,?)";
           JdbcUtils.insertBatch2(insertsql,code,data);
                         }
       }else{
                     //没有数据
         System.out.println(url);
                   }

      
               }
      System.out.println("----finish----insertDataByYear:"+year);
}
    
    public static void insert03Date() throws InterruptedException, IOException{

      String sql = "select * from stock_info WHERE  code LIKE \"3%\" OR  code LIKE \"0%\" ";
      List<Map<String, Object>> list = JdbcUtils.read(sql);
      for(Map<String, Object> map:list){
       String url = "http://img1.money.126.net/data/hs/kline/day/history/2018/1";
       url += map.get("code").toString();
       url += ".json";
//       System.out.println(url);
       String jsonstr = HTTPUtils.getRawHtml(url);
//       System.out.println(jsonstr);
      
       JSON json = JSON.fromObject(jsonstr);
       if(null!=json){
         String code = json.getString("symbol");
         List data = json.getList("data");          
         if(null!=data&&data.size()>0){
           String insertsql = "INSERT INTO  stock_info_date";
           insertsql+="(code,date,open,close,high,low,volume,rate)VALUE";
           insertsql+="(?,?,?,?,?,?,?,?)";
           JdbcUtils.insertBatch2(insertsql,code,data);
//           System.out.println("finish:"+code);
                         }
       }else{
         System.out.println(url);
                   }

      
               }

}
    
    public static void insert6Date() throws InterruptedException, IOException{
        //    String jsonstr = "{" 
        //    + "\"data\": [" 
        //                                                    + "[\"20180102\", 17.82, 18.01, 18.05, 17.72, 1756506, 0.33],"
        //                                                    + "[\"20180103\", 18.01, 18.09, 18.23, 17.9, 1756682, 0.44]" 
        //                                                    + "],"
        //    + "\"symbol\": \"300202\","
        //    + "\"name\": \"聚龙股份\"" + "}";
        
        //String jsonstr = HTTPUtils.getRawHtml(URL5);
        //System.out.println(jsonstr);
        //JSON json = JSON.fromObject(jsonstr);
        //String code = json.getString("symbol");
        //List data = json.getList("data");
        String sql = "select * from stock_info WHERE  code > \"600083\"";
        List<Map<String, Object>> list = JdbcUtils.read(sql);
        for(Map<String, Object> map:list){
         String url = "http://img1.money.126.net/data/hs/kline/day/history/2018/0";
         url += map.get("code").toString();
         url += ".json";
//         System.out.println(url);
         String jsonstr = HTTPUtils.getRawHtml(url);
//         System.out.println(jsonstr);
        
         JSON json = JSON.fromObject(jsonstr);
         if(null!=json){
           String code = json.getString("symbol");
           List data = json.getList("data");          
           if(null!=data&&data.size()>0){
             String insertsql = "INSERT INTO  stock_info_date";
             insertsql+="(code,date,open,close,high,low,volume,rate)VALUE";
             insertsql+="(?,?,?,?,?,?,?,?)";
             JdbcUtils.insertBatch2(insertsql,code,data);
//             System.out.println("finish:"+code);
                           }
         }else{
           System.out.println(url);
                     }

        
                 }

}
  
    
    public static void insertExcelListA() throws Exception{
      
        List<String[]> list = ExcelHelper.exportListFromExcel(new File("/home/jack/A11.xlsx"),0);
        String sql = "INSERT INTO  stock_info";
        sql+="(code,abbreviation,a_code,a_abbreviation,list_date,";
        sql+="all_equity,circulation_equity)VALUE";
        sql+="(?,?,?,?,?,?,?)";
        JdbcUtils.insertBatch1(sql,list);
   }
    
    
    public static void insertExcelList() throws Exception{
      
       List<String[]> list = ExcelHelper.exportListFromExcel(new File("/home/jack/A股列表.xlsx"),0);
       String sql = "INSERT INTO  stock_info";
       sql+="(code,abbreviation,fullname,englishname,address,a_code,a_abbreviation,list_date,";
       sql+="all_equity,circulation_equity,industry,website)VALUE";
       sql+="(?,?,?,?,?,?,?,?,?,?,?,?)";
       JdbcUtils.insertBatch(sql,list);
    }

    
    
  
    public static void insert(String[] array) throws UnsupportedEncodingException{
//          String sql = "INSERT INTO  stock_info";
//          sql+="(code,abbreviation,fullname,englishname,address,a_code,a_abbreviation,list_date,";
//          sql+="all_equity,circulation_equity,industry,website)VALUE";
//          sql+="(";
////                        ("000001","平安银行","平安银行股份有限公司","Ping An Bank Co., Ltd",
////                            "广东省深圳市罗湖区深南东路5047号","000001","平安银行","1991-04-03",
////                            17170411366,16918003290 ,"金融业","www.bank.pingan.com");";
//          sql+="\""+array[0]+"\",";
//          sql+="\""+array[1]+"\",";
//          sql+="\""+array[2]+"\",";
//          sql+="\""+array[3]+"\",";
//          sql+="\""+array[4]+"\",";
//          sql+="\""+array[5]+"\",";
//          sql+="\""+array[6]+"\",";
//          sql+="\""+array[7]+"\",";
//          sql+="\""+array[8]+"\",";
//          sql+="\""+array[9]+"\",";
//          sql+="\""+array[18]+"\",";
//          sql+="\""+array[19]+"\"";
//          sql+=")";
//          JdbcUtils.insert(sql);

          
      
    }


}
