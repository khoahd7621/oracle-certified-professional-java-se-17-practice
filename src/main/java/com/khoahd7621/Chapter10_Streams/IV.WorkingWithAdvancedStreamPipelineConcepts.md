# Working with Advanced Stream Pipeline Concepts

Congrats, you only have a few more topics left! In this last stream section, we learn about
the relationship between streams and the underlying data, chaining Optional, and grouping
collectors. After this, you should be a pro with streams!

## I. Linking Streams to the Underlying Data

What do you think this outputs?

```java
25: var cats = new ArrayList<String>();
26: cats.add("Annie");
27: cats.add("Ripley");
28: var stream = cats.stream();
29: cats.add("KC");
30: System.out.println(stream.count());
```

&emsp;&emsp;
The correct answer is 3. Lines 25–27 create a List with two elements. Line 28 requests
that a stream be created from that List. Remember that streams are lazily evaluated. This
means that the stream isn’t created on line 28. An object is created that knows where to
look for the data when it is needed. On line 29, the List gets a new element. On line 30, the
stream pipeline runs. First, it looks at the source and seeing three elements.

## II. Chaining Optionals
By now, you are familiar with the benefits of chaining operations in a stream pipeline. A few
of the intermediate operations for streams are available for Optional. <br />

&emsp;&emsp;
Suppose that you are given an `Optional<Integer>` and asked to print the value, but only if
it is a three-digit number. Without functional programming, you could write the following:

```java
private static void threeDigit(Optional<Integer> optional) {
    if (optional.isPresent()) { // outer if
        var num = optional.get();
        var string = "" + num;
        if (string.length() == 3) // inner if
        System.out.println(string);
    }
}
```

&emsp;&emsp;
It works, but it contains nested if statements. That’s extra complexity. Let’s try this again
with functional programming:

```java
private static void threeDigit(Optional<Integer> optional) {
    optional.map(n -> "" + n)            // part 1
        .filter(s -> s.length() == 3)    // part 2
        .ifPresent(System.out::println); // part 3
}
```

&emsp;&emsp;
This is much shorter and more expressive. With lambdas, the exam is fond of carving
up a single statement and identifying the pieces with a comment. We’ve done that here to
show what happens with both the functional programming and nonfunctional programming
approaches. <br />

&emsp;&emsp;
Suppose that we are given an empty Optional. The first approach returns false for the outer
if statement. The second approach sees an empty Optional and has both `map()` and `filter()` pass
it through. Then `ifPresent()` sees an empty Optional and doesn’t call the Consumer parameter. <br />

&emsp;&emsp;
The next case is where we are given an `Optional.of(4)`. The first approach returns
false for the inner if statement. The second approach maps the number 4 to "4".
The `filter()` then returns an empty Optional since the filter doesn’t match, and
`ifPresent()` doesn’t call the Consumer parameter. <br />

&emsp;&emsp;
The final case is where we are given an `Optional.of(123)`. The first approach returns
true for both if statements. The second approach maps the number 123 to "123".
The `filter()` then returns the same Optional, and `ifPresent()` now does call the
Consumer parameter. <br />

&emsp;&emsp;
Now suppose that we wanted to get an `Optional<Integer>` representing the length of the
String contained in another Optional. Easy enough:

```java
Optional<Integer> result = optional.map(String::length);
```

&emsp;&emsp;
What if we had a helper method that did the logic of calculating something for us that
returns `Optional<Integer>`? Using map doesn’t work:

```java
Optional<Integer> result = optional
    .map(ChainingOptionals::calculator); // DOES NOT COMPILE
```

&emsp;&emsp;
The problem is that calculator returns `Optional<Integer>`. The `map()` method adds
another Optional, giving us `Optional<Optional<Integer>>`. Well, that’s no good. The
solution is to call `flatMap()`, instead:

```java
Optional<Integer> result = optional
    .flatMap(ChainingOptionals::calculator);
```

&emsp;&emsp;
This one works because flatMap removes the unnecessary layer. In other words, it 
flattens the result. Chaining calls to `flatMap()` is useful when you want to transform one
Optional type to another.

