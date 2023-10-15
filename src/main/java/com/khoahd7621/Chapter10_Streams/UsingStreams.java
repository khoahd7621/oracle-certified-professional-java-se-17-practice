package com.khoahd7621.Chapter10_Streams;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UsingStreams {}

class CreateFiniteStream {
    public static void main(String[] args) {
        Stream<String> empty = Stream.empty();          // count = 0
        Stream<Integer> singleElement = Stream.of(1);   // count = 1
        Stream<Integer> fromArray = Stream.of(1, 2, 3); // count = 3

        var list = List.of("a", "b", "c");
        Stream<String> fromList = list.stream(); // Creating sequential stream
        Stream<String> fromListParallel = list.parallelStream(); // Creating parallel string
    }
}

class CreateInfiniteStream {
    public static void main(String[] args) {
        Stream<Double> randoms = Stream.generate(Math::random);
        randoms.forEach(System.out::println); // Infinite stream will print random numbers until you kill it
        Stream<Integer> oddNumbers = Stream.iterate(
                1,         // seed
                n -> n + 2);    // UnaryOperator to get next value
        oddNumbers.forEach(System.out::println); // Infinite stream will print odd numbers until you kill it
        Stream<Integer> oddNumberUnder100 = Stream.iterate(
                1,         // seed
                n -> n < 100,   // Predicate to specify when done
                n -> n + 2);    // UnaryOperator to get next value
        oddNumberUnder100.forEach(System.out::println); // Infinite stream will print odd numbers until you kill it
    }
}

// Terminal operation
class CommonStreamOperations {
    public static void main(String[] args) {
        // 1. Counting
        Stream<String> s = Stream.of("monkey", "ape", "bonobo");
        System.out.println(s.count()); // 3

        // 2. Min/Max
        Optional<String> min = s.min((s1, s2) -> s1.length() - s2.length());
        min.ifPresent(System.out::println); // ape

        Optional<?> minEmpty = Stream.empty().min((s1, s2) -> 0);
        System.out.println(minEmpty.isPresent()); // false

        // 3. Finding a Value
        Stream<String> infinite = Stream.generate(() -> "chimp");

        s.findAny().ifPresent(System.out::println);         // monkey (usually)
        infinite.findAny().ifPresent(System.out::println);  // chimp

        // 4. Matching
        var list = List.of("monkey", "2", "chimp");
        Predicate<String> pred = x -> Character.isLetter(x.charAt(0));

        System.out.println(list.stream().anyMatch(pred));   // true
        System.out.println(list.stream().allMatch(pred));   // false
        System.out.println(list.stream().noneMatch(pred));  // false
        System.out.println(infinite.anyMatch(pred));        // true

        // 5. Iterating
        s.forEach(System.out::print); // MonkeyGorillaBonobo

        // 6. Reducing
        var array = new String[] { "w", "o", "l", "f" };
        var result = "";
        for (var c: array) result = result + c;
        System.out.println(result); // wolf

        // Type 1: T reduce(T identity, BinaryOperator<T> accumulator)
        Stream<String> stream = Stream.of("w", "o", "l", "f");
        String word = stream.reduce("", (z, c) -> z + c);
        System.out.println(word); // wolf

        String wordMethodReference = stream.reduce("", String::concat);
        System.out.println(wordMethodReference); // wolf

        // Type 2: Optional<T> reduce(BinaryOperator<T> accumulator)
        /*
        * When you don’t specify an identity, an Optional is returned because there might
        * not be any data. There are three choices for what is in the Optional:
        * * If the stream is empty, an empty Optional is returned.
        * * If the stream has one element, it is returned.
        * * If the stream has multiple elements, the accumulator is applied to combine them.
        * */
        BinaryOperator<Integer> op = (a, b) -> a * b;
        Stream<Integer> empty = Stream.empty();
        Stream<Integer> oneElement = Stream.of(3);
        Stream<Integer> threeElements = Stream.of(3, 5, 6);

        empty.reduce(op).ifPresent(System.out::println);         // no output
        oneElement.reduce(op).ifPresent(System.out::println);    // 3
        threeElements.reduce(op).ifPresent(System.out::println); // 90

        // Type 3: <U> U reduce(U identity, BiFunction<U,? super T,U> accumulator, BinaryOperator<U> combiner)
        int length = stream.reduce(0, (i, z) -> i + z.length(), (a, b) -> a + b);
        System.out.println(length); // 5

        // 7. Collecting
        // Type 1: public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner)

        StringBuilder wordCollecting = stream.collect(
                StringBuilder::new,
                StringBuilder::append,
                StringBuilder::append);
        System.out.println(wordCollecting); // wolf

        Stream<String> otherStream = Stream.of("w", "o", "l", "f");
        TreeSet<String> set = otherStream.collect(
                TreeSet::new,
                TreeSet::add,
                TreeSet::addAll);
        System.out.println(set); // [f, l, o, w]

        // Type 2: public <R, A> R collect(Collector<? super T, A, R> collector)
        TreeSet<String> otherSet =
                otherStream.collect(Collectors.toCollection(TreeSet::new));
        System.out.println(otherSet); // [f, l, o, w]

        Set<String> otherSet1 = otherStream.collect(Collectors.toSet());
        System.out.println(otherSet1); // [f, w, l, o]
    }
}

