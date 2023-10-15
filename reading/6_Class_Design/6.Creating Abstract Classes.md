# Creating Abstract Classes

When designing a model, we sometimes want to create an entity that cannot be instantiated
directly. For example, imagine that we have a Canine class with subclasses Wolf, Fox, and
Coyote. We want other developers to be able to create instances of the subclasses, but 
perhaps we don’t want them to be able to create a Canine instance. In other words, we want to
force all objects of Canine to have a particular type at runtime.

## I. Introducing Abstract Classes

Enter abstract classes. An abstract class is a class declared with the abstract modifier that
cannot be instantiated directly and may contain abstract methods. Let’s take a look at an
example based on the Canine data model:

```java
public abstract class Canine {}
public class Wolf extends Canine {}
public class Fox extends Canine {}
public class Coyote extends Canine {}
```

&emsp;&emsp;
In this example, other developers can create instances of Wolf, Fox, or Coyote, but not
Canine. Sure, they can pass a variable reference as a Canine, but the underlying object must
be a subclass of Canine at runtime. <br />

&emsp;&emsp;
But wait, there’s more! An abstract class can contain abstract methods. An abstract
method is a method declared with the abstract modifier that does not define a body. Put
another way, an abstract method forces subclasses to override the method. <br />

&emsp;&emsp;
Why would we want this? Polymorphism, of course! By declaring a method abstract, we
can guarantee that some version will be available on an instance without having to specify
what that version is in the abstract parent class.

```java
public abstract class Canine {
    public abstract String getSound();
    public void bark() { System.out.println(getSound()); }
}
public class Wolf extends Canine {
    public String getSound() {
        return "Wooooooof!";
    }
}
public class Fox extends Canine {
    public String getSound() {
        return "Squeak!";
    } 
}
public class Coyote extends Canine {
    public String getSound() {
        return "Roar!";
    } 
}
```

&emsp;&emsp;
We can then create an instance of Fox and assign it to the parent type Canine. The overridden method will be used at runtime.

```java
public static void main(String[] p) {
    Canine w = new Fox();
    w.bark(); // Squeak!
}
```

&emsp;&emsp;
Easy so far. But there are some rules you need to be aware of:

- Only instance methods can be marked abstract within a class, not variables, constructors, or static methods.
- An abstract method can only be declared in an abstract class.
- A non-abstract class that extends an abstract class must implement all inherited
abstract methods.
- Overriding an abstract method follows the existing rules for overriding methods that
you learned about earlier in the chapter.

&emsp;&emsp;
Let’s see if you can spot why each of these class declarations does not compile:

```java
public class FennecFox extends Canine {
    public int getSound() {
        return 10;
    } 
}

public class ArcticFox extends Canine {}

public class Direwolf extends Canine {
    public abstract rest();
    public String getSound() {
        return "Roof!";
    } 
}

public class Jackal extends Canine {
    public abstract String name;
    public String getSound() {
        return "Laugh";
    } 
}
```

&emsp;&emsp;
First off, the FennecFox class does not compile because it is an invalid method override.
In particular, the return types are not covariant. The ArcticFox class does not compile
because it does not override the abstract getSound() method. The Direwolf class does
not compile because it is not abstract but declares an abstract method rest(). Finally, the
Jackal class does not compile because variables cannot be marked abstract. <br />

&emsp;&emsp;
An abstract class is most commonly used when you want another class to inherit properties
of a particular class, but you want the subclass to fill in some of the implementation details. <br />

&emsp;&emsp;
Earlier, we said that an abstract class is one that cannot be instantiated. This means that if
you attempt to instantiate it, the compiler will report an exception, as in this example:

```java
abstract class Alligator {
    public static void main(String... food) {
        var a = new Alligator(); // DOES NOT COMPILE
    }
}
```

An abstract class can be initialized, but only as part of the instantiation of a non-abstract subclass.

## II. Declaring Abstract Methods

An abstract method is always declared without a body. It also includes a semicolon (;) after
the method declaration. As you saw in the previous example, an abstract class may include
non-abstract methods, in this case with the bark() method. In fact, an abstract class can
include all of the same members as a non-abstract class, including variables, static and 
instance methods, constructors, etc. <br />

&emsp;&emsp;
It might surprise you to know that an abstract class is not required to include any
abstract methods. For example, the following code compiles even though it doesn’t define
any abstract methods:

```java
public abstract class Llama {
    public void chew() {}
}
```

&emsp;&emsp;
Even without abstract methods, the class cannot be directly instantiated. For the exam,
keep an eye out for abstract methods declared outside abstract classes, such as the following:

```java
public class Egret { // DOES NOT COMPILE
    public abstract void peck();
}
```

&emsp;&emsp;
The exam creators like to include invalid class declarations, mixing non-abstract classes
with abstract methods. <br />

