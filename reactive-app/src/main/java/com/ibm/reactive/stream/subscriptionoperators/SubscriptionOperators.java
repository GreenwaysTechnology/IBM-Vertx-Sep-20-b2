package com.ibm.reactive.stream.subscriptionoperators;

import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

public class SubscriptionOperators {
    public static void simpleSubscription() {
        Observable
                .range(1, 10)
                .subscribe(System.out::println, System.out::println, () -> System.out.println("done"));

        Observable
                .range(1, 10)
                .doOnSubscribe(c -> System.out.println("on subscription"))
                .doOnNext(data -> System.out.println("do on next"))
                .doOnError(err -> System.out.println("do on error"))
                .doOnComplete(() -> System.out.println("do on complete"))
                .doFinally(() -> System.out.println("do on finally"))
                .subscribe();

        Observable
                .interval(1000, TimeUnit.MILLISECONDS)
                .doOnSubscribe(c -> System.out.println("on subscription"))
                .doOnNext(data -> System.out.println("do on next" + data))
                .doOnError(err -> System.out.println("do on error"))
                .doOnComplete(() -> System.out.println("do on complete"))
                .doFinally(() -> System.out.println("do on finally"))
                .blockingLast() ; //block the main thread infinitely


    }

    public static void main(String[] args) {
        simpleSubscription();
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException exception) {
//            exception.printStackTrace();
//        }
    }
}
