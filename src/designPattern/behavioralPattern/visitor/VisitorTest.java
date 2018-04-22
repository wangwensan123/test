package designPattern.behavioralPattern.visitor;


/**
 * 访问者模式表示一个作用于某对象结构中的各元素的操作。它使你可以在不改变各元素类的前提下定义作用于这些元素的新操作。
 * 1.IVisitor 抽象访问者角色，为该对象结构中具体元素角色声明一个访问操作接口。该操作接口的名字和
   *  参数标识了发送访问请求给具休访问者的具休元素角色，这样访问者就可以通过该元素角色的特定接口直接访问它。
　*　2.VisitorImpl.具体访问者角色，实现Visitor声明的接口。
　*　3.IElement 定义一个接受访问操作(accept())，它以一个访问者(Visitor)作为参数。
　*　4.ConcreteElement 具体元素，实现了抽象元素(Element)所定义的接受操作接口。
　*　5.ObjectStructure 结构对象角色，这是使用访问者模式必备的角色。它具备以下特性：
 * 
 */
public class VisitorTest {

    public static void main(String[] args) {
        IVisitor visitor = new VisitorImpl();

        IElement selement = new StringElement("I am a String");
        selement.accept(visitor);
        System.out.println("------------");
        
        IElement felement = new FloatElement(12f);
        felement.accept(visitor);
        System.out.println("------------");
        
        ObjectStructure os = new ObjectStructure();
        os.add(new StringElement("I am a String1"));
        os.add(new StringElement("I am a String2"));
        os.add(new FloatElement(new Float(12)));
        os.add(new StringElement("I am a String3"));
        os.accpet(visitor);
    }
}
