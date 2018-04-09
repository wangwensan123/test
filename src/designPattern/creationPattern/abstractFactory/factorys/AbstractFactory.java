package designPattern.creationPattern.abstractFactory.factorys;

import designPattern.creationPattern.abstractFactory.interf.IFlyable;
import designPattern.creationPattern.abstractFactory.interf.IMoveable;
import designPattern.creationPattern.abstractFactory.interf.IWriteable;


public abstract class AbstractFactory {

    public abstract IFlyable createFlyable();
    
    public abstract IMoveable createMoveable();
    
    public abstract IWriteable createWriteable();
}
