package com.ibm.fp.lambda;

//functional interface, to activate lambda calculs ; functional programming
interface Greeter {
    //abstract method
    void sayGreet();
}


public class LambdaDeclration {
    public static void main(String[] args) {

        Greeter greeter = null;
        //innerclasses
        greeter = new Greeter() {
            @Override
            public void sayGreet() {
                System.out.println("Greeter");
            }
        };
        greeter.sayGreet();

        //lambda syntax
        greeter = () -> {
            System.out.println("Greeter-lambda");
        };
        greeter.sayGreet();


    }
}
