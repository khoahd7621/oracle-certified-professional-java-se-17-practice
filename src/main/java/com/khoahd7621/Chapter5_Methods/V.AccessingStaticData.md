# Accessing *static* Data

When the static keyword is applied to a variable, method, or class, it belongs to the class
rather than a specific instance of the class. In this section, you see that the static keyword
can also be applied to import statements.

## I. Designing *static* Methods and Variables

Except for the main() method, we’ve been looking at instance methods. Methods and variables 
declared static don’t require an instance of the class. They are shared among all
users of the class. For instance, take a look at the following Penguin class:

```java
public class Penguin {
    String name;
    static String nameOfTallestPenguin;
}
```

&emsp;&emsp;
In this class, every Penguin instance has its own name like Willy or Lilly, but only
one Penguin among all the instances is the tallest. You can think of a static variable as
being a member of the single class object that exists independently of any instances of that
class. Consider the following example:

```java
public static void main(String[] unused) {
    var p1 = new Penguin();
    p1.name = "Lilly";
    p1.nameOfTallestPenguin = "Lilly";
    var p2 = new Penguin();
    p2.name = "Willy";
    p2.nameOfTallestPenguin = "Willy";
    System.out.println(p1.name);                 // Lilly
    System.out.println(p1.nameOfTallestPenguin); // Willy
    System.out.println(p2.name);                 // Willy
    System.out.println(p2.nameOfTallestPenguin); // Willy
}
```

&emsp;&emsp;
We see that each penguin instance is updated with its own unique name. The
nameOfTallestPenguin field is static and therefore shared, though, so anytime it is
updated, it impacts all instances of the class. <br />

&emsp;&emsp;
You have seen one static method since Chapter 1. The main() method is a static
method. That means you can call it using the class name:

```java
public class Koala {
    public static int count = 0;             // static variable
    public static void main(String[] args) { // static method
        System.out.print(count);
    }
}
```

&emsp;&emsp;
Here the JVM basically calls Koala.main() to get the program started. You can do this
too. We can have a KoalaTester that does nothing but call the main() method:

```java
public class KoalaTester {
    public static void main(String[] args) {
        Koala.main(new String[0]); // call static method
    }
}
```

&emsp;&emsp;
Quite a complicated way to print 0, isn’t it? When we run KoalaTester, it makes a call
to the main() method of Koala, which prints the value of count. The purpose of all these
examples is to show that main() can be called just like any other static method. <br />

&emsp;&emsp;
In addition to main() methods, static methods have two main purposes:

- For utility or helper methods that don’t require any object state. Since there is no need
  to access instance variables, having static methods eliminates the need for the caller to
  instantiate an object just to call the method.
- For state that is shared by all instances of a class, like a counter. All instances must share
  the same state. Methods that merely use that state should be static as well.

In the following sections, we look at some examples covering other static concepts.

## II. Accessing a *static* Variable or Method

Usually, accessing a static member is easy.

```java
public class Snake {
    public static long hiss = 2;
}
```

&emsp;&emsp;
You just put the class name before the method or variable, and you are done. Here’s
an example:

```java
System.out.println(Snake.hiss);
```

&emsp;&emsp;
Nice and easy. There is one rule that is trickier. You can use an instance of the object
to call a static method. The compiler checks for the type of the reference and uses that
instead of the object—which is sneaky of Java. This code is perfectly legal:

```java
5: Snake s = new Snake();
6: System.out.println(s.hiss); // s is a Snake
7: s = null;
8: System.out.println(s.hiss); // s is still a Snake
```

&emsp;&emsp;
Believe it or not, this code outputs 2 twice. Line 6 sees that s is a Snake and hiss
is a static variable, so it reads that static variable. Line 8 does the same thing. Java
doesn’t care that s happens to be null. Since we are looking for a static variable, it
doesn’t matter.

> **Note**: <br />
> Remember to look at the reference type for a variable when you see a
static method or variable. The exam creators will try to trick you into
thinking a NullPointerException is thrown because the variable happens to be null. Don’t be fooled!

&emsp;&emsp;
One more time, because this is really important: what does the following output?

```java
Snake.hiss = 4;
Snake snake1 = new Snake();
Snake snake2 = new Snake();
snake1.hiss = 6;
snake2.hiss = 5;
System.out.println(Snake.hiss);
```

&emsp;&emsp;
We hope you answered 5. There is only one hiss variable since it is static. It is set to 4
and then 6 and finally winds up as 5. All the Snake variables are just distractions.

