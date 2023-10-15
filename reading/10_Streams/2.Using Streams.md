# Using Streams

A stream in Java is a sequence of data. A stream pipeline consists of the operations that run
on a stream to produce a result. First, we look at the flow of pipelines conceptually. After
that, we get into the code.

## I. Understanding the Pipeline Flow

Think of a stream pipeline as an assembly line in a factory. Suppose that we are running an
assembly line to make signs for the animal exhibits at the zoo. We have a number of jobs. It
is one person’s job to take the signs out of a box. It is a second person’s job to paint the sign.
It is a third person’s job to stencil the name of the animal on the sign. It’s the last person’s
job to put the completed sign in a box to be carried to the proper exhibit. <br />

&emsp;&emsp;
Notice that the second person can’t do anything until one sign has been taken out of the
box by the first person. Similarly, the third person can’t do anything until one sign has been
painted, and the last person can’t do anything until it is stenciled. <br />

&emsp;&emsp;
The assembly line for making signs is finite. Once we process the contents of our box of
signs, we are finished. Finite streams have a limit. Other assembly lines essentially run 
forever, like one for food production. Of course, they do stop at some point when the factory
closes down, but pretend that doesn’t happen. Or think of a sunrise/sunset cycle as infinite,
since it doesn’t end for an inordinately large period of time. <br />

&emsp;&emsp;
Another important feature of an assembly line is that each person touches each element to
do their operation, and then that piece of data is gone. It doesn’t come back. The next person
deals with it at that point. This is different than the lists and queues that you saw in the
previous chapter. With a list, you can access any element at any time. With a queue, you are
limited in which elements you can access, but all of the elements are there. With streams,
the data isn’t generated up front—it is created when needed. This is an example of
lazy evaluation, which delays execution until necessary. <br />

&emsp;&emsp;
Many things can happen in the assembly line stations along the way. In functional
programming, these are called stream operations. Just like with the assembly line, operations
occur in a pipeline. Someone has to start and end the work, and there can be any number of
stations in between. After all, a job with one person isn’t an assembly line! There are three
parts to a stream pipeline, as shown in Figure 10.2.

- **Source**: Where the stream comes from.
- **Intermediate** operations: Transforms the stream into another one. There can be as few
  or as many intermediate operations as you’d like. Since streams use lazy evaluation, the
  intermediate operations do not run until the terminal operation runs.
- **Terminal operation**: Produces a result. Since streams can be used only once, the stream is
  no longer valid after a terminal operation completes.

> **Figure 10.2** Stream pipeline
> 
> ```java
> Source -> Intermediate Operations -> Terminal Operation
>              1 -> 2 -> ... -> n
> ```

&emsp;&emsp;
Notice that the operations are unknown to us. When viewing the assembly line from the
outside, you care only about what comes in and goes out. What happens in between is an
implementation detail. <br />

&emsp;&emsp;
You will need to know the differences between intermediate and terminal operations well.
Make sure you can fill in Table 10.2.

> **Table 10.2** Intermediate vs. terminal operations
> 
> |Scenario|Intermediate operation|Terminal operation|
> |--------|----------------------|------------------|
> |Required part of useful pipeline? |No |Yes|
> |Can exist multiple times in pipeline? |Yes |No|
> |Return type is stream type? |Yes |No|
> |Executed upon method call? |No |Yes|
> |Stream valid after call? |Yes |No|

&emsp;&emsp;
A factory typically has a foreperson who oversees the work. Java serves as the foreperson
when working with stream pipelines. This is a really important role, especially when dealing
with lazy evaluation and infinite streams. Think of declaring the stream as giving instructions
to the foreperson. As the foreperson finds out what needs to be done, they set up the stations
and tell the workers what their duties will be. However, the workers do not start until the
foreperson tells them to begin. The foreperson waits until they see the terminal operation to
kick off the work. They also watch the work and stop the line as soon as work is complete. <br />

&emsp;&emsp;
Let’s look at a few examples of this. We aren’t using code in these examples because it is
really important to understand the stream pipeline concept before starting to write the code.
Figure 10.3 shows a stream pipeline with one intermediate operation.

> **Figure 10.3** Steps in running a stream pipeline
>
> ```java
> Take sign out of box    ->    Intermediate Operation    ->    Put sign in pile
>                                    (paint sign)
>                                     1 -> 2 -> 3
>                                     4 -> 5 -> 6                                 
> ```

&emsp;&emsp;
Let’s take a look at what happens from the point of view of the foreperson. First, they see
that the source is taking signs out of the box. The foreperson sets up a worker at the table to
unpack the box and says to await a signal to start. Then the foreperson sees the intermediate
operation to paint the sign. They set up a worker with paint and say to await a signal to
start. Finally, the foreperson sees the terminal operation to put the signs into a pile. They set
up a worker to do this and yell that all three workers should start. <br />

&emsp;&emsp;
Suppose that there are two signs in the box. Step 1 is the first worker taking one sign out
of the box and handing it to the second worker. Step 2 is the second worker painting it and
handing it to the third worker. Step 3 is the third worker putting it in the pile. Steps 4–6 are
this same process for the other sign. Then the foreperson sees that there are no signs left and
shuts down the entire enterprise. <br />

