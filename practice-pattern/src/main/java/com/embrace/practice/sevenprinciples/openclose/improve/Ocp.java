package com.embrace.practice.sevenprinciples.openclose.improve;

import javax.print.attribute.standard.MediaSize;

/**
 * @author embrace
 * @describe 开闭原则
 *  原来存在的问题
 *     如果要画正方形，需要修改 GraphicEditor 里面 draw 方法 ，还要添加一个方法
 *     违背了对扩展修改了原来的代码
 *
 *  修改方案，抽象出Sharp 图形，共同的抽象方法 draw 提出， 图形子类继承后自己实现
 *  这样扩展一个其他图像就很方便，不用修改原来的代码
 * @date created in 2021/1/12 21:00
 */
public class Ocp {
    public static void main(String[] args) {
        GraphicEditor editor = new GraphicEditor();
        editor.draw(new Circle());
        editor.draw(new Line());
        // 其他图形
        editor.draw(new OtherGraphic());
        System.out.println(new Circle().type);
    }
}

// 使用方要修改代码，  添加画一个正方形的功能
class GraphicEditor{
    public  void draw(Sharp s) {
        //现在不用判断直接调用
        s.draw();
    }

}

// 顶层抽象类，继承得到自己的方法实现
abstract class Sharp{
    int type;
    abstract void draw();
}

class Circle extends Sharp{
    public Circle(){
        super.type = 1;
    }

    @Override
    void draw() {
        System.out.println("画一个圆");
    }
}

class Line extends Sharp{
    public Line(){
        super.type = 2;
    }

    @Override
    void draw() {
        System.out.println("画一个直线");
    }
}

class OtherGraphic extends Sharp{
    public OtherGraphic() {
        super.type = 3;
    }
    @Override
    void draw() {
        System.out.println("画其他图形");
    }
}
