package com.ibm.reactive.stream.create;

import io.reactivex.rxjava3.core.Observable;

import java.util.Arrays;
import java.util.List;

public class ArrayAndCollectionOperators {
    public static void arrayOperator() {
        Integer[] array = {1, 2, 3, 4, 5};
        Observable<Integer> stream = Observable.fromArray(array);
        stream.subscribe(System.out::println, System.out::println, () -> System.out.println("complete"));
    }
    public static void listOperator() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        Observable<Integer> stream = Observable.fromIterable(list);
        stream.subscribe(System.out::println, System.out::println, () -> System.out.println("complete"));
    }
    public static void listEmployeeOperator() {
        List<Employee> list = Arrays.asList(new Employee(1, "A"), new Employee(2, "b"));
        Observable<Employee> stream = Observable.fromIterable(list);
        stream.subscribe(System.out::println, System.out::println, () -> System.out.println("complete"));
    }


    public static void main(String[] args) {
      arrayOperator();
      listOperator();
      listEmployeeOperator();
    }
}