> ### **Real World Scenario**
> #### **Checked Exceptions and Functional Interfaces**
> You might have noticed by now that most functional interfaces do not declare checked
exceptions. This is normally okay. However, it is a problem when working with methods
that declare checked exceptions. Suppose that we have a class with a method that throws a
checked exception:
> ```java
>   import java.io.*;
>   import java.util.*;
>   public class ExceptionCaseStudy {
>       private static List<String> create() throws IOException {
>           throw new IOException();
>       }
>   }
> ```
> Now we use it in a stream:
> ```java
>   public void good() throws IOException {
>       ExceptionCaseStudy.create().stream().count();
>   }
> ```
> Nothing new here. The `create()` method throws a checked exception. The calling method
handles or declares it. Now, what about this one?
> ```java
>   public void bad() throws IOException {
>       Supplier<List<String>> s = ExceptionCaseStudy::create; // DOES NOT COMPILE
>   }
> ```
> The actual compiler error is as follows:
> ```java
>   unhandled exception type IOException
> ```
> Say what now? The problem is that the lambda to which this method reference expands
does not declare an exception. The Supplier interface does not allow checked exceptions.
There are two approaches to get around this problem. One is to catch the exception and
turn it into an unchecked exception.
> ```java
>   public void ugly() {
>       Supplier<List<String>> s = () -> {
>           try {
>               return ExceptionCaseStudy.create();
>           } catch (IOException e) {
>               throw new RuntimeException(e);
>           }
>       };
>   }
> ```
> This works. But the code is ugly. One of the benefits of functional programming is that the
code is supposed to be easy to read and concise. Another alternative is to create a wrapper
method with try/catch.
> ```java
>   private static List<String> createSafe() {
>       try {
>           return ExceptionCaseStudy.create();
>       } catch (IOException e) {
>           throw new RuntimeException(e);
>       } 
>   }
> ```
> Now we can use the safe wrapper in our Supplier without issue.
> ```java
>   public void wrapped() {
>       Supplier<List<String>> s2 = ExceptionCaseStudy::createSafe;
    }
> ```

## III. Using a Spliterator
Suppose you buy a bag of food so two children can feed the animals at the petting zoo. To
avoid arguments, you have come prepared with an extra empty bag. You take roughly half
the food out of the main bag and put it into the bag you brought from home. The original
bag still exists with the other half of the food. <br />

&emsp;&emsp;
A Spliterator provides this level of control over processing. It starts with a Collection or a
stream—that is your bag of food. You call `trySplit()` to take some food out of the bag. The
rest of the food stays in the original Spliterator object. <br />

&emsp;&emsp;
The characteristics of a Spliterator depend on the underlying data source. A Collection data
source is a basic Spliterator. By contrast, when using a Stream data source, the Spliterator can
be parallel or even infinite. The Stream itself is executed lazily rather than when the Spliterator
is created. <br />

&emsp;&emsp;
Implementing your own Spliterator can get complicated and is conveniently not on the
exam. You do need to know how to work with some of the common methods declared on
this interface. The simplified methods you need to know are in Table 10.9.

> **Table 10.9**: Spliterator methods
> 
> | Method | Description                                                                                                                                                                                                     |
> | ------ |-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
> |Spliterator<T> trySplit()| Returns Spliterator containing ideally half of the data, which is removed from current Spliterator. This method can be called multiple times and will eventually return null when data is no longer splittable. |
> |void forEachRemaining(Consumer<T> c)| Processes remaining elements in Spliterator.                                                                                                                                                                    |
> |boolean tryAdvance(Consumer<T> c)| Processes single element from Spliterator if any remain. Returns whether element was processed.

&emsp;&emsp;
Now let’s look at an example where we divide the bag into three:

```java
12: var stream = List.of("bird-", "bunny-", "cat-", "dog-", "fish-", "lamb-", 
13:     "mouse-");
14: Spliterator<String> originalBagOfFood = stream.spliterator();
15: Spliterator<String> emmasBag = originalBagOfFood.trySplit();
16: emmasBag.forEachRemaining(System.out::print); // bird-bunny-cat-
17:
18: Spliterator<String> jillsBag = originalBagOfFood.trySplit();
19: jillsBag.tryAdvance(System.out::print); // dog-
20: jillsBag.forEachRemaining(System.out::print); // fish-
21: 
22: originalBagOfFood.forEachRemaining(System.out::print); // lamb-mouse-
```

&emsp;&emsp;
On lines 12 and 13, we define a List. Lines 14 and 15 create two Spliterator references. 
The first is the original bag, which contains all seven elements. The second is our split
of the original bag, putting roughly half of the elements at the front into Emma’s bag. We
then print the three contents of Emma’s bag on line 16. <br />

