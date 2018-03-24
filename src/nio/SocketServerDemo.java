package nio;
/**
 *@auth wws
 *@date 2018年3月22日---下午4:04:35
 *
 **/
import java.io.IOException;   
import java.nio.channels.*;  
import java.nio.charset.*;  
import java.net.*;  
import java.util.*;  
import java.nio.*;

public class SocketServerDemo {  
  //Socket协议服务端  
  private int port = 8989;  
  private ServerSocketChannel serverSocketChannel;  
  private Charset charset = Charset.forName("UTF-8");  
  private Selector selector = null;  

  public SocketServerDemo() throws IOException {  
      selector = Selector.open();  
      serverSocketChannel = ServerSocketChannel.open();  
      serverSocketChannel.socket().setReuseAddress(true);  
      serverSocketChannel.socket().bind(new InetSocketAddress(port));  
      System.out.println("服务器启动");  
  }  

  /* 编码过程 */  
  public ByteBuffer encode(String str) {  
      return charset.encode(str);  
  }  

  /* 解码过程 */  
  public String decode(ByteBuffer bb) {  
      return charset.decode(bb).toString();  
  }  

  /* 服务器服务方法 */  
  public void service() throws IOException, InterruptedException {  
      serverSocketChannel.configureBlocking(false);  
      serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);  
            /** 外循环，已经发生了SelectionKey数目 */  
      while (selector.select() > 0) {
          System.out.println("---selector.select()----");
                        /* 得到已经被捕获了的SelectionKey的集合 */  
          Iterator iterator = selector.selectedKeys().iterator();
          while (iterator.hasNext()) { 
              System.out.println("---key----");
              SelectionKey key = null;  
              try {  
                  key = (SelectionKey) iterator.next();
                  iterator.remove();  
                  if (key.isAcceptable()) {  
                      ServerSocketChannel ssc = (ServerSocketChannel) key.channel();  
                      SocketChannel sc = ssc.accept();  
                      System.out.println("客户端机子的地址是 " + sc.socket().getRemoteSocketAddress()); 
                      sc.configureBlocking(false);
                      ByteBuffer buffer = ByteBuffer.allocate(1024);  
                      sc.register(selector, SelectionKey.OP_READ , buffer);//buffer通过附件方式，传递  
                                          }  
                  if (key.isReadable()) {
                      reveice(key);
                                          }  
                  if (key.isWritable()) {  
//                     send(key);  
                                          }  
              } catch (IOException e) {  
                  e.printStackTrace();  
                  try {  
                      if (key != null) {  
                          key.cancel();  
                          key.channel().close();  
                                                      }  
                  } catch (ClosedChannelException cex) {  
                      e.printStackTrace();  
                                            }  
                                  }  
          }  
          /* 内循环完 */  
      }  
      /* 外循环完 */  
  }  

  int x = 1;  

  /* 接收 */  
   public void reveice(SelectionKey key) throws IOException, InterruptedException {  
      if (key == null)  
          return;  

      //***用channel.read()获取客户端消息***//  
      //：接收时需要考虑字节长度        
      SocketChannel sc = (SocketChannel) key.channel();  
      StringBuffer content = new StringBuffer();  
      //create buffer with capacity of 1024 bytes         
      ByteBuffer buf = ByteBuffer.allocate(1024);
      int bytesRead = sc.read(buf); //read into buffer.  
        
      while (bytesRead >0) {  
        buf.flip();  //make buffer ready for read  
        while(buf.hasRemaining()){
            buf.get(new byte[buf.limit()]); // read 1 byte at a time    
            content.append(new String(buf.array()));  
                    }                   
        buf.clear(); //make buffer ready for writing        
        bytesRead = sc.read(buf);   
              } 
      if(null!=content&&!content.toString().equals("")){
        System.out.println("接收：" + content.toString().trim()); 
      }else{
        Thread.sleep(5000);
        System.out.println("未接收...");
            }
       
  }  
    
  int y = 0;  
  public void send(SelectionKey key) {  
//    if (key == null)  
//        return;  
//    ByteBuffer buff = (ByteBuffer) key.attachment();  
//    SocketChannel sc = (SocketChannel) key.channel();  
//    try {  
//        sc.write(ByteBuffer.wrap(new String("aaaa").getBytes()));  
//    } catch (IOException e1) {  
//        e1.printStackTrace();  
//    }  
      System.out.println("send2() " +(++y));  
  }  



  public static void main(String[] args) throws IOException, InterruptedException {  
      new SocketServerDemo().service();  
  }  
} 
