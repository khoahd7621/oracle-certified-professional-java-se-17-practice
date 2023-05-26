# Cosntructing *for* Loops

Even though while and do/while statements are quite powerful, some tasks are so common
in writing software that special types of loops were created—for example, iterating over a
statement exactly 10 times or iterating over a list of names. You could easily accomplish
these tasks with various while loops that you’ve seen so far, but they usually require a lot of
boilerplate code. Wouldn’t it be great if there was a looping structure that could do the same
thing in a single line of code?

&emsp;&emsp;
With that, we present the most convenient repetition control structure, for loops. There
are two types of for loops, although both use the same for keyword. The first is referred to as
the basic for loop, and the second is often called the enhanced for loop. For clarity, we refer
to them as the for loop and the for-each loop, respectively, throughout the book.

## 1. The *for* Loop
A basic for loop has the same conditional boolean expression and statement, or block of
statements, as the while loops, as well as two new sections: an initialization block and an
update statement.

```java
for (initialization; booleanExpression; updateStatement) {
    // Body
}
```

1. Initialization statement executes
2. If booleanExpression is true, continue; else exit loop
3. Body executes
4. Execute updateStatement
5. Return to Step 2

&emsp;&emsp;
Although structure above might seem a little confusing and almost arbitrary at first, the organization of the components 
and flow allow us to create extremely powerful statements in
a single line that otherwise would take multiple lines with a while loop. Each of the three
sections is separated by a semicolon. In addition, the initialization and update sections may
contain multiple statements, separated by commas.

&emsp;&emsp;
Variables declared in the initialization block of a for loop have limited scope and
are accessible only within the for loop. Be wary of any exam questions in which a variable is declared within the initialization block of a for loop and then read outside the
loop. For example, this code does not compile because the loop variable i is referenced
outside the loop:

```java
for (int i = 0; i < 10; i++)
    System.out.println("Value is: " + i);
System.out.println(i); // DOES NOT COMPILE
```

&emsp;&emsp;
Alternatively, variables declared before the for loop and assigned a value in the initialization block may be used outside the for loop because their scope precedes the creation of
the for loop.

```java
int i;
for (i = 0; i < 10; i++)
    System.out.println("Value is: " + i);
System.out.println(i);
```

> **Why *i* in for Loops?**
> 
> You may notice it is common practice to name a for loop variable i. Long before Java
existed, programmers started using i as short for increment variable, and the practice
exists today, even though many of those programming languages no longer do! For double or triple loops, where i is already used, the next letters in the alphabet, j and k, are
often used.

## 2. Printing Elements in Reverse
Let’s say you wanted to print the same first five numbers from zero as we did in the previous
section, but this time in reverse order. The goal then is to print 4 3 2 1 0.

&emsp;&emsp;
How would you do that? An initial implementation might look like the following:

```java
for (var counter = 5; counter > 0; counter--) {
    System.out.print(counter + " ");
}
```

&emsp;&emsp;
While this snippet does output five distinct values, and it resembles our first for loop
example, it does not output the same five values. Instead, this is the output:
5 4 3 2 1

&emsp;&emsp;
Wait, that’s not what we wanted! We wanted 4 3 2 1 0. It starts with 5, because that is
the first value assigned to it. Let’s fix that by starting with 4 instead:

```java
for (var counter = 4; counter > 0; counter--) {
    System.out.print(counter + " ");
}
```

&emsp;&emsp;
What does this print now? It prints the following: 4 3 2 1

&emsp;&emsp;
So close! The problem is that it ends with 1, not 0, because we told it to exit as soon as
the value was not strictly greater than 0. If we want to print the same 0 through 4 as our
first example, we need to update the termination condition, like this:

```java
for (var counter = 4; counter >= 0; counter--) {
    System.out.print(counter + " ");
}
```

&emsp;&emsp;
Finally! We have code that now prints 4 3 2 1 0 and matches the reverse of our
for loop example in the previous section. We could have instead used counter > -1
as the loop termination condition in this example, although counter >= 0 tends to be
more readable.

> **Note:**
> 
> For the exam, you are going to have to know how to read forward and
backward for loops. When you see a for loop on the exam, pay close
attention to the loop variable and operations if the decrement
operator, --, is used. While incrementing from 0 in a for loop is often
straightforward, decrementing tends to be less intuitive. In fact, if you
do see a for loop with a decrement operator on the exam, you should
assume they are trying to test your knowledge of loop operations.

## 3. Working with for Loops
Although most for loops you are likely to encounter in your professional development
experience will be well defined and similar to the previous examples, there are a number of
variations and edge cases you could see on the exam. You should familiarize yourself with
the following five examples; variations of these are likely to be seen on the exam.

&emsp;&emsp;
Let’s tackle some examples for illustrative purposes:

### &emsp; 1. Creating an Infinite Loop
```java
for( ; ; )
    System.out.println("Hello World");
```

Although this for loop may look like it does not compile, it will in fact compile and run
without issue. It is actually an infinite loop that will print the same statement repeatedly.
This example reinforces the fact that the components of the for loop are each optional.
Note that the semicolons separating the three sections are required, as for( ) without
any semicolons will not compile.

