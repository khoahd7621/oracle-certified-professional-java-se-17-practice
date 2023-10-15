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

# II. Extending an Interface

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
In this example, we’ve marked in bold the implicit modifiers that the compiler automatically 
inserts. First, the abstract keyword is added to the interface declaration. Next, the
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

> **What About protected or Package Interface Members?**: <br/>
> Alongside public methods, interfaces now support private methods. They do not
support protected access, though, as a class cannot extend an interface. They also do not
support package access, although more likely for syntax reasons and backward 
compatibility. Since interface methods without an access modifier have been considered implicitly
public, changing this behavior to package access would break many existing programs!

### &emsp;&emsp; 1. Writing a default Interface Method
The first type of concrete method you should be familiar with for the exam is a default
method. A default method is a method defined in an interface with the default keyword
and includes a method body. It may be optionally overridden by a class implementing the
interface. <br />

&emsp;&emsp;
One use of default methods is for backward compatibility. You can add a new default
method to an interface without the need to modify all of the existing classes that implement
the interface. The older classes will just use the default implementation of the method defined
in the interface. This is where the name default method comes from! <br />

&emsp;&emsp;
The following is an example of a default method defined in an interface:

```java
public interface IsColdBlooded {
    boolean hasScales();
    default double getTemperature() {
        return 10.0;
    } 
}
```

&emsp;&emsp;
This example defines two interface methods, one abstract and one default. The following 
Snake class, which implements IsColdBlooded, must implement hasScales(). It
may rely on the default implementation of getTemperature() or override the method with
its own version:

```java
public class Snake implements IsColdBlooded {
    public boolean hasScales() { // Required override
        return true;
    }
    public double getTemperature() { // Optional override
        return 12;
    }
}
```

> **Note**: <br/>
> Note that the default interface method modifier is not the same as the
default label used in a switch statement or expression. Likewise, even
though package access is sometimes referred to as default access, that
feature is implemented by omitting an access modifier. Sorry if this is
confusing! We agree Java has overused the word default over the years!

&emsp;&emsp;
For the exam, you should be familiar with various rules for declaring default methods. <br />

**Default Interface Method Definition Rules**
1. A default method may be declared only within an interface.
2. A default method must be marked with the default keyword and include a method body.
3. A default method is implicitly public.
4. A default method cannot be marked abstract, final, or static.
5. A default method may be overridden by a class that implements the interface.
6. If a class inherits two or more default methods with the same method signature, then the
   class must override the method.

&emsp;&emsp;
The first rule should give you some comfort in that you’ll only see default methods in
interfaces. If you see them in a class or enum on the exam, something is wrong. The second
rule just denotes syntax, as default methods must use the default keyword. For example,
the following code snippets will not compile because they mix up concrete and abstract
interface methods:

```java
public interface Carnivore {
    public default void eatMeat(); // DOES NOT COMPILE
    public int getRequiredFoodAmount() { // DOES NOT COMPILE
        return 13;
    } 
}
```

&emsp;&emsp;
The next three rules for default methods follow from the relationship with abstract
interface methods. Like abstract interface methods, default methods are implicitly public.
Unlike abstract methods, though, default interface methods cannot be marked abstract
since they provide a body. They also cannot be marked as final, because they are designed
so that they can be overridden in classes implementing the interface, just like abstract
methods. Finally, they cannot be marked static since they are associated with the instance
of the class implementing the interface.

### &emsp;&emsp; 2. Inheriting Duplicate default Methods
The last rule for creating a default interface method requires some explanation. For example,
what value would the following code output?

```java
public interface Walk {
    public default int getSpeed() { return 5; }
}

public interface Run {
    public default int getSpeed() { return 10; }
}

public class Cat implements Walk, Run {} // DOES NOT COMPILE
```

&emsp;&emsp;
In this example, Cat inherits the two default methods for getSpeed(), so which does
it use? Since Walk and Run are considered siblings in terms of how they are used in the Cat
class, it is not clear whether the code should output 5 or 10. In this case, the compiler throws
up its hands and says, “Too hard, I give up!” and fails. <br />

