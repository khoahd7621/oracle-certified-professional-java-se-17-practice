# Using Method References

Method references are another way to make the code easier to read, such as simply 
mentioning the name of the method. Like lambdas, it takes time to get used to the new syntax. In
this section, we show the syntax along with the four types of method references. We also mix
in lambdas with method references. <br />

&emsp;&emsp;
Suppose we are coding a duckling that is trying to learn how to quack. First we have a
functional interface:

```java
public interface LearnToSpeak {
     void speak(String sound);
}
```

&emsp;&emsp;
Next, we discover that our duckling is lucky. There is a helper class that the duckling can
work with. We’ve omitted the details of teaching the duckling how to quack and left the part
that calls the functional interface:

```java
public class DuckHelper {
    public static void teacher(String name, LearnToSpeak trainer) {
        // Exercise patience (omitted)
        trainer.speak(name);
    }
}
```

&emsp;&emsp;
Finally, it is time to put it all together and meet our little Duckling. This code 
implements the functional interface using a lambda:

```java
public class Duckling {
    public static void makeSound(String sound) {
        LearnToSpeak learner = s -> System.out.println(s);
        DuckHelper.teacher(sound, learner);
    }
}
```

&emsp;&emsp;
Not bad. There’s a bit of redundancy, though. The lambda declares one parameter named
s. However, it does nothing other than pass that parameter to another method. A method
reference lets us remove that redundancy and instead write this:

```java
LearnToSpeak learner = System.out::println;
```

&emsp;&emsp;
The :: operator tells Java to call the println() method later. It will take a little while to
get used to the syntax. Once you do, you may find your code is shorter and less distracting
without writing as many lambdas.

> **Note**: <br />
> Remember that :: is like a lambda, and it is used for deferred execution
with a functional interface. You can even imagine the method reference
as a lambda if it helps you.

&emsp;&emsp;
A method reference and a lambda behave the same way at runtime. You can pretend the
compiler turns your method references into lambdas for you. <br />

&emsp;&emsp;
There are four formats for method references:

- static methods
- Instance methods on a particular object
- Instance methods on a parameter to be determined at runtime
- Constructors

&emsp;&emsp;
Let’s take a brief look at each of these in turn. In each example, we show the method reference and its lambda equivalent. For now, we create a separate functional interface for each
example. In the next section, we introduce built-in functional interfaces so you don’t have to
keep writing your own.

## I. Calling static Methods
For the first example, we use a functional interface that converts a double to a long:

```java
interface Converter { 
    long round(double num);
}
```

&emsp;&emsp;
We can implement this interface with the round() method in Math. Here we assign a
method reference and a lambda to this functional interface:

```java
14: Converter methodRef = Math::round;
15: Converter lambda = x -> Math.round(x);
16:
17: System.out.println(methodRef.round(100.1)); // 100
```

&emsp;&emsp;
On line 14, we reference a method with one parameter, and Java knows that it’s like a
lambda with one parameter. Additionally, Java knows to pass that parameter to the method. <br />

&emsp;&emsp;
Wait a minute. You might be aware that the round() method is overloaded—it can take a
double or a float. How does Java know that we want to call the version with a double? With
both lambdas and method references, Java infers information from the context. In this case,
we said that we were declaring a Converter, which has a method taking a double parameter.
Java looks for a method that matches that description. If it can’t find it or finds multiple
matches, then the compiler will report an error. The latter is sometimes called an ambiguous
type error.

## II. Calling Instance Methods on a Particular Object

&emsp;&emsp;
For this example, our functional interface checks if a String starts with a specified value:

```java
interface StringStart {
    boolean beginningCheck(String prefix);
}
```

&emsp;&emsp;
Conveniently, the String class has a startsWith() method that takes one parameter
and returns a boolean. Let’s look at how to use method references with this code:

```java
18: var str = "Zoo";
19: StringStart methodRef = str::startsWith;
20: StringStart lambda = s -> str.startsWith(s);
21:
22: System.out.println(methodRef.beginningCheck("A")); // false
```

&emsp;&emsp;
Line 19 shows that we want to call str.startsWith() and pass a single parameter to
be supplied at runtime. This would be a nice way of filtering the data in a list. <br />

&emsp;&emsp;
A method reference doesn’t have to take any parameters. In this example, we create a
functional interface with a method that doesn’t take any parameters but returns a value:

```java
interface StringChecker {
    boolean check();
}
```

&emsp;&emsp;
We implement it by checking if the String is empty:

```java
18: var str = "";
19: StringChecker methodRef = str::isEmpty;
20: StringChecker lambda = () -> str.isEmpty();
21:
22: System.out.print(methodRef.check()); // true
```