### &emsp; 2. Adding Multiple Terms to the *for* Statement
```java
int x = 0;
for (long y = 0, z = 4; x < 5 && y < 10; x++, y++) {
    System.out.print(y + " "); 
}
System.out.print(x + " ");
```

This code demonstrates three variations of the for loop you may not have seen.
First, you can declare a variable, such as x in this example, before the loop begins and
use it after it completes. Second, your initialization block, boolean expression, and
update statements can include extra variables that may or may not reference each
other. For example, z is defined in the initialization block and is never used. Finally,
the update statement can modify multiple variables. This code will print the following
when executed: *0 1 2 3 4 5*

### &emsp; 3. Re-declaring a Variable in the Initialization Block
```java
int x = 0;
for(int x = 4; x < 5; x++) // DOES NOT COMPILE
    System.out.print(x + " ");
```

This example looks similar to the previous one, but it does not compile because of the
initialization block. The difference is that x is repeated in the initialization block after
already being declared before the loop, resulting in the compiler stopping because of
a duplicate variable declaration. We can fix this loop by removing the declaration of x
from the for loop as follows:

```java
int x = 0;
for(x = 0; x < 5; x++)
    System.out.print(x + " ");
```
Note that this variation will now compile because the initialization block simply assigns
a value to x and does not declare it.

### &emsp; 4. Using Incompatible Data Types in the Initialization Block
```java
int x = 0;
for (long y = 0, int z = 4; x < 5; x++) // DOES NOT COMPILE
    System.out.print(y + " ");
```

Like the third example, this code will not compile, although this time for a different
reason. The variables in the initialization block must all be of the same type. In the
multiple-terms example, y and z were both long, so the code compiled without issue;
but in this example, they have different types, so the code will not compile.

### &emsp; 5. Using Loop Variables Outside the Loop
```java
for (long y = 0, x = 4; x < 5 && y < 10; x++, y++)
    System.out.print(y + " ");
System.out.print(x); // DOES NOT COMPILE
```

We covered this already at the start of this section, but it is so important for passing the
exam that we discuss it again here. If you notice, x is defined in the initialization block
of the loop and then used after the loop terminates. Since x was only scoped for the
loop, using it outside the loop will cause a compiler error.

> **Modifying Loop Variables**: <br />
> As a general rule, it is considered a poor coding practice to modify loop variables due to the
unpredictability of the result, such as in the following examples:
> ```java
> for (int i = 0; i < 10; i++)
>     i = 0;
> for (int j = 1; j < 10; j++)
>     j++;
> ```

## 4. The for-each loop
The *for-each* loop is a specialized structure designed to iterate over arrays and various
Collections Framework classes, as presented below:

```java
for (datatype instance: collection) {
    // Body
}
```

&emsp;&emsp;
The for-each loop declaration is composed of an initialization section and an object to be
iterated over. The right side of the for-each loop must be one of the following:
- A built-in Java array
- An object whose type implements java.lang.Iterable

&emsp;&emsp;
We cover what implements means in Chapter 7, but for now you just need to know
that the right side must be an array or collection of items, such as a List or a Set. For the
exam, you should know that this does not include all of the Collections Framework classes or interfaces, but only those that implement or extend that Collection interface. For
example, Map is not supported in a for-each loop, although Map does include methods that
return Collection instances.

&emsp;&emsp;
The left side of the for-each loop must include a declaration for an instance of a variable
whose type is compatible with the type of the array or collection on the right side of the
statement. On each iteration of the loop, the named variable on the left side of the statement
is assigned a new value from the array or collection on the right side of the statement.

&emsp;&emsp;
Compare these two methods that both print the values of an array, one using a traditional
for loop and the other using a for-each loop:

```java
public void printNames(String[] names) {
    for (int counter = 0; counter < names.length; counter++)
        System.out.println(names[counter]);
} 

public void printNames(String[] names) {
    for (var name : names)
        System.out.println(name);
}
```

&emsp;&emsp;
The for-each loop is a lot shorter, isn’t it? We no longer have a counter loop variable
that we need to create, increment, and monitor. Like using a for loop in place of a while
loop, for-each loops are meant to reduce boilerplate code, making code easier to read/write,
and freeing you to focus on the parts of your code that really matter.

&emsp;&emsp;
We can also use a for-each loop on a List, since it implements Iterable.

```java
public void printNames(List<String> names) {
    for (var name : names)
        System.out.println(name);
}
```

&emsp;&emsp;
We cover generics in detail in Chapter 9, “Collections and Generics.” For this chapter, you
just need to know that on each iteration, a for-each loop assigns a variable with the same
type as the generic argument. In this case, name is of type String.

&emsp;&emsp;
So far, so good. What about the following examples?

```java
String birds = "Jay";
for (String bird : birds) // DOES NOT COMPILE
    System.out.print(bird + " "); 

String[] sloths = new String[3];
for (int sloth : sloths) // DOES NOT COMPILE
    System.out.print(sloth + " ");
```

&emsp;&emsp;
The first for-each loop does not compile because String cannot be used on the right side
of the statement. While a String may represent a list of characters, it has to actually be an
array or implement Iterable. 

&emsp;&emsp;
The second example does not compile because the loop type
on the left side of the statement is int and doesn’t match the expected type of String.


