package designPattern.behavioralPattern.chainResponsibility;
/**
 *@auth wws
 *@date 2018年3月24日---下午3:34:31
 *
 **/
public abstract class Lingdao {
  private Lingdao nextLingdao;
  public Lingdao getNextLingdao() {
      return nextLingdao;
  }
  public void setNextLingdao(Lingdao nextLingdao) {
      this.nextLingdao = nextLingdao;
  }
  abstract void chuli(Files file);
}
