package com.ibm.fp.lambda.methodreference;

@FunctionalInterface
interface Printer {
    void print(String message);
}

@FunctionalInterface
interface UpperCase {
    String convertToUpper(String message);
}


class MicroTask {
    public static void runMicroTaskStatic() {
        System.out.println(Thread.currentThread().getName());
    }

    public void runMicroTask() {
        System.out.println(Thread.currentThread().getName());
    }
}

class Task {

    //instance method
    private void runTask() {
        System.out.println(Thread.currentThread().getName());
    }

    public void startTask() {
        Thread thread = null;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        });
        thread.start();
        thread = new Thread(() -> System.out.println(Thread.currentThread().getName()));
        thread.start();
        //isloate thread runnable target into separate variable
        Runnable runner = () -> System.out.println(Thread.currentThread().getName());
        thread = new Thread(runner);
        thread.start();
        //isloate the runntable target into separate method
        thread = new Thread(this::runTask);
        thread.start();
        //isloate into a separate class
        //lambda expression pattern
        MicroTask microTask = new MicroTask();
        thread = new Thread(() -> microTask.runMicroTask());
        thread.start();
        thread = new Thread(() -> new MicroTask().runMicroTask());
        thread.start();
        //method reference
        thread = new Thread(microTask::runMicroTask);
        thread.start();
        thread = new Thread(new MicroTask()::runMicroTask);
        thread.start();
        //static method
        thread = new Thread(MicroTask::runMicroTaskStatic);
        thread.start();
    }

}

public class MethodReference {
    public static void main(String[] args) {
        Task task = new Task();
        task.startTask();

        //Printer interface
        Printer printer = null;

        //lambda syntax
        printer = (message) -> System.out.println(message);
        printer.print("Hello");
        printer = System.out::println;
        printer.print("Hello");

        UpperCase upperCase = null;
        upperCase = message -> message.toUpperCase();
        System.out.println(upperCase.convertToUpper("subramanian"));

        upperCase = String::toUpperCase;
        System.out.println(upperCase.convertToUpper("subramanian"));

    }
}
