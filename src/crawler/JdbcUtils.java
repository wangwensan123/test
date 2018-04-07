package crawler;
/**
 *@auth wws
 *@date 2018年4月6日---下午6:27:09
 *
 **/
import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import json.JSON;

import com.google.gson.JsonArray;




public class JdbcUtils {
   
    public static void main(String[] args){
      
    }
    
    
    // 通过结果集元数据封装List结果集
    public static List<Map<String, Object>> read(String sql) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtils.getconnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = ps.getMetaData();

            // 取得结果集列数
            int columnCount = rsmd.getColumnCount();

            // 构造泛型结果集
            List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
            Map<String, Object> data = null;

            // 循环结果集
            while (rs.next()) {
                data = new HashMap<String, Object>();
                // 每循环一条将列名和列值存入Map
                for (int i = 1; i < columnCount; i++) {
                    data.put(rsmd.getColumnLabel(i), rs.getObject(rsmd
                            .getColumnLabel(i)));
                }
                // 将整条数据的Map存入到List中
                datas.add(data);
            }
            return datas;
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
          JdbcUtils.free(rs, ps, conn);
        }
    }
    
    public static void query(String sql){

      Connection conn = JdbcUtils.getconnection();
      try {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while(rs.next()){
          String code  = rs.getString(1);
                   }
        st.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }finally{
        try {
          conn.close();
        } catch (SQLException e) {
          e.printStackTrace();
                    }
            }
  }
    
    public static void excute(String sql){

      Connection conn = JdbcUtils.getconnection();
      try {
        Statement st = conn.createStatement();
        st.execute(sql);
        st.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }finally{
        try {
          conn.close();
        } catch (SQLException e) {
          e.printStackTrace();
                    }
            }
  }
    
    public static void insertBatch2(String sql,String code,List<ArrayList> list){

      Connection conn = JdbcUtils.getconnection();
      try {
        conn.setAutoCommit(false); 
        PreparedStatement prest = conn.prepareStatement(sql);
        for(int i = 0; i < list.size();i++){
          ArrayList array = (ArrayList)list.get(i);
          prest.setString(1,code);   
          prest.setString(2,array.get(0).toString()); 
          prest.setBigDecimal(3,new BigDecimal(array.get(1).toString()));
          prest.setBigDecimal(4,new BigDecimal(array.get(2).toString()));
          prest.setBigDecimal(5,new BigDecimal(array.get(3).toString()));
          prest.setBigDecimal(6,new BigDecimal(array.get(4).toString()));
          prest.setBigDecimal(7,new BigDecimal(array.get(5).toString()));
          prest.setBigDecimal(8,new BigDecimal(array.get(6).toString()));
          prest.addBatch();   
                  } 
        prest.executeBatch();   
        conn.commit();   
        conn.close();  
      } catch (SQLException e) {
        e.printStackTrace();
      }finally{
        try {
          conn.close();
        } catch (SQLException e) {
          e.printStackTrace();
                    }
            }
  }
    
    public static void insertBatch1(String sql,List<String[]> list){

      Connection conn = JdbcUtils.getconnection();
      try {
        conn.setAutoCommit(false); 
        PreparedStatement prest = conn.prepareStatement(sql);
        for(int i = 0; i < list.size();i++){
          String[] array = list.get(i);
          prest.setString(1,new BigDecimal(array[0]).longValue()+"");   
          prest.setString(2,array[1]); 
          prest.setString(3,new BigDecimal(array[2]).longValue()+""); 
          prest.setString(4,array[3]); 
          prest.setString(5,array[4]);
          prest.setString(6,array[5]); 
          prest.setString(7,array[6]); 
          prest.addBatch();   
                  } 
        prest.executeBatch();   
        conn.commit();   
        conn.close();  
      } catch (SQLException e) {
        e.printStackTrace();
      }finally{
        try {
          conn.close();
        } catch (SQLException e) {
          e.printStackTrace();
                    }
            }
  }
    
    public static void insertBatch(String sql,List<String[]> list){

      Connection conn = JdbcUtils.getconnection();
      try {
        conn.setAutoCommit(false); 
        PreparedStatement prest = conn.prepareStatement(sql);
        for(int i = 0; i < list.size();i++){
          String[] array = list.get(i);
          prest.setString(1,array[0]);   
          prest.setString(2,array[1]); 
          prest.setString(3,array[2]); 
          prest.setString(4,array[3]); 
          prest.setString(5,array[4]);
          prest.setString(6,array[5]); 
          prest.setString(7,array[6]); 
          prest.setString(8,array[7]); 
          prest.setString(9,array[8]); 
          prest.setString(10,array[9]); 
          prest.setString(11,array[18]); 
          prest.setString(12,array[19]); 
          prest.addBatch();   
                  } 
        prest.executeBatch();   
        conn.commit();   
        conn.close();  
      } catch (SQLException e) {
        e.printStackTrace();
      }finally{
        try {
          conn.close();
        } catch (SQLException e) {
          e.printStackTrace();
                    }
            }
  }
    
    public static Connection getconnection(){
      String URL="jdbc:mysql://127.0.0.1:3306/wws?useUnicode=true&amp;characterEncoding=utf-8";
      String USER="root";
      String PASSWORD="123456";
            //1.加载驱动程序
      try {
        Class.forName("com.mysql.jdbc.Driver");
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
              }
              //2.获得数据库链接
      Connection conn = null;
      try {
        conn = DriverManager.getConnection(URL, USER, PASSWORD);
      } catch (SQLException e) {
        e.printStackTrace();
              }
      return conn;
    }
    
    // 释放连接
    public static void free(ResultSet rs, Statement st, Connection conn) {
        try {
            if (rs != null) {
                rs.close(); // 关闭结果集
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null) {
                    st.close(); // 关闭Statement
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (conn != null) {
                        conn.close(); // 关闭连接
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

        }

    }
}