&emsp;&emsp;
Our original bag of food now contains four elements. We create a new Spliterator on
line 18 and put the first two elements into Jill’s bag. We use `tryAdvance()` on line 19 to
output a single element, and then line 20 prints all remaining elements (just one left!). <br />

&emsp;&emsp;
We started with seven elements, removed three, and then removed two more. This leaves
us with two elements in the original bag created on line 14. These two items are output
on line 22. <br />

&emsp;&emsp;
Now let’s try an example with a Stream. This is a complicated way to print out 123:

```java
var originalBag = Stream.iterate(1, n -> ++n)
    .spliterator();

Spliterator<Integer> newBag = originalBag.trySplit();

newBag.tryAdvance(System.out::print); // 1
newBag.tryAdvance(System.out::print); // 2
newBag.tryAdvance(System.out::print); // 3
```

&emsp;&emsp;
You might have noticed that this is an infinite stream. No problem! The Spliterator
recognizes that the stream is infinite and doesn’t attempt to give you half. Instead, newBag
contains a large number of elements. We get the first three since we call `tryAdvance()`
three times. It would be a bad idea to call `forEachRemaining()` on an infinite stream! <br />

&emsp;&emsp;
Note that a Spliterator can have a number of characteristics such as CONCURRENT,
ORDERED, SIZED, and SORTED. You will only see a straightforward Spliterator on the
exam. For example, our infinite stream was not SIZED.

## IV. Collecting Results

You’re almost finished learning about streams. The last topic builds on what you’ve learned
so far to group the results. Early in the chapter, you saw the `collect()` terminal operation.
There are many predefined collectors, including those shown in Table 10.10. These collectors
are available via static methods on the Collectors class. We look at the different types of 
collectors in the following sections. We left out the generic types for simplicity.

> **Note**: <br />
> There is one more collector called `reducing()`. You don’t need to know it
for the exam. It is a general reduction in case all of the previous collectors
don’t meet your needs.

### &emsp;&emsp; 1. Using Basic Collectors
Luckily, many of these collectors work the same way. Let’s look at an example:

```java
var ohMy = Stream.of("lions", "tigers", "bears");
String result = ohMy.collect(Collectors.joining(", "));
System.out.println(result); // lions, tigers, bears
```

&emsp;&emsp;
Notice how the predefined collectors are in the Collectors class rather than the
Collector interface. This is a common theme, which you saw with Collection versus
Collections. In fact, you see this pattern again in Chapter 14 when working with Paths
and Path and other related types.

> **Table 10.10**: Examples of grouping/partitioning collectors
> 
> | Collector                                                                                                            | Description                                                                                                  |Return valued when passed to collect|
> |----------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------| ---------------------------------- |
> | averagingDouble(ToDoubleFunction f)<br/>averagingInt(ToIntFunction f)<br/>averagingLong(ToLongFunction f)            | Calculates average for three core primitive types                                                            |Double|
> | counting()                                                                                                           | Counts number of elements                                                                                    |Long|
> | filtering(Predicate p, Collector c)                                                                                  | Applies filter before calling downstream collector                                                           |R|
> | groupingBy(Function f) <br />groupingBy(Function f, Collector dc) <br />groupingBy(Function f, Supplier s, Collector dc) | Creates map grouping by specified function with optional map type supplier and optional downstream collector |Map<K, List<T>>|
> | joining(CharSequence cs)                                                                                             | Creates single String using cs as delimiter between elements if one is specified                             |String|
> | maxBy(Comparator c)<br/>minBy(Comparator c)                                                                          | Finds largest/smallest elements                                                                              |Optional<T>|
> | mapping(Function f, Collector dc)                                                                                    | Adds another level of collectors                                                                             |Collector|
> | partitioningBy(Predicate p)<br/>partitioningBy(Predicate p, Collector dc)|Creates map grouping by specified predicate with optional further downstream collector |Map<Boolean, List<T>>|
> | summarizingDouble(ToDoubleFunction f)<br/>summarizingInt(ToIntFunction f)<br/>summarizingLong(ToLongFunction f)|Calculates average, min, max, etc.|DoubleSummaryStatistics<br/>IntSummaryStatistics<br/>LongSummaryStatistics|
> | summingDouble(ToDoubleFunction f)<br/>summingInt(ToIntFunction f)<br/>summingLong(ToLongFunction f)|Calculates sum for our three core primitive types |Double<br/>Integer<br/>Long|
> | teeing(Collector c1, Collector c2, BiFunction f)|Works with results of two collectors to create new type|R|
> | toList()<br/>toSet()|Creates arbitrary type of list or set |List<br/>Set|
> | toCollection(Supplier s)|Creates Collection of specified type|Collection|
> | toMap(Function k, Function v)<br/>toMap(Function k, Function v, BinaryOperator m)<br/>toMap(Function k, Function v, BinaryOperator m, Supplier s)|Creates map using functions to map keys, values, optional merge function, and optional map type supplier |Map|

