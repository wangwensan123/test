package designPattern.creationPattern.abstractFactory.impl;

import designPattern.creationPattern.abstractFactory.interf.IMoveable;

public class Bus implements IMoveable {

    @Override
    public void run(double speed) {
        System.out.println("我是一辆公交车，我目前的时速是:" + speed + "/小时");
    }

}
