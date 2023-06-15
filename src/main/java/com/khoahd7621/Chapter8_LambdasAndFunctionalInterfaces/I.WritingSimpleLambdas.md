# Writing Simple Lambdas

Java is an object-oriented language at heart. You’ve seen plenty of objects by now.
Functional programming is a way of writing code more declaratively. You specify what you
want to do rather than dealing with the state of objects. You focus more on expressions
than loops. <br />

&emsp;&emsp;
Functional programming uses lambda expressions to write code. A lambda expression is a
block of code that gets passed around. You can think of a lambda expression as an unnamed
method existing inside an anonymous class like the ones you saw in Chapter 7, “Beyond
Classes.” It has parameters and a body just like full-fledged methods do, but it doesn’t have a
name like a real method. Lambda expressions are often referred to as lambdas for short. You
might also know them as closures if Java isn’t your first language. If you had a bad 
experience with closures in the past, don’t worry. They are far simpler in Java. <br />

&emsp;&emsp;
Lambdas allow you to write powerful code in Java. In this section, we cover an example
of why lambdas are helpful and the syntax of lambdas.

## I. Looking at a Lambda Example
Our goal is to print out all the animals in a list according to some criteria. We show you how to
do this without lambdas to illustrate how lambdas are useful. We start with the Animal record:

```java
public record Animal(String species, boolean canHop, boolean canSwim) { }
```

&emsp;&emsp;
The Animal record has three fields. Let’s say we have a list of animals, and we want to 
process the data based on a particular attribute. For example, we want to print all animals that can
hop. We can define an interface to generalize this concept and support a large variety of checks:

```java
public interface CheckTrait {
    boolean test(Animal a);
}
```

&emsp;&emsp;
The first thing we want to check is whether the Animal can hop. We provide a class that
implements our interface:

```java
public class CheckIfHopper implements CheckTrait {
    public boolean test(Animal a) {
        return a.canHop();
    }
}
```

&emsp;&emsp;
This class may seem simple — and it is. This is part of the problem that lambdas solve. Just
bear with us for a bit. Now we have everything we need to write our code to find out if an
Animal can hop:

```java
1:  import java.util.*;
2:  public class TraditionalSearch {
3:      public static void main(String[] args) {
4:
5:          // list of animals
6:          var animals = new ArrayList<Animal>();
7:          animals.add(new Animal("fish", false, true));
8:          animals.add(new Animal("kangaroo", true, false));
9:          animals.add(new Animal("rabbit", true, false));
10:         animals.add(new Animal("turtle", false, true));
11:
12:         // pass class that does check
13:         print(animals, new CheckIfHopper());
14:     }
15:     private static void print(List<Animal> animals, CheckTrait checker) {
16:         for (Animal animal : animals) {
17:
18:             // General check
19:             if (checker.test(animal))
20:                 System.out.print(animal + " ");
21:         }
22:         System.out.println();
23:     }
24: }
```

&emsp;&emsp;
Line 6 shows configuring an ArrayList with a specific type of Animal. The print()
method on line 15 is very general—it can check for any trait. This is good design. It
shouldn’t need to know what specifically we are searching for in order to print a list
of animals. <br />

&emsp;&emsp;
What happens if we want to print the Animals that swim? Sigh. We need to write another
class, CheckIfSwims. Granted, it is only a few lines, but it is a whole new file. Then we need
to add a new line under line 13 that instantiates that class. That’s two things just to do
another check. <br />

&emsp;&emsp;
Why can’t we specify the logic we care about right here? It turns out that we can, with
lambda expressions. We could repeat the whole class here and make you find the one line
that changed. Instead, we just show you that we can keep our print() method declaration
unchanged. Let’s replace line 13 with the following, which uses a lambda:

```java
13:         print(animals, a -> a.canHop());
```

&emsp;&emsp;
Don’t worry that the syntax looks a little funky. You’ll get used to it, and we describe it
in the next section. We also explain the bits that look like magic. For now, just focus on how
easy it is to read. We are telling Java that we only care if an Animal can hop. <br />

&emsp;&emsp;
It doesn’t take much imagination to figure out how we would add logic to get the
Animals that can swim. We only have to add one line of code—no need for an extra class to
do something simple. Here’s that other line:

```java
13:         print(animals, a -> a.canSwim());
```

&emsp;&emsp;
How about Animals that cannot swim?
```java
13:         print(animals, a -> !a.canSwim());
```

&emsp;&emsp;
The point is that it is really easy to write code that uses lambdas once you get the basics
in place. This code uses a concept called deferred execution. Deferred execution means that
code is specified now but will run later. In this case, “later” is inside the print() method
body, as opposed to when it is passed to the method.

## II. Learning Lambda Syntax
One of the simplest lambda expressions you can write is the one you just saw:

```java
a -> a.canHop()
```

&emsp;&emsp;
Lambdas work with interfaces that have exactly one abstract method. In this case, Java
looks at the CheckTrait interface, which has one method. The lambda in our example 
suggests that Java should call a method with an Animal parameter that returns a boolean value
that’s the result of a.canHop(). We know all this because we wrote the code. But how does
Java know? <br />

