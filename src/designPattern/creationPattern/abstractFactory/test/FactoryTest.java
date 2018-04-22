package designPattern.creationPattern.abstractFactory.test;

import designPattern.creationPattern.abstractFactory.factorys.AbstractFactory;
import designPattern.creationPattern.abstractFactory.factorys.Factory1;
import designPattern.creationPattern.abstractFactory.factorys.Factory2;
import designPattern.creationPattern.abstractFactory.interf.IFlyable;
import designPattern.creationPattern.abstractFactory.interf.IMoveable;
import designPattern.creationPattern.abstractFactory.interf.IWriteable;
/*
 * 创建一组相关或相互依赖的对象提供一个接口，而且无须指定它们的具体类。
 */
public class FactoryTest {

    public static void main(String[] args) {
        AbstractFactory factory = new Factory1();
        IFlyable flyable = factory.createFlyable();
        flyable.fly(1589);
        
        IMoveable moveable = factory.createMoveable();
        moveable.run(87.6);
        
        IWriteable writeable = factory.createWriteable();
        writeable.write("Hello World.");
        System.out.println("-----------------------------");
        
        AbstractFactory factory2 = new Factory2();
        IFlyable flyable2 = factory2.createFlyable();
        flyable2.fly(3000);
        
        IMoveable moveable2 = factory2.createMoveable();
        moveable2.run(66.6);
        
        IWriteable writeable2 = factory2.createWriteable();
        writeable2.write("你好.");
    }
}
