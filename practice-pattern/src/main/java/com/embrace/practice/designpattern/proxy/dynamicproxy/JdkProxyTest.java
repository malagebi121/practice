package com.embrace.practice.designpattern.proxy.dynamicproxy;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/14 17:37
 */
public class JdkProxyTest {
    public static void main(String[] args) {
        IUserService userService = (IUserService) new MyProxy(new UserServiceImpl()).getTarget();
        System.out.println(userService);
        System.out.println(userService.getClass()); //class com.sun.proxy.$Proxy0  代理对象
        userService.addUser("1","张三");

    }
}
