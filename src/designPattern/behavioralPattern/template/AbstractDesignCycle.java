package designPattern.behavioralPattern.template;
/**
 *@auth wws
 *@date 2018年4月19日---下午1:10:31
 *
 **/
//就是从需求分析到发布系统的一套设计流程  
public abstract class AbstractDesignCycle {  

  public abstract void needAnalysis();  
    
  public abstract void conceptualDesign();  
    
  public abstract void detailedDesign();  
    
  public abstract void coding();  
    
  public abstract void testSystem();  
    
  public abstract void publishSystem();  
    
  public void templateDesignSystem(){  
      System.out.println("------------开始开发----------");  
      needAnalysis();  
      conceptualDesign();  
      detailedDesign();  
      coding();  
      testSystem();  
      publishSystem();  
      System.out.println("------------开发完毕-----------");  
  }  
}  
