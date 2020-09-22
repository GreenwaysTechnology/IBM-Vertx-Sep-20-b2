package com.ibm.reactive.stream.create;

import io.reactivex.rxjava3.core.Observable;
import java.util.concurrent.TimeUnit;

public class InteveralOperator {
    public static void main(String[] args)throws  Exception {
        System.out.println("interval");
        //interval kicks start emitting data after 1000ms only,but main method will be closed before that
        //so we cant see the output.
        //if i want output, pause main method(thread) for certain time
        Observable<Long> stream = Observable.interval(1000, TimeUnit.MILLISECONDS);
        stream.subscribe(System.out::println, System.out::println, () -> System.out.println("complete"));
        Thread.sleep(10000);
    }
}
