# Returning an Optional

Suppose that you are taking an introductory Java class and receive scores of 90 and 100 on
the first two exams. Now, we ask you what your average is. An average is calculated by 
adding the scores and dividing by the number of scores, so you have (90+100)/2. This gives
190/2, so you answer with 95. Great! <br />

&emsp;&emsp;
Now suppose that you are taking your second class on Java, and it is the first day of class.
We ask you what your average is in this class that just started. You haven’t taken any exams
yet, so you don’t have anything to average. It wouldn’t be accurate to say that your average
is zero. That sounds bad and isn’t true. There simply isn’t any data, so you don’t have
an average. <br />

&emsp;&emsp;
How do we express this “we don’t know” or “not applicable” answer in Java? We use the
Optional type. An Optional is created using a factory. You can either request an empty
Optional or pass a value for the Optional to wrap. Think of an Optional as a box that
might have something in it or might instead be empty. Figure 10.1 shows both options.

> **Figure 10.1** Optional <br />
> ```java
> |                 |          |       95        |
>  Optional.empty()              Optional.of(95)
> ```

## I. Creating an Optional
Here’s how to code our average method:

```java
10: public static Optional<Double> average(int... scores) {
11:     if (scores.length == 0) return Optional.empty();
12:     int sum = 0;
13:     for (int score: scores) sum += score;
14:     return Optional.of((double) sum / scores.length);
15: }
```

&emsp;&emsp;
Line 11 returns an empty Optional when we can’t calculate an average. Lines 12 and
13 add up the scores. There is a functional programming way of doing this math, but we
will get to that later in the chapter. In fact, the entire method could be written in one line,
but that wouldn’t teach you how Optional works! Line 14 creates an Optional to wrap
the average. <br />

&emsp;&emsp;
Calling the method shows what is in our two boxes:

```java
System.out.println(average(90, 100)); // Optional[95.0]
System.out.println(average());        // Optional.empty
```

&emsp;&emsp;
You can see that one Optional contains a value and the other is empty. Normally,
we want to check whether a value is there and/or get it out of the box. Here’s one way to do that:

```java
Optional<Double> opt = average(90, 100);
if (opt.isPresent())
    System.out.println(opt.get()); // 95.0
```

&emsp;&emsp;
First we check whether the Optional contains a value. Then we print it out. What if we
didn’t do the check, and the Optional was empty?

```java
Optional<Double> opt = average();
System.out.println(opt.get()); // NoSuchElementException
```

&emsp;&emsp;
We’d get an exception since there is no value inside the Optional.

```java
java.util.NoSuchElementException: No value present
```

&emsp;&emsp;
When creating an Optional, it is common to want to use empty() when the value is
null. You can do this with an if statement or ternary operator. We use the ternary operator
(? :) to simplify the code, which you saw in Chapter 2, “Operators.”

```java
Optional o = (value == null) ? Optional.empty() : Optional.of(value);
```

&emsp;&emsp;
If value is null, o is assigned the empty Optional. Otherwise, we wrap the value. Since
this is such a common pattern, Java provides a factory method to do the same thing.

```java
Optional o = Optional.ofNullable(value);
```

&emsp;&emsp;
That covers the static methods you need to know about Optional. Table 10.1 
summarizes most of the instance methods on Optional that you need to know for the exam. There
are a few others that involve chaining. We cover those later in the chapter.

> **Table 10.1** Common **Optional** instance methods
> 
> |Method|When Optional is empty|When Optional contains value|
> |------|----------------------|----------------------------|
> |get()|Throws exception|Returns value|
> |ifPresent(Consumer c)|Does nothing|Calls Consumer with value|
> |isPresent() |Returns false |Returns true|
> |orElse(T other) |Returns other parameter |Returns value|
> |orElseGet(Supplier s) |Returns result of calling Supplier |Returns value|
> |orElseThrow() |Throws NoSuchElementException |Returns value|
> |orElseThrow(Suppliers)|Throws exception created by calling Supplier|Returns value|