&emsp;&emsp;
The foreperson is smart and can make decisions about how to best do the work based on
what is needed. As an example, let’s explore the stream pipeline in Figure 10.4.

> **Figure 10.4** A stream pipeline with a limit
>
> ```java
> Take sign out of box     ->      Intermediate Operation      ->     Put sign in pile
>                                      (paint sign)
>                               Paint sign -> Only do 2 signs
> ```

&emsp;&emsp;
The foreperson still sees a source of taking signs out of the box and assigns a worker to
do that on command. They still see an intermediate operation to paint and set up another
worker with instructions to wait and then paint. Then they see an intermediate step that
we need only two signs. They set up a worker to count the signs that go by and notify the
foreperson when the worker has seen two. Finally, they set up a worker for the terminal
operation to put the signs in a pile. <br />

&emsp;&emsp;
This time, suppose that there are 10 signs in the box. We start like last time. The first sign
makes its way down the pipeline. The second sign also makes its way down the pipeline.
When the worker in charge of counting sees the second sign, they tell the foreperson. The
foreperson lets the terminal operation worker finish their task and then yells, “Stop the line.”
It doesn’t matter that there are eight more signs in the box. We don’t need them, so it would
be unnecessary work to paint them. And we all want to avoid unnecessary work! <br />

&emsp;&emsp;
Similarly, the foreperson would have stopped the line after the first sign if the terminal
operation was to find the first sign that gets created. <br />

&emsp;&emsp;
In the following sections, we cover the three parts of the pipeline. We also discuss special
types of streams for primitives and how to print a stream.

## II. Creating Stream Sources
In Java, the streams we have been talking about are represented by the `Stream<T>` interface,
defined in the `java.util.stream` package.

### &emsp;&emsp; 1. Creating Finite Streams
For simplicity, we start with finite streams. There are a few ways to create them.

```java
11: Stream<String> empty = Stream.empty();          // count = 0
12: Stream<Integer> singleElement = Stream.of(1);   // count = 1
13: Stream<Integer> fromArray = Stream.of(1, 2, 3); // count = 3
```

&emsp;&emsp;
Line 11 shows how to create an empty stream. Line 12 shows how to create a stream
with a single element. Line 13 shows how to create a stream from a varargs. <br />

&emsp;&emsp;
Java also provides a convenient way of converting a Collection to a stream.

```java
14: var list = List.of("a", "b", "c");
15: Stream<String> fromList = list.stream();
```

&emsp;&emsp;
Line 15 shows that it is a simple method call to create a stream from a list. This is helpful
since such conversions are common.

> #### **Creating a Parallel Stream**
> It is just as easy to create a parallel stream from a list.
> ```java
> 24: var list = List.of("a", "b", "c");
> 25: Stream<String> fromListParallel = list.parallelStream();
> ```
> This is a great feature because you can write code that uses concurrency before even
learning what a thread is. Using parallel streams is like setting up multiple tables of workers
who can do the same task. Painting would be a lot faster if we could have five painters
painting signs instead of just one. Just keep in mind some tasks cannot be done in parallel,
such as putting the signs away in the order that they were created in the stream. Also be
aware that there is a cost in coordinating the work, so for smaller streams, it might be faster
to do it sequentially. You learn much more about running tasks concurrently in Chapter 13,
“Concurrency.”

### &emsp;&emsp; 2. Creating Infinite Streams
So far, this isn’t particularly impressive. We could do all this with lists. We can’t create an
infinite list, though, which makes streams more powerful.

```java
17: Stream<Double> randoms = Stream.generate(Math::random);
18: Stream<Integer> oddNumbers = Stream.iterate(1, n -> n + 2);
```

&emsp;&emsp;
Line 17 generates a stream of random numbers. How many random numbers? However
many you need. If you call `randoms.forEach(System.out::println)`, the program will
print random numbers until you kill it. Later in the chapter, you learn about operations like
`limit()` to turn the infinite stream into a finite stream. <br />

&emsp;&emsp;
Line 18 gives you more control. The `iterate()` method takes a seed or starting value as the
first parameter. This is the first element that will be part of the stream. The other parameter
is a lambda expression that is passed the previous value and generates the next value. As
with the random numbers example, it will keep on producing odd numbers as long as you
need them.

> #### **Printing a Stream Reference**
> If you try to call `System.out.print(stream)`, you’ll get something like the following:
> ```java
> java.util.stream.ReferencePipeline$3@4517d9a3
> ```
> This is different from a Collection, where you see the contents. You don’t need to know
this for the exam. We mention it so that you aren’t caught by surprise when writing code
for practice.

&emsp;&emsp;
What if you wanted just odd numbers less than 100? There’s an overloaded version of
`iterate()` that helps:

```java
19: Stream<Integer> oddNumberUnder100 = Stream.iterate(
20: 1,              // seed
21: n -> n < 100,   // Predicate to specify when done
22: n -> n + 2);    // UnaryOperator to get next value
```

&emsp;&emsp;
This method takes three parameters. Notice how they are separated by commas (,) just
like in all other methods. The exam may try to trick you by using semicolons since it is 
similar to a for loop. Similar to a for loop, you have to take care that you aren’t accidentally
creating an infinite stream.

### &emsp;&emsp; 3. Reviewing Stream Creation Methods
To review, make sure you know all the methods in Table 10.3. These are the ways of creating
a source for streams, given a Collection instance coll.

