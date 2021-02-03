package com.embrace.practice.designpattern.proxy.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author embrace
 * @describe   自定义的代理类
 * @date created in 2021/1/14 17:22
 */
public class MyProxy {
    // 引入目标类
    private Object target;

    // 构造方法传入
    public MyProxy(Object target) {
        this.target = target;
    }
    /**
     *  newProxyInstance(ClassLoader loader, Class<?>[] interfaces,InvocationHandler h)
     *  ClassLoader loader : 目标对象的 ClassLoader
     *  Class<?>[] interfaces ： 目标对象的接口
     *  InvocationHandler h : 执行目标对象方法时候的方法处理
     * @return
     */
    public Object getTarget(){
       return Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces(), new InvocationHandler() {
           @Override
           public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
               System.out.println("开始执行目标接口");
               Object result = method.invoke(target, args);
               System.out.println("调用目标类结束");
               return result;
           }
       });
    }
}
