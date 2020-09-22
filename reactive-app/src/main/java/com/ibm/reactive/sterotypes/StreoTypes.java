package com.ibm.reactive.sterotypes;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;

public class StreoTypes {

    public static void singleType() {
        Single.create(emitter -> {
            emitter.onSuccess("Hello");
        }).subscribe(System.out::println);

        Single.create(emitter -> {
            emitter.onError(new RuntimeException("error"));
        }).subscribe(System.out::println, System.out::println);

        Single.just(1).subscribe(System.out::println);
        Single.error(new RuntimeException("error")).subscribe(System.out::println, System.out::println);
    }

    public static void maybeType() {
        //only item
        Maybe.just(1).subscribe(System.out::println);
        //only error
        Maybe.error(new RuntimeException("error")).subscribe(System.out::println, System.out::println);
        //only complete
        Maybe.empty().subscribe(System.out::println, System.out::println, () -> System.out.println("onComplete"));

    }
    public static void completeTest() {
        Completable.complete().subscribe(() -> System.out.println("Completeable"));
    }

    public static void main(String[] args) {
 //       singleType();
       // maybeType();
        completeTest();
    }
}
