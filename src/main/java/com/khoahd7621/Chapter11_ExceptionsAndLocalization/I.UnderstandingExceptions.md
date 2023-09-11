# Understanding Exceptions

A program can fail for just about any reason. Here are just a few possibilities:
- The code tries to connect to a website, but the Internet connection is down.
- You made a coding mistake and tried to access an invalid index in an array.
- One method calls another with a value that the method doesn’t support. <br />

As you can see, some of these are coding mistakes. Others are completely beyond your
control. Your program can’t help it if the Internet connection goes down. What it can do is
deal with the situation.

## I. The Role of Exceptions
An *exception* is Java’s way of saying, “I give up. I don’t know what to do right now. You
deal with it.” When you write a method, you can either deal with the exception or make it
the calling code’s problem. <br/>

&emsp;&emsp;
As an example, think of Java as a child who visits the zoo. The *happy path* is when
nothing goes wrong. The child continues to look at the animals until the program ends
nicely. Nothing went wrong, and there were no exceptions to deal with. <br />

&emsp;&emsp;
This child’s younger sister doesn’t experience the happy path. In all the excitement, she
trips and falls. Luckily, it isn’t a bad fall. The little girl gets up and proceeds to look at more
animals. She has handled the issue all by herself. Unfortunately, she falls again later in the
day and starts crying. This time, she has declared that she needs help by crying. The story
ends well. Her daddy rubs her knee and gives her a hug. Then they go back to seeing more
animals and enjoy the rest of the day. <br />

&emsp;&emsp;
These are the two approaches Java uses when dealing with exceptions. A method can
handle the exception case itself or make it the caller’s responsibility.

> ### **Return Codes vs. Exceptions**
> Exceptions are used when “something goes wrong.” However, the word wrong is
subjective. The following code returns -1 instead of throwing an exception if no
match is found:
> ```java
>   public int indexOf(String[] names, String name) {
>       for (int i = 0; i < names.length; i++) {
>           if (names[i].equals(name)) { return i; }
>       }
>       return -1;
>   }
> ```
> While common for certain tasks like searching, return codes should generally be avoided.
After all, Java provided an exception framework, so you should use it!

## II. Understanding Exception Types
An exception is an event that alters program flow. Java has a Throwable class for all objects
that represent these events. Not all of them have the word exception in their class name,
which can be confusing. Figure 11.1 shows the key subclasses of Throwable.

> **Figure 11.1** Categories of exception <br />
> ```java
>                     [java.lang.Throwable]
>                      /               \
>     [java.lang.Exception]          (java.lang.Error)
>                    /
> (java.lang.RuntimeException)
> 
> [] Checked
> () Unchecked
> ```

### &emsp;&emsp; A. Checked Exceptions
A *checked exception* is an exception that must be declared or handled by the application
code where it is thrown. In Java, checked exceptions all inherit Exception but not
RuntimeException. Checked exceptions tend to be more anticipated—for example, trying
to read a file that doesn’t exist. <br />

> **Note**: <br />
> Checked exceptions also include any class that inherits Throwable but
not Error or RuntimeException, such as a class that directly extends
Throwable. For the exam, you just need to know about checked exceptions
that extend Exception.

&emsp;&emsp;
Checked exceptions? What are we checking? Java has a rule called the handle or declare
rule. The *handle* or *declare rule* means that all checked exceptions that could be thrown
within a method are either wrapped in compatible `try` and `catch` blocks or declared in the
method signature. <br />

&emsp;&emsp;
Because checked exceptions tend to be anticipated, Java enforces the rule that the programmer 
must do something to show that the exception was thought about. Maybe it was
handled in the method. Or maybe the method declares that it can’t handle the exception and
someone else should. <br />

&emsp;&emsp;
Let’s take a look at an example. The following `fall()` method declares that it might throw
an `IOException`, which is a checked exception:

```java
void fall(int distance) throws IOException {
    if (distance > 10) {
        throw new IOException();
    }
}
```

&emsp;&emsp;
Notice that you’re using two different keywords here. The throw keyword tells Java
that you want to throw an Exception, while the throws keyword simply declares that the
method might throw an Exception. It also might not. <br />

&emsp;&emsp;
Now that you know how to declare an exception, how do you handle it? The following
alternate version of the `fall()` method handles the exception:

```java
void fall(int distance) {
    try {
        if (distance > 10) {
            throw new IOException();
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
```

&emsp;&emsp;
Notice that the catch statement uses Exception, not IOException. Since
IOException is a subclass of Exception, the catch block is allowed to catch it. We cover
try and catch blocks in more detail later in this chapter.

### &emsp;&emsp; B. Unchecked Exceptions
An *unchecked exception* is any exception that does not need to be declared or handled by
the application code where it is thrown. Unchecked exceptions are often referred to as
*runtime exceptions*, although in Java, unchecked exceptions include any class that inherits
`RuntimeException` or `Error`. <br />

