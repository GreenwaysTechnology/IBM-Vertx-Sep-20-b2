package com.ibm.fp.lambda.rules;


@FunctionalInterface
interface Greeter {
    //abstract method
    void sayGreet();
    //
   // void sayHello();
    public default  void saySomething(){
        System.out.println("Default methods");
    }
    //static methods
    public static void doSomething(){
        System.out.println("Static methods");
    }
}

public class LambdaBasicRules {
    public static void main(String[] args) {
        Greeter greeter = null;
        //lambda syntax
        greeter = () -> {
            System.out.println("Greeter-lambda");
        };
        greeter.sayGreet();
        greeter.saySomething();
        Greeter.doSomething();
    }
}
