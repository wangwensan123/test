package designPattern.behavioralPattern.visitor;

/**
 * 
 */
class StringElement implements IElement {
    private String value;

    public StringElement(String string) {
        value = string;
    }

    public String getValue() {
        return value;
    }

    // 定义 accept 的具体内容 这里是很简单的一句调用
    public void accept(IVisitor visitor) {
        visitor.visitString(this);
    }
}