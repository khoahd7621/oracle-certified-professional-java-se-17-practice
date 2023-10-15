# Working with Primitive Streams

Up until now, all of the streams we’ve created used the Stream interface with a generic type,
like `Stream<String>`, `Stream<Integer>`, and so on. For numeric values, we have been using
wrapper classes. We did this with the Collections API in Chapter 9, so it should feel natural. <br />

&emsp;&emsp;
Java actually includes other stream classes besides Stream that you can use to work with
select primitives: int, double, and long. Let’s take a look at why this is needed. Suppose that
we want to calculate the sum of numbers in a finite stream:

```java
Stream<Integer> stream = Stream.of(1, 2, 3);
System.out.println(stream.reduce(0, (s, n) -> s + n)); // 6
```

&emsp;&emsp;
Not bad. It wasn’t hard to write a reduction. We started the accumulator with zero. We
then added each number to that running total as it came up in the stream. There is another
way of doing that, shown here:

```java
Stream<Integer> stream = Stream.of(1, 2, 3);
System.out.println(stream.mapToInt(x -> x).sum()); // 6
```

&emsp;&emsp;
This time, we converted our `Stream<Integer>` to an `IntStream` and asked the
`IntStream` to calculate the sum for us. An `IntStream` has many of the same intermediate
and terminal methods as a Stream but includes specialized methods for working with
numeric data. The primitive streams know how to perform certain common operations
automatically. <br />

&emsp;&emsp;
So far, this seems like a nice convenience but not terribly important. Now think about
how you would compute an average. You need to divide the sum by the number of elements.
The problem is that streams allow only one pass. Java recognizes that calculating an average
is a common thing to do, and it provides a method to calculate the average on the stream
classes for primitives.

```java
IntStream intStream = IntStream.of(1, 2, 3);
OptionalDouble avg = intStream.average();
System.out.println(avg.getAsDouble()); // 2.0
```

&emsp;&emsp;
Not only is it possible to calculate the average, but it is also easy to do so. Clearly, 
primitive streams are important. We look at creating and using such streams, including optionals
and functional interfaces.

## I. Creating Primitive Streams
Here are the three types of primitive streams:
- **IntStream**: Used for the primitive types int, short, byte, and char
- **LongStream**: Used for the primitive type long
- **DoubleStream**: Used for the primitive types double and float

&emsp;&emsp;
Why doesn’t each primitive type have its own primitive stream? These three are the most
common, so the API designers went with them.

> **Tip**: <br />
> When you see the word stream on the exam, pay attention to the case.
With a capital S or in code, Stream is the name of a class that contains an
Object type. With a lowercase s, a stream is a concept that might be a
Stream, DoubleStream, IntStream, or LongStream.

&emsp;&emsp;
Table 10.5 shows some of the methods that are unique to primitive streams. Notice that
we don’t include methods in the table like `empty()` that you already know from the Stream
interface.

> **Table 10.5**: Common primitive stream methods
> 
> |Method|Primitive stream| Description                                                        |
> |------|----------------|--------------------------------------------------------------------|
> |OptionalDouble average()|IntStream <br/> LongStream <br/> DoubleStream| Arithmetic mean of elements                                        |
> |Stream<T> boxed()|IntStream <br/> LongStream <br/> DoubleStream| Stream<T> where T is wrapper class associated with primitive value |
> |OptionalInt max()|IntStream| Maximum element of stream                                          |
> |OptionalLong max() |LongStream| Maximum element of stream                                          |
> |OptionalDouble max() |DoubleStream| Maximum element of stream                                          |
> |OptionalInt min()|IntStream| Minimum element of stream                                          |
> |OptionalLong min()|LongStream| Minimum element of stream                                          |
> |OptionalDouble min()|DoubleStream| Minimum element of stream                                          |
> |IntStream range(int a, int b)|IntStream| Returns primitive stream from a (inclusive) to b (exclusive)       |
> |LongStream range(long a, long b)|LongStream| Returns primitive stream from a (inclusive) to b (exclusive)       |
> |IntStream rangeClosed(int a, int b)|IntStream| Returns primitive stream from a (inclusive) to b (inclusive)       |
> |LongStream rangeClosed(long a, long b)|LongStream| Returns primitive stream from a (inclusive) to b (inclusive)       |
> |int sum() |IntStream |Returns sum of elements in stream|
> |long sum() |LongStream|Returns sum of elements in stream|
> |double sum()|DoubleStream|Returns sum of elements in stream|
> |IntSummaryStatistics summaryStatistics()|IntStream|Returns object containing numerous stream statistics such as average, min, max, etc.|
> |LongSummaryStatistics summaryStatistics()|LongStream|Returns object containing numerous stream statistics such as average, min, max, etc.|
> |DoubleSummaryStatistics summaryStatistics()|DoubleStream|Returns object containing numerous stream statistics such as average, min, max, etc.|

