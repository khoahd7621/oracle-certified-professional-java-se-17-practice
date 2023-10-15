# Handling Exceptions

What do you do when you encounter an exception? How do you handle or recover from
the exception? In this section, we show the various statements in Java that support handling
exceptions.

## I. Using *try* and *catch* Statements
Now that you know what exceptions are, let’s explore how to handle them. Java uses a try
statement to separate the logic that might throw an exception from the logic to handle that
exception. Figure 11.2 shows the syntax of a try statement.

> **Figure 11.2** The syntax of a try statement
>
> ```java
>   try {
>       // Protected code
>   } catch (exception_type identifier) {
>       // Exception handler
>   }
> ```

&emsp;&emsp;
The code in the `try` block is run normally. If any of the statements throws an exception
that can be caught by the exception type listed in the `catch` block, the `try` block stops
running, and execution goes to the `catch` statement. If none of the statements in the `try`
block throws an exception that can be caught, the `catch` clause is not run. <br />

&emsp;&emsp;
You probably noticed the words *block* and *clause* used interchangeably. The exam does
this as well, so get used to it. Both are correct. *Block* is correct because there are braces 
present. *Clause* is correct because it is part of a try statement. <br />

&emsp;&emsp;
There aren’t a ton of syntax rules here. The curly braces are required for try and catch
blocks. In our example, the little girl gets up by herself the first time she falls. Here’s what
this looks like:

```java
3:  void explore() {
4:      try {
5:          fall();
6:          System.out.println("never get here");
7:      } catch (RuntimeException e) {
8:          getUp();
9:      }
10:     seeAnimals();
11: }
12: void fall() { throw new RuntimeException(); }
```

&emsp;&emsp;
First, line 5 calls the `fall()` method. Line 12 throws an exception. This means Java
jumps straight to the catch block, skipping line 6. The girl gets up on line 8. Now the try
statement is over, and execution proceeds normally with line 10. <br />

&emsp;&emsp;
Now let’s look at some invalid try statements that the exam might try to trick you with.
Do you see what’s wrong with this one?

```java
try // DOES NOT COMPILE
    fall();
catch (Exception e)
    System.out.println("get up");
```

&emsp;&emsp;
The problem is that the braces {} are missing. The try statements are like methods in
that the curly braces are required even if there is only one statement inside the code blocks,
while if statements and loops are special and allow you to omit the curly braces. <br />

&emsp;&emsp;
What about this one?

```java
try { // DOES NOT COMPILE
    fall();
}
```

&emsp;&emsp;
This code doesn’t compile because the try block doesn’t have anything after it. Remember, the
point of a try statement is for something to happen if an exception is thrown. Without another
clause, the try statement is lonely. As you see shortly, there is a special type of try statement that
includes an implicit finally block, although the syntax is quite different from this example.

## II. Chaining *catch* Blocks
For the exam, you may be given exception classes and need to understand how they
function. Here’s how to tackle them. First, you must be able to recognize if the exception
is a checked or an unchecked exception. Second, you need to determine whether any of the
exceptions are subclasses of the others.

```java
class AnimalsOutForAWalk extends RuntimeException {}

class ExhibitClosed extends RuntimeException {}

class ExhibitClosedForLunch extends ExhibitClosed {}
```

&emsp;&emsp;
In this example, there are three custom exceptions. All are unchecked exceptions because
they directly or indirectly extend `RuntimeException`. Now we chain both types of 
exceptions with two catch blocks and handle them by printing out the appropriate message:

```java
public void visitPorcupine() {
    try {
        seeAnimal();
    } catch (AnimalsOutForAWalk e) { // first catch block
        System.out.print("try back later");
    } catch (ExhibitClosed e) { // second catch block
        System.out.print("not today");
    }
}
```

&emsp;&emsp;
There are three possibilities when this code is run. If `seeAnimal()` doesn’t throw an
exception, nothing is printed out. If the animal is out for a walk, only the first catch block
runs. If the exhibit is closed, only the second catch block runs. It is not possible for both
catch blocks to be executed when chained together like this. <br/>

