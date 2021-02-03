package com.embrace.practice.gc;

/**
 * @author embrace
 * @describe    springboot.jar  带 参数启动  java -server
 * GC算法（引用计数器、复制、标记清除、标记整理）是内存的回收方法，垃级回收器是对算法的落地实现
 * 因为目前为止还没有完美的收集器出现，更加没有万能的收集器，只是针对具体应用最合适的收集器，进行分代收集
 *
 * 垃级回收器
 *      串行垃级回收器（Serial） -XX:-UseSerialGc
 *      并行垃级回收器（Parallel） -XX:-UseParallelGc
 *      并发垃级回收器（CMS）   UseConcMarkSweepGC
 *      混合垃级回收器（G1）  UseG1GC
 *      ZGC
 *
 * 查看默垃级回收器
 *     java -XX:+PrintCommandLineFlags -version
 *
 *
 *
 * @date created in 2020/12/23 17:46
 */
public class Garbage {
}
