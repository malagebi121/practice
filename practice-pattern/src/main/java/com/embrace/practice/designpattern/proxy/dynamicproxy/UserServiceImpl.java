package com.embrace.practice.designpattern.proxy.dynamicproxy;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/14 17:35
 */
public class UserServiceImpl  implements IUserService{
    @Override
    public void addUser(String id, String name) {
        System.out.println("添加 id = " + id + ", 姓名为 ：" + name);
    }

    @Override
    public void queryUser(String id) {
        System.out.println("查询 id = " + id);
    }
}