&emsp;&emsp;
A rule exists for the order of the catch blocks. Java looks at them in the order they appear.
If it is impossible for one of the catch blocks to be executed, a compiler error about 
unreachable code occurs. For example, this happens when a superclass catch block appears before a
subclass catch block. Remember, we warned you to pay attention to any subclass exceptions. <br />

&emsp;&emsp;
In the porcupine example, the order of the catch blocks could be reversed because the
exceptions don’t inherit from each other. And yes, we have seen a porcupine be taken for a
walk on a leash. <br />

&emsp;&emsp;
The following example shows exception types that do inherit from each other:

```java
public void visitMonkeys() {
    try {
        seeAnimal();
    } catch (ExhibitClosedForLunch e) { // Subclass exception
        System.out.print("try back later");
    } catch (ExhibitClosed e) { // Superclass exception
        System.out.print("not today");
    }
}
```

&emsp;&emsp;
If the more specific `ExhibitClosedForLunch` exception is thrown, the first catch block
runs. If not, Java checks whether the superclass `ExhibitClosed` exception is thrown and
catches it. This time, the order of the catch blocks does matter. The reverse does not work.

```java
public void visitMonkeys() {
    try {
        seeAnimal();
    } catch (ExhibitClosed e) {
        System.out.print("not today");
    } catch (ExhibitClosedForLunch e) { // DOES NOT COMPILE
        System.out.print("try back later");
    }
}
```

&emsp;&emsp;
If the more specific `ExhibitClosedForLunch` exception is thrown, the catch block for
`ExhibitClosed` runs—which means there is no way for the second catch block to ever
run. Java correctly tells you there is an unreachable catch block. <br />

&emsp;&emsp;
Let’s try this one more time. Do you see why this code doesn’t compile?

```java
public void visitSnakes() {
    try {
    } catch (IllegalArgumentException e) {
    } catch (NumberFormatException e) { // DOES NOT COMPILE
    }
}
```

&emsp;&emsp;
Remember we said earlier that you needed to know that NumberFormatException is
a subclass of IllegalArgumentException? This example is the reason why. Since
NumberFormatException is a subclass, it will always be caught by the first catch block,
making the second catch block unreachable code that does not compile. Likewise, for the
exam, you need to know that FileNotFoundException is a subclass of IOException and
cannot be used in a similar manner. <br />

&emsp;&emsp;
To review multiple catch blocks, remember that at most one catch block will run, and it will
be the first catch block that can handle the exception. Also, remember that an exception defined
by the catch statement is only in scope for that catch block. For example, the following causes a
compiler error since it tries to use the exception object outside the block for which it was defined:

```java
public void visitManatees() {
    try {
    } catch (NumberFormatException e1) {
        System.out.println(e1);
    } catch (IllegalArgumentException e2) {
        System.out.println(e1); // DOES NOT COMPILE
    }
}
```

## III. Applying a Multi-catch Block
Often, we want the result of an exception that is thrown to be the same, regardless of which
particular exception is thrown. For example, take a look at this method:

```java
public static void main(String args[]) {
    try {
        System.out.println(Integer.parseInt(args[1]));
    } catch (ArrayIndexOutOfBoundsException e) {
        System.out.println("Missing or invalid input");
    } catch (NumberFormatException e) {
        System.out.println("Missing or invalid input");
    }
}
```

&emsp;&emsp;
Notice that we have the same `println()` statement for two different catch blocks.
We can handle this more gracefully using a *multi-catch* block. A multi-catch block allows
multiple exception types to be caught by the same catch block. Let’s rewrite the previous
example using a multi-catch block:

```java
public static void main(String[] args) {
    try {
        System.out.println(Integer.parseInt(args[1]));
    } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
        System.out.println("Missing or invalid input");
    }
}
```

