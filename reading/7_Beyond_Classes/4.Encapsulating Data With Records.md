# Encapsulating Data with Records

We saved the best new Java type for last! If you’ve heard anything about the new features in
Java, you have probably heard about records. Records are exciting because they remove a
ton of boilerplate code. Before we get into records, it helps to have some context of why they
were added to the language, so we start with encapsulation.

## I. Understanding Encapsulation
A POJO, which stands for Plain Old Java Object, is a class used to model and pass data
around, often with few or no complex methods (hence the “plain” part of the definition). 
You might have also heard of a JavaBean, which is POJO that has some additional
rules applied. Let’s create a simple POJO with two fields:

```java
public class Crane {
    int numberEggs;
    String name;
    public Crane(int numberEggs, String name) {
        this.numberEggs = numberEggs;
        this.name = name;
    }
}
```

&emsp;&emsp;
Uh oh, the fields are package access. Why do we care? That means someone outside the
class in the same package could change these values and create invalid data such as this:

```java
public class Poacher {
    public void badActor() {
        var mother = new Crane(5, "Cathy");
        mother.numberEggs = -100;
    }
}
```

&emsp;&emsp;
This is clearly no good. We do not want the mother Crane to have a negative number
of eggs! Encapsulation to the rescue. Encapsulation is a way to protect class members by
restricting access to them. In Java, it is commonly implemented by declaring all instance 
variables private. Callers are required to use methods to retrieve or modify instance variables. <br />

&emsp;&emsp;
Encapsulation is about protecting a class from unexpected use. It also allows us to modify
the methods and behavior of the class later without someone already having direct access
to an instance variable within the class. For example, we can change the data type of an 
instance variable but maintain the same method signatures. In this manner, we maintain full
control over the internal workings of a class. <br />

&emsp;&emsp;
Let’s take a look at the newly encapsulated (and immutable) Crane class:

```java
1:  public final class Crane {
2:      private final int numberEggs;
3:      private final String name;
4:      public Crane(int numberEggs, String name) {
5:          if (numberEggs >= 0) this.numberEggs = numberEggs; // guard condition
6:          else throw new IllegalArgumentException();
7:          this.name = name;
8:      }
9:      public int getNumberEggs() { // getter
10:         return numberEggs;
11:     }
12:     public String getName() { // getter
13:         return name;
14:     } 
15: }
```

&emsp;&emsp;
Note that the instance variables are now private on lines 2 and 3. This means only code
within the class can read or write their values. Since we wrote the class, we know better than
to set a negative number of eggs. We added a method on lines 9–11 to read the value, which
is called an accessor method or a getter. <br />

&emsp;&emsp;
You might have noticed that we marked the class and its instance variables final, and we
don’t have any mutator methods, or setters, to modify the value of the instance variables.
That’s because we want our class to be immutable in addition to being well encapsulated.
As you saw in Chapter 6, the immutable objects pattern is an object-oriented design pattern
in which an object cannot be modified after it is created. Instead of modifying an immutable
object, you create a new object that contains any properties from the original object you
want copied over. <br />

&emsp;&emsp;
To review, remember that data (an instance variable) is private and getters/setters
are public for encapsulation. You don’t even have to provide getters and setters. As long
as the instance variables are private, you are good. For example, the following class
is well encapsulated, although it is not terribly useful since it doesn’t declare any non-private methods:

```java
public class Vet {
    private String name = "Dr Rogers";
    private int yearsExperience = 25;
}
```

&emsp;&emsp;
You must omit the setters for a class to be immutable. Review Chapter 6 for the 
additional rules on creating immutable object.

## II. Applying Records
Our Crane class was 15 lines long. We can write that much more succinctly, as shown in
Figure 7.6. Putting aside the guard clause on numberEggs in the constructor for a moment,
this record is equivalent and immutable!

> **Figure 7.6** Defining a record

```java
// record keyword
// Record name
// List of fields surrounded by parentheses
public record Crane(int numberEggs, String name) { 
    // May declare optional constructors, methods, and constants
}
```

&emsp;&emsp;
Wow! It’s only one line long! A record is a special type of data-oriented class in which the
compiler inserts boilerplate code for you. <br />

&emsp;&emsp;
In fact, the compiler inserts much more than the 14 lines we wrote earlier. As a bonus, the
compiler inserts useful implementations of the Object methods equals(), hashCode(),
and toString(). We’ve covered a lot in one line of code! <br />

&emsp;&emsp;
Now imagine that we had 10 data fields instead of 2. That’s a lot of methods we are saved
from writing. And we haven’t even talked about constructors! Worse yet, any time someone
changes a field, dozens of lines of related code may need to be updated. For example, name
may be used in the constructor, toString(), equals() method, and so on. If we have an
application with hundreds of POJOs, a record can save us valuable time. <br />

&emsp;&emsp;
Creating an instance of a Crane and printing some fields is easy:

