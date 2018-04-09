package designPattern.creationPattern.abstractFactory.impl;

import designPattern.creationPattern.abstractFactory.interf.IFlyable;

public class Fighter implements IFlyable {

    @Override
    public void fly(int height) {
        System.out.println("我是一架战斗机，我目前的飞行高度为：" + height + "千米。");
    }

}
