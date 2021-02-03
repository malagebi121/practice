package com.embrace.practice.designpattern.template;

/**
 * @author embrace
 * @describe  豆浆类,
 *
 * 父类
 *
 *
 * @date created in 2021/1/14 23:48
 */
public abstract  class SoyaMilk {

    final void make(){
        select();
        yanmo();
        over();
    }

    abstract void select();

    private void yanmo(){
        System.out.println("开始磨");
    }

    private void over(){
        System.out.println("搞好了");
    }

    //钩子方法，要不要被子类实现
    boolean isAdd(){
        return  true;
    }
}
