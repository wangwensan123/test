package designPattern.behavioralPattern.visitor;

/**
 * 
 */
class FloatElement implements IElement {
    private Float value;

    public FloatElement(Float value) {
        this.value = value;
    }

    public Float getValue() {
        return value;
    }

    // 定义 accept 的具体内容 这里是很简单的一句调用
    public void accept(IVisitor visitor) {
        visitor.visitFloat(this);
    }
}