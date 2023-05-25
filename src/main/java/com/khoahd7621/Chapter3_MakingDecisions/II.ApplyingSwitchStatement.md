# Applying *switch* Statements

What if we have a lot of possible branches or paths for a single value? For example, we
might want to print a different message based on the day of the week. We could certainly
accomplish this with a combination of seven if or else statements, but that tends to create
code that is long, difficult to read, and often not fun to maintain:

```java
public void printDayOfWeek(int day) {
    if (day == 0)
        System.out.print("Sunday");
    else if (day == 1)
        System.out.print("Monday");
    else if (day == 2)
        System.out.print("Tuesday");
    else if (day == 3)
        System.out.print("Wednesday");
    ...
}
```

&nbsp;&nbsp;&nbsp;&nbsp;
Luckily, Java, along with many other languages, provides a cleaner approach. In this
section we present the switch statement, along with the newer switch expression for
controlling program flow.

## 1. The *switch* Statement
A switch statement, is a complex decision-making structure in
which a single value is evaluated and flow is redirected to the first matching branch, known
as a case statement. If no such case statement is found that matches the value, an optional
default statement will be called. If no such default option is available, the entire switch
statement will be skipped. Case values can be combined into a
single case statement using commas.

> **Combining *case* Values**
> Starting with Java 14, case values can now be combined:
> ```java
> switch(animal) {
>     case 1, 2: System.out.print("Lion");
>     case 3: System.out.print("Tiger");
> }
> ```
> Prior to Java 14, the equivalent code would have been the following:
> ```java
> switch(animal) {
>     case 1: case 2: System.out.print("Lion");
>     case 3: System.out.print("Tiger");
> }
> ```
> As you see shortly, switch expressions can reduce boilerplate code even more!

&nbsp;&nbsp;&nbsp;&nbsp;
Going back to our printDayOfWeek() method, we can rewrite it to use a switch statement instead of if/else statements:
```java
public void printDayOfWeek(int day) {
    switch(day) {
        case 0:
            System.out.print("Sunday");
            break;
        case 1:
            System.out.print("Monday");
            break;
        case 2:
            System.out.print("Tuesday");
            break;
        ...    
        default:
            System.out.print("Invalid value");
            break;
    } 
}
```

## 2. Exiting with *break* Statements
Taking a look at our previous printDayOfWeek() implementation, you’ll see a break statement
at the end of each case and default section. A break statement terminates the switch statement
and returns flow control to the enclosing process. Put simply, it ends the switch statement
immediately.

&nbsp;&nbsp;&nbsp;&nbsp;
The break statements are optional, but without them the code will execute every branch
following a matching case statement, including any default statements it finds. Without break
statements in each branch, the order of case and default statements is now extremely important. 
What do you think the following prints when printSeason(2) is called?

```java
public void printSeason(int month) {
    switch(month) {
        case 1, 2, 3: System.out.print("Winter");
        case 4, 5, 6: System.out.print("Spring");
        default: System.out.print("Unknown");
        case 7, 8, 9: System.out.print("Summer");
        case 10, 11, 12: System.out.print("Fall");
    } 
}
```

&nbsp;&nbsp;&nbsp;&nbsp;
It prints everything! <br />
WinterSpringUnknownSummerFall <br />

&nbsp;&nbsp;&nbsp;&nbsp;
It matches the first case statement and executes all of the branches in the order they are
found, including the default statement. It is common, although certainly not required, to
use a break statement after every case statement.

> **Tips**
> The exam creators are fond of switch examples that are missing break
statements! When evaluating switch statements on the exam, always
consider that multiple branches may be visited in a single execution.

## 3. Selecting *switch* Data Types
A switch statement has a target variable that is not evaluated until
runtime. The type of this target can include select primitive data types (int, byte, short, char)
and their associated wrapper classes (Integer, Byte, Short, Character). The following is a list of
all data types supported by switch statements:

- int and Integer 
- byte and Byte
- short and Short
- char and Character
- String
- enum values
- var (if the type resolves to one of the preceding types)

&nbsp;&nbsp;&nbsp;&nbsp;
For this chapter, you just need to know that an enumeration, or *enum*, represents a fixed
set of constants, such as days of the week, months of the year, and so on. We cover enums
in more detail in Chapter 7, including showing how they can define variables, methods, and
constructors.

> **Note**
> Notice that boolean, long, float, and double are excluded from
switch statements, as are their associated Boolean, Long, Float, and
Double classes. The reasons are varied, such as boolean having too
small a range of values and floating-point numbers having quite a wide
range of values. For the exam, though, you just need to know that they
are not permitted in switch statements.

