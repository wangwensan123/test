package nio.reactor;
/**
 *@auth wws
 *@date 2018年3月24日---下午4:57:32
 *https://blog.csdn.net/yehjordan/article/details/51026045
 **/
import java.io.IOException;  

public class Main {  
  
      
    public static void main(String[] args) {  
        // TODO Auto-generated method stub  
        try {  
            TCPReactor reactor = new TCPReactor(1333);  
            new Thread(reactor).start();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
    }  
  
}  