&emsp;&emsp;
Some of the methods for creating a primitive stream are equivalent to how we created the
source for a regular Stream. You can create an empty stream with this:

```java
DoubleStream empty = DoubleStream.empty();
```

&emsp;&emsp;
Another way is to use the `of()` factory method from a single value or by using the
varargs overload.

```java
DoubleStream oneValue = DoubleStream.of(3.14);
oneValue.forEach(System.out::println);

DoubleStream varargs = DoubleStream.of(1.0, 1.1, 1.2);
varargs.forEach(System.out::println);
```

&emsp;&emsp;
This code outputs the following:

```java
3.14
1.0
1.1
1.2
```

&emsp;&emsp;
You can also use the two methods for creating infinite streams, just like we did with Stream.

```java
var random = DoubleStream.generate(Math::random);
var fractions = DoubleStream.iterate(.5, d -> d / 2);
random.limit(3).forEach(System.out::println);
fractions.limit(3).forEach(System.out::println);
```

&emsp;&emsp;
Since the streams are infinite, we added a limit intermediate operation so that the output
doesn’t print values forever. The first stream calls a static method on Math to get a
random double. Since the numbers are random, your output will obviously be different. The
second stream keeps creating smaller numbers, dividing the previous value by two each time.
The output from when we ran this code was as follows:

```java
0.07890654781186413
0.28564363465842346
0.6311403511266134
0.5
0.25
0.125
```

&emsp;&emsp;
You don’t need to know this for the exam, but the Random class provides a method to get
primitives streams of random numbers directly. Fun fact! For example, ints() generates an
infinite IntStream of primitives. <br />

&emsp;&emsp;
It works the same way for each type of primitive stream. When dealing with int or long
primitives, it is common to count. Suppose that we wanted a stream with the numbers from
1 through 5. We could write this using what we’ve explained so far:

```java
IntStream count = IntStream.iterate(1, n -> n + 1).limit(5);
count.forEach(System.out::print); // 12345
```

&emsp;&emsp;
This code does print out the numbers 1–5. However, it is a lot of code to do something so
simple. Java provides a method that can generate a range of numbers.

```java
IntStream range = IntStream.range(1, 6);
range.forEach(System.out::print); // 1234
```

&emsp;&emsp;
This is better. If we wanted numbers 1–5, why did we pass 1–6? The first parameter to the
`range()` method is inclusive, which means it includes the number. The second parameter to
the `range()` method is exclusive, which means it stops right before that number. However,
it still could be clearer. We want the numbers 1–5 inclusive. Luckily, there’s another method,
`rangeClosed()`, which is inclusive on both parameters.

```java
IntStream rangeClosed = IntStream.rangeClosed(1, 5);
rangeClosed.forEach(System.out::print); // 12345
```

&emsp;&emsp;
Even better. This time we expressed that we want a closed range or an inclusive range.
This method better matches how we express a range of numbers in plain English.

## II. Mapping Streams
Another way to create a primitive stream is by mapping from another stream type.
Table 10.6 shows that there is a method for mapping between any stream types.

