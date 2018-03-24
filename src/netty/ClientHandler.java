package netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

/**
 *@auth wws
 *@date 2018年3月23日---下午8:26:53
 *
 **/
public class ClientHandler extends ChannelHandlerAdapter{

  public void channelActive(ChannelHandlerContext ctx) throws Exception {
  }

  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
      try {
          ByteBuf buf = (ByteBuf) msg;
          byte[] req = new byte[buf.readableBytes()];
          buf.readBytes(req);
          String body = new String(req, "utf-8");
          System.out.println("Client :" + body );
      } finally {
          // 记得释放xxxHandler里面的方法的msg参数: 写(write)数据, msg引用将被自动释放不用手动处理; 但只读数据时,!必须手动释放引用数
           ReferenceCountUtil.release(msg);
      }
  }

  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
          throws Exception {
      ctx.close();
  }
}