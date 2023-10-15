# Overloading Methods

Now that you are familiar with the rules for declaring and using methods, it is time to look
at creating methods with the same name in the same class. Method overloading occurs when
methods in the same class have the same name but different method signatures, which means
they use different parameter lists. (Overloading differs from overriding, which you learn
about in Chapter 6.) <br />

&emsp;&emsp;
We’ve been showing how to call overloaded methods for a while. System.out.
println() and StringBuilder’s append() methods provide many overloaded versions,
so you can pass just about anything to them without having to think about it. In both of
these examples, the only change was the type of the parameter. Overloading also allows different numbers of parameters. <br />

&emsp;&emsp;
Everything other than the method name can vary for overloading methods. This means
there can be different access modifiers, optional specifiers (like static), return types, and
exception lists. <br />

&emsp;&emsp;
The following shows five overloaded versions of the fly() method:

```java
public class Falcon {
    public void fly(int numMiles) {}
    public void fly(short numFeet) {}
    public boolean fly() { return false; }
    void fly(int numMiles, short numFeet) {}
    public void fly(short numFeet, int numMiles) throws Exception {}
}
```

&emsp;&emsp;
As you can see, we can overload by changing anything in the parameter list. We can have
a different type, more types, or the same types in a different order. Also notice that the return
type, access modifier, and exception list are irrelevant to overloading. Only the method name
and parameter list matter. <br />

&emsp;&emsp;
Now let’s look at an example that is not valid overloading:

```java
public class Eagle {
    public void fly(int numMiles) {}
    public int fly(int numMiles) { return 1; } // DOES NOT COMPILE
}
```

&emsp;&emsp;
This method doesn’t compile because it differs from the original only by return type. The
method signatures are the same, so they are duplicate methods as far as Java is concerned. <br />

&emsp;&emsp;
What about these; why do they not compile?

```java
public class Hawk {
    public void fly(int numMiles) {}
    public static void fly(int numMiles) {} // DOES NOT COMPILE
    public void fly(int numKilometers) {}   // DOES NOT COMPILE
}
```

&emsp;&emsp;
Again, the method signatures of these three methods are the same. You cannot declare
methods in the same class where the only difference is that one is an instance method and
one is a static method. You also cannot have two methods that have parameter lists with
the same variable types and in the same order. As we mentioned earlier, the names of the
parameters in the list do not matter when determining the method signature. <br />

&emsp;&emsp;
Calling overloaded methods is easy. You just write code, and Java calls the right one.
For example, look at these two methods:

```java
public class Dove {
    public void fly(int numMiles) {
        System.out.println("int");
    }
    public void fly(short numFeet) {
        System.out.println("short");
    }
}
```

&emsp;&emsp;
The call fly((short) 1) prints short. It looks for matching types and calls the 
appropriate method. Of course, it can be more complicated than this. <br />

&emsp;&emsp;
Now that you know the basics of overloading, let’s look at some more complex scenarios
that you may encounter on the exam.

## I. Reference Types

Given the rule about Java picking the most specific version of a method that it can, what do
you think this code outputs?

```java
public class Pelican {
    public void fly(String s) {
        System.out.print("string");
    }
    public void fly(Object o) {
        System.out.print("object");
    }
    public static void main(String[] args) {
        var p = new Pelican();
        p.fly("test");
        System.out.print("-");
        p.fly(56);
    }
}
```

&emsp;&emsp;
The answer is string-object. The first call passes a String and finds a direct match.
There’s no reason to use the Object version when there is a nice String parameter list just
waiting to be called. The second call looks for an int parameter list. When it doesn’t find
one, it autoboxes to Integer. Since it still doesn’t find a match, it goes to the Object one. <br />

&emsp;&emsp;
Let’s try another. What does this print?

```java
import java.time.*;
import java.util.*;
public class Parrot {
    public static void print(List<Integer> i) {
        System.out.print("I");
    }
    public static void print(CharSequence c) {
        System.out.print("C");
    }
    public static void print(Object o) {
        System.out.print("O");
    }
    public static void main(String[] args){
        print("abc");
        print(Arrays.asList(3));
        print(LocalDate.of(2019, Month.JULY, 4));
    }
}
```

&emsp;&emsp;
The answer is CIO. The code is due for a promotion! The first call to print() passes
a String. As you learned in Chapter 4, String and StringBuilder implement the
CharSequence interface. You also learned that Arrays.asList() can be used to create a
List<Integer> object, which explains the second output. The final call to print() passes
a LocalDate. This is a class you might not know, but that’s okay. It clearly isn’t a sequence
of characters or a list. That means the Object method signature is used.

