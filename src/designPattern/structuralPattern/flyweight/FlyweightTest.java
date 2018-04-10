package designPattern.structuralPattern.flyweight;
/**
 *@auth wws
 *@date 2018年4月10日---下午4:27:10
 *使用共享对象的方法，用来尽可能减少内存使用量以及分享资讯。
 *通常使用工厂类辅助，例子中使用一个HashMap类进行辅助判断，数据池中是否已经有了目标实例，
 *如果有，则直接返回，不需要多次创建重复实例。
 *
   *1.享元模式由于其共享的特征，可以在任何“池”中操作，比如：线程池，数据库连接池。
 *2.String类的设计也是享元模式。
 **/
public class FlyweightTest {


  public static void main(String[] args) {
    FlyweightFactory factory = new FlyweightFactory();
    IFlyweight fly1 = factory.getFlyWeight("001");
    IFlyweight fly2 = factory.getFlyWeight("002");
    IFlyweight fly3 = factory.getFlyWeight("002");
    IFlyweight fly4 = factory.getFlyWeight("003");
    fly1.operation();  
    fly2.operation();  
    fly3.operation();  
    fly4.operation();  
    int objSize = factory.getFlyweightSize();
    System.out.println("objSize = " + objSize);

  }
}
