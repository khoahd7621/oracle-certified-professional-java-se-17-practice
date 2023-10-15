# Assigning Values
To be successful with the assignment operators, you should be fluent in understanding how the compiler handles numeric promotion and when casting is required. Being able to spot these issues is critical to passing the exam, as assignment operators appear in nearly every question with a code snippet.

## 1. Assignment Operator
An assignment operator is a binary operator that modifies, or assigns, the variable on the
left side of the operator with the result of the value on the right side of the equation. Unlike
most other Java operators, the assignment operator is evaluated from right to left.
```java
int x = 1;
```

Java will automatically promote from smaller to larger data types, as you saw in the
previous section on arithmetic operators, but it will throw a compiler exception if it detects
that you are trying to convert from larger to smaller data types without casting.
```java
int x = 2.0; // DOES NOT COMPILE
```

## 2. Casting Values
Casting is a unary operation where one data type is explicitly
interpreted as another data type. Casting is optional and unnecessary when converting to a
larger or widening data type, but it is required when converting to a smaller or narrowing
data type. Without casting, the compiler will generate an error when trying to put a larger
data type inside a smaller one.
```java
int fur = (int) 5;
int hair = (short) 2;
String type = (String) "Bird";
short tail = (short)(4 + 10);
long feathers = 10 (long); // DOES NOT COMPILE
```

On the one hand, it is convenient that the compiler automatically casts smaller data
types to larger ones. On the other hand, it makes for great exam questions when they do the
opposite to see whether you are paying attention
```java
float egg = 2.0 / 9; // DOES NOT COMPILE
int tadpole = (int)5 * 2L; // DOES NOT COMPILE
short frog = 3 - 2.0; // DOES NOT COMPILE
```

## 3. Reviewing Primitive Assignments
See if you can figure out why each of the following lines does not compile:
```java
int fish = 1.0; // DOES NOT COMPILE
short bird = 1921222; // DOES NOT COMPILE
int mammal = 9f; // DOES NOT COMPILE
long reptile = 192_301_398_193_810_323; // DOES NOT COMPILE
```
- The first statement does not compile because you are trying to assign a double 1.0
to an integer value. Even though the value is a mathematic integer, by adding .0, you’re
instructing the compiler to treat it as a double. 
- The second statement does not compile because the literal value 1921222 is outside the range of short, and the compiler detects this. 
- The third statement does not compile because the f added to the end of the number
instructs the compiler to treat the number as a floating-point value, but the assignment is to an int. 
- Finally, the last statement does not compile because Java interprets the literal as an
int and notices that the value is larger than int allows. The literal would need a postfix L
or l to be considered a long.

## 4. Applying Casting
We can fix three of the previous examples by casting the results to a smaller data type.
Remember, casting primitives is required any time you are going from a larger numerical
data type to a smaller numerical data type, or converting from a floating-point number to an
integral value.
```java
int fish = (int) 1.0;
short bird = (short) 1921222; // Stored as 20678
int mammal = (int) 9f;
```
What about applying casting to the last example?
```java
long reptile = (long) 192301398193810323; // DOES NOT COMPILE
```
This still does not compile because the value is first interpreted as an int by the compiler
and is out of range. The following fixes this code without requiring casting:
`long reptile = 192301398193810323L;`

> ### Overflow and Underflow
> - The expressions in the previous example now compile, although there’s a cost. The second
value, 1,921,222, is too large to be stored as a short, so numeric overflow occurs, and it
becomes 20,678. 
> - Overflow is when a number is so large that it will no longer fit within the
data type, so the system “*wraps around*” to the lowest negative value and counts up from
there, similar to how modulus arithmetic works. There’s also an analogous underflow, when
the number is too low to fit in the data type, such as storing -200 in a byte field.
> - For example, the following statement outputs a negative number:
`System.out.print(2147483647 + 1); // -2147483648`
> - Since 2147483647 is the maximum int value, adding any strictly positive value to it will
cause it to wrap to the smallest negative number.

## 5. Casting Values vs. Variables
Revisiting our third numeric promotional rule, the compiler doesn’t require casting when
working with literal values that fit into the data type. Consider these examples:
```java
byte hat = 1;
byte gloves = 7 * 10;
short scarf = 5;
short boots = 2 + 1;
```
All of these statements compile without issue. On the other hand, neither of these statements compiles:
```java
short boots = 2 + hat; // DOES NOT COMPILE
byte gloves = 7 * 100; // DOES NOT COMPILE
```
- The first statement does not compile because hat is a variable, not a value, and both
operands are automatically promoted to int. 
- When working with values, the compiler
had enough information to determine the writer’s intent. 
When working with variables, though, there is ambiguity about how to proceed, so the compiler reports an error. 
- The second expression does not compile because 700 triggers an overflow for byte, which has a
maximum value of 127.

## 6. Compound Assignment Operators
Besides the simple assignment operator (=), Java supports numerous compound assignment
operators.

|Operator | Example | Description |
|-------|--------|-----------|
|+= | x += 5 | x = x + 5 |
|-= | x -= 5 | x = x - 5 |
|*= | x *= 5 | x = x * 5 |
|/= | x /= 5 | x = x / 5 |

Compound operators are really just glorified forms of the simple assignment operator,
with a built-in arithmetic or logical operation that applies the left and right sides of the
statement and stores the resulting value in the variable on the left side of the statement.

```java
int camel = 2, giraffe = 3;
camel = camel * giraffe; // Simple assignment operator
camel *= giraffe; // Compound assignment operator
```
The left side of the compound operator can be applied only to a variable that is already
defined and cannot be used to declare a new variable. In this example, if camel were not
already defined, the expression camel *= giraffe would not compile. <br />

Compound operators are useful for more than just shorthand—they can also save you
from having to explicitly cast a value. For example, consider the following. Can you figure
out why the last line does not compile?
```java
long goat = 10;
int sheep = 5;
sheep = sheep * goat; // DOES NOT COMPILE
```
We are
trying to assign a long value to an int variable. This last line could be fixed with an explicit
cast to (int), but there’s a better way using the compound assignment operator:
```java
long goat = 10;
int sheep = 5;
sheep *= goat;
```
The compound operator will first cast sheep to a long, apply the multiplication of two
long values, and then cast the result to an int. Unlike the previous example, in which the
compiler reported an error, the compiler will automatically cast the resulting value to the
data type of the value on the left side of the compound operator.

## 7. Return Value of Assignment Operators
One final thing to know about assignment operators is that the result of an assignment is an
expression in and of itself equal to the value of the assignment. For example, the following
snippet of code is perfectly valid, if a little odd-looking:
```java
long wolf = 5;
long coyote = (wolf = 3);
System.out.println(wolf); // 3
System.out.println(coyote); // 3
```
The key here is that (wolf = 3) does two things. First, it sets the value of the variable
wolf to be 3. Second, it returns a value of the assignment, which is also 3.<br />

The exam creators are fond of inserting the assignment operator (=) in the middle of an
expression and using the value of the assignment as part of a more complex expression. For
example, don’t be surprised if you see an if statement on the exam similar to the following:
```java
boolean healthy = false;
if (healthy = true)
    System.out.print("Good!");
```
While this may look like a test if healthy is true, it’s actually assigning healthy a
value of true. The result of the assignment is the value of the assignment, which is true,
resulting in this snippet printing Good!.
