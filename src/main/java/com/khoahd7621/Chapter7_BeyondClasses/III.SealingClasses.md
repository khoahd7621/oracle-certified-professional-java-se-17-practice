# Sealing Classes

An enum with many constructors, fields, and methods may start to resemble a full-featured
class. What if we could create a class but limit the direct subclasses to a fixed set of classes?
Enter sealed classes! A sealed class is a class that restricts which other classes may directly
extend it. These are brand new to Java 17, so expect to see at least one question about them
on the exam.

> **Note**: <br />
> Did you happen to notice that we said directly extend in the definition of
a sealed class? As you see shortly, there is a way for a class not named
in the sealed class declaration to extend it indirectly. Unless we say 
otherwise, though, assume that we’re referring to subclasses that directly
extend the sealed class.

## I. Declaring a Sealed Class
Let’s start with a simple example. A sealed class declares a list of classes that can extend it,
while the subclasses declare that they extend the sealed class. Figure 7.5 declares a sealed
class with two direct subclasses.

> **Figure 7.5** Defining a sealed class

```java
// sealed keyword
// class keyword
// permits keyword
// List of permitted classes: Kodiak, Panda
public sealed class Bear permits Kodiak, Panda {}

// final, sealed or non-sealed subclass modifier
public final class Kodiak extends Bear {}
public non-sealed class Panda extends Bear {}
```

&emsp;&emsp;
Notice anything new? Java 17 includes three new keywords that you should be familiar
with for the exam. We often use final with sealed subclasses, but we get into each of these
after we cover the basics. <br />

**Sealed Class Keywords**
- **sealed**: Indicates that a class or interface may only be extended/implemented by named
  classes or interfaces
- **permits**: Used with the sealed keyword to list the classes and interfaces allowed
- **non-sealed**: Applied to a class or interface that extends a sealed class, indicating that it
  can be extended by unspecified classes

&emsp;&emsp;
Pretty easy so far, right? The exam is just as likely to test you on what sealed classes
cannot be used for. For example, can you see why each of these sets of declarations does
not compile?

```java
public class sealed Frog permits GlassFrog {} // DOES NOT COMPILE
public final class GlassFrog extends Frog {}

public abstract sealed class Wolf permits Timber {}
public final class Timber extends Wolf {}
public final class MyWolf extends Wolf {} // DOES NOT COMPILE
```

&emsp;&emsp;
The first example does not compile because the class and sealed modifiers are in the
wrong order. The modifier has to be before the class type. The second example does not
compile because MyWolf isn’t listed in the declaration of Wolf.

> **Note**: <br />
> Sealed classes are commonly declared with the abstract modifier,
although this is certainly not required.

&emsp;&emsp;
Declaring a sealed class with the sealed modifier is the easy part. Most of the time, if
you see a question on the exam about sealed classes, they are testing your knowledge of
whether the subclass extends the sealed class properly. There are a number of important
rules you need to know for the exam, so read the next sections carefully.

## II. Compiling Sealed Classes
Let’s say we create a Penguin class and compile it in a new package without any other source
code. With that in mind, does the following compile?

```java
// Penguin.java
package zoo;

public sealed class Penguin permits Emperor {}
```

&emsp;&emsp;
No, it does not! Why? The answer is that a sealed class needs to be declared 
(and compiled) in the same package as its direct subclasses. But what about the subclasses themselves?
They must each extend the sealed class. For example, the following does not compile.

```java
// Penguin.java
package zoo;
public sealed class Penguin permits Emperor {} // DOES NOT COMPILE

// Emperor.java
package zoo;
public final class Emperor {}
```

&emsp;&emsp;
Even though the Emperor class is declared, it does not extend the Penguin class.

> **Note**: <br />
> But wait, there’s more! In Chapter 12, “Modules,” you learn about named
modules, which allow sealed classes and their direct subclasses in 
different packages, provided they are in the same named module.

## III. Specifying the Subclass Modifier
While some types, like interfaces, have a certain number of implicit modifiers, sealed classes
do not. Every class that directly extends a sealed class must specify exactly one of the following 
three modifiers: final, sealed, or non-sealed. Remember this rule for the exam!

### &emsp;&emsp; 1. A final Subclass
The first modifier we’re going to look at that can be applied to a direct subclass of a sealed
class is the final modifier.

```java
public sealed class Antelope permits Gazelle {} 

public final class Gazelle extends Antelope {}

public class George extends Gazelle {} // DOES NOT COMPILE
```

&emsp;&emsp;
Just as with a regular class, the final modifier prevents the subclass Gazelle from being
extended further.

### &emsp;&emsp; 2. A sealed Subclass
Next, let’s look at an example using the sealed modifier:

```java
public sealed class Mammal permits Equine {}

public sealed class Equine extends Mammal permits Zebra {}

public final class Zebra extends Equine {}
```

&emsp;&emsp;
The sealed modifier applied to the subclass Equine means the same kind of rules that
we applied to the parent class Mammal must be present. Namely, Equine defines its own list
of permitted subclasses. Notice in this example that Zebra is an indirect subclass of Mammal
but is not named in the Mammal class. <br />

&emsp;&emsp;
Despite allowing indirect subclasses not named in Mammal, the list of classes that can
inherit Mammal is still fixed. If you have a reference to a Mammal object, it must be a Mammal,
Equine, or Zebra.

