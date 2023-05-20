# Binary Operators
Binary operators are by far the most common operators in the Java language. 
They can be used to perform mathematical operations on variables, create 
logical expressions, and perform basic variable assignments. Binary 
operators are often combined in complex expressions with other binary 
operators; therefore, operator precedence is very important in evaluating 
expressions containing binary operators.

## 1. Arithmetic Operators
| Operator      | Example | Description                                                          |
| ----------- | ----------- |----------------------------------------------------------------------|
| Addition | a + b | Adds two numeric values                                              |
| Subtraction | c - d | Subtracts two numeric values                                         |
| Multiplication | e * f | Multiplies two numeric values                                        |
| Division | g / h | Divides one numeric value by another                                 |
| Modulus | i % j | Returns the remainder after division of one numeric value by another |

- Arithmetic operators also include
  the unary operators, ++ and --, which we covered already. As you may have 
  noticed, the multiplicative operators (*, /, %) have a higher 
  order of precedence than the additive operators (+, -).

> **_NOTE:_** All of the arithmetic operators may be applied to any Java primitives,
with the exception of boolean. Furthermore, only the addition operators + and += may be applied to String values, which results in String
concatenation. You will learn more about these operators and how they
apply to String values in Chapter 4, “Core APIs.”

## 2. Adding Parentheses
You can change the order of operation explicitly by wrapping parentheses around the sections you want evaluated first.

### a. Changing the Order of Operation
```java
int price = 2 * ((5 + 3) * 4 - 8);
```

> **_NOTE:_** When you encounter code in your professional career in which you are
not sure about the order of operation, feel free to add optional parentheses. While often not required, they can improve readability, especially
as you’ll see with ternary operators.

### b. Verifying Parentheses Syntax
When working with parentheses, you need to make sure they are always valid and balanced.

```java
long pigeon = 1 + ((3 * 5) / 3; // DOES NOT COMPILE
int blueJay = (9 + 2) + 3) / (2 * 4; // DOES NOT COMPILE
short robin = 3 + [(4 * 2) + 4]; // DOES NOT COMPILE
```

> **_NOTE:_** The compiler will not allow you to compile code that contains unbalanced parentheses. Unlike some other programming languages,
does not allow brackets, [], to be used in place of parentheses.

## 3. Division and Modulus Operators
- The modulus operator, sometimes called the remainder operator, is simply the remainder when two numbers are
  divided. For example, 9 divided by 3 divides evenly and has no remainder; therefore, the
  result of 9 % 3 is 0. On the other hand, 11 divided by 3 does not divide evenly; therefore,
  the result of 11 % 3 is 2.

- Be sure to understand the difference between arithmetic division and modulus. For integer
  values, division results in the floor value of the nearest integer that fulfills the operation,
  whereas modulus is the remainder value. If you hear the phrase floor value, it just means the
  value without anything after the decimal point. For example, the floor value is 4 for each of
  the values 4.0, 4.5, and 4.9999999. Unlike rounding, which we’ll cover in Chapter 4, you
  just take the value before the decimal point, regardless of what is after the decimal point.

> **_NOTE:_** The modulus operation is not limited to positive integer values in Java; it
may also be applied to negative integers and floating-point numbers. For
example, if the divisor is 5, then the modulus value of a negative number
is between -4 and 0.

## 4. Numeric Promotion
Now that you understand the basics of arithmetic operators, it is vital to talk about primitive 
numeric promotion, as Java may do things that seem unusual to you at first. As you known, each primitive 
numeric type has a bit-length. You don’t need to know the exact size. For example, you should know that a 
long takes up more space than an int, which in turn takes up more space than a short, and so on.

You need to memorize certain rules that Java will follow when applying operators to
data types:

Numeric Promotion Rules
: 1. If two values have different data types, Java will automatically promote one of the
   values to the larger of the two data types.
: 2. If one of the values is integral and the other is floating-point, Java will automatically
   promote the integral value to the floating-point value’s data type.
: 3. Smaller data types, namely, byte, short, and char, are first promoted to int any time
   they’re used with a Java binary arithmetic operator with a variable (as opposed to a
   value), even if neither of the operands is int.
: 4. After all promotion has occurred and the operands have the same data type, the resulting value will have the same data type as its promoted operands.

The last two rules are the ones most people have trouble with and the ones likely to trip
you up on the exam. For the third rule, note that unary operators are excluded from this
rule. For example, applying ++ to a short value results in a short value.

Let’s tackle some examples for illustrative purposes:
1. What is the data type of ```x * y```?
```java 
int x = 1;
long y = 33;
var z = x * y;
```
In this case, we follow the first rule. Since one of the values is int and the other is long,
and long is larger than int, the int value x is first promoted to a long. The result z is
then a long value.
2. What is the data type of ```x + y```?
```java 
double x = 39.21;
float y = 2.1;
var z = x + y;
```
This is actually a trick question, as the second line does not compile! As you may
remember from Chapter 1, floating-point literals are assumed to be double unless postfixed with an f, as in 2.1f. 
If the value of y was set properly to 2.1f, then the promotion would be similar to the previous example, with both operands 
being promoted to a double, and the result z would be a double value.
3. What is the data type of ```x * y```?
```java 
short x = 10;
short y = 3;
var z = x * y;
```
On the last line, we must apply the third rule: that x and y will both be promoted to
int before the binary multiplication operation, resulting in an output of type int. If
you were to try to assign the value to a short variable z without casting, then the code
would not compile. Pay close attention to the fact that the resulting output is not a
short, as we’ll come back to this example in the upcoming “Assigning Values” section.
4. What is the data type of ```w * x / y```?
```java 
short w = 14;
float x = 13;
double y = 30;
var z = w * x / y;
```
In this case, we must apply all the rules. First, w will automatically be promoted to
int solely because it is a short and is being used in an arithmetic binary operation. The
promoted w value will then be automatically promoted to a float so that it can be multiplied with x. The result of w * x will then be automatically promoted to a double so
that it can be divided by y, resulting in a double value.

> When working with arithmetic operators in Java, you should always be aware of the data
type of variables, intermediate values, and resulting values. You should apply operator precedence and parentheses and work outward, promoting data types along the way. In the next
section, we’ll discuss the intricacies of assigning these values to variables of a particular type.