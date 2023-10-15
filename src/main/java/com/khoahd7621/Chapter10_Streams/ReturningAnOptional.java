package com.khoahd7621.Chapter10_Streams;

import java.util.NoSuchElementException;
import java.util.Optional;

public class ReturningAnOptional {

    public static Optional<Double> average(int... scores) {
        if (scores.length == 0) return Optional.empty();
        int sum = 0;
        for (int score : scores) sum += score;
        return Optional.of((double) sum / scores.length);
    }

    public static void main(String[] args) {
        System.out.println(average(90, 100)); // Optional[95.0]
        System.out.println(average());               // Optional.empty

        Optional<Double> opt = average(90, 100);
        if (opt.isPresent())
            System.out.println(opt.get());  // 95.0

        try {
            Optional<Double> opt1 = average();
            System.out.println(opt1.get()); // NoSuchElementException
        } catch (NoSuchElementException e) {
            System.out.println(e);
        }

        Double value = null; // DOES NOT COMPILE
        Optional o = (value == null) ? Optional.empty() : Optional.of(value);
        Optional o1 = Optional.ofNullable(value);

        Optional<Double> opt2 = average(90, 100);
        opt2.ifPresent(System.out::println);

        // Dealing with an Empty Optional
        Optional<Double> opt3 = average(90, 100);
        System.out.println(opt3.orElse(Double.NaN));
        System.out.println(opt3.orElseGet(() -> Math.random()));
        System.out.println(opt3.orElseThrow());
    }
}
