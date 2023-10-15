# Declaring Variables

You’ve seen some variables already. A _variable_ is a name for a piece of memory that stores
data. When you declare a variable, you need to state the variable type along with giving it a
name. Giving a variable a value is called _initializing_ a variable. To initialize a variable, you
just type the variable name followed by an equal sign, followed by the desired value. This
example shows declaring and initializing a variable in one line:

```java
String zooName = "The Best Zoo";
```

&emsp;&emsp;
In the following sections, we look at how to properly define variables in one or multiple lines.

## I. Identifying Identifiers

It probably comes as no surprise to you that Java has precise rules about identifier names.
An _identifier_ is the name of a variable, method, class, interface, or package. Luckily, the rules
for identifiers for variables apply to all of the other types that you are free to name. <br />

&emsp;&emsp;
There are only four rules to remember for legal identifiers:
- Identifiers must begin with a letter, a currency symbol, or a _ symbol. Currency symbols
include dollar ($), yuan (¥), euro (€), and so on.
- Identifiers can include numbers but not start with them.
- A single underscore _ is not allowed as an identifier.
- You cannot use the same name as a Java reserved word. A _reserved word_ is a special
word that Java has held aside so that you are not allowed to use it. Remember that Java
is case sensitive, so you can use versions of the keywords that only differ in case. Please
don’t, though.

Don’t worry—you won’t need to memorize the full list of reserved words. The exam will
only ask you about ones that are commonly used, such as class and for. Table 1.9 lists all
of the reserved words in Java.

> #### Table 1.9 Reserved words

||||||
|---|---|---|---|---|
|abstract |assert |boolean |break |byte|
|case |catch |char |class |const*|
|continue |default |do |double |else|
|enum |extends |final |finally |float|
|for |goto* |if |implements |import|
|instanceof |int |interface |long |native|
|new |package |private |protected |public|
|return| short |static strictfp |super|
|switch |synchronized |this| throw| throws|
|transient| try |void |volatile| while|

_*The reserved words `const` and `goto` aren’t actually used in Java. They are reserved so that people 
coming from other programming languages don’t use them by accident—and, in theory, in case Java wants to use
them one day._ <br />

&emsp;&emsp;
There are other names that you can’t use. For example, `true`, `false`, and `null` are literal
values, so they can’t be variable names. Additionally, there are contextual keywords like
`module` in Chapter 12. Prepare to be tested on these rules. The following examples are legal:

```java
long okidentifier;
float $OK2Identifier;
boolean _alsoOK1d3ntifi3r;
char __SStillOkbutKnotsonice$;
```

&emsp;&emsp;
These examples are not legal:

```java
int 3DPointClass;       // identifiers cannot begin with a number
byte hollywood@vine;    // @ is not a letter, digit, $ or _
String *$coffee;        // * is not a letter, digit, $ or _
double public;          // public is a reserved word
short _;                // a single underscore is not allowed
```

> ### camelCase and snake_case
> Although you can do crazy things with identifier names, please don’t. Java has conventions
so that code is readable and consistent. For example, _camel case_ has the first letter of each
word capitalized. Method and variable names are typically written in camel case with the
first letter lowercase, such as `toUpper()`. Class and interface names are also written in
camel case, with the first letter uppercase, such as `ArrayList`. <br /><br />
> Another style is called _snake case_. It simply uses an underscore (_) to separate words.
Java generally uses uppercase snake case for constants and enum values, such as
`NUMBER_FLAGS`. <br /><br />
> The exam will not always follow these conventions to make questions about identifiers
trickier. By contrast, questions on other topics generally do follow standard conventions. We
recommend you follow these conventions on the job.

## II. Declaring Multiple Variables
You can also declare and initialize multiple variables in the same statement. How many 
variables do you think are declared and initialized in the following example?

```java
void sandFence() {
    String s1, s2;
    String s3 = "yes", s4 = "no";
}
```

&emsp;&emsp;
Four `String` variables were declared: `s1`, `s2`, `s3`, and `s4`. You can declare many variables
in the same declaration as long as they are all of the same type. You can also initialize any or
all of those values inline. In the previous example, we have two initialized variables: `s3` and
`s4`. The other two variables remain declared but not yet initialized. <br />

&emsp;&emsp;
This is where it gets tricky. Pay attention to tricky things! The exam will attempt to
trick you. Again, how many variables do you think are declared and initialized in the following code?

```java
void paintFence() {
    int i1, i2, i3 = 0;
}
```

&emsp;&emsp;
As you should expect, three variables were declared: i1, i2, and i3. However, only one
of those values was initialized: i3. The other two remain declared but not yet initialized.
That’s the trick. Each snippet separated by a comma is a little declaration of its own. The
initialization of i3 only applies to i3. It doesn’t have anything to do with i1 or i2 despite
being in the same statement. As you will see in the next section, you can’t actually use i1 or
i2 until they have been initialized. <br />

&emsp;&emsp;
Another way the exam could try to trick you is to show you code like this line:

```java
int num, String value;  // DOES NOT COMPILE
```

&emsp;&emsp;
This code doesn’t compile because it tries to declare multiple variables of different types
in the same statement. The shortcut to declare multiple variables in the same statement is
legal only when they share a type.

> ### Note:
> _Legal_, _valid_, and _compiles_ are all synonyms in the Java exam world. We
try to use all the terminology you could encounter on the exam.

&emsp;&emsp;
To make sure you understand this, see if you can figure out which of the following are
legal declarations:

```java
4:  boolean b1, b2;
5:  String s1 = "1", s2;
6:  double d1, double d2;
7:  int i1; int i2;
8:  int i3; i4;
```

&emsp;&emsp;
Lines 4 and 5 are legal. They each declare two variables. Line 4 doesn’t initialize either
variable, and line 5 initializes only one. Line 7 is also legal. Although `int` does appear twice,
each one is in a separate statement. A semicolon `;` separates statements in Java. It just so
happens there are two completely different statements on the same line. <br />

&emsp;&emsp;
Line 6 is _not_ legal. Java does not allow you to declare two different types in the same
statement. Wait a minute! Variables `d1` and `d2` are the same type. They are both of type
double. Although that’s true, it still isn’t allowed. If you want to declare multiple variables
in the same statement, they must share the same type declaration and not repeat it. <br />

&emsp;&emsp;
Line 8 is _not_ legal. Again, we have two completely different statements on the same line.
The second one on line 8 is not a valid declaration because it omits the type. When you see
an oddly placed semicolon on the exam, pretend the code is on separate lines and think
about whether the code compiles that way. In this case, the last two lines of code could be
rewritten as follows:

```java
int i1;
int i2;
int i3;
i4;
```

&emsp;&emsp;
Looking at the last line on its own, you can easily see that the declaration is invalid. And
yes, the exam really does cram multiple statements onto the same line—partly to try to trick
you and partly to fit more code on the screen. In the real world, please limit yourself to one
declaration per statement and line. Your teammates will thank you for the readable code.