> **Table 10.3** Creating a source
> 
> |Method|Finite or Infinite|Notes|
> |------|------------------|-----|
> |Stream.empty() |Finite |Creates Stream with zero elements.|
> |Stream.of(varargs) |Finite |Creates Stream with elements listed.|
> |coll.stream() |Finite |Creates Stream from Collection.|
> |coll.parallelStream() |Finite |Creates Stream from Collection where the stream can run in parallel.|
> |Stream.generate(supplier) |Infinite |Creates Stream by calling Supplier for each element upon request.|
> |Stream.iterate(seed, unaryOperator) |Infinite |Creates Stream by using seed for first element and then calling UnaryOperator for each subsequent element upon request.|
> |Stream.iterate(seed, predicate, unaryOperator) |Finite or Infinite |Creates Stream by using seed for first element and then calling UnaryOperator for each subsequent element upon request. Stops if Predicate returns false.|

## III. Common Stream Operations
You can perform a terminal operation without any intermediate operations but not the other
way around. This is why we talk about terminal operations first. Reductions are a special
type of terminal operation where all of the contents of the stream are combined into a single
primitive or Object. For example, you might have an int or a Collection. <br />

&emsp;&emsp;
Table 10.4 summarizes this section. Feel free to use it as a guide to remember the most
important points as we go through each one individually. We explain them from simplest to
most complex rather than alphabetically.

> **Table 10.4** Terminal stream operations
> 
> |Method|What happens for infinite streams| Return value |Reduction|
> |------|---------------------------------|--------------|---------|
> |count() |Does not terminate | long         |Yes|
> |min()<br/>max() |Does not terminate | Optional<T>  | Yes          |
> |findAny()<br/>findFirst() |Terminates | Optional<T>  | No           |
> |allMatch()<br/>anyMatch()<br/>noneMatch() |Sometimes terminates | boolean      | No           |
> |forEach() |Does not terminate | void         | No           |
> |reduce() |Does not terminate | Varies       | Yes          |
> |collect() |Does not terminate | Varies       | Yes          |

### &emsp;&emsp; 1. Counting
The `count()` method determines the number of elements in a finite stream. For an infinite
stream, it never terminates. Why? Count from 1 to infinity, and let us know when you are
finished. Or rather, don’t do that, because we’d rather you study for the exam than spend
the rest of your life counting. The `count()` method is a reduction because it looks at each
element in the stream and returns a single value. The method signature is as follows:

```java
public long count()
```

&emsp;&emsp;
This example shows calling `count()` on a finite stream:

```java
Stream<String> s = Stream.of("monkey", "gorilla", "bonobo");
System.out.println(s.count()); // 3
```

### &emsp;&emsp; 2. Finding the Minimum and Maximum
The `min()` and `max()` methods allow you to pass a custom comparator and find the 
smallest or largest value in a finite stream according to that sort order. Like the `count()` method,
`min()` and `max()` hang on an infinite stream because they cannot be sure that a smaller
or larger value isn’t coming later in the stream. Both methods are reductions because they
return a single value after looking at the entire stream. The method signatures are as follows:

```java
public Optional<T> min(Comparator<? super T> comparator)
public Optional<T> max(Comparator<? super T> comparator)
```

&emsp;&emsp;
This example finds the animal with the fewest letters in its name:

```java
Stream<String> s = Stream.of("monkey", "ape", "bonobo");
Optional<String> min = s.min((s1, s2) -> s1.length() - s2.length());
min.ifPresent(System.out::println); // ape
```

&emsp;&emsp;
Notice that the code returns an Optional rather than the value. This allows the method
to specify that no minimum or maximum was found. We use the Optional method
`ifPresent()` and a method reference to print out the minimum only if one is found. As an
example of where there isn’t a minimum, let’s look at an empty stream:

```java
Optional<?> minEmpty = Stream.empty().min((s1, s2) -> 0);
System.out.println(minEmpty.isPresent()); // false
```

&emsp;&emsp;
Since the stream is empty, the comparator is never called, and no value is present in
the Optional.

> **Note**: <br />
> What if you need both the min() and max() values of the same stream?
For now, you can’t have both, at least not using these methods.
Remember, a stream can have only one terminal operation. Once a
terminal operation has been run, the stream cannot be used again. As
you see later in this chapter, there are built-in summary methods for
some numeric streams that will calculate a set of values for you.

### &emsp;&emsp; 3. Finding a Value
The `findAny()` and `findFirst()` methods return an element of the stream unless the
stream is empty. If the stream is empty, they return an empty Optional. This is the first
method you’ve seen that can terminate with an infinite stream. Since Java generates only the
amount of stream you need, the infinite stream needs to generate only one element. <br />

&emsp;&emsp;
As its name implies, the `findAny()` method can return any element of the stream.
When called on the streams you’ve seen up until now, it commonly returns the first element,
although this behavior is not guaranteed. As you see in Chapter 13, the `findAny()` method
is more likely to return a random element when working with parallel streams. <br />

&emsp;&emsp;
These methods are terminal operations but not reductions. The reason is that they 
sometimes return without processing all of the elements. This means that they return a value
based on the stream but do not reduce the entire stream into one value.
The method signatures are as follows:

```java
public Optional<T> findAny()
public Optional<T> findFirst()
```

