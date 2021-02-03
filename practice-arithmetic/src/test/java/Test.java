import org.jcp.xml.dsig.internal.dom.DOMUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author embrace
 * @describe
 * @date created in 2020/12/18 17:05
 */
public class Test {



    static class  UserInfo{
        String  name;
        Double price;
        Double weight;

        public UserInfo(String name, Double price, Double weight) {
            this.name = name;
            this.price = price;
            this.weight = weight;
        }

        public String getName() {
            return name;
        }

        public Double getPrice() {
            return price;
        }

        public Double getWeight() {
            return weight;
        }
    }


    public static void main(String[] args) {
        List<Map<String, Double>> li = new ArrayList<>();
        Map<String, Double> map1 = new HashMap() {{
            put("guid",1.1);
            put("weight",2.0);
            put("price",3.0);
        }};
        Map<String, Double> map2 = new HashMap() {{
            put("guid",2.0);
            put("weight",3.0);
            put("price",4.0);
        }};
        li.add(map1);
        li.add(map1);
        li.add(map2);

        //stream 流对  groupby 并且  sum(weight * price), 成为Map<String,Object>
        // 类似sql 的 sum(weight * price)  money  form  table group by guid

        //collect(Collectors.groupingBy( m -> m.get("guid").toString()))
        List<Object>  list  =  li.stream().map(m -> m.get("price") ).collect(Collectors.toList());
        System.out.println(list);  //[3, 3, 3, 3, 4, 4]

        Map<String,List<Map<String, Double>>>  list2 = li.stream().collect(Collectors.groupingBy( m -> m.get("guid").toString()));
        System.out.println( list2);//{1=[{price=3, guid=1, weight=2}, {price=3, guid=1, weight=2}], 2=[{price=4, guid=2, weight=3}]}
//
//        Map<String,List<Map<String, Object>>>  list3 = li.stream().map(m -> m.put("amount","6")).
//                collect(Collectors.groupingBy( m ->    ));
        System.out.println(li.parallelStream().
                collect(Collectors.groupingBy(m -> m.get("guid"),
                        Collectors.summingDouble(m -> Double.valueOf(m.get("weight"))))));

        //userList
        List<UserInfo> userInfos = new ArrayList<>();
        userInfos.add(new  UserInfo("zhangsan",2.0,3d) );
        userInfos.add(new  UserInfo("zhangsan",2.0,3d) );
        userInfos.add(new  UserInfo("lisi",1.0,3d) );
        userInfos.add(new  UserInfo("lisi",1.0,3d) );

        Map<String,Double> map =    userInfos.parallelStream().
               collect(Collectors.groupingBy(m -> m.getName(),
                       Collectors.summingDouble(m -> m.getPrice() * m.getWeight() )));
        System.out.println(map);

        String[] a = new  String[]{"2020-12-30","2020-12-31","2020-01-01","2020-01-02","2020-01-02"};
        List<String> lis = Arrays.stream(a).map(s -> s.substring(0,7)).collect(Collectors.toList());
        lis.forEach( s->
                        System.out.println(s)
                );


        Map<String,Integer> mm = Arrays.stream(a).map(s -> s.substring(0,7)).collect(Collectors.groupingBy(m -> m,
                Collectors.summingInt(m ->  1 )));
        for(String key : mm.keySet()){
            System.out.println(key  +  "  "  + mm.get(key));
        }



    }


}
