package com.embrace.practice.lru;


import java.util.HashMap;
import java.util.Map;

/**
 * @author embrace
 * @describe  自己的 lru 算法 Least Recently Used
 *            思路：
 *               hash 加上 双向链表 ，hash key 可以算作 O(1) 的时间复杂度， 用 Map 装载 Node节点
 *               step1 Node<K, V>  节点挂载真正数据，节点含有前后指针
 *               step2 双向链表挂载 Node 节点 ， 含有头尾节点（这个也可以不用）有点像 aqs 实现状态机队列的头尾节点）
 *                   method 插入头部
 *                   method 删除数据
 *                   method 返回最后一个元素
 *               step3 依赖 map HashMap ,list  DoubleLinkedList
 *                  method  添加元素  判断 map 中是否含有, 含有就更改数据，删除节点，添加至头节点 ， 判断容量 ...
 *                  method  获取元素  含有 key 就直接返回，没有 -1
 * @date created in 2020/12/14 10:14
 */
public class MYLRUCache {
    //容量
    int cacheSize;

    //hash 节点
    Map<Integer, Node<Integer, Integer>> map;

    //双向链表
    DoubleLinkedList<Integer, Integer> doubleLinkedList;

    /**
     *  构造方法，初始化
     * @param cacheSize
     */
    public MYLRUCache(int  cacheSize){
        this.cacheSize = cacheSize;
        map = new HashMap<>();
        doubleLinkedList = new DoubleLinkedList<>();
    }

    /**
     *  get 方法，判断是否有 k , 没有返回 -1 ，有的话放回 value
     * @param k
     * @return
     */
    public  int  get(int  k){
        if(!map.containsKey(k)){
            return -1;
        }
        Node<Integer, Integer> node = map.get(k);
        //先删除，后添加
        doubleLinkedList.removeNode(node);
        doubleLinkedList.addHead(node);
        return node.v;
    }

    /**  如果以前有k , 选择更新 map 里面 key 的 Node,并且删除 node ，将 node 添加至 头部
     * put 方法
     * @param k
     * @param v
     */
    public void put(int  k, int  v){
        if(map.containsKey(k)){
            Node<Integer, Integer> node = map.get(k);
            node.v = v;
            map.put(k, node);
            doubleLinkedList.removeNode(node);
            doubleLinkedList.addHead(node);
        }else{

            if(cacheSize == map.size()){
                //容量满了,删除最后一个元素
                Node<Integer, Integer> last = doubleLinkedList.getLast();
                map.remove(last.k);
                doubleLinkedList.removeNode(last);
            }
            //添加元素
            Node<Integer, Integer> node = new Node<>(k, v);
            doubleLinkedList.addHead(node);
            map.put(k, node);
        }
    }

    /**
     *  返回现在缓存的容量
     * @return
     */
    public int getSize(){
        return  map.size();
    }

    /**
     * 打印
     */
    public void printKey(){
        Node node = doubleLinkedList.head;
        System.out.print("\n");
        while (node != null){
            if(node != doubleLinkedList.head && node != doubleLinkedList.tail){
                System.out.print(node.k);
            }
            node = node.next;
        }
        System.out.print("\n");
    }

    /**
     *  step 1
     *  数据挂载节点
     * @param <K>
     * @param <V>
     */
    class Node<K, V> {
        //数据K V 键值对，next prev 实现双向链表
        K k;
        V v;
        Node<K, V> next;
        Node<K, V> prev;

        //无参构造方法
        public Node(){
            this.next  = this.prev = null;
        }

        //有参构造方法
        public Node(K k, V v){
            this.k = k;
            this.v = v;
            this.prev = this.next = null;
        }
    }

    /**
     *  step2  双向链表
     * @param <K>
     * @param <V>
     */
    class DoubleLinkedList<K, V>{

        //头尾节点
        Node<K, V> head;
        Node<K, V> tail;

        // 初始化头尾相连
        public DoubleLinkedList(){
            head = new Node<>();
            tail = new Node<>();
            this.head.next = tail;
            this.tail.prev = head;
        }

        //2.1 head tail ,  到 head node tail , tail 泛指头节点的下一个节点
        public void addHead(Node<K, V> node){
            //要用head对链表处理,初始化步骤不能错
            node.prev = head;
            node.next = head.next;
            head.next.prev = node;
            head.next = node;
        }

        //2.2  删除node
        public  void  removeNode(Node<K, V> node){
            //绕过node
            node.next.prev = node.prev;
            node.prev.next = node.next;
            //前后赋值null, 称为垃圾回收
            node.next = null;
            node.prev = null;
        }

        //2.3 获取最后一个节点，要出队
        public Node<K ,V>  getLast(){
            return  tail.prev;
        }

    }


    /**
     * 测试
     */
    public static void main(String[] args) {
        MYLRUCache cache = new MYLRUCache(3);

        //无节点，测试获取
        int get1 = cache.get(3);
        System.out.println("无cache时获取key：" + get1);

        //添加节点
        cache.put(1,1);
        cache.put(2,2);
        System.out.println("====   打印key  ====");
        cache.printKey();
        System.out.println("缓存现在的容量:" + cache.getSize());


        //添加节点
        cache.put(3,1);
        cache.put(4,2);
        System.out.println("====   打印key  ====");
        cache.printKey();

        cache.put(3,1);
        System.out.println("====   打印key  ====");
        cache.printKey();


        cache.put(1, 5);
        System.out.println("====   打印key  ====");
        cache.printKey();
        System.out.println("====   打印值 ====");
        System.out.println(cache.get(3));
        System.out.println(cache.get(1));

        System.out.println("缓存现在的容量:" + cache.getSize());
    }

}
