package netty;

import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 *@auth wws
 *@date 2018年3月23日---下午7:46:02
 *
 **/
public class TimeServerHandler extends ChannelHandlerAdapter{


  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
      /*ByteBuf byteBuf = (ByteBuf) msg;
      String message = new String(byteBuf.array());*/
//      ByteBuf byteBuf = (ByteBuf) msg;
//      byte[] buf = new byte[byteBuf.readableBytes()];
//      byteBuf.readBytes(buf);
//      String message = new String(buf,"UTF-8");
//      System.out.println("the server received msg："+message);
      System.out.println("------------channelRead-------");
      ByteBuf byteBuf = (ByteBuf) msg;
      String message = new String(byteBuf.array());
      System.out.println("the server received msg："+message);

      String resText = "你好，我已经收到你的消息！"+new Date(System.currentTimeMillis()).toString();
      ByteBuf writeByteBuf = Unpooled.copiedBuffer(resText.getBytes());
      ctx.write(writeByteBuf);
  }

  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
      ctx.flush();
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
      System.out.println(cause.getMessage());
      ctx.close();
  }

}
