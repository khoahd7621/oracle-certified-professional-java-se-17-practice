# Designing Methods

Every interesting Java program we’ve seen has had a main() method. You can write
other methods too. For example, you can write a basic method to take a nap, as shown below:

```java
public final void nap(int minutes) throws InterruptedException {
    // take a nap
    System.out.println("Taking a nap now.");
}
```

&emsp;&emsp;
This is called a method declaration, which specifies all the information needed to call the
method. There are a lot of parts, and we cover each one in more detail. Two of the parts —
the method name and parameter list — are called the method signature. The method signature
provides instructions for how callers can reference this method. The method signature does
not include the return type and access modifiers, which control where the method can be
referenced. <br />

&emsp;&emsp;
Table below is a brief reference to the elements of a method declaration. Don’t worry if it
seems like a lot of information—by the time you finish this chapter, it will all fit together.

| Element            | Value in nap() example      | Required?                         |
|--------------------|-----------------------------|-----------------------------------|
| Access modifier    | public                      | No                                |
| Optional specifier | final                       | No                                |
| Return type        | void                        | Yes                               |
| Method name        | nap                         | Yes                               |
| Parameter list     | (int minutes)               | Yes, but can be empty parentheses |
| Method signature   | nap (int minutes)           | Yes                               |
| Exception list     | throws InterruptedException | No                                |
| Method body        | { // take a nap }           | Yes, except for abstract methods  |

&emsp;&emsp;
To call this method, just use the method signature and provide an int value in parentheses:

```java
nap(10);
```

## 1. Access Modifiers
An access modifier determines what classes a method can be accessed from. Think of it like
a security guard. Some classes are good friends, some are distant relatives, and some are
complete strangers. Access modifiers help to enforce when these components are allowed to
talk to each other. Java offers four choices of access modifier:

- **private**: The private modifier means the method can be called only from within the same class.
- **Package Access**: With package access, the method can be called only from a class in
  the same package. This one is tricky because there is no keyword. You simply omit the
  access modifier. Package access is sometimes referred to as package-private or default
  access (even within this book!).
- **protected**: The protected modifier means the method can be called only from a class
  in the same package or a subclass.
- **public**: The public modifier means the method can be called from anywhere.

> **Note**: <br />
> For simplicity, we’re primarily concerned with access modifiers applied
to methods and fields in this chapter. Rules for access modifiers are
also applicable to classes and other types you learn about in Chapter 7,
“Beyond Classes," such as interfaces, enums, and records.

&emsp;&emsp;
We explore the impact of the various access modifiers later in this chapter. For now, just
master identifying valid syntax of methods. The exam creators like to trick you by putting
method elements in the wrong order or using incorrect values. <br />

&emsp;&emsp;
We’ll see practice examples as we go through each of the method elements in this chapter.
Make sure you understand why each of these is a valid or invalid method declaration. Pay
attention to the access modifiers as you figure out what is wrong with the ones that don’t
compile when inserted into a class:

```java
public class ParkTrip {
    public void skip1() {}
    default void skip2() {} // DOES NOT COMPILE
    void public skip3() {} // DOES NOT COMPILE
    void skip4() {}
}
```

## 2. Optional Specifiers
There are a number of optional specifiers for methods, shown in Table 5.2. Unlike with
access modifiers, you can have multiple specifiers in the same method (although not all 
combinations are legal). When this happens, you can specify them in any order. And since these
specifiers are optional, you are allowed to not have any of them at all. This means you can
have zero or more specifiers in a method declaration. <br />

&emsp;&emsp;
As you can see in Table 5.2, four of the method modifiers are covered in later chapters,
and the last two aren’t even in scope for the exam (and are seldom used in real life). In this
chapter, we focus on introducing you to these modifiers. Using them often requires a lot
more rules.

- **Table 5.2**: Optional specifiers for methods

|Specifiers| Description                                                           | Chapter covered |
|----------|-----------------------------------------------------------------------|-----------------|
|static| Indicates the method is a member of the shared class object           | Chapter 5       |
|abstract| Used in an abstract class or interface when the method body is excluded | Chapter 6       |
|final| Specifies that the method may not be overridden in a sub-class        | Chapter 6       |
|default|Used in an interface to provide a default implementation of a method for classes that implement the interface | Chapter 7       |
|synchronized| Used with multithreaded code | Chapter 13      |
|native| Used when interacting with code written in another language, such as C++ | Out of scope|
|strictfp| Used for making floating-point calculations portable | Out of scope|

&emsp;&emsp;
While access modifiers and optional specifiers can appear in any order, they must all
appear before the return type. In practice, it is common to list the access modifier first. As
you’ll also learn in upcoming chapters, some specifiers are not compatible with one another.
For example, you can’t declare a method (or class) both final and abstract.

> **Tip**: <br />
> Remember, access modifiers and optional specifiers can be listed in any
order, but once the return type is specified, the rest of the parts of the
method are written in a specific order: name, parameter list, exception
list, body.

&emsp;&emsp;
Again, just focus on syntax for now. Do you see why these compile or don’t compile?

```java
public class Exercise {
    public void bike1() {}
    public final void bike2() {}
    public static final void bike3() {}
    public final static void bike4() {}
    public modifier void bike5() {} // DOES NOT COMPILE
    public void final bike6() {} // DOES NOT COMPILE
    final public void bike7() {}
}
```

## 3. Return Type
The next item in a method declaration is the return type. It must appear after any access
modifiers or optional specifiers and before the method name. The return type might be an
actual Java type such as String or int. If there is no return type, the void keyword is used.
This special return type comes from the English language: void means without contents.

> **Note**: <br />
> Remember that a method must have a return type. If no value is returned,
the void keyword must be used. You cannot omit the return type.

&emsp;&emsp;
When checking return types, you also have to look inside the method body. Methods with
a return type other than void are required to have a return statement inside the method
body. This return statement must include the primitive or object to be returned. Methods
that have a return type of void are permitted to have a return statement with no value
returned or omit the return statement entirely. Think of a return statement in a void
method as the method saying, “I’m done!” and quitting early, such as the following:

```java
public void swim(int distance) {
    if (distance <= 0) {
        // Exit early, nothing to do!
        return;
    }
    System.out.print("Fish is swimming " + distance + " meters");
}
```

&emsp;&emsp;
Ready for some examples? Can you explain why these methods compile or don’t?

```java
public class Hike {
    public void hike1() {}
    public void hike2() { return; }
    public String hike3() { return ""; }
    public String hike4() {} // DOES NOT COMPILE
    public hike5() {} // DOES NOT COMPILE
    public String int hike6() { } // DOES NOT COMPILE
    String hike7(int a) { // DOES NOT COMPILE
        if (1 < 2) return "orange";
    }
}
```

&emsp;&emsp;
What about this modified version?

```java
String hike8(int a) {
    if (1 < 2) return "orange";
    return "apple"; // COMPILER WARNING
}
```

&emsp;&emsp;
The code compiles, although the compiler will produce a warning about unreachable code
(or dead code). This means the compiler was smart enough to realize you wrote code that
cannot possibly be reached. <br />

&emsp;&emsp;
When returning a value, it needs to be assignable to the return type. Can you spot what’s
wrong with two of these examples?

```java
public class Measurement {
    int getHeight1() {
        int temp = 9;
        return temp;
    }
    int getHeight2() {
        int temp = 9L; // DOES NOT COMPILE
        return temp;
    }
    int getHeight3() {
        long temp = 9L;
        return temp; // DOES NOT COMPILE
    }
}
```

&emsp;&emsp;
The getHeight2() method doesn’t compile because you can’t assign a long to an int.
The method getHeight3() method doesn’t compile because you can’t return a long value
as an int. If this wasn’t clear to you, you should go back to Chapter 2, “Operators,” and
reread the sections about numeric types and casting.

## 4. Method Name

Method names follow the same rules we practiced with variable names in Chapter 1,
“Building Blocks.” To review, an identifier may only contain letters, numbers, currency 
symbols, or _. Also, the first character is not allowed to be a number, and reserved words are not
allowed. Finally, the single underscore character is not allowed. <br />

&emsp;&emsp;
By convention, methods begin with a lowercase letter, but they are not required to. Since
this is a review of Chapter 1, we can jump right into practicing with some examples:

```java
public class BeachTrip {
    public void jog1() {}
    public void 2jog() {} // DOES NOT COMPILE
    public jog3 void() {} // DOES NOT COMPILE
    public void Jog_$() {}
    public _() {} // DOES NOT COMPILE
    public void() {} // DOES NOT COMPILE
}
```

&emsp;&emsp;
The jog1() method is a valid declaration with a traditional name. The 2jog() method
doesn’t compile because identifiers are not allowed to begin with numbers. The jog3()
method doesn’t compile because the method name is before the return type. The Jog_$()
method is a valid declaration. While it certainly isn’t good practice to start a method name
with a capital letter and end with punctuation, it is legal. The _ method is not allowed since
it consists of a single underscore. The final line of code doesn’t compile because the method
name is missing.

## 5. Parameter List
Although the parameter list is required, it doesn’t have to contain any parameters. This
means you can just have an empty pair of parentheses after the method name, as follows:

```java
public class Sleep {
    void nap() {}
}
```

&emsp;&emsp;
If you do have multiple parameters, you separate them with a comma. There are a couple
more rules for the parameter list that you’ll see when we cover varargs shortly. For now, let’s
practice looking at method declaration with “regular” parameters:

```java
public class PhysicalEducation {
    public void run1() {}
    public void run2 {}                 // DOES NOT COMPILE
    public void run3(int a) {}
    public void run4(int a; int b) {}   // DOES NOT COMPILE
    public void run5(int a, int b) {}
}
```

&emsp;&emsp;
The run1() method is a valid declaration without any parameters. The run2() method
doesn’t compile because it is missing the parentheses around the parameter list. The run3()
method is a valid declaration with one parameter. The run4() method doesn’t compile
because the parameters are separated by a semicolon rather than a comma. Semicolons are
for separating statements, not for parameter lists. The run5() method is a valid declaration
with two parameters.

## 6. Method Signature
A method signature, composed of the method name and parameter list, is what Java uses
to uniquely determine exactly which method you are attempting to call. Once it determines
which method you are trying to call, it then determines if the call is allowed. For example,
attempting to access a private method outside the class or assigning the return value of a
void method to an int variable results in compiler errors. Neither of these compiler errors is
related to the method signature, though. <br />

&emsp;&emsp;
It’s important to note that the names of the parameters in the method signature are not
used as part of a method signature. The parameter list is about the types of parameters and
their order. For example, the following two methods have the exact same signature:

```java
public class Trip {
    public void visitZoo(String name, int waitTime) {}
    public void visitZoo(String attraction, int rainFall) {} // DOES NOT COMPILE
}
```

&emsp;&emsp;
Despite having different parameter names, these two methods have the same signature
and cannot be declared within the same class. Changing the order of parameter types does
allow the method to compile, though:

```java
public class Trip {
    public void visitZoo(String name, int waitTime) {}
    public void visitZoo(int rainFall, String attraction) {}
}
```

## 7. Exception List
In Java, code can indicate that something went wrong by throwing an exception. We cover
this in Chapter 11, “Exceptions and Localization.” For now, you just need to know
that it is optional and where in the method declaration it goes if present. For example,
InterruptedException is a type of Exception. You can list as many types of exceptions
as you want in this clause, separated by commas. Here’s an example:

```java
public class ZooMonorail {
    public void zeroExceptions() {}
    public void oneException() throws IllegalArgumentException {}
    public void twoExceptions() throws IllegalArgumentException, InterruptedException {}
}
```

&emsp;&emsp;
While the list of exceptions is optional, it may be required by the compiler, depending on
what appears inside the method body. You learn more about this, as well as how methods
calling them may be required to handle these exception declarations, in Chapter 11.

## 8. Method Body
The final part of a method declaration is the method body. A method body is simply a code
block. It has braces that contain zero or more Java statements. We’ve spent several chapters
looking at Java statements by now, so you should find it easy to figure out why these compile or don’t:

```java
public class Bird {
    public void fly1() {}
    public void fly2() // DOES NOT COMPILE
    public void fly3(int a) { int name = 5; }
}
```

&emsp;&emsp;
The fly1() method is a valid declaration with an empty method body. The fly2()
method doesn’t compile because it is missing the braces around the empty method body.
Methods are required to have a body unless they are declared abstract. We cover
abstract methods in Chapter 6, “Class Design.” The fly3() method is a valid declaration
with one statement in the method body. <br />

&emsp;&emsp;
Congratulations! You’ve made it through the basics of identifying correct and incorrect
method declarations. Now you can delve into more detail.
