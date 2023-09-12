# Recognizing Exception Classes

You need to recognize three groups of exception classes for the exam: RuntimeException,
checked Exception, and Error. We look at common examples of each type. For the exam,
you’ll need to recognize which type of an exception it is and whether it’s thrown by the Java
Virtual Machine (JVM) or by a programmer. For some exceptions, you also need to know
which are inherited from one another

## I. *RuntimeException* Classes

`RuntimeException` and its subclasses are unchecked exceptions that don’t have to be 
handled or declared. They can be thrown by the programmer or the JVM. Common unchecked
exception classes are listed in Table 11.2.

> **Table 11.2** Unchecked exceptions
> 
> | **Exception** | **Description** |
> | --- | --- |
> |ArithmeticException|Thrown when code attempts to divide by zero.|
> |ArrayIndexOutOfBoundsException|Thrown when code uses illegal index to access array.|
> |ClassCastException|Thrown when attempt is made to cast object to class of which it is not an instance.|
> |NullPointerException|Thrown when there is a null reference where an object is required.|
> |IllegalArgumentException|Thrown by programmer to indicate that method has been passed illegal or inappropriate argument.|
> |NumberFormatException|Subclass of IllegalArgumentException. Thrown when attempt is made to convert String to numeric type but String doesn’t have appropriate format.|

### &emsp;&emsp; 1. *ArithmeticException*

Trying to divide an int by zero gives an undefined result. When this occurs, the JVM will
throw an `ArithmeticException`:

```java
int answer = 11 / 0;
```

&emsp;&emsp;
Running this code results in the following output:

```java
Exception in thread "main" java.lang.ArithmeticException: / by zero
```

&emsp;&emsp;
Java doesn’t spell out the word *divide*. That’s okay, though, because we know that / is the
division operator and that Java is trying to tell you division by zero occurred. <br />

&emsp;&emsp;
The thread "main" is telling you the code was called directly or indirectly from a
program with a main method. On the exam, this is all the output you will see. Next comes
the name of the exception, followed by extra information (if any) that goes with the
exception

### &emsp;&emsp; 2. *ArrayIndexOutOfBoundsException*
You know by now that array indexes start with 0 and go up to 1 less than the length of the
array—which means this code will throw an `ArrayIndexOutOfBoundsException`:

```java
int[] countsOfMoose = new int[3];
System.out.println(countsOfMoose[-1]);
```

&emsp;&emsp;
This is a problem because there’s no such thing as a negative array index. Running this
code yields the following output:

```java
Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException:
Index -1 out of bounds for length 3
```

### &emsp;&emsp; 3. *ClassCastException*
Java tries to protect you from impossible casts. This code doesn’t compile because `Integer`
is not a subclass of `String`:

```java
String type = "moose";
Integer number = (Integer) type; // DOES NOT COMPILE
```

More complicated code thwarts Java’s attempts to protect you. When the cast fails at 
runtime, Java will throw a `ClassCastException`:

```java
String type = "moose";
Object obj = type;
Integer number = (Integer) obj; // ClassCastException
```

The compiler sees a cast from `Object` to `Integer`. This could be okay. The compiler doesn’t
realize there’s a String in that `Object`. When the code runs, it yields the following output:

```java
Exception in thread "main" java.lang.ClassCastException: 
java.base/java.lang.String
cannot be cast to java.lang.base/java.lang.Integer
```

&emsp;&emsp;
Java tells you both types that were involved in the problem, making it apparent what’s wrong.

### &emsp;&emsp; 4. *NullPointerException*
Instance variables and methods must be called on a non-null reference. If the reference is null,
the JVM will throw a `NullPointerException`.

```java
1:  public class Frog {
2:      public void hop(String name, Integer jump) {
3:          System.out.print(name.toLowerCase() + " " + jump.intValue());
4:      }
5: 
6:      public static void main(String[] args) {
7:          new Frog().hop(null, 1); 
8:      }
9: }
```

&emsp;&emsp;
Running this code results in the following output:

```java
Exception in thread "main" java.lang.NullPointerException: Cannot invoke 
"String.toLowerCase()" because "<parameter1>" is null
```

&emsp;&emsp;
If you’re new to Java 17, you should have noticed something special about the output.
The JVM now tells you the object reference that triggered the `NullPointerException`!
This new feature is called *Helpful NullPointerExceptions*. <br />

&emsp;&emsp;
As another example, suppose we change line 7:

```java
7:          new Frog().hop("Kermit", null);
```

&emsp;&emsp;
Then the output at runtime changes as follows:

