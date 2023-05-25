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