&emsp;&emsp;
We pass the predefined `joining()` collector to the `collect()` method. All elements of
the stream are then merged into a String with the specified delimiter between each element.
It is important to pass the Collector to the collect method. It exists to help collect 
elements. A Collector doesn’t do anything on its own. <br/>

&emsp;&emsp;
Let’s try another one. What is the average length of the three animal names?

```java
var ohMy = Stream.of("lions", "tigers", "bears");
Double result = ohMy.collect(Collectors.averagingInt(String::length));
System.out.println(result); // 5.333333333333333
```

&emsp;&emsp;
The pattern is the same. We pass a collector to `collect()`, and it performs the average
for us. This time, we needed to pass a function to tell the collector what to average. We used
a method reference, which returns an int upon execution. With primitive streams, the result
of an average was always a double, regardless of what type is being averaged. For collectors, 
it is a Double since those need an Object. <br />

&emsp;&emsp;
Often, you’ll find yourself interacting with code that was written without streams. This
means that it will expect a Collection type rather than a Stream type. No problem. You
can still express yourself using a Stream and then convert to a Collection at the end.
For example:

```java
var ohMy = Stream.of("lions", "tigers", "bears");
TreeSet<String> result = ohMy
    .filter(s -> s.startsWith("t"))
    .collect(Collectors.toCollection(TreeSet::new));
System.out.println(result); // [tigers]
```

&emsp;&emsp;
This time we have all three parts of the stream pipeline. `Stream.of()` is the source
for the stream. The intermediate operation is `filter()`. Finally, the terminal operation is
`collect()`, which creates a TreeSet. If we didn’t care which implementation of Set we
got, we could have written `Collectors.toSet()`, instead. <br />

&emsp;&emsp;
At this point, you should be able to use all of the Collectors in Table 10.10 except
`groupingBy()`, `mapping()`, `partitioningBy()`, `toMap()`, and `teeing()`.

### &emsp;&emsp; 2. Collecting into Maps
Code using Collectors involving maps can get quite long. We will build it up slowly. Make
sure that you understand each example before going on to the next one. Let’s start with a
straightforward example to create a map from a stream:

```java
var ohMy = Stream.of("lions", "tigers", "bears");
Map<String, Integer> map = ohMy.collect(
    Collectors.toMap(s -> s, String::length));
System.out.println(map); // {lions=5, bears=5, tigers=6}
```

&emsp;&emsp;
When creating a map, you need to specify two functions. The first function tells the
collector how to create the key. In our example, we use the provided String as the key. The
second function tells the collector how to create the value. In our example, we use the length
of the String as the value.

> **Note**: <br/>
> Returning the same value passed into a lambda is a common operation, 
so Java provides a method for it. You can rewrite `s -> s` as
`Function.identity()`. It is not shorter and may or may not be clearer,
so use your judgment about whether to use it.

&emsp;&emsp;
Now we want to do the reverse and map the length of the animal name to the name itself.
Our first incorrect attempt is shown here:

```java
var ohMy = Stream.of("lions", "tigers", "bears");
Map<Integer, String> map = ohMy.collect(Collectors.toMap(String::length, k -> k)); // BAD
```

&emsp;&emsp;
Running this gives an exception similar to the following:

```java
Exception in thread "main" java.lang.IllegalStateException: Duplicate key 5
```

&emsp;&emsp;
What’s wrong? Two of the animal names are the same length. We didn’t tell Java what
to do. Should the collector choose the first one it encounters? The last one it encounters?
Concatenate the two? Since the collector has no idea what to do, it “solves” the problem
by throwing an exception and making it our problem. How thoughtful. Let’s suppose that
our requirement is to create a comma-separated String with the animal names. We could
write this:

```java
var ohMy = Stream.of("lions", "tigers", "bears");
Map<Integer, String> map = ohMy.collect(Collectors.toMap(
    String::length,
    k -> k,
    (s1, s2) -> s1 + "," + s2));
System.out.println(map); // {5=lions,bears, 6=tigers}
System.out.println(map.getClass()); // class java.util.HashMap
```

