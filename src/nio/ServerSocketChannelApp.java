package nio;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 *@auth wws
 *@date 2018年3月22日---下午3:44:20
 *
 **/
public class ServerSocketChannelApp {

  public static void main(String[] args) throws Exception {  
  
      int port = 8989;  
      ServerSocketChannel ssc = ServerSocketChannel.open();  
      ServerSocket ss = ssc.socket();  
      ss.bind(new InetSocketAddress(port));  
      // set no blocking  
      ssc.configureBlocking(false);  
  
      while (true) {  
          System.out.println("wait for connection ……");  
          SocketChannel sc = ssc.accept();  
            
          if (sc == null) {  
              // no connections, snooze a while ...  
              Thread.sleep(3000);  
          } else {  
              System.out.println("Incoming connection from " + sc.socket().getRemoteSocketAddress());  
              ByteBuffer readerBuffer = ByteBuffer.allocate(1024);  
              sc.read(readerBuffer);  
              readerBuffer.flip();  
              //output get   
              out(readerBuffer);   
              sc.close();  
          }  
      }  
}  

  private static void out(ByteBuffer readerBuffer) {  
      StringBuffer sb = new StringBuffer();  
      for (int i = 0; i < readerBuffer.limit(); i++) {  
          char c = (char) readerBuffer.get();  
          sb.append(new String(new char[]{c}));  
               }         
      System.out.println(sb.toString());  
  }  
} 
