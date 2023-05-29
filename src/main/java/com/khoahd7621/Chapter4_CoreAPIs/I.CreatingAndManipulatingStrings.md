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

### &emsp;&emsp; 5. Adjusting Case
Whew. After that mental exercise, it is nice to have methods that act exactly as they sound!
These methods make it easy to convert your data. The method signatures are as follows:

```java
public String toLowerCase()
public String toUpperCase()
```

&emsp;&emsp;
The following code shows how to use these methods:

```java
var name = "animals";
System.out.println(name.toUpperCase());     // ANIMALS
System.out.println("Abc123".toLowerCase()); // abc123
```

&emsp;&emsp;
These methods do what they say. The toUpperCase() method converts any lowercase
characters to uppercase in the returned string. The toLowerCase() method converts any
uppercase characters to lowercase in the returned string. These methods leave alone any
characters other than letters. Also, remember that strings are immutable, so the original
string stays the same.

### &emsp;&emsp; 6. Checking for Equality
The equals() method checks whether two String objects contain exactly the same characters in the same order. The equalsIgnoreCase() method checks whether two String
objects contain the same characters, with the exception that it ignores the characters’ case.
The method signatures are as follows:

```java
public boolean equals(Object obj)
public boolean equalsIgnoreCase(String str)
```

&emsp;&emsp;
You might have noticed that equals() takes an Object rather than a String. This is
because the method is the same for all objects. If you pass in something that isn’t a String,
it will just return false. By contrast, the equalsIgnoreCase() method only applies to
String objects, so it can take the more specific type as the parameter.

&emsp;&emsp;
In Java, String values are case-sensitive. That means "abc" and "ABC" are considered different values. 
With that in mind, the following code shows how to use these methods:

```java
System.out.println("abc".equals("ABC"));            // false
System.out.println("ABC".equals("ABC"));            // true
System.out.println("abc".equalsIgnoreCase("ABC"));  // true
```

&emsp;&emsp;
This example should be fairly intuitive. In the first example, the values aren’t exactly the
same. In the second, they are exactly the same. In the third, they differ only by case, but it is
okay because we called the method that ignores differences in case.

> **Note** <br />
> The exam may try to trick you by using the == operator instead of the equals() method. Remember that the == operator checks whether two variables refer to the same object. The equals() method checks whether two objects are equal, which is a more complex process.

> **Overriding *toString()*, *equals(Object)*, and *hashCode()*** <br />
> 
> Knowing how to properly override toString(), equals(Object), and hashCode()
was part of Java certification exams in the past. As a professional Java developer, it is still
important for you to know at least the basic rules for overriding each of these methods:
> - toString(): The toString() method is called when you try to print an object or
    concatenate the object with a String. It is commonly overridden with a version that
    prints a unique description of the instance using its instance fields.
> - equals(Object): The equals(Object) method is used to compare objects,
    with the default implementation just using the == operator. You should override the
    equals(Object) method any time you want to conveniently compare elements for
    equality, especially if this requires checking numerous fields.
> - hashCode(): Any time you override equals(Object), you must override
    hashCode() to be consistent. This means that for any two objects, if a.equals(b) is
    true, then a.hashCode() == b.hashCode() must also be true. If they are not consistent, this could lead to invalid data and side effects in hash-based collections such as
    HashMap and HashSet.

### &emsp;&emsp; 7. Searching for Substrings
Often, you need to search a larger string to determine if a substring is contained within it.
The startsWith() and endsWith() methods look at whether the provided value matches part
of the String. The contains() method isn’t as particular; it looks for matches anywhere in the
String. The method signatures are as follows:

```java
public boolean startsWith(String prefix)
public boolean endsWith(String suffix)
public boolean contains(CharSequence charSeq)
```

&emsp;&emsp;
The following code shows how to use these methods:

```java
System.out.println("abc".startsWith("a")); // true
System.out.println("abc".startsWith("A")); // false
        
System.out.println("abc".endsWith("c")); // true
System.out.println("abc".endsWith("a")); // false
        
System.out.println("abc".contains("b")); // true
System.out.println("abc".contains("B")); // false
```

&emsp;&emsp;
Again, nothing surprising here. Java is doing a case-sensitive check on the values
provided. Note that the contains() method is a convenience method so you don’t have to
write `str.indexOf(otherString) != -1`

