package com.ibm.reactive.stream.create;

import io.reactivex.rxjava3.core.Observable;

public class StreamCreateOperator {
    public static void main(String[] args) {

        //stream creation using create operator.
        //producer
        Observable<Integer> stream = Observable.create(emitter -> {
            //push the data into event channel
            try {
                boolean isError = false;
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onNext(4);
                emitter.onNext(5);
                //try throw some error
                if (isError) {
                    throw new RuntimeException("Something went wrong");
                }
                emitter.onNext(6);
                emitter.onNext(7);
                emitter.onComplete();
                //  emitter.onNext(8);

            } catch (Throwable e) {
                emitter.onError(e);
            }
        });
        //subscriber; ondata, on error,oncomplete
        //   stream.subscribe(data -> System.out.println(data), err -> System.out.println(err), () -> System.out.println("Complete"));

        stream.subscribe(System.out::println, System.out::println, () -> System.out.println("Complete"));


    }
}
