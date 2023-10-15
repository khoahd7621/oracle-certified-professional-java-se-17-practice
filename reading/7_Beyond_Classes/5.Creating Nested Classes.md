# Creating Nested Classes

A nested class is a class that is defined within another class. A nested class can come in one
of four flavors.

- *Inner class*: A non-static type defined at the member level of a class
- *Static nested class*: A static type defined at the member level of a class
- *Local class*: A class defined within a method body
- *Anonymous class*: A special case of a local class that does not have a name

&emsp;&emsp;
There are many benefits of using nested classes. They can define helper classes and restrict
them to the containing class, thereby improving encapsulation. They can make it easy to
create a class that will be used in only one place. They can even make the code cleaner and
easier to read. <br />

&emsp;&emsp;
When used improperly, though, nested classes can sometimes make the code harder to
read. They also tend to tightly couple the enclosing and inner class, but there may be cases
where you want to use the inner class by itself. In this case, you should move the inner class
out into a separate top-level class. <br />

&emsp;&emsp;
Unfortunately, the exam tests edge cases where programmers wouldn’t typically use
a nested class. This tends to create code that is difficult to read, so please never do this
in practice!

> **Note**: <br />
> By convention, and throughout this chapter, we often use the term nested
class to refer to all nested types, including nested interfaces, enums,
records, and annotations. You might even come across literature that
refers to all of them as inner classes. We agree that this can be confusing!

## I. Declaring an Inner Class

An *inner* class, also called a *member inner class*, is a non-static type defined at the member
level of a class (the same level as the methods, instance variables, and constructors). Because
they are not top-level types, they can use any of the four access levels, not just public and
package access. <br />

&emsp;&emsp;
Inner classes have the following properties:

- Can be declared public, protected, package, or private
- Can extend a class and implement interfaces
- Can be marked abstract or final
- Can access members of the outer class, including private members

&emsp;&emsp;
The last property is pretty cool. It means that the inner class can access variables in
the outer class without doing anything special. Ready for a complicated way to print Hi
three times?

```java
1:  public class Home {
2:      private String greeting = "Hi"; // Outer class instance variable
3:
4:      protected class Room { // Inner class declaration
5:          public int repeat = 3;
6:          public void enter() {
7:              for (int i = 0; i < repeat; i++) greet(greeting);
8:          }
9:          private static void greet(String message) {
10:             System.out.println(message);
11:         }
12:     }
13:
14:     public void enterRoom() {  // Instance method in outer class
15:         var room = new Room(); // Create the inner class instance
16:         room.enter();
17:     }
18:     public static void main(String[] args) {
19:         var home = new Home(); // Create the outer class instance
20:         home.enterRoom();
21:     } 
22: }
```

&emsp;&emsp;
An inner class declaration looks just like a stand-alone class declaration except that it
happens to be located inside another class. Line 7 shows that the inner class just refers to
greeting as if it were available in the Room class. This works because it is, in fact, available.
Even though the variable is private, it is accessed within that same class. <br />

&emsp;&emsp;
Since an inner class is not static, it has to be called using an instance of the outer class.
That means you have to create two objects. Line 19 creates the outer Home object, while
line 15 creates the inner Room object. It’s important to notice that line 15 doesn’t require an
explicit instance of Home because it is an instance method within Home. This works because
enterRoom() is an instance method within the Home class. Both Room and enterRoom() are
members of Home.

> **Nested Classes Can Now Have static Members**: <br />
> Eagle-eyed readers may have noticed that we included a static method in our inner Room
class on line 9. In Java 11, this would have resulted in a compiler error. Previously, only
static nested classes were allowed to include static methods. With the introduction of
records in Java 16, the existing rule that prevented an inner class from having any static
members (other than static constants) was removed. All four types of nested classes can
now define static variables and methods!

## II. Instantiating an Instance of an Inner Class
There is another way to instantiate Room that looks odd at first. Okay, well, maybe not just
at first. This syntax isn’t used often enough to get used to it:

