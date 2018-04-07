package designPattern.chainResponsibility;
/**
 *@auth wws
 *@date 2018年3月24日---下午3:35:51
 *职责链模式（称责任链模式）将请求的处理对象像一条长链一般组合起来，
 *形成一条对象链。请求并不知道具体执行请求的对象是哪一个，
 *这样就实现了请求与处理对象之间的解耦。
 **/
public class Clienter {
  
    public static void main(String[] args) {
      //定义职责链
      Lingdao zongjingli = new Zongjingli();
      Lingdao fujingli = new Fujingli();
      Lingdao bumenjingli = new Bumenjingli();
      bumenjingli.setNextLingdao(fujingli);
      fujingli.setNextLingdao(zongjingli);
      //定义两份文件
      Files f1 = new Files();
      f1.setFileName("正确对待旱鸭子");
      f1.setLevel(1);
      Files f2 = new Files();
      f2.setFileName("年会在那里举行");
      f2.setLevel(0);
      //提交文件
      bumenjingli.chuli(f1);
      bumenjingli.chuli(f2);
  }
  
  /**
   * 总经理
   */
  public static class Zongjingli extends Lingdao {
      private final String name = "总经理";
      private final int level = 0;//最大
      @Override
      public void chuli(Files file) {
          if(this.level > file.getLevel()){
              System.out.println(name + "未处理文件《" + file.getFileName() + "》");
              getNextLingdao().chuli(file);
          }else{
              System.out.println(name + "处理了文件《" + file.getFileName() + "》");
          }
      }
  }

  /**
   * 副经理
   */
  public static class Fujingli extends Lingdao {
      private final String name = "副经理";
      private final int level = 1;
      @Override
      public void chuli(Files file) {
          if(this.level > file.getLevel()){
              System.out.println(name + "未处理文件《" + file.getFileName() + "》");
              getNextLingdao().chuli(file);
          }else{
              System.out.println(name + "处理了文件《" + file.getFileName() + "》");
          }
      }
  }

  /**
   * 部门经理
   */
  public static class Bumenjingli extends Lingdao{
      private final String name = "部门经理";
      private final int level = 2;
      @Override
      public void chuli(Files file) {
          if(this.level > file.getLevel()){
              System.out.println(name + "未处理文件《" + file.getFileName() + "》");
              getNextLingdao().chuli(file);
          }else{
              System.out.println(name + "处理了文件《" + file.getFileName() + "》");
          }
      }
  }
  

}