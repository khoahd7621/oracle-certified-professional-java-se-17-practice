# Crating Objects

Our programs wouldn’t be able to do anything useful if we didn’t have the ability to create
new objects. Remember that an object is an instance of a class. In the following sections,
we look at constructors, object fields, instance initializers, and the order in which values are
initialized.

## I. Calling Constructors

To create an instance of a class, all you have to do is write `new` before the class name and
add parentheses after it. Here’s an example:

```java
Park p = new Park();
```

&emsp;&emsp;
First you declare the type that you’ll be creating (Park) and give the variable a name (p).
This gives Java a place to store a reference to the object. Then you write `new Park()` to
actually create the object. <br />

&emsp;&emsp;
`Park() `looks like a method since it is followed by parentheses. It’s called a _constructor_,
which is a special type of method that creates a new object. Now it’s time to define a 
constructor of your own:

```java
public class Chick {
    public Chick() {
        System.out.println("in constructor");
    }
}
```

&emsp;&emsp;
There are two key points to note about the constructor: the name of the constructor
matches the name of the class, and there’s no return type. You may see a method like this
on the exam:

```java
public class Chick {
    public void Chick() { } // NOT A CONSTRUCTOR
}
```

&emsp;&emsp;
When you see a method name beginning with a capital letter and having a return type,
pay special attention to it. It is _not_ a constructor since there’s a return type. It’s a regular
method that does compile but will not be called when you write `new Chick()`. <br />

&emsp;&emsp;
The purpose of a constructor is to initialize fields, although you can put any code in there.
Another way to initialize fields is to do so directly on the line on which they’re declared. This
example shows both approaches:

```java
public class Chicken {
    int numEggs = 12; // initialize on line
    String name;
    public Chicken() {
        name = "Duke"; // initialize in constructor
    }
}
```

&emsp;&emsp;
For most classes, you don’t have to code a constructor—the compiler will supply a
“do nothing” default constructor for you. There are some scenarios that do require you to
declare a constructor. You learn all about them in Chapter 6.

## II. Reading and Writing Member Fields
It’s possible to read and write instance variables directly from the caller. In this example, a
mother swan lays eggs:

```java
public class Swan {
    int numberEggs;                             // instance variable
    public static void main(String[] args) {
        Swan mother = new Swan();
        mother.numberEggs = 1;                  // set variable
        System.out.println(mother.numberEggs);  // read variable
    }
}
```

&emsp;&emsp;
The “caller” in this case is the `main()` method, which could be in the same class or in
another class. This class sets `numberEggs` to 1 and then reads `numberEggs` directly to print
it out. In Chapter 5, you learn how to use encapsulation to protect the `Swan` class from 
having someone set a negative number of eggs. <br />

&emsp;&emsp;
You can even read values of already initialized fields on a line initializing a new field:

```java
1:  public class Name {
2:      String first = "Theodore";
3:      String last = "Moose";
4:      String full = first + last;
5:  }
```

&emsp;&emsp;
Lines 2 and 3 both write to fields. Line 4 both reads and writes data. It reads the fields
`first` and `last`. It then writes the field `full`.

## III. Executing Instance Initializer Blocks
When you learned about methods, you saw braces ({}). The code between the braces 
(sometimes called “inside the braces”) is called a _code block_. Anywhere you see braces is a
_code block_. <br />

&emsp;&emsp;
Sometimes code blocks are inside a method. These are run when the method is called.
Other times, code blocks appear outside a method. These are called _instance initializers_. In
Chapter 6, you learn how to use a `static` initializer. <br />

&emsp;&emsp;
How many blocks do you see in the following example? How many instance initializers
do you see?

```java
1:  public class Bird {
2:      public static void main(String[] args) {
3:          { System.out.println("Feathers"); }
4:      }
5:      { System.out.println("Snowy"); }
6:  }
```

&emsp;&emsp;
There are four code blocks in this example: a class definition, a method declaration, an
inner block, and an instance initializer. Counting code blocks is easy: you just count the
number of pairs of braces. If there aren’t the same number of open ({) and close (}) braces
or they aren’t defined in the proper order, the code doesn’t compile. For example, you cannot
use a closed brace (}) if there’s no corresponding open brace ({) that it matches written 
earlier in the code. In programming, this is referred to as the _balanced parentheses problem_, and
it often comes up in job interview questions. <br />

&emsp;&emsp;
When you’re counting instance initializers, keep in mind that they cannot exist inside of a
method. Line 5 is an instance initializer, with its braces outside a method. On the other hand,
line 3 is not an instance initializer, as it is only called when the `main()` method is executed.
There is one additional set of braces on lines 1 and 6 that constitute the class declaration.

## IV. Following the Order of Initialization
When writing code that initializes fields in multiple places, you have to keep track of the
order of initialization. This is simply the order in which different methods, constructors, or
blocks are called when an instance of the class is created. We add some more rules to the
order of initialization in Chapter 6. In the meantime, you need to remember:
- Fields and instance initializer blocks are run in the order in which they appear in the file.
- The constructor runs after all fields and instance initializer blocks have run.

&emsp;&emsp;
Let’s look at an example:

```java
1:  public class Chick {
2:      private String name = "Fluffy";
3:      { System.out.println("setting field"); }
4:      public Chick() {
5:          name = "Tiny";
6:          System.out.println("setting constructor");
7:      }
8:      public static void main(String[] args) {
9:          Chick chick = new Chick();
10:         System.out.println(chick.name); 
11:     }
12: }
```

&emsp;&emsp;
Running this example prints this:

```java
setting field
setting constructor
Tiny
```

&emsp;&emsp;
Let’s look at what’s happening here. We start with the `main()` method because that’s
where Java starts execution. On line 9, we call the constructor of `Chick`. Java creates a new
object. First it initializes name to `Fluffy` on line 2. Next it executes the `println()` 
statement in the instance initializer on line 3. Once all the fields and instance initializers have
run, Java returns to the constructor. Line 5 changes the value of name to `Tiny`, and line 6
prints another statement. At this point, the constructor is done, and then the execution goes
back to the `println()` statement on line 10. <br />

&emsp;&emsp;
Order matters for the fields and blocks of code. You can’t refer to a variable before it has
been defined:

```java
{ System.out.println(name); } // DOES NOT COMPILE
private String name = "Fluffy";
```

&emsp;&emsp;
You should expect to see a question about initialization on the exam. Let’s try one more.
What do you think this code prints out?

```java
public class Egg {
    public Egg() {
        number = 5;
    }
    public static void main(String[] args) {
        Egg egg = new Egg();
        System.out.println(egg.number);
    }
    private int number = 3;
    { number = 4; } 
}
```

&emsp;&emsp;
If you answered 5, you got it right. Fields and blocks are run first in order, setting `number`
to `3` and then `4`. Then the constructor runs, setting `_number` to `5`. You see a lot more rules
and examples_ covering order of initialization in Chapter 6. We only cover the basics here so
you can follow the order of initialization for simple programs.