### &emsp;&emsp; 8. Replacing Values
The replace() method does a simple search and replace on the string. There’s a version that
takes char parameters as well as a version that takes CharSequence parameters. The method
signatures are as follows:

```java
public String replace(char oldChar, char newChar)
public String replace(CharSequence target, CharSequence replacement)
```

&emsp;&emsp;
The following code shows how to use these methods:

```java
System.out.println("abcabc".replace('a', 'A')); // AbcAbc
System.out.println("abcabc".replace("a", "A")); // AbcAbc
```

&emsp;&emsp;
The first example uses the first method signature, passing in char parameters. The second
example uses the second method signature, passing in String parameters.

### &emsp;&emsp; 9. Removing Whitespace
These methods remove blank space from the beginning and/or end of a String. The *strip()*
and *trim()* methods remove whitespace from the beginning and end of a String. In terms of
the exam, whitespace consists of spaces along with the \t (tab) and \n (newline) characters.
Other characters, such as \r (carriage return), are also included in what gets trimmed. The
*strip()* method does everything that *trim()* does, but it supports Unicode.

> **Note** <br />
> You don’t need to know about Unicode for the exam. But if you want
to test the difference, one of the Unicode whitespace characters is as
follows:
`char ch = '\u2000';`

&emsp;&emsp;
Additionally, the stripLeading() method removes whitespace from the beginning of
the String and leaves it at the end. The stripTrailing() method does the opposite. It
removes whitespace from the end of the String and leaves it at the beginning. The method
signatures are as follows:

```java
public String strip()
public String stripLeading()
public String stripTrailing()
public String trim()
```

&emsp;&emsp;
The following code shows how to use these methods:

```java
System.out.println("abc".strip());                  // abc
System.out.println("\t a b c\n".strip());           // a b c
        
String text = " abc\t ";
System.out.println(text.trim().length());           // 3
System.out.println(text.strip().length());          // 3
System.out.println(text.stripLeading().length());   // 5
System.out.println(text.stripTrailing().length());  // 4
```

&emsp;&emsp;
First, remember that \t is a single character. The backslash escapes the t to represent a
tab. The first example prints the original string because there are no whitespace characters at
the beginning or end. The second example gets rid of the leading tab, subsequent spaces, and
the trailing newline. It leaves the spaces that are in the middle of the string.

&emsp;&emsp;
The remaining examples just print the number of characters remaining. You can see that
trim() and strip() leave the same three characters "abc" because they remove both
the leading and trailing whitespace. The stripLeading() method only removes the one
whitespace character at the beginning of the String. It leaves the tab and space at the end.
The stripTrailing() method removes these two characters at the end but leaves the
character at the beginning of the String.

### &emsp;&emsp; 10. Working with Indentation
Now that Java supports text blocks, it is helpful to have methods that deal with indentation.
Both of these are a little tricky, so read carefully!

```java
public String indent(int numberSpaces)
public String stripIndent()
```

&emsp;&emsp;
The indent() method adds the same number of blank spaces to the beginning of each
line if you pass a positive number. If you pass a negative number, it tries to remove that
number of whitespace characters from the beginning of the line. If you pass zero, the indentation will not change.

> **Note** <br />
> If you call indent() with a negative number and try to remove more
whitespace characters than are present at the beginning of the line, Java
will remove all that it can find.
 
&emsp;&emsp;
This seems straightforward enough. However, indent() also normalizes whitespace
characters. What does normalizing whitespace mean, you ask? First, a line break is added
to the end of the string if not already there. Second, any line breaks are converted to the
\n format. Regardless of whether your operating system uses \r\n (Windows) or \n (Mac/
Unix), Java will standardize on \n for you.

&emsp;&emsp;
The stripIndent() method is useful when a String was built with concatenation rather than
using a text block. It gets rid of all incidental whitespace. This means that all non-blank lines
are shifted left so the same number of whitespace characters are removed from each line and
the first character that remains is not blank. Like indent(), \r\n is turned into \n. However, the
stripIndent() method does not add a trailing line break if it is missing.

&emsp;&emsp;
Well, that was a lot of rules. Table below provides a reference to make them easier to remember.

| Method                | Indent change                           |Normalizes existing line breaks|Adds line break at end if missing|
|-----------------------|-----------------------------------------|-------------------------------|---------------------------------|
| indent(n) where n > 0 | Adds n spaces to beginning of each line |Yes|Yes|
| indent(n) where n < 0 | Removes up to n spaces from each line where the same number of characters is removed from each non-blank line |Yes|Yes|
| indent(n) where n = 0 | No change                               |Yes|Yes|
| stripIndent()         | Removes all leading incidental whitespace|Yes|No|

