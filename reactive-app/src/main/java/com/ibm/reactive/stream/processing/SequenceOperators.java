package com.ibm.reactive.stream.processing;

import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

public class SequenceOperators {
    public static void main(String[] args) {
        Observable
                .just("--", "this", "is", "--", "a", "sequence", "of", "items", "!")
                .doOnSubscribe(d -> System.out.println("Subscribed!"))
                .delay(5, TimeUnit.SECONDS)
                .filter(s -> !s.startsWith("--"))
                .doOnNext(x -> System.out.println("doOnNext: " + x))
                .map(String::toUpperCase)
                .buffer(2)
                .subscribe(
                        pair -> System.out.println("next: " + pair),
                        Throwable::printStackTrace,
                        () -> System.out.println("~Done~"));
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }

    }
}
