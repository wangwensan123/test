package designPattern.creationPattern.abstractFactory.factorys;

import designPattern.creationPattern.abstractFactory.impl.Bus;
import designPattern.creationPattern.abstractFactory.impl.Fighter;
import designPattern.creationPattern.abstractFactory.impl.Pencil;
import designPattern.creationPattern.abstractFactory.interf.IFlyable;
import designPattern.creationPattern.abstractFactory.interf.IMoveable;
import designPattern.creationPattern.abstractFactory.interf.IWriteable;

/**
 *
 * 第二个工厂生产：战斗机、公交车和铅笔
 */
public class Factory2 extends AbstractFactory {

    @Override
    public IFlyable createFlyable() {
        return new Fighter();
    }

    @Override
    public IMoveable createMoveable() {
        return new Bus();
    }

    @Override
    public IWriteable createWriteable() {
        return new Pencil();
    }

}