&emsp;&emsp;
Java relies on context when figuring out what lambda expressions mean. Context
refers to where and how the lambda is interpreted. For example, if we see someone in line
to enter the zoo and they have their wallet out, it is fair to assume they want to buy zoo
tickets. Alternatively, if they are in the concession line with their wallet out, they are probably hungry. <br />

&emsp;&emsp;
Referring to our earlier example, we passed the lambda as the second parameter of the
print method():

```java
print(animals, a -> a.canHop());
```

&emsp;&emsp;
The print() method expects a CheckTrait as the second parameter:

```java
private static void print(List<Animal> animals, CheckTrait checker) { ... }
```

&emsp;&emsp;
Since we are passing a lambda instead, Java tries to map our lambda to the abstract
method declaration in the CheckTrait interface:

```java
boolean test(Animal a);
```

&emsp;&emsp;
Since that interface’s method takes an Animal, the lambda parameter has to be an
Animal. And since that interface’s method returns a boolean, we know the lambda returns
a boolean. <br />

&emsp;&emsp;
The syntax of lambdas is tricky because many parts are optional. These two lines do the
exact same thing:

```java
a -> a.canHop()
        
(Animal a) -> { return a.canHop(); }
```

&emsp;&emsp;
Let’s look at what is going on here. The first example, shown in Figure 8.1, has three parts:
- A single parameter specified with the name a
- The arrow operator (->) to separate the parameter and body
- A body that calls a single method and returns the result of that method

> **Figure 8.1** Lambda syntax omitting optional parts

```java
a -> a.canHop()
```

&emsp;&emsp;
The second example shows the most verbose form of a lambda that returns a boolean (see Figure 8.2):
- A single parameter specified with the name a and stating that the type is Animal
- The arrow operator (->) to separate the parameter and body
- A body that has one or more lines of code, including a semicolon and a
return statement

> **Figure 8.2** Lambda syntax including optional parts

```java
(Animal a) -> { return a.canHop(); }
```

&emsp;&emsp;
The parentheses around the lambda parameters can be omitted only if there is a single
parameter and its type is not explicitly stated. Java does this because developers commonly
use lambda expressions this way and can do as little typing as possible. <br />

&emsp;&emsp;
It shouldn’t be news to you that we can omit braces when we have only a single statement.
We did this with if statements and loops already. Java allows you to omit a return 
statement and semicolon (;) when no braces are used. This special shortcut doesn’t work when you
have two or more statements. At least this is consistent with using {} to create blocks of code
elsewhere. <br />

&emsp;&emsp;
The syntax in Figure 8.1 and Figure 8.2 can be mixed and matched. For example, the following are valid:

```java
a -> { return a.canHop(); }
(Animal a) -> a.canHop()
```

> **Note**: <br />
> Here’s a fun fact: s -> {} is a valid lambda. If there is no code on the
right side of the expression, you don’t need the semicolon or return
statement.

Table 8.1 shows examples of valid lambdas that return a boolean.

> **Table 8.1** Valid lambdas that return a boolean

|Lambda|# of parameters|
|------|----------------|
|() -> true |0|
|x -> x.startsWith("test") |1|
|(String x) -> x.startsWith("test") |1|
|(x, y) -> { return x.startsWith("test"); } |2|
|(String x, String y) -> x.startsWith("test") |2|

&emsp;&emsp;
The first row takes zero parameters and always returns the boolean value true. The
second row takes one parameter and calls a method on it, returning the result. The third row
does the same, except that it explicitly defines the type of the variable. The final two rows
take two parameters and ignore one of them—there isn’t a rule that says you must use all
defined parameters. <br />

&emsp;&emsp;
Now let’s make sure you can identify invalid syntax for each row in Table 8.2, where
each lambda is supposed to return a boolean. Make sure you understand what’s wrong
with these.

> **Table 8.2** Invalid lambdas that should return a boolean
 
|Invalid lambda|Reason|
|--------------|------|
|x, y -> x.startsWith("fish")|Missing parentheses on left|
|x -> { x.startsWith("camel"); }|Missing return on right|
|x -> { return x.startsWith("giraffe") }|Missing semicolon inside braces|
|String x -> x.endsWith("eagle")|Missing parentheses on left|

&emsp;&emsp;
Remember that the parentheses are optional only when there is one parameter and it
doesn’t have a type declared. Those are the basics of writing a lambda. At the end of the
chapter, we cover additional rules about using variables in a lambda.

> **Assigning Lambdas to var** <br />
> Why do you think this line of code doesn’t compile?
> ```java
> var invalid = (Animal a) -> a.canHop(); // DOES NOT COMPILE
> ```
> Remember when we talked about Java inferring information about the lambda from the
context? Well, var assumes the type based on the context as well. There’s not enough 
context here! Neither the lambda nor var have enough information to determine what type of
functional interface should be used.
