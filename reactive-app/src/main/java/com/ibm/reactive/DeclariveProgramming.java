package com.ibm.reactive;

import java.util.Arrays;
import java.util.List;

public class DeclariveProgramming {
    public static void main(String[] args) {
        //declartive pattern
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        /**
         * req : i need sum of even numbers from the list
         */
        //declartive programming via java streams.
        int result = numbers.stream()
                .filter(e -> e % 2 == 0) //function: declarative
                .mapToInt(e -> e)    //funtional: declarative
                .sum();                 //declarative
        System.out.println(result);

    }
}
