# Initializing Variables

Before you can use a variable, it needs a value. Some types of variables get this value set
automatically, and others require the programmer to specify it. In the following sections, we
look at the differences between the defaults for local, instance, and class variables.

## I. Creating Local Variables

A _local variable_ is a variable defined within a constructor, method, or initializer block. For
simplicity, we focus primarily on local variables within methods in this section, although the
rules for the others are the same.

### &emsp;&emsp; 1. Final Local Variables

The `final` keyword can be applied to local variables and is equivalent to declaring constants
in other languages. Consider this example:

```java
5: final int y = 10;
6: int x = 20;
7: y = x + 10; // DOES NOT COMPILE
```

&emsp;&emsp;
Both variables are set, but `y` uses the `final` keyword. For this reason, line 7 triggers a
compiler error since the value cannot be modified. <br />

&emsp;&emsp;
The `final` modifier can also be applied to local variable references. The following example
uses an `int[]` array object, which you learn about in Chapter 4.

```java
5:  final int[] favoriteNumbers = new int[10];
6:  favoriteNumbers[0] = 10;
7:  favoriteNumbers[1] = 20;
8:  favoriteNumbers = null; // DOES NOT COMPILE
```

&emsp;&emsp;
Notice that we can modify the content, or data, in the array. The compiler error isn’t until
line 8, when we try to change the value of the reference `favoriteNumbers`.

### &emsp;&emsp; 2. Uninitialized Local Variables

Local variables do not have a default value and must be initialized before use. Furthermore,
the compiler will report an error if you try to read an uninitialized value. For example, the
following code generates a compiler error:

```java
4:  public int notValid() {
5:      int y = 10;
6:      int x;
7:      int reply = x + y; // DOES NOT COMPILE
8:      return reply;
9:  }
```

&emsp;&emsp;
The `y` variable is initialized to `10`. By contrast, `x` is not initialized before it is used in the
expression on line 7, and the compiler generates an error. The compiler is smart enough to
recognize variables that have been initialized after their declaration but before they are used.
Here’s an example:

```java
public int valid() {
    int y = 10;
    int x; // x is declared here
    x = 3; // x is initialized here
    int z; // z is declared here but never initialized or used
    int reply = x + y;
    return reply;
}
```

&emsp;&emsp;
In this example, `x` is declared, initialized, and used in separate lines. Also, `z` is declared
but never used, so it is not required to be initialized. <br />

&emsp;&emsp;
The compiler is also smart enough to recognize initializations that are more complex. In
this example, there are two branches of code:

```java
public void findAnswer(boolean check) {
    int answer;
    int otherAnswer;
    int onlyOneBranch;
    if (check) {
        onlyOneBranch = 1;
        answer = 1;
    } else {
        answer = 2;
    }
    System.out.println(answer);
    System.out.println(onlyOneBranch); // DOES NOT COMPILE
}
```

&emsp;&emsp;
The `answer` variable is initialized in both branches of the `if` statement, so the 
compiler is perfectly happy. It knows that regardless of whether `check` is `true` or `false`, the
value answer will be set to something before it is used. The `otherAnswer` variable is not
initialized but never used, and the compiler is equally as happy. Remember, the compiler is
only concerned if you try to use uninitialized local variables; it doesn’t mind the ones you
never use. <br />

&emsp;&emsp;
The `onlyOneBranch` variable is initialized only if `check` happens to be true. The compiler
knows there is the possibility for `check` to be `false`, resulting in uninitialized code, and gives a
compiler error. You learn more about the if statement in Chapter 3, “Making Decisions.”

> ### Note:
> On the exam, be wary of any local variable that is declared but not 
initialized in a single line. This is a common place on the exam that could result
in a “Does not compile” answer. Be sure to check to make sure it’s initialized 
before it’s used on the exam.

## II. Passing Constructor and Method Parameters

Variables passed to a constructor or method are called _constructor parameters_ or _method
parameters_, respectively. These parameters are like local variables that have been 
preinitialized. The rules for initializing constructor and method parameters are the same, so we
focus primarily on method parameters. <br />

