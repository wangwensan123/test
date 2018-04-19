package designPattern.behavioralPattern.observable1;

import java.util.Observable;
import java.util.Observer;

/**
 *@auth wws
 *@date 2018年4月19日---下午3:52:36
 *
 **/
public class BObserver implements Observer {
  
  public BObserver(){
    super();
  }
  
  public BObserver(ServerManager sm) {
      super();
      sm.addObserver(this);//注册加入观察者
  }

  @Override
  public void update(Observable o, Object arg) {
      // TODO Auto-generated method stub
      System.out.println("BObserver receive:Data has changed to "+((ServerManager) o).getData());
  }

}
