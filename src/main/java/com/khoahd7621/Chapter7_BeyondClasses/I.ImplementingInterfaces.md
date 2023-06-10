# Implementing Interfaces

In Chapter 6, you learned about abstract classes, specifically how to create and extend
one. Since classes can only extend one class, they had limited use for inheritance. On the
other hand, a class may implement any number of interfaces. An interface is an abstract
data type that declares a list of abstract methods that any class implementing the interface
must provide. <br />

&emsp;&emsp;
Over time, the precise definition of an interface has changed, as new method types are
now supported. In this chapter, we start with a rudimentary definition of an interface and
expand it to cover all of the supported members.

# I. Declaring and Using an Interface

In Java, an interface is defined with the interface keyword, analogous to the class keyword
used when defining a class. Refer to code below for a proper interface declaration.

```java
    // implicit modifier abstract
// Access modifier is public or package
public abstract interface CanBurrow {
    // implicit modifier public abstract
    public abstract Float getSpeed(int age);   // Abstract interface method
    // implicit modifier public static final
    public static final int MINIMUM_DEPTH = 2; // Constant variable
}
```

&emsp;&emsp;
In the code above, our interface declaration includes an abstract method and a constant 
variable. Interface variables are referred to as constants because they are assumed to be public,
static, and final. They are initialized with a constant value when they are declared. Since
they are public and static, they can be used outside the interface declaration without
requiring an instance of the interface. The code above also includes an abstract method that, like
an interface variable, is assumed to be public.

> **Note**: <br />
> For brevity, we often say “an instance of an interface” in this chapter to
mean an instance of a class that implements the interface.

&emsp;&emsp;
What does it mean for a variable or method to be assumed to be something? One aspect
of an interface declaration that differs from an abstract class is that it contains implicit 
modifiers. An implicit modifier is a modifier that the compiler automatically inserts into the code.
For example, an interface is always considered to be abstract, even if it is not marked so.
We cover rules and examples for implicit modifiers in more detail shortly. <br />

&emsp;&emsp;
Let’s start with a simple example. Imagine that we have an interface WalksOnTwoLegs,
defined as follows:

```java
public abstract interface WalksOnTwoLegs {}
```

&emsp;&emsp;
It compiles because interfaces are not required to define any methods. The abstract
modifier in this example is optional for interfaces, with the compiler inserting it if it is not
provided. Now, consider the following two examples, which do not compile:

```java
public class Biped {
    public static void main(String[] args) {
        var e = new WalksOnTwoLegs(); // DOES NOT COMPILE
    }
}

public final interface WalksOnEightLegs {} // DOES NOT COMPILE
```

&emsp;&emsp;
The first example doesn’t compile, as WalksOnTwoLegs is an interface and cannot be
instantiated. The second example, WalksOnEightLegs, doesn’t compile because interfaces
cannot be marked as final for the same reason that abstract classes cannot be marked as
final. In other words, marking an interface final implies no class could ever implement it. <br />

&emsp;&emsp;
How do you use an interface? Let’s say we have an interface Climb, defined as follows:

```java
public interface Climb {
    Number getSpeed(int age);
}
```

&emsp;&emsp;
Next, we have a concrete class FieldMouse that invokes the Climb interface by using
the implements keyword in its class declaration, as shown in the following code:

```java
// implements keyword is required
// interface name(s) separated by commas (,)
public class FieldMouse implements Climb, CanBurrow {
    // public keyword is required
    // Signature matches interface method
    public Float getSpeed(int age) {
        return 11f;
    }
}
```

&emsp;&emsp;
The FieldMouse class declares that it implements the Climb interface and includes an
overridden version of getSpeed() inherited from the Climb interface. The method 
signature of getSpeed() matches exactly, and the return type is covariant, since a Float can be
implicitly cast to a Number. The access modifier of the interface method is implicitly public
in Climb, although the concrete class FieldMouse must explicitly declare it. <br />

&emsp;&emsp;
As shown in the code above, a class can implement multiple interfaces, each separated by
a comma (,). If any of the interfaces define abstract methods, then the concrete class is
required to override them. In this case, FieldMouse implements the CanBurrow interface
that we saw in the code above. In this manner, the class overrides two abstract methods at the
same time with one method declaration. You learn more about duplicate and compatible
interface methods in this chapter.

