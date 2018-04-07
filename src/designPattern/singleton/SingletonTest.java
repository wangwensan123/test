package designPattern.singleton;
/**
 *@auth wws
 *@date 2018年3月17日---下午9:04:50
 *懒汉式优点是延时加载、缺点是应该用同步
 **/
public class SingletonTest {
  //该类的一个普通属性  
  int value;  
  //使用静态属性保存该类的一个实例  
  private static SingletonTest instance;  
  //构造器私有化，避免该类被多次实例化  
  private SingletonTest(){  
      System.out.println("正在执行构造器...");  
  }  
  //提供静态方法返回该类实例   
  public static SingletonTest getInstance(){  
      //实例化类实例前，先检查该实例是否存在  
      if(instance == null){  
          //如果不存在，则新建一个实例  
          instance = new SingletonTest();  
      }  
      //返回该类的成员变量：该类的实例   
      return instance;      
  }  
  //以下提供对普通属性value的getter和setter方法  
  public int getValue(){  
      return value;  
  }  

  public void setValue(int values){  
      this.value = values;      
  }  
  public static void main(String args[]){  
      SingletonTest t1 = SingletonTest.getInstance();  
      SingletonTest t2 = SingletonTest.getInstance();  
      t2.setValue(9);  
      System.out.println(t1 == t2);  
  }  
}