```java
var mommy = new Crane(4, "Cammy");
System.out.println(mommy.numberEggs()); // 4
System.out.println(mommy.name());       // Cammy
```

&emsp;&emsp;
A few things should stand out here. First, we never defined any constructors or methods
in our Crane declaration. How does the compiler know what to do? Behind the scenes, it
creates a constructor for you with the parameters in the same order in which they appear in
the record declaration. Omitting or changing the type order will lead to compiler errors:

```java
var mommy1 = new Crane("Cammy", 4); // DOES NOT COMPILE
var mommy2 = new Crane("Cammy");    // DOES NOT COMPILE
```

&emsp;&emsp;
For each field, it also creates an accessor as the field name, plus a set of parentheses.
Unlike traditional POJOs or JavaBeans, the methods don’t have the prefix get or is. Just a
few more characters that records save you from having to type! Finally, records override a
number of methods in Object for you. <br />

**Members Automatically Added to Records**
- Constructor: A constructor with the parameters in the same order as the record
  declaration
- Accessor method: One accessor for each field
- equals(): A method to compare two elements that returns true if each field is equal in
  terms of equals() 
- hashCode(): A consistent hashCode() method using all of the fields
- toString(): A toString() implementation that prints each field of the record in a convenient, easy-to-read format

&emsp;&emsp;
The following shows examples of the new methods. Remember that the println()
method will call the toString() method automatically on any object passed to it.

```java
var father = new Crane(0, "Craig");
System.out.println(father);         // Crane[numberEggs=0, name=Craig]
        
var copy = new Crane(0, "Craig");
System.out.println(copy);           // Crane[numberEggs=0, name=Craig]
System.out.println(father.equals(copy)); // true
System.out.println(father.hashCode() + ", " + copy.hashCode()); // 1007, 1007
```

&emsp;&emsp;
That’s the basics of records. We say “basics” because there’s a lot more you can do with
them, as you see in the next sections.

> **Note**: <br />
> Given our one-line declaration of Crane, imagine how much code and
work would be required to write an equivalent class. It could easily take
40+ lines! It might be a fun exercise to try to write all the methods that
records supply.

&emsp;&emsp;
Fun fact: it is legal to have a record without any fields. It is simply declared with the
record keyword and parentheses:

```java
public record Crane() {}
```

Not the kind of thing you’d use in your own code, but it could come up on the exam.

## III. Understanding Record Immutability

As you saw, records don’t have setters. Every field is inherently final and cannot be modified
after it has been written in the constructor. In order to “modify” a record, you have to make
a new object and copy all of the data you want to preserve. 

```java
var cousin = new Crane(3, "Jenny");
var friend = new Crane(cousin.numberEggs(), "Janeice");
```

&emsp;&emsp;
Just as interfaces are implicitly abstract, records are also implicitly final. The final
modifier is optional but assumed.

```java
public final record Crane(int numberEggs, String name) {}
```

&emsp;&emsp;
Like enums, that means you can’t extend or inherit a record.

```java
public record BlueCrane() extends Crane {} // DOES NOT COMPILE
```

&emsp;&emsp;
Also like enums, a record can implement a regular or sealed interface, provided it implements all of the abstract methods.

```java
public interface Bird {}
public record Crane(int numberEggs, String name) implements Bird {}
```

> **Note**: <br />
> Although well beyond the scope of this book, there are some good 
reasons to make data-oriented classes immutable. Doing so can lead to less
error-prone code, as a new object is established any time the data is 
modified. It also makes them inherently thread-safe and usable in concurrent
frameworks.

## IV. Declaring Constructors

What if you need to declare a record with some guards as we did earlier? In this section, we
cover two ways we can accomplish this with records.

### &emsp;&emsp; 1. The Long Constructor

First, we can just declare the constructor the compiler normally inserts automatically, which
we refer to as the long constructor.

```java
public record Crane(int numberEggs, String name) {
    public Crane(int numberEggs, String name) {
        if (numberEggs < 0) throw new IllegalArgumentException();
        this.numberEggs = numberEggs;
        this.name = name;
    }
}
```

&emsp;&emsp;
The compiler will not insert a constructor if you define one with the same list of 
parameters in the same order. Since each field is final, the constructor must set every field. For
example, this record does not compile:

```java
public record Crane(int numberEggs, String name) {
    public Crane(int numberEggs, String name) {} // DOES NOT COMPILE
}
```

&emsp;&emsp;
While being able to declare a constructor is a nice feature of records, it’s also problematic.
If we have 20 fields, we’ll need to declare assignments for every one, introducing the 
boilerplate we sought to remove. Oh, bother!

### &emsp;&emsp; 2. Compact Constructors
Luckily, the authors of Java added the ability to define a compact constructor for records. A
compact constructor is a special type of constructor used for records to process validation
and transformations succinctly. It takes no parameters and implicitly sets all fields. Figure 7.7
shows an example of a compact constructor.

> **FIGURE 7.7**: Declaring a compact constructor
 