&emsp;&emsp;
This example finds an animal:

```java
Stream<String> s = Stream.of("monkey", "gorilla", "bonobo");
Stream<String> infinite = Stream.generate(() -> "chimp");

s.findAny().ifPresent(System.out::println);         // monkey (usually)
infinite.findAny().ifPresent(System.out::println);  // chimp
```

&emsp;&emsp;
Finding any one match is more useful than it sounds. Sometimes we just want to sample
the results and get a representative element, but we don’t need to waste the processing
generating them all. After all, if we plan to work with only one element, why bother
looking at more?

### &emsp;&emsp; 4. Matching
The `allMatch()`, `anyMatch()`, and `noneMatch()` methods search a stream and return information
about how the stream pertains to the predicate. These may or may not terminate for infinite
streams. It depends on the data. Like the find methods, they are not reductions because they
do not necessarily look at all of the elements. <br />

&emsp;&emsp;
The method signatures are as follows:

```java
public boolean anyMatch(Predicate <? super T> predicate)
public boolean allMatch(Predicate <? super T> predicate)
public boolean noneMatch(Predicate <? super T> predicate)
```

&emsp;&emsp;
This example checks whether animal names begin with letters:

```java
var list = List.of("monkey", "2", "chimp");
Stream<String> infinite = Stream.generate(() -> "chimp");
Predicate<String> pred = x -> Character.isLetter(x.charAt(0));

System.out.println(list.stream().anyMatch(pred));   // true
System.out.println(list.stream().allMatch(pred));   // false
System.out.println(list.stream().noneMatch(pred));  // false
System.out.println(infinite.anyMatch(pred));        // true
```

&emsp;&emsp;
This shows that we can reuse the same predicate, but we need a different stream each
time. The `anyMatch()` method returns true because two of the three elements match. The
`allMatch()` method returns false because one doesn’t match. The `noneMatch()` method
also returns false because at least one matches. On the infinite stream, one match is found,
so the call terminates. If we called `allMatch()`, it would run until we killed the program.

> **Tip**: <br />
> Remember that allMatch(), anyMatch(), and noneMatch() return a
boolean. By contrast, the find methods return an Optional because
they return an element of the stream.

### &emsp;&emsp; 5. Iterating
As in the Java Collections Framework, it is common to iterate over the elements of a stream.
As expected, calling `forEach()` on an infinite stream does not terminate. Since there is no
return value, it is not a reduction. <br />

&emsp;&emsp;
Before you use it, consider if another approach would be better. Developers who learned
to write loops first tend to use them for everything. For example, a loop with an if 
statement could be written with a filter. You will learn about filters in the intermediate operations section. <br />

&emsp;&emsp;
The method signature is as follows:

```java
public void forEach(Consumer<? super T> action)
```

&emsp;&emsp;
Notice that this is the only terminal operation with a return type of void. If you want
something to happen, you have to make it happen in the Consumer. Here’s one way to print
the elements in the stream (there are other ways, which we cover later in the chapter):

```java
Stream<String> s = Stream.of("Monkey", "Gorilla", "Bonobo");
s.forEach(System.out::print); // MonkeyGorillaBonobo
```

> **Note**: <br />
> Remember that you can call forEach() directly on a Collection or on a
Stream. Don’t get confused on the exam when you see both approaches.

&emsp;&emsp;
Notice that you can’t use a traditional for loop on a stream.

```java
Stream<Integer> s = Stream.of(1);
for (Integer i : s) {} // DOES NOT COMPILE
```

&emsp;&emsp;
While `forEach()` sounds like a loop, it is really a terminal operator for streams. Streams
cannot be used as the source in a for-each loop because they don’t implement the Iterable
interface.

### &emsp;&emsp; 6. Reducing
The `reduce()` method combines a stream into a single object. It is a reduction, which means it
processes all elements. The three method signatures are these:

```java
public T reduce(T identity, BinaryOperator<T> accumulator)
public Optional<T> reduce(BinaryOperator<T> accumulator)
public <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner)
```

&emsp;&emsp;
Let’s take them one at a time. The most common way of doing a reduction is to start with
an initial value and keep merging it with the next value. Think about how you would 
concatenate an array of String objects into a single String without functional programming.
It might look something like this:

```java
var array = new String[] { "w", "o", "l", "f" };
var result = "";
for (var s: array) result = result + s;
System.out.println(result); // wolf
```

&emsp;&emsp;
The *identity* is the initial value of the reduction, in this case an empty String. The 
*accumulator* combines the current result with the current value in the stream. With lambdas, we
can do the same thing with a stream and reduction:

```java
Stream<String> stream = Stream.of("w", "o", "l", "f");
String word = stream.reduce("", (s, c) -> s + c);
System.out.println(word); // wolf
```

&emsp;&emsp;
Notice how we still have the empty String as the identity. We also still concatenate the
String objects to get the next value. We can even rewrite this with a method reference:

```java
Stream<String> stream = Stream.of("w", "o", "l", "f");
String word = stream.reduce("", String::concat);
System.out.println(word); // wolf
```

&emsp;&emsp;
Let’s try another one. Can you write a reduction to multiply all of the Integer objects in
a stream? Try it. Our solution is shown here:

```java
Stream<Integer> stream = Stream.of(3, 5, 6);
System.out.println(stream.reduce(1, (a, b) -> a * b)); // 90
```

&emsp;&emsp;
We set the identity to 1 and the accumulator to multiplication. In many cases, the 
identity isn’t really necessary, so Java lets us omit it. When you don’t specify an identity, an
Optional is returned because there might not be any data. There are three choices for what
is in the Optional:

- If the stream is empty, an empty Optional is returned.
- If the stream has one element, it is returned.
- If the stream has multiple elements, the accumulator is applied to combine them.

&emsp;&emsp;
The following illustrates each of these scenarios:

```java
BinaryOperator<Integer> op = (a, b) -> a * b;
Stream<Integer> empty = Stream.empty();
Stream<Integer> oneElement = Stream.of(3);
Stream<Integer> threeElements = Stream.of(3, 5, 6);

empty.reduce(op).ifPresent(System.out::println);         // no output
oneElement.reduce(op).ifPresent(System.out::println);    // 3
threeElements.reduce(op).ifPresent(System.out::println); // 90
```

&emsp;&emsp;
Why are there two similar methods? Why not just always require the identity? Java could
have done that. However, sometimes it is nice to differentiate the case where the stream is
empty rather than the case where there is a value that happens to match the identity being
returned from the calculation. The signature returning an Optional lets us differentiate
these cases. For example, we might return `Optional.empty()` when the stream is empty
and `Optional.of(3)` when there is a value. <br />

&emsp;&emsp;
The third method signature is used when we are dealing with different types. It allows
Java to create intermediate reductions and then combine them at the end. Let’s take a look at
an example that counts the number of characters in each String:

```java
Stream<String> stream = Stream.of("w", "o", "l", "f!");
int length = stream.reduce(0, (i, s) -> i + s.length(), (a, b) -> a + b);
System.out.println(length); // 5
```

&emsp;&emsp;
The first parameter (0) is the value for the *initializer*. If we had an empty stream, this
would be the answer. The second parameter is the *accumulator*. Unlike the accumulators you
saw previously, this one handles mixed data types. In this example, the first argument, i, is
an Integer, while the second argument, s, is a String. It adds the length of the current
String to our running total. The third parameter is called the *combiner*, which combines
any intermediate totals. In this case, a and b are both Integer values. <br />

&emsp;&emsp;
The three-argument `reduce()` operation is useful when working with parallel streams
because it allows the stream to be decomposed and reassembled by separate threads. For
example, if we needed to count the length of four 100-character strings, the first two values
and the last two values could be computed independently. The intermediate result `(200 + 200)` 
would then be combined into the final value.

### &emsp;&emsp; 7. Collecting
The `collect()` method is a special type of reduction called a *mutable* reduction. It is more 
efficient than a regular reduction because we use the same mutable object while accumulating.
Common mutable objects include StringBuilder and ArrayList. This is a really useful method,
because it lets us get data out of streams and into another form. The method signatures are
as follows:

```java
public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner)
public <R, A> R collect(Collector<? super T, A, R> collector)
```

&emsp;&emsp;
Let’s start with the first signature, which is used when we want to code specifically
how collecting should work. Our wolf example from reduce can be converted to use
collect():

```java
Stream<String> stream = Stream.of("w", "o", "l", "f");

StringBuilder word = stream.collect(
        StringBuilder::new,
        StringBuilder::append,
        StringBuilder::append);

System.out.println(word); // wolf
```

&emsp;&emsp;
The first parameter is the *supplier*, which creates the object that will store the results
as we collect data. Remember that a Supplier doesn’t take any parameters and returns a
value. In this case, it constructs a new StringBuilder. <br />

&emsp;&emsp;
The second parameter is the accumulator, which is a BiConsumer that takes two 
parameters and doesn’t return anything. It is responsible for adding one more element to the data
collection. In this example, it appends the next String to the StringBuilder. <br />

&emsp;&emsp;
The final parameter is the combiner, which is another BiConsumer. It is responsible for
taking two data collections and merging them. This is useful when we are processing in
parallel. Two smaller collections are formed and then merged into one. This would work
with StringBuilder only if we didn’t care about the order of the letters. In this case, the 
accumulator and combiner have similar logic. <br />

&emsp;&emsp;
Now let’s look at an example where the logic is different in the accumulator and combiner:

```java
Stream<String> stream = Stream.of("w", "o", "l", "f");

TreeSet<String> set = stream.collect(
        TreeSet::new,
        TreeSet::add,
        TreeSet::addAll);
System.out.println(set); // [f, l, o, w]
```

&emsp;&emsp;
The collector has three parts as before. The supplier creates an empty TreeSet. The 
accumulator adds a single String from the Stream to the TreeSet. The combiner adds all of
the elements of one TreeSet to another in case the operations were done in parallel and
need to be merged. <br />

&emsp;&emsp;
We started with the long signature because that’s how you implement your own collector.
It is important to know how to do this for the exam and understand how collectors work. In
practice, many common collectors come up over and over. Rather than making developers
keep reimplementing the same ones, Java provides a class with common collectors cleverly
named Collectors. This approach also makes the code easier to read because it is more
expressive. For example, we could rewrite the previous example as follows:

