# Using the *StringBuilder* Class

A small program can create a lot of String objects very quickly. For example, how many
objects do you think this piece of code creates?

```java
10: String alpha = "";
11: for (char current = 'a'; current <= 'z'; current++)
12:     alpha += current;
13: System.out.println(alpha);
```

&emsp;&emsp;
The empty String on line 10 is instantiated, and then line 12 appends an "a". However,
because the String object is immutable, a new String object is assigned to alpha, and the
"" object becomes eligible for garbage collection. The next time through the loop, alpha is
assigned a new String object, "ab", and the "a" object becomes eligible for garbage collection. 
The next iteration assigns alpha to "abc", and the "ab" object becomes eligible for
garbage collection, and so on.

&emsp;&emsp;
This sequence of events continues, and after 26 iterations through the loop, *a total of 27
objects are instantiated*, most of which are immediately eligible for garbage collection.

&emsp;&emsp;
This is very inefficient. Luckily, Java has a solution. The StringBuilder class creates a
String without storing all those interim String values. Unlike the String class,
StringBuilder is not immutable.

```java
15: StringBuilder alpha = new StringBuilder();
16: for (char current = 'a'; current <= 'z'; current++)
17:     alpha.append(current);
18: System.out.println(alpha);
```

&emsp;&emsp;
On line 15, a new StringBuilder object is instantiated. The call to append() on line
17 adds a character to the StringBuilder object each time through the for loop, appending the value of current to the end of alpha. This code reuses the same StringBuilder
without creating an interim String each time.

&emsp;&emsp;
In old code, you might see references to StringBuffer. It works the same way, except it
supports threads, which you learn about in Chapter 13, “Concurrency.” StringBuffer is
not on the exam. It performs slower than StringBuilder, so just use StringBuilder.

## I. Mutability and Chaining
We’re sure you noticed this from the previous example, but StringBuilder is not immutable.
In fact, we gave it 27 different values in the example (a blank plus adding each letter in
the alphabet). The exam will likely try to trick you with respect to String and StringBuilder
being mutable.

&emsp;&emsp;
Chaining makes this even more interesting. When we chained String method calls, the
result was a new String with the answer. Chaining StringBuilder methods doesn’t work this
way. Instead, the StringBuilder changes its own state and returns a reference to itself. Let’s
look at an example to make this clearer:

```java
4: StringBuilder sb = new StringBuilder("start");
5: sb.append("+middle");                            // sb = "start+middle"
6: StringBuilder same = sb.append("+end");          // "start+middle+end"
```

&emsp;&emsp;
Line 5 adds text to the end of sb. It also returns a reference to sb, which is ignored. Line
6 also adds text to the end of sb and returns a reference to sb. This time the reference is
stored in same. This means sb and same point to the same object and would print out the same value.

&emsp;&emsp;
The exam won’t always make the code easy to read by having only one method per line.
What do you think this example prints?

```java
4: StringBuilder a = new StringBuilder("abc");
5: StringBuilder b = a.append("de");
6: b = b.append("f").append("g");
7: System.out.println("a=" + a);
8: System.out.println("b=" + b);
```

&emsp;&emsp;
Did you say both print "abcdefg"? Good. There’s only one StringBuilder object
here. We know that because new StringBuilder() is called only once. On line 5, there
are two variables referring to that object, which has a value of "abcde". On line 6, those
two variables are still referring to that same object, which now has a value of "abcdefg".
Incidentally, the assignment back to b does absolutely nothing. b is already pointing to that
StringBuilder.

## II. Creating a *StringBuilder*

There are three ways to construct a StringBuilder:

```java
StringBuilder sb1 = new StringBuilder();
StringBuilder sb2 = new StringBuilder("animal");
StringBuilder sb3 = new StringBuilder(10);
```

&emsp;&emsp;
The first says to create a StringBuilder containing an empty sequence of characters
and assign sb1 to point to it. The second says to create a StringBuilder containing a
specific value and assign sb2 to point to it. The first two examples tell Java to manage the
implementation details. The final example tells Java that we have some idea of how big the
eventual value will be and would like the StringBuilder to reserve a certain capacity, or
number of slots, for characters.

## III. Important *StringBuilder* Methods

As with String, we aren’t going to cover every single method in the StringBuilder class. These
are the ones you might see on the exam.

### &emsp;&emsp; 1. Using Common Methods

These four methods work exactly the same as in the String class. Be sure you can identify the
output of this example:

```java
var sb = new StringBuilder("animals");
String sub = sb.substring(sb.indexOf("a"), sb.indexOf("al"));
int len = sb.length();
char ch = sb.charAt(6);
System.out.println(sub + " " + len + " " + ch);
```

&emsp;&emsp;
The correct answer is `anim 7 s`. The indexOf() method calls return 0 and 4, respectively. The substring() method returns the String starting with index 0 and ending right
before index 4. <br />
&emsp;&emsp;
The length() method returns 7 because it is the number of characters in the StringBuilder rather than an index. Finally, charAt() returns the character at index 6. Here, we
do start with 0 because we are referring to indexes. If this doesn’t sound familiar, go back
and read the section on String again. <br />
&emsp;&emsp;
Notice that substring() returns a String rather than a StringBuilder. That is why sb is not
changed. The substring() method is really just a method that inquires about the state of the
StringBuilder.