# II. DExtending an Interface

Like a class, an interface can extend another interface using the extends keyword.

```java
public interface Nocturnal {}
public interface HasBigEyes extends Nocturnal {}
```

&emsp;&emsp;
Unlike a class, which can extend only one class, an interface can extend multiple
interfaces.

```java
public interface Nocturnal {
    public int hunt();
}

public interface CanFly {
    public void flap();
}

public interface HasBigEyes extends Nocturnal, CanFly {}

public class Owl implements HasBigEyes {
    public int hunt() { return 5; }
    public void flap() { System.out.println("Flap!"); }
}
```

&emsp;&emsp;
In this example, the Owl class implements the HasBigEyes interface and must implement
the hunt() and flap() methods. Extending two interfaces is permitted because interfaces
are not initialized as part of a class hierarchy. Unlike abstract classes, they do not contain
constructors and are not part of instance initialization. Interfaces simply define a set of rules
and methods that a class implementing them must follow.

# III. Inheriting an Interface

Like an abstract class, when a concrete class inherits an interface, all of the inherited abstract
methods must be implemented. We illustrate this principle in the following code below. How many abstract
methods does the concrete Swan class inherit?

```java
interface Fly() {
    void fly();
}

interface Swin() {
    void swim();
}

abstract class Animal  {
    abstract int getType()
}

abstract class Bird extends Animal implements Fly {
    abstract boolean canSwoop()
}

class Swan extends Bird implements Swim {
    /// ???
}
```

&emsp;&emsp;
Give up? The concrete Swan class inherits four abstract methods that it must 
implement: getType(), canSwoop(), fly(), and swim(). Let’s take a look at another example
involving an abstract class that implements an interface:

```java
public interface HasTail {
    public int getTailLength();
}

public interface HasWhiskers {
    public int getNumberOfWhiskers();
}

public abstract class HarborSeal implements HasTail, HasWhiskers {}

public class CommonSeal extends HarborSeal {} // DOES NOT COMPILE
```

&emsp;&emsp;
The HarborSeal class compiles because it is abstract and not required to implement
any of the abstract methods it inherits. The concrete CommonSeal class, though, must
override all inherited abstract methods.

# IV. Mixing Class and Interface Keywords
The exam creators are fond of questions that mix class and interface terminology. Although
a class can implement an interface, a class cannot extend an interface. Likewise, while an
interface can extend another interface, an interface cannot implement another interface. The
following examples illustrate these principles:

```java
public interface CanRun {}
public class Cheetah extends CanRun {} // DOES NOT COMPILE

public class Hyena {}
public interface HasFur extends Hyena {} // DOES NOT COMPILE
```

&emsp;&emsp;
The first example shows a class trying to extend an interface and doesn’t compile. The
second example shows an interface trying to extend a class, which also doesn’t compile. Be
wary of examples on the exam that mix class and interface declarations.

# V. Inheriting Duplicate Abstract Methods
Java supports inheriting two abstract methods that have compatible method declarations.

```java
public interface Herbivore { public void eatPlants(); }

public interface Omnivore { public void eatPlants(); }

public class Bear implements Herbivore, Omnivore {
    public void eatPlants() {
        System.out.println("Eating plants");
    } 
}
```

&emsp;&emsp;
By compatible, we mean a method can be written that properly overrides both inherited
methods: for example, by using covariant return types that you learned about in Chapter 6. <br/>

&emsp;&emsp;
The following is an example of an incompatible declaration:

```java
public interface Herbivore { public void eatPlants(); }

public interface Omnivore { public int eatPlants(); }

public class Tiger implements Herbivore, Omnivore { // DOES NOT COMPILE
    ...
}
```

&emsp;&emsp;
It’s impossible to write a version of Tiger that satisfies both inherited abstract
methods. The code does not compile, regardless of what is declared inside the Tiger class.

# VI. Inserting Implicit Modifiers

As mentioned earlier, an implicit modifier is one that the compiler will automatically insert.
It’s reminiscent of the compiler inserting a default no-argument constructor if you do not
define a constructor, which you learned about in Chapter 6. You can choose to insert these
implicit modifiers yourself or let the compiler insert them for you. <br />

