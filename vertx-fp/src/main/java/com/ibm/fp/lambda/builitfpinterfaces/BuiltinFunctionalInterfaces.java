package com.ibm.fp.lambda.builitfpinterfaces;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

//@FunctionalInterface
//interface HttpHandler {
//    void handle(Object response);
//}

class HttpServer {
    public void requestHandler(Consumer httpHandler) {
        httpHandler.accept("Hello,Response");
    }
}

public class BuiltinFunctionalInterfaces {
    public static void main(String[] args) {
        Consumer<String> consumer = null;
        consumer = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        };
        consumer.accept("hello");
        consumer = s -> System.out.println(s);
        consumer.accept("hai");
        consumer = System.out::println;
        consumer.accept("hai");

        HttpServer httpServer = new HttpServer();
        httpServer.requestHandler(res -> System.out.println(res));
        httpServer.requestHandler(System.out::println);

        //Supplier
        Supplier<Integer> supplier = null;
        supplier = () -> 10;
        System.out.println(supplier.get());

        //Predicate
        Predicate<Integer> predicate = null;
        predicate = num -> num > 10;
        System.out.println(predicate.test(2));
        System.out.println(predicate.test(20));

        //Function
        Function<Integer, Integer> function = null;
        function = num -> num * 2;
        System.out.println(function.apply(10));


    }
}
