# Controlling Flow with Branching

The final types of control flow structures we cover in this chapter are branching statements.
Up to now, we have been dealing with single loops that ended only when their boolean
expression evaluated to false. We now show you other ways loops could end, or branch,
and you see that the path taken during runtime may not be as straightforward as in the
previous examples.

## 1. Nested Loops
Before we move into branching statements, we need to introduce the concept of nested
loops. A nested loop is a loop that contains another loop, including *while, do/while,
for, and for-each* loops. For example, consider the following code that iterates over a two-dimensional array, which is an array that contains other arrays as its members. We cover
multidimensional arrays in detail in Chapter 4, “Core APIs,” but for now, assume the following is how you would declare a two-dimensional array:

```java
int[][] myComplexArray = {{5,2,1,3},{3,9,8,9},{5,7,12,7}};

for (int[] mySimpleArray : myComplexArray) {
    for (int i = 0; i < mySimpleArray.length; i++) {
        System.out.print(mySimpleArray[i] + "\t");
    }
    System.out.println();
}
```

&emsp;&emsp;
Notice that we intentionally mix a for loop and a for-each loop in this example. The
outer loop will execute a total of three times. Each time the outer loop executes, the inner
loop is executed four times. When we execute this code, we see the following output:

5&emsp;&emsp;2&emsp;&emsp;1&emsp;&emsp;3 <br />
3&emsp;&emsp;9&emsp;&emsp;8&emsp;&emsp;9 <br />
5&emsp;&emsp;7&emsp;&emsp;12&emsp;&nbsp;&nbsp;7 <br />

&emsp;&emsp;
Nested loops can include while and do/while, as shown in this example. See whether
you can determine what this code will output:

```java
int hungryHippopotamus = 8;
while (hungryHippopotamus > 0) {
    do {
        hungryHippopotamus -= 2;
    } while (hungryHippopotamus > 5);
    hungryHippopotamus--;
    System.out.print(hungryHippopotamus + ", ");
}
```

&emsp;&emsp;
The first time this loop executes, the inner loop repeats until the value of
hungryHippopotamus is 4. The value will then be decremented to 3, and that will be the
output at the end of the first iteration of the outer loop.

&emsp;&emsp;
On the second iteration of the outer loop, the inner do/while will be executed once, even though hungryHippopotamus is already not greater than 5. As you may recall, do/while statements always execute the body 
at least once. This will reduce the value to 1, which will be further lowered by the decrement operator in the outer loop 
to 0. Once the value reaches 0, the outer loop will terminate. The result is that the code will output the following: 3, 0,

&emsp;&emsp;
The examples in the rest of this section include many nested loops. You will also
encounter nested loops on the exam, so the more practice you have with them, the more prepared you will be.

## 2. Adding Optional Labels
One thing we intentionally skipped when we presented if statements, switch statements,
and loops is that they can all have optional labels. A label is an optional pointer to the head
of a statement that allows the application flow to jump to it or break from it. It is a single
identifier that is followed by a colon (:). For example, we can add optional labels to one of
the previous examples:

```java
int[][] myComplexArray = {{5, 2, 1, 3}, {3, 9, 8, 9}, {5, 7, 12, 7}}; 

OUTER_LOOP: for (int[] mySimpleArray : myComplexArray) {
    INNER_LOOP: for (int i = 0; i < mySimpleArray.length; i++) {
        System.out.print(mySimpleArray[i] + "\t");
    }
    System.out.println();
}
```

&emsp;&emsp;
Labels follow the same rules for formatting as identifiers. For readability, they are commonly expressed using uppercase letters in snake_case with underscores between words.
When dealing with only one loop, labels do not add any value, but as you learn in the next
section, they are extremely useful in nested structures.

> **Note**<br />
> While this topic is not on the exam, it is possible to add optional labels to
control and block statements. For example, the following is permitted by
the compiler, albeit extremely uncommon:
> ```java
> int frog = 15;
> BAD_IDEA: if (frog > 10)
> EVEN_WORSE_IDEA: {
>   frog++;
> }
> ```

## 3. Using *break* Statements
As you saw when working with switch statements, a break statement transfers the flow
of control out to the enclosing statement. The same holds true for a break statement that
appears inside of a while, do/while, or for loop, as it will end the loop early, as shown below:

```java
optionalLabel: while(booleanExpression) {
    // Body
        
    // Somewhere in the loop
    break optionalLabel;
}
```