&emsp;&emsp;
The following list includes the implicit modifiers for interfaces that you need to know
for the exam:

- Interfaces are implicitly abstract.
- Interface variables are implicitly public, static, and final.
- Interface methods without a body are implicitly abstract.
- Interface methods without the private modifier are implicitly public.

&emsp;&emsp;
The last rule applies to abstract, default, and static interface methods, which we cover in
the next section. <br />

&emsp;&emsp;
Let’s take a look at an example. The following two interface definitions are equivalent, as
the compiler will convert them both to the second declaration:

```java
public interface Soar {
    int MAX_HEIGHT = 10;
    final static boolean UNDERWATER = true;

    void fly(int speed);

    abstract void takeoff();

    public abstract double dive();
}

public abstract interface Soar {
    public static final int MAX_HEIGHT = 10;
    public final static boolean UNDERWATER = true;
    public abstract void fly(int speed);
    public abstract void takeoff();
    public abstract double dive();
}
```

&emsp;&emsp;
In this example, we’ve marked in bold the implicit modifiers that the compiler automatically inserts. First, the abstract keyword is added to the interface declaration. Next, the
public, static, and final keywords are added to the interface variables if they do not
exist. Finally, each abstract method is prepended with the abstract and public keywords
if it does not contain them already

# VII. Conflicting Modifiers
What happens if a developer marks a method or variable with a modifier that conflicts with
an implicit modifier? For example, if an abstract method is implicitly public, can it be
explicitly marked protected or private?

```java
public interface Dance {
    private int count = 4; // DOES NOT COMPILE
    protected void step(); // DOES NOT COMPILE
}
```

Neither of these interface member declarations compiles, as the compiler will apply the
public modifier to both, resulting in a conflict.

# VIII. Differences between Interfaces and Abstract Classes
Even though abstract classes and interfaces are both considered abstract types, only 
interfaces make use of implicit modifiers. How do the play() methods differ in the following two
definitions?

```java
abstract class Husky {    // abstract required in class declaration
    abstract void play(); // abstract required in method declaration
}

interface Poodle { // abstract optional in interface declaration
    void play();   // abstract optional in method declaration
}
```

&emsp;&emsp;
Both of these method definitions are considered abstract. That said, the Husky class will
not compile if the play() method is not marked abstract, whereas the method in the
Poodle interface will compile with or without the abstract modifier. <br />

&emsp;&emsp;
What about the access level of the play() method? Can you spot anything wrong with
the following class definitions that use our abstract types?

```java
public class Webby extends Husky {
    void play() {} // OK - play() is declared with package access in Husky
}

public class Georgette implements Poodle {
    void play() {} // DOES NOT COMPILE - play() is public in Poodle
}
```

&emsp;&emsp;
The Webby class compiles, but the Georgette class does not. Even though the two
method implementations are identical, the method in the Georgette class reduces the access
modifier on the method from public to package access.

# IX. Declaring Concrete Interface Methods
While interfaces started with abstract methods and constants, they’ve grown to include a
lot more. Table 7.1 lists the six interface member types that you need to know for the exam.
We’ve already covered abstract methods and constants, so we focus on the remaining four
concrete methods in this section.

> **Table 7.1** Interface Member Types

|—|Membership type| Required modifiers | Implicit modifiers  |Has value or body?|
|---|---|--------------------|---------------------|---|
|Constant variable|Class|—| public static final |Yes|
|Abstract method|Instance| —| public abstract     |No|
|Default method|Instance| default            | public              |Yes|
|Static method|Class| static             | public              |Yes|
|Private method|Instance| private            | —                   |Yes|
|Private static method|Class| private static     | —                   |Yes|

&emsp;&emsp;
In Table 7.1, the membership type determines how it is able to be accessed. A method
with a membership type of class is shared among all instances of the interface, whereas a
method with a membership type of instance is associated with a particular instance of the
interface.

> **What About protected or Package Interface Members?**:
> Alongside public methods, interfaces now support private methods. They do not
support protected access, though, as a class cannot extend an interface. They also do not
support package access, although more likely for syntax reasons and backward 
compatibility. Since interface methods without an access modifier have been considered implicitly
public, changing this behavior to package access would break many existing programs!