## III. Class vs. Instance Membership

There’s another way the exam creators will try to trick you regarding static and instance
members. A static member cannot call an instance member without referencing an instance
of the class. This shouldn’t be a surprise since static doesn’t require any instances of the class
to even exist. <br />

&emsp;&emsp;
The following is a common mistake for rookie programmers to make:

```java
public class MantaRay {
    private String name = "Sammy";
    public static void first() { }
    public static void second() { }
    public void third() { System.out.print(name); }
    public static void main(String args[]) {
        first();
        second();
        third(); // DOES NOT COMPILE
    }
}
```

&emsp;&emsp;
The compiler will give you an error about making a static reference to an instance
method. If we fix this by adding static to third(), we create a new problem. Can you
figure out what it is?

```java
public static void third() { System.out.print(name); } // DOES NOT COMPILE
```

&emsp;&emsp;
All this does is move the problem. Now, third() is referring to an instance variable
name. There are two ways we could fix this. The first is to add static to the name variable as well.

```java
public class MantaRay {
    private static String name = "Sammy";
    ...
    public static void third() { System.out.print(name); }
    ...
}
```

&emsp;&emsp;
The second solution would have been to call third() as an instance method and not use
static for the method or the variable.

```java
public class MantaRay {
    private String name = "Sammy";
    ...
    public void third() { System.out.print(name); }
    public static void main(String args[]) {
        ...
        var ray = new MantaRay();
        ray.third();
    }
}
```

&emsp;&emsp;
The exam creators like this topic—a lot. A static method or instance method can call a
static method because static methods don’t require an object to use. Only an instance
method can call another instance method on the same class without using a reference 
variable, because instance methods do require an object. Similar logic applies for instance and
static variables. <br />

&emsp;&emsp;
Suppose we have a Giraffe class:

```java
public class Giraffe {
    public void eat(Giraffe g) {}
    public void drink() {}
    public static void allGiraffeGoHome(Giraffe g) {}
    public static void allGiraffeComeOut() {}
}
```

&emsp;&emsp;
Make sure you understand Table 5.5 before continuing.

> **Table 5.5** Static vs. instance calls

|Method|Calling|Legal?|
|------|-------|------|
|allGiraffeGoHome()|allGiraffeComeOut()|Yes|
|allGiraffeGoHome()|drink()|No|
|allGiraffeGoHome()|g.eat()|Yes|
|eat()|allGiraffeComeOut()|Yes|
|eat()|drink()|Yes|
|eat()|g.eat()|Yes|

&emsp;&emsp;
Let’s try one more example so you have more practice at recognizing this scenario. Do
you understand why the following lines fail to compile?

```java
1: public class Gorilla {
2:      public static int count;
3:      public static void addGorilla() { count++; }
4:      public void babyGorilla() { count++; }
5:      public void announceBabies() {
6:          addGorilla();
7:          babyGorilla();
8:      }
9:      public static void announceBabiesToEveryone() {
10:         addGorilla();
11:         babyGorilla(); // DOES NOT COMPILE
12:     }
13:     public int total;
14:     public static double average 
15:          = total / count; // DOES NOT COMPILE
16: }
```

&emsp;&emsp;
Lines 3 and 4 are fine because both static and instance methods can refer to a static
variable. Lines 5–8 are fine because an instance method can call a static method. Line 11
doesn’t compile because a static method cannot call an instance method. Similarly, line 15
doesn’t compile because a static variable is trying to use an instance variable. <br />

&emsp;&emsp;
A common use for static variables is counting the number of instances:

```java
public class Counter {
    private static int count;
    public Counter() { count++; }
    public static void main(String[] args) {
        Counter c1 = new Counter();
        Counter c2 = new Counter();
        Counter c3 = new Counter();
        System.out.println(count); // 3
    }
}
```

&emsp;&emsp;
Each time the constructor is called, it increments count by one. This example relies on
the fact that static (and instance) variables are automatically initialized to the default
value for that type, which is 0 for int. See Chapter 1 to review the default values. <br />

&emsp;&emsp;
Also notice that we didn’t write Counter.count. We could have. It isn’t necessary
because we are already in that class, so the compiler can infer it.

> **Tips**: <br />
> Make sure you understand this section really well. It comes up
throughout this book. You even see a similar topic when we discuss interfaces 
in Chapter 7. For example, a static interface method cannot call a
default interface method without a reference, much the same way that
within a class, a static method cannot call an instance method without
a reference.




