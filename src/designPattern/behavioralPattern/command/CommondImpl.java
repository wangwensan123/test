package designPattern.behavioralPattern.command;
/**
 *@auth wws
 *@date 2018年4月19日---下午6:07:43
 *命令接口实现对象，是“虚”的实现；通常会持有接收者，并调用接收者的功能来完成命令要执行的操作。
 **/
public class CommondImpl implements ICommand{
      
    private IWork work;
  
  public CommondImpl(IWork work){
      this.work = work;
    }

  @Override
  public void execute() {
    work.writeDocument();
    work.work();
    work.overWork();
    }

}
