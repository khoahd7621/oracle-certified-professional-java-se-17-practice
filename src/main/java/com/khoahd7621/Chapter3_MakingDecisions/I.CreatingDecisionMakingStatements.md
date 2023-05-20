# Creating Decision-Making Statements

## 1. Statements and Blocks
As you may recall from Chapter 1, “Building Blocks,” a Java statement is a complete unit of
execution in Java, terminated with a semicolon (;). In this chapter, we introduce you to various Java control flow 
statements. Control flow statements break up the flow of execution by
using decision-making, looping, and branching, allowing the application to selectively execute particular segments of code. <br />

&nbsp;&nbsp;&nbsp;&nbsp;
These statements can be applied to single expressions as well as a block of Java code.
As described in Chapter 1, a block of code in Java is a group of zero or more statements
between balanced braces ({}) and can be used anywhere a single statement is allowed. For
example, the following two snippets are equivalent, with the first being a single expression
and the second being a block containing the same statement:
```java
// Single statement
patrons++;

// Statement inside a block
{
    patrons++;
}
```

&nbsp;&nbsp;&nbsp;&nbsp;
A statement or block often serves as the target of a decision-making statement. For
example, we can prepend the decision-making if statement to these two examples:
```java
// Single statement
if (ticketsTaken > 1)
    patrons++;

// Statement inside a block
if (ticketsTaken > 1)
{
    patrons++;
}
```

&nbsp;&nbsp;&nbsp;&nbsp;
Again, both of these code snippets are equivalent. Just remember that the target of a
decision-making statement can be a single statement or block of statements. For the rest of
the chapter, we use both forms to better prepare you for what you will see on the exam.

> **Note** <br />
> While both of the previous examples are equivalent, stylistically using
blocks is often preferred, even if the block has only one statement. The
second form has the advantage that you can quickly insert new lines of
code into the block, without modifying the surrounding structure.

## 2. The _if_ Statement
Often, we want to execute a block only under certain circumstances. The if statement  allowing our application to execute a particular
block of code if and only if a boolean expression evaluates to true at runtime.

&nbsp;&nbsp;&nbsp;&nbsp;
For example, imagine we had a function that used the hour of day, an integer value from
0 to 23, to display a message to the user:
```java
if (hourOfDay < 11)
    System.out.println("Good Morning");
```

&nbsp;&nbsp;&nbsp;&nbsp;
If the hour of the day is less than 11, then the message will be displayed. Now let’s say
we also wanted to increment some value, morningGreetingCount, every time the greeting
is printed. We could write the if statement twice, but luckily Java offers us a more natural
approach using a block:
```java
if (hourOfDay < 11) {
    System.out.println("Good Morning");
    morningGreetingCount++;
}
```
> **Watch Indentation and Braces** <br />
> One area where the exam writers will try to trip you up is if statements without braces
({}). For example, take a look at this slightly modified form of our example:
> ```java
> if (hourOfDay < 11)
>     System.out.println("Good Morning");
>     morningGreetingCount++;
> ```
> Based on the indentation, you might be inclined to think the variable
morningGreetingCount is only going to be incremented if hourOfDay is less than 11,
but that’s not what this code does. It will execute the print statement only if the condition is
met, but it will always execute the increment operation. <br />
>
> Remember that in Java, unlike some other programming languages, tabs are just
whitespace and are not evaluated as part of the execution. When you see a control flow
statement in a question, be sure to trace the open and close braces of the block, ignoring
any indentation you may come across.

## 3. The _else_ Statement
Let’s expand our example a little. What if we want to display a different message if it is 11
a.m. or later? Can we do it using only the tools we have? Of course we can!
```java
if (hourOfDay < 11) {
    System.out.println("Good Morning");
}
if (hourOfDay >= 11) {
    System.out.println("Good Afternoon");
}
```

