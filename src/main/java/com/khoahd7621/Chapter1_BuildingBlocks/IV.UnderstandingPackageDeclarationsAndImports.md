# Understanding Package Declarations and Imports

Java comes with thousands of built-in classes, and there are countless more from developers
like you. With all those classes, Java needs a way to organize them. It handles this in a way
similar to a file cabinet. You put all your pieces of paper in folders. Java puts classes in
_packages_. These are logical groupings for classes. <br />

&emsp;&emsp;
We wouldn’t put you in front of a file cabinet and tell you to find a specific paper. Instead,
we’d tell you which folder to look in. Java works the same way. It needs you to tell it which
packages to look in to find code. <br />

&emsp;&emsp;
Suppose you try to compile this code:

```java
public class NumberPicker {
    public static void main(String[] args) {
        Random r = new Random(); // DOES NOT COMPILE
        System.out.println(r.nextInt(10));
    }
}
```

&emsp;&emsp;
The Java compiler helpfully gives you an error that looks like this:

```java
error: cannot find symbol
```

&emsp;&emsp;
This error could mean you made a typo in the name of the class. You double-check and
discover that you didn’t. The other cause of this error is omitting a _needed_ import statement.
A _statement_ is an instruction, and `import` statements tell Java which packages to look in for
classes. Since you didn’t tell Java where to look for `Random`, it has no clue. <br />

&emsp;&emsp;
Trying this again with the `import` allows the code to compile.

```java
import java.util.Random; // import tells us where to find Random
public class NumberPicker {
    public static void main(String[] args) {
        Random r = new Random();
        System.out.println(r.nextInt(10)); // a number 0-9
    }
}
```

&emsp;&emsp;
Now the code runs; it prints out a random number between 0 and 9. Just like arrays, Java
likes to begin counting with 0.

> #### Note:
> In Chapter 5, we cover another type of import referred to as a `static`
import. It allows you to make `static` members of a class known, often
so you can use variables and method names without having to keep specifying the class name.

## I. Packages
As you saw in the previous example, Java classes are grouped into packages. The `import`
statement tells the compiler which package to look in to find a class. This is similar to how
mailing a letter works. Imagine you are mailing a letter to `123 Main Street, Apartment 9`.
The mail carrier first brings the letter to `123 Main Street`. Then the carrier looks for the
mailbox for `apartment number 9`. The address is like the _package name_ in Java.
The apartment number is like the _class name_ in Java. Just as the mail carrier only looks
at apartment numbers in the building, Java only looks for class names in the package. <br />

&emsp;&emsp;
Package names are hierarchical like the mail as well. The postal service starts with the
top level, looking at your country first. You start reading a package name at the beginning
too. For example, if it begins with `java`, this means it came with the JDK. If it starts with
something else, it likely shows where it came from using the website name in reverse. For
example, `com.wiley.javabook` tells us the code is associated with the `wiley.com` website 
or organization. After the website name, you can add whatever you want. For example,
`com.wiley.java.my.name` also came from `wiley.com`. Java calls more detailed packages
child packages. The package `com.wiley.javabook` is a child package of `com.wiley`. You
can tell because it’s longer and thus more specific. <br />

&emsp;&emsp;
You’ll see package names on the exam that don’t follow this convention. Don’t be 
surprised to see package names like `a.b.c`. The rule for package names is that they are mostly
letters or numbers separated by periods `.`. Technically, you’re allowed a couple of other
characters between the periods `.`. You can even use package names of websites you don’t
own if you want to, such as `com.wiley`, although people reading your code might be 
confused! The rules are the same as for variable names, which you see later in this chapter. The
exam may try to trick you with invalid variable names. Luckily, it doesn’t try to trick you by
giving invalid package names. <br />

&emsp;&emsp;
In the following sections, we look at imports with wildcards, naming conflicts with
imports, how to create a package of your own, and how the exam formats code.

## II. Wildcards
Classes in the same package are often imported together. You can use a shortcut to `import` all
the classes in a package.

