package designPattern.creationPattern.factoryMethod;

import designPattern.creationPattern.simpleFactoryPattern.IMoveable;


/**
 *@auth wws
 *@date 2018年4月9日---下午4:06:05
 *定义一个用于创建对象的接口，让子类决定实例化哪一个类。工厂方法使一个类的实例化延迟到其子类。
 **/
public class FactoryTest {
  
  public static void main(String[] args) {
        // 工厂方法测试
    AbstractFactory factory = new PlaneFactory();
    IMoveable moveable = factory.create();
    moveable.run();
    
    AbstractFactory factory1 = new BroomFactory();
    IMoveable moveable1 = factory1.create();
    moveable1.run();
  }
  

}