&nbsp;&nbsp;&nbsp;&nbsp;
This seems a bit redundant, though, since we’re performing an evaluation on hourOfDay
twice. Luckily, Java offers us a more useful approach in the form of an **else** statement:
```java
if (hourOfDay < 11) {
    System.out.println("Good Morning");
} else System.out.println("Good Afternoon");
```

&nbsp;&nbsp;&nbsp;&nbsp;
Now our code is truly branching between one of the two possible options, with the
boolean evaluation happening only once. The else operator takes a statement or block of
statements, in the same manner as the if statement. Similarly, we can append additional if
statements to an else block to arrive at a more refined example:
```java
if (hourOfDay < 11) {
    System.out.println("Good Morning");
} else if (hourOfDay < 15) {
    System.out.println("Good Afternoon");
} else {
    System.out.println("Good Evening");
}
```

&nbsp;&nbsp;&nbsp;&nbsp;
In this example, the Java process will continue execution until it encounters an if statement that evaluates to true. If neither of the first two expressions is true, it will execute the
final code of the else block.

> **Verifying That the *if* Statement Evaluates to a Boolean Expression** <br />
> Another common way the exam may try to lead you astray is by providing code where the
boolean expression inside the if statement is not actually a boolean expression. For
example, take a look at the following lines of code:
> ```java
> int hourOfDay = 1;
> if (hourOfDay) { // DOES NOT COMPILE
>     ...
> }
> ```
> This statement may be valid in some other programming and scripting languages, but not
in Java, where 0 and 1 are not considered boolean values.

## 4. Shortening Code with Pattern Matching
Java 16 officially introduced pattern matching with if statements and the instanceof operator.
Pattern matching is a technique of controlling program flow that only executes a section
of code that meets certain criteria. It is used in conjunction with if statements for greater
program control.

> If pattern matching is new to you, be careful not to confuse it with the
> Java Pattern class or regular expressions (regex). While pattern matching can include the use of regular expressions 
> for filtering, they are unrelated concepts

&nbsp;&nbsp;&nbsp;&nbsp;
Pattern matching is a new tool at your disposal to reduce boilerplate in your code.
Boilerplate code is code that tends to be duplicated throughout a section of code over and
over again in a similar manner. A lot of the newer enhancements to the Java language focus
on reducing boilerplate code.

&nbsp;&nbsp;&nbsp;&nbsp;
To understand why this tool was added, consider the following code that takes a Number
instance and compares it with the value 5. If you haven’t seen Number or Integer, you just
need to know that Integer inherits from Number for now.

```java
void compareIntegers(Number number) {
    if (number instanceof Integer) {
        Integer data = (Integer) number;
        System.out.print(data.compareTo(5));
    }
}
```

&nbsp;&nbsp;&nbsp;&nbsp;
The cast is needed since the compareTo() method is defined on Integer, but not
on Number.

&nbsp;&nbsp;&nbsp;&nbsp;
Code that first checks if a variable is of a particular type and then immediately casts it to
that type is extremely common in the Java world. It’s so common that the authors of Java
decided to implement a shorter syntax for it:

```java
void compareIntegers(Number number) {
    if (number instanceof Integer data) {
        System.out.print(data.compareTo(5));
    }
}
```

&nbsp;&nbsp;&nbsp;&nbsp;
The variable data in this example is referred to as the pattern variable. Notice that this
code also avoids any potential ClassCastException because the cast operation is executed
only if the implicit instanceof operator returns true.

> **Reassigning Pattern Variables** <br />
> While possible, it is a bad practice to reassign a pattern variable since doing so can lead to
ambiguity about what is and is not in scope.
> ```java
> if (number instanceof Integer data) {
>     data = 10;
> }
> ```
> The reassignment can be prevented with a final modifier, but it is better not to reassign
the variable at all.
> ```java
> if (number instanceof final Integer data) {
>     data = 10; // DOES NOT COMPILE
> }
> ```

### a. Pattern Variables and Expressions
Pattern matching includes expressions that can be used to filter data out, such as in the following example:
```java
void printIntegersGreaterThan5(Number number) {
    if (number instanceof Integer data && data.compareTo(5) > 0)
        System.out.print(data);
}
```