```java

public record Crane(int numberEggs, String name) {
    // No parentheses or constructor parameters
    public Crane {
        // Custom validation
        if (numberEggs < 0) throw new IllegalArgumentException();
        name = name.toUpperCase();
        // name: Refers to input parameters (not instance members)
    }
    // Long constructor implicitly called at end of compact constructor
}
```

&emsp;&emsp;
Great! Now we can check the values we want, and we don’t have to list all the 
constructor parameters and trivial assignments. Java will execute the full constructor after the
compact constructor. You should also remember that a compact constructor is declared
without parentheses, as the exam might try to trick you on this. As shown in Figure 7.7, we
can even transform constructor parameters as we discuss more in the next section.

> **Note**: <br />
> You might think that you need custom methods for every field in the
record, like the negative check we did with setNumberEggs(). In
practice, many POJOs are created for general-purpose use with little
validation.

### &emsp;&emsp; 3. Transforming Parameters

Compact constructors give you the opportunity to apply transformations to any of the input
values. See if you can figure out what the following compact constructor does:

```java
public record Crane(int numberEggs, String name) {
    public Crane {
        if (name == null || name.length() < 1)
            throw new IllegalArgumentException();
        name = name.substring(0,1).toUpperCase()
                + name.substring(1).toLowerCase();
    }
}
```

&emsp;&emsp;
Give up? It validates the string, then formats it such that only the first letter is capitalized.
As before, Java calls the full constructor after the compact constructor but with the modified
constructor parameters. <br />

&emsp;&emsp;
While compact constructors can modify the constructor parameters, they cannot modify
the fields of the record. For example, this does not compile:

```java
public record Crane(int numberEggs, String name) {
    public Crane {
        this.numberEggs = 10; // DOES NOT COMPILE
    }
}
```

&emsp;&emsp;
Removing the this reference allows the code to compile, as the constructor parameter is
modified instead.

> **Tip**: <br />
> Although we covered both the long and compact forms of record 
constructors in this section, it is highly recommended that you stick with the
compact form unless you have a good reason not to.

### &emsp;&emsp; 4. Overloaded Constructors
You can also create overloaded constructors that take a completely different list of 
parameters. They are more closely related to the long-form constructor and don’t use any of the syntactical features of compact constructors

```java
public record Crane(int numberEggs, String name) {
    public Crane(String firstName, String lastName) {
        this(0, firstName + " " + lastName);
    }
}
```

&emsp;&emsp;
The first line of an overloaded constructor must be an explicit call to another constructor
via this(). If there are no other constructors, the long constructor must be called. Contrast
this with what you learned about in Chapter 6, where calling super() or this() was often
optional in constructor declarations. Also, unlike compact constructors, you can only 
transform the data on the first line. After the first line, all of the fields will already be assigned,
and the object is immutable.

```java
public record Crane(int numberEggs, String name) {
    public Crane(int numberEggs, String firstName, String lastName) {
        this(numberEggs + 1, firstName + " " + lastName);
        numberEggs = 10;      // NO EFFECT (applies to parameter, not instance field)
        this.numberEggs = 20; // DOES NOT COMPILE
    }
}
```

&emsp;&emsp;
As you saw in Chapter 6, you also can’t declare two record constructors that call each
other infinitely or as a cycle.

```java
public record Crane(int numberEggs, String name) {
    public Crane(String name) {
        this(1); // DOES NOT COMPILE
    }
    public Crane(int numberEggs) {
        this(""); // DOES NOT COMPILE
    }
}
```

## V. Customizing Records
Since records are data-oriented, we’ve focused on the features of records you are likely to
use. Records actually support many of the same features as a class. Here are some of the
members that records can include and that you should be familiar with for the exam:

- Overloaded and compact constructors
- Instance methods including overriding any provided methods (accessors, equals(),
  hashCode(), toString())
- Nested classes, interfaces, annotations, enum, and records

&emsp;&emsp;
As an illustrative example, the following overrides two instance methods using the
optional @Override annotation:

```java
public record Crane(int numberEggs, String name) {
    @Override public int numberEggs() { return 10; }
    @Override public String toString() { return name; }
}
```

&emsp;&emsp;
While you can add methods, static fields, and other data types, you cannot add instance
fields outside the record declaration, even if they are private. Doing so defeats the purpose
of using a record and could break immutability!

```java
public record Crane(int numberEggs, String name) {
    private static int type = 10;
    public int size;          // DOES NOT COMPILE
    private boolean friendly; // DOES NOT COMPILE
}
```

&emsp;&emsp;
Records also do not support instance initializers. All initialization for the fields of a
record must happen in a constructor.

> **Tip**: <br />
> While it’s a useful feature that records support many of the same 
members as a class, try to keep them simple. Like the POJOs and JavaBeans
they were born out of, the more complicated they get, the less usable
they become.

This is the second time we’ve mentioned nested types, the first being with sealed classes
and now records. Don’t worry; we’re covering them next!