```java
Exception in thread "main" java.lang.NullPointerException: Cannot invoke 
"java.lang.Integer.intValue()" because "<parameter2>" is null
```

> **Tip** <br />
> By default, a `NullPointerException` on a local variable or method
parameter is printed with a number indicating the order in which it
appears in the method, such as <local2> or <parameter4>. If you’re
like us and want the actual variable name to be shown, compile the code
with the -g:vars flag, which adds debug info. In the previous examples,
<parameter1> and <parameter2> are then replaced with name and
jump, respectively.

&emsp;&emsp;
Since this is a new feature in Java, it’s possible you’ll see it in a question on the exam.

> **Enabling/Disabling Helpful *NullPointerExceptions*** <br />
> When helpful NullPointerExceptions were added in Java 14, the feature
was disabled by default and had to be enabled via a command-line argument
`ShowCodeDetailsInExceptionMessages` to the JVM:
> ```java
> java -XX:+ShowCodeDetailsInExceptionMessages Frog
> ```
> In Java 15 and above, the default behavior was changed so that it is enabled by default,
although it can still be disabled via the command-line argument.
> ```java
> java -XX:-ShowCodeDetailsInExceptionMessages Frog
> ```

### &emsp;&emsp; 5. *IllegalArgumentException*
IllegalArgumentException is a way for your program to protect itself. You want to
tell the caller that something is wrong—preferably in an obvious way that the caller can’t
ignore so the programmer will fix the problem. Seeing the code end with an exception
is a great reminder that something is wrong. Consider this example when called as
`setNumberEggs(-2)`:

```java
public void setNumberEggs(int numberEggs) {
    if (numberEggs < 0)
        throw new IllegalArgumentException("# eggs must not be negative");
    this.numberEggs = numberEggs;
}
```

&emsp;&emsp;
The program throws an exception when it’s not happy with the parameter values. The
output looks like this:

```java
Exception in thread "main"
java.lang.IllegalArgumentException: # eggs must not be negative
```

&emsp;&emsp;
Clearly, this is a problem that must be fixed if the programmer wants the program to do
anything useful.

### &emsp;&emsp; 6. *NumberFormatException*
Java provides methods to convert strings to numbers. When these are passed an invalid
value, they throw a `NumberFormatException`. The idea is similar to `IllegalArgumentException`.
Since this is a common problem, Java gives it a separate class. In fact, `NumberFormatException`
is a subclass of `IllegalArgumentException`. Here’s an example of trying to convert something
non-numeric into an int:

```java
Integer.parseInt("abc");
```

&emsp;&emsp;
The output looks like this:

```java
Exception in thread "main"
java.lang.NumberFormatException: For input string: "abc"
```

&emsp;&emsp;
For the exam, you need to know that  is a subclass of `NumberFormatException`
`IllegalArgumentException`. We cover more about why that is important later in
the chapter.

## II. *Checked Exception* Classes

Checked exceptions have `Exception` in their hierarchy but not `RuntimeException`. They must be
handled or declared. Common checked exceptions are listed in Table 11.3. <br />

&emsp;&emsp;
For the exam, you need to know that these are all checked exceptions that must be
handled or declared. You also need to know that `FileNotFoundException` and
`NotSerializableException` are subclasses of `IOException`. You see these three classes
in Chapter 14, “I/O,” and SQLException in Chapter 15, “JDBC.”


> **Table 11.3** Checked exceptions
> 
> | **Checked exception** | **Description**                                                                                                      |
> |-----------------------|----------------------------------------------------------------------------------------------------------------------|
> |FileNotFoundException | Subclass of IOException. Thrown programmatically when code tries to reference file that does not exist.              |
> |IOException | Thrown programmatically when problem reading or writing file.                                                        |
> |NotSerializableException | Subclass of IOException. Thrown programmatically when attempting to serialize or deserialize non-serializable class. |
> |ParseException | Indicates problem parsing input.                                                                                     |
> |SQLException | Thrown when error related to accessing database.                                                                     |

## III. *Error* Classes
Errors are unchecked exceptions that extend the `Error` class. They are thrown by the JVM and
should not be handled or declared. Errors are rare, but you might see the ones listed in Table 11.4.

> **Table 11.4** Errors
> 
> | **Error** | **Description** |
> | --- | --- |
> |ExceptionInInitializerError |Thrown when static initializer throws exception and doesn’t handle it|
> |StackOverflowError |Thrown when method calls itself too many times (called infinite recursion because method typically calls itself without end)|
> |NoClassDefFoundError |Thrown when class that code uses is available at compile time but not runtime|

&emsp;&emsp;
For the exam, you just need to know that these errors are unchecked and the code is often
unable to recover from them.
