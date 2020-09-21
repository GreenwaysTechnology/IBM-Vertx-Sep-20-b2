package com.ibm.fp.interfaces.anonmous;

interface Greeter {
    void sayGreet();
}

public class AnonmousInnerMain {
    public static void main(String[] args) {
        //inner classes;annonmous inner classes
        Greeter greeter = null;
        greeter = new Greeter() {
            @Override
            public void sayGreet() {
                System.out.println("Greeter");
            }
        };
        greeter.sayGreet();
        greeter = new Greeter() {
            @Override
            public void sayGreet() {
                System.out.println("Say Hello");
            }
        };
        greeter.sayGreet();

        greeter = new Greeter() {
            @Override
            public void sayGreet() {
                System.out.println("Say Hai");
            }
        };
        greeter.sayGreet();


    }
}
