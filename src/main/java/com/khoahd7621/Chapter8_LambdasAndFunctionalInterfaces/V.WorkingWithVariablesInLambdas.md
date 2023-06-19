# Working with Variables in Lambdas

Now that we’ve learned about functional interfaces, we will use them to show different
approaches for variables. They can appear in three places with respect to lambdas: the
parameter list, local variables declared inside the lambda body, and variables referenced from
the lambda body. All three of these are opportunities for the exam to trick you. We explore
each one so you’ll be alert when tricks show up!

## I. Listing Parameters
Earlier in this chapter, you learned that specifying the type of parameters is optional. Additionally, 
var can be used in place of the specific type. That means that all three of these statements are interchangeable:

```java
Predicate<String> p = x -> true;
Predicate<String> p = (var x) -> true;
Predicate<String> p = (String x) -> true;
```

&emsp;&emsp;
The exam might ask you to identify the type of the lambda parameter. In our example,
the answer is String. How did we figure that out? A lambda infers the types from the 
surrounding context. That means you get to do the same. <br />

&emsp;&emsp;
In this case, the lambda is being assigned to a Predicate that takes a String. Another
place to look for the type is in a method signature. Let’s try another example. Can you figure
out the type of x?

```java
public void whatAmI() {
    consume((var x) -> System.out.print(x), 123);
}

public void consume(Consumer<Integer> c, int num) {
    c.accept(num);
}
```

&emsp;&emsp;
If you guessed Integer, you were right. The whatAmI() method creates a lambda to be
passed to the consume() method. Since the consume() method expects an Integer as the
generic, we know that is what the inferred type of x will be. <br />

&emsp;&emsp;
But wait; there’s more. In some cases, you can determine the type without even seeing the
method signature. What do you think the type of x is here?

```java
public void counts(List<Integer> list) {
    list.sort((var x, var y) -> x.compareTo(y));
}
```

&emsp;&emsp;
The answer is again Integer. Since we are sorting a list, we can use the type of the list to
determine the type of the lambda parameter. <br />

&emsp;&emsp;
Since lambda parameters are just like method parameters, you can add modifiers to them.
Specifically, you can add the final modifier or an annotation, as shown in this example:

```java
public void counts(List<Integer> list) {
    list.sort((final var x, @Deprecated var y) -> x.compareTo(y));
}
```

&emsp;&emsp;
While this tends to be uncommon in real life, modifiers such as these have been known to
appear in passing on the exam.

> **Parameter List Formats** <br />
> You have three formats for specifying parameter types within a lambda: without types, with
types, and with var. The compiler requires all parameters in the lambda to use the same
format. Can you see why the following are not valid?
> ```java
>   5: (var x, y) -> "Hello"                // DOES NOT COMPILE
>   6: (var x, Integer y) -> true           // DOES NOT COMPILE
>   7: (String x, var y, Integer z) -> true // DOES NOT COMPILE
>   8: (Integer x, y) -> "goodbye"          // DOES NOT COMPILE
> ```
> Lines 5 needs to remove var from x or add it to y. Next, lines 6 and 7 need to use the type
or var consistently. Finally, line 8 needs to remove Integer from x or add a type to y.

## II. Using Local Variables Inside a Lambda Body

While it is most common for a lambda body to be a single expression, it is legal to define a
block. That block can have anything that is valid in a normal Java block, including local variable declarations. <br />

&emsp;&emsp;
The following code does just that. It creates a local variable named c that is scoped to the
lambda block:

```java
(a, b) -> { int c = 0; return 5; }
```

&emsp;&emsp;
Now let’s try another one. Do you see what’s wrong here?

```java
(a, b) -> { int a = 0; return 5; }  // DOES NOT COMPILE
```

&emsp;&emsp;
We tried to redeclare a, which is not allowed. Java doesn’t let you create a local variable
with the same name as one already declared in that scope. While this kind of error is less
likely to come up in real life, it has been known to appear on the exam! <br />

&emsp;&emsp;
Now let’s try a hard one. How many syntax errors do you see in this method?

```java
11: public void variables(int a) {
12:     int b = 1;
13:     Predicate<Integer> p1 = a -> {
14:         int b = 0;
15:         int c = 0;
16:         return b == c; }
17: }
```

&emsp;&emsp;
There are three syntax errors. The first is on line 13. The variable a was already used in
this scope as a method parameter, so it cannot be reused. The next syntax error comes on
line 14, where the code attempts to redeclare local variable b. The third syntax error is quite
subtle and on line 16. See it? Look really closely. <br />

&emsp;&emsp;
The variable p1 is missing a semicolon at the end. There is a semicolon before the }, but
that is inside the block. While you don’t normally have to look for missing semicolons,
lambdas are tricky in this space, so beware!

> **Keep Your Lambdas Short** <br />
> Having a lambda with multiple lines and a return statement is often a clue that you
should refactor and put that code in a method. For example, the previous example could be
rewritten as
> ```java
> Predicate<Integer> p1 = a -> returnSame(a);
> ```
> This simpler form can be further refactored to use a method reference:
> ```java
> Predicate<Integer> p1 = this::returnSame;
> ```
> You might be wondering why this is so important. In Chapter 10, lambdas and method
references are used in chained method calls. The shorter the lambda, the easier it is to
read the code.

## III. Referencing Variables from the Lambda Body
Lambda bodies are allowed to reference some variables from the surrounding code. The
following code is legal:

```java
public class Crow {
    private String color;
    public void caw(String name) {
        String volume = "loudly";
        Consumer<String> consumer = s ->
                System.out.println(name + " says "
                        + volume + " that she is " + color);
    }
}
```

&emsp;&emsp;
This shows that a lambda can access an instance variable, method parameter, or local 
variable under certain conditions. Instance variables (and class variables) are always allowed. <br />

&emsp;&emsp;
The only thing lambdas cannot access are variables that are not final or effectively final. If
you need a refresher on effectively final, see Chapter 5, “Methods.” <br />

&emsp;&emsp;
It gets even more interesting when you look at where the compiler errors occur when the
variables are not effectively final.

```java
2:  public class Crow {
3:      private String color;
4:      public void caw(String name) {
5:          String volume = "loudly";
6:          name = "Caty";
7:          color = "black";
8:
9:          Consumer<String> consumer = s ->
10:             System.out.println(name + " says " // DOES NOT COMPILE
11:                 + volume + " that she is " + color); // DOES NOT COMPILE
12:         volume = "softly";
13:     }
14: }
```

&emsp;&emsp;
In this example, the method parameter name is not effectively final because it is set on
line 6. However, the compiler error occurs on line 10. It’s not a problem to assign a value to
a non-final variable. However, once the lambda tries to use it, we do have a problem. The
variable is no longer effectively final, so the lambda is not allowed to use the variable. <br />

&emsp;&emsp;
The variable volume is not effectively final either since it is updated on line 12. In this
case, the compiler error is on line 11. That’s before the reassignment! Again, the act of
assigning a value is only a problem from the point of view of the lambda. Therefore, the
lambda has to be the one to generate the compiler error.

&emsp;&emsp;
To review, make sure you’ve memorized Table 8.8.

> **TABLE 8.8** Rules for accessing a variable from a lambda body inside a method

|Variable type|Rule|
|-------------|----|
|Instance variable |Allowed|
|Static variable |Allowed|
|Local variable |Allowed if final or effectively final|
|Method parameter |Allowed if final or effectively final|
|Lambda parameter |Allowed|
