package designPattern.creationPattern.simpleFactoryPattern;

import designPattern.creationPattern.simpleFactoryPattern.IMoveable;

// 具体产品角色
public class Plane implements IMoveable {

    @Override
    public void run() {
        System.out.println("我是Plane.");
    }

}
