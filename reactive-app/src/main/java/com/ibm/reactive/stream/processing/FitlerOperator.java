package com.ibm.reactive.stream.processing;

import io.reactivex.rxjava3.core.Observable;

public class FitlerOperator {
    public static void evenNumbers() {
        Observable.range(1, 50)
                .map(j -> {
                    System.out.println("Map: up stream got value " + j);
                    return j * 2;
                })
                .filter(item -> { //upstream
                    System.out.println("Got : " + item);
                    return item % 2 == 0; //data will be supplied to down stream, only condition to true
                })
                .map(i -> { //down stream
                    System.out.println("Map: down stream got value " + i);
                    return i;
                })
                .subscribe(System.out::println, System.out::println, () -> System.out.println("complete"));

    }

    public static void startsWith() {
        Observable
                .just("arun", "subramanian", "karthik", "deepti", "geetha")
                .filter(name -> {
                    return name.startsWith("a");
                })
                .subscribe(System.out::println, System.out::println, () -> System.out.println("complete"));

        Observable
                .just("arun", "subramanian", "karthik", "deepti", "geetha")
                .filter(name -> {
                   // System.out.println(name);
                    return name.startsWith("x");
                })
                .defaultIfEmpty("None")
                .subscribe(System.out::println, System.out::println, () -> System.out.println("complete"));

    }

    public static void main(String[] args) {
        startsWith();

    }
}
