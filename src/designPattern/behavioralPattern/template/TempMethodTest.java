package designPattern.behavioralPattern.template;
/**
 *@auth wws
 *@date 2018年4月19日---下午1:12:18
 * 模板方法模式是类的行为模式。准备一个抽象类，将部分逻辑以具体方法以及具体构造函数的形式实现，
 * 然后声明一些抽象方法来迫使子类实现剩余的逻辑。不同的子类可以以不同的方式实现这些抽象方法，
 * 从而对剩余的逻辑有不同的实现。这就是模板方法模式的用意。
 **/
public class TempMethodTest {
  
  public static void main(String[] args) {
    AbstractDesignCycle ps = new PaymentSystem();
    ps.templateDesignSystem();

    AbstractDesignCycle ls = new LogisticSystem();
    ls.templateDesignSystem();
  }
  
}
