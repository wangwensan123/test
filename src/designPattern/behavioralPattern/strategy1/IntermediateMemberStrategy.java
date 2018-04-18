package designPattern.behavioralPattern.strategy1;
/**
 *@auth wws
 *@date 2018年4月10日---下午5:30:36
 *
 **/
public class IntermediateMemberStrategy implements MemberStrategy {

  @Override
  public double calcPrice(double booksPrice) {

      System.out.println("对于中级会员的折扣为10%");
      return booksPrice * 0.9;
  }

}
