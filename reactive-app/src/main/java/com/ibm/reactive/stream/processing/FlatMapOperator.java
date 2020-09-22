package com.ibm.reactive.stream.processing;

import io.reactivex.rxjava3.core.Observable;

public class FlatMapOperator {
    public static void flatMap() {
//        Observable.just(1, 2, 3, 4, 5)
//                .map(i -> {
//                            System.out.println("map return vaule");
//                            return i;// return only value
//                        }
//                ).subscribe(System.out::println, System.out::println, () -> System.out.println("Done"));

        Observable.just(1, 2, 3, 4, 5)
                .flatMap(i -> {
                            System.out.println("source value " + i);
                            return Observable.just(2, 3, 8, 9);
                        }
                )
                .map(j -> {
                  //  System.out.println("Previous flat map value : " + j);
                    return j * 2;
                }).subscribe(System.out::println, System.out::println, () -> System.out.println("Done"));

    }

    public static void main(String[] args) {
        flatMap();
    }
}
