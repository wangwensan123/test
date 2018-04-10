package designPattern.structuralPattern.composite;
/**
 *@auth wws
 *@date 2018年4月10日---下午2:31:45
   *组合模式，就是在一个对象中包含其他对象，这些被包含的对象可能是终点对象（不再包含别的对象），
   *也有可能是非终点对象（其内部还包含其他对象，或叫组对象），我们将对象称为节点，即一个根节点包含许多子节点，
   *这些子节点有的不再包含子节点，而有的仍然包含子节点，以此类推。很明显，这是树形结构，终结点叫叶子节点，
   *非终节点（组节点）叫树枝节点，第一个节点叫根节点。同时也类似于文件目录的结构形式：文件可称之为终节点，目录可称之为非终节点（组节点）。
   *1、想要表示对象的部分-整体层次结构。
   * 2、想要客户端忽略组合对象与单个对象的差异，客户端将统一地使用组合结构中的所有对象。
    *关于分级数据结构的一个普遍性的例子是你每次使用电脑时所遇到的:文件系统。
    *文件系统由目录和文件组成。每个目录都可以装内容。目录的内容可以是文件，也 可以是目录。
   * 按照这种方式，计算机的文件系统就是以递归结构来组织的。如果你想要描述这样的数据结构，那么你可以使用组合模式。
 **/
public class CompositeTest {

  public static void main(String[] args) {
      Composite root = new Composite("root");
      root.add(new Leaf("Leaf A"));
      root.add(new Leaf("Leaf B"));
      
      Composite compX = new Composite("Composite X");
      compX.add(new Leaf("Leaf XA"));
      compX.add(new Leaf("Leaf XB"));
      
      Composite compXY = new Composite("Composite XC");
      compXY.add(new Leaf("Leaf XCA"));
      compXY.add(new Leaf("Leaf XCB"));
      Component compXCC = new Leaf("Leaf XCC");
      compXCC.add(new Leaf("Composite XCCA"));
      compXY.add(compXCC);
      compX.add(compXY);   
      
      root.add(compX);
      
      root.display(1);
      System.out.println("------------------");
      root.remove(new Leaf("Leaf XCB"));
      root.display(2);
  }

}
