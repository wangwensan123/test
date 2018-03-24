package nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 *@auth wws
 *@date 2018年3月22日---下午3:43:30
 *
 **/
public class SocketChannelApp{  
  public static void main(String[] args) throws Exception {
    sendMessage("hello word!");
//    sendMessage("hello ketty!");
    
  }  
  
  public static void sendMessage(String message)throws Exception {
    
    InetSocketAddress addr = new InetSocketAddress("127.0.0.1", 8765);  
    SocketChannel sc = SocketChannel.open();  
    sc.connect(addr);  
    sc.configureBlocking(false);  
      
    while (!sc.finishConnect()) {  
        doSomethings();  
            }  
      
    //Do something with the connected socket  
    ByteBuffer buffer = ByteBuffer.wrap(new String(message).getBytes());  
    sc.write(buffer);
    sc.close();  
      

  }

  private static void doSomethings() {  
      System.out.println("do something useless!");  
  }  
} 