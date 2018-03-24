package netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 *@auth wws
 *@date 2018年3月23日---下午7:47:33
 *
 **/
public class TimeClinet {

  public void connect(String host, int port) throws Exception {
      EventLoopGroup workerGroup = new NioEventLoopGroup();
      try {
          Bootstrap server = new Bootstrap();
          server.group(workerGroup)
              .channel(NioSocketChannel.class)
              .option(ChannelOption.TCP_NODELAY, true)
              .handler(new ChannelInitializer<SocketChannel>() {
                      @Override
                      protected void initChannel(SocketChannel ch) throws Exception {
                          ch.pipeline().addLast(new TimeClientHandler());
                      }
              });
          // 发起异步连接操作
          ChannelFuture future = server.connect(host, port).sync();
          // 等待客户端链路关闭
          future.channel().closeFuture().sync();
      } finally {
          //优雅退出，释放NIO线程组
          workerGroup.shutdownGracefully();
      }
  }

  public static void main(String[] args) throws Exception {
      int port = 8888;
      new TimeClinet().connect("127.0.0.1", port);
  }

}