```java
import java.util.*; // imports java.util.Random among other things
public class NumberPicker {
    public static void main(String[] args) {
        Random r = new Random();
        System.out.println(r.nextInt(10));
    }
}
```

&emsp;&emsp;
In this example, we imported `java.util.Random` and a pile of other classes. The `*` is
a wildcard that matches all classes in the package. Every class in the `java.util` package
is available to this program when Java compiles it. The `import` statement doesn’t bring in
child packages, fields, or methods; it imports only classes directly under the package. Let’s
say you wanted to use the class `AtomicInteger` (you learn about that one in Chapter 13,
“Concurrency”) in the `java.util.concurrent.atomic` package. Which import or
imports support this?

```java
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
```

&emsp;&emsp;
Only the last import allows the class to be recognized because child packages are not
included with the first two. <br />

&emsp;&emsp;
You might think that including so many classes slows down your program execution, but
it doesn’t. The compiler figures out what’s actually needed. Which approach you choose is
personal preference—or team preference, if you are working with others on a team. Listing
the classes used makes the code easier to read, especially for new programmers. Using the
wildcard can shorten the import list. You’ll see both approaches on the exam.

## III. Redundant Imports
Wait a minute! We’ve been referring to `System` without an import every time we printed
text, and Java found it just fine. There’s one special package in the Java world called
`java.lang`. This package is special in that it is automatically imported. You can type this
package in an import statement, but you don’t have to. In the following code, how many of
the imports do you think are redundant?

```java
1:  import java.lang.System;
2:  import java.lang.*;
3:  import java.util.Random;
4:  import java.util.*;
5:  public class NumberPicker {
6:      public static void main(String[] args) {
7:          Random r = new Random();
8:          System.out.println(r.nextInt(10));
9:      }
10: }
```

&emsp;&emsp;
The answer is that three of the imports are redundant. Lines 1 and 2 are redundant because
everything in `java.lang` is automatically imported. Line 4 is also redundant in this example
because `Random` is already imported from `java.util.Random`. If line 3 wasn’t present,
`java.util.*` wouldn’t be redundant, though, since it would cover importing `Random`. <br />

&emsp;&emsp;
Another case of redundancy involves importing a class that is in the same package as the
class importing it. Java automatically looks in the current package for other classes. <br />

&emsp;&emsp;
Let’s take a look at one more example to make sure you understand the edge cases for
imports. For this example, `Files` and `Paths` are both in the package `java.nio.file`. The
exam may use packages you may never have seen before. The question will let you know
which package the class is in if you need to know that in order to answer the question. <br />

&emsp;&emsp;
Which `import` statements do you think would work to get this code to compile?

```java
public class InputImports {
    public void read(Files files) {
        Paths.get("name");
    }
}
```

&emsp;&emsp;
There are two possible answers. The shorter one is to use a wildcard to import both at the
same time.

```java
import java.nio.file.*;
```

&emsp;&emsp;
The other answer is to import both classes explicitly.

```java
import java.nio.file.Files;
import java.nio.file.Paths;
```

&emsp;&emsp;
Now let’s consider some imports that don’t work.

```java
import java.nio.*;              // NO GOOD - a wildcard only matches
                                // class names, not "file.Files"

import java.nio.*.*;            // NO GOOD - you can only have one wildcard
                                // and it must be at the end

import java.nio.file.Paths.*;   // NO GOOD - you cannot import methods
                                // only class names
```

## IV. Naming Conflicts
One of the reasons for using packages is so that class names don’t have to be unique across
all of Java. This means you’ll sometimes want to import a class that can be found in multiple 
places. A common example of this is the `Date` class. Java provides implementations of
`java.util.Date` and `java.sql.Date`. What import statement can we use if we want the
`java.util.Date` version?

```java
public class Conflicts {
    Date date;
    // some more code
}
```

&emsp;&emsp;
The answer should be easy by now. You can write either `import java.util.*;` or
`import java.util.Date;`. The tricky cases come about when other imports are present.

```java
import java.util.*;
import java.sql.*;  // causes Date declaration to not compile
```