&emsp;&emsp;
It so happens that the Map returned is a HashMap. This behavior is not guaranteed. 
Suppose that we want to mandate that the code return a TreeMap instead. No problem. We
would just add a constructor reference as a parameter:

```java
var ohMy = Stream.of("lions", "tigers", "bears");
TreeMap<Integer, String> map = ohMy.collect(Collectors.toMap(
    String::length,
    k -> k,
    (s1, s2) -> s1 + "," + s2,
    TreeMap::new));
System.out.println(map); // // {5=lions,bears, 6=tigers}
System.out.println(map.getClass()); // class java.util.TreeMap
```

&emsp;&emsp;
This time we get the type that we specified. With us so far? This code is long but not 
particularly complicated. We did promise you that the code would be long!

### &emsp;&emsp; 3. Grouping, Partitioning, and Mapping
Great job getting this far. The exam creators like asking about `groupingBy()` and
`partitioningBy()`, so make sure you understand these sections very well. Now suppose
that we want to get groups of names by their length. We can do that by saying that we want
to group by length.

```java
var ohMy = Stream.of("lions", "tigers", "bears");
Map<Integer, List<String>> map = ohMy.collect(
    Collectors.groupingBy(String::length));
System.out.println(map); // {5=[lions, bears], 6=[tigers]}
```

&emsp;&emsp;
The `groupingBy()` collector tells `collect()` that it should group all of the elements of
the stream into a Map. The function determines the keys in the Map. Each value in the Map is
a List of all entries that match that key.

> **Note**: <br/>
> Note that the function you call in `groupingBy()` cannot return null. It
does not allow null keys.

&emsp;&emsp;
Suppose that we don’t want a List as the value in the map and prefer a Set instead. No
problem. There’s another method signature that lets us pass a downstream collector. This is
a second collector that does something special with the values.

```java
var ohMy = Stream.of("lions", "tigers", "bears");
Map<Integer, Set<String>> map = ohMy.collect(
    Collectors.groupingBy(
        String::length,
        Collectors.toSet()));
System.out.println(map); // {5=[lions, bears], 6=[tigers]}
```

&emsp;&emsp;
We can even change the type of Map returned through yet another parameter.

```java
var ohMy = Stream.of("lions", "tigers", "bears");
TreeMap<Integer, Set<String>> map = ohMy.collect(
    Collectors.groupingBy(
        String::length,
        TreeMap::new,
        Collectors.toSet()));
System.out.println(map); // {5=[lions, bears], 6=[tigers]}
```

&emsp;&emsp;
This is very flexible. What if we want to change the type of Map returned but leave the
type of values alone as a List? There isn’t a method for this specifically because it is easy
enough to write with the existing ones.

```java
var ohMy = Stream.of("lions", "tigers", "bears");
TreeMap<Integer, List<String>> map = ohMy.collect(
    Collectors.groupingBy(
        String::length,
        TreeMap::new,
        Collectors.toList()));
System.out.println(map);
```

&emsp;&emsp;
Partitioning is a special case of grouping. With partitioning, there are only two possible
groups: true and false. Partitioning is like splitting a list into two parts. <br/>

&emsp;&emsp;
Suppose that we are making a sign to put outside each animal’s exhibit. We have two sizes
of signs. One can accommodate names with five or fewer characters. The other is needed for
longer names. We can partition the list according to which sign we need.

```java
var ohMy = Stream.of("lions", "tigers", "bears");
Map<Boolean, List<String>> map = ohMy.collect(
    Collectors.partitioningBy(s -> s.length() <= 5));
System.out.println(map); // {false=[tigers], true=[lions, bears]}
```

&emsp;&emsp;
Here we pass a Predicate with the logic for which group each animal name belongs in.
Now suppose that we’ve figured out how to use a different font, and seven characters can
now fit on the smaller sign. No worries. We just change the Predicate.

```java
var ohMy = Stream.of("lions", "tigers", "bears");
Map<Boolean, List<String>> map = ohMy.collect(
    Collectors.partitioningBy(s -> s.length() <= 7));
System.out.println(map); // {false=[], true=[lions, tigers, bears]}
```

&emsp;&emsp;
Notice that there are still two keys in the map—one for each boolean value. It so 
happens that one of the values is an empty list, but it is still there. As with groupingBy(), we
can change the type of List to something else.

