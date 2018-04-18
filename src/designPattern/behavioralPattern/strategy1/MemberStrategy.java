package designPattern.behavioralPattern.strategy1;
/**
 *@auth wws
 *@date 2018年4月10日---下午5:28:36
 *
 **/
public interface MemberStrategy {
  /**
   * 计算图书的价格
   * @param booksPrice    图书的原价
   * @return    计算出打折后的价格
   */
  public double calcPrice(double booksPrice);
}