&emsp;&emsp;
When the class name is found in multiple packages, Java gives you a compiler error. In
our example, the solution is easy—remove the `import java.sql.*` that we don’t need. But
what do we do if we need a whole pile of other classes in the `java.sql` package?


```java
import java.util.Date;
import java.sql.*;
```

&emsp;&emsp;
Ah, now it works! If you explicitly import a class name, it takes precedence over any
wildcards present. Java thinks, “The programmer really wants me to assume use of the
`java.util.Date` class.” <br />

&emsp;&emsp;
One more example. What does Java do with “ties” for precedence?

```java
import java.util.Date;
import java.sql.Date;
```

&emsp;&emsp;
Java is smart enough to detect that this code is no good. As a programmer, you’ve claimed
to explicitly want the default to be both the `java.util.Date` and `java.sql.Date` 
implementations. Because there can’t be two defaults, the compiler tells you the imports are
ambiguous.

> #### If You Really Need to Use Two Classes with the Same Name
> Sometimes you really do want to use Date from two different packages. When this 
happens, you can pick one to use in the `import` statement and use the other’s _fully qualified
class name_. Or you can drop both `import` statements and always use the fully qualified
class name.
> ```java
>   public class Conflicts {
>       java.util.Date date;
>       java.sql.Date sqlDate;
>   }
>```

## V. Creating a New Package
Up to now, all the code we’ve written in this chapter has been in the _default package_. This is
a special unnamed package that you should use only for throwaway code. You can tell the
code is in the default package, because there’s no package name. On the exam, you’ll see the
default package used a lot to save space in code listings. In real life, always name your 
packages to avoid naming conflicts and to allow others to reuse your code. <br />

&emsp;&emsp;
Now it’s time to create a new package. The directory structure on your computer is
related to the package name. In this section, just read along. We cover how to compile and
run the code in the next section. <br />

&emsp;&emsp;
Suppose we have these two classes:

```java
package packagea;
public class ClassA {}

package packageb;
import packagea.ClassA;
public class ClassB {
    public static void main(String[] args) {
        ClassA a;
        System.out.println("Got it");
    }
}
```

&emsp;&emsp;
When you run a Java program, Java knows where to look for those package names.
In this case, running from `C:\temp` works because both `packagea` and `packageb` are
underneath it.

## VI. Compiling and Running Code with Packages
You’ll learn Java much more easily by using the command line to compile and test your
examples. Once you know the Java syntax well, you can switch to an IDE. But for the exam,
your goal is to know details about the language and not have the IDE hide them for you. <br />

