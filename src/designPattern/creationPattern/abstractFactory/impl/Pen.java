package designPattern.creationPattern.abstractFactory.impl;

import designPattern.creationPattern.abstractFactory.interf.IWriteable;

public class Pen implements IWriteable {

    @Override
    public void write(String label) {
        System.out.println("我是一支钢笔。我刚刚写下一句：“" + label + "”。");
    }

}
