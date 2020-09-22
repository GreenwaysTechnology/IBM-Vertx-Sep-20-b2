package com.ibm.reactive.stream.processing;

import io.reactivex.rxjava3.core.Observable;

public class ZipOperator {

    public static void zip() {
        Observable<Integer> intStream = Observable.just(1, 2, 3, 4);
        Observable<String> stringStream = Observable.just("a", "b", "c", "d", "e");

        Observable.zip(intStream, stringStream, (i, str) -> {
            System.out.println("I " + i + " String " + str);
            return i + str;
        }).subscribe(System.out::println, System.out::println, () -> System.out.println("Done"));

    }

    public static void main(String[] args) {
        zip();
    }
}
