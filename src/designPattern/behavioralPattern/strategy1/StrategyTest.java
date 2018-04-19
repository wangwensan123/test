package designPattern.behavioralPattern.strategy1;
/**
 *@auth wws
 *@date 2018年4月10日---下午5:32:27
 *策略模式属于对象的行为模式。其用意是针对一组算法，将每一个算法封装到具有共同接口的独立的类中，
 *从而使得它们可以相互替换。策略模式使得算法可以在不影响到客户端的情况下发生变化。
 *
        假设现在要设计一个贩卖各类书籍的电子商务网站的购物车系统。一个最简单的情况就是把所有货品的单价乘上数量，但是实际情况肯定比这要复杂。
        比如，本网站可能对所有的高级会员提供每本20%的促销折扣；对中级会员提供每本10%的促销折扣；对初级会员没有折扣。
　　根据描述，折扣是根据以下的几个算法中的一个进行的：
　　算法一：对初级会员没有折扣。
　　算法二：对中级会员提供10%的促销折扣。
　　算法三：对高级会员提供20%的促销折扣。
 **/
public class StrategyTest {

  public static void main(String[] args) {
              //选择并创建需要使用的策略对象
      MemberStrategy strategy = new AdvancedMemberStrategy();
              //创建环境
      Price price = new Price(strategy);
      int p = 300;
      System.out.println("图书的原价格为：" + p);
               //计算价格
      double quote = price.quote(p);
      System.out.println("图书的最终价格为：" + quote);
  }

}
