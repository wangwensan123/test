package designPattern.behavioralPattern.observable1;
/**
 *@auth wws
 *@date 2018年4月19日---下午3:53:10
 *实现观察者模式非常简单，
 *[1]创建被观察者类，它继承自java.util.Observable类；
 *[2]创建观察者类，它实现java.util.Observer接口；
 *添加它的观察者：
 *void addObserver(Observer o)
 *addObserver()方法把观察者对象添加到观察者对象列表中
 *
 *当被观察者中的事件发生变化时，执行
 *setChanged();
 *notifyObservers();
 *setChange()方法用来设置一个内部标志位注明数据发生了变化；notifyObservers()方法会去调用观察者对象列表中所有的Observer的update()方法，通知它们数据发生了变化。
 *只有在setChange()被调用后，notifyObservers()才会去调用update()。
 
 *对于观察者类，实现Observer接口的唯一方法update
 *void update(Observable o, Object arg)

 *形参Object arg，对应一个由notifyObservers(Object arg);传递来的参数，当执行的是notifyObservers();时，arg为null。
 **/
public class TestDemo {

  public static void main(String[] args) {
      // TODO Auto-generated method stub
      ServerManager sm = new ServerManager();
      AObserver a = new AObserver();
      BObserver b = new BObserver();
      sm.addObserver(a);
      sm.addObserver(b);
      
      sm.setData(5);
      sm.deleteObserver(a);//注销观察者，以后被观察者有数据变化就不再通知这个已注销的观察者
      sm.setData(10);
  }

}
