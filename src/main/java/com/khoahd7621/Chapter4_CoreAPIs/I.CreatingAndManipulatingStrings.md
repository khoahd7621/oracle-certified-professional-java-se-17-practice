# Creating and Manipulating Strings

The String class is such a fundamental class that you’d be hard-pressed to write code
without it. After all, you can’t even write a main() method without using the String class.
A string is basically a sequence of characters; here’s an example:

```java
String name = "Fluffy";
```

&emsp;&emsp;
As you learned in Chapter 1, “Building Blocks,” this is an example of a reference type.
You also learned that reference types are created using the new keyword. Wait a minute.
Something is missing from the previous example: it doesn’t have new in it! In Java, these two
snippets both create a String:

```java
String name = "Fluffy";
String name = new String("Fluffy");
```

&emsp;&emsp;
Both give you a reference variable named name pointing to the String object "Fluffy".
They are subtly different, as you see later in this chapter. For now, just remember that the
String class is special and doesn’t need to be instantiated with *new*.

&emsp;&emsp;
Further, text blocks are another way of creating a String. To review, this text block is the
same as the previous variables:

```java
String name = """
              Fluffy""";
```

&emsp;&emsp;
Since a String is a sequence of characters, you probably won’t be surprised to hear that
it implements the interface CharSequence. This interface is a general way of representing
several classes, including String and StringBuilder. You learn more about interfaces in
Chapter 7, “Beyond Classes.”

&emsp;&emsp;
In this section, we look at concatenation, common methods, and method chaining.

## I. Concatenating

In Chapter 2, “Operators,” you learned how to add numbers. 1 + 2 is clearly 3. But what is
"1" + "2"? It’s "12" because Java combines the two String objects. Placing one String
before the other String and combining them is called string concatenation. The exam
creators like string concatenation because the + operator can be used in two ways within
the same line of code. There aren’t a lot of rules to know for this, but you have to know
them well:

1. If both operands are numeric, + means numeric addition.
2. If either operand is a String, + means concatenation.
3. The expression is evaluated left to right.

&emsp;&emsp;
Now let’s look at some examples:

```java
System.out.println(1 + 2);          // 3
System.out.println("a" + "b");      // ab
System.out.println("a" + "b" + 3);  // ab3
System.out.println(1 + 2 + "c");    // 3c
System.out.println("c" + 1 + 2);    // c12
System.out.println("c" + null);     // cnull
```

&emsp;&emsp;
The exam takes trickery a step further and will try to fool you with something like this:

```java
int three = 3;
String four = "4";
System.out.println(1 + 2 + three + four);
```

&emsp;&emsp;
When you see this, just take it slow, remember the three rules, and be sure to check the
variable types. In this example, we start with the third rule, which tells us to consider 1 + 2.
The first rule gives us 3. Next, we have 3 + three. Since three is of type int, we still use
the first rule, giving us 6. Then, we have 6 + four. Since four is of type String, we switch
to the second rule and get a final answer of "64". When you see questions like this, just take
your time and check the types. Being methodical pays off.

&emsp;&emsp;
There is one more thing to know about concatenation, but it is easy. In this example, you
just have to remember what += does. Keep in mind, s += "2" means the same thing as s
= s + "2".

```java
4: var s = "1"; // s currently holds "1"
5: s += "2";    // s currently holds "12"
6: s += 3;      // s currently holds "123"
7: System.out.println(s); // 123
```

&emsp;&emsp;
To review the rules one more time: use numeric addition if two numbers are involved, use
concatenation otherwise, and evaluate from left to right. Have you memorized these three
rules yet? Be sure to do so before the exam!

## II. Important *String* Methods
The String class has dozens of methods. Luckily, you need to know only a handful for the
exam. The exam creators pick most of the methods developers use in the real world.

&emsp;&emsp;
For all these methods, you need to remember that a string is a sequence of characters
and Java counts from 0 when indexed. Figure below shows how each character in the string
"animals" is indexed.

| 0 | 1 | 2 | 3 | 4 | 5 | 6 |
|---|---|---|---|---|---|---|
| a | n | i | m | a | l | s |

&emsp;&emsp;
You also need to know that a String is immutable, or unchangeable. This means calling
a method on a String will return a different String object rather than changing the value
of the reference. In this chapter, you use immutable objects. In Chapter 6, “Class Design,”
you learn how to create immutable objects of your own.

&emsp;&emsp;
Let’s look at a number of methods from the String class. Many of them are straightforward, so we won’t 
discuss them at length. You need to know how to use these methods.

### &emsp;&emsp; 1. Determining the Length
The method length() returns the number of characters in the String. The method signature is as follows:

```java
public int length()
```

&emsp;&emsp;
The following code shows how to use length():

```java
var name = "animals";
System.out.println(name.length()); // 7
```

&emsp;&emsp;
Wait. It outputs 7? Didn’t we just tell you that Java counts from 0? The difference is
that zero counting happens only when you’re using indexes or positions within a list.
When determining the total size or length, Java uses normal counting again.

### &emsp;&emsp; 2. Getting a Single Character
The method charAt() lets you query the string to find out what character is at a specific index.
The method signature is as follows:

```java
public char charAt(int index)
```

&emsp;&emsp;
The following code shows how to use charAt():