&emsp;&emsp;
This is much better. There’s no duplicate code, the common logic is all in one place, and the
logic is exactly where you would expect to find it. If you wanted, you could still have a second
catch block for Exception in case you want to handle other types of exceptions differently. <br />

&emsp;&emsp;
Figure 11.3 shows the syntax of multi-catch. It’s like a regular catch clause, except two
or more exception types are specified, separated by a pipe. The pipe (|) is also used as the
“or” operator, making it easy to remember that you can use either/or of the exception types.
Notice how there is only one variable name in the catch clause. Java is saying that the 
variable named e can be of type Exception1 or Exception2.

> **Figure 11.3** The syntax of a multi-catch block
> 
> ```java
>   try {
>       // Protected code
>   } catch (Exception1 | Exception2 e) {
>       // Exception handler
>   }
> ```

&emsp;&emsp;
The exam might try to trick you with invalid syntax. Remember that the exceptions can
be listed in any order within the catch clause. However, the variable name must appear only
once and at the end. Do you see why these are valid or invalid?

```java
catch(Exception1 e | Exception2 e | Exception3 e) // DOES NOT COMPILE
        
catch(Exception1 e1 | Exception2 e2 | Exception3 e3) // DOES NOT COMPILE
        
catch(Exception1 | Exception2 | Exception3 e)
```

&emsp;&emsp;
The first line is incorrect because the variable name appears three times. Just because it
happens to be the same variable name doesn’t make it okay. The second line is incorrect
because the variable name again appears three times. Using different variable names doesn’t
make it any better. The third line does compile. It shows the correct syntax for specifying
three exceptions. <br />

&emsp;&emsp;
Java intends multi-catch to be used for exceptions that aren’t related, and it prevents you
from specifying redundant types in a multi-catch. Do you see what is wrong here?

```java
try {
    throw new IOException();
} catch (FileNotFoundException | IOException p) {} // DOES NOT COMPILE
```

&emsp;&emsp;
Specifying related exceptions in the multi-catch is redundant, and the compiler gives a
message such as this:

```java
The exception FileNotFoundException is already caught by the alternative 
IOException
```

&emsp;&emsp;
Since `FileNotFoundException` is a subclass of `IOException`, this code will not compile.
A multi-catch block follows rules similar to chaining catch blocks together, which you
saw in the previous section. For example, both trigger compiler errors when they encounter
unreachable code or duplicate exceptions being caught. The one difference between 
multi-catch blocks and chaining catch blocks is that order does not matter for a multi-catch block
within a single catch expression. <br />

&emsp;&emsp;
Getting back to the example, the correct code is just to drop the extraneous subclass reference, as shown here:

```java
try {
    throw new IOException();
} catch (IOException e) {}
```

## IV. Adding a *finally* Block
The try statement also lets you run code at the end with a *finally* clause, regardless of
whether an exception is thrown. Figure 11.4 shows the syntax of a try statement with this
extra functionality.

> **Figure 11.4** The syntax of a `try` statement with a `finally`
> 
> ```java
> try {
>   // Protected code
> } catch (exception_type identifier) {
>   // Exception handler
> } finally {
>   // finally block
> }
> ```

&emsp;&emsp;
There are two paths through code with both a `catch` and a `finally`. If an exception
is thrown, the `finally` block is run after the `catch` block. If no exception is thrown, the
`finally` block is run after the `try` block completes. <br />

&emsp;&emsp;
Let’s go back to our young girl example, this time with `finally`:

```java
12: void explore() {
13:     try {
14:         seeAnimals();
15:         fall();
16:     } catch (Exception e) {
17:         getHugFromDaddy();
18:     } finally {
19:         seeMoreAnimals();
20:     }
21:     goHome();
22: }
```

&emsp;&emsp;
The girl falls on line 15. If she gets up by herself, the code goes on to the `finally` block
and runs line 19. Then the `try` statement is over, and the code proceeds on line 21. If the
girl doesn’t get up by herself, she throws an exception. The `catch` block runs, and she gets
a hug on line 17. With that hug, she is ready to see more animals on line 19. Then the `try`
statement is over, and the code proceeds on line 21. Either way, the ending is the same. The
`finally` block is executed, and execution continues after the `try` statement. <br />

