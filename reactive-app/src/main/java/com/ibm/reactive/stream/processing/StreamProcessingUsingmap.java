package com.ibm.reactive.stream.processing;

import io.reactivex.rxjava3.core.Observable;
import java.util.Arrays;
import java.util.List;

class Employee {
    private int id;
    private String name;

    Employee() {
    }

    public Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

public class StreamProcessingUsingmap {

    public static void multiplyByTwo() {
        Observable
                .range(1, 20) //source stream --- up stream
                .map(element -> {  // down stream -----upstream
                    System.out.println("first map called for " + element);
                    return element * 2;
                })
                .map(element -> { //down stream ----upstream
                    System.out.println("second map called for " + element);
                    return element;
                })
                .subscribe(System.out::println, System.out::println, () -> System.out.println("complete"));
    }
    public static void empUpperCase(){
        List<Employee> list = Arrays.asList(new Employee(1, "A"), new Employee(2, "b"));
        Observable
                .fromIterable(list)
                .map(employee -> {
                    return employee.getName().toUpperCase();
                })
                .subscribe(System.out::println, System.out::println, () -> System.out.println("complete"));
    }

    public static void main(String[] args) {
        multiplyByTwo();
        empUpperCase();
    }
}
