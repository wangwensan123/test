package designPattern.structuralPattern.adapter1;
/**
 *@auth wws
 *@date 2018年4月9日---下午4:55:50
 *
 **/
public class Adapter1 extends Usber implements IPs2{

  @Override
  public void isPs2() {
    isUsb();    
  }

}
