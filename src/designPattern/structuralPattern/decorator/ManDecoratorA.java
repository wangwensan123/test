package designPattern.structuralPattern.decorator;
/**
 *@auth wws
 *@date 2018年4月9日---下午7:16:39
 *
 **/
public class ManDecoratorA extends Decorator {

  public void eat() {
      super.eat();
      reEat();
      System.out.println("ManDecoratorA类");
  }

  public void reEat() {
      System.out.println("再吃一顿饭");
  }
}

