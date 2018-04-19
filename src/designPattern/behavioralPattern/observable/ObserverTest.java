package designPattern.behavioralPattern.observable;
/**
 *@auth wws
 *@date 2018年4月19日---下午3:44:40
 *在对象之间定义了一对多的依赖，这样一来，当一个对象改变状态，依赖它的对象会收到通知并自动更新
 *其实就是发布订阅模式，发布者发布信息，订阅者获取信息，订阅了就能收到信息，没订阅就收不到信息。
 *有一个微信公众号服务，不定时发布一些消息，关注公众号就可以收到推送消息，取消关注就收不到推送消息。
 **/
public class ObserverTest {
  
  public static void main(String[] args) {
    WechatServer server = new WechatServer();

    Observer userZhang = new User("ZhangSan");
    Observer userLi = new User("LiSi");
    Observer userWang = new User("WangWu");

    server.registerObserver(userZhang);
    server.registerObserver(userLi);
    server.registerObserver(userWang);
    server.setInfomation("PHP是世界上最好用的语言！");

    System.out.println("----------------------------------------------");
    server.removeObserver(userZhang);
    server.setInfomation("JAVA是世界上最好用的语言！");

  }
  
}
