package nio;

import java.io.IOException;  
import java.net.InetSocketAddress;  
import java.nio.ByteBuffer;  
import java.nio.channels.SelectionKey;  
import java.nio.channels.Selector;  
import java.nio.channels.SocketChannel;  
import java.util.Iterator;  


public class NIOClient {  
    //通道管理器  
    private Selector selector;  
  
    /** 
     * 获得一个Socket通道，并对该通道做一些初始化的工作 
     * @param ip 连接的服务器的ip 
     * @param port  连接的服务器的端口号          
     * @throws IOException 
     */  
  public void initClient(String ip, int port) throws IOException {
    // 获得一个Socket通道
    SocketChannel channel = SocketChannel.open();
    // 设置通道为非阻塞
    channel.configureBlocking(false);
    // 获得一个通道管理器
    this.selector = Selector.open();

    // 客户端连接服务器,其实方法执行并没有实现连接，需要在listen（）方法中调
    // 用channel.finishConnect();才能完成连接
    channel.connect(new InetSocketAddress(ip, port));
    // 将通道管理器和该通道绑定，并为该通道注册SelectionKey.OP_CONNECT事件。
    channel.register(selector, SelectionKey.OP_CONNECT);
  }  
  
    /** 
     * 采用轮询的方式监听selector上是否有需要处理的事件，如果有，则进行处理 
     * @throws IOException 
     */  
    @SuppressWarnings("unchecked")  
  public void listen() throws IOException {
          // 轮询访问selector
    while (true) {
      selector.select();
                // 获得selector中选中的项的迭代器
      Iterator ite = this.selector.selectedKeys().iterator();
      while (ite.hasNext()) {
        System.out.println("---ite.hasNext()");
        SelectionKey key = (SelectionKey) ite.next();
                  // 删除已选的key,以防重复处理
        ite.remove();
                  // 连接事件发生
        if (key.isConnectable()) {
          SocketChannel channel = (SocketChannel) key.channel();
                      // 如果正在连接，则完成连接
          if (channel.isConnectionPending()) {
            channel.finishConnect();

                        }
                        // 设置成非阻塞
          channel.configureBlocking(false);

                      // 在和服务端连接成功之后，为了可以接收到服务端的信息，需要给通道设置读的权限。
          channel.register(this.selector, SelectionKey.OP_WRITE|SelectionKey.OP_WRITE);

                      // 获得了可读的事件
        } else if (key.isWritable()) {
          System.out.println("---key.isWritable()");
          write(key);
        } else if (key.isReadable()) {
          System.out.println("----key.isWritable()");
          read(key);
                  }
        key.channel().close();

      }

    }
  }  
    
    /** 
     * 处理读取服务端发来的信息 的事件 
     * @param key 
     * @throws IOException  
     */  
  public void write(SelectionKey key) throws IOException {
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
            }
        // 可读取消息:得到事件发生的Socket通道
    SocketChannel channel = (SocketChannel) key.channel();
    channel.write(ByteBuffer.wrap(new String("hello!i am client").getBytes())); 
     
  }
    
    /** 
     * 处理读取服务端发来的信息 的事件 
     * @param key 
     * @throws IOException  
     */  
  public void read(SelectionKey key) throws IOException {
    // 可读取消息:得到事件发生的Socket通道
    SocketChannel channel = (SocketChannel) key.channel();
    // 创建读取的缓冲区
    ByteBuffer buffer = ByteBuffer.allocate(10);
    int bytesRead = channel.read(buffer);
    StringBuilder content = new StringBuilder();
    while (bytesRead != -1) {
      buffer.flip();
      while (buffer.hasRemaining()) {
        content.append((char) buffer.get());
            }
      buffer.clear();
      bytesRead = channel.read(buffer);
          }
    if(null!=content&&!content.toString().equals("")){
      System.out.println("客户端接收：" + content.toString().trim()); 
    }else{
      try {
        Thread.sleep(3000);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
              }
      System.out.println("客户端未接收...");
          }

  }
      
      
    /** 
     * 启动客户端测试 
     * @throws IOException  
     */  
    public static void main(String[] args) throws IOException {  
        NIOClient client = new NIOClient();  
        client.initClient("127.0.0.1",8000);  
        client.listen();  
    }  
  
} 