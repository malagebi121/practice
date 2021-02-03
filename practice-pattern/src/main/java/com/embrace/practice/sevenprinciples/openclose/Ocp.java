package com.embrace.practice.sevenprinciples.openclose;

/**
 * @author embrace
 * @describe  开闭原则
 *  原来存在的问题
 *     如果要画正方形，需要修改 GraphicEditor 里面 draw 方法 ，还要添加一个方法
 *     违背了对扩展修改了原来的代码
 * @date created in 2021/1/12 21:00
 */
public class Ocp {
    public static void main(String[] args) {
        GraphicEditor editor = new GraphicEditor();
        editor.draw(new Circle());
        editor.draw(new Line());
    }
}

// 使用方要修改代码，  添加画一个正方形的功能
class GraphicEditor{
    public  void draw(Sharp s) {
        if(s.type == 1){
            drawCircle();
        }
        if(s.type == 2){
            drawLine();
        }
    }

    private void drawLine() {
        System.out.println("画一条直线");
    }

    private void drawCircle() {
        System.out.println("画一个圆");
    }
}




class Sharp{
    int type;
}

class Circle extends Sharp{
    public Circle(){
        super.type = 1;
    }
}

class Line extends Sharp{
    public Line(){
        super.type = 2;
    }
}
