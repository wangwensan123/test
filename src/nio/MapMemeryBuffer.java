package nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 *@auth wws
 *@date 2018年3月22日---下午6:32:23
 *
 **/
public class MapMemeryBuffer {
  
    public static void main(String[] args) throws IOException {
      ByteBuffer byteBuf = ByteBuffer.allocate(1024 * 14 * 1024);
      byte[] bbb = new byte[14 * 1024 * 1024];
      FileInputStream fis = new FileInputStream("/home/jack/test.txt");
      FileOutputStream fos = new FileOutputStream("/home/jack/test.txt");
      FileChannel fc = fis.getChannel();
      long timeStar = System.currentTimeMillis();// 得到当前的时间
//      fc.read(byteBuf);// 1 读取
       MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0,fc.size());
      System.out.println(fc.size() / 1024);
      long timeEnd = System.currentTimeMillis();// 得到当前的时间
      System.out.println("Read time :" + (timeEnd - timeStar) + "ms");
      timeStar = System.currentTimeMillis();
//      fos.write(bbb);// 2.写入
       mbb.flip();
       FileChannel out = new FileOutputStream(new File("/home/jack/test.txt")).getChannel();
       out.write(mbb);
      timeEnd = System.currentTimeMillis();
      System.out.println("Write time :" + (timeEnd - timeStar) + "ms");
      fos.flush();
      fc.close();
      fis.close();
          }

                //文件复制
        public void copyFile(String filename,String srcpath,String destpath)throws IOException {
         File source = new File(srcpath+"/"+filename);
         File dest = new File(destpath+"/"+filename);
          FileChannel in = null, out = null;
          try { 
           in = new FileInputStream(source).getChannel();
           out = new FileOutputStream(dest).getChannel();
           long size = in.size();
          MappedByteBuffer buf = in.map(FileChannel.MapMode.READ_ONLY, 0, size);
          out.write(buf);
          in.close();
          out.close();
          source.delete();//文件复制完成后，删除源文件
         }catch(Exception e){
          e.printStackTrace();
         } finally {
          in.close();
          out.close();
         }
     }

}
