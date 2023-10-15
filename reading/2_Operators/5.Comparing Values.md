# Comparing Values

The last set of binary operators revolves around comparing values. They can be used to 
check if two values are the same, check if one numeric value is less than or greater than
another, and perform Boolean arithmetic. Chances are, you have used many of the operators
in this section in your development experience.

## 1. Equality Operators
&nbsp;&nbsp;&nbsp;&nbsp;
Determining equality in Java can be a nontrivial endeavor as there’s a semantic difference
between “two objects are the same” and “two objects are equivalent.” It is further complicated by the fact that for numeric and boolean primitives, there is no such distinction.

| Operator   | Example | Apply to primitives                                       | Apply to objects                                                |
|:-----------|:--------|:----------------------------------------------------------|:----------------------------------------------------------------|
| Equality   | a == 10 | Returns true if the two values represent the same value   | Returns true if the two values reference the same object        |
| Inequality | a != 10 | Returns true if the two values represent different values | Returns true if the two values do not reference the same object |

&nbsp;&nbsp;&nbsp;&nbsp;
The equality operator can be applied to numeric values, boolean values, and objects
(including String and null). When applying the equality operator, you cannot mix these
types. Each of the following results in a compiler error:
```java
boolean monkey = true == 3; // DOES NOT COMPILE
boolean ape = false != "Grape"; // DOES NOT COMPILE
boolean gorilla = 10.2 == "Koko"; // DOES NOT COMPILE
```

&nbsp;&nbsp;&nbsp;&nbsp;
Pay close attention to the data types when you see an equality operator on the exam. As
mentioned in the previous section, the exam creators also have a habit of mixing assignment
operators and equality operators.
```java
boolean bear = false;
boolean polar = (bear = true);
System.out.println(polar); // true
```

&nbsp;&nbsp;&nbsp;&nbsp;
At first glance, you might think the output should be false, and if the expression were
(bear == true), then you would be correct. In this example, though, the expression is
assigning the value of true to bear, and as you saw in the section on assignment operators,
the assignment itself has the value of the assignment. Therefore, polar is also assigned a
value of true, and the output is true. <br />

&nbsp;&nbsp;&nbsp;&nbsp;
For object comparison, the equality operator is applied to the references to the objects,
not the objects they point to. Two references are equal if and only if they point to the same
object or both point to null. Let’s take a look at some examples:
```java
var monday = new File("schedule.txt");
var tuesday = new File("schedule.txt");
var wednesday = tuesday;
System.out.println(monday == tuesday); // false
System.out.println(tuesday == wednesday); // true
```

&nbsp;&nbsp;&nbsp;&nbsp;
Even though all of the variables point to the same file information, only two references,
tuesday and wednesday, are equal in terms of == since they point to the same object. <br />

&nbsp;&nbsp;&nbsp;&nbsp;
In some languages, comparing null with any other value is always false, although this
is not the case in Java.
```java
System.out.print(null == null); // true
```

## 2. Relational Operators
&nbsp;&nbsp;&nbsp;&nbsp;
We now move on to relational operators, which compare two expressions and return a
boolean value.

#### TABLE 2.8: Relational operators
| Operator                 | Example             | Description                                                                                                                              |
|:-------------------------|:--------------------|:-----------------------------------------------------------------------------------------------------------------------------------------|
| Less than                | a < 5               | Returns true if the value on the left is strictly less than the value on the right                                                       |
| Greater than             | a > 5               | Returns true if the value on the left is strictly greater than the value on the right                                                    |
| Less than or equal to    | a <= 5              | Returns true if the value on the left is less than or equal to the value on the right                                                    |
| Greater than or equal to | a >= 5              | Returns true if the value on the left is greater than or equal to the value on the right                                                 |
| Type comparison          | a instanceof String | Returns true if the reference on the left side is an instance of the type on the right side (class, interface, record, enum, annotation) |

## 3. Numeric Comparison Operators
The first four relational operators in Table 2.8 apply only to numeric values. If the two
numeric operands are not of the same data type, the smaller one is promoted, as previously
discussed. <br />