&emsp;&emsp;
You’ve already seen get() and isPresent(). The other methods allow you to write
code that uses an Optional in one line without having to use the ternary operator. This
makes the code easier to read. Instead of using an if statement, which we used when
checking the average earlier, we can specify a Consumer to be run when there is a value
inside the Optional. When there isn’t, the method simply skips running the Consumer.

```java
Optional<Double> opt = average(90, 100);
opt.ifPresent(System.out::println);
```

&emsp;&emsp;
Using ifPresent() better expresses our intent. We want something done if a value is
present. You can think of it as an if statement with no else.

## II. Dealing with an Empty Optional
The remaining methods allow you to specify what to do if a value isn’t present. There are
a few choices. The first two allow you to specify a return value either directly or using a Supplier.

```java
30: Optional<Double> opt = average();
31: System.out.println(opt.orElse(Double.NaN));
32: System.out.println(opt.orElseGet(() -> Math.random()));
```

&emsp;&emsp;
This prints something like the following:

```java
NaN
0.5396183290228018
```

&emsp;&emsp;
Line 31 shows that you can return a specific value or variable. In our case, we print the
“not a number” value. Line 32 shows using a Supplier to generate a value at runtime to
return instead. I’m glad our professors didn’t give us a random average, though! <br />

&emsp;&emsp;
Alternatively, we can have the code throw an exception if the Optional is empty.

```java
30: Optional<Double> opt = average();
31: System.out.println(opt.orElseThrow());
```

&emsp;&emsp;
This prints something like the following:

```java
Exception in thread "main" java.util.NoSuchElementException:
    No value present
    at java.base/java.util.Optional.orElseThrow(Optional.java:382)
```

&emsp;&emsp;
Without specifying a Supplier for the exception, Java will throw a
NoSuchElementException. Alternatively, we can have the code throw a custom exception
if the Optional is empty. Remember that the stack trace looks weird because the lambdas are
generated rather than named classes. 

```java
30: Optional<Double> opt = average();
31: System.out.println(opt.orElseThrow(
32:     () -> new IllegalStateException()));
```

&emsp;&emsp;
This prints something like the following:

```java
Exception in thread "main" java.lang.IllegalStateException
    at optionals.Methods.lambda$orElse$1(Methods.java:31)
    at java.base/java.util.Optional.orElseThrow(Optional.java:408)
```

&emsp;&emsp;
Line 32 shows using a Supplier to create an exception that should be thrown. Notice
that we do not write throw new IllegalStateException(). The orElseThrow()
method takes care of actually throwing the exception when we run it. <br />

&emsp;&emsp;
The two methods that take a Supplier have different names. Do you see why this code
does not compile?

```java
System.out.println(opt.orElseGet(
    () -> new IllegalStateException())); // DOES NOT COMPILE
```

&emsp;&emsp;
The opt variable is an Optional<Double>. This means the Supplier must return a
Double. Since this Supplier returns an exception, the type does not match. <br />

&emsp;&emsp;
The last example with Optional is really easy. What do you think this does?

```java
Optional<Double> opt = average(90, 100);
System.out.println(opt.orElse(Double.NaN));
System.out.println(opt.orElseGet(() -> Math.random()));
System.out.println(opt.orElseThrow());
```

&emsp;&emsp;
It prints out 95.0 three times. Since the value does exist, there is no need to use the “or
else” logic.

> #### **Is Optional the Same as null?**
> An alternative to Optional is to return null. There are a few shortcomings with this
approach. One is that there isn’t a clear way to express that null might be a special value.
By contrast, returning an Optional is a clear statement in the API that there might not
be a value. <br />
> 
> Another advantage of Optional is that you can use a functional programming style with
ifPresent() and the other methods rather than needing an if statement. Finally, you
see toward the end of the chapter that you can chain Optional calls.