## 4. Determining Acceptable Case Values
Not just any variable or value can be used in a case statement. First, the values in each
case statement must be compile-time constant values of the same data type as the switch
value. This means you can use only literals, enum constants, or final constant variables
of the same data type. By final constant, we mean that the variable must be marked with
the final modifier and initialized with a literal value in the same expression in which it
is declared. For example, you can’t have a case statement value that requires executing a
method at runtime, even if that method always returns the same value. For these reasons,
only the first and last case statements in the following example compile:
```java
final int getCookies() { return 4; }
void feedAnimals() {
    final int bananas = 1;
    int apples = 2;
    int numberOfAnimals = 3;
    final int cookies = getCookies();
    switch(numberOfAnimals) {
        case bananas:
        case apples: // DOES NOT COMPILE
        case getCookies(): // DOES NOT COMPILE
        case cookies : // DOES NOT COMPILE
        case 3 * 5 :
    } 
}
```

- The bananas variable is marked final, and its value is known at compile-time, so it is
valid. 
- The apples variable is not marked final, even though its value is known, so it is
not permitted. 
- The next two case statements, with values getCookies() and cookies, do
not compile because methods are not evaluated until runtime, so they cannot be used as the
value of a case statement, even if one of the values is stored in a final variable. 
- The last case statement, with value 3 * 5, does compile, as expressions are allowed as case values,
provided the value can be resolved at compile-time. 

They also must be able to fit in the
switch data type without an explicit cast. We go into that in more detail shortly.

&nbsp;&nbsp;&nbsp;&nbsp;
Next, the data type for case statements must match the data type of the switch variable.
For example, you can’t have a case statement of type String if the switch statement variable is
of type int, since the types are incomparable.

## 5. The *switch* Expression
Our second implementation of printDayOfWeek() was improved but still quite long. Notice
that there was a lot of boilerplate code, along with numerous break statements. Can we do
better? Yes, thanks to the new switch expressions that were officially added to Java 14.

&nbsp;&nbsp;&nbsp;&nbsp;
A switch expression is a much more compact form of a switch statement, capable of
returning a value.

&nbsp;&nbsp;&nbsp;&nbsp;
Because a switch expression is a compact form, there’s a lot going on. For starters, we can now assign the result of a switch expression to a variable result. For
this to work, all case and default branches must return a data type that is compatible with
the assignment. The switch expression supports two types of branches: an expression and
a block. Each has different syntactical rules on how it must be created. More on these
topics shortly.

```java
int result = switch(variableToTest) {
    case constantExpression1 -> 5;
    case constantExpression2, constantExpression3 -> {
        yield 10;
    }
        ...
    default -> 0;
};
```

&nbsp;&nbsp;&nbsp;&nbsp;
Like a traditional switch statement, a switch expression supports zero or many case
branches and an optional default branch. Both also support the new feature that allows
case values to be combined with a single case statement using commas. Unlike a traditional switch statement, though, switch expressions have special rules around when the
default branch is required.

> **Note**
> Recall from Chapter 2, “Operators,” that -> is the arrow operator. While
the arrow operator is commonly used in lambda expressions, when it is
used in a switch expression, the case branches are not lambdas.

&nbsp;&nbsp;&nbsp;&nbsp;
We can rewrite our previous printDayOfWeek() method in a much more concise
manner using case expressions:

```java
public void printDayOfWeek(int day) {
    var result = switch(day) {
        case 0 -> "Sunday";
        case 1 -> "Monday";
            ...
        default -> "Invalid";
    };
    System.out.print(result);
}
```

&nbsp;&nbsp;&nbsp;&nbsp;
Compare this code with the switch statement we wrote earlier. Both accomplish the
same task, but a lot of the boilerplate code has been removed, leaving the behavior we care
most about.

&nbsp;&nbsp;&nbsp;&nbsp;
Case statements can take multiple values, separated by commas.
Let’s rewrite our printSeason() method from earlier using a switch expression:

```java
public void printSeason(int month) {
    switch(month) {
        case 1, 2, 3 -> System.out.print("Winter");
        case 4, 5, 6 -> System.out.print("Spring");
        case 7, 8, 9 -> System.out.print("Summer");
        case 10, 11, 12 -> System.out.print("Fall");
    } 
}
```

&nbsp;&nbsp;&nbsp;&nbsp;
Calling printSeason(2) prints the single value Winter. This time we don’t have to
worry about break statements, since only one branch is executed.

> **Note**
> Most of the time, a switch expression returns a value, although
printSeason() demonstrates one in which the return type is void.
Since the type is void, it can’t be assigned to a variable. On the exam,
you are more likely to see a switch expression that returns a value, but
you should be aware that it is possible.

&nbsp;&nbsp;&nbsp;&nbsp;
All of the previous rules around switch data types and case values still apply, although
we have some new rules. Don’t worry if these rules are new to you or you’ve never seen the
yield keyword before; we’ll be discussing them in the following sections.

1. All of the branches of a switch expression that do not throw an exception must return
   a consistent data type (if the switch expression returns a value).
2. If the switch expression returns a value, then every branch that isn’t an expression must
   yield a value.