&nbsp;&nbsp;&nbsp;&nbsp;
Let’s look at examples of these operators in action:
```java
int gibbonNumFeet = 2, wolfNumFeet = 4, ostrichNumFeet = 2;
System.out.println(gibbonNumFeet < wolfNumFeet); // true
System.out.println(gibbonNumFeet <= wolfNumFeet); // true
System.out.println(gibbonNumFeet >= ostrichNumFeet); // true
System.out.println(gibbonNumFeet > ostrichNumFeet); // false
```

&nbsp;&nbsp;&nbsp;&nbsp;
Notice that the last example outputs false, because although gibbonNumFeet and
ostrichNumFeet have the same value, gibbonNumFeet is not strictly greater than
ostrichNumFeet.

## 4. instanceof Operator
The final relational operator you need to know for the exam is the instanceof operator,
shown in Table 2.8. It is useful for determining whether an arbitrary object is a member of a
particular class or interface at runtime. <br />

&nbsp;&nbsp;&nbsp;&nbsp;
Why wouldn’t you know what class or interface an object is? As we will get into in
Chapter 6, “Class Design,” Java supports polymorphism. For now, all you need to know is
objects can be passed around using a variety of references. For example, all classes inherit
from java.lang.Object. This means that any instance can be assigned to an Object reference. For example, how many objects are created and used in the following code snippet?
```java
Integer zooTime = Integer.valueOf(9);
Number num = zooTime;
Object obj = zooTime;
```

&nbsp;&nbsp;&nbsp;&nbsp;
In this example, only one object is created in memory, but there are three different references to it because Integer inherits both Number and Object. This means that you can
call instanceof on any of these references with three different data types, and it will return
true for each of them. <br />

&nbsp;&nbsp;&nbsp;&nbsp;
Where polymorphism often comes into play is when you create a method that takes a
data type with many possible subclasses. For example, imagine that we have a function that
opens the zoo and prints the time. As input, it takes a Number as an input parameter.
```java
public void openZoo(Number time) {}
```

&nbsp;&nbsp;&nbsp;&nbsp;
Now, we want the function to add O'clock to the end of output if the value is a whole
number type, such as an Integer; otherwise, it just prints the value.
```java
public void openZoo(Number time) {
    if (time instanceof Integer)
        System.out.print((Integer)time + " O'clock");
    else 
        System.out.print(time);
}
```

&nbsp;&nbsp;&nbsp;&nbsp;
We now have a method that can intelligently handle both Integer and other values. A
good exercise left for the reader is to add checks for other numeric data types such as Short,
Long, Double, and so on. <br />

&nbsp;&nbsp;&nbsp;&nbsp;
Notice that we cast the Integer value in this example. It is common to use casting with
instanceof when working with objects that can be various different types, since casting gives
you access to fields available only in the more specific classes. It is considered a good coding
practice to use the instanceof operator prior to casting from one object to a narrower type.

## 5. Invalid instanceof
One area the exam might try to trip you up on is using instanceof with incompatible
types. For example, Number cannot possibly hold a String value, so the following causes a
compilation error:
```java
public void openZoo(Number time) {
    if (time instanceof String) // DOES NOT COMPILE
        System.out.print(time);
}
```

&nbsp;&nbsp;&nbsp;&nbsp;
If the compiler can determine that a variable cannot possibly be cast to a specific class, it
reports an error

## 6. null and the instanceof operator
What happens if you call instanceof on a null variable? For the exam, you should know that
calling instanceof on the null literal or a null reference always returns false.
```java
System.out.print(null instanceof Object); // false
        
Object noObjectHere = null;
System.out.print(noObjectHere instanceof String); // false
```

&nbsp;&nbsp;&nbsp;&nbsp;
The preceding examples both print false. It almost doesn't matter what the right side of
the expression is. We say “almost” because there are exceptions. This example does not compile, since null is used on the right side of the instanceof operator:

```java
System.out.print(null instanceof null); // DOES NOT COMPILE
```

> Although it may feel like you’ve learned everything there is about the
instanceof operator, there’s a lot more coming! In Chapter 3, we introduce pattern matching with the instanceof operator, which was 
officially added in Java 16. In Chapter 7, “Beyond Classes,” we introduce
polymorphism in much more detail and show how to apply these rules to
interfaces.

