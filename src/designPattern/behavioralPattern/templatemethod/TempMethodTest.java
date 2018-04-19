package designPattern.behavioralPattern.templatemethod;
/**
 *@auth wws
 *@date 2018年4月19日---下午1:12:18
 *
 **/
public class TempMethodTest {
  
  public static void main(String[] args) {
    DesignCycle dc;
    dc = new PaymentSystem();
    dc.templateDesignSystem();

    dc = new LogisticSystem();
    dc.templateDesignSystem();
  }
  
}
