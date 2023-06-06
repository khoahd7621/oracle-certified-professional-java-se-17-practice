# Understanding Inheritance

When creating a new class in Java, you can define the class as inheriting from an existing
class. Inheritance is the process by which a subclass automatically includes certain members
of the class, including primitives, objects, or methods, defined in the parent class. <br/>

&emsp;&emsp;
For illustrative purposes, we refer to any class that inherits from another class as a sub-class 
or child class, as it is considered a descendant of that class. Alternatively, we refer to
the class that the child inherits from as the superclass or parent class, as it is considered an
ancestor of the class.

> **Note**: <br/>
> When working with other types, like interfaces, we tend to use the general 
terms subtype and supertype. You see this more in the next chapter.

## I. Declaring a Subclass
Let’s begin with the declaration of a class and its subclass. The following code below shows an example of a
superclass, Mammal, and subclass Rhinoceros.

```java
public class Mammal { } // Superclass
public final class Rhinoceros extends Mammal { } //Subclass
```

&emsp;&emsp;
We indicate a class is a subclass by declaring it with the extends keyword. We don’t need
to declare anything in the superclass other than making sure it is not marked final. More
on that shortly. <br/>

&emsp;&emsp;
One key aspect of inheritance is that it is transitive. Given three classes [X, Y, Z], if X
extends Y, and Y extends Z, then X is considered a subclass or descendant of Z. Likewise,
Z is a superclass or ancestor of X. We sometimes use the term direct subclass or descendant
to indicate the class directly extends the parent class. For example, X is a direct descendant
only of class Y, not Z. <br/>

&emsp;&emsp;
In the last chapter, you learned that there are four access levels: public, protected,
package, and private. When one class inherits from a parent class, all public and
protected members are automatically available as part of the child class. If the two classes
are in the same package, then package members are available to the child class. Last but not
least, private members are restricted to the class they are defined in and are never available
via inheritance. This doesn’t mean the parent class can’t have private members that can
hold data or modify an object; it just means the subclass doesn’t have direct access to them.

```java
public class BigCat {
    protected double size;
}

public class Jaguar extends BigCat {
    public Jaguar() {
        size = 10.2;
    }
    public void printDetails() {
        System.out.print(size);
    }
}

public class Spider {
    public void printDetails() {
        System.out.println(size);  // DOES NOT COMPILE
    }
}
```

&emsp;&emsp;
Jaguar is a subclass or child of BigCat, making BigCat a superclass or parent of
Jaguar. In the Jaguar class, size is accessible because it is marked protected. Via 
inheritance, the Jaguar subclass can read or write size as if it were its own member. Contrast
this with the Spider class, which has no access to size since it is not inherited.


## II. Class Modifiers

Like methods and variables, a class declaration can have various modifiers. Table 6.1 lists the
modifiers you should know for the exam.

> **Table 6.1** Class Modifiers

| Modifier   | Description                                                                                          | Chapter covered |
|------------|------------------------------------------------------------------------------------------------------|-----------------|
| final      | The class may not be extended.                                                                       | Chapter 6       |
| abstract   | The class is abstract, may contain abstract methods, and requires a concrete subclass to instantiate. | Chapter 6       |                      
| sealed     | The class may only be extended by a specific list of classes.                                        | Chapter 7       |
| non-sealed | A subclass of a sealed class permits potentially unnamed subclasses.                                 | Chapter 7       |
| static     |Used for static nested classes defined within a class.| Chapter 7       |

&emsp;&emsp;
We cover abstract classes later in this chapter. In the next chapter, we cover sealed
and non-sealed classes, as well as static nested classes. <br />

&emsp;&emsp;
For now, let’s talk about marking a class final. The final modifier prevents a class
from being extended any further. For example, the following does not compile:

```java
public final class Rhinoceros extends Mammal { }
public class Clara extends Rhinoceros { } // DOES NOT COMPILE
```

&emsp;&emsp;
On the exam, pay attention to any class marked final. If you see another class extending
it, you know immediately the code does not compile.

## III. Single vs. Multiple Inheritance
Java supports single inheritance, by which a class may inherit from only one direct 
parent class. Java also supports multiple levels of inheritance, by which one class may extend
another class, which in turn extends another class. You can have any number of levels of
inheritance, allowing each descendant to gain access to its ancestor’s members. <br />

&emsp;&emsp;
To truly understand single inheritance, it may be helpful to contrast it with multiple 
inheritance, by which a class may have multiple direct parents. By design, Java doesn’t support
multiple inheritance in the language because multiple inheritance can lead to complex, often
difficult-to-maintain data models. Java does allow one exception to the single inheritance
rule, which you see in Chapter 7 — a class may implement multiple interfaces. <br />

&emsp;&emsp;
Figure 6.2 illustrates the various types of inheritance models. The items on the left are
considered single inheritance because each child has exactly one parent. You may notice that
single inheritance doesn’t preclude parents from having multiple children. The right side
shows items that have multiple inheritance. As you can see, a Dog object has multiple parent
designations.

> **Figure 6.2** Types of inheritance

```java
              Animal                      Animal     Pet     Friendly
           /          \                         \     |     /
    Mammal             Bird                          Dog
    /     \          /       \                     /      \
Bat     Tiger     Parrot     Eagle              Husky      Poodle
        Sigle Inheritance                      Multiple Inheritance
```

&emsp;&emsp;
Part of what makes multiple inheritance complicated is determining which parent to
inherit values from in case of a conflict. For example, if you have an object or method
defined in all of the parents, which one does the child inherit? There is no natural ordering
for parents in this example, which is why *Java avoids these issues by disallowing multiple
inheritance altogether*.

## IV. Inheriting *Object*
Throughout our discussion of Java in this book, we have thrown around the word object
numerous times—and with good reason. In Java, all classes inherit from a single class:
java.lang.Object, or Object for short. Furthermore, Object is the only class that
doesn’t have a parent class. <br />

&emsp;&emsp;
You might be wondering, “None of the classes I’ve written so far extend Object, so
how do all classes inherit from it?” The answer is that the compiler has been automatically
inserting code into any class you write that doesn’t extend a specific class. For example, the
following two are equivalent:

```java
public class Zoo { }
public class Zoo extends java.lang.Object { }
```

&emsp;&emsp;
The key is that when Java sees you define a class that doesn’t extend another class, the
compiler automatically adds the syntax extends java.lang.Object to the class definition. 
The result is that every class gains access to any accessible methods in the Object class.
For example, the toString() and equals() methods are available in Object; therefore,
they are accessible in all classes. Without being overridden in a subclass, though, they may
not be particularly useful. We cover overriding methods later in this chapter. <br />

&emsp;&emsp;
On the other hand, when you define a new class that extends an existing class, Java does
not automatically extend the Object class. Since all classes inherit from Object, extending an
existing class means the child already inherits from Object by definition. If you look at the
inheritance structure of any class, it will always end with Object on the top of the tree, as
shown in Figure 6.3.

> **Figure 6.3** Inheritance structure <br />
> java.lang.Object <br />
&emsp;&emsp;&emsp;^ <br />
&ensp;&ensp;&emsp;&ensp;&ensp;... <br />
&emsp;&emsp;&emsp;^ <br />
&emsp;&ensp;Mammal <br />
&emsp;&emsp;&emsp;^ <br />
&ensp;&ensp;&ensp;&ensp;&ensp;Ox <br />

&emsp;&emsp;
Primitive types such as int and boolean do not inherit from Object, since they are not
classes. As you learned in Chapter 5, through autoboxing they can be assigned or passed as
an instance of an associated wrapper class, which does inherit Object.