class CommonIntermediateOperations {
    public static void main(String[] args) {
        // 1. Filtering
        Stream<String> s = Stream.of("monkey", "gorilla", "bonobo");
        s.filter(x -> x.startsWith("m")).forEach(System.out::print); // monkey

        // 2. Removing duplicates
        Stream<String> s1 = Stream.of("duck", "duck", "duck", "goose");
        s1.distinct().forEach(System.out::print); // duckgoose

        // 3. Restricting by Position
        Stream<Integer> s2 = Stream.iterate(1, n -> n + 1);
        s2.skip(5).limit(2).forEach(System.out::print); // 67

        // 4. Mapping
        Stream<String> s3 = Stream.of("monkey", "gorilla", "bonobo");
        s3.map(String::length).forEach(System.out::print); // 676

        // 5. FlatMap
        List<String> zero = List.of();
        var one = List.of("Bonobo");
        var two = List.of("Mama Gorilla", "Baby Gorilla");
        Stream<List<String>> animals = Stream.of(zero, one, two);

        animals.flatMap(m -> m.stream()).forEach(System.out::println);
        /*
        * Result:
        * Bonobo
        * Mama Gorilla
        * Baby Gorilla
        * */

        // While flatMap() is good for the general case, there
        // is a more convenient way to concatenate two streams
        var streamOne = Stream.of("Bonobo");
        var streamTwo = Stream.of("Mama Gorilla", "Baby Gorilla");
        Stream.concat(streamOne, streamTwo).forEach(System.out::println);
        // This produces the same three lines as the previous example.
        // The two streams are concatenated, and the terminal operation,
        // forEach(), is called.

        // 6. Sorting
        Stream<String> s4 = Stream.of("brown-", "bear-");
        s4.sorted().forEach(System.out::print); // bear-brown-

        Stream<String> s5 = Stream.of("brown bear-", "grizzly-");
        s5.sorted(Comparator.reverseOrder()).forEach(System.out::print); // grizzly-brown bear-

        // 7. Taking a Peek
        // The peek() method is our final intermediate operation. It is useful for debugging because
        // it allows us to perform a stream operation without changing the stream.

        // The most common use for peek() is to output the contents of the stream as it goes by.
        // Suppose that we made a typo and counted bears beginning with the letter g instead of b.
        // We are puzzled why the count is 1 instead of 2. We can add a peek() method to find out why.
        var stream = Stream.of("black bear", "brown bear", "grizzly");
        long count = stream.filter(u -> u.startsWith("g"))
                .peek(System.out::println).count(); // grizzly
        System.out.println(count); // 1
    }
}
