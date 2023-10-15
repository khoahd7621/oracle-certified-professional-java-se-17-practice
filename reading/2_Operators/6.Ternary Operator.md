# Making Decisions with the Ternary Operator

The final operator you should be familiar with for the exam is the conditional operator,
? :, otherwise known as the ternary operator. It is notable in that it is the only operator
that takes three operands. The ternary operator has the following form: <br />

`booleanExpression ? expression1 : expression2` <br />

&nbsp;&nbsp;&nbsp;&nbsp;
The first operand must be a boolean expression, and the second and third operands can
be any expression that returns a value. The ternary operation is really a condensed form of a
combined if and else statement that returns a value. We cover if/else statements in a lot
more detail in Chapter 3, so for now we just use simple examples.

&nbsp;&nbsp;&nbsp;&nbsp;
For example, consider the following code snippet that calculates the food amount
for an owl:
```java
int owl = 5;
int food;
if (owl < 2) {
    food = 3;
} else {
    food = 4;
}
System.out.println(food); // 4
```
&nbsp;&nbsp;&nbsp;&nbsp;
Compare the previous code snippet with the following ternary operator code snippet:
```java
int owl = 5;
int food = owl < 2 ? 3 : 4;
System.out.println(food); // 4
```

&nbsp;&nbsp;&nbsp;&nbsp;
These two code snippets are equivalent. Note that it is often helpful for readability to add
parentheses around the expressions in ternary operations, although doing so is certainly not
required. It is especially helpful when multiple ternary operators are used together, though.
Consider the following two equivalent expressions:

```java
int food1 = owl < 4 ? owl > 2 ? 3 : 4 : 5;
int food2 = (owl < 4 ? ((owl > 2) ? 3 : 4) : 5);
```

&nbsp;&nbsp;&nbsp;&nbsp;
While they are equivalent, we find the second statement far more readable. That said, it is
possible the exam could use multiple ternary operators in a single line.

&nbsp;&nbsp;&nbsp;&nbsp;
For the exam, you should know that there is no requirement that second and third
expressions in ternary operations have the same data types, although it does come into play
when combined with the assignment operator. Compare the two statements following the
variable declaration:

```java
int stripes = 7;

System.out.print((stripes > 5) ? 21 : "Zebra");

int animal = (stripes < 9) ? 3 : "Horse"; // DOES NOT COMPILE
```

&nbsp;&nbsp;&nbsp;&nbsp;
Both expressions evaluate similar boolean values and return an int and a String,
although only the first one will compile. System.out.print() does not care that the
expressions are completely different types, because it can convert both to Object values and
call toString() on them. On the other hand, the compiler does know that "Horse" is of
the wrong data type and cannot be assigned to an int; therefore, it does not allow the code
to be compiled.

> **Ternary Expression and Unperformed Side Effects** <br />
> As we saw with the conditional operators, a ternary expression can contain an unperformed side effect, as only one of the expressions on the right side will be evaluated at runtime. Let’s illustrate this principle with the following example:
> ```java
> int sheep = 1;
> int zzz = 1;
> int sleep = zzz < 10 ? sheep++ : zzz++;
> System.out.print(sheep + "," + zzz); // 2,1
> ```
> Notice that since the left-hand boolean expression was true, only sheep was incremented. Contrast the preceding example with the following modification
> ```java
> int sheep = 1;
> int zzz = 1;
> int sleep = sheep>=10 ? sheep++ : zzz++;
> System.out.print(sheep + "," + zzz); // 1,2
> ```
> Now that the left-hand boolean expression evaluates to false, only zzz is incremented.
In this manner, we see how the changes in a ternary operator may not be applied if the
particular expression is not used. <br />
> 
> For the exam, be wary of any question that includes a ternary expression in which a variable
is modified in one of the expressions on the right-hand side.