&emsp;&emsp;
Follow this example to make sure you know how to use the command line. If you have
any problems following this procedure, post a question in the _Beginning Java_ forum at
[CodeRanch](http://www.coderanch.com/forums/f-33/java). Describe what you tried and what the error said. <br />

&emsp;&emsp;
The first step is to create the two files from the previous section. Table 1.1 shows the
expected fully qualified filenames and the command to get into the directory for the
next steps.

> Table 1.1 Setup procedure by operating system

 | Step | Windows                       | Mac/Linux                 |
 | :--- |:------------------------------|:--------------------------|
 | 1. Create first class. | C:\temp\packagea\ClassA.java  | /tmp/packagea/ClassA.java |
 | 2. Create second class. | C:\temp\packageb\ClassB.java  | /tmp/packageb/ClassB.java |
 | 3. Go to directory. | cd C:\temp | cd /tmp |

&emsp;&emsp;
Now it is time to compile the code. Luckily, this is the same regardless of the operating
system. To compile, type the following command:

```bash
javac packagea/ClassA.java packageb/ClassB.java
```

&emsp;&emsp;
If this command doesn’t work, you’ll get an error message. Check your files carefully for
typos against the provided files. If the command does work, two new files will be created:
`packagea/ClassA.class` and `packageb/ClassB.class`.

> #### Compiling with Wildcards
> You can use an asterisk to specify that you’d like to include all Java files in a directory. This
is convenient when you have a lot of files in a package. We can rewrite the previous `javac`
command like this:
> ```bash
> javac packagea/*.java packageb/*.java
> ```
> However, you cannot use a wildcard to include subdirectories. If you were to write
`javac *.java`, the code in the packages would not be picked up.

&emsp;&emsp;
Now that your code has compiled, you can run it by typing the following command:

```bash
java packageb.ClassB
```

&emsp;&emsp;
If it works, you’ll see `Got it` printed. You might have noticed that we typed `ClassB`
rather than `ClassB.class`. As discussed earlier, you don’t pass the extension when running
a program. <br />

&emsp;&emsp;
Figure 1.1 shows where the .class files were created in the directory structure.

> #### Figure 1.1 Compiling with packages
> ```java
> ├── packagea
> │   ├── ClassA.java
> │   └── ClassA.class 
> │
> └── packageb
>     ├── ClassB.java
>     └── ClassB.class
> ```

# VII. Compiling to Another Directory
By default, the `javac` command places the compiled classes in the same directory as the
source code. It also provides an option to place the class files into a different directory. The
`-d` option specifies this target directory.

> #### Note:
> Java options are case sensitive. This means you cannot pass `-D` instead of `-d`.

&emsp;&emsp;
If you are following along, delete the `ClassA.class` and `ClassB.class` files that
were created in the previous section. Where do you think this command will create the file
`ClassA.class`?

```bash
javac -d classes packagea/ClassA.java packageb/ClassB.java
```

&emsp;&emsp;
The correct answer is in `classes/packagea/ClassA.class`. The package structure is
preserved under the requested target directory. Figure 1.2 shows this new structure.

> #### Figure 1.2 Compiling with packages and directories
> ```java
> ├── classes
> │   ├── packagea
> │   │   └── ClassA.class
> │   └── packageb
> │       └── ClassB.class
> │
> ├── packagea
> │   └── ClassA.java
> │
> └── packageb
>    └── ClassB.java
> ```

&emsp;&emsp;
To run the program, you specify the classpath so Java knows where to find the classes.
There are three options you can use. All three of these do the same thing:

```bash
java -cp classes packageb.ClassB
java -classpath classes packageb.ClassB
java --class-path classes packageb.ClassB
```

&emsp;&emsp;
Notice that the last one requires two dashes (--), while the first two require one dash (-).
If you have the wrong number of dashes, the program will not run.

> #### Three Classpath Options
> You might wonder why there are three options for the classpath. The `-cp` option is the
short form. Developers frequently choose the short form because we are lazy typists. The
`-classpath` and `--class-path` versions can be clearer to read but require more typing.

&emsp;&emsp;
Table 1.2 and Table 1.3 review the options you need to know for the exam. There are
_many_ other options available! And in Chapter 12, “Modules,” you learn additional options
specific to modules.

> Table 1.2 Important javac options

| Option                                                | Description |
|:------------------------------------------------------|:------------|
| -d <dir>                                              | Directory in which to place generated class files |
| -cp <classpath> <br /> -classpath <classpath> <br /> --class-path <classpath> | Location of classes needed to compile the program |

> Table 1.3 Important java options

|Option|Description|
|:-----|:----------|
| -cp <classpath> <br /> -classpath <classpath> <br /> --class-path <classpath> | Location of classes needed to run the program |

## VIII. Compiling with JAR Files
Just like the `classes` directory in the previous example, you can also specify the location
of the other files explicitly using a classpath. This technique is useful when the class files are
located elsewhere or in special JAR files. A _Java archive_ (JAR) file is like a ZIP file of mainly
Java class files. <br />

&emsp;&emsp;
On Windows, you type the following:

```bash
java -cp ".;C:\temp\someOtherLocation;c:\temp\myJar.jar" myPackage.MyClass
```

&emsp;&emsp;
And on macOS/Linux, you type this:

```bash
java -cp ".:/tmp/someOtherLocation:/tmp/myJar.jar" myPackage.MyClass
```

&emsp;&emsp;
The period `.` indicates that you want to include the current directory in the classpath. The
rest of the command says to look for loose class files (or packages) in `someOtherLocation`
and within `myJar.jar`. Windows uses semicolons `;` to separate parts of the classpath; other
operating systems use colons. <br />

&emsp;&emsp;
Just like when you’re compiling, you can use a wildcard `*` to match all the JARs in a
directory. Here’s an example:

```bash
java -cp "C:\temp\directoryWithJars\*" myPackage.MyClass
```

&emsp;&emsp;
This command will add to the classpath all the JARs that are in `directoryWithJars`. It
won’t include any JARs in the classpath that are in a subdirectory of `directoryWithJars`.


## IX. Creating a JAR File
Some JARs are created by others, such as those downloaded from the Internet or created
by a teammate. Alternatively, you can create a JAR file yourself. To do so, you use the `jar`
command. The simplest commands create a `jar` containing the files in the current directory.
You can use the short or long form for each option.

```bash
jar -cvf myNewFile.jar .
jar --create --verbose --file myNewFile.jar .
```

&emsp;&emsp;
Alternatively, you can specify a directory instead of using the current directory.

```bash
jar -cvf myNewFile.jar -C dir .
```

&emsp;&emsp;
There is no long form of the `-C` option. Table 1.4 lists the options you need to use the `jar`
command to create a JAR file. In Chapter 12, you see jar again for modules.

> Table 1.4 Important jar options

| Option              | Description |
|:--------------------|:------------|
| -c <br /> --create  | Creates a new JAR file |
| -v <br /> --verbose | Prints details when working with JAR files |
| -f <fileName> <br /> --file <fileName>  | JAR filename |
| -C <directory> | Directory containing files to be used to create the JAR |

## X. Ordering Elements in a Class
Now that you’ve seen the most common parts of a class, let’s take a look at the correct order
to type them into a file. Comments can go anywhere in the code. Beyond that, you need to
memorize the rules in Table 1.5.

> Table 1.5 Order for declaring a class

| **Element** | **Example** | **Required?** | **Where does it go?** |
|:------------|:------------|:--------------|:----------------------|
| Package declaration| package abc; |No| First line in the file (excluding comments or blank lines)|
| import statements| import java.util.*; |No| Immediately after the package (if present)|
| Top-level type declaration| public class C |Yes| Immediately after the import (if any)|
| Field declarations| int value; |No| Any top-level element within a class|
| Method declarations| void method() |No| Any top-level element within a class|

&emsp;&emsp;
Let’s look at a few examples to help you remember this. The first example contains one of
each element:

```java
package structure;      // package must be first non-comment
import java.util.*;     // import must come after package
public class Meerkat {  // then comes the class
    double weight;      // fields and methods can go in either order
    public double getWeight() {
        return weight; }
    double height;      // another field - they don't need to be together
}
```

&emsp;&emsp;
So far, so good. This is a common pattern that you should be familiar with. How
about this one?

```java
/* header */

package structure;


// class Meerkat
public class Meerkat { }
```

&emsp;&emsp;
Still good. We can put comments anywhere, blank lines are ignored, and imports are
optional. In the next example, we have a problem:

```java
import java.util.*;
package structure;          // DOES NOT COMPILE
String name;                // DOES NOT COMPILE
public class Meerkat { }    // DOES NOT COMPILE
```

&emsp;&emsp;
There are two problems here. One is that the `package` and `import` statements are
reversed. Although both are optional, `package` must come before `import` if present. The
other issue is that a field attempts a declaration outside a class. This is not allowed. Fields
and methods must be within a class. <br />

&emsp;&emsp;
Got all that? Think of the acronym PIC (picture): package, import, and class. Fields and
methods are easier to remember because they merely have to be inside a class.

> #### Note:
> Throughout this book, if you see two `public` classes in a code snippet or
question, you can assume they are in different files unless it specifically
says they are in the same `.java` file.

&emsp;&emsp;
Now you know how to create and arrange a class. Later chapters show you how to create
classes with more powerful operations.
