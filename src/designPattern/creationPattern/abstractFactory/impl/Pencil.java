package designPattern.creationPattern.abstractFactory.impl;

import designPattern.creationPattern.abstractFactory.interf.IWriteable;

public class Pencil implements IWriteable {

    @Override
    public void write(String label) {
        System.out.println("我是一支铅笔。我刚刚写下一句：“" + label + "”。");
    }

}
