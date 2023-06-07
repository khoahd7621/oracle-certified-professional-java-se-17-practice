# Initializing Objects

In Chapter 1, we covered order of initialization, albeit in a very simplistic manner. Order of
initialization refers to how members of a class are assigned values. They can be given default
values, like 0 for an int, or require explicit values, such as for final variables. In this section,
we go into much more detail about how order of initialization works and how to spot errors
on the exam.

## I. Initializing Classes

We begin our discussion of order of initialization with class initialization. First, we initialize
the class, which involves invoking all static members in the class hierarchy, starting with the
highest superclass and working downward. This is sometimes referred to as loading the class.
The Java Virtual Machine (JVM) controls when the class is initialized, although you can
assume the class is loaded before it is used. The class may be initialized when the program
first starts, when a static member of the class is referenced, or shortly before an instance of
the class is created. <br/>

&emsp;&emsp;
One of the most important rules with class initialization is that it happens at most once
for each class. The class may also never be loaded if it is not used in the program.
We summarize the order of initialization for a class as follows: <br />

&emsp;&emsp;
**Initialize Class X**
1. If there is a superclass Y of X, then initialize class Y first.
2. Process all static variable declarations in the order in which they appear in the class.
3. Process all static initializers in the order in which they appear in the class.

&emsp;&emsp;
Taking a look at an example, what does the following program print?

```java
public class Animal {
    static { System.out.print("A"); }
}

public class Hippo extends Animal {
    public static void main(String[] grass) {
        System.out.print("C");
        new Hippo();
        new Hippo();
        new Hippo();
    }
    static { System.out.print("B"); }
}
```

&emsp;&emsp;
It prints ABC exactly once. Since the main() method is inside the Hippo class, the class
will be initialized first, starting with the superclass and printing AB. Afterward, the main()
method is executed, printing C. Even though the main() method creates three instances, the
class is loaded only once.

> **Why the Hippo Program Printed C After AB**:
> In the previous example, the Hippo class was initialized before the main() method was
executed. This happened because our main() method was inside the class being executed,
so it had to be loaded on startup. What if you instead called Hippo inside another program?
> ```java
>  public class HippoFriend {
>       public static void main(String[] grass) {
>           System.out.print("C");
>           new Hippo();
>       }
>  }
> ```
> Assuming the class isn’t referenced anywhere else, this program will likely print CAB, with
the Hippo class not being loaded until it is needed inside the main() method. We say
likely because the rules for when classes are loaded are determined by the JVM at runtime.
For the exam, you just need to know that a class must be initialized before it is referenced
or used. Also, the class containing the program entry point, aka the main() method, is
loaded before the main() method is executed.

## II. Initializing final Fields

Before we delve into order of initialization for instance members, we need to talk about final
fields (instance variables) for a minute. When we presented instance and class variables in
Chapter 1, we told you they are assigned a default value based on their type if no value is
specified. For example, a double is initialized with 0.0, while an object reference is initialized
to null. A default value is only applied to a non-final field, though. <br />

&emsp;&emsp;
As you saw in Chapter 5, final static variables must be explicitly assigned a value
exactly once. Fields marked final follow similar rules. They can be assigned values in the
line in which they are declared or in an instance initializer.

```java
public class MouseHouse {
    private final int volume;
    private final String name = "The Mouse House"; // Declaration assignment
    {
        volume = 10; // Instance initializer assignment
    }
}
```

&emsp;&emsp;
Unlike static class members, though, final instance fields can also be set in a constructor. 
The constructor is part of the initialization process, so it is allowed to assign final
instance variables. For the exam, you need to know one important rule: by the time the 
constructor completes, all final instance variables must be assigned a value exactly once. <br />

&emsp;&emsp;
Let’s try this out in an example:

```java
public class MouseHouse {
    private final int volume;
    private final String name;
    public MouseHouse() {
        this.name = "Empty House"; // Constructor assignment
    }
    {
        volume = 10; // Instance initializer assignment
    }
}
```

