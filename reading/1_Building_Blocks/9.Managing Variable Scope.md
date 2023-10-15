# Managing Variable Scope

You’ve learned that local variables are declared within a code block. How many variables do
you see that are scoped to this method?

```java
public void eat(int piecesOfCheese) {
    int bitesOfCheese = 1;
}
```

&emsp;&emsp;
There are two variables with local scope. The `bitesOfCheese` variable is declared inside
the method. The `piecesOfCheese` variable is a method parameter. Neither variable can be
used outside of where it is defined.

## I. Limiting Scope

Local variables can never have a scope larger than the method they are defined in. However,
they can have a smaller scope. Consider this example:

```java
3:  public void eatIfHungry(boolean hungry) {
4:      if (hungry) {
5:          int bitesOfCheese = 1;
6:      } // bitesOfCheese goes out of scope here
7:      System.out.println(bitesOfCheese); // DOES NOT COMPILE
8:  }
```

&emsp;&emsp;
The variable `hungry` has a scope of the entire method, while the variable `bitesOfCheese`
has a smaller scope. It is only available for use in the `if` statement because it is declared inside
of it. When you see a set of braces `{}` in the code, it means you have entered a new block of
code. Each block of code has its own scope. When there are multiple blocks, you match them
from the inside out. In our case, the `if` statement block begins at line 4 and ends at line 6. The
method’s block begins at line 3 and ends at line 8. <br />

&emsp;&emsp;
Since `bitesOfCheese` is declared in an `if` statement block, the scope is limited to that
block. When the compiler gets to line 7, it complains that it doesn’t know anything about
this `bitesOfCheese` thing and gives an error.

&emsp;&emsp;
Remember that blocks can contain other blocks. These smaller contained blocks can 
reference variables defined in the larger scoped blocks, but not vice versa. Here’s an example:

```java
16: public void eatIfHungry(boolean hungry) {
17:     if (hungry) {
18:         int bitesOfCheese = 1;
19:         {
20:             var teenyBit = true;
21:             System.out.println(bitesOfCheese);
22:         }
23:     }
24:     System.out.println(teenyBit); // DOES NOT COMPILE
25: }
```

&emsp;&emsp;
The variable defined on line 18 is in scope until the block ends on line 23. Using it in the
smaller block from lines 19 to 22 is fine. The variable defined on line 20 goes out of scope on
line 22. Using it on line 24 is not allowed.

## II. Tracing Scope

The exam will attempt to trick you with various questions on scope. You’ll probably see a
question that appears to be about something complex and fails to compile because one of
the variables is out of scope. <br />

&emsp;&emsp;
Let’s try one. Don’t worry if you aren’t familiar with `if` statements or `while` loops yet. It
doesn’t matter what the code does since we are talking about scope. See if you can figure out
on which line each of the five local variables goes into and out of scope:

```java
11: public void eatMore(boolean hungry, int amountOfFood) {
12:     int roomInBelly = 5;
13:     if (hungry) {
14:         var timeToEat = true;
15:         while (amountOfFood > 0) {
16:             int amountEaten = 2;
17:             roomInBelly = roomInBelly - amountEaten;
18:             amountOfFood = amountOfFood - amountEaten;
19:         }
20:     }
21:     System.out.println(amountOfFood);
22: }
```

&emsp;&emsp;
This method does compile. The first step in figuring out the scope is to identify the blocks
of code. In this case, there are three blocks. You can tell this because there are three sets
of braces. Starting from the innermost set, we can see where the `while` loop’s block starts
and ends. Repeat this process as we go on for the `if` statement block and method block.
Table 1.10 shows the line numbers that each block starts and ends on.

> #### Table 1.10 Tracking scope by block

|Line|First line in block|Last line in block|
|----|-------------------|------------------|
|while|15|19|
|if|13|20|
|method|11|22|

&emsp;&emsp;
Now that we know where the blocks are, we can look at the scope of each variable.
`hungry` and `amountOfFood` are method parameters, so they are available for the entire
method. This means their scope is lines 11 to 22. The variable `roomInBelly` goes into scope
on line 12 because that is where it is declared. It stays in scope for the rest of the method
and goes out of scope on line 22. The variable `timeToEat` goes into scope on line 14 where
it is declared. It goes out of scope on line 20 where the if block ends. Finally, the variable
`amountEaten` goes into scope on line 16 where it is declared. It goes out of scope on line
19 where the while block ends. <br />

&emsp;&emsp;
_You’ll want to practice this skill a lot!_ Identifying blocks and variable scope needs to be
second nature for the exam. The good news is that there are lots of code examples to 
practice on. You can look at any code example on any topic in this book and match up braces.

## III. Applying Scope to Classes
All of that was for local variables. Luckily, the rule for instance variables is easier: they are
available as soon as they are defined and last for the entire lifetime of the object itself. The
rule for class, aka static, variables is even easier: they go into scope when declared like the
other variable types. However, they stay in scope for the entire life of the program. <br />

&emsp;&emsp;
Let’s do one more example to make sure you have a handle on this. Again, try to figure
out the type of the four variables and when they go into and out of scope.

```java
1:  public class Mouse {
2:      final static int MAX_LENGTH = 5;
3:      int length;
4:      public void grow(int inches) {
5:          if (length < MAX_LENGTH) {
6:              int newSize = length + inches;
7:              length = newSize;
8:          }
9:      }
10: }
```

&emsp;&emsp;
In this class, we have one _class variable_, `MAX_LENGTH`; one _instance variable_, `length`;
and two _local variables_, `inches` and `newSize`. The `MAX_LENGTH` variable is a class variable
because it has the `static` keyword in its declaration. In this case, `MAX_LENGTH` goes into
scope on line 2 where it is declared. It stays in scope until the program ends. <br />

&emsp;&emsp;
Next, `length` goes into scope on line 3 where it is declared. It stays in scope as long as
this `Mouse` object exists. `inches` goes into scope where it is declared on line 4. It goes out of
scope at the end of the method on line 9. `newSize` goes into scope where it is declared on line 6. 
Since it is defined inside the if statement block, it goes out of scope when that block ends on line 8.

## IV. Reviewing Scope
Got all that? Let’s review the rules on scope:
- _Local variables_: In scope from declaration to the end of the block
- _Method parameters_: In scope for the duration of the method
- _Instance variables_: In scope from declaration until the object is eligible for garbage
collection
- _Class variables_: In scope from declaration until the program ends

&emsp;&emsp;
Not sure what garbage collection is? Relax: that’s our next and final section for  this chapter.
