package designPattern.behavioralPattern.strategy1;
/**
 *@auth wws
 *@date 2018年4月10日---下午5:31:10
 *
 **/
public class AdvancedMemberStrategy implements MemberStrategy {

  @Override
  public double calcPrice(double booksPrice) {
      
      System.out.println("对于高级会员的折扣为20%");
      return booksPrice * 0.8;
  }
}
