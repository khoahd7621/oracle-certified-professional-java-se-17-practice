# Calculating with Math APIs

It should come as no surprise that computers are good at computing numbers. Java comes
with a powerful Math class with many methods to make your life easier. We just cover a
few common ones here that are most likely to appear on the exam. When doing your own
projects, look at the Math Javadoc to see what other methods can help you. <br />

&emsp;&emsp;
Pay special attention to return types in math questions. They are an excellent opportunity
for trickery!

## I. Finding the Minimum and Maximum
The min() and max() methods compare two values and return one of them.
The method signatures for min() are as follows:

```java
public static double min(double a, double b)
public static float min(float a, float b)
public static int min(int a, int b)
public static long min(long a, long b)
```

&emsp;&emsp;
There are four overloaded methods, so you always have an API available with the same
type. Each method returns whichever of a or b is smaller. The max() method works the
same way, except it returns the larger value. The following shows how to use these methods:

```java
int first = Math.max(3, 7);     // 7
int second = Math.min(7, -9);   // -9
```

&emsp;&emsp;
The first line returns 7 because it is larger. The second line returns -9 because it is smaller.
Remember from school that negative values are smaller than positive ones.

## II. Rounding Numbers
The round() method gets rid of the decimal portion of the value, choosing the next higher
number if appropriate. If the fractional part is .5 or higher, we round up.
The method signatures for round() are as follows:

```java
public static long round(double num)
public static int round(float num)
```

&emsp;&emsp;
There are two overloaded methods to ensure that there is enough room to store a
rounded double if needed. The following shows how to use this method:

```java
long low = Math.round(123.45);          // 123
long high = Math.round(123.50);         // 124
int fromFloat = Math.round(123.45f);    // 123
```

&emsp;&emsp;
The first line returns 123 because .45 is smaller than a half. The second line returns 124
because the fractional part is just barely a half. The final line shows that an explicit float
triggers the method signature that returns an int.

## III. Determining the Ceiling and Floor
The ceil() method takes a double value. If it is a whole number, it returns the same
value. If it has any fractional value, it rounds up to the next whole number. By contrast, the
floor() method discards any values after the decimal.
The method signatures are as follows:

```java
public static double ceil(double num)
public static double floor(double num)
```

&emsp;&emsp;
The following shows how to use these methods:

```java
double c = Math.ceil(3.14);  // 4.0
double f = Math.floor(3.14); // 3.0
```

&emsp;&emsp;
The first line returns 4.0 because four is the integer, just larger. The second line returns
3.0 because it is the integer, just smaller.


## IV. Calculating Exponents

The pow() method handles exponents. As you may recall from your elementary school math class, 32
means three squared. This is 3 * 3 or 9. Fractional exponents are allowed as well.
Sixteen to the .5 power means the square root of 16, which is 4. (Don’t worry, you won’t
have to do square roots on the exam.) <br />

&emsp;&emsp;
The method signatures are as follows:

```java
public static double pow(double number, double exponent)
```

&emsp;&emsp;
The following shows how to use this method:

```java
double squared = Math.pow(5, 2); // 25.0
```

&emsp;&emsp;
Notice that the result is 25.0 rather than 25 since it is a double. Again, don’t worry; the
exam won’t ask you to do any complicated math.

## V. Generating Random Numbers
The random() method returns a value greater than or equal to 0 and less than 1. The method
signature is as follows:

```java
public static double random()
```

&emsp;&emsp;
The following shows how to use this method:

```java
double num = Math.random();
```

&emsp;&emsp;
Since it is a random number, we can’t know the result in advance. However, we can rule
out certain numbers. For example, it can’t be negative because that’s less than 0. It can’t be
1.0 because that’s not less than 1.

> **Note:** <br />
> While not on the exam, it is common to use the Random class for generating pseudo-random numbers. It allows generating numbers of different
types.
