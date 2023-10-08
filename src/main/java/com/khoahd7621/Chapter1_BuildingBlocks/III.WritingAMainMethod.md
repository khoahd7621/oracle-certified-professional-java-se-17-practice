# Writing a _main()_ Method

A Java program begins execution with its `main()` method. In this section, you learn how to
create one, pass a parameter, and run a program. The `main()` method is often called an entry
point into the program, because it is the starting point that the JVM looks for when it begins
running a new program.

## I. Creating a _main()_ Method

The `main()` method lets the JVM call our code. The simplest possible class with a `main()`
method looks like this:

```java
1:  public class Zoo {
2:      public static void main(String[] args) {
3:          System.out.println("Hello World");
4:      }
5:  }
```

This code prints `Hello World`. To compile and execute this code, type it into a file called
_Zoo.java_ and execute the following:

```bash
javac Zoo.java
java Zoo
```

&emsp;&emsp;
If it prints `Hello World`, you were successful. If you do get error messages, check that
you’ve installed the Java 17 JDK, that you have added it to the PATH, and that you didn’t
make any typos in the example. If you have any of these problems and don’t know what
to do, post a question with the error message you received in the _Beginning Java_ forum at
CodeRanch:

```link
http://www.coderanch.com/forums/f-33/java
```

&emsp;&emsp;
To compile Java code with the `javac` command, the file must have the extension `.java`.
The name of the file must match the name of the public class. The result is a file of bytecode
with the same name but with a `.class` filename extension. Remember that bytecode consists
of instructions that the JVM knows how to execute. Notice that we must omit the `.class`
extension to run `Zoo.class`. <br />

&emsp;&emsp;
The rules for what a Java file contains, and in what order, are more detailed than what we
have explained so far (there is more on this topic later in the chapter). To keep things simple
for now, we follow this subset of the rules:

- Each file can contain only one public class.
- The filename must match the class name, including case, and have a `.java` extension.
- If the Java class is an entry point for the program, it must contain a valid `main()` method.

&emsp;&emsp;
Let’s first review the words in the `main()` method’s signature, one at a time. The keyword
`public` is what’s called an _access modifier_. It declares this method’s level of exposure to
potential callers in the program. Naturally, `public` means full access from anywhere in the
program. You learn more about access modifiers in Chapter 5. <br />

&emsp;&emsp;
The keyword `static` binds a method to its class so it can be called by just the class name,
as in, for example, `Zoo.main()`. Java doesn’t need to create an object to call the `main()`
method—which is good since you haven’t learned about creating objects yet! In fact, the
JVM does this, more or less, when loading the class name given to it. If a `main()` method
doesn’t have the right keywords, you’ll get an error trying to run it. You see `static` again in
Chapter 6, “Class Design.” <br />

&emsp;&emsp;
The keyword `void` represents the _return type_. A method that returns no data returns 
control to the caller silently. In general, it’s good practice to use void for methods that change an
object’s state. In that sense, the `main()` method changes the program state from started to finished. 
We explore return types in Chapter 5 as well. (Are you excited for Chapter 5 yet?) <br />

&emsp;&emsp;
Finally, we arrive at the `main()` method’s parameter list, represented as an array of
`java.lang.String` objects. You can use any valid variable name along with any of these
three formats:

```java
String[] args
String options[]
String... friends
```

&emsp;&emsp;
The compiler accepts any of these. The variable name args is common because it hints
that this list contains values that were read in (arguments) when the JVM started. The 
characters `[]` are brackets and represent an array. An array is a fixed-size list of items that are
all of the same type. The characters `...` are called varargs (variable argument lists). You
learn about `String` in this chapter. `Arrays` are in Chapter 4, “Core APIs,” and `varargs` are in
Chapter 5.

> #### Optional Modifiers in _main()_ Methods
> While most modifiers, such as `public` and `static`, are required for `main()` methods,
there are some optional modifiers allowed.
> ```java
> public final static void main(final String[] args) {}
> ```
> In this example, both `final` modifiers are optional, and the `main()` method is a valid
entry point with or without them. We cover the meaning of final methods and parameters
in Chapter 6

## II. Passing Parameters to a Java Program
Let’s see how to send data to our program’s `main()` method. First, we modify the Zoo
program to print out the first two arguments passed in:

```java
public class Zoo {
    public static void main(String[] args) {
        System.out.println(args[0]);
        System.out.println(args[1]);
    }
}
```

&emsp;&emsp;
The code `args[0]` accesses the first element of the array. That’s right: array indexes begin
with 0 in Java. To run it, type this:

```bash
javac Zoo.java
java Zoo Bronx Zoo
```

&emsp;&emsp;
The output is what you might expect:

```java
Bronx
Zoo
```

&emsp;&emsp;
The program correctly identifies the first two “words” as the arguments. Spaces are used
to separate the arguments. If you want spaces inside an argument, you need to use quotes as
in this example:

```bash
javac Zoo.java
java Zoo "San Diego" Zoo
```

&emsp;&emsp;
Now we have a space in the output:

```java
San Diego
Zoo
```

&emsp;&emsp;
Finally, what happens if you don’t pass in enough arguments?

```bash
javac Zoo.java
java Zoo Zoo
```

&emsp;&emsp;
Reading `args[0]` goes fine, and Zoo is printed out. Then Java panics. There’s no second
argument! What to do? Java prints out an exception telling you it has no idea what to do
with this argument at position 1. (You learn about exceptions in Chapter 11, “Exceptions
and Localization.”)

```java
Zoo
Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException:
    Index 1 out of bounds for length 1
    at Zoo.main(Zoo.java:4)
```

&emsp;&emsp;
To review, the JDK contains a compiler. Java class files run on the JVM and therefore run
on any machine with Java rather than just the machine or operating system they happened
to have been compiled on.

> #### Single-File Source-Code
> If you get tired of typing both `javac` and `java` every time you want to try a code example,
there’s a shortcut. You can instead run
> ```bash
> java Zoo.java Bronx Zoo
> ```
> There is a key difference here. When compiling first, you omitted the `.java` extension
when running java. When skipping the explicit compilation step, you include this
extension. This feature is called launching _single-file source-code_ programs and is useful for
testing or for small programs. The name cleverly tells you that it is designed for when your
program is one file.
