package designPattern.creationPattern.simpleFactoryPattern;

import designPattern.creationPattern.simpleFactoryPattern.Broom;
import designPattern.creationPattern.simpleFactoryPattern.Plane;

/*
 * 1、简单工厂模式是属于创建型模式，又叫做静态工厂方法模式。
          它的实现方式是由一个工厂类根据传入的参数，动态决定应该创建哪一个产品类（这些产品类继承自一个父类或接口）的实例。
    2、简单工厂模式由三种角色组成： 
        （1）工厂角色：简单工厂模式的核心，它负责实现创建所有实例的内部逻辑。工厂类的创建产品类的方法可以被外界直接调用，创建所需的产品对象。
        （2）抽象产品角色：简单工厂模式所创建的所有对象的父类，它负责描述所有实例所共有的公共接口。 
        （3）具体产品角色：是简单工厂模式的创建目标，所有创建的对象都是充当这个角色的某个具体类的实例。
 * 
 */

public class SimpleFactory {

    public IMoveable create(Class<? extends IMoveable> clazz) {
        IMoveable obj = null;
        try {
          obj = clazz.newInstance();
        } catch (Exception e) {
          e.printStackTrace();
                  }
        return obj;
    }
    
    
    public static void main(String[] args) {
              // 简单工厂模式测试
      SimpleFactory simpleFactory = new SimpleFactory();
      IMoveable broom =  simpleFactory.create(Broom.class);
      broom.run();
      
      IMoveable plane =  simpleFactory.create(Plane.class);
      plane.run();

  }
}
