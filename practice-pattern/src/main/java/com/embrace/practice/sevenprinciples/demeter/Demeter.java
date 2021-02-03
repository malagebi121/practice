package com.embrace.practice.sevenprinciples.demeter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/12 22:13
 */
public class Demeter {
    public static void main(String[] args) {
        SchoolManager schoolManager = new SchoolManager();
        schoolManager.PrintAll(new CollegeManager());
    }
}

//学校员工类
class Employee {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

//学院员工类
class CollegeEmployee{
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

// 学院员工类
class CollegeManager{
    public List<CollegeEmployee> getEmployee(){
        List<CollegeEmployee> arrayList = new ArrayList();
        for (int i = 0; i < 10; i++) {
            CollegeEmployee collegeEmployee = new CollegeEmployee();
            collegeEmployee.setId("学院员工第" + i);
            arrayList.add(collegeEmployee);
        }
        return arrayList;
    }

    // 自己的方法留在自己里面
    public void print(){
        // 这段代码有问题
        List<CollegeEmployee> employee = getEmployee();
        employee.forEach(e  -> System.out.println(e.getId()));
    }
}

//学校员工类

/**
 * Employee 直接朋友
 * CollegeManager 直接朋友
 * CollegeEmployee 不是直接朋友（局部变量了），违背了迪米特原则
 */
class SchoolManager{
    public List<Employee> getEmployee(){
        List<Employee> arrayList = new ArrayList();
        for (int i = 0; i < 5; i++) {
            Employee employee = new Employee();
            employee.setId("学校总部员工第" + i);
            arrayList.add(employee);
        }
        return arrayList;
    }

    // 输出所有的学生,学院的和学校的
    public void PrintAll(CollegeManager collegeManager){
//        // 这段代码有问题
//        List<CollegeEmployee> employee = collegeManager.getEmployee();
//        employee.forEach(e  -> System.out.println(e.getId()));
        collegeManager.print();

        List<Employee> employee1 = this.getEmployee();
        employee1.forEach(e  -> System.out.println(e.getId()));

    }


}