&emsp;&emsp;
In the previous example, check is a method parameter.

```java
public void findAnswer(boolean check) {}
```

&emsp;&emsp;
Take a look at the following method `checkAnswer()` in the same class:

```java
public void checkAnswer() {
    boolean value;
    findAnswer(value); // DOES NOT COMPILE
}
```

&emsp;&emsp;
The call to `findAnswer()` does not compile because it tries to use a variable that is not
initialized. While the caller of a method `checkAnswer()` needs to be concerned about the
variable being initialized, once inside the method `findAnswer()`, we can assume the local
variable has been initialized to some value.

## III. Defining Instance and Class Variables

Variables that are not local variables are defined either as instance variables or as class 
variables. An _instance variable_, often called a field, is a value defined within a specific instance of
an object. Let’s say we have a `Person` class with an instance variable name of type `String`.
Each instance of the class would have its own value for name, such as `Elysia` or `Sarah`.
Two instances could have the same value for name, but changing the value for one does not
modify the other. <br />

&emsp;&emsp;
On the other hand, a _class variable_ is one that is defined on the class level and shared
among all instances of the class. It can even be publicly accessible to classes outside the
class and doesn’t require an instance to use. In our previous `Person` example, a shared class
variable could be used to represent the list of people at the zoo today. You can tell a 
variable is a class variable because it has the keyword `static` before it. You learn about this in
Chapter 5. For now, just know that a variable is a class variable if it has the `static` 
keyword in its declaration. <br />

&emsp;&emsp;
Instance and class variables do not require you to initialize them. As soon as you declare
these variables, they are given a default value. The compiler doesn’t know what value to use
and so wants the simplest value it can give the type: `null` for an object, `zero` for the numeric
types, and `false` for a boolean. You don’t need to know the default value for char, but in
case you are curious, it is '\u0000' (NUL).

## IV. Inferring the Type with _var_
You have the option of using the keyword `var` instead of the type when declaring local 
variables under certain conditions. To use this feature, you just type var instead of the primitive
or reference type. Here’s an example:

```java
public class Zoo {
    public void whatTypeAmI() {
        var name = "Hello";
        var size = 7;
    }
}
```

&emsp;&emsp;
The formal name of this feature is _local variable type inference_. Let’s take that apart. First
comes _local variable_. This means just what it sounds like. You can only use this feature for
local variables. The exam may try to trick you with code like this:

```java
public class VarKeyword {
    var tricky = "Hello"; // DOES NOT COMPILE
}
```

&emsp;&emsp;
Wait a minute! We just learned the difference between instance and local variables. The
variable `tricky` is an instance variable. Local variable type inference works with local 
variables and not instance variables.

### &emsp;&emsp; 1. Type Inference of _var_

Now that you understand the local variable part, it is time to go on to what _type inference_
means. The good news is that this also means what it sounds like. When you type `var`, you
are instructing the compiler to determine the type for you. The compiler looks at the code on
the line of the declaration and uses it to infer the type. Take a look at this example:

```java
7:  public void reassignment() {
8:      var number = 7;
9:      number = 4;
10:     number = "five"; // DOES NOT COMPILE
11: }
```

&emsp;&emsp;
On line 8, the compiler determines that we want an `int` variable. On line 9, we have no
trouble assigning a different `int` to it. On line 10, Java has a problem. We’ve asked it to
assign a `String` to an `int` variable. This is not allowed. It is equivalent to typing this:
`int number = "five";`

> ### Note:
> If you know a language like JavaScript, you might be expecting `var` to
mean a variable that can take on any type at runtime. In Java, var is still a
specific type defined at compile time. It does not change type at runtime.

&emsp;&emsp;
For simplicity when discussing `var`, we are going to assume a variable declaration 
statement is completed in a single line. You could insert a line break between the variable name
and its initialization value, as in the following example:

```java
7:  public void breakingDeclaration() {
8:      var silly
9:          = 1;
10: }
```