> **Table 10.6**: Mapping methods between types of streams
> 
> |Source stream class| **To create** Stream|**To create** DoubleStream|**To create** IntStream|**To create** LongStream|
> |-------------------|----------------------|---------------------------|------------------------|-------------------------|
> |Stream<T> |map() |mapToDouble() |mapToInt() |mapToLong()|
> |DoubleStream |mapToObj() |map() |mapToInt() |mapToLong()|
> |IntStream |mapToObj() |mapToDouble() |map() |mapToLong()|
> |LongStream |mapToObj() |mapToDouble() |mapToInt() |map()|

&emsp;&emsp;
Obviously, they have to be compatible types for this to work. Java requires a mapping
function to be provided as a parameter, for example:

```java
Stream<String> objStream = Stream.of("penguin", "fish");
IntStream intStream = objStream.mapToInt(s -> s.length());
```

&emsp;&emsp;
This function takes an Object, which is a String in this case. The function returns an
int. The function mappings are intuitive here. They take the source type and return the
target type. In this example, the actual function type is ToIntFunction. Table 10.7 shows
the mapping function names. As you can see, they do what you might expect. <br />

&emsp;&emsp;
You do have to memorize Table 10.6 and Table 10.7. It’s not as hard as it might seem.
There are patterns in the names if you remember a few rules. For Table 10.6, mapping
to the same type you started with is just called `map()`. When returning an object stream,
the method is `mapToObj()`. Beyond that, it’s the name of the primitive type in the map
method name. <br />

&emsp;&emsp;
For Table 10.7, you can start by thinking about the source and target types. When
the target type is an object, you drop the To from the name. When the mapping is to the
same type you started with, you use a unary operator instead of a function for the primitive streams.

> **Using *flatMap()*** <br />
> We can use this approach on primitive streams as well. It works the same way as on a 
regular Stream, except the method name is different. Here’s an example:
> ```java
>   var integerList = new ArrayList<Integer>();
>   IntStream ints = integerList.stream()
>       .flatMapToInt(x -> IntStream.of(x));
>   DoubleStream doubles = integerList.stream()
>       .flatMapToDouble(x -> DoubleStream.of(x));
>   LongStream longs = integerList.stream()
>       .flatMapToLong(x -> LongStream.of(x));
> ```

> **Table 10.7**: Function parameters when mapping between types of streams
> 
> |Source stream class|To create Stream|To create DoubleStream|To create IntStream|To create LongStream|
> |------------------|----------------|----------------------|--------------------|---------------------|
> |Stream<T> |Function<T,R> |ToDouble Function<T>|ToInt  Function<T>|ToLong  Function<T>|
> |DoubleStream |DoubleFunction<R> |DoubleUnaryOperator|DoubleToIntFunction|DoubleToLongFunction|
> |IntStream |IntFunction<R> |IntToDoubleFunction|IntUnaryOperator|IntToLongFunction|
> |LongStream |LongFunction<R> |LongToDoubleFunction|LongToIntFunction|LongUnaryOperator|

&emsp;&emsp;
Additionally, you can create a Stream from a primitive stream. These methods show two
ways of accomplishing this:

```java
private static Stream<Integer> mapping(IntStream stream) {
    return stream.mapToObj(x -> x);
}

private static Stream<Integer> boxing(IntStream stream) {
    return stream.boxed();
}
```

&emsp;&emsp;
The first one uses the `mapToObj()` method we saw earlier. The second one is more 
succinct. It does not require a mapping function because all it does is autobox each primitive to
the corresponding wrapper object. The `boxed()` method exists on all three types of primitive streams.

## III. Using *Optional* with Primitive Streams
Earlier in the chapter, we wrote a method to calculate the average of an `int[]` and promised
a better way later. Now that you know about primitive streams, you can calculate the
average in one line.

```java
var stream = IntStream.rangeClosed(1, 10);
OptionalDouble optional = stream.average();
```

&emsp;&emsp;
The return type is not the Optional you have become accustomed to using. It is a new
type called OptionalDouble. Why do we have a separate type, you might wonder? Why
not just use `Optional<Double>`? The difference is that OptionalDouble is for a 
primitive and Optional<Double> is for the Double wrapper class. Working with the primitive
optional class looks similar to working with the Optional class itself.

