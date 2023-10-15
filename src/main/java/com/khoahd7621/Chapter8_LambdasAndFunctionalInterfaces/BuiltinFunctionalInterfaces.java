package com.khoahd7621.Chapter8_LambdasAndFunctionalInterfaces;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.function.*;

public class BuiltinFunctionalInterfaces {
    class SupplierFI {
        public static void main(String[] args) {
            Supplier<LocalDate> s1 = LocalDate::now;
            Supplier<LocalDate> s2 = () -> LocalDate.now();

            LocalDate d1 = s1.get();
            LocalDate d2 = s2.get();

            System.out.println(d1);
            System.out.println(d2);
        }
    }

    class ConsumerAndBiComsumerFIs {
        public static void main(String[] args) {
            Consumer<String> c1 = System.out::println;
            Consumer<String> c2 = x -> System.out.println(x);

            c1.accept("Annie"); // Annie
            c2.accept("Annie"); // Annie

            var map = new HashMap<String, Integer>();
            BiConsumer<String, Integer> b1 = map::put;
            BiConsumer<String, Integer> b2 = (k, v) -> map.put(k, v);

            b1.accept("chicken", 7);
            b2.accept("chick", 1);
        }
    }

    class PredicateAndBiPredicateFIs {
        public static void main(String[] args) {
            Predicate<String> p1 = String::isEmpty;
            Predicate<String> p2 = x -> x.isEmpty();

            System.out.println(p1.test("")); // true
            System.out.println(p2.test("")); // true

            BiPredicate<String, String> b1 = String::startsWith;
            BiPredicate<String, String> b2 = (string, prefix) -> string.startsWith(prefix);

            System.out.println(b1.test("chicken", "chick")); // true
            System.out.println(b2.test("chicken", "chick")); // true

        }
    }

    class FunctionAndBiFunctionFis {
        public static void main(String[] args) {
            Function<String, Integer> f1 = String::length;
            Function<String, Integer> f2 = x -> x.length();

            System.out.println(f1.apply("cluck")); // 5
            System.out.println(f2.apply("cluck")); // 5

            BiFunction<String, String, String> b1 = String::concat;
            BiFunction<String, String, String> b2 = (string, toAdd) -> string.concat(toAdd);

            System.out.println(b1.apply("baby ", "chick")); // baby chick
            System.out.println(b2.apply("baby ", "chick")); // baby chick
        }
    }

    class UnaryOperatorAndBinaryOperatorFis {
        public static void main(String[] args) {
            UnaryOperator<String> u1 = String::toUpperCase;
            UnaryOperator<String> u2 = x -> x.toUpperCase();

            System.out.println(u1.apply("chirp")); // CHIRP
            System.out.println(u2.apply("chirp")); // CHIRP

            BinaryOperator<String> b1 = String::concat;
            BinaryOperator<String> b2 = (string, toAdd) -> string.concat(toAdd);

            System.out.println(b1.apply("baby ", "chick")); // baby chick
            System.out.println(b2.apply("baby ", "chick")); // baby chick
        }
    }

    class ConvenienceMethods {
        public static void main(String[] args) {
            // Predicate
            Predicate<String> egg = s -> s.contains("egg");
            Predicate<String> brown = s -> s.contains("brown");

            Predicate<String> brownEggs = egg.and(brown);
            Predicate<String> otherEggs = egg.and(brown.negate());

            System.out.println(brownEggs.test("brown egg")); // true
            System.out.println(brownEggs.test("yolk")); // false
            System.out.println(otherEggs.test("brown egg")); // false
            System.out.println(otherEggs.test("yolk")); // false

            // Consumer
            Consumer<String> c1 = x -> System.out.print("1: " + x);
            Consumer<String> c2 = x -> System.out.print(",2: " + x);

            Consumer<String> combinedConsumer = c1.andThen(c2);
            combinedConsumer.accept("Annie"); // 1: Annie,2: Anni

            // Function
            Function<Integer, Integer> before = x -> x + 1;
            Function<Integer, Integer> after = x -> x * 2;

            Function<Integer, Integer> combinedFunction = after.compose(before);
            System.out.println(combinedFunction.apply(3)); // 8
        }
    }
}
