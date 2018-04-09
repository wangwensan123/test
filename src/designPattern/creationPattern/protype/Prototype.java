package designPattern.creationPattern.protype;
/**
 *@auth wws
 *@date 2018年4月9日---下午3:35:44
 *
 **/
public class Prototype implements Cloneable{

  private String name;

  public String getName() {
      return name;
  }

  public void setName(String name) {
      this.name = name;
  }

  @Override
  protected Object clone()   {
      try {
          return super.clone();
      } catch (CloneNotSupportedException e) {
          e.printStackTrace();
      }finally {
          return null;
      }
  }

  public static void main ( String[] args){
      Prototype pro = new Prototype();
      Prototype pro1 = (Prototype)pro.clone();
  }
}
