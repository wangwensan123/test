package designPattern.structuralPattern.decorator;
/**
 *@auth wws
 *@date 2018年4月9日---下午7:17:39
 *装饰者模式
1、意图： 动态地给一个对象添加一些额外的职责。就增加功能来说， Decorator模式相比生成子类更为灵活。该模式以对客 户端透明的方式扩展对象的功能。
2、适用环境
（1）在不影响其他对象的情况下，以动态、透明的方式给单个对象添加职责。
（2）处理那些可以撤消的职责。
（3）当不能采用生成子类的方法进行扩充时。一种情况是，可能有大量独立的扩展，为支持每一种组合将产生大量的 子类，
          使得子类数目呈爆炸性增长。另一种情况可能是因为类定义被隐藏，或类定义不能用于生成子类。
3、参与者
    1.Component（被装饰对象的基类）
      定义一个对象接口，可以给这些对象动态地添加职责。
    2.ConcreteComponent（具体被装饰对象）
      定义一个对象，可以给这个对象添加一些职责。
    3.Decorator（装饰者抽象类）
      维持一个指向Component实例的引用，并定义一个与Component接口一致的接口。
    4.ConcreteDecorator（具体装饰者）
      具体的装饰对象，给内部持有的具体被装饰对象，增加具体的职责。
 **/
public class DecoratorTest {

  public static void main(String[] args) {
      IPerson man = new Man();
      man.eat();
      System.out.println("------------------------");
      
      Decorator md1 = new ManDecoratorA();
      md1.setPerson(man);
      md1.eat();
      System.out.println("------------------------");
      
      Decorator md2 = new ManDecoratorB();
      md2.setPerson(md1);
      md2.eat();
      

  }
}
