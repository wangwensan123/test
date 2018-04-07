package designPattern.singleton;
/***
 * 在用enum实现Singleton时我曾介绍过三个特性，自由序列化，线程安全，保证单例
 * 会在静态代码中对枚举量进行初始化,这样的加载时间其实有点类似于饿汉模式，并没有起到lazy-loading的作用
 * 对于序列化和反序列化，因为每一个枚举类型和枚举变量在JVM中都是唯一的，
 * 即Java在序列化和反序列化枚举时做了特殊的规定，枚举的writeObject、readObject、readObjectNoData、
 * writeReplace和readResolve等方法是被编译器禁用的，
 * 因此也不存在实现序列化接口后调用readObject会破坏单例的问题。
 */
public enum RegisterSingleton {

  INSTANCE;
    
   public void aaa(){
      System.out.println("1111111");
    }
    
    public static void main(String[] args) {
      RegisterSingleton a = RegisterSingleton.INSTANCE;
      RegisterSingleton b = RegisterSingleton.INSTANCE;
      a.aaa();
      System.out.println(a==b);
    }
}
