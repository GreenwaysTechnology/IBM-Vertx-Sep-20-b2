package com.ibm.fp.generics;

interface Handler<E> {
    void handle(E event);
}

interface Future<T> {
    Future<T> onComplete(Handler<T> handler);
}

class Box<T> {
    // T stands for "Type"
    private T t;

    public void set(T t) {
        this.t = t;
    }

    public T get() {
        return t;
    }
}

public class CustomGenerics {
    public static void main(String[] args) {
        Box<Integer> box = new Box<>();
        box.set(10);
        System.out.println(box.get() * 10);

    }

}
