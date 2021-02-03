package com.embrace.practice.designpattern.observer.improve;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/14 11:22
 */
public interface Observer {
    // 通知接口
    boolean notice(String msg);
    // 扩展的Observer的名字
    String getName();
}