### &emsp;&emsp; 3. A non-sealed Subclass
The non-sealed modifier is used to open a sealed parent class to potentially unknown 
subclasses. Let’s modify our earlier example to allow MyWolf to compile without modifying the
declaration of Wolf:

```java
public sealed class Wolf permits Timber {}

public non-sealed class Timber extends Wolf {}

public class MyWolf extends Timber {}
```

&emsp;&emsp;
In this example, we are able to create an indirect subclass of Wolf, called MyWolf, not
named in the declaration of Wolf. Also notice that MyWolf is not final, so it may be
extended by any subclass, such as MyFurryWolf.

```java
public class MyFurryWolf extends MyWolf {}
```

&emsp;&emsp;
At first glance, this might seem a bit counterintuitive. After all, we were able to create 
subclasses of Wolf that were not declared in Wolf. So is Wolf still sealed? Yes, but that’s thanks
to polymorphism. Any instance of MyWolf or MyFurryWolf is also an instance of Timber,
which is named in the Wolf declaration. We discuss polymorphism more toward the end of
this chapter.
 
> **Tip**: <br />
> If you’re still worried about opening a sealed class too much with a 
non-sealed subclass, remember that the person writing the sealed class can
see the declaration of all direct subclasses at compile time. They can
decide whether to allow the non-sealed subclass to be supported.

## IV. Omitting the permits Clause
Up until now, all of the examples you’ve seen have required a permits clause when
declaring a sealed class, but this is not always the case. Imagine that you have a Snake.java
file with two top-level classes defined inside it:

```java
// Snake.java
public sealed class Snake permits Cobra {}
final class Cobra extends Snake {}
```

&emsp;&emsp;
In this case, the permits clause is optional and can be omitted. The extends keyword is
still required in the subclass, though:

```java
// Snake.java
public sealed class Snake {}
final class Cobra extends Snake {}
```

&emsp;&emsp;
If these classes were in separate files, this code would not compile! This rule also applies
to sealed classes with nested subclasses.

```java
// Snake.java
public sealed class Snake {
    final class Cobra extends Snake {}
}
```

> **Referencing Nested Subclasses**: <br />
> While it makes the code easier to read if you omit the permits clause for nested 
subclasses, you are welcome to name them. However, the syntax might be different than
you expect.
> ```java
>   public sealed class Snake permits Cobra { // DOES NOT COMPILE
>       final class Cobra extends Snake {}
>   }
> ```
> This code does not compile because Cobra requires a reference to the Snake namespace.
The following fixes this issue:
> ```java
>   public sealed class Snake permits Snake.Cobra {
>       final class Cobra extends Snake {}
>   }
> ```
> When all of your subclasses are nested, we strongly recommend omitting the permits class.

&emsp;&emsp;
We cover nested classes shortly. For now, you just need to know that a nested class is a
class defined inside another class and that the omit rule also applies to nested classes.
Table 7.3 is a handy reference to these cases:

> **Table 7.3** Usage of the permits clause in sealed classes

|Location of direct subclasses|*permits* clause|
|:---|:---|
|In a different file from the sealed class|Required|
|In the same file as the sealed class|Permitted, but not required|
|Nested inside of the sealed class|Permitted, but not required|

## V. Sealing Interfaces
Besides classes, interfaces can also be sealed. The idea is analogous to classes, and many of
the same rules apply. For example, the sealed interface must appear in the same package or
named module as the classes or interfaces that directly extend or implement it. <br />

&emsp;&emsp;
One distinct feature of a sealed interface is that the permits list can apply to a class that
implements the interface or an interface that extends the interface.

```java
// Sealed interface
public sealed interface Swims permits Duck, Swan, Floats {}

// Classes permitted to implement sealed interface
public final class Duck implements Swims {}
public final class Swan implements Swims {}

// Interface permitted to extend sealed interface
public non-sealed interface Floats extends Swims {}
```

&emsp;&emsp;
What about the modifier applied to interfaces that extend the sealed interface? Well,
remember that interfaces are implicitly abstract and cannot be marked final. For
this reason, interfaces that extend a sealed interface can only be marked sealed or
non-sealed. They cannot be marked final.

## VI. Reviewing Sealed Class Rules
Any time you see a sealed class on the exam, pay close attention to the subclass declaration
and modifiers. <br />

**Sealed Class Rules**

- Sealed classes are declared with the sealed and permits modifiers.
- Sealed classes must be declared in the same package or named module as their direct
subclasses.
- Direct subclasses of sealed classes must be marked final, sealed, or non-sealed.
- The permits clause is optional if the sealed class and its direct subclasses are declared
within the same file or the subclasses are nested within the sealed class.
- Interfaces can be sealed to limit the classes that implement them or the interfaces that
extend them.

> **Why Have Sealed Classes?**: <br />
> 
> In Chapter 3, “Making Decisions,” you learned about switch expressions and pattern
matching. Imagine if we could treat a sealed class like an enum in a switch expression by
applying pattern matching. Given a sealed class Fish with two direct subclasses, it might
look something like this:
> ```java
>   public void printName(Fish fish) {
>       System.out.println(switch(fish) {
>           case Trout t -> t.getTroutName();
>           case Bass b -> b.getBassName();
>       });
>   }
> ```
> If Fish wasn’t sealed, the switch expression would require a default branch, or the
code would not compile. Since it’s sealed, the compiler knows all the options! The good
news is that this feature is on the way, but the bad news is that it’s still in Preview in Java 17
and not officially released. We just wanted to give you an idea of where some of these new
features were heading.
