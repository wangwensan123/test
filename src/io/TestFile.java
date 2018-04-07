package io;
/**
 *@auth wws
 *@date 2018年3月27日---上午10:59:57
 *
 **/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;  
import java.io.FileOutputStream;  
import java.io.InputStreamReader;
public class TestFile {  
    public static void main(String args[]) throws IOException {  
        try {  
            System.out.println("please Input from      Keyboard");  
            int count, n = 512;  
            byte buffer[] = new byte[n];  
            count = System.in.read(buffer);  
            FileOutputStream wf = new FileOutputStream("d:/myjava/write.txt");  
            wf.write(buffer, 0, count);  
            wf.close(); // 当流写操作结束时，调用close方法关闭流。   
            System.out.println("Save to the write.txt");  
        } catch (IOException IOe) {  
            System.out.println("File Write Error!");  
        }  
    }  
    
    public static void readWriteToFile() throws IOException {  
      InputStreamReader sin = new InputStreamReader(System.in);  
      BufferedReader bin = new BufferedReader(sin);  
      FileWriter out = new FileWriter("myfile.txt");  
      BufferedWriter bout = new BufferedWriter(out);  
      String s;  
      while ((s = bin.readLine()).length() > 0) {  
          bout.write(s, 0, s.length());  
      }  

  }  
    
    public static void readFile() throws IOException {  
      try {  
          File inFile = new File("copy.java");  
          File outFile = new File("copy2.java");  
          FileInputStream finS = new FileInputStream(inFile);  
          FileOutputStream foutS = new FileOutputStream(outFile);  
          int c;  
          while ((c = finS.read()) != -1) {  
              foutS.write(c);  
                        }  
          finS.close();  
          foutS.close();  
      } catch (IOException e) {  
          System.err.println("FileStreamsTest: " + e);  
      }  
  }  

} 
