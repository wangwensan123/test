package netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 *@auth wws
 *@date 2018年3月23日---下午7:48:32
 *
 **/
public class TimeClientHandler extends ChannelHandlerAdapter {
  
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
      String msg = "你好！TimeServer。。。。。。。。。";
      ByteBuf byteBuf = Unpooled.copiedBuffer(msg.getBytes());
      ctx.writeAndFlush(byteBuf);
  }
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
      ByteBuf byteBuf = (ByteBuf) msg;
      byte[] buf = new byte[byteBuf.readableBytes()];
      byteBuf.readBytes(buf);
      String message = new String(buf);
      System.out.println("Now is ：" + message);
  }
  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
      ctx.close();
  }

}