```java
optional.ifPresent(System.out::println);                    // 5.5
System.out.println(optional.getAsDouble());                 // 5.5
System.out.println(optional.orElseGet(() -> Double.NaN));   // 5.5
```

&emsp;&emsp;
The only noticeable difference is that we called `getAsDouble()` rather than `get()`.
This makes it clear that we are working with a primitive. Also, `orElseGet()` takes a
DoubleSupplier instead of a Supplier. <br />

&emsp;&emsp;
As with the primitive streams, there are three type-specific classes for primitives.
Table 10.8 shows the minor differences among the three. You probably won’t be surprised
that you have to memorize this table as well. This is really easy to remember since the 
primitive name is the only change. As you should remember from the terminal operations section,
a number of stream methods return an optional such as `min()` or `findAny()`. These each
return the corresponding optional type. The primitive stream implementations also add two
new methods that you need to know. The `sum()` method does not return an optional. If you
try to add up an empty stream, you simply get zero. The `average()` method always returns
an OptionalDouble since an average can potentially have fractional data for any type.

> **Table 10.8**: Optional types for primitives
> 
> |-|OptionalDouble| OptionalInt| OptionalLong|
> |---|-------------|------------|-------------|
> |Getting as primitive |getAsDouble() |getAsInt() |getAsLong()|
> |orElseGet() parameter type |DoubleSupplier |IntSupplier |LongSupplier|
> |Return type of max() and min() |OptionalDouble |OptionalInt |OptionalLong|
> |Return type of sum() |double |int |long|
> |Return type of average() |OptionalDouble |OptionalDouble |OptionalDouble|

&emsp;&emsp;
Let’s try an example to make sure that you understand this:

```java
5: LongStream longs = LongStream.of(5, 10);
6: long sum = longs.sum();
7: System.out.println(sum);     // 15
8: DoubleStream doubles = DoubleStream.generate(() -> Math.PI);
9: OptionalDouble min = doubles.min(); // runs infinitely
```

&emsp;&emsp;
Line 5 creates a stream of long primitives with two elements. Line 6 shows that we don’t
use an optional to calculate a sum. Line 8 creates an infinite stream of double primitives.
Line 9 is there to remind you that a question about code that runs infinitely can appear with
primitive streams as well.

## IV. Summarizing Statistics
You’ve learned enough to be able to get the maximum value from a stream of int 
primitives. If the stream is empty, we want to throw an exception.

```java
private static int max(IntStream ints) {
    OptionalInt optional = ints.max();
    return optional.orElseThrow(RuntimeException::new);
}
```

&emsp;&emsp;
This should be old hat by now. We got an OptionalInt because we have an
IntStream. If the optional contains a value, we return it. Otherwise, we throw a new
RuntimeException. <br />

&emsp;&emsp;
Now we want to change the method to take an IntStream and return a range. The
range is the minimum value subtracted from the maximum value. Uh-oh. Both `min()` and
`max()` are terminal operations, which means that they use up the stream when they are run.
We can’t run two terminal operations against the same stream. Luckily, this is a common
problem, and the primitive streams solve it for us with summary statistics. Statistic is just a
big word for a number that was calculated from data.

```java
private static int range(IntStream ints) {
    IntSummaryStatistics stats = ints.summaryStatistics();
    if (stats.getCount() == 0) throw new RuntimeException();
    return stats.getMax()-stats.getMin();
}
```

&emsp;&emsp;
Here we asked Java to perform many calculations about the stream. Summary statistics
include the following:

- **getCount()**: Returns a long representing the number of values.
- **getAverage()**: Returns a double representing the average. If the stream is empty, returns 0.
- **getSum()**: Returns the sum as a double for DoubleSummaryStream and long for
  IntSummaryStream and LongSummaryStream.
- **getMin()**: Returns the smallest number (minimum) as a double, int, or long, depending on
  the type of the stream. If the stream is empty, returns the largest numeric value based
  on the type.
- **getMax()**: Returns the largest number (maximum) as a double, int, or long 
  depending on the type of the stream. If the stream is empty, returns the smallest numeric value
  based on the type.
