package designPattern.creationPattern.factoryMethod;

import designPattern.creationPattern.simpleFactoryPattern.IMoveable;
import designPattern.creationPattern.simpleFactoryPattern.Plane;


// 具体工厂
public class PlaneFactory extends VehicleFactory {

    @Override
    public IMoveable create() {
        return new Plane();
    }

}