&emsp;&emsp;
Like the final modifier, the abstract modifier can be placed before or after the access
modifier in class and method declarations, as shown in this Tiger class:

```java
abstract public class Tiger {
    abstract public int claw();
}
```

&emsp;&emsp;
The abstract modifier cannot be placed after the class keyword in a class declaration
or after the return type in a method declaration. The following Bear and howl() 
declarations do not compile for these reasons:

```java
public class abstract Bear { // DOES NOT COMPILE
    public int abstract howl(); // DOES NOT COMPILE
}
```

> **Note**: <br />
> It is not possible to define an abstract method that has a body or default
implementation. You can still define a default method with a body—you
just can’t mark it as abstract. As long as you do not mark the method as
final, the subclass has the option to override the inherited method.

## III. Creating a Concrete Class
An abstract class becomes usable when it is extended by a concrete subclass. A concrete class
is a non-abstract class. The first concrete subclass that extends an abstract class is required to
implement all inherited abstract methods. This includes implementing any inherited abstract
methods from inherited interfaces, as you see in the next chapter. <br />

&emsp;&emsp;
When you see a concrete class extending an abstract class on the exam, check to make
sure that it implements all of the required abstract methods. Can you see why the following
Walrus class does not compile?

```java
public abstract class Animal {
    public abstract String getName();
}

public class Walrus extends Animal {} // DOES NOT COMPILE
```

&emsp;&emsp;
In this example, we see that Animal is marked as abstract and Walrus is not, making
Walrus a concrete subclass of Animal. Since Walrus is the first concrete subclass, it must
implement all inherited abstract methods—getName() in this example. Because it doesn’t,
the compiler reports an error with the declaration of Walrus. <br />

&emsp;&emsp;
We highlight the first concrete subclass for a reason. An abstract class can extend a 
non-abstract class and vice versa. Anytime a concrete class is extending an abstract class, it must
implement all of the methods that are inherited as abstract. Let’s illustrate this with a set of
inherited classes:

```java
public abstract class Mammal {
    abstract void showHorn();
    abstract void eatLeaf();
}

public abstract class Rhino extends Mammal {
    void showHorn() {} // Inherited from Mammal
}

public class BlackRhino extends Rhino {
    void eatLeaf() {} // Inherited from Mammal
}
```

&emsp;&emsp;
In this example, the BlackRhino class is the first concrete subclass, while the Mammal
and Rhino classes are abstract. The BlackRhino class inherits the eatLeaf() method as
abstract and is therefore required to provide an implementation, which it does. <br />

&emsp;&emsp;
What about the showHorn() method? Since the parent class, Rhino, provides an 
implementation of showHorn(), the method is inherited in the BlackRhino as a non-abstract method.
For this reason, the BlackRhino class is permitted but not required to override the showHorn()
method. The three classes in this example are correctly defined and compile. <br />

&emsp;&emsp;
What if we changed the Rhino declaration to remove the abstract modifier?

```java
public class Rhino extends Mammal { // DOES NOT COMPILE
    void showHorn() {}
}
```

&emsp;&emsp;
By changing Rhino to a concrete class, it becomes the first non-abstract class to extend
the abstract Mammal class. Therefore, it must provide an implementation of both the
showHorn() and eatLeaf() methods. Since it only provides one of these methods, the
modified Rhino declaration does not compile. <br />

&emsp;&emsp;
Let’s try one more example. The following concrete class Lion inherits two abstract
methods, getName() and roar():

```java
public abstract class Animal {
    abstract String getName();
}

public abstract class BigCat extends Animal {
    protected abstract void roar();
}

public class Lion extends BigCat {
    public String getName() {
        return "Lion";
    }
    public void roar() {
        System.out.println("The Lion lets out a loud ROAR!");
    }
}
```

&emsp;&emsp;
In this sample code, BigCat extends Animal but is marked as abstract; therefore, it
is not required to provide an implementation for the getName() method. The class Lion
is not marked as abstract, and as the first concrete subclass, it must implement all of the
inherited abstract methods not defined in a parent class. All three of these classes compile
successfully.

## IV. Creating Constructors in Abstract Classes

Even though abstract classes cannot be instantiated, they are still initialized through 
constructors by their subclasses. For example, consider the following program:

```java
abstract class Mammal {
    abstract CharSequence chew();
    public Mammal() {
        System.out.println(chew()); // Does this line compile?
    }
}

public class Platypus extends Mammal {
    String chew() { return "yummy!"; }
    public static void main(String[] args) {
        new Platypus();
    }
}
```

&emsp;&emsp;
Using the constructor rules you learned about earlier in this chapter, the compiler inserts
a default no-argument constructor into the Platypus class, which first calls super() in the
Mammal class. The Mammal constructor is only called when the abstract class is being 
initialized through a subclass; therefore, there is an implementation of chew() at the time the 
constructor is called. This code compiles and prints yummy! at runtime. <br />

