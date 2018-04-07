package io;
/**
 *@auth wws
 *@date 2018年3月27日---上午10:49:20
 ＊1. File（文件特征与管理）：用于文件或者目录的描述信息，例如生成新目录，修改文件名，删除文件，判断文件所在路径等。
 ＊2. InputStream（二进制格式操作）：抽象类，基于字节的输入操作，是所有输入流的父类。定义了所有输入流都具有的共同特征。
 ＊3. OutputStream（二进制格式操作）：抽象类。基于字节的输出操作。是所有输出流的父类。定义了所有输出流都具有的共同特征。
 ＊Java中字符是采用Unicode标准，一个字符是16位，即一个字符使用两个字节来表示。为此，JAVA中引入了处理字符的流。
 ＊4. Reader（文件格式操作）：抽象类，基于字符的输入操作。
 ＊5. Writer（文件格式操作）：抽象类，基于字符的输出操作。
 ＊6. RandomAccessFile（随机文件操作）：它的功能丰富，可以从文件的任意位置进行存取（输入输出）操作。
 ＊－－－－－－－－－－－－－－－－－－－－－－
 ＊1、File（文件）： FileInputStream, FileOutputStream, FileReader, FileWriter 
  ＊2、byte[]：ByteArrayInputStream, ByteArrayOutputStream 
  ＊3、Char[]: CharArrayReader, CharArrayWriter 
  ＊4、String: StringBufferInputStream, StringReader, StringWriter 
  ＊5、网络数据流：InputStream, OutputStream, Reader, Writer 
 **/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;  
import java.io.FileOutputStream;  
import java.io.InputStreamReader;
import java.io.Reader;

public class IoUtil {  
    public static void main(String args[]) throws IOException {
      try {
        Class.forName("");
      } catch (ClassNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
//      readbyReader1();
      
    }  
    
    public static void readWriteToFile() throws IOException {
      FileInputStream finS = new FileInputStream(new File("/home/jack/test.txt")); 
      InputStreamReader sin = new InputStreamReader(finS);  
      BufferedReader bin = new BufferedReader(sin);  
      FileWriter out = new FileWriter("/home/jack/myfile.txt");  
      BufferedWriter bout = new BufferedWriter(out);  
      String s;  
      while ((s = bin.readLine()) != null) {  
          bout.write(s, 0, s.length());  
              } 
      bin.close();
      bout.close();
  }  
    
    public static void readbyReader() throws IOException {  
      File file = new File("/home/jack/test.txt");
      try {
        Reader reader = new FileReader(file);
        BufferedReader buffered = new BufferedReader(reader);
        String data = null;
        while ((data = buffered.readLine()) != null) {
          System.out.println(data);
          System.out.println("---------");
                  }
        buffered.close();
        reader.close();
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
              }
      } 
    
    public static void readbyReader1() throws IOException {  
      File file = new File("/home/jack/test.txt");
      try {
        Reader reader = new FileReader(file);
        char [] buf = new char[50];
        int len = 0;
        while ((len = reader.read(buf)) > -1) {
          System.out.println(new String(buf,0,len));
          System.out.println("---------");
                  }
        reader.close();
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
              }
      } 
    
    public static void readbyInputStream() throws IOException {  
      try {  
          File inFile = new File("/home/jack/test.txt");  
          FileInputStream finS = new FileInputStream(inFile);
          int len = 0;
          byte[] buf = new byte[1024];  
          while((len=finS.read(buf)) != -1){  
              System.out.println(new String(buf,0,len));
              System.out.println("---------");
                        }  
          finS.close();  
      } catch (IOException e) {  
        e.printStackTrace(); 
            }  
  } 
    
    public static void copyFile() throws IOException {  
      try {  
          File inFile = new File("/home/jack/test.txt");  
          File outFile = new File("/home/jack/myfile.txt");  
          FileInputStream finS = new FileInputStream(inFile);  
          FileOutputStream foutS = new FileOutputStream(outFile);
          int len = 0;
          byte[] buf = new byte[1024];
          while ((len=finS.read(buf)) != -1) {  
              foutS.write(buf,0,len);
                        }  
          finS.close();  
          foutS.close();  
      } catch (IOException e) {  
          System.err.println("FileStreamsTest: " + e);  
      }  
  }  

} 
