package com.embrace.practice.oom;

import com.sun.deploy.security.EnhancedJarVerifier;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author embrace
 * @describe  异常信息  java.lang.OutOfMemoryError: Metaspace
 *     -XX:MetaspaceSize=10m -XX:MaxMetaspaceSize=12m
 *     MetaSpace 区溢出
 *     java8之后  Metaspace 元空间替代了永久代
 *
 *     Metaspace 是方法区在Hotspot中的实现，与永久代的区别在于， Metaspace 并不在
 *     虚拟机对的内存中而是使用本地内存(不在一个锅里抢饭吃)，也就是说 java8之后 class mataDate 储存在
 *     叫做 Metaspace 的 native memory 中
 *
 *     永久代被元空间代替，储存
 *          虚拟机加载的类信息
 *          常量池
 *          静态变量
 *          及时编译的代码
 * @date created in 2020/12/23 12:58
 */
public class MetaSpaceDemo {

    static class OOMTest{}
    public static void main(String[] args) {
        int  a = 0;
        try {
            for(int  i = 1;; i++){
                a = i;
                // cglib 动态代理
                //java.lang.OutOfMemoryError: Metaspace
                Enhancer  enhancer = new Enhancer();
                enhancer.setSuperclass(OOMTest.class);
                enhancer.setUseCache(false);
                enhancer.setCallback(new MethodInterceptor() {
                    @Override
                    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                        return methodProxy.invoke(o, args);
                    }
                });
                enhancer.create();
            }
        } catch (Throwable e) {
            System.out.println("生成的次数：" +  a); // 生成的次数：745
            e.printStackTrace();
        }
    }

}
