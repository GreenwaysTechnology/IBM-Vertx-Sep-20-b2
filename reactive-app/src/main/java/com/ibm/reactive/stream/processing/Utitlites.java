package com.ibm.reactive.stream.processing;

import io.reactivex.rxjava3.core.Observable;

import java.util.Arrays;
import java.util.List;

public class Utitlites {
    public static void utilties() {
        List<String> words = Arrays.asList("the", "quick", "quick", "brown", "fox", "apple", "fox", "jumped", "over", "the", "lazy", "dog");
        Observable<String> manyLetters = Observable.fromIterable(words)
                .flatMap(word -> Observable.just(word))
                .distinct() // boolean  operator will eleminate duplicates
                .sorted();  // operator which sorting.
        manyLetters.subscribe(System.out::println);
    }

    public static void main(String[] args) {
        utilties();
    }
}
