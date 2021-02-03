package com.embrace.practice.designpattern.factory.simplefactory;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author embrace
 * @describe   订购奶茶
 * @date created in 2021/1/13 16:50
 */
public class OrderMilkTea {
    SimpleFactory simpleFactory;
    MilkTea milkTea;

    public OrderMilkTea(SimpleFactory simpleFactory){
        setFactory(simpleFactory);
    }

    public void setFactory(SimpleFactory simpleFactory) {
        this.simpleFactory = simpleFactory;
        String orderType = "";
        do{
            orderType = getType();
            milkTea = simpleFactory.CreateMilkTea(orderType);
            makeMilkTea(milkTea);
        }while (true);

    }


    //    public OrderMilkTea(){
//        MilkTea milkTea = null;
//        String orderType;
//        do{
//            orderType = getType();
//            if(orderType.equals("珍珠")){
//                milkTea = new ZhenZhuMilkTea();
//            }else if(orderType.equals("坚果")){
//                milkTea = new JianGuoMilkTea();
//            }
////            else if(orderType.equals("XXX")){//新添加一种奶茶要修改
////
////            }
//            makeMilkTea(milkTea);
//        }while (true);
//    }

    private void makeMilkTea(MilkTea milkTea) {
        if(milkTea != null){
            milkTea.prepare();
            milkTea.addWater();
            milkTea.mix();
            milkTea.pack();
        }
    }

    private String getType(){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("输入想要的奶茶");
        String s = "";
        try {
            s = bufferedReader.readLine();
//            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }
}