&emsp;&emsp;
The following code shows how to use these methods. Don’t worry if the results aren’t
what you expect. We explain each one.

```java
10: var block = """
11:             a
12:              b
13:             c""";
14: var concat = " a\n"
15:            + " b\n"
16:            + " c";
17: System.out.println(block.length());                 // 6
18: System.out.println(concat.length());                // 9
19: System.out.println(block.indent(1).length());       // 10
20: System.out.println(concat.indent(-1).length());     // 7
21: System.out.println(concat.indent(-4).length());     // 6
22: System.out.println(concat.stripIndent().length());  // 6
```

- Lines 10–16 create similar strings using a text block and a regular String, respectively.
We say “similar” because concat has a whitespace character at the beginning of each line
while block does not.
- Line 17 counts the six characters in block, which are the three letters, the blank space
before b, and the \n after a and b. Line 18 counts the nine characters in concat, which are
the three letters, one blank space before a, two blank spaces before b, one blank space before
c, and the \n after a and b. Count them up yourself. If you don’t understand which characters are counted, it will only get more confusing.
- On line 19, we ask Java to add a single blank space to each of the three lines in block.
However, the output says we added 4 characters rather than 3 since the length went from 6
to 10. This mysterious additional character is thanks to the line termination normalization.
Since the text block doesn’t have a line break at the end, indent() adds one!
- On line 20, we remove one whitespace character from each of the three lines of concat.
This gives a length of seven. We started with nine, got rid of three characters, and added a
trailing normalized new line.
- On line 21, we ask Java to remove four whitespace characters from the same three lines.
Since there are not four whitespace characters, Java does its best. The single space is removed
before a and c. Both spaces are removed before b. The length of six should make sense here;
we removed one more character here than on line 20.
- Finally, line 22 uses the stripIndent() method. All of the lines have at least one
whitespace character. Since they do not all have two whitespace characters, the method only
gets rid of one character per line. Since no new line is added by stripIndent(), the length
is six, which is three less than the original nine.

### &emsp;&emsp; 11. Translating Escapes

When we escape characters, we use a single backslash. For example, \t is a tab. If we don’t
want this behavior, we add another backslash to escape the backslash, so \\t is the literal
string \t. The translateEscapes() method takes these literals and turns them into the
equivalent escaped character. The method signature is as follows:

```java
public String translateEscapes()
```

&emsp;&emsp;
The following code shows how to use these methods:

```java
var str = "1\\t2";
System.out.println(str);                    // 1\t2
System.out.println(str.translateEscapes()); // 1    2
```

&emsp;&emsp;
The first line prints the literal string \t because the backslash is escaped. The second
line prints an actual tab since we translated the escape. This method can be used for escape
sequences such as \t (tab), \n (new line), \s (space), \" (double quote), and \' (single quote.)

### &emsp;&emsp; 12. Checking for Empty or Blank *Strings*
Java provides convenience methods for whether a String has a length of zero or contains only
whitespace characters. The method signatures are as follows:

```java
public boolean isEmpty()
public boolean isBlank()
```

&emsp;&emsp;
The following code shows how to use these methods:

```java
System.out.println(" ".isEmpty());  // false
System.out.println("".isEmpty());   // true
System.out.println(" ".isBlank());  // true
System.out.println("".isBlank());   // true
```

&emsp;&emsp;
The first line prints false because the String is not empty; it has a blank space in it.
The second line prints true because this time, there are no characters in the String. The
final two lines print true because there are no characters other than whitespace present.

### &emsp;&emsp; 13. Formatting Values
There are methods to format String values using formatting flags. Two of the methods take
the format string as a parameter, and the other uses an instance for that value. One method
takes a Locale, which you learn about in Chapter 11.

&emsp;&emsp;
The method parameters are used to construct a formatted String in a single method call,
rather than via a lot of format and concatenation operations. They return a reference to the
instance they are called on so that operations can be chained together. The method signatures are as follows:

```java
public static String format(String format, Object args...)
public static String format(Locale loc, String format, Object args...)
public String formatted(Object args...)
```

&emsp;&emsp;
The following code shows how to use these methods:

