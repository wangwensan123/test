package designPattern.behavioralPattern.observable1;

import java.util.Observable;
import java.util.Observer;

/**
 *@auth wws
 *@date 2018年4月19日---下午3:51:30
 *
 **/
public class AObserver implements Observer {
  
  public AObserver() {
    super();
  }
  
  public AObserver(ServerManager sm) {
      super();
      // TODO Auto-generated constructor stub
      sm.addObserver(this);//注册加入观察者
  }

  @Override
  public void update(Observable arg0, Object arg1) {
      System.out.println("AObserver receive:Data has changed to "+((ServerManager) arg0).getData());

  }

}
