package designPattern.structuralPattern.flyweight;

import java.util.Hashtable;

/**
 *@auth wws
 *@date 2018年4月10日---下午4:22:41
 *
 **/
public class FlyweightFactory {
  private Hashtable<String,IFlyweight> flyweights; 

  public FlyweightFactory() {
    flyweights = new Hashtable<String,IFlyweight>();
  }

  public IFlyweight getFlyWeight(String code) {
    IFlyweight flyweight = flyweights.get(code);
    if (flyweight == null) {
      flyweight = new FlyweightImpl(code);
      flyweights.put(code, flyweight);
          }
    return flyweight;
  }

  public int getFlyweightSize() {
    return flyweights.size();
  }
}
