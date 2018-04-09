package designPattern.creationPattern.factoryMethod;

import designPattern.creationPattern.simpleFactoryPattern.IMoveable;


/**
 *@auth wws
 *@date 2018年4月9日---下午4:06:05
 *
 **/
public class FactoryTest {
  
  public static void main(String[] args) {
        // 工厂方法测试
    VehicleFactory factory = new PlaneFactory();
    IMoveable moveable = factory.create();
    moveable.run();
    
    VehicleFactory factory1 = new BroomFactory();
    IMoveable moveable1 = factory1.create();
    moveable1.run();
  }
  

}