&emsp;&emsp;
Since the method on String is an instance method, we call the method reference on an
instance of the String class. <br />

&emsp;&emsp;
While all method references can be turned into lambdas, the opposite is not always true.
For example, consider this code:

```java
var str = "";
StringChecker lambda = () -> str.startsWith("Zoo");
```

&emsp;&emsp;
How might we write this as a method reference? You might try one of the following:

```java
StringChecker methodReference = str::startsWith; // DOES NOT COMPILE
        
StringChecker methodReference = str::startsWith("Zoo"); // DOES NOT COMPILE
```

Neither of these works! While we can pass the str as part of the method reference,
there’s no way to pass the "Zoo" parameter with it. Therefore, it is not possible to write this
lambda as a method reference.

## III. Calling Instance Methods on a Parameter
This time, we are going to call the same instance method that doesn’t take any parameters.
The trick is that we will do so without knowing the instance in advance. We need a different
functional interface this time since it needs to know about the String

```java
interface StringParameterChecker {
    boolean check(String text);
}
```

&emsp;&emsp;
We can implement this functional interface as follows:

```java
23: StringParameterChecker methodRef = String::isEmpty;
24: StringParameterChecker lambda = s -> s.isEmpty();
25:
26: System.out.println(methodRef.check("Zoo")); // false
```

&emsp;&emsp;
Line 23 says the method that we want to call is declared in String. It looks like a
static method, but it isn’t. Instead, Java knows that isEmpty() is an instance method that
does not take any parameters. Java uses the parameter supplied at runtime as the instance on
which the method is called. <br />

&emsp;&emsp;
Compare lines 23 and 24 with lines 19 and 20 of our instance example. They look 
similar, although one references a local variable named str, while the other only references the
functional interface parameters. <br />

&emsp;&emsp;
You can even combine the two types of instance method references. Again, we need a new
functional interface that takes two parameters:

```java
interface StringTwoParameterChecker {
    boolean check(String text, String prefix);
}
```

&emsp;&emsp;
Pay attention to the parameter order when reading the implementation:

```java
26: StringTwoParameterChecker methodRef = String::startsWith;
27: StringTwoParameterChecker lambda = (s, p) -> s.startsWith(p);
28:
29: System.out.println(methodRef.check("Zoo", "A")); // false
```

&emsp;&emsp;
Since the functional interface takes two parameters, Java has to figure out what they
represent. The first one will always be the instance of the object for instance methods. Any
others are to be method parameters. <br />

&emsp;&emsp;
Remember that line 26 may look like a static method, but it is really a method 
reference declaring that the instance of the object will be specified later. Line 27 shows some
of the power of a method reference. We were able to replace two lambda parameters
this time.

## IV. Calling Constructors
A constructor reference is a special type of method reference that uses new instead of a
method and instantiates an object. For this example, our functional interface will not take
any parameters but will return a String:

```java
interface EmptyStringCreator {
    String create();
}
```

&emsp;&emsp;
To call this, we use new as if it were a method name:

```java
30: EmptyStringCreator methodRef = String::new;
31: EmptyStringCreator lambda = () -> new String();
32:
33: var myString = methodRef.create();
34: System.out.println(myString.equals("Snake")); // false
```

&emsp;&emsp;
It expands like the method references you have seen so far. In the previous example, the
lambda doesn’t have any parameters. <br />

&emsp;&emsp;
Method references can be tricky. This time we create a functional interface that takes one
parameter and returns a result:

```java
interface StringCopier {
    String copy(String value);
}
```

&emsp;&emsp;
In the implementation, notice that line 32 in the following example has the same method
reference as line 30 in the previous example:

```java
32: StringCopier methodRef = String::new;
33: StringCopier lambda = x -> new String(x);
34:
35: var myString = methodRef.copy("Zebra");
36: System.out.println(myString.equals("Zebra")); // true
```

&emsp;&emsp;
This means you can’t always determine which method can be called by looking at the
method reference. Instead, you have to look at the context to see what parameters are used
and if there is a return type. In this example, Java sees that we are passing a String 
parameter and calls the constructor of String that takes such a parameter.

## V. Reviewing Method References
Reading method references is helpful in understanding the code. Table 8.3 shows the four
types of method references. If this table doesn’t make sense, please reread the previous 
section. It can take a few tries before method references start to add up.

> **Table 8.3**: Method references

|Type|Before colon|After colon|Example|
|----|------------|-----------|-------|
|static methods|Class name|Method name|Math::random|
|Instance methods on a particular object |Instance variable name |Method name |str::startsWith|
|Instance methods on a parameter |Class name |Method name |String::isEmpty|
|Constructor |Class name |new |String::new|
