package com.ibm.reactive.stream.create;

import io.reactivex.rxjava3.core.Observable;

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

public class JustOperator {
    public static void intStream() {
        Observable<Integer> stream = Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9);
        stream.subscribe(System.out::println, System.out::println, () -> System.out.println("Complete"));

    }
    public static void stringStream() {
        Observable<String> stream = Observable.just("reative","rxjava","reactor");
        stream.subscribe(System.out::println, System.out::println, () -> System.out.println("Complete"));

    }
    public static void empStream() {
        Observable<Employee> stream = Observable.just(new Employee(1,"A"),new Employee(2,"B"));
        stream.subscribe(System.out::println, System.out::println, () -> System.out.println("Complete"));

    }
    public static void main(String[] args) {
      intStream();
      stringStream();
      empStream();
    }
}