## II. Primitives

Primitives work in a way that’s similar to reference variables. Java tries to find the most
specific matching overloaded method. What do you think happens here?

```java
public class Ostrich {
    public void fly(int i) {
        System.out.print("int");
    }
    public void fly(long l) {
        System.out.print("long");
    }
    public static void main(String[] args) {
        var p = new Ostrich();
        p.fly(123);
        System.out.print("-");
        p.fly(123L);
    }
}
```

&emsp;&emsp;
The answer is int-long. The first call passes an int and sees an exact match. The 
second call passes a long and also sees an exact match. If we comment out the overloaded
method with the int parameter list, the output becomes long-long. Java has no problem
calling a larger primitive. However, it will not do so unless a better match is not found.

## III. Autoboxing

As we saw earlier, autoboxing applies to method calls, but what happens if you have both a
primitive and an integer version?

```java
public class Kiwi {
    public void fly(int numMiles) {}
    public void fly(Integer numMiles) {}
}
```

&emsp;&emsp;
These method overloads are valid. Java tries to use the most specific parameter list it can
find. This is true for autoboxing as well as other matching types we talk about in this section. <br />

&emsp;&emsp;
This means calling fly(3) will call the first method. When the primitive int version isn’t
present, Java will autobox. However, when the primitive int version is provided, there is no
reason for Java to do the extra work of autoboxing.

## IV. Arrays
Unlike the previous example, this code does not autobox:

```java
public static void walk(int[] ints) {}
public static void walk(Integer[] integers) {}
```

Arrays have been around since the beginning of Java. They specify their actual types.
What about generic types, such as List<Integer>? We cover this topic in Chapter 9.

## V. Varargs
Which method do you think is called if we pass an int[]?

```java
public class Toucan {
    public void fly(int[] lengths) {}
    public void fly(int... lengths) {} // DOES NOT COMPILE
}
```

&emsp;&emsp;
Trick question! Remember that Java treats varargs as if they were an array. This means
the method signature is the same for both methods. Since we are not allowed to overload
methods with the same parameter list, this code doesn’t compile. Even though the code
doesn’t look the same, it compiles to the same parameter list. <br />

&emsp;&emsp;
Now that we’ve just gotten through explaining that the two methods are similar, it is time
to mention how they are different. It shouldn’t be a surprise that you can call either method
by passing an array:

```java
fly(new int[] { 1, 2, 3 }); // Allowed to call either fly() method
```

&emsp;&emsp;
However, you can only call the varargs version with stand-alone parameters:

```java
fly(1, 2, 3); // Allowed to call only the fly() method using varargs
```

Obviously, this means they don’t compile exactly the same. The parameter list is the same,
though, and that is what you need to know with respect to overloading for the exam.

## VI. Putting It All Together
So far, all the rules for when an overloaded method is called should be logical. Java calls
the most specific method it can. When some of the types interact, the Java rules focus on
backward compatibility. A long time ago, autoboxing and varargs didn’t exist. Since old code
still needs to work, this means autoboxing and varargs come last when Java looks at 
overloaded methods. Ready for the official order? Table 5.6 lays it out for you.

> **Table 5.6** The order that Java uses to choose the right overloaded method

|Rule|Example of what will be chosen for glide(1,2)|
|----|--------------------------------------------|
|Exact match by type|String glide(int i, int j)|
|Larger primitive type|String glide(long i, long j)|
|Autoboxed type|String glide(Integer i, Integer j)|
|Varargs|String glide(int... nums)|

Let’s give this a practice run using the rules in Table 5.6. What do you think this outputs?

```java
public class Glider {
    public static String glide(String s) {
        return "1";
    }
    public static String glide(String... s) {
        return "2";
    }
    public static String glide(Object o) {
        return "3";
    }
    public static String glide(String s, String t) {
        return "4";
    }
    public static void main(String[] args) {
        System.out.print(glide("a"));
        System.out.print(glide("a", "b"));
        System.out.print(glide("a", "b", "c"));
    }
}
```

It prints out 142. The first call matches the signature taking a single String because that
is the most specific match. The second call matches the signature taking two String parameters since that is an exact match. It isn’t until the third call that the varargs version is used
since there are no better matches.