```java
var name = "Kate";
var orderId = 5;

// All print: Hello Kate, order 5 is ready
System.out.println("Hello " + name + ", order " + orderId + " is ready");
System.out.println(String.format("Hello %s, order %d is ready", name, orderId));
System.out.println("Hello %s, order %d is ready".formatted(name, orderId));
```

&emsp;&emsp;
In the format() and formatted() operations, the parameters are inserted and formatted via symbols in the order that 
they are provided in the vararg. Table below lists the ones you should know for the exam.

| Symbol | Description                                                     |
|--------|-----------------------------------------------------------------|
| %s     | Applies to any type, commonly String values                     |
| %d     | Applies to integer values like int and long                     |
| %f     | Applies to floating-point values like float and double          |
| %n     | Inserts a line break using the system-dependent line separator  |

&emsp;&emsp;
The following example uses all four symbols from Table above:

```java
var name = "James";
var score = 90.25;
var total = 100;
System.out.println("%s:%n Score: %f out of %d".formatted(name, score, total));
```

&emsp;&emsp;
This prints the following:

```java
James:
        Score: 90.250000 out of 100
```

&emsp;&emsp;
Mixing data types may cause exceptions at runtime. For example, the following throws
an exception because a floating-point number is used when an integer value is expected:

```java
var str = "Food: %d tons".formatted(2.0); // IllegalFormatConversionException
```

> **Using *format()* with Flags** <br />
> 
> Besides supporting symbols, Java also supports optional flags between the % and the
symbol character. In the previous example, the floating-point number was printed as
90.250000. By default, %f displays exactly six digits past the decimal. If you want to
display only one digit after the decimal, you can use %.1f instead of %f. The format()
method relies on rounding rather than truncating when shortening numbers. For example,
90.250000 will be displayed as 90.3 (not 90.2) when passed to format() with %.1f.
> The format() method also supports two additional features. You can specify the total
length of output by using a number before the decimal symbol. By default, the method will
fill the empty space with blank spaces. You can also fill the empty space with zeros by placing a single zero before the decimal symbol. The following examples use brackets, [], to
show the start/end of the formatted value:
> ```java
> var pi = 3.14159265359;
> System.out.format("[%f]", pi);     // [3.141593]
> System.out.format("[%12.8f]", pi); // [  3.14159265]
> System.out.format("[%012f]", pi);  // [00003.141593]
> System.out.format("[%12.2f]", pi); // [        3.14]
> System.out.format("[%.3f]", pi);   // [3.142]
> ```
> The format() method supports a lot of other symbols and flags. You don’t need to know
any of them for the exam beyond what we’ve discussed already.

### &emsp;&emsp; 14. Method Chaining
Ready to put together everything you just learned about? It is common to call multiple methods as shown here:

```java
var start = "AniMaL ";
var trimmed = start.trim();                 // "AniMaL"
var lowercase = trimmed.toLowerCase();      // "animal"
var result = lowercase.replace('a', 'A');   // "AnimAl"
System.out.println(result);
```

&emsp;&emsp;
This is just a series of String methods. Each time one is called, the returned value is put
in a new variable. There are four String values along the way, and AnimAl is output.

&emsp;&emsp;
However, on the exam, there is a tendency to cram as much code as possible into a small
space. You’ll see code using a technique called method chaining. Here’s an example:

```java
String result = "AniMaL   ".trim().toLowerCase().replace('a', 'A');
System.out.println(result);
```

&emsp;&emsp;
This code is equivalent to the previous example. It also creates four String objects and
outputs AnimAl. To read code that uses method chaining, start at the left and evaluate the
first method. Then call the next method on the returned value of the first method. Keep
going until you get to the semicolon.

&emsp;&emsp;
What do you think the result of this code is?

```java
5: String a = "abc";
6: String b = a.toUpperCase();
7: b = b.replace("B", "2").replace('C', '3');
8: System.out.println("a=" + a);
9: System.out.println("b=" + b);
```

&emsp;&emsp;
On line 5, we set a to point to "abc" and never pointed a to anything else. Since none of
the code on lines 6 and 7 changes a, the value remains "abc".

&emsp;&emsp;
However, b is a little trickier. Line 6 has b pointing to "ABC", which is straightforward.
On line 7, we have method chaining. First, "ABC".replace("B", "2") is called. This
returns "A2C". Next, "A2C".replace('C', '3') is called. This returns "A23". Finally, b
changes to point to this returned String. When line 9 executes, b is "A23".
