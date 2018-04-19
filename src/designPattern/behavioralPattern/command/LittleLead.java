package designPattern.behavioralPattern.command;
/**
 *@auth wws
 *@date 2018年4月19日---下午6:13:01
 *可以看作是遥控器Invoker
 *可以根据不同的命令接口实现执行不同的操作
 **/
public class LittleLead {
  
    private ICommand command;
    
//    private ICommand1 command2;
//    
//    private ICommand1 command3;
          
    private LittleLead(){
           
         }
    
    public LittleLead(ICommand command){
      this.command = command;
        }
    
    public void action(){
      command.execute();
          }
    
//    public void action1(){
//      command1.execute1();
//          }
//    
//    public void action2(){
//      command2.execute2();
//          }

}
