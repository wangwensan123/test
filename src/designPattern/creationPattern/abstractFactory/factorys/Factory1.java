package designPattern.creationPattern.abstractFactory.factorys;

import designPattern.creationPattern.abstractFactory.impl.Aircraft;
import designPattern.creationPattern.abstractFactory.impl.Car;
import designPattern.creationPattern.abstractFactory.impl.Pen;
import designPattern.creationPattern.abstractFactory.interf.IFlyable;
import designPattern.creationPattern.abstractFactory.interf.IMoveable;
import designPattern.creationPattern.abstractFactory.interf.IWriteable;

/**
 *
 * 第一个工厂生产：客机、汽车和铅笔
 */
public class Factory1 extends AbstractFactory {

    @Override
    public IFlyable createFlyable() {
        return new Aircraft();
    }

    @Override
    public IMoveable createMoveable() {
        return new Car();
    }

    @Override
    public IWriteable createWriteable() {
        return new Pen();
    }

}
