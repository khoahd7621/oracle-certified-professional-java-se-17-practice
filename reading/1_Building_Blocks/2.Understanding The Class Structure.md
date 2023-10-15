# Understanding the Class Structure

In Java programs, classes are the basic building blocks. When defining a `class`, you describe
all the parts and characteristics of one of those building blocks. In later chapters, you see
other building blocks such as interfaces, records, and enums. <br />

&emsp;&emsp;
To use most classes, you have to create objects. An `object` is a runtime instance of a class
in memory. An object is often referred to as an `instance` since it represents a single representation 
of the class. All the various objects of all the different classes represent the state of
your program. A `reference` is a variable that points to an object. <br />

&emsp;&emsp;
In the following sections, we look at fields, methods, and comments. We also explore the
relationship between classes and files.

## I. Fields and Methods
Java classes have two primary elements: `methods`, often called functions or procedures in
other languages, and `fields`, more generally known as variables. Together these are called the
*members* of the class. Variables hold the state of the program, and methods operate on that
state. If the change is important to remember, a variable stores that change. That’s all classes
really do. It’s the programmer’s job to create and arrange these elements in such a way that
the resulting code is useful and, ideally, easy for other programmers to understand. <br />

&emsp;&emsp;
The simplest Java class you can write looks like this:

```java
1:  public class Animal {
2:  }
```

&emsp;&emsp;
Java calls a word with special meaning a *keyword*, which we’ve marked bold in the
previous snippet. Throughout the book, we often bold parts of code snippets to call
attention to them. Line 1 includes the `public` keyword, which allows other classes to use
it. The `class` keyword indicates you’re defining a class. `Animal` gives the name of the class.
Granted, this isn’t an interesting class, so let’s add your first field.

```java
1:  public class Animal {
2:  String name;
3:  }
```

> #### Note:
> The line numbers aren’t part of the program; they’re just there to make
the code easier to talk about.

&emsp;&emsp;
On line 2, we define a variable named `name`. We also declare the type of that variable to
be `String`. A `String` is a value that we can put text into, such as "this is a string".
`String` is also a class supplied with Java. Next we can add methods.

```java
1:  public class Animal {
2:      String name;
3:      public String getName() {
4:          return name;
5:      }
6:      public void setName(String newName) {
7:          name = newName;
8:      }
9:  }
```

&emsp;&emsp;
On lines 3–5, we define a method. A method is an operation that can be called. Again,
`public` is used to signify that this method may be called from other classes. Next comes
the return type—in this case, the method returns a `String`. On lines 6–8 is another method.
This one has a special return type called `void`. The `void` keyword means that no value at all
is returned. This method requires that information be supplied to it from the calling method;
this information is called a `parameter`. The `setName()` method has one parameter named
`newName`, and it is of type `String`. This means the caller should pass in one `String` parameter 
and expect nothing to be returned. <br />

&emsp;&emsp;
The method name and parameter types are called the method signature. In this example,
can you identify the method name and parameters?

```java
public int numberVisitors(int month) {
    return 10;
}
```

&emsp;&emsp;
The method name is `numberVisitors`. There’s one parameter named `month`,
which is of type `int`, which is a numeric type. Therefore, the method signature is
`numberVisitors(int)`.

## II. Comments
Another common part of the code is called a _comment_. Because comments aren’t executable
code, you can place them in many places. Comments can make your code easier to read.
While the exam creators are trying to make the code harder to read, they still use comments
to call attention to line numbers. We hope you use comments in your own code. There are
three types of comments in Java. The first is called a single-line comment:

```java
// comment until end of line
```

&emsp;&emsp;
A single-line comment begins with two slashes. The compiler ignores anything you type
after that on the same line. Next comes the multiple-line comment:

```java
/* Multiple
 * line comment
 */
```

&emsp;&emsp;
A multiple-line comment (also known as a multiline comment) includes anything starting
from the symbol `/*` until the symbol `*/`. People often type an asterisk `*` at the beginning of
each line of a multiline comment to make it easier to read, but you don’t have to. Finally, we
have a Javadoc comment:

```java
/**
 * Javadoc multiple-line comment
 * @author Jeanne and Scott
 */
```

&emsp;&emsp;
This comment is similar to a multiline comment, except it starts with `/**`. This special
syntax tells the Javadoc tool to pay attention to the comment. Javadoc comments have a
specific structure that the Javadoc tool knows how to read. You probably won’t see a
Javadoc comment on the exam. Just remember it exists so you can read up on it online when
you start writing programs for others to use. <br />

&emsp;&emsp;
As a bit of practice, can you identify which type of comment each of the following six
words is in? Is it a single-line or a multiline comment?

```java
/*
 * // anteater
 */

// bear

// // cat

// /* dog */

/* elephant */

/*
 * /* ferret */
 */
```

&emsp;&emsp;
Did you look closely? Some of these are tricky. Even though comments technically aren’t
on the exam, it is good to practice looking at code carefully. <br />

&emsp;&emsp;
Okay, on to the answers. The comment containing anteater is in a multiline comment.
Everything between `/*` and `*/` is part of a multiline comment—even if it includes a single-line
comment within it! The comment containing bear is your basic single-line comment. The
comments containing cat and dog are also single-line comments. Everything from `//` to the
end of the line is part of the comment, even if it is another type of comment. The comment
containing elephant is your basic multiline comment, even though it only takes up one line.

&emsp;&emsp;
The line with `ferret` is interesting in that it doesn’t compile. Everything from the first `/*` to
the first `*/` is part of the comment, which means the compiler sees something like this:

```java
/* */ */
```

&emsp;&emsp;
We have a problem. There is an extra `*/`. That’s not valid syntax—a fact the compiler is
happy to inform you about.

## III. Classes and Source Files
Most of the time, each Java class is defined in its own `.java` file. In this chapter, the only 
top-level type is a class. A `top-level` type is a data structure that can be defined independently
within a source file. For the majority of the book, we work with classes as the top-level type,
but in Chapter 7, “Beyond Classes,” we present other top-level types, as well as nested types. <br />

&emsp;&emsp;
A top-level class is often `public`, which means any code can call it. Interestingly, Java does
not require that the type be `public`. For example, this class is just fine:

```java
1:  class Animal {
2:      String name;
3:  }
```

&emsp;&emsp;
You can even put two types in the same file. When you do so, _at most one_ of the 
top-level types in the file is allowed to be `public`. That means a file containing the following is
also fine:

```java
1:  public class Animal {
2:      private String name;
3:  }
4:  class Animal2 {}
```

&emsp;&emsp;
If you do have a `public` type, it needs to match the filename. The declaration
`public class Animal2` would not compile in a file named _Animal.java_. In Chapter 5,
“Methods,” we discuss what access options are available other than public.

> #### Note:
> Noticing a pattern yet? This chapter includes numerous references to
topics that we go into in more detail in later chapters. If you’re an 
experienced Java developer, you’ll notice we keep a lot of the examples and
rules simple in this chapter. Don’t worry; we have the rest of the book to
present more rules and complicated edge cases!