&emsp;&emsp;
Unlike local final variables, which are not required to have a value unless they are 
actually used, final instance variables must be assigned a value. If they are not assigned a value
when they are declared or in an instance initializer, then they must be assigned a value in
the constructor declaration. Failure to do so will result in a compiler error on the line that
declares the constructor.

```java
public class MouseHouse {
    private final int volume;
    private final String type;
    {
        this.volume = 10;
    }
    public MouseHouse(String type) {
        this.type = type;
    }
    public MouseHouse() { // DOES NOT COMPILE
        this.volume = 2; // DOES NOT COMPILE
    }
}
```

&emsp;&emsp;
In this example, the first constructor that takes a String argument compiles. In terms
of assigning values, each constructor is reviewed individually, which is why the second 
constructor does not compile. First, the constructor fails to set a value for the type variable.
The compiler detects that a value is never set for type and reports an error on the line where
the constructor is declared. Second, the constructor sets a value for the volume variable,
even though it was already assigned a value by the instance initializer.

> **Note**: <br />
> On the exam, be wary of any instance variables marked final. Make
sure they are assigned a value in the line where they are declared, in an
instance initializer, or in a constructor. They should be assigned a value
only once, and failure to assign a value is considered a compiler error in
the constructor.

What about final instance variables when a constructor calls another constructor in the
same class? In that case, you have to follow the flow carefully, making sure every final 
instance variable is assigned a value exactly once. We can replace our previous bad constructor
with the following one that does compile:

```java
public MouseHouse() {
    this(null);
}
```

&emsp;&emsp;
This constructor does not perform any assignments to any final instance variables, but
it calls the MouseHouse(String) constructor, which we observed compiles without issue.
We use null here to demonstrate that the variable does not need to be an object value. We
can assign a null value to final instance variables as long as they are explicitly set.

## III. Initializing Instances

We’ve covered class initialization and final fields, so now it’s time to move on to order of
initialization for objects. We’ll warn you that this can be a bit cumbersome at first, but the
exam isn’t likely to ask questions more complicated than the examples in this section. We
promise to take it slowly, though. <br />

&emsp;&emsp;
First, start at the lowest-level constructor where the new keyword is used. Remember, the
first line of every constructor is a call to this() or super(), and if omitted, the compiler will
automatically insert a call to the parent no-argument constructor super(). Then, progress
upward and note the order of constructors. Finally, initialize each class starting with the
superclass, processing each instance initializer and constructor in the reverse order in which
it was called. We summarize the order of initialization for an instance as follows: <br/>

**Initialize Instance of X**
1. Initialize class X if it has not been previously initialized.
2. If there is a superclass Y of X, then initialize the instance of Y first.
3. Process all instance variable declarations in the order in which they appear in the class.
4. Process all instance initializers in the order in which they appear in the class.
5. Initialize the constructor, including any overloaded constructors referenced with this().

&emsp;&emsp;
Let’s try an example with no inheritance. See if you can figure out what the following
application outputs:

```java
1:  public class ZooTickets {
2:      private String name = "BestZoo";
3:      { System.out.print(name + "-"); }
4:      private static int COUNT = 0;
5:      static { System.out.print(COUNT + "-"); }
6:      static { COUNT += 10; System.out.print(COUNT + "-"); }
7:
8:      public ZooTickets() {
9:          System.out.print("z-");
10:     }
11:
12:     public static void main(String... patrons) {
13:         new ZooTickets();
14:     } 
15: }
```

&emsp;&emsp;
The output is as follows:

```java
0-10-BestZoo-z-
```

&emsp;&emsp;
First, we have to initialize the class. Since there is no superclass declared, which means
the superclass is Object, we can start with the static components of ZooTickets. In
this case, lines 4, 5, and 6 are executed, printing 0- and 10-. Next, we initialize the instance
created on line 13. Again, since no superclass is declared, we start with the instance 
components. Lines 2 and 3 are executed, which prints BestZoo-. Finally, we run the constructor
on lines 8–10, which outputs z-. <br/>

