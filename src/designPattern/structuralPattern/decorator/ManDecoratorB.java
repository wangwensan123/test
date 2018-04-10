package designPattern.structuralPattern.decorator;
/**
 *@auth wws
 *@date 2018年4月9日---下午7:17:14
 *
 **/
public class ManDecoratorB extends Decorator {
  
  public void eat() {
      super.eat();
      System.out.println("===============");
      System.out.println("ManDecoratorB类");
  }
}