> **Tip**: <br />
> It is permissible to handle or declare an unchecked exception. That said, it
is better to document the unchecked exceptions callers should know about
in a Javadoc comment rather than declaring an unchecked exception.

&emsp;&emsp;
A *runtime exception* is defined as the `RuntimeException` class and its subclasses. Runtime 
exceptions tend to be unexpected but not necessarily fatal. For example, accessing an
invalid array index is unexpected. Even though they do inherit the Exception class, they are
not checked exceptions. <br />

&emsp;&emsp;
An unchecked exception can occur on nearly any line of code, as it is not required to be
handled or declared. For example, a `NullPointerException` can be thrown in the body of the
following method if the input reference is null:

```java
void fall(String input) {
    System.out.println(input.toLowerCase());
}
```

&emsp;&emsp;
We work with objects in Java so frequently that a `NullPointerException` can 
happen almost anywhere. If you had to declare unchecked exceptions everywhere, every
single method would have that clutter! The code will compile if you declare an unchecked
exception. However, it is redundant.

### &emsp;&emsp; C. *Error* and *Throwable*
`Error` means something went so horribly wrong that your program should not attempt to
recover from it. For example, the disk drive “disappeared” or the program ran out of memory.
These are abnormal conditions that you aren’t likely to encounter and cannot recover from. <br />

&emsp;&emsp;
For the exam, the only thing you need to know about Throwable is that it’s the parent
class of all exceptions, including the Error class. While you can handle `Throwable` and
`Error` exceptions, it is not recommended you do so in your application code. When we refer
to exceptions in this chapter, we generally mean any class that inherits `Throwable`, although
we are almost always working with the `Exception` class or subclasses of it.

### &emsp;&emsp; D. Reviewing Exception Types
Be sure to closely study everything in Table 11.1. For the exam, remember that a `Throwable` is
either an `Exception` or an `Error`. You should not catch `Throwable` directly in your code.

> **Table 11.1** Exception types
> 
> |Type|How to recognize|Okay for program to catch?|Is program required to handle or declare?|
> |----|-----------------|--------------------------|--------------------------------------|
> |Unchecked exception|Subclass of `RuntimeException`|Yes|No|
> |Checked exception|Subclass of `Exception` but not `RuntimeException`|Yes|Yes|
> |Error|Subclass of `Error`|No|No|

## III. Throwing an Exception
Any Java code can throw an exception; this includes code you write. Some exceptions are
provided with Java. You might encounter an exception that was made up for the exam. This
is fine. The question will make it obvious that this is an exception by having the class name
end with `Exception`. For example, `MyMadeUpException` is clearly an exception. <br />

&emsp;&emsp;
On the exam, you will see two types of code that result in an exception. The first is code
that’s wrong. Here’s an example:

```java
String[] animals = new String[0];
System.out.println(animals[0]); // ArrayIndexOutOfBoundsException
```

&emsp;&emsp;
This code throws an `ArrayIndexOutOfBoundsException` since the array has no 
elements. That means questions about exceptions can be hidden in questions that appear to be
about something else.

> **Note**: <br />
> On the exam, some questions have a choice about not compiling and
about throwing an exception. Pay special attention to code that calls a
method on a null reference or that references an invalid array or List
index. If you spot this, you know the correct answer is that the code
throws an exception at runtime.

&emsp;&emsp;
The second way for code to result in an exception is to explicitly request Java to throw
one. Java lets you write statements like these:

```java
throw new Exception();
throw new Exception("Ow! I fell.");
throw new RuntimeException();
throw new RuntimeException("Ow! I fell.");
```

&emsp;&emsp;
The throw keyword tells Java that you want some other part of the code to deal with
the exception. This is the same as the young girl crying for her daddy. Someone else needs to
figure out what to do about the exception.

> ### *throw* vs. *throws*
> Anytime you see throw or throws on the exam, make sure the correct one is being used.
The throw keyword is used as a statement inside a code block to throw a new exception
or rethrow an existing exception, while the throws keyword is used only at the end of a
method declaration to indicate what exceptions it supports.

&emsp;&emsp;
When creating an exception, you can usually pass a `String` parameter with a message, or
you can pass no parameters and use the defaults. We say `usually` because this is a convention.
Someone has declared a constructor that takes a String. Someone could also create an
exception class that does not have a constructor that takes a message. <br />

&emsp;&emsp;
Additionally, you should know that an `Exception` is an `Object`. This means you can
store it in an object reference, and this is legal:

```java
var e = new RuntimeException();
throw e;
```

&emsp;&emsp;
The code instantiates an exception on one line and then throws on the next. The
exception can come from anywhere, even passed into a method. As long as it is a valid
exception, it can be thrown. <br />

&emsp;&emsp;
The exam might also try to trick you. Do you see why this code doesn’t compile?

```java
throw RuntimeException(); // DOES NOT COMPILE
```

&emsp;&emsp;
If your answer is that there is a missing keyword, you’re absolutely right. The exception is
never instantiated with the new keyword. <br/>