```java
Stream<String> stream = Stream.of("w", "o", "l", "f");
TreeSet<String> set =
    stream.collect(Collectors.toCollection(TreeSet::new));
System.out.println(set); // [f, l, o, w]
```

&emsp;&emsp;
If we didn’t need the set to be sorted, we could make the code even shorter:

```java
Stream<String> stream = Stream.of("w", "o", "l", "f");
Set<String> set = stream.collect(Collectors.toSet());
System.out.println(set); // [f, w, l, o]
```

&emsp;&emsp;
You might get different output for this last one since `toSet()` makes no guarantees as
to which implementation of Set you’ll get. It is likely to be a HashSet, but you shouldn’t
expect or rely on that.

> **Note**: <br />
> The exam expects you to know about common predefined collectors in
addition to being able to write your own by passing a supplier, accumulator, and combiner.

&emsp;&emsp;
Later in this chapter, we show many Collectors that are used for grouping data. It’s
a big topic, so it’s best to master how streams work before adding too many Collectors
into the mix.

## IV. Using Common Intermediate Operations
Unlike a terminal operation, an intermediate operation produces a stream as its result. An
intermediate operation can also deal with an infinite stream simply by returning another
infinite stream. Since elements are produced only as needed, this works fine. The assembly
line worker doesn’t need to worry about how many more elements are coming through and
instead can focus on the current element.

### &emsp;&emsp; 1. Filtering

The `filter()` method returns a Stream with elements that match a given expression. Here is the
method signature:

```java
public Stream<T> filter(Predicate<? super T> predicate)
```

&emsp;&emsp;
This operation is easy to remember and powerful because we can pass any Predicate to
it. For example, this retains all elements that begin with the letter m:

```java
Stream<String> s = Stream.of("monkey", "gorilla", "bonobo");
s.filter(x -> x.startsWith("m"))
    .forEach(System.out::print); // monkey
```

### &emsp;&emsp; 2. Removing Duplicates
The `distinct()` method returns a stream with duplicate values removed. The duplicates do not
need to be adjacent to be removed. As you might imagine, Java calls `equals()` to determine
whether the objects are equivalent. The method signature is as follows:

```java
public Stream<T> distinct()
```

&emsp;&emsp;
Here’s an example:

```java
Stream<String> s = Stream.of("duck", "duck", "duck", "goose");
s.distinct().forEach(System.out::print); // duckgoose
```

### &emsp;&emsp; 3. Restricting by Position
The `limit()` and `skip()` methods can make a Stream smaller, or `limit()` could make a
finite stream out of an infinite stream. The method signatures are shown here:

```java
public Stream<T> limit(long maxSize)
public Stream<T> skip(long n)
```

&emsp;&emsp;
The following code creates an infinite stream of numbers counting from 1. The `skip()`
operation returns an infinite stream starting with the numbers counting from 6, since it skips
the first five elements. The `limit()` call takes the first two of those. Now we have a finite
stream with two elements, which we can then print with the `forEach()` method:

```java
Stream<Integer> s = Stream.iterate(1, n -> n + 1);
s.skip(5)
    .limit(2)
    .forEach(System.out::print); // 67
```

### &emsp;&emsp; 4. Mapping
The `map()` method creates a one-to-one mapping from the elements in the stream to the 
elements of the next step in the stream. The method signature is as follows:

```java
public <R> Stream<R> map(Function<? super T, ? extends R> mapper)
``` 

&emsp;&emsp;
This one looks more complicated than the others you have seen. It uses the lambda
expression to figure out the type passed to that function and the one returned. The return
type is the stream that is returned.

> **Note**: <br />
> The map() method on streams is for transforming data. Don’t confuse it
with the Map interface, which maps keys to values.

As an example, this code converts a list of String objects to a list of Integer objects
representing their lengths:

```java
Stream<String> s = Stream.of("monkey", "gorilla", "bonobo");
s.map(String::length)
    .forEach(System.out::print); // 676
```

&emsp;&emsp;
Remember that `String::length` is shorthand for the `lambda x -> x.length()`,
which clearly shows it is a function that turns a String into an Integer.

### &emsp;&emsp; 5. Using flatMap
The `flatMap()` method takes each element in the stream and makes any elements it contains
top-level elements in a single stream. This is helpful when you want to remove empty 
elements from a stream or combine a stream of lists. We are showing you the method signature
for consistency with the other methods so you don’t think we are hiding anything. You aren’t
expected to be able to read this:

```java
public <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper)
```

&emsp;&emsp;
This gibberish basically says that it returns a Stream of the type that the function 
contains at a lower level. Don’t worry about the signature. It’s a headache. <br />

&emsp;&emsp;
What you should understand is the example. This gets all of the animals into the same
level and removes the empty list.

```java
List<String> zero = List.of();
var one = List.of("Bonobo");
var two = List.of("Mama Gorilla", "Baby Gorilla");
Stream<List<String>> animals = Stream.of(zero, one, two);

animals.flatMap(m -> m.stream()).forEach(System.out::println);
```

&emsp;&emsp;
Here’s the output:

```java
Bonobo
Mama Gorilla
Baby Gorilla
```

&emsp;&emsp;
As you can see, it removed the empty list completely and changed all elements of each list
to be at the top level of the stream.