```java
20: public static void main(String[] args) {
21:     var home = new Home();
22:     Room room = home.new Room(); // Create the inner class instance
23:     room.enter();
24: }
```

&emsp;&emsp;
Let’s take a closer look at lines 21 and 22. We need an instance of Home to create a Room.
We can’t just call new Room() inside the static main() method, because Java won’t
know which instance of Home it is associated with. Java solves this by calling new as if it
were a method on the room variable. We can shorten lines 21–23 to a single line:

```java
21:     new Home().new Room().enter(); // Sorry, it looks ugly to us too!
```

> **Creating *.class* Files for Inner Classes**: <br />
> Compiling the Home.java class with which we have been working creates two class files.
You should be expecting the Home.class file. For the inner class, the compiler creates
Home$Room.class. You don’t need to know this syntax for the exam. We mention it so that
you aren’t surprised to see files with $ appearing in your directories. You do need to 
understand that multiple class files are created from a single .java file.

## III. Referencing Members of an Inner Class
Inner classes can have the same variable names as outer classes, making scope a little tricky.
There is a special way of calling this to say which variable you want to access. This is
something you might see on the exam but, ideally, not in the real world. <br />

&emsp;&emsp;
In fact, you aren’t limited to just one inner class. While the following is common on the
exam, please never do this in code you write. Here is how to nest multiple classes and access
a variable with the same name in each:

```java
1:  public class A {
2:      private int x = 10;
3:      class B {
4:          private int x = 20;
5:          class C {
6:              private int x = 30;
7:              public void allTheX() {
8:                  System.out.println(x);        // 30
9:                  System.out.println(this.x);   // 30
10:                 System.out.println(B.this.x); // 20
11:                 System.out.println(A.this.x); // 10
12:             } } }
13:     public static void main(String[] args) {
14:         A a = new A();
15:         A.B b = a.new B();
16:         A.B.C c = b.new C();
17:         c.allTheX();
18: }}
```

&emsp;&emsp;
Yes, this code makes us cringe too. It has two nested classes. Line 14 instantiates the
outermost one. Line 15 uses the awkward syntax to instantiate a B. Notice that the type is
A.B. We could have written B as the type because that is available at the member level of A.
Java knows where to look for it. On line 16, we instantiate a C. This time, the A.B.C type
is necessary to specify. C is too deep for Java to know where to look. Then line 17 calls a
method on the instance variable c. <br />

&emsp;&emsp;
Lines 8 and 9 are the type of code that we are used to seeing. They refer to the instance
variable on the current class—the one declared on line 6, to be precise. Line 10 uses this in a
special way. We still want an instance variable. But this time, we want the one on the B class,
which is the variable on line 4. Line 11 does the same thing for class A, getting the variable
from line 2.

> **Inner Classes Require an Instance**: <br />
> Take a look at the following and see whether you can figure out why two of the three constructor calls do not compile:
> ```java
>   public class Fox {
>       private class Den {}
>       public void goHome() {
>           new Den();
>       }
>       public static void visitFriend() {
>           new Den(); // DOES NOT COMPILE
>       }
>   }
>   public class Squirrel {
>       public void visitFox() {
>           new Den(); // DOES NOT COMPILE
>       }
>   }
> ```
> The first constructor call compiles because goHome() is an instance method, and therefore
the call is associated with the this instance. The second call does not compile because it is
called inside a static method. You can still call the constructor, but you have to explicitly
give it a reference to a Fox instance. <br />
> The last constructor call does not compile for two reasons. Even though it is an 
instance method, it is not an instance method inside the Fox class. Adding a Fox reference
would not fix the problem entirely, though. Den is private and not accessible in the
Squirrel class.

## IV. Creating a static Nested Class
A static nested class is a static type defined at the member level. Unlike an inner class, a static
nested class can be instantiated without an instance of the enclosing class. The trade-off,
though, is that it can’t access instance variables or methods declared in the outer class. <br />

&emsp;&emsp;
In other words, it is like a top-level class except for the following:

- The nesting creates a namespace because the enclosing class name must be used to
  refer to it.
