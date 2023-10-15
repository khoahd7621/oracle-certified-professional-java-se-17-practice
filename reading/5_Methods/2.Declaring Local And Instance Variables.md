# Declaring Local and Instance Variables

Now that we have methods, we need to talk a little bit about the variables that they can
create or use. As you might recall from Chapter 1, local variables are those defined with a
method or block, while instance variables are those that are defined as a member of a class.
Let’s take a look at an example:

```java
public class Lion {
    int hunger = 4;
 
    public int feedZooAnimals() {
        int snack = 10; // Local variable
        if (snack > 4) {
            long dinnerTime = snack++;
            hunger--;
        }
        return snack;
    }
}
```

&emsp;&emsp;
In the Lion class, snack and dinnerTime are local variables only accessible within their
respective code blocks, while hunger is an instance variable and created in every object of
the Lion class. <br />

&emsp;&emsp;
The object or value returned by a method may be available outside the method, but the
variable reference snack is gone. Keep this in mind while reading this chapter: all local 
variable references are destroyed after the block is executed, but the objects they point to may
still be accessible.

## 1. Local Variable Modifiers

There’s only one modifier that can be applied to a local variable: final. Easy to remember,
right? When writing methods, developers may want to set a variable that does not change
during the course of the method. In this code sample, trying to change the value or object
these variables reference results in a compiler error:

```java
public void zooAnimalCheckup(boolean isWeekend) {
    final int rest;
    if (isWeekend) rest = 5; else rest = 20;
    System.out.print(rest);
 
    final var giraffe = new Animal();
    final int[] friends = new int[5];
 
    rest = 10;              // DOES NOT COMPILE
    giraffe = new Animal(); // DOES NOT COMPILE
    friends = null;         // DOES NOT COMPILE
}
```

&emsp;&emsp;
As shown with the rest variable, we don’t need to assign a value when a final variable
is declared. The rule is only that it must be assigned a value before it can be used. We can
even use var and final together. Contrast this with the following example:

```java
public void zooAnimalCheckup(boolean isWeekend) {
    final int rest;
    if (isWeekend) rest = 5;
    System.out.print(rest); // DOES NOT COMPILE
}
```

&emsp;&emsp;
The rest variable might not have been assigned a value, such as if isWeekend is false.
Since the compiler does not allow the use of local variables that may not have been assigned
a value, the code does not compile. <br />

&emsp;&emsp;
Does using the final modifier mean we can’t modify the data? Nope. The final attribute
only refers to the variable reference; the contents can be freely modified (assuming the object
isn’t immutable).

```java
public void zooAnimalCheckup() {
    final int rest = 5;
    final Animal giraffe = new Animal();
    final int[] friends = new int[5];
 
    giraffe.setName("George");
    friends[2] = 2;
}
```

&emsp;&emsp;
The rest variable is a primitive, so it’s just a value that can’t be modified. On the other
hand, the contents of the giraffe and friends variables can be freely modified, provided
the variables aren’t reassigned.

> **Tip** <br />
> While it might not seem obvious, marking a local variable final is
often a good practice. For example, you may have a complex method
in which a variable is referenced dozens of times. It would be really bad
if someone came in and reassigned the variable in the middle of the
method. Using the final attribute is like sending a message to other
developers to leave the variable alone!
 
## 2. Effectively Final Variables
An effectively final local variable is one that is not modified after it is assigned. This means
that the value of a variable doesn’t change after it is set, regardless of whether it is explicitly
marked as final. If you aren’t sure whether a local variable is effectively final, just add the
final keyword. If the code still compiles, the variable is effectively final. <br />

&emsp;&emsp;
Given this definition, which of the following variables are effectively final?

```java
11: public String zooFriends() {
12:     String name = "Harry the Hippo";
13:     var size = 10;
14:     boolean wet;
15:     if (size > 100) size++;
16:     name.substring(0);
17:     wet = true;
18:     return name;
19: }
```

&emsp;&emsp;
Remember, a quick test of effectively final is to just add final to the variable declaration 
and see if it still compiles. In this example, name and wet are effectively final and can
be updated with the final modifier, but not size. The name variable is assigned a value
on line 12 and not reassigned. Line 16 creates a value that is never used. Remember from
Chapter 4, “Core APIs,” that strings are immutable. The size variable is not effectively final
because it could be incremented on line 15. The wet variable is assigned a value only once
and not modified afterward.

> **Effective Final Parameters** <br />
> Recall from Chapter 1 that *method and constructor parameters are local variables that have
been pre-initialized*. In the context of local variables, the same rules around final and
effectively final apply. This is especially important in Chapter 7 and Chapter 8, “Lambdas
and Functional Interfaces,” since local classes and lambda expressions declared within a
method can only reference local variables that are final or effectively final.

## 3. Instance Variable Modifiers
Like methods, instance variables can use access modifiers, such as private, package, protected,
and public. Remember, package access is indicated by the lack of any modifiers. We cover
each of the different access modifiers shortly in this chapter. Instance variables can also use
optional specifiers, described in Table 5.3.

- **Table 5.3** Optional Instance Variable Specifiers <br />

|Modifier|Description|Chapter Covered|
|--------|-----------|--------------|
|final|Specifies that the instance variable must be initialized with each instance of the class exactly once|Chapter 5|
|volatile|Instructs the JVM that the value in this variable may be modified by other threads|Chapter 13|
|transient|Used to indicate that an instance variable should not be serialized with the class|Chapter 14|

&emsp;&emsp;
Looks like we only need to discuss final in this chapter! If an instance variable is
marked final, then it must be assigned a value when it is declared or when the object is
instantiated. Like a local final variable, it cannot be assigned a value more than once,
though. The following PolarBear class demonstrates these properties:

```java
public class PolarBear {
    final int age = 10;
    final int fishEaten;
    final String name;
    
    { fishEaten = 10; }
    
    public PolarBear() {
        name = "Robert";
    }
}
```

&emsp;&emsp;
The age variable is given a value when it is declared, while the fishEaten variable
is assigned a value in an instance initializer. The name variable is given a value in the 
no-argument constructor. Failing to initialize an instance variable (or assigning a value more
than once) will lead to a compiler error. We talk about final variable initialization in more
detail when we cover constructors in the next chapter.

> **Note**: <br />
> In Chapter 1, we show that instance variables receive default values
based on their type when not set. For example, int receives a default
value of 0, while an object reference receives a default value of null. The
compiler does not apply a default value to final variables, though. A
final instance or final static variable must receive a value when it is
declared or as part of initialization.
