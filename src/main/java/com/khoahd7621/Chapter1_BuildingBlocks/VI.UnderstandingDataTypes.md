# Understanding Data Types

Java applications contain two types of data: primitive types and reference types. In this 
section, we discuss the differences between a primitive type and a reference type.

## I. Using Primitive Types

Java has eight built-in data types, referred to as the Java _primitive types_. These eight data
types represent the building blocks for Java objects, because all Java objects are just a 
complex collection of these primitive data types. That said, a primitive is not an object in Java,
nor does it represent an object. A primitive is just a single value in memory, such as a number
or character.

### 1. The Primitive Types

The exam assumes you are well versed in the eight primitive data types, their relative sizes,
and what can be stored in them. Table 1.6 shows the Java primitive types together with their
size in bits and the range of values that each holds.

> **Table 1.6** Primitive types

|Keyword|Type|Min value|Max value|Default value| Example |
|-------|----|---------|---------|-------------|---------|
|boolean|true or false|N/A|N/A|false| true    |
|byte|8-bit integral value|-128|127|0| 123     |
|short|16-bit integral value|-32,768|32,767|0| 123     |
|int|32-bit integral value|-2,147,483,648|2,147,483,647|0| 123     |
|long|64-bit integral value|-9,223,372,036,854,775,808|9,223,372,036,854,775,807|0L| 123L    |
|float|32-bit floating-point value|N/A|N/A|0.0f| 123.45f |
|double|64-bit floating-point value|N/A|N/A|0.0d| 123.456 |
|char|16-bit Unicode value|'\u0000'|'\uffff'|'\u0000'| 'a'     |

> #### Is _String_ a Primitive?
> No, it is not. That said, `String` is often mistaken for a ninth primitive because Java
includes built-in support for `String` literals and operators. You learn more about `String` in
Chapter 4, but for now, just remember it’s an `object`, not a `primitive`.

&emsp;&emsp;
There’s a lot of information in Table 1.6. Let’s look at some key points:
- The `byte`, `short`, `int`, and `long` types are used for integer values without decimal points.
- Each numeric type uses twice as many bits as the smaller similar type. For example,
`short` uses twice as many bits as `byte` does.
- All of the numeric types are signed and reserve one of their bits to cover a negative
range. For example, instead of byte covering 0 to 255 (or even 1 to 256) it actually
covers -128 to 127.
- A `float` requires the letter `f` or `F` following the number so Java knows it is a `float`.
Without an `f` or `F`, Java interprets a decimal value as a `double`.
- A `long` requires the letter `l` or `L` following the number so Java knows it is a `long`.
Without an `l` or `L`, Java interprets a number without a decimal point as an int in most
scenarios.

&emsp;&emsp;
You won’t be asked about the exact sizes of these types, although you should have a 
general idea of the size of smaller types like `byte` and `short`. A common question among newer
Java developers is, what is the bit size of boolean? The answer is, it is not specified and is
dependent on the JVM where the code is being executed.

> #### Signed and Unsigned: _short_ and _char_
> - For the exam, you should be aware that `short` and `char` are closely related, as both are
stored as integral types with the same 16-bit length. The primary difference is that `short`
is **signed**, which means it splits its range across the positive and negative integers. 
Alternatively, _char_ is **unsigned**, which means its range is strictly positive, including 0.
> - Often, `short` and `char` values can be cast to one another because the underlying data size
is the same. You learn more about casting in Chapter 2, “Operators.”

### 2. Writing Literals
There are a few more things you should know about numeric primitives. When a number is
present in the code, it is called a _literal_. By default, Java assumes you are defining an `int` value
with a numeric literal. In the following example, the number listed is bigger than what fits in
an `int`. Remember, you aren’t expected to memorize the maximum value for an `int`. The exam
will include it in the question if it comes up.

```java
long max = 3123456789; // DOES NOT COMPILE
```

&emsp;&emsp;
Java complains the number is out of range. And it is—for an `int`. However, we don’t
have an `int`. The solution is to add the character `L` to the number:

```java
long max = 3123456789L; // Now Java knows it’s a long
```

&emsp;&emsp;
Alternatively, you could add a lowercase `l` to the number. But please use the uppercase `L`.
The lowercase `l` looks like the number `1`. <br />

&emsp;&emsp;
Another way to specify numbers is to change the “base.” When you learned how to count,
you studied the digits 0–9. This numbering system is called `base 10` since there are 10 
possible values for each digit. It is also known as the `decimal number system`. Java allows you to
specify digits in several other formats:

- Octal (digits 0–7), which uses the number 0 as a prefix—for example, 017.
- Hexadecimal (digits 0–9 and letters A–F/a–f), which uses 0x or 0X as a prefix—for
example, 0xFF, 0xff, 0XFf. Hexadecimal is case insensitive, so all of these examples
mean the same value.
- Binary (digits 0–1), which uses the number 0 followed by b or B as a prefix—for
example, 0b10, 0B10.

You won’t need to convert between number systems on the exam. You’ll have to 
recognize valid literal values that can be assigned to numbers.

### 3. Literals and the Underscore Character
The last thing you need to know about numeric literals is that you can have underscores in
numbers to make them easier to read:

```java
int million1 = 1000000;
int million2 = 1_000_000;
```

&emsp;&emsp;
We’d rather be reading the latter one because the zeros don’t run together. You can add
underscores anywhere except at the beginning of a literal, the end of a literal, right before a
decimal point, or right after a decimal point. You can even place multiple underscore 
characters next to each other, although we don’t recommend it. <br />

&emsp;&emsp;
Let’s look at a few examples:

```java
double notAtStart       = _1000.00;           // DOES NOT COMPILE
double notAtEnd         = 1000.00_;           // DOES NOT COMPILE
double notByDecimal     = 1000_.00;           // DOES NOT COMPILE
double annoyingButLegal = 1_00_0.0_0;         // Ugly, but compiles
double reallyUgly       = 1__________2;       // Also compiles
```

## II. Using Reference Types
A _reference type_ refers to an object (an instance of a class). Unlike primitive types that hold
their values in the memory where the variable is allocated, references do not hold the value
of the object they refer to. Instead, a reference “points” to an object by storing the memory
address where the object is located, a concept referred to as a _pointer_. Unlike other 
languages, Java does not allow you to learn what the physical memory address is. You can only
use the reference to refer to the object. <br />

&emsp;&emsp;
Let’s take a look at some examples that declare and initialize reference types. Suppose we
declare a reference of type `String`:

```java
String greeting;
```

&emsp;&emsp;
The `greeting` variable is a reference that can only point to a String object. A value is
assigned to a reference in one of two ways:

- A reference can be assigned to another object of the same or compatible type.
- A reference can be assigned to a new object using the new keyword.

&emsp;&emsp;
For example, the following statement assigns this reference to a new object:

```java
greeting = new String("How are you?");
```

The greeting reference points to a new `String` object, "How are you?". The `String`
object does not have a name and can be accessed only via a corresponding reference.

## III. Distinguishing between Primitives and Reference Types
Continue ...