> #### **Concatenating Streams** <br />
> While flatMap() is good for the general case, there is a more convenient way to concatenate two streams:
> ```java
> var one = Stream.of("Bonobo");
> var two = Stream.of("Mama Gorilla", "Baby Gorilla");
> Stream.concat(one, two).forEach(System.out::println);
> ```
> This produces the same three lines as the previous example. The two streams are
concatenated, and the terminal operation, forEach(), is called.

### &emsp;&emsp; 6. Sorting
The `sorted()` method returns a stream with the elements sorted. Just like sorting arrays, Java
uses natural ordering unless we specify a comparator. The method signatures are these:

```java
public Stream<T> sorted()
public Stream<T> sorted(Comparator<? super T> comparator)
```

&emsp;&emsp;
Calling the first signature uses the default sort order.

```java
Stream<String> s = Stream.of("brown-", "bear-");
s.sorted().forEach(System.out::print); // bear-brown-
```

&emsp;&emsp;
We can optionally use a Comparator implementation via a method or a lambda. In this
example, we are using a method:

```java
Stream<String> s = Stream.of("brown bear-", "grizzly-");
s.sorted(Comparator.reverseOrder())
    .forEach(System.out::print); // grizzly-brown bear-
```

&emsp;&emsp;
Here we pass a Comparator to specify that we want to sort in the reverse of natural sort
order. Ready for a tricky one? Do you see why this doesn’t compile?

```java
Stream<String> s = Stream.of("brown bear-", "grizzly-");
s.sorted(Comparator::reverseOrder); // DOES NOT COMPILE
```

&emsp;&emsp;
Take a look at the second `sorted()` method signature again. It takes a Comparator,
which is a functional interface that takes two parameters and returns an int. However,
`Comparator::reverseOrder` doesn’t do that. Because `reverseOrder()` takes
no arguments and returns a value, the method reference is equivalent to
`() -> Comparator.reverseOrder()`, which is really a `Supplier<Comparator>`. This
is not compatible with `sorted()`. We bring this up to remind you that you really do need to
know method references well.

### &emsp;&emsp; 7. Taking a Peek
The `peek()` method is our final intermediate operation. It is useful for debugging because it
allows us to perform a stream operation without changing the stream. The method signature
is as follows:

```java
public Stream<T> peek(Consumer<? super T> action)
```

&emsp;&emsp;
You might notice the intermediate `peek()` operation takes the same argument as the
terminal `forEach()` operation. Think of `peek()` as an intermediate version of `forEach()`
that returns the original stream to you. <br />

&emsp;&emsp;
The most common use for `peek()` is to output the contents of the stream as it goes by. 
Suppose that we made a typo and counted bears beginning with the letter g instead of b. We are
puzzled why the count is 1 instead of 2. We can add a `peek()` method to find out why.


```java
var stream = Stream.of("black bear", "brown bear", "grizzly");
long count = stream.filter(s -> s.startsWith("g"))
    .peek(System.out::println).count(); // grizzly
System.out.println(count);      // 1
```

&emsp;&emsp;
In Chapter 9, you saw that `peek()` looks only at the first element when working with a
Queue. In a stream, `peek()` looks at each element that goes through that part of the stream
pipeline. It’s like having a worker take notes on how a particular step of the process is doing.

> ### **Danger: Changing State with peek()**
> Remember that peek() is intended to perform an operation without changing the result.
Here’s a straightforward stream pipeline that doesn’t use peek():
> ```java
>   var numbers = new ArrayList<>();
>   var letters = new ArrayList<>();
>   numbers.add(1);
>   letters.add('a');
> 
>   Stream<List<?>> stream = Stream.of(numbers, letters);
>   stream.map(List::size).forEach(System.out::print); // 11
> ```
> Now we add a peek() call and note that Java doesn’t prevent us from writing bad
peek code:
> ```java
>   Stream<List<?>> bad = Stream.of(numbers, letters);
>   bad.peek(x -> x.remove(0))
>           .map(List::size)
>           .forEach(System.out::print); // 00
> ```
> This example is bad because peek() is modifying the data structure that is used in the
stream, which causes the result of the stream pipeline to be different than if the peek
wasn’t present.

## V. Putting Together the Pipeline
Streams allow you to use chaining and express what you want to accomplish rather than
how to do so. Let’s say that we wanted to get the first two names of our friends 
alphabetically that are four characters long. Without streams, we’d have to write something like the
following:

```java
var list = List.of("Toby", "Anna", "Leroy", "Alex");
List<String> filtered = new ArrayList<>();
for (String name: list)
    if (name.length() == 4) filtered.add(name);
Collections.sort(filtered);
var iter = filtered.iterator();
if (iter.hasNext()) System.out.println(iter.next());
if (iter.hasNext()) System.out.println(iter.next());
```

&emsp;&emsp;
This works. It takes some reading and thinking to figure out what is going on. The
problem we are trying to solve gets lost in the implementation. It is also very focused on the
how rather than on the what. With streams, the equivalent code is as follows:

```java
var list = List.of("Toby", "Anna", "Leroy", "Alex");
list.stream().filter(n -> n.length() == 4).sorted()
    .limit(2).forEach(System.out::println);
```

&emsp;&emsp;
Before you say that it is harder to read, we can format it.

```java
var list = List.of("Toby", "Anna", "Leroy", "Alex");
list.stream()
    .filter(n -> n.length() == 4)
    .sorted()
    .limit(2)
    .forEach(System.out::println);
```