- It can additionally be marked private or protected.
- The enclosing class can refer to the fields and methods of the static nested class.

&emsp;&emsp;
Let’s take a look at an example:

```java
1:  public class Park {
2:      static class Ride {
3:          private int price = 6;
4:      }
5:      public static void main(String[] args) {
6:          var ride = new Ride();
7:          System.out.println(ride.price);
8:      } 
9:  }
```

&emsp;&emsp;
Line 6 instantiates the nested class. Since the class is static, you do not need an instance
of Park to use it. You are allowed to access private instance variables, as shown on line 7.

## V. Writing a Local Class

A local class is a nested class defined within a method. Like local variables, a local class
declaration does not exist until the method is invoked, and it goes out of scope when the
method returns. This means you can create instances only from within the method. Those
instances can still be returned from the method. This is just how local variables work.

> **Note**: <br />
> Local classes are not limited to being declared only inside methods. For
example, they can be declared inside constructors and initializers. For
simplicity, we limit our discussion to methods in this chapter.

&emsp;&emsp;
Local classes have the following properties:

- They do not have an access modifier.
- They can be declared final or abstract.
- They have access to all fields and methods of the enclosing class (when defined in an
  instance method).
- They can access final and effectively final local variables.

> **Note**: <br />
> Remember when we presented effectively final in Chapter 5? Well, we
said it would come in handy later, and it’s later! If you need a refresher on
final and effectively final, turn back to Chapter 5 now. Don’t worry;
we’ll wait!

&emsp;&emsp;
Ready for an example? Here’s a complicated way to multiply two numbers:

```java
1: public class PrintNumbers {
2: private int length = 5;
3: public void calculate() {
4: final int width = 20;
5: class Calculator {
6: public void multiply() {
7: System.out.print(length * width);
8: }
9: } 
10: var calculator = new Calculator();
11: calculator.multiply();
12: }
13: public static void main(String[] args) {
14: var printer = new PrintNumbers();
15: printer.calculate(); // 100
16: }
17: }
```

&emsp;&emsp;
Lines 5–9 are the local class. That class’s scope ends on line 12, where the method ends.
Line 7 refers to an instance variable and a final local variable, so both variable references
are allowed from within the local class. <br />

&emsp;&emsp;
Earlier, we made the statement that local variable references are allowed if they are final
or effectively final. As an illustrative example, consider the following:

```java
public void processData() {
    final int length = 5;
    int width = 10;
    int height = 2;
    class VolumeCalculator {
        public int multiply() {
            return length * width * height; // DOES NOT COMPILE
        }
    }
    width = 2;
}
```

&emsp;&emsp;
The length and height variables are final and effectively final, respectively, so 
neither causes a compilation issue. On the other hand, the width variable is reassigned during
the method, so it cannot be effectively final. For this reason, the local class declaration does
not compile.

> **Why Can Local Classes Only Access final or Effectively Final Variables?**: <br />
> Earlier, we mentioned that the compiler generates a separate .class file for each inner
class. A separate class has no way to refer to a local variable. However, if the local variable
is final or effectively final, Java can handle it by passing a copy of the value or reference
variable to the constructor of the local class. If it weren’t final or effectively final, these
tricks wouldn’t work because the value could change after the copy was made.

## VI. Defining an Anonymous Class
An anonymous class is a specialized form of a local class that does not have a name. It is
declared and instantiated all in one statement using the new keyword, a type name with
parentheses, and a set of braces {}. Anonymous classes must extend an existing class or
implement an existing interface. They are useful when you have a short implementation that
will not be used anywhere else. Here’s an example:

```java
1:  public class ZooGiftShop {
2:      abstract class SaleTodayOnly {
3:          abstract int dollarsOff();
4:      }
5:      public int admission(int basePrice) {
6:          SaleTodayOnly sale = new SaleTodayOnly() {
7:              int dollarsOff() { return 3; }
8:          }; // Don't forget the semicolon!
9:          return basePrice - sale.dollarsOff();
10:     } 
11: }
```

