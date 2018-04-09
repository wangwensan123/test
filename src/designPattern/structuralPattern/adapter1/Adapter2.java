package designPattern.structuralPattern.adapter1;
/**
 *@auth wws
 *@date 2018年4月9日---下午4:59:15
 *
 **/
public class Adapter2 implements IPs2{
  
  private IUsb usb;

  public Adapter2(IUsb usb) {
    this.usb = usb;
  }

  @Override
  public void isPs2() {
    usb.isUsb();   
  }

}
