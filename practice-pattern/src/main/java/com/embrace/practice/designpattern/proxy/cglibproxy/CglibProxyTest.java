package com.embrace.practice.designpattern.proxy.cglibproxy;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/14 20:01
 */
public class CglibProxyTest {
    public static void main(String[] args) {
        StudentServiceImpl studentService = (StudentServiceImpl) new ProxyFactory(new StudentServiceImpl()).getInstance();
        studentService.addStudent();
    }
}
