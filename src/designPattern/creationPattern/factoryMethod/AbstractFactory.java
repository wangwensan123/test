package designPattern.creationPattern.factoryMethod;

import designPattern.creationPattern.simpleFactoryPattern.IMoveable;


// 抽象工厂
public abstract class AbstractFactory {

    public abstract IMoveable create();
}
