package nio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 *@auth wws
 *@date 2018年3月22日---下午2:42:42
 *
 **/
public class NioTest {

   public static void main(String[] args) throws IOException, InterruptedException {
//     ByteBuffer bbuf = ByteBuffer.allocate(48);
//     CharBuffer cbuf = CharBuffer.allocate(48); 
     write1FileChannel();
     
   }
   
   
   public static void writeFileChannel() throws IOException {
     String filename = "/home/jack/test.txt";
     RandomAccessFile aFile = new RandomAccessFile(filename, "rw");
     FileChannel channel = aFile.getChannel();
     ByteBuffer buffer = ByteBuffer.wrap(new String("1111dfdsfdsfdseqwewqewqeqw11").getBytes());
     channel.write(buffer);  
     channel.close();
     aFile.close();
      }
   
   
   public static void write1FileChannel() throws IOException {
     File file = new File("/home/jack/test.txt");
     FileOutputStream outputStream = new FileOutputStream(file);
     FileChannel channel = outputStream.getChannel();
     ByteBuffer buffer = ByteBuffer.allocate(1024);
     String string = "2222dfdsadasdsfdsfdseqwewqewqeqw11";
     buffer.put(string.getBytes());
     buffer.flip();     //此处必须要调用buffer的flip方法
     channel.write(buffer);
//     buffer.position(buffer.limit());
//     buffer.limit(buffer.capacity());
     buffer.rewind();
     buffer.put("5333333333336".getBytes());
     buffer.flip(); 
     channel.write(buffer);
     channel.close();
     outputStream.close();
   }
   
   
   public static void readFileChannel() throws IOException {
     RandomAccessFile aFile = new RandomAccessFile("/home/jack/test.txt", "rw");
     FileChannel inChannel = aFile.getChannel();

     ByteBuffer buf = ByteBuffer.allocate(48);
     int bytesRead = inChannel.read(buf);
     while (bytesRead != -1) {

       System.out.println("Read " + bytesRead);
       buf.flip();
  
       while(buf.hasRemaining()){
         System.out.print((char) buf.get());
                 }
       System.out.println("");
       buf.clear();
       bytesRead = inChannel.read(buf);
         }
     aFile.close();
      }
  
}