```java
var name = "animals";
System.out.println(name.charAt(0)); // a
System.out.println(name.charAt(6)); // s
System.out.println(name.charAt(7)); // exception
```

&emsp;&emsp;
Since indexes start counting with 0, charAt(0) returns the “first” character in the
sequence. Similarly, charAt(6) returns the “seventh” character in the sequence.
However, charAt(7) is a problem. It asks for the “eighth” character in the sequence, but
there are only seven characters present. When something goes wrong that Java doesn’t know
how to deal with, it throws an exception, as shown here. You learn more about exceptions in
Chapter 11, “Exceptions and Localization.”

```java
java.lang.StringIndexOutOfBoundsException: String index out of range: 7
```

### &emsp;&emsp; 3. Finding an Index
The method indexOf() looks at the characters in the string and finds the first index that
matches the desired value. The indexOf method can work with an individual character or a
whole String as input. It can also start from a requested position. Remember that a char
can be passed to an int parameter type. On the exam, you’ll only see a char passed to the
parameters named ch. The method signatures are as follows:

```java
public int indexOf(int ch)

public int indexOf(int ch, int fromIndex)
public int indexOf(String str)
public int indexOf(String str, int fromIndex)
```

&emsp;&emsp;
The following code shows you how to use indexOf():

```java
var name = "animals";
System.out.println(name.indexOf('a'));      // 0
System.out.println(name.indexOf("al"));     // 4
System.out.println(name.indexOf('a', 4));   // 4
System.out.println(name.indexOf("al", 5));  // -1
```

&emsp;&emsp;
Since indexes begin with 0, the first 'a' matches at that position. The second statement
looks for a more specific string, so it matches later. The third statement says Java shouldn’t
even look at the characters until it gets to index 4. The final statement doesn’t find anything
because it starts looking after the match occurred. Unlike charAt(), the indexOf()
method doesn’t throw an exception if it can’t find a match, instead returning –1. Because
indexes start with 0, the caller knows that –1 couldn’t be a valid index. This makes it a
common value for a method to signify to the caller that no match is found.

### &emsp;&emsp; 4. Getting a Substring
The method substring() also looks for characters in a string. It returns parts of the string.
The first parameter is the index to start with for the returned string. As usual, this is a
zero-based index. There is an optional second parameter, which is the end index you want
to stop at.

&emsp;&emsp;
Notice we said “stop at” rather than “include.” This means the endIndex parameter
is allowed to be one past the end of the sequence if you want to stop at the end of the
sequence. That would be redundant, though, since you could omit the second parameter
entirely in that case. In your own code, you want to avoid this redundancy. Don’t be surprised if the exam uses it, though. The method signatures are as follows:

```java
public String substring(int beginIndex)
public String substring(int beginIndex, int endIndex)
```

&emsp;&emsp;
It helps to think of indexes a bit differently for the substring methods. Pretend the indexes
are right before the character they would point to. Figure below helps visualize this. Notice
how the arrow with the 0 points to the character that would have index 0. The arrow
with the 1 points between characters with indexes 0 and 1. There are seven characters in
the String. Since Java uses zero-based indexes, this means the last character has an index
of 6. The arrow with the 7 points immediately after this last character. This will help you
remember that endIndex doesn’t give an out-of-bounds exception when it is one past the
end of the String.

| * | a | | n |  | i |  | m |  | a |  | l |  | s |  |
|---|---|-|---|--|---|--|---|--|---|--|---|--|---|--|
| 0 | - | 1 | - | 2 | - | 3 | - | 4 | - | 5 | - | 6 | - | 7 |

The following code shows how to use substring():

```java
var name = "animals";
System.out.println(name.substring(3));                  // mals
System.out.println(name.substring(name.indexOf('m')));  // mals
```

&emsp;&emsp;
The substring() method is the trickiest String method on the exam. The first example says
to take the characters starting with index 3 through the end, which gives us "mals". The second
example does the same thing, but it calls indexOf() to get the index rather than hard-coding it.
This is a common practice when coding because you may not know the index in advance.

&emsp;&emsp;
The third example says to take the characters starting with index 3 until, but not
including, the character at index 4. This is a complicated way of saying we want a String
with one character: the one at index 3. This results in "m". The final example says to take the
characters starting with index 3 until we get to index 7. Since index 7 is the same as the end
of the string, it is equivalent to the first example.

&emsp;&emsp;
We hope that wasn’t too confusing. The next examples are less obvious:

```java
System.out.println(name.substring(3, 3)); // empty string
System.out.println(name.substring(3, 2)); // exception
System.out.println(name.substring(3, 8)); // exception
```

&emsp;&emsp;
The first example in this set prints an empty string. The request is for the characters
starting with index 3 until we get to index 3. Since we start and end with the same index,
there are no characters in between. The second example in this set throws an exception
because the indexes can’t be backward. Java knows perfectly well that it will never get
to index 2 if it starts with index 3. The third example says to continue until the eighth
character. There is no eighth position, so Java throws an exception. Granted, there is no
seventh character either, but at least there is the “end of string” invisible position.

&emsp;&emsp;
Let’s review this one more time since substring() is so tricky. The method returns the string
starting from the requested index. If an end index is requested, it stops right before that
index. Otherwise, it goes to the end of the string.
