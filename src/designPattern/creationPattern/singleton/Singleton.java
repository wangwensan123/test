package designPattern.creationPattern.singleton;
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileOutputStream;  
import java.io.ObjectInputStream;  
import java.io.ObjectOutputStream;  
import java.io.Serializable;  
import java.lang.reflect.Constructor;  
  
/** 
 * 懒汉汉式单例，在需要单例对象的时候，才创建唯一的单例对象，以后再次调用，返回的也是第一创建的单例对象 
 * 将静态成员初始化为null，在获取单例的时候才创建，故此叫懒汉式。 
 * 恶汉式、懒汉式的方式还不能防止反射来实现多个实例，通过反射的方式，设置ACcessible.setAccessible方法可以调用私有的构造器，
 * 可以修改构造器，让它在被要求创建第二个实例的时候抛出异常。
 *其实这样还不能保证单例，当序列化后，反序列化是还可以创建一个新的实例，在单例类中添加readResolve()方法进行防止。 
 * 
 */  
public class Singleton implements Serializable{  
    /** 
     *  
     */  
    private static final long serialVersionUID = -5271537207137321645L;  
    private static Singleton instance = null;  
    private static int i = 1;  
    private Singleton() {  
        /** 
         * 防止反射攻击，只运行调用一次构造器，第二次抛异常 
         */  
        if(i==1){  
            i++;  
        }else{  
            throw new RuntimeException("只能调用一次构造函数");  
                   }  
        System.out.println("调用Singleton的私有构造器");  
          
    }  
    /** 
     * 用同步代码块的方式，在判断单例是否存在的if方法里使用同步代码块，在同步代码块中再次检查是否单例已经生成， 
     * 这也就是网上说的 双重检查加锁的方法 
     * @return 
     */  
    public static synchronized Singleton getInstance(){  
        if(instance==null){  
            synchronized (Singleton.class) {  
                if(instance==null){  
                    instance = new Singleton();  
                }  
            }  
        }  
        return instance;  
    }  
    /** 
     *  
     * 防止反序列生成新的单例对象，这是effective Java 一书中说的用此方法可以防止，具体细节我也不明白 
     * @return 
     */  
    private Object readResolve(){  
        return instance;  
    }  
    public static void main(String[] args) throws Exception {  
        test1();
//        test2();  
    }  
    /** 
     * 测试 反序列 仍然为单例模式 
     * @throws Exception 
     */  
    public static void test2() throws Exception{  
        Singleton s  = Singleton.getInstance();  
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File("E:\\Singleton.txt")));  
        objectOutputStream.writeObject(s);  
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File("E:\\Singleton.txt")));  
        Object readObject = objectInputStream.readObject();  
        Singleton s1 = (Singleton)readObject;  
        System.out.println("s.hashCode():"+s.hashCode()+",s1.hashCode():"+s1.hashCode());  
          
        objectOutputStream.flush();  
        objectOutputStream.close();  
        objectInputStream.close();  
    }  
    /** 
     * 测试反射攻击 
     * @throws Exception 
     */  
    public static void test1(){  
        Singleton s  = Singleton.getInstance();  
        Class c = Singleton.class;  
        Constructor privateConstructor;  
        try {  
            privateConstructor = c.getDeclaredConstructor();  
            privateConstructor.setAccessible(true);  
            privateConstructor.newInstance();
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}  