### &emsp;&emsp; 2. Appending Values

The append() method is by far the most frequently used method in StringBuilder. In fact, it is
so frequently used that we just started using it without comment. Luckily, this method does
just what it sounds like: it adds the parameter to the StringBuilder and returns a reference to
the current StringBuilder. One of the method signatures is as follows:

```java
public StringBuilder append(String str)
```

&emsp;&emsp;
Notice that we said one of the method signatures. There are more than 10 method signatures that look similar but take different data types as parameters, such as int, char, etc.
All those methods are provided so you can write code like this:

```java
var sb = new StringBuilder().append(1).append('c');
sb.append("-").append(true);
System.out.println(sb);             // 1c-true
```

&emsp;&emsp;
Nice method chaining, isn’t it? The append() method is called directly after the constructor. By having all these method signatures, you can just call append() without having
to convert your parameter to a String first.

### &emsp;&emsp; 3. Inserting Data

The insert() method adds characters to the StringBuilder at the requested index and returns a
reference to the current StringBuilder. Just like append(), there are lots of method signatures
for different types. Here’s one:

```java
public StringBuilder insert(int offset, String str)
```

&emsp;&emsp;
Pay attention to the offset in these examples. It is the index where we want to insert the
requested parameter.

```java
3: var sb = new StringBuilder("animals");
4: sb.insert(7, "-");       // sb = animals-
5: sb.insert(0, "-");       // sb = -animals-
6: sb.insert(4, "-");       // sb = -ani-mals-
7: System.out.println(sb);
```

&emsp;&emsp;
Line 4 says to insert a dash at index 7, which happens to be the end of the sequence of
characters. Line 5 says to insert a dash at index 0, which happens to be the very beginning.
Finally, line 6 says to insert a dash right before index 4. The exam creators will try to trip
you up on this. As we add and remove characters, their indexes change. When you see a
question dealing with such operations, draw what is going on using available writing materials so you won’t be confused.

### &emsp;&emsp; 4. Deleting Contents

The delete() method is the opposite of the insert() method. It removes characters from the
sequence and returns a reference to the current StringBuilder. The deleteCharAt() method
is convenient when you want to delete only one character. The method signatures are as follows:

```java
public StringBuilder delete(int startIndex, int endIndex)
public StringBuilder deleteCharAt(int index)
```

&emsp;&emsp;
The following code shows how to use these methods:

```java
var sb = new StringBuilder("abcdef");
sb.delete(1, 3);    // sb = adef
sb.deleteCharAt(5); // exception
```

&emsp;&emsp;
First, we delete the characters starting with index 1 and ending right before index 3. This
gives us adef. Next, we ask Java to delete the character at position 5. However, the remaining
value is only four characters long, so it throws a StringIndexOutOfBoundsException.

&emsp;&emsp;
The delete() method is more flexible than some others when it comes to array indexes.
If you specify a second parameter that is past the end of the StringBuilder, Java will just
assume you meant the end. That means this code is legal:

```java
var sb = new StringBuilder("abcdef");
sb.delete(1, 100);  // sb = a
```

### &emsp;&emsp; 5. Replacing Portions

The replace() method works differently for StringBuilder than it did for String. The method signature is as follows:

```java
public StringBuilder replace(int startIndex, int endIndex, String newString)
```

&emsp;&emsp;
The following code shows how to use this method:

```java
var builder = new StringBuilder("pigeon dirty");
builder.replace(3, 6, "sty");
System.out.println(builder);    // pigsty dirty
```

&emsp;&emsp;
First, Java deletes the characters starting with index 3 and ending right before index 6.
This gives us pig dirty. Then Java inserts the value "sty" in that position.

&emsp;&emsp;
In this example, the number of characters removed and inserted are the same. However,
there is no reason they have to be. What do you think this does?

```java
var builder = new StringBuilder("pigeon dirty");
builder.replace(3, 100, "");
System.out.println(builder);
```

&emsp;&emsp;
It prints "pig". Remember, the method is first doing a logical delete. The replace()
method allows specifying a second parameter that is past the end of the StringBuilder.
That means only the first three characters remain.

### &emsp;&emsp; 6. Reversing
After all that, it’s time for a nice, easy method. The reverse() method does just what it
sounds like: it reverses the characters in the sequences and returns a reference to the current
StringBuilder. The method signature is as follows:

```java
public StringBuilder reverse()
```

&emsp;&emsp;
The following code shows how to use this method:

```java
var sb = new StringBuilder("ABC");
sb.reverse();
System.out.println(sb);
```

&emsp;&emsp;
As expected, this prints CBA. This method isn’t that interesting. Maybe the exam creators
like to include it to encourage you to write down the value rather than relying on memory
for indexes.

> **Working with *toString()*** <br />
> 
> The Object class contains a toString() method that many classes provide custom
implementations of. The StringBuilder class is one of these. <br />
> The following code shows how to use this method:
> ```java
> var sb = new StringBuilder("ABC");
> String s = sb.toString();
> ```
> Often StringBuilder is used internally for performance purposes, but the end result
needs to be a String. For example, maybe it needs to be passed to another method that is
expecting a String.