&emsp;&emsp;
Notice that the break statement can take an optional label parameter.
Without a label parameter, the break statement will terminate the nearest inner loop it is
currently in the process of executing. The optional label parameter allows us to break out of
a higher-level outer loop. In the following example, we search for the first (x,y) array index
position of a number within an unsorted two-dimensional array:

```java
public class FindInMatrix {
    public static void main(String[] args) {
        int[][] list = {{1,13},{5,2},{2,2}};
        int searchValue = 2;
        int positionX = -1;
        int positionY = -1; 
        PARENT_LOOP: for (int i = 0; i < list.length; i++) {
            for (int j = 0; j < list[i].length; j++) {
                if (list[i][j] == searchValue) {
                    positionX = i;
                    positionY = j;
                    break PARENT_LOOP;
                }
            }
        }
        if (positionX == -1 || positionY == -1) {
            System.out.println("Value " + searchValue + " not found");
        } else {
            System.out.println("Value " + searchValue + " found at: " +
                 "(" + positionX + "," + positionY + ")");
        }
    }
}
```

&emsp;&emsp;
When executed, this code will output the following: Value 2 found at: (1,1)

&emsp;&emsp;
In particular, take a look at the statement break PARENT_LOOP. This statement will break out of the entire loop 
structure as soon as the first matching value is found. Now, imagine what would happen if we replaced the body of 
the inner loop with the following:

```java
if (list[i][j] == searchValue) {
    positionX = i;
    positionY = j;
    break;
}
```

&emsp;&emsp;
How would this change our flow, and would the output change? Instead of exiting when
the first matching value is found, the program would now only exit the inner loop when the
condition was met. In other words, the structure would find the first matching value of the
last inner loop to contain the value, resulting in the following output: Value 2 found at: (2,0)

&emsp;&emsp;
Finally, what if we removed the break altogether?

```java
if (list[i][j] == searchValue) {
    positionX = i;
    positionY = j;
}
```

&emsp;&emsp;
In this case, the code would search for the last value in the entire structure that had the
matching value. The output would look like this: Value 2 found at: (2,1)

&emsp;&emsp;
You can see from this example that using a label on a break statement in a nested
loop, or not using the break statement at all, can cause the loop structure to behave quite
differently.

## 4. The *continue* Statements
Let’s now extend our discussion of advanced loop control with the *continue* statement,
a statement that causes flow to finish the execution of the current loop iteration, as shown below:

```java
optionalLabel: while(booleanExpression) {
    // Body
        
    // Somewhere in the loop
    continue optionalLabel;
}
```

&emsp;&emsp;
You may notice that the syntax of the continue statement mirrors that of the break
statement. In fact, the statements are identical in how they are used, but with different results. <br />
While the break statement transfers control to the enclosing statement, the continue statement transfers control 
to the boolean expression that determines if the loop should continue.
In other words, it ends the current iteration of the loop. Also, like the break statement, the
continue statement is applied to the nearest inner loop under execution, using optional label
statements to override this behavior.

&emsp;&emsp;
Let’s take a look at an example. Imagine we have a zookeeper who is supposed to clean
the first leopard in each of four stables but skip stable b entirely.

```java
1: public class CleaningSchedule {
2:    public static void main(String[] args) {
3:        CLEANING: for (char stables = 'a'; stables <= 'd'; stables++) {
4:            for (int leopard = 1; leopard < 4; leopard++) {
5:                if (stables == 'b' || leopard == 2) {
6:                    continue CLEANING;
7:                }
8:                System.out.println("Cleaning: " + stables + "," + leopard);
9:            } 
10:       } 
11:   } 
12:}
```

&emsp;&emsp;
With the structure as defined, the loop will return control to the parent loop any time the
first value is b or the second value is 2. On the first, third, and fourth executions of the outer
loop, the inner loop prints a statement exactly once and then exits on the next inner loop
when leopard is 2. On the second execution of the outer loop, the inner loop immediately
exits without printing anything since b is encountered right away. The following is printed: <br />

Cleaning: a,1 <br />
Cleaning: c,1 <br />
Cleaning: d,1 <br />

&emsp;&emsp;
Now, imagine we remove the CLEANING label in the continue statement so that control
is returned to the inner loop instead of the outer.

```java
6:                    continue;
```

This corresponds to the zookeeper cleaning all leopards except those labeled 2 or in stable
b. The output is then the following: <br />

