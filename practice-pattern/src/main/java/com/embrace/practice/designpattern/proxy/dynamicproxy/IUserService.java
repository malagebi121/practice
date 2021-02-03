package com.embrace.practice.designpattern.proxy.dynamicproxy;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/14 17:34
 */
public interface IUserService {
    void addUser(String id,String name);

    void queryUser(String id);

}
