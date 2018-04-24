package designPattern.creationPattern.factoryMethod;

import designPattern.creationPattern.simpleFactoryPattern.IMoveable;


/**
 *@auth wws
 *@date 2018年4月9日---下午4:06:05
 *定义一个用于创建对象的接口，让子类决定实例化哪一个类。工厂方法使一个类的实例化延迟到其子类。
 *工厂方法模式：
    一个抽象产品类，可以派生出多个具体产品类。   
    一个抽象工厂类，可以派生出多个具体工厂类。   
    每个具体工厂类只能创建一个具体产品类的实例。
    抽象工厂模式：
    多个抽象产品类，每个抽象产品类可以派生出多个具体产品类。   
    一个抽象工厂类，可以派生出多个具体工厂类。   
    每个具体工厂类可以创建多个具体产品类的实例。   
    区别：
    工厂方法模式只有一个抽象产品类，而抽象工厂模式有多个。   
    工厂方法模式的具体工厂类只能创建一个具体产品类的实例，而抽象工厂模式可以创建多个。
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
