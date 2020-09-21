package com.ibm.fp.interfaces;

interface Greeter {
    //abstract method
    void sayGreet();
}

class GreeterImpl implements Greeter {
    @Override
    public void sayGreet() {
        System.out.println("Greet!!!");
    }
}


public class InterfacesMain {
    public static void main(String[] args) {
        Greeter greeter = new GreeterImpl();
        greeter.sayGreet();
    }
}
