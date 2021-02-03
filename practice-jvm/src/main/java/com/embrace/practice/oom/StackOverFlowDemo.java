package com.embrace.practice.oom;

/**
 * @author embrace
 * @describe   异常信息  Exception in thread "main" java.lang.StackOverflowError
 *             异常类关系, java.lang 包下,模拟 yml 格式来一个
 *               Serializable >
 *                  Throwable >
 *                      Error >
 *                          VirtualMachineError >
 *                              StackOverflowError
 *                              OutOfMemoryError
 *                      Exception >
 *                           RunTimeException >
 *                              XXX(NullPointerException ...)
 *                                  运行时异常，未检测的异常（unchecked exception），这表示这种异常不需要编译器来检测
 *                           IOException  >
 *                              XXX（ClassNotFoundException ...）
 *                                  受检查异常(checked exception)都是编译器在编译时进行校验的，
 *                                  通过throws语句或者try{}cathch{} 语句块来处理检测异常
 *
 *
 *             太大的栈和深度的递归要把栈撑爆了，默认的栈大小为
 *              栈内存大小设置
 *
 *              栈内存为线程私有的空间，每个线程都会创建私有的栈内存。栈空间内存设置过大，
 *              创建线程数量较多时会出现栈内存溢出StackOverflowError。同时，栈内存也决定方法调用的深度，
 *              栈内存过小则会导致方法调用的深度较小，如递归调用的次数较少。
 *
 *              -Xss：如-Xss128k
 *
 *              通常只有几百K
 *              决定了函数调用的深度
 *              每个线程都有独立的栈空间
 *              局部变量、参数 分配在栈上
 *
 * @date created in 2020/12/22 22:34
 */
public class StackOverFlowDemo {

    //感觉还有点问题，下来测试下到底多大
    public static void main(String[] args) {
        stackOver();
    }

    public static void stackOver(){
        stackOver();  //Exception in thread "main" java.lang.StackOverflowError
    }
}