&nbsp;&nbsp;&nbsp;&nbsp;
We can apply a number of filters, or patterns, so that the if statement is executed only in
specific circumstances. Notice that we’re using the pattern variable in an expression in the
same line in which it is declared.

### b. Subtypes
The type of the pattern variable must be a subtype of the variable on the left side of the
expression. It also cannot be the same type. This rule does not exist for traditional instanceof
operator expressions, though. Consider the following two uses of the instanceof operator:
```java
Integer value = 123;
if (value instanceof Integer) {}
if (value instanceof Integer data) {} // DOES NOT COMPILE
```

&nbsp;&nbsp;&nbsp;&nbsp;
While the second line compiles, the last line does not compile because pattern matching
requires that the pattern variable type Integer be a strict subtype of Integer.

> **Limitations of Subtype Enforcement** <br />
> The compiler has some limitations on enforcing pattern matching types when we mix
classes and interfaces, which will make more sense after you read Chapter 7, “Beyond
Classes.” For example, given the non-final class Number and interface List, this does
compile even though they are unrelated:
> ```java
> Number value = 123;
> if (value instanceof List) {}
> if (value instanceof List data) {}
> ```

### c. Flow Scoping
The compiler applies flow scoping when working with pattern matching. Flow scoping
means the variable is only in scope when the compiler can definitively determine its type.
Flow scoping is unlike any other type of scoping in that it is not strictly hierarchical like instance, class, or local scoping. It is determined by the compiler based on the branching and
flow of the program.

&nbsp;&nbsp;&nbsp;&nbsp;
Given this information, can you see why the following does not compile?

```java
void printIntegersOrNumbersGreaterThan5(Number number) {
    if (number instanceof Integer data || data.compareTo(5) > 0)
        System.out.print(data);
}
```

&nbsp;&nbsp;&nbsp;&nbsp;
If the input does not inherit Integer, the data variable is undefined. Since the compiler
cannot guarantee that data is an instance of Integer, data is not in scope, and the code
does not compile.

&nbsp;&nbsp;&nbsp;&nbsp;
What about this example?

```java
void printIntegerTwice(Number number) {
    if (number instanceof Integer data)
        System.out.print(data.intValue());
    System.out.print(data.intValue()); // DOES NOT COMPILE
}
```

&nbsp;&nbsp;&nbsp;&nbsp;
Since the input might not have inherited Integer, data is no longer in scope after the
if statement. Oh, so you might be thinking that the pattern variable is then only in scope
inside the if statement, right? Well, not exactly! Consider the following example that
does compile:

```java
void printOnlyIntegers(Number number) {
    if (!(number instanceof Integer data))
        return;
    System.out.print(data.intValue());
}
```

&nbsp;&nbsp;&nbsp;&nbsp;
It might surprise you to learn this code does compile. Eek! What is going on here? The
method returns if the input does not inherit Integer. This means that when the last line of
the method is reached, the input must inherit Integer, and therefore data stays in scope
even after the if statement ends.

> **Flow Scoping and else Branches** <br />
> If the last code sample confuses you, don’t worry: you’re not alone! Another way to think
about it is to rewrite the logic to something equivalent that uses an else statement:
> ```java
> void printOnlyIntegers(Number number) { 
>     if (!(number instanceof Integer data))
>         return;
>     else
>         System.out.print(data.intValue());
> }
> ```
> We can now go one step further and reverse the if and else branches by inverting the
boolean expression:
> ```java
> void printOnlyIntegers(Number number) { 
>     if (number instanceof Integer data)
>         System.out.print(data.intValue());
>     else
>         return;
> }
> ```
> Our new code is equivalent to our original and better demonstrates how the compiler was
able to determine that data was in scope only when number is an Integer.

&nbsp;&nbsp;&nbsp;&nbsp;
Make sure you understand the way flow scoping works. In particular, it is possible to use
a pattern variable outside of the if statement, but only when the compiler can definitively
determine its type.
