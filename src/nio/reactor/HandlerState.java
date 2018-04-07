package nio.reactor;
/**
 *@auth wws
 *@date 2018年3月24日---下午4:55:54
 *
 **/
import java.io.IOException;  
import java.nio.channels.SelectionKey;  
import java.nio.channels.SocketChannel;  
import java.util.concurrent.ThreadPoolExecutor;  
  
public interface HandlerState {  
  
    public void changeState(TCPHandler h);  
  
    public void handle(TCPHandler h, SelectionKey sk, SocketChannel sc,  
            ThreadPoolExecutor pool) throws IOException ;  
}  
