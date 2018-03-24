package netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 *@auth wws
 *@date 2018年3月23日---下午7:44:20
 *
 **/
public class TimeServer {

  public void bind(int port) throws Exception {
                /**
       * NioEventLoopGroup 是用来处理I/O操作的多线程事件循环器，
       * Netty提供了许多不同的EventLoopGroup的实现用来处理不同传输协议，
               * 在这个例子中我们实现了一个服务端的应用，因此会有2个NioEventLoopGroup会被使用。
               * 第一个经常被叫做‘boss’，用来接收进来的连接。
               * 第二个经常被叫做‘worker’，用来处理已经被接收的连接。
               */
      EventLoopGroup bossGroup = new NioEventLoopGroup();
      EventLoopGroup workerGroup = new NioEventLoopGroup();
      try {
          ServerBootstrap server = new ServerBootstrap();
          server.group(bossGroup, workerGroup)
          .channel(NioServerSocketChannel.class)
          .option(ChannelOption.SO_BACKLOG, 1024)
          .childHandler(new ChildChannelHandler());
          System.out.println("------------1111-------");
                          //绑定端口，同步等待成功
          ChannelFuture future = server.bind(port).sync();
          System.out.println("------------2222-------");
                        //等待服务端监听端口关闭
          future.channel().closeFuture().sync();
          System.out.println("------------3333-------");
      }catch(Exception e){
        System.out.println("------------4444-------");
          e.printStackTrace();
      } finally {
                        //优雅退出，释放线程池资源
          bossGroup.shutdownGracefully();
          workerGroup.shutdownGracefully();
      }

  }

  private class ChildChannelHandler extends ChannelInitializer<SocketChannel>{
      @Override
      protected void initChannel(SocketChannel socketChannel) throws Exception {
        System.out.println("------------5555555-------");
          socketChannel.pipeline().addLast(new TimeServerHandler());
      }
  }

  public static void main(String[] args) throws Exception {
      int port = 8888;
      new TimeServer().bind(port); 
  }

}
