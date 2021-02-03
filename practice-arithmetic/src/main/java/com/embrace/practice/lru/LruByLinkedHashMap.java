package com.embrace.practice.lru;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author embrace
 * @describe LRU latest recent used  最近最少使用
 *          利用 LinkedHashMap 制作自己的lRU
 * @date created in 2020/12/13 23:43
 */
public class LruByLinkedHashMap<K, V>  extends LinkedHashMap {

    //容量
    private  int capacity;

    //负载因子
    private static final float  loadFactor = 0.75f;

    /**
     * 调用父类的
     * accessOrder  为 false 就直接排队，不是 lru
     * @param capacity
     */
    public LruByLinkedHashMap(int capacity) {
        //
        super(capacity, loadFactor, true);
        this.capacity = capacity;
    }

    /**
     * 删除最近未使用的
     * @param eldest
     * @return
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        return super.size() > capacity;
    }


    public static void main(String[] args) {
        LruByLinkedHashMap lru = new LruByLinkedHashMap(3);
        lru.put("1","a");
        lru.put("2","b");
        lru.put("3","c");
        System.out.println(lru.keySet()); //[1, 2, 3]


        lru.put("4","e");
        lru.put("3","d");
        System.out.println(lru.keySet()); //[2, 4, 3]
    }
}
