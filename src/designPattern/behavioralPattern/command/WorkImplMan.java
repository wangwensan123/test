package designPattern.behavioralPattern.command;
/**
 *@auth wws
 *@date 2018年4月19日---下午6:00:38
 *接收者，真正执行命令的对象。任何类都可能成为一个接收者，只要它能够实现命令要求实现的相应功能。
 **/
public class WorkImplMan implements IWork{

  @Override
  public void writeDocument() {
    System.out.println("------do writeDocument");
  }

  @Override
  public void work() {
    System.out.println("------do work");
  }

  @Override
  public void overWork() {
    System.out.println("------do overWork");
  }

}