3. A default branch is required unless all cases are covered or no value is returned.

&nbsp;&nbsp;&nbsp;&nbsp;
We cover the last rule shortly, but notice that our printSeason() example does not contain a
default branch. Since the switch expression does not return a value and assign it to a variable,
it is entirely optional.

> **Note**
> Java 17 also supports pattern matching within switch expressions, but
since this is a Preview feature, it is not in scope for the exam.

## 6. Returning Consistent Data Types

The first rule of using a switch expression is probably the easiest. You can’t return incompatible or random data types.
For example, can you see why three of the lines of the following code do not compile?

```java
int measurement = 10;
int size = switch(measurement) {    
    case 5 -> 1;
    case 10 -> (short)2;
    default -> 5;
    case 20 -> "3"; // DOES NOT COMPILE
    case 40 -> 4L; // DOES NOT COMPILE
    case 50 -> null; // DOES NOT COMPILE
};
```

&nbsp;&nbsp;&nbsp;&nbsp;
Notice that the second case expression returns a short, but that can be implicitly cast
to an int. In this manner, the values have to be consistent with size, but they do not all
have to be the same data type. The last three case expressions do not compile because each
returns a type that cannot be assigned to the int variable.

## 7. Applying a *case* Block
A switch expression supports both an expression and a block in the case and default
branches. Like a regular block, a case block is one that is surrounded by braces ({}). It also
includes a yield statement if the switch expression returns a value. For example, the following
uses a mix of case expressions and blocks:

```java
int fish = 5;
int length = 12;
var name = switch(fish) {
    case 1 -> "Goldfish";
    case 2 -> {yield "Trout";}
    case 3 -> {
        if (length > 10) yield "Blobfish";
        else yield "Green";
    }
    default -> "Swordfish";
};
```

&nbsp;&nbsp;&nbsp;&nbsp;
The yield keyword is equivalent to a return statement within a switch expression and
is used to avoid ambiguity about whether you meant to exit the block or method around the
switch expression.

> **Watch Semicolons in *switch* Expressions**
> 
> Unlike a regular switch statement, a switch expression can be used with the assignment
operator and requires a semicolon when doing so. Furthermore, semicolons are required
for case expressions but cannot be used with case blocks.
> ```java
> var name = switch(fish) {
>     case 1 -> "Goldfish" // DOES NOT COMPILE (missing semicolon)
>     case 2 -> {yield "Trout";}; // DOES NOT COMPILE (extra semicolon)
>     ...
> } // DOES NOT COMPILE (missing semicolon)
> ```
> A bit confusing, right? It’s just one of those things you have to train yourself to spot
on the exam.

## 8. Covering All Possible Values
The last rule about switch expressions is probably the one the exam is most likely to try to
trick you on: a switch expression that returns a value must handle all possible input values.
And as you saw earlier, when it does not return a value, it is optional. <br />

&nbsp;&nbsp;&nbsp;&nbsp;
Let’s try this out. Given the following code, what is the value of type if canis is 5?

```java
String type = switch(canis) { // DOES NOT COMPILE
    case 1 -> "dog";
    case 2 -> "wolf";
    case 3 -> "coyote";
};
```

&nbsp;&nbsp;&nbsp;&nbsp;
There’s no case branch to cover 5 (or 4, -1, 0, etc.), so should the switch expression
return null, the empty string, undefined, or some other value? When adding switch expressions to the Java language, the authors decided this behavior would be unsupported. Every
switch expression must handle all possible values of the switch variable. As a developer,
there are two ways to address this:
- Add a default branch.
- If the switch expression takes an enum value, add a case branch for every possible
  enum value.

&nbsp;&nbsp;&nbsp;&nbsp;
In practice, the first solution is the one most often used. The second solution applies only
to switch expressions that take an enum. You can try writing case statements for all possible
int values, but we promise it doesn’t work! Even smaller types like byte are not permitted by
the compiler, despite there being only 256 possible values.

&nbsp;&nbsp;&nbsp;&nbsp;
For enums, the second solution works well when the number of enum values is relatively
small. For example, consider the following enum definition and method:

```java
enum Season {WINTER, SPRING, SUMMER, FALL}

String getWeather(Season value) {
    return switch(value) {
        case WINTER -> "Cold";
        case SPRING -> "Rainy";
        case SUMMER -> "Hot";
        case FALL -> "Warm";
    };
}
```

&nbsp;&nbsp;&nbsp;&nbsp;
Since all possible permutations of Season are covered, a default branch is not required
in this switch expression. You can include an optional default branch, though, even if
you cover all known values.

> **Tips**
> 
> What happens if you use an enum with three values and later someone
adds a fourth value? Any switch expressions that use the enum without
a default branch will suddenly fail to compile. If this was done frequently, you might have a lot of code to fix! For this reason, consider
including a default branch in every switch expression, even those that
involve enum values.
