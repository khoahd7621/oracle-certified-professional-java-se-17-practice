# Writing *while* loop
A common practice when writing software is doing the same task some number of times.
You could use the decision structures we have presented so far to accomplish this, but that’s
going to be a pretty long chain of if or else statements, especially if you have to execute the
same thing 100 times or more.

&emsp;&emsp;
Enter loops! A loop is a repetitive control structure that can execute a statement of code
multiple times in succession. By using variables that can be assigned new values, each repetition of the statement 
may be different. The following loop executes exactly 10 times:

```java
int counter = 0;
while (counter < 10) {
    double price = counter * 10;
    System.out.println(price);
    counter++;
}
```

## 1. The *while* Statement
The simplest repetitive control structure in Java is the while statement, it has a termination 
condition, implemented as a boolean expression, that will continue as long as the expression evaluates to true.

```java
while (booleanExpression) {
    // Body
}
```

&emsp;&emsp;
A while loop is similar to an if statement in that it is composed
of a boolean expression and a statement, or a block of statements. During execution, the
boolean expression is evaluated before each iteration of the loop and exits if the evaluation
returns false.

&emsp;&emsp;
One thing to remember is that a while loop may terminate after its first evaluation of
the boolean expression. For example, how many times is Not full! printed in the following example?

```java
int full = 5;
while (full < 5) {
    System.out.println("Not full!");
    full++;
}
```

&emsp;&emsp;
The answer? Zero! On the first iteration of the loop, the condition is reached, and the
loop exits. This is why while loops are often used in places where you expect zero or more
executions of the loop. Simply put, the body of the loop may not execute at all or may
execute many times.

## 2. The *do/while* Statement
The second form a while loop can take is called a do/while loop, which, like a while loop,
is a repetition control structure with a termination condition and statement, or a block of
statements

```java
do {
    // Body
} while (booleanExpression);
```

&emsp;&emsp;
Unlike a while loop, though, a do/while loop guarantees that the statement or block
will be executed at least once. For example, what is the output of the following statements?
```java
int lizard = 0;
do {
    lizard++;
} while(false);
System.out.println(lizard); // 1
```

&emsp;&emsp;
Java will execute the statement block first and then check the loop condition. Even
though the loop exits right away, the statement block is still executed once, and the
program prints 1.

## 3. Infinite Loops
The single most important thing you should be aware of when you are using any repetition
control structures is to make sure they always terminate! Failure to terminate a loop can
lead to numerous problems in practice, including overflow exceptions, memory leaks, slow
performance, and even bad data. Let’s take a look at an example:

```java
int pen = 2;
int pigs = 5;
while(pen < 10)
    pigs++;
```

&emsp;&emsp;
You may notice one glaring problem with this statement: it will never end. The variable
pen is never modified, so the expression (pen < 10) will always evaluate to true. The
result is that the loop will never end, creating what is commonly referred to as an infinite
loop. An infinite loop is a loop whose termination condition is never reached during runtime.

&emsp;&emsp;
Anytime you write a loop, you should examine it to determine whether the termination
condition is always eventually met under some condition. For example, a loop in which no
variables are changing between two executions suggests that the termination condition may
not be met. The loop variables should always be moving in a particular direction.

&emsp;&emsp;
In other words, make sure the loop condition, or the variables the condition is dependent
on, are changing between executions. Then, ensure that the termination condition will be
eventually reached in all circumstances. As you learn in the last section of this chapter, a loop
may also exit under other conditions, such as a break statement.