&emsp;&emsp;
All is not lost, though. If the class implementing the interfaces overrides the duplicate
default method, the code will compile without issue. By overriding the conflicting method,
the ambiguity about which version of the method to call has been removed. For example, the
following modified implementation of Cat will compile:

```java
public class Cat implements Walk, Run {
    public int getSpeed() { return 1; }
}
```

### &emsp;&emsp; 3. Calling a Hidden default Method
In the last section, we showed how our Cat class could override a pair of conflicting
default methods, but what if the Cat class wanted to access the version of getSpeed() in
Walk or Run? Is it still accessible? <br />

&emsp;&emsp;
Yes, but it requires some special syntax.

```java
public class Cat implements Walk, Run {
    public int getSpeed() {
        return 1;
    }
    public int getWalkSpeed() {
        return Walk.super.getSpeed();
    } 
}
```

&emsp;&emsp;
This is an area where a default method exhibits properties of both a static and 
instance method. We use the interface name to indicate which method we want to call, but we
use the super keyword to show that we are following instance inheritance, not class 
inheritance. Note that calling Walk.getSpeed() or Walk.this.getSpeed() would not have
worked. A bit confusing, we know, *but you need to be familiar with this syntax for the exam*.

### &emsp;&emsp; 4. Declaring static Interface Methods

Interfaces are also declared with static methods. These methods are defined explicitly with
the static keyword and, for the most part, behave just like static methods defined in classes.

**Static Interface Method Definition Rules**
1. A static method must be marked with the static keyword and include a
   method body.
2. A static method without an access modifier is implicitly public.
3. A static method cannot be marked abstract or final.
4. A static method is not inherited and cannot be accessed in a class implementing the
   interface without a reference to the interface name.

&emsp;&emsp;
These rules should follow from what you know so far of classes, interfaces, and static
methods. For example, you can’t declare static methods without a body in classes, either. Like
default and abstract interface methods, static interface methods are implicitly public if they
are declared without an access modifier. As you see shortly, you can use the private access
modifier with static methods. <br />

&emsp;&emsp;
Let’s take a look at a static interface method:

```java
public interface Hop {
    static int getJumpHeight() {
        return 8;
    } 
}
```

&emsp;&emsp;
Since the method is defined without an access modifier, the compiler will automatically 
insert the public access modifier. The method getJumpHeight() works just like a
static method as defined in a class. In other words, it can be accessed without an instance
of a class.

```java
public class Skip {
    public int skip() {
        return Hop.getJumpHeight();
    } 
}
```

&emsp;&emsp;
The last rule about inheritance might be a little confusing, so let’s look at an example. The
following is an example of a class Bunny that implements Hop and does not compile:

```java
public class Bunny implements Hop {
    public void printDetails() {
        System.out.println(getJumpHeight()); // DOES NOT COMPILE
    } 
}
```

&emsp;&emsp;
Without an explicit reference to the name of the interface, the code will not compile, even
though Bunny implements Hop. This can be easily fixed by using the interface name:

```java
public class Bunny implements Hop {
    public void printDetails() {
        System.out.println(Hop.getJumpHeight());
    } 
}
```

&emsp;&emsp;
Notice we don’t have the same problem we did when we inherited two default interface
methods with the same signature. Java “solved” the multiple inheritance problem of static
interface methods by not allowing them to be inherited!

### &emsp;&emsp; 5. Reusing Code with private Interface Methods
The last two types of concrete methods that can be added to interfaces are private and
private static interface methods. Because both types of methods are private, they
can only be used in the interface declaration in which they are declared. For this reason,
they were added primarily to reduce code duplication. For example, consider the following
code sample:

```java
public interface Schedule {
    default void wakeUp() { checkTime(7); }
    private void haveBreakfast() { checkTime(9); }
    static void workOut() { checkTime(18); }
    private static void checkTime(int hour) {
        if (hour> 17) {
            System.out.println("You're late!");
        } else {
            System.out.println("You have "+(17-hour)+" hours left "
                + "to make the appointment");
        } 
    } 
}
```

