package com.ibm.fp.lambda.refactoring;

@FunctionalInterface
interface Greeter {
    void sayGreet();
}

//parameters and args
@FunctionalInterface
interface Hello {
    //name is args
    void sayHello(String name);
}

//multi parameter
@FunctionalInterface
interface Calculator {
    void add(int a, int b);
}

//return values
@FunctionalInterface
interface Stock {
    int getStockValue();
}

//args,return value
@FunctionalInterface
interface Adder {
    int add(int a, int b);
}

public class LambdaCodeRefactoring {
    public static void main(String[] args) {
        Greeter greeter = null;
        greeter = () -> {
            System.out.println("Greeter");
        };
        greeter.sayGreet();
        //refactoring 1: if function has only one line of body. you can remove {};
        greeter = () -> System.out.println("Greeter");
        greeter.sayGreet();
        //args and parameters

        Hello hello = null;
        //args -firstName
        hello = (String firstName) -> System.out.println("Hello " + firstName);
        //call function
        //"Subramanian" - parameters
        hello.sayHello("Subramanian");
        hello.sayHello("Ram");

        //Refactoring : args and parameters : you can remove data type;type is understood
        //type inference : the type of variable is verified against interface abstract method type.
        hello = (firstName) -> System.out.println("Hello " + firstName);
        hello.sayHello("Subramanian");
        //hello.sayHello(12);
        //Reactoring for single args: you can remove () if you have single arg
        hello = firstName -> System.out.println("Hello " + firstName);
        hello.sayHello("Subramanian");
        //multi parameters and args

        Calculator calculator = null;
        calculator = (int a, int b) -> {
            int result = a + b;
            System.out.println("The Add Result is " + result);
        };
        calculator.add(10, 10);

        calculator = (a, b) -> {
            int result = a + b;
            System.out.println("The Add Result is " + result);
        };
        calculator.add(10, 10);
        //return values
        Stock stock = null;
        stock = () -> {
            return 10;
        };
        System.out.println(stock.getStockValue());
        //return value reactoring  : if funciton has only return statement , we can remove return statement and{}
        stock = () -> 10;
        System.out.println(stock.getStockValue());
        ///////////////////////////////////////////////////////////////////////////////////////
        Adder adder = null;
        adder = (a, b) -> {
            return a + b;
        };
        System.out.println(adder.add(1, 4));
        adder = (a, b) -> a + b;
        System.out.println(adder.add(1, 4));


    }
}