&emsp;&emsp;
The exam will try to trick you with missing clauses or clauses in the wrong order. Do you
see why the following do or do not compile?

```java
25: try { // DOES NOT COMPILE
26:     fall();
27: } finally {
28:     System.out.println("all better");
29: } catch (Exception e) {
30:     System.out.println("get up");
31: }
32:
33: try { // DOES NOT COMPILE
34:     fall();
35: }
36:
37: try {
38:     fall();
39: } finally {
40:     System.out.println("all better");
41: }
```

&emsp;&emsp;
The first example (lines 25–31) does not compile because the `catch` and `finally` blocks
are in the wrong order. The second example (lines 33–35) does not compile because there
must be a `catch` or `finally` block. The third example (lines 37–41) is just fine. The `catch`
block is not required if `finally` is present. <br />

&emsp;&emsp;
Most of the examples you encounter on the exam with finally are going to look contrived.
For example, you’ll get asked questions such as what this code outputs:

```java
public static void main(String[] unused) {
    StringBuilder sb = new StringBuilder();
    try {
        sb.append("t");
    } catch (Exception e) {
        sb.append("c");
    } finally {
        sb.append("f");
    }
    sb.append("a");
    System.out.print(sb.toString());
}
```

&emsp;&emsp;
The answer is `tfa`. The try block is executed. Since no exception is thrown, Java goes
straight to the `finally` block. Then the code after the try statement is run. We know that
this is a silly example, but you can expect to see examples like this on the exam. <br />

&emsp;&emsp;
There is one additional rule you should know for `finally` blocks. If a `try` statement with a
`finally` block is entered, then the `finally` block will always be executed, regardless of whether
the code completes successfully. Take a look at the following `goHome()` method. Assuming
an exception may or may not be thrown on line 14, what are the possible values that this
method could print? Also, what would the return value be in each case?

```java
12: int goHome() {
13:     try {
14:         // Optionally throw an exception here
15:         System.out.print("1");
16:         return -1;
17:     } catch (Exception e) {
18:         System.out.print("2");
19:         return -2;
20:     } finally {
21:         System.out.print("3");
22:         return -3;
23:     }
24: }
```

&emsp;&emsp;
If an exception is not thrown on line 14, then line 15 will be executed, printing 1. Before
the method returns, though, the `finally` block is executed, printing 3. If an exception is
thrown, then lines 15 and 16 will be skipped and lines 17–19 will be executed, printing 2,
followed by 3 from the `finally` block. While the first value printed may differ, the method
always prints 3 last since it’s in the `finally` block. <br />

&emsp;&emsp;
What is the return value of the `goHome()` method? In this case, it’s always -3. Because
the finally block is executed shortly before the method completes, it interrupts the
return statement from inside both the try and catch blocks. <br />

&emsp;&emsp;
For the exam, you need to remember that a finally block will always be executed. That
said, it may not complete successfully. Take a look at the following code snippet. What
would happen if info was null on line 32?

```java
31: } finally {
32:     info.printDetails();
33:     System.out.print("Exiting");
34:     return "zoo";
35: }
```

&emsp;&emsp;
If info was null, then the finally block would be executed, but it would stop on line
32 and throw a `NullPointerException`. Lines 33 and 34 would not be executed. In this
example, you see that while a finally block will always be executed, it may not finish.

> ### **System.exit()**
> There is one exception to “the finally block will always be executed” rule: Java defines a
method that you call as `System.exit()`. It takes an integer parameter that represents the
status code that is returned.
> ```java
> try {
>   System.exit(0);
> } finally {
>   System.out.print("Never going to get here"); // Not printed
> }
> ```
> `System.exit()` tells Java, “Stop. End the program right now. Do not pass Go. Do not 
collect $200.” When `System.exit()` is called in the `try` or `catch` block, the `finally` block
does not run.
