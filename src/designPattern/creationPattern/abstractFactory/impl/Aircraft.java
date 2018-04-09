package designPattern.creationPattern.abstractFactory.impl;

import designPattern.creationPattern.abstractFactory.interf.IFlyable;

public class Aircraft implements IFlyable {

    @Override
    public void fly(int height) {
        System.out.println("我是一架客运机，我目前的飞行高度为：" + height + "千米。");
    }

}