&emsp;&emsp;
Let’s take a look at another place the exam might try to trick you. Can you see why the
following does not compile?

```java
3:  try {
4:      throw new RuntimeException();
5:      throw new ArrayIndexOutOfBoundsException(); // DOES NOT COMPILE
6:  } catch (Exception e) {}
```

&emsp;&emsp;p
Since line 4 throws an exception, line 5 can never be reached during runtime. The 
compiler recognizes this and reports an unreachable code error.

## IV. Calling Methods That Throw Exceptions
When you’re calling a method that throws an exception, the rules are the same as within a
method. Do you see why the following doesn’t compile?

```java
class NoMoreCarrotsException extends Exception {}

public class Bunny {
    public static void main(String[] args) {
         eatCarrot(); // DOES NOT COMPILE
    }
    private static void eatCarrot() throws NoMoreCarrotsException {}
}
```

&emsp;&emsp;
The problem is that `NoMoreCarrotsException` is a checked exception. Checked 
exceptions must be handled or declared. The code would compile if you changed the `main()`
method to either of these:

```java
public static void main(String[] args) throws NoMoreCarrotsException {
    eatCarrot();
}

public static void main(String[] args) {
    try {
        eatCarrot();
    } catch (NoMoreCarrotsException e) {
        System.out.print("sad rabbit");
    }
}
```
&emsp;&emsp;
You might have noticed that `eatCarrot()` didn’t throw an exception; it just declared that it
could. This is enough for the compiler to require the caller to handle or declare the exception. <br />

&emsp;&emsp;
The compiler is still on the lookout for unreachable code. Declaring an unused exception
isn’t considered unreachable code. It gives the method the option to change the 
implementation to throw that exception in the future. Do you see the issue here?


```java
public void bad() {
    try {
        eatCarrot();
    } catch (NoMoreCarrotsException e) { // DOES NOT COMPILE
        System.out.print("sad rabbit");
    }
}

private void eatCarrot() {}
```

&emsp;&emsp;
Java knows that `eatCarrot()` can’t throw a checked exception—which means there’s no
way for the catch block in `bad()` to be reached.

> **Note**: <br />
> When you see a checked exception declared inside a catch block on the
exam, make sure the code in the associated try block is capable of throwing
the exception or a subclass of the exception. If not, the code is unreachable
and does not compile. Remember that this rule does not extend to
unchecked exceptions or exceptions declared in a method signature.

## V. Overriding Methods with Exceptions
When we introduced overriding methods in Chapter 6, “Class Design,” we included a rule
related to exceptions. An overridden method may not declare any new or broader checked
exceptions than the method it inherits. For example, this code isn’t allowed:

```java
class CanNotHopException extends Exception {}

class Hopper {
    public void hop() {}
}

class Bunny extends Hopper {
    public void hop() throws CanNotHopException {} // DOES NOT COMPILE
}
```

&emsp;&emsp;
Java knows `hop()` isn’t allowed to throw any checked exceptions because the `hop()`
method in the superclass `Hopper` doesn’t declare any. Imagine what would happen if the
subclasses’ versions of the method could add checked exceptions—you could write code that
calls Hopper’s `hop()` method and not handle any exceptions. Then, if `Bunny` were used in
its place, the code wouldn’t know to handle or declare `CanNotHopException`. <br />

&emsp;&emsp;
An overridden method in a subclass is allowed to declare fewer exceptions than the superclass 
or interface. This is legal because callers are already handling them.

```java
class Hopper {
    public void hop() throws CanNotHopException {}
}
class Bunny extends Hopper {
    public void hop() {} // This is fine
}
```

&emsp;&emsp;
An overridden method not declaring one of the exceptions thrown by the parent method
is similar to the method declaring that it throws an exception it never actually throws. This
is perfectly legal. Similarly, a class is allowed to declare a subclass of an exception type. The
idea is the same. The superclass or interface has already taken care of a broader type.

## VI. Printing an Exception
There are three ways to print an exception. You can let Java print it out, print just the message, 
or print where the stack trace comes from. This example shows all three approaches:

```java
5:  public static void main(String[] args) {
6:      try {
7:          hop();
8:      } catch (Exception e) {
9:          System.out.println(e + "\n");
10:         System.out.println(e.getMessage()+ "\n");
11:         e.printStackTrace();
12:     }
13: }
14: private static void hop() {
15:     throw new RuntimeException("cannot hop");
16: }
```

&emsp;&emsp;
This code prints the following:

```java
java.lang.RuntimeException: cannot hop
        
cannot hop
        
java.lang.RuntimeException: cannot hop
 at Handling.hop(Handling.java:15)
 at Handling.main(Handling.java:7)
```

&emsp;&emsp;
The first line shows what Java prints out by default: the exception type and message. The
second line shows just the message. The rest shows a stack trace. The stack trace is usually
the most helpful because it shows the hierarchy of method calls that were made to reach the
line that threw the exception.