&emsp;&emsp;
For the exam, remember that abstract classes are initialized with constructors in the same
way as non-abstract classes. For example, if an abstract class does not provide a constructor,
the compiler will automatically insert a default no-argument constructor. <br />

&emsp;&emsp;
The primary difference between a constructor in an abstract class and a non-abstract class
is that a constructor in an abstract class can be called only when it is being initialized by a
non-abstract subclass. This makes sense, as abstract classes cannot be instantiated.

## V. Spotting Invalid Declarations

We conclude our discussion of abstract classes with a review of potential issues you’re more
likely to encounter on the exam than in real life. The exam writers are fond of questions
with methods marked as abstract for which an implementation is also defined. For example,
can you see why each of the following methods does not compile?

```java
public abstract class Turtle {
    public abstract long eat()      // DOES NOT COMPILE
    public abstract void swim() {}; // DOES NOT COMPILE
    public abstract int getAge() {  // DOES NOT COMPILE
        return 10;
    }
    public abstract void sleep; // DOES NOT COMPILE
    public void goInShell();    // DOES NOT COMPILE
}
```

&emsp;&emsp;
The first method, eat(), does not compile because it is marked abstract but does not
end with a semicolon (;). The next two methods, swim() and getAge(), do not compile
because they are marked abstract, but they provide an implementation block enclosed
in braces ({}). For the exam, remember that an abstract method declaration must end in
a semicolon without any braces. The next method, sleep, does not compile because it is
missing parentheses, (), for method arguments. The last method, goInShell(), does not
compile because it is not marked abstract and therefore must provide a body enclosed
in braces. <br />

&emsp;&emsp;
Make sure you understand why each of the previous methods does not compile and that
you can spot errors like these on the exam. If you come across a question on the exam in
which a class or method is marked abstract, make sure the class is properly implemented
before attempting to solve the problem.

### &emsp;&emsp; A. abstract and final Modifiers

What would happen if you marked a class or method both abstract and final? If you mark
something abstract, you intend for someone else to extend or implement it. But if you mark
something final, you are preventing anyone from extending or implementing it. 
These concepts are in direct conflict with each other. <br />

&emsp;&emsp;
Due to this incompatibility, Java does not permit a class or method to be marked both
abstract and final. For example, the following code snippet will not compile:

```java
public abstract final class Tortoise { // DOES NOT COMPILE
    public abstract final void walk(); // DOES NOT COMPILE
}
```

&emsp;&emsp;
In this example, neither the class nor the method declarations will compile because
they are marked both abstract and final. The exam doesn’t tend to use final 
modifiers on classes or methods often, so if you see them, make sure they aren’t used with the
abstract modifier.

### &emsp;&emsp; B. abstract and private Modifiers

A method cannot be marked as both abstract and private. This rule makes sense if you think
about it. How would you define a subclass that implements a required method if the method
is not inherited by the subclass? The answer is that you can’t, which is why the compiler will
complain if you try to do the following:

```java
public abstract class Whale {
    private abstract void sing(); // DOES NOT COMPILE
}

public class HumpbackWhale extends Whale {
    private void sing() {
    System.out.println("Humpback whale is singing");
    } 
}
```

&emsp;&emsp;
In this example, the abstract method sing() defined in the parent class Whale is not
visible to the subclass HumpbackWhale. Even though HumpbackWhale does provide an
implementation, it is not considered an override of the abstract method since the abstract
method is not inherited. The compiler recognizes this in the parent class and reports an error
as soon as private and abstract are applied to the same method.

> **Note**: <br />
> While it is not possible to declare a method abstract and private, it is
possible (albeit redundant) to declare a method final and private.

If we changed the access modifier from private to protected in the parent class
Whale, would the code compile?

```java
public abstract class Whale {
    protected abstract void sing();
}

public class HumpbackWhale extends Whale {
    private void sing() { // DOES NOT COMPILE
        System.out.println("Humpback whale is singing");
    }
}
```

In this modified example, the code will still not compile, but for a completely different
reason. If you remember the rules for overriding a method, the subclass cannot reduce the
visibility of the parent method, sing(). Because the method is declared protected in
the parent class, it must be marked as protected or public in the child class. Even with
abstract methods, the rules for overriding methods must be followed.

### &emsp;&emsp; C. abstract and static Modifiers
As we discussed earlier in the chapter, a static method can only be hidden, not overridden. It
is defined as belonging to the class, not an instance of the class. If a static method cannot be
overridden, then it follows that it also cannot be marked abstract since it can never be 
implemented. For example, the following class does not compile:

```java
abstract class Hippopotamus {
    abstract static void swim(); // DOES NOT COMPILE
}
```

&emsp;&emsp;
For the exam, make sure you know which modifiers can and cannot be used with one
another, especially for abstract classes and interfaces.