```java
var ohMy = Stream.of("lions", "tigers", "bears");
Map<Boolean, Set<String>> map = ohMy.collect(
    Collectors.partitioningBy(
        s -> s.length() <= 7,
        Collectors.toSet()));
System.out.println(map); // {false=[], true=[lions, tigers, bears]}
```

&emsp;&emsp;
Unlike `groupingBy()`, we cannot change the type of Map that is returned. However,
there are only two keys in the map, so does it really matter which Map type we use? <br />

&emsp;&emsp;
Instead of using the downstream collector to specify the type, we can use any of the 
collectors that we’ve already shown. For example, we can group by the length of the animal
name to see how many of each length we have.

```java
var ohMy = Stream.of("lions", "tigers", "bears");
Map<Integer, Long> map = ohMy.collect(
    Collectors.groupingBy(
        String::length,
        Collectors.counting()));
System.out.println(map); // {5=2, 6=1}
```

> **Debugging Complicated Generics**: <br/>
> When working with `collect()`, there are often many levels of generics, making compiler
errors unreadable. Here are three useful techniques for dealing with this situation:
> - Start over with a simple statement, and keep adding to it. By making one tiny change at
    a time, you will know which code introduced the error.
> - Extract parts of the statement into separate statements. For example, try writing
    `Collectors.groupingBy(String::length, Collectors.counting());`. If it
    compiles, you know that the problem lies elsewhere. If it doesn’t compile, you have a
    much shorter statement to troubleshoot.
> - Use generic wildcards for the return type of the final statement: for example,
    `Map<?, ?>`. If that change alone allows the code to compile, you’ll know that the
    problem lies with the return type not being what you expect.

&emsp;&emsp;
Finally, there is a `mapping()` collector that lets us go down a level and add another
collector. Suppose that we wanted to get the first letter of the first animal alphabetically of
each length. Why? Perhaps for random sampling. The examples on this part of the exam are
fairly contrived as well. We’d write the following: <br/>

```java
var ohMy = Stream.of("lions", "tigers", "bears");
Map<Integer, Optional<Character>> map = ohMy.collect(
    Collectors.groupingBy(
        String::length,
        Collectors.mapping(
            s -> s.charAt(0),
            Collectors.minBy((a, b) -> a - b))));
System.out.println(map); // {5=Optional[b], 6=Optional[t]}
```

&emsp;&emsp;
We aren’t going to tell you that this code is easy to read. We will tell you that it is the
most complicated thing you need to understand for the exam. Comparing it to the previous
example, you can see that we replaced `counting()` with `mapping()`. It so happens that
`mapping()` takes two parameters: the function for the value and how to group it further. <br/>

&emsp;&emsp;
You might see collectors used with a static import to make the code shorter. The exam
might even use var for the return value and less indentation than we used. This means that
you might see something like this:

```java
var ohMy = Stream.of("lions", "tigers", "bears");
var map = ohMy.collect(groupingBy(String::length,
    mapping(s -> s.charAt(0), minBy((a, b) -> a - b))));
System.out.println(map); // {5=Optional[b], 6=Optional[t]}
```

&emsp;&emsp;
The code does the same thing as in the previous example. This means that it is important
to recognize the collector names because you might not have the Collectors class name to
call your attention to it.

### &emsp;&emsp; 4. Teeing Collectors
Suppose you want to return two things. As we’ve learned, this is problematic with streams
because you only get one pass. The summary statistics are good when you want those 
operations. Luckily, you can use `teeing()` to return multiple values of your own. <br/>

&emsp;&emsp;
First, define the return type. We use a record here:

```java
record Separations(String spaceSeparated, String commaSeparated) {}
```

&emsp;&emsp;
Now we write the stream. As you read, pay attention to the number of Collectors:

```java
var list = List.of("x", "y", "z");
Separations result = list.stream()
    .collect(Collectors.teeing(
                Collectors.joining(" "),
                Collectors.joining(","),
                (s, c) -> new Separations(s, c)));
System.out.println(result);
```

&emsp;&emsp;
When executed, the code prints the following:

```java
Separations[spaceSeparated=x y z, commaSeparated=x,y,z]
```

&emsp;&emsp;
There are three Collectors in this code. Two of them are for `joining()` and produce
the values we want to return. The third is `teeing()`, which combines the results into the
single object we want to return. This way, Java is happy because only one object is returned,
and we are happy because we don’t have to go through the stream twice.
