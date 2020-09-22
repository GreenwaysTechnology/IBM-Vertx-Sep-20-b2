package com.ibm.reactive.concurrency;


import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ReactiveThreading {
    public static void observeOn() {
        Observable.range(1, 5)
                .map(i -> {
                    System.out.println("map 1 " + Thread.currentThread().getName());
                    return i * 2;
                })
                .observeOn(Schedulers.computation()) //declarative
                .map(i -> {
                    System.out.println("map 2 " + Thread.currentThread().getName());
                    return i * 2;
                })
                .observeOn(Schedulers.newThread()) //declarative
                .map(i -> {
                    System.out.println("map 3 " + Thread.currentThread().getName());
                    return i * 3;
                })
                .subscribe();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    public static void subscribeOn() {
        Observable.range(1, 5)
                .map(i -> {
                    System.out.println("map 1 " + Thread.currentThread().getName());
                    return i * 2;
                })
               // .observeOn(Schedulers.newThread())
                .map(i -> {
                    System.out.println("map 2 " + Thread.currentThread().getName());
                    return i * 2;
                })
                .subscribeOn(Schedulers.computation())
                .map(i -> {
                    System.out.println("map 3 " + Thread.currentThread().getName());
                    return i * 3;
                })
                .subscribe();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) {
        subscribeOn();
    }
}
