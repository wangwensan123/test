package designPattern.creationPattern.singleton;
/***
 * 懒汉式 （静态内部类），推荐
 */
public class LazySingleton2 {

    private static boolean initialized = false;

    //静态的内部类（寄生虫）,解决多线程安全问题
    private static class LazyHolder {
        private static final LazySingleton2 LAZY = new LazySingleton2();
    }

    private LazySingleton2() {

                    //解决反射破坏到单例
        synchronized (LazySingleton2.class) {
            if (!initialized) {
                initialized = true;
            } else {
                throw new RuntimeException("禁止初始化...");
            }
        }
    }

    public static LazySingleton2 getInstance() {
        return LazyHolder.LAZY;
    }

}