&emsp;&emsp;
Lines 2–4 define an abstract class. Lines 6–8 define the anonymous class. Notice
how this anonymous class does not have a name. The code says to instantiate a new
SaleTodayOnly object. But wait: SaleTodayOnly is abstract. This is okay because we
provide the class body right there—anonymously. In this example, writing an anonymous
class is equivalent to writing a local class with an unspecified name that extends
SaleTodayOnly and immediately uses it. <br />

&emsp;&emsp;
Pay special attention to the semicolon on line 8. We are declaring a local variable on these
lines. Local variable declarations are required to end with semicolons, just like other Java
statements—even if they are long and happen to contain an anonymous class. <br />

&emsp;&emsp;
Now we convert this same example to implement an interface instead of extending an
abstract class:

```java
1:  public class ZooGiftShop {
2:      interface SaleTodayOnly {
3:          int dollarsOff();
4:      }
5:      public int admission(int basePrice) {
6:          SaleTodayOnly sale = new SaleTodayOnly() {
7:              public int dollarsOff() { return 3; }
8:          };
9:          return basePrice - sale.dollarsOff();
10:     } 
11: }
```

&emsp;&emsp;
The most interesting thing here is how little has changed. Lines 2–4 declare an
interface instead of an abstract class. Line 7 is public instead of using default access
since interfaces require public methods. And that is it. The anonymous class is the same
whether you implement an interface or extend a class! Java figures out which one you want
automatically. Just remember that in this second example, an instance of a class is created on
line 6, not an interface. <br />

&emsp;&emsp;
But what if we want to both implement an interface and extend a class? You can’t do
so with an anonymous class unless the class to extend is java.lang.Object. The Object
class doesn’t count in the rule. Remember that an anonymous class is just an unnamed local
class. You can write a local class and give it a name if you have this problem. Then you can
extend a class and implement as many interfaces as you like. If your code is this complex, a
local class probably isn’t the most readable option anyway. <br />

&emsp;&emsp;
You can even define anonymous classes outside a method body. The following may look
like we are instantiating an interface as an instance variable, but the {} after the interface
name indicates that this is an anonymous class implementing the interface:

```java
public class Gorilla {
    interface Climb {}
    Climb climbing = new Climb() {};
}
```

> **Anonymous Classes and Lambda Expressions**: <br />
> Prior to Java 8, anonymous classes were frequently used for asynchronous tasks and event
handlers. For example, the following shows an anonymous class used as an event handler
in a JavaFX application:
> ```java
>   var redButton = new Button();
>       redButton.setOnAction(new EventHandler<ActionEvent>() {
>       public void handle(ActionEvent e) {
>           System.out.println("Red button pressed!");
>       }
>   });
> ```
> Since the introduction of lambda expressions, anonymous classes are now often replaced
with much shorter implementations:
> ```java
>   Button redButton = new Button();
>   redButton.setOnAction(e -> System.out.println("Red button pressed!"));
> ```
> We cover lambda expressions in detail in the next chapter.

## Reviewing Nested Classes
For the exam, make sure that you know the information in Table 7.4 about which syntax
rules are permitted in Java.

> **Table 7.4**: Modifiers in nested classes

| Permitted modifiers |Inner class|static nested class|Local class| Anonymous class |
|---------------------|---|---|---|----------------|
| Access modifiers    |All |All |None| None           |
| abstract            |Yes |Yes |Yes | No             |
| final               |Yes |Yes |Yes | No             |

You should also know the information in Table 7.5 about types of access. For example,
the exam might try to trick you by having a static class access an outer class instance 
variable without a reference to the outer class.

> **Table 7.5**: Nested class access rules

|-|Inner class|static nested class|Local class|Anonymous class|
|---|---|---|---|---|
|Can extend a class or implement any number of interfaces?|Yes|Yes|Yes|No—must have exactly one superclass or one interface|
|Can access instance members of enclosing class?|Yes|No|Yes (if declared in an instance method)|Yes (if declared in an instance method)|
|Can access local variables of enclosing method?|N/a|N/a|Yes (if final or effectively final)|Yes (if final or effectively final)|
