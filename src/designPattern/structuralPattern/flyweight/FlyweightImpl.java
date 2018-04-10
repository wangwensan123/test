package designPattern.structuralPattern.flyweight;
/**
 *@auth wws
 *@date 2018年4月10日---下午4:21:55
 *
 **/
public class FlyweightImpl extends IFlyweight {
  private String code;

  public FlyweightImpl(String code) {
    this.code = code;
  }
  
  public FlyweightImpl(String code,String name) {
    this.code = code;
  }

  public void operation() {
    System.out.println("Concrete---FlyweightImpl : " + code);
  }
}