## 7. Logical Operators
&nbsp;&nbsp;&nbsp;&nbsp;
The logical operators, (&), (|), and (^), may be applied to both numeric and boolean
data types; they are listed in Table 2.9. When they’re applied to boolean data types, they’re
referred to as logical operators. Alternatively, when they’re applied to numeric data types,
they’re referred to as bitwise operators, as they perform bitwise comparisons of the bits
that compose the number. For the exam, though, you don’t need to know anything about
numeric bitwise comparisons, so we’ll leave that educational aspect to other books.

#### Table 2.9: Logical Operators
| Operator | Example | Description                                                    |
|:---------|:--------|:---------------------------------------------------------------|
| Logical AND | a & b | Value is true only if both values are true.                    |
| Logical OR | a \| b | Value is true if at least one of the values is true.           |
| Logical XOR | a ^ b | Value is true only if one value is true and the other is false.|

&nbsp;&nbsp;&nbsp;&nbsp;
Here are some tips to help you remember this table:
- AND is only true if both operands are true. 
- Inclusive OR is only false if both operands are false. 
- Exclusive OR is only true if the operands are different.

&nbsp;&nbsp;&nbsp;&nbsp;
Let’s take a look at some examples:
```java
boolean eyesClosed = true;
boolean breathingSlowly = true;

boolean resting = eyesClosed | breathingSlowly;
boolean asleep = eyesClosed & breathingSlowly;
boolean awake = eyesClosed ^ breathingSlowly;
System.out.println(resting); // true
System.out.println(asleep); // true
System.out.println(awake); // false
```
## 8. Conditional Operators
#### Table 2.10: Conditional Operators
| Operator | Example | Description                                                                                                               |
|:---------|:--------|:--------------------------------------------------------------------------------------------------------------------------|
| Conditional AND | a && b | Value is true only if both values are true. If the left side is false, then the right side will not be evaluated.         |
| Conditional OR | a \|\| b | Value is true if at least one of the values is true. If the left side is true, then the right side will not be evaluated. |

&nbsp;&nbsp;&nbsp;&nbsp;
The conditional operators, often called short-circuit operators, are nearly identical to the
logical operators, & and |, except that the right side of the expression may never be evaluated if the final result 
can be determined by the left side of the expression. For example, consider the following statement
```java
int hour = 10;
boolean zooOpen = true || (hour < 4);
System.out.println(zooOpen); // true
```

&nbsp;&nbsp;&nbsp;&nbsp;
Referring to the truth tables, the value zooOpen can be false only if both sides of the
expression are false. Since we know the left side is true, there’s no need to evaluate the
right side, since no value of hour will ever make this code print false. In other words,
hour could have been -10 or 892; the output would have been the same. Try it yourself
with different values for hour!

## 9. Avoiding a NullPointerException
A more common example of where conditional operators are used is checking for null
objects before performing an operation. In the following example, if duck is null, the program
will throw a NullPointerException at runtime:
```java
if (duck != null & duck.getAge() < 5) { // Could throw a NullPointerException
    // Do something
}
```

&nbsp;&nbsp;&nbsp;&nbsp;
The issue is that the logical AND (&) operator evaluates both sides of the expression. We
could add a second if statement, but this could get unwieldy if we have a lot of variables to
check. An easy-to-read solution is to use the conditional AND operator (&&):
```java
if (duck != null && duck.getAge() < 5) {
    // Do something
}
```

&nbsp;&nbsp;&nbsp;&nbsp;
In this example, if duck is null, the conditional prevents a NullPointerException
from ever being thrown, since the evaluation of duck.getAge() < 5 is never reached.

## 10. Checking for Unperformed Side Effects
Be wary of short-circuit behavior on the exam, as questions are known to alter a variable on
the right side of the expression that may never be reached. This is referred to as an unperformed side effect. For example, what is the output of the following code?
```java
int rabbit = 6;
boolean bunny = (rabbit >= 6) || (++rabbit <= 7);
System.out.println(rabbit);
```

&nbsp;&nbsp;&nbsp;&nbsp;
Because rabbit >= 6 is true, the increment operator on the right side of the expression
is never evaluated, so the output is 6.