Cleaning: a,1 <br />
Cleaning: a,3 <br />
Cleaning: c,1 <br />
Cleaning: c,3 <br />
Cleaning: d,1 <br /> 
Cleaning: d,3 <br />

Finally, if we remove the continue statement and the associated if statement altogether
by removing lines 5–7, we arrive at a structure that outputs all the values, such as this: <br />

Cleaning: a,1 <br />
Cleaning: a,2 <br />
Cleaning: a,3 <br />
Cleaning: b,1 <br />
Cleaning: b,2 <br />
Cleaning: b,3 <br />
Cleaning: c,1 <br />
Cleaning: c,2 <br />
Cleaning: c,3 <br />
Cleaning: d,1 <br />
Cleaning: d,2 <br />
Cleaning: d,3 <br />

## 5. The *return* Statement
For now, though, you should be familiar with the idea that creating methods and using
return statements can be used as an alternative to using labels and break statements. For
example, take a look at this rewrite of our earlier FindInMatrix class:

```java
public class FindInMatrixUsingReturn {
    private static int[] searchForValue(int[][] list, int v) {
        for (int i = 0; i < list.length; i++) {
            for (int j = 0; j < list[i].length; j++) {
                if (list[i][j] == v) {
                    return new int[] {i,j};
                }
            }
        }
        return null;
    } 
    
    public static void main(String[] args) {
        int[][] list = {{1, 13}, {5, 2}, {2, 2}};
        int searchValue = 2;
        int[] results = searchForValue(list,searchValue); 
        if (results == null) {
            System.out.println("Value " + searchValue + " not found");
        } else {
            System.out.println("Value " + searchValue + " found at: " +
                "(" + results[0] + "," + results[1] + ")");
        }
    }
}
```

&emsp;&emsp;
This class is functionally the same as the first FindInMatrix class we saw earlier using
break. If you need finer-grained control of the loop with multiple break and continue
statements, the first class is probably better. That said, we find code without labels
and break statements a lot easier to read and debug. Also, making the search logic an
independent function makes the code more reusable and the calling main() method a lot
easier to read.

&emsp;&emsp;
Just remember that return statements
can be used to exit loops quickly and can lead to more readable code in practice, especially
when used with nested loops.

## 6. Unreachable Code
One facet of break, continue, and return that you should be aware of is that any code
placed immediately after them in the same block is considered unreachable and will not
compile. For example, the following code snippet does not compile:

```java
int checkDate = 0;
while(checkDate < 10) {
    checkDate++;
    if (checkDate > 100) {
        break;
        checkDate++; // DOES NOT COMPILE
    }
}
```

&emsp;&emsp;
Even though it is not logically possible for the if statement to evaluate to true in this
code sample, the compiler notices that you have statements immediately following the
break and will fail to compile with “unreachable code” as the reason. The same is true for
continue and return statements, as shown in the following two examples:

```java
int minute = 1;
WATCH: while(minute > 2) {
    if (minute++ > 2) {
        continue WATCH;
        System.out.print(minute); // DOES NOT COMPILE
    }
}

int hour = 2;
switch (hour) {
    case 1: return; hour++; // DOES NOT COMPILE
    case 2:
}
```

&emsp;&emsp;
One thing to remember is that it does not matter if the loop or decision structure actually visits the line of code. 
For example, the loop could execute zero or infinite times at runtime. Regardless of execution, the compiler will report an error if it finds any code it deems
unreachable, in this case any statements immediately following a break, continue, or return statement.

## 7. Reviewing Branching
We conclude this section with Table below, which will help remind you when labels, break,
and continue statements are permitted in Java. Although for illustrative purposes our
examples use these statements in nested loops, they can be used inside single loops as well.

|            | Support *labels* | Support *break* | Support *continue* | Support *yield* |
|:-----------|:---|:---|:---|:----------------|
| *while*    | Yes | Yes | Yes | No              |
| *do/while* | Yes | Yes | Yes | No              |
| *for*      | Yes | Yes | Yes | No              |
| *switch*   | Yes | Yes | No | Yes             |

&emsp;&emsp;
Last but not least, all testing centers should offer some form of scrap paper or dry-erase
board to use during the exam. We strongly recommend you make use of these testing aids,
should you encounter complex questions involving nested loops and branching statements.

> **Tips:** <br />
> Some of the most time-consuming questions you may see on the exam
could involve nested loops with lots of branching. Unless you spot an
obvious compiler error, we recommend skipping these questions and
coming back to them at the end. Remember, all questions on the exam
are weighted evenly!