&emsp;&emsp;
The difference is that we express what is going on. We care about String objects of
length 4. Then we want them sorted. Then we want the first two. Then we want to print
them out. It maps better to the problem that we are trying to solve, and it is simpler. <br />

&emsp;&emsp;
Once you start using streams in your code, you may find yourself using them in many
places. Having shorter, briefer, and clearer code is definitely a good thing! <br />

&emsp;&emsp;
In this example, you see all three parts of the pipeline. Figure 10.5 shows how each
intermediate operation in the pipeline feeds into the next.

> **Figure 10.5**: Stream pipeline with multiple intermediate operations
> 
> ```java
> stream() ->     Intermediate Operations     -> forEach()
>             filter() -> sorted() -> limit()
> ```

&emsp;&emsp;
Remember that the assembly line foreperson is figuring out how to best implement the
stream pipeline. They set up all of the tables with instructions to wait before starting. They
tell the limit() worker to inform them when two elements go by. They tell the sorted()
worker that they should just collect all of the elements as they come in and sort them all at
once. After sorting, they should start passing them to the limit() worker one at a time. The
data flow looks like this:

1. The `stream()` method sends Toby to `filter()`. The `filter()` method sees that
   the length is good and sends Toby to `sorted()`. The `sorted()` method can’t sort yet
   because it needs all of the data, so it holds Toby.
2. The `stream()` method sends Anna to `filter()`. The `filter()` method sees that
   the length is good and sends Anna to `sorted()`. The `sorted()` method can’t sort yet
   because it needs all of the data, so it holds Anna.
3. The `stream()` method sends Leroy to `filter()`. The `filter()` method sees that the
   length is not a match, and it takes Leroy out of the assembly line processing.
4. The `stream()` method sends Alex to `filter()`. The `filter()` method sees that
   the length is good and sends Alex to `sorted()`. The `sorted()` method can’t sort yet
   because it needs all of the data, so it holds Alex. It turns out `sorted()` does have all of
   the required data, but it doesn’t know it yet.
5. The foreperson lets `sorted()` know that it is time to sort, and the sort occurs.
6. The `sorted()` method sends Alex to `limit()`. The `limit()` method remembers that it has seen
   one element and sends Alex to `forEach()`, printing Alex.
7. The `sorted()` method sends Anna to `limit()`. The `limit()` method remembers that it has seen
   two elements and sends Anna to `forEach()`, printing Anna.
8. The `limit()` method has now seen all of the elements that are needed and tells the 
   foreperson. The foreperson stops the line, and no more processing occurs in the pipeline.

&emsp;&emsp;
Make sense? Let’s try a few more examples to make sure that you understand this well.
What do you think the following does?

```java
Stream.generate(() -> "Elsa")
    .filter(n -> n.length() == 4)
    .sorted()
    .limit(2)
    .forEach(System.out::println);
```

&emsp;&emsp;
It hangs until you kill the program, or it throws an exception after running out of
memory. The foreperson has instructed `sorted()` to wait until everything to sort is present.
That never happens because there is an infinite stream. What about this example?

```java
Stream.generate(() -> "Elsa")
    .filter(n -> n.length() == 4)
    .limit(2)
    .sorted()
    .forEach(System.out::println);
```

&emsp;&emsp;
This one prints Elsa twice. The filter lets elements through, and `limit()` stops the 
earlier operations after two elements. Now `sorted()` can sort because we have a finite list.
Finally, what do you think this does?

```java
Stream.generate(() -> "Olaf Lazisson")
    .filter(n -> n.length() == 4)
    .limit(2)
    .sorted()
    .forEach(System.out::println);
```

&emsp;&emsp;
This one hangs as well until we kill the program. The filter doesn’t allow anything
through, so `limit()` never sees two elements. This means we have to keep waiting and hope
that they show up. <br />

&emsp;&emsp;
You can even chain two pipelines together. See if you can identify the two sources and
two terminal operations in this code.

```java
30: long count = Stream.of("goldfish", "finch")
31:         .filter(s -> s.length()> 5)
32:         .collect(Collectors.toList())
33:         .stream()
34:         .count();
35: System.out.println(count); // 1
```

&emsp;&emsp;
Lines 30–32 are one pipeline, and lines 33 and 34 are another. For the first pipeline, line
30 is the source, and line 32 is the terminal operation. For the second pipeline, line 33 is the
source, and line 34 is the terminal operation. Now that’s a complicated way of outputting
the number 1!

> **Tip**: <br />
> On the exam, you might see long or complex pipelines as answer
choices. If this happens, focus on the differences between the answers.
Those will be your clues to the correct answer. This approach will also
save you time by not having to study the whole pipeline on each option.

&emsp;&emsp;
When you see chained pipelines, note where the source and terminal operations are. This
will help you keep track of what is going on. You can even rewrite the code in your head
to have a variable in between so it isn’t as long and complicated. Our prior example can be
written as follows:

```java
List<String> helper = Stream.of("goldfish", "finch")
        .filter(s -> s.length()> 5)
        .collect(Collectors.toList());
long count = helper.stream()
        .count();
System.out.println(count);
```

&emsp;&emsp;
Which style you use is up to you. However, you need to be able to read both styles before
you take the exam.