&emsp;&emsp;
This example is valid and does compile, but we consider the declaration and initialization
of `silly` to be happening on the same line.

### &emsp;&emsp; 2. Examples with _var_

Let’s go through some more scenarios so the exam doesn’t trick you on this topic! Do you
think the following compiles?

```java
3:  public void doesThisCompile(boolean check) {
4:      var question;
5:      question = 1;
6:      var answer;
7:      if (check) {
8:          answer = 2;
9:      } else {
10:         answer = 3;
11:     }
12:     System.out.println(answer);
13: }
```

&emsp;&emsp;
The code does not compile. Remember that for local variable type inference, the compiler
looks only at the line with the declaration. Since `question` and `answer` are not assigned
values on the lines where they are defined, the compiler does not know what to make of
them. For this reason, both lines 4 and 6 do not compile. <br />

&emsp;&emsp;
You might find that strange since both branches of the `if/else` do assign a value. Alas, it is
not on the same line as the declaration, so it does not count for `var`. Contrast this behavior
with what we saw a short while ago when we discussed branching and initializing a local
variable in our `findAnswer()` method. <br />

&emsp;&emsp;
Now we know the initial value used to determine the type needs to be part of the same
statement. Can you figure out why these two statements don’t compile?

```java
4:  public void twoTypes() {
5:      int a, var b = 3; // DOES NOT COMPILE
6:      var n = null; // DOES NOT COMPILE
7:  }
```

&emsp;&emsp;
Line 5 wouldn’t work even if you replaced var with a real type. All the types declared on
a single line must be the same type and share the same declaration. We couldn’t write `int
a, int v = 3;` either. <br />

&emsp;&emsp;
Line 6 is a single line. The compiler is being asked to infer the type of `null`. This could
be any reference type. The only choice the compiler could make is `Object`. However, that is
almost certainly not what the author of the code intended. The designers of Java decided it
would be better not to allow `var` for `null` than to have to guess at intent.

> ### Note:
> While a `var` cannot be initialized with a `null` value without a type, it can
be reassigned a `null` value after it is declared, provided that the 
underlying data type is a reference type.

&emsp;&emsp;
Let’s try another example. Do you see why this does not compile?

```java
public int addition(var a, var b) { // DOES NOT COMPILE
    return a + b;
}
```

&emsp;&emsp;
In this example, a and b are method parameters. These are not local variables. Be on the
lookout for `var` used with constructors, method parameters, or instance variables. Using
var in one of these places is a good exam trick to see if you are paying attention. Remember
that var is only used for local variable type inference! <br />

&emsp;&emsp;
There’s one last rule you should be aware of: `var` is not a reserved word and allowed to
be used as an identifier. It is considered a reserved type name. A _reserved type name_ means it
cannot be used to define a type, such as a `class`, `interface`, or `enum`. Do you think this is legal?

```java
package var;

public class Var {
    public void var() {
        var var = "var";
    }
    public void Var() {
        Var var = new Var();
    }
}
```

&emsp;&emsp;
Believe it or not, this code does compile. Java is case sensitive, so `Var` doesn’t introduce
any conflicts as a class name. Naming a local variable `var` is legal. Please don’t write code
that looks like this at your job! But understanding why it works will help get you ready for
any tricky exam questions the exam creators could throw at you.

> ### _var_ in the Real World
> The _var_ keyword is great for exam authors because it makes it easier to write tricky code.
When you work on a real project, you want the code to be easy to read. <br /><br />
> Once you start having code that looks like the following, it is time to consider using `var`
> ```java
>   PileOfPapersToFileInFilingCabinet pileOfPapersToFile = 
>       new PileOfPapersToFileInFilingCabinet();
> ```
> You can see how shortening this would be an improvement without losing any information:
> ```java
>   var pileOfPapersToFile = new PileOfPapersToFileInFilingCabinet();
> ```
> If you are ever unsure whether it is appropriate to use `var`, we recommend [Local Variable
Type Inference: Style Guidelines](https://openjdk.org/projects/amber/guides/lvti-style-guide), which is available at the following location.
