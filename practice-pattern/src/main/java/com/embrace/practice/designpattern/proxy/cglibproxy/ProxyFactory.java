package com.embrace.practice.designpattern.proxy.cglibproxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author embrace
 * @describe  cglib  实现
 * @date created in 2021/1/14 19:48
 */
public class ProxyFactory implements MethodInterceptor {
    // 聚合一个目标类
    private Object target;

    public ProxyFactory(Object target) {
        this.target = target;
        getInstance();
    }
    //返回代理对象
    public Object getInstance(){
        //创建工具类
        Enhancer enhancer = new Enhancer();
        //设置父类
        enhancer.setSuperclass(target.getClass());
        //设置目标函数
        enhancer.setCallback(this);
        //返回
        return enhancer.create();
    }
    //调用目标类的方法  ,  执行会调用这里
    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("cglib 开始");
        Object result = method.invoke(target, args);
        System.out.println("cglib 结束");
        return result;
    }
}