&emsp;&emsp;
Next, let’s try a simple example with inheritance:

```java
class Primate {
    public Primate() {
        System.out.print("Primate-");
    } 
}

class Ape extends Primate {
    public Ape(int fur) {
        System.out.print("Ape1-");
    }
    public Ape() {
        System.out.print("Ape2-");
    } 
}

public class Chimpanzee extends Ape {
    public Chimpanzee() {
        super(2);
        System.out.print("Chimpanzee-");
    }
    public static void main(String[] args) {
        new Chimpanzee();
    } 
}
```

&emsp;&emsp;
The compiler inserts the super() command as the first statement of both the Primate
and Ape constructors. The code will execute with the parent constructors called first and
yield the following output:

```java
Primate-Ape1-Chimpanzee-
```

&emsp;&emsp;
Notice that only one of the two Ape() constructors is called. You need to start with the
call to new Chimpanzee() to determine which constructors will be executed. Remember,
constructors are executed from the bottom up, but since the first line of every constructor is
a call to another constructor, the flow ends up with the parent constructor executed before
the child constructor. <br/>

&emsp;&emsp;
The next example is a little harder. What do you think happens here?

```java
1:  public class Cuttlefish {
2:      private String name = "swimmy";
3:      { System.out.println(name); }
4:      private static int COUNT = 0;
5:      static { System.out.println(COUNT); }
6:      { COUNT++; System.out.println(COUNT); }
7:
8:      public Cuttlefish() {
9:          System.out.println("Constructor");
10:     }
11:
12:     public static void main(String[] args) {
13:         System.out.println("Ready");
14:         new Cuttlefish();
15:     } 
16: }
```

&emsp;&emsp;
The output looks like this:

```java
0
Ready
swimmy
1
Constructor
```

&emsp;&emsp;
No superclass is declared, so we can skip any steps that relate to inheritance. We first
process the static variables and static initializers—lines 4 and 5, with line 5 printing 0. 
Now that the static initializers are out of the way, the main() method can run, which
prints Ready. Next we create an instance declared on line 14. Lines 2, 3, and 6 are 
processed, with line 3 printing swimmy and line 6 printing 1. Finally, the constructor is run on
lines 8–10, which prints Constructor. <br />

&emsp;&emsp;
Ready for a more difficult example, the kind you might see on the exam? What does the
following output?

```java
1:  class GiraffeFamily {
2:      static { System.out.print("A"); }
3:      { System.out.print("B"); }
4:
5:      public GiraffeFamily(String name) {
6:          this(1);
7:          System.out.print("C");
8:      }
9:
10:     public GiraffeFamily() {
11:         System.out.print("D");
12:     }
13:
14:     public GiraffeFamily(int stripes) {
15:         System.out.print("E");
16:     }
17: }
18: public class Okapi extends GiraffeFamily {
19:     static { System.out.print("F"); }
20:
21:     public Okapi(int stripes) {
22:         super("sugar");
23:         System.out.print("G");
24:     }
25:     { System.out.print("H"); }
26:
27:     public static void main(String[] grass) {
28:         new Okapi(1);
29:         System.out.println();
30:         new Okapi(2);
31:     }
32: }
```

&emsp;&emsp;
The program prints the following:

```java
AFBECHG
BECHG
```

&emsp;&emsp;
This example is tricky for a few reasons. There are multiple overloaded constructors, lots
of initializers, and a complex constructor pathway to keep track of. Luckily, questions like
this are uncommon on the exam. If you see one, just write down what is going on as you
read the code. <br/>

&emsp;&emsp;
We conclude this section by listing important rules you should know for the exam:

- A class is initialized at most once by the JVM before it is referenced or used.
- All static final variables must be assigned a value exactly once, either when they
are declared or in a static initializer.
- All final fields must be assigned a value exactly once, either when they are declared, in an
instance initializer, or in a constructor.
- Non-final static and instance variables defined without a value are assigned a
default value based on their type.
- Order of initialization is as follows: variable declarations, then initializers, and finally
constructors.