&emsp;&emsp;
You could write this interface without using a private method by copying the contents of the checkTime() method into the places it is used. It’s a lot shorter and easier to
read if you don’t. Since the authors of Java were nice enough to add this feature for our
convenience, we might as well use it!

> **Note**: <br />
> We could have also declared checkTime() as public in the previous
example, but this would expose the method to use outside the interface.
One important tenet of encapsulation is to not expose the internal 
workings of a class or interface when not required. We cover encapsulation
later in this chapter.

&emsp;&emsp;
The difference between a non-static private method and a static one is 
analogous to the difference between an instance and static method declared within a class. In
particular, it’s all about what methods each can be called from. <br/>

**Private Interface Method Definition Rules**
1. A private interface method must be marked with the private modifier and include a
   method body.
2. A private static interface method may be called by any method within the interface
   definition.
3. A private interface method may only be called by default and other private 
   nonstatic methods within the interface definition.

&emsp;&emsp;
Another way to think of it is that a private interface method is only accessible to 
nonstatic methods defined within the interface. A private static interface method, on
the other hand, can be accessed by any method in the interface. For both types of private
methods, a class inheriting the interface cannot directly invoke them.

### &emsp;&emsp; 6. Calling Abstract Methods
We’ve talked a lot about the newer types of interface methods, but what about abstract
methods? It turns out default and private non-static methods can access abstract methods
declared in the interface. This is the primary reason we associate these methods with instance
membership. When they are invoked, there is an instance of the interface.

```java
public interface ZooRenovation {
    public String projectName();
    abstract String status();
    default void printStatus() {
        System.out.print("The " + projectName() + " project " + status());
    } 
}
```

&emsp;&emsp;
In this example, both projectName() and status() have the same modifiers 
(abstract and public are implicit) and can be called by the default method
printStatus().

### &emsp;&emsp; 7. Reviewing Interface Members
We conclude our discussion of interface members with Table 7.2, which shows the access
rules for members within and outside an interface.

> **Table 7.2** Interface Member Access Rules

|-|Accessible from *default* and *private* methods within the interface?| Accessible from *static* methods within the interface? | Accessible from methods in classes inheriting the interface? | Accessible without an instance of the interface? |
|---|---|--------------------------------------------------|--------------------------------------------------------------|--------------------------------------------------|
|Constant variable|Yes|Yes| Yes                                                          | Yes                                              |
|Abstract method|Yes|No| Yes                                                          | No                                               |
|Default method|Yes|No| Yes                                                          | No                                               |
|Static method|Yes|Yes| Yes (interface name required)                                | Yes (interface name required)                                                   |
|Private method|Yes|No| No                                                           | No                                               |
|Private static method|Yes|Yes| No                                                           | No                                               |

&emsp;&emsp;
While Table 7.2 might seem like a lot to remember, here are some quick tips for the exam:

- Treat abstract, default, and non-static private methods as belonging to an instance of the interface.
- Treat static methods and variables as belonging to the interface class object.
- All private interface method types are only accessible within the interface declaration.

&emsp;&emsp;
Using these rules, which of the following methods do not compile?

```java
public interface ZooTrainTour {
    abstract int getTrainName();
    private static void ride() {}
    default void playHorn() { getTrainName(); ride(); }
    public static void slowDown() { playHorn(); }
    static void speedUp() { ride(); }
}
```

&emsp;&emsp;
The ride() method is private and static, so it can be accessed by any default or
static method within the interface declaration. The getTrainName() is abstract, so
it can be accessed by a default method associated with the instance. The slowDown()
method is static, though, and cannot call a default or private method, such as
playHorn(), without an explicit reference object. Therefore, the slowDown() method does
not compile. <br />

&emsp;&emsp;
Give yourself a pat on the back! You just learned a lot about interfaces, probably more
than you thought possible. Now take a deep breath. Ready? The next type we are going to
cover is enums.
