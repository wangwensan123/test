package designPattern.creationPattern.singleton;
/**
 *@auth wws
 *@date 2018年3月17日---下午9:04:50
　 *饿汉式是线程安全的,在类创建的同时就已经创建好一个静态的对象供系统使用,以后不在改变
　 *如果是一个工厂模式、缓存了很多实例、那么就得考虑效率问题，因为这个类一加载则把所有实例不管用不用一块创建
 **/
public class SingletonTest1 {
  //该类的一个普通属性  
  int value;  
  //使用静态属性保存该类的一个实例  
  private static SingletonTest1 instance = new SingletonTest1();  
  //构造器私有化，避免该类被多次实例化  
  private SingletonTest1(){  
      System.out.println("正在执行构造器...");  
  }  
  //提供静态方法返回该类实例   
  public static SingletonTest1 getInstance(){  
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
      SingletonTest1 t1 = SingletonTest1.getInstance();  
      SingletonTest1 t2 = SingletonTest1.getInstance();  
      t2.setValue(9);  
      System.out.println(t1 == t2);  
  }  
}
