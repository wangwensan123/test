package designPattern.creationPattern.factoryMethod;

import designPattern.creationPattern.simpleFactoryPattern.Broom;
import designPattern.creationPattern.simpleFactoryPattern.IMoveable;



// 具体工厂
public class BroomFactory extends AbstractFactory {

    @Override
    public IMoveable create() {
        return new Broom();
    }

}
