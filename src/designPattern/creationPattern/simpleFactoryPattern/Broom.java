package designPattern.creationPattern.simpleFactoryPattern;

import designPattern.creationPattern.simpleFactoryPattern.IMoveable;

// 具体产品角色
public class Broom implements IMoveable {

    @Override
    public void run() {
        System.out.println("我是Broom.");
    }

}
