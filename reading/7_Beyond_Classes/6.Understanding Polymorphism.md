# Understanding Polymorphism

We conclude this chapter with a discussion of polymorphism, the property of an object
to take on many different forms. To put this more precisely, a Java object may be
accessed using:

- A reference with the same type as the object
- A reference that is a superclass of the object
- A reference that defines an interface the object implements or inherits

&emsp;&emsp;
Furthermore, a cast is not required if the object is being reassigned to a supertype or 
interface of the object. Phew, that’s a lot! Don’t worry; it’ll make sense shortly.
Let’s illustrate this polymorphism property with the following example:

```java
public class Primate {
    public boolean hasHair() {
        return true;
    }
}

public interface HasTail {
    public abstract boolean isTailStriped();
}

public class Lemur extends Primate implements HasTail {
    public boolean isTailStriped() {
        return false;
    }
    public int age = 10;
    public static void main(String[] args) {
        Lemur lemur = new Lemur();
        System.out.println(lemur.age);
        HasTail hasTail = lemur;
        System.out.println(hasTail.isTailStriped());
        Primate primate = lemur;
        System.out.println(primate.hasHair());
    } 
}
```

&emsp;&emsp;
This code compiles and prints the following output:

```java
10
false
true
```

&emsp;&emsp;
The most important thing to note about this example is that only one object, Lemur, is
created. Polymorphism enables an instance of Lemur to be reassigned or passed to a method
using one of its supertypes, such as Primate or HasTail. <br />

&emsp;&emsp;
Once the object has been assigned to a new reference type, only the methods and 
variables available to that reference type are callable on the object without an explicit cast. For
example, the following snippets of code will not compile:

```java
HasTail hasTail = new Lemur();
System.out.println(hasTail.age); // DOES NOT COMPILE
        
Primate primate = new Lemur();
System.out.println(primate.isTailStriped()); // DOES NOT COMPILE
```

&emsp;&emsp;
In this example, the reference hasTail has direct access only to methods defined with
the HasTail interface; therefore, it doesn’t know that the variable age is part of the object.
Likewise, the reference primate has access only to methods defined in the Primate class,
and it doesn’t have direct access to the isTailStriped() method.

## I. Object vs. Reference

In Java, all objects are accessed by reference, so as a developer you never have direct
access to the object itself. Conceptually, though, you should consider the object as the entity
that exists in memory, allocated by the Java Runtime Environment. Regardless of the type
of the reference you have for the object in memory, the object itself doesn’t change. For
example, since all objects inherit java.lang.Object, they can all be reassigned to
java.lang.Object, as shown in the following example:

```java
Lemur lemur = new Lemur();
Object lemurAsObject = lemur;
```

&emsp;&emsp;
Even though the Lemur object has been assigned to a reference with a different type, the
object itself has not changed and still exists as a Lemur object in memory. What has changed,
then, is our ability to access methods within the Lemur class with the lemurAsObject 
reference. Without an explicit cast back to Lemur, as you see in the next section, we no longer
have access to the Lemur properties of the object. <br />

&emsp;&emsp;
We can summarize this principle with the following two rules:

1. The type of the object determines which properties exist within the object in memory.
2. The type of the reference to the object determines which methods and variables are
   accessible to the Java program.

&emsp;&emsp;
It therefore follows that successfully changing a reference of an object to a new reference
type may give you access to new properties of the object; but remember, those properties
existed before the reference change occurred.

> **Using Interface References**: <br />
> When working with a group of objects that implement a common interface, it is considered
a good coding practice to use an interface as the reference type. This is especially common
with collections that you learn about in Chapter 9, “Collections and Generics.” Consider the
following method:
> ```java
>   public void sortAndPrintZooAnimals(List<String> animals) {
>       Collections.sort(animals);
>       for(String a : animals) System.out.println(a);
>   }
> ```
> This method sorts and prints animals in alphabetical order. At no point is this class 
interested in what the actual underlying object for animals is. It might be an ArrayList or
another type. The point is, our code works on any of these types because we used the 
interface reference type rather than a class type.

## II. Casting Objects
In the previous example, we created a single instance of a Lemur object and accessed it via
superclass and interface references. Once we changed the reference type, though, we lost
access to more specific members defined in the subclass that still exist within the object. We
can reclaim those references by casting the object back to the specific subclass it came from:

```java
Lemur lemur = new Lemur();

Primate primate = lemur;        // Implicit Cast to supertype
        
Lemur lemur2 = (Lemur) primate; // Explicit Cast to subtype
        
Lemur lemur3 = primate;         // DOES NOT COMPILE (missing cast)
```

&emsp;&emsp;
In this example, we first create a Lemur object and implicitly cast it to a Primate reference. Since Lemur is a subtype of Primate, this can be done without a cast operator. We
then cast it back to a Lemur object using an explicit cast, gaining access to all of the methods
and fields in the Lemur class. The last line does not compile because an explicit cast is
required. Even though the object is stored in memory as a Lemur object, we need an explicit
cast to assign it to Lemur. <br />

&emsp;&emsp;
Casting objects is similar to casting primitives, as you saw in Chapter 2, “Operators.”
When casting objects, you do not need a cast operator if casting to an inherited supertype.
This is referred to as an implicit cast and applies to classes or interfaces the object inherits.
Alternatively, if you want to access a subtype of the current reference, you need to perform
an explicit cast with a compatible type. If the underlying object is not compatible with the
type, then a ClassCastException will be thrown at runtime.

&emsp;&emsp;
When reviewing a question on the exam that involves casting and polymorphism, be sure
to remember what the instance of the object actually is. Then, focus on whether the compiler
will allow the object to be referenced with or without explicit casts. <br />

&emsp;&emsp;
We summarize these concepts into a set of rules for you to memorize for the exam:

1. Casting a reference from a subtype to a supertype doesn’t require an explicit cast.
2. Casting a reference from a supertype to a subtype requires an explicit cast.
3. At runtime, an invalid cast of a reference to an incompatible type results in a
   ClassCastException being thrown.
4. The compiler disallows casts to unrelated types.

### &emsp;&emsp; 1. Disallowed Casts
The first three rules are just a review of what we’ve said so far. The last rule is a bit more
complicated. The exam may try to trick you with a cast that the compiler knows is not 
permitted (aka impossible). In the previous example, we were able to cast a Primate reference to
a Lemur reference because Lemur is a subclass of Primate and therefore related. Consider this
example instead:

```java
public class Bird {}
public class Fish {
   public static void main(String[] args) {
      Fish fish = new Fish();
      Bird bird = (Bird) fish; // DOES NOT COMPILE
   }
}
```

&emsp;&emsp;
In this example, the classes Fish and Bird are not related through any class hierarchy
that the compiler is aware of; therefore, the code will not compile. While they both extend
Object implicitly, they are considered unrelated types since one cannot be a subtype of
the other.

### &emsp;&emsp; 2. Casting Interfaces

While the compiler can enforce rules about casting to unrelated types for classes, it cannot
always do the same for interfaces. Remember, instances support multiple inheritance, which
limits what the compiler can reason about them. While a given class may not implement an
interface, it’s possible that some subclass may implement the interface. When holding a 
reference to a particular class, the compiler doesn’t know which specific subtype it is holding.
Let’s try an example. Do you think the following program compiles?

```java
1:  interface Canine {}
2:  interface Dog {}
3:  class Wolf implements Canine {}
4:
5:  public class BadCasts {
6:      public static void main(String[] args) {
7:          Wolf wolfy = new Wolf();
8:          Dog badWolf = (Dog) wolfy;
9:      } 
10: }
```

&emsp;&emsp;
In this program, a Wolf object is created and then assigned to a Wolf reference type on
line 7. With interfaces, the compiler has limited ability to enforce many rules because even
though a reference type may not implement an interface, one of its subclasses could. 
Therefore, it allows the invalid cast to the Dog reference type on line 8, even though Dog and
Wolf are not related. Fear not, even though the code compiles, it still throws a
ClassCastException at runtime. <br />

&emsp;&emsp;
This limitation aside, the compiler can enforce one rule around interface casting. The
compiler does not allow a cast from an interface reference to an object reference if the object
type cannot possibly implement the interface, such as if the class is marked final. For
example, if the Wolf interface is marked final on line 3, then line 8 no longer compiles.
The compiler recognizes that there are no possible subclasses of Wolf capable of implementing the Dog interface.

## III. The instanceof Operator

In Chapter 3, we presented the instanceof operator with pattern matching. The instanceof
operator can be used to check whether an object belongs to a particular class or interface
and to prevent a ClassCastException at runtime. Consider the following example:

```java
1:  class Rodent {}
2:
3:  public class Capybara extends Rodent {
4:      public static void main(String[] args) {
5:          Rodent rodent = new Rodent();
6:          var capybara = (Capybara) rodent; // ClassCastException
7:      }
8:  }
```

&emsp;&emsp;
This program throws an exception on line 6. We can replace line 6 with the following.

```java
6:      if (rodent instanceof Capybara c) {
7:          // Do stuff
8:      }
```

&emsp;&emsp;
Now the code snippet doesn’t throw an exception at runtime and performs the cast only
if the instanceof operator is successful. <br />

&emsp;&emsp;
Just as the compiler does not allow casting an object to unrelated types, it also does not
allow instanceof to be used with unrelated types. We can demonstrate this with our unrelated
Bird and Fish classes:

```java
public class Bird {}

public class Fish {
   public static void main(String[] args) {
      Fish fish = new Fish();
      if (fish instanceof Bird b) { // DOES NOT COMPILE
         // Do stuff
      }
   }
}
```

## IV. Polymorphism and Method Overriding
In Java, polymorphism states that when you override a method, you replace all calls to it,
even those defined in the parent class. As an example, what do you think the following code
snippet outputs?

```java
class Penguin {
   public int getHeight() { return 3; }
   public void printInfo() {
      System.out.print(this.getHeight());
   }
}

public class EmperorPenguin extends Penguin {
   public int getHeight() { return 8; }
   public static void main(String []fish) {
      new EmperorPenguin().printInfo();
   }
}
```

&emsp;&emsp;
If you said 8, then you are well on your way to understanding polymorphism. In
this example, the object being operated on in memory is an EmperorPenguin. The
getHeight() method is overridden in the subclass, meaning all calls to it are replaced at
runtime. Despite printInfo() being defined in the Penguin class, calling getHeight()
on the object calls the method associated with the precise object in memory, not the current
reference type where it is called. Even using the this reference, which is optional in this
example, does not call the parent version because the method has been replaced. <br />

&emsp;&emsp;
*Polymorphism’s ability to replace methods at runtime via overriding is one of the most
important properties of Java.* It allows you to create complex inheritance models with 
subclasses that have their own custom implementation of overridden methods. It also means
the parent class does not need to be updated to use the custom or overridden method. If the
method is properly overridden, then the overridden version will be used in all places that it
is called. <br />

&emsp;&emsp;
Remember, you can choose to limit polymorphic behavior by marking methods final,
which prevents them from being overridden by a subclass.

> **Calling the Parent Version of an Overridden Method** <br />
> Just because a method is overridden doesn’t mean the parent method is completely 
inaccessible. We can use the super reference that you learned about in Chapter 6 to access it.
How can you modify our previous example to print 3 instead of 8? You could try
calling super.getHeight() in the parent Penguin class:
> ```java
>   class Penguin {
>       public int getHeight() { return 3; }
>       public void printInfo() {
>           System.out.print(super.getHeight()); // DOES NOT COMPILE
>       }
>   }
> ```
> Unfortunately, this does not compile, as super refers to the superclass of Penguin; in this
case, Object. The solution is to override printInfo() in the child EmperorPenguin
class and use super there.
> ```java
>   public class EmperorPenguin extends Penguin {
>       public int getHeight() { return 8; }
>       public void printInfo() {
>           System.out.print(super.getHeight());
>       }
>       public static void main(String []fish) {
>           new EmperorPenguin().printInfo(); // 3
>       }
>   }
> ```

## V. Overriding vs. Hiding Members
While method overriding replaces the method everywhere it is called, static method and
variable hiding do not. Strictly speaking, hiding members is not a form of polymorphism
since the methods and variables maintain their individual properties. Unlike method 
overriding, hiding members is very sensitive to the reference type and location where the member is
being used. Let’s take a look at an example:

```java
class Penguin {
   public static int getHeight() { return 3; }
   public void printInfo() {
      System.out.println(this.getHeight());
   }
}

public class CrestedPenguin extends Penguin {
   public static int getHeight() { return 8; }
   public static void main(String... fish) {
      new CrestedPenguin().printInfo();
   }
}
```

&emsp;&emsp;
The CrestedPenguin example is nearly identical to our previous
EmperorPenguin example, although as you probably already guessed, it prints 3 instead of 8. The getHeight() method is static and is therefore hidden, not overridden. The result
is that calling getHeight() in CrestedPenguin returns a different value than calling it in
Penguin, even if the underlying object is the same. Contrast this with overriding a method,
where it returns the same value for an object regardless of which class it is called in. <br />

&emsp;&emsp;
What about the fact that we used this to access a static method in
this.getHeight()? As discussed in Chapter 5, while you are permitted to use an instance
reference to access a static variable or method, doing so is often discouraged. The 
compiler will warn you when you access static members in a non-static way. In this case,
the this reference had no impact on the program output. <br />

&emsp;&emsp;
Besides the location, the reference type can also determine the value you get when you are
working with hidden members. Ready? Let’s try a more complex example:

```java
class Marsupial {
   protected int age = 2;
   public static boolean isBiped() {
      return false;
   } 
}
public class Kangaroo extends Marsupial {
   protected int age = 6;
   public static boolean isBiped() {
      return true;
   }
   public static void main(String[] args) {
      Kangaroo joey = new Kangaroo();
      Marsupial moey = joey;
      System.out.println(joey.isBiped());
      System.out.println(moey.isBiped());
      System.out.println(joey.age);
      System.out.println(moey.age);
   } 
}
```

&emsp;&emsp;
The program prints the following:

```java
true
false
6
2
```

&emsp;&emsp;
In this example, only one object (of type Kangaroo) is created and stored in memory!
Since static methods can only be hidden, not overridden, Java uses the reference type to
determine which version of isBiped() should be called, resulting in joey.isBiped()
printing true and moey.isBiped() printing false. <br />

&emsp;&emsp;
Likewise, the age variable is hidden, not overridden, so the reference type is used to
determine which value to output. This results in joey.age returning 6 and moey.age
returning 2. <br />

&emsp;&emsp;
For the exam, make sure you understand these examples, as they show how hidden and
overridden methods are fundamentally different. In practice, overriding methods is the 
cornerstone of polymorphism and an extremely powerful feature.

> **Don’t Hide Members in Practice** <br />
> Although Java allows you to hide variables and static methods, it is considered an
extremely poor coding practice. As you saw in the previous example, the value of the 
variable or method can change depending on what reference is used, making your code very
confusing, difficult to follow, and challenging for others to maintain. This is further 
compounded when you start modifying the value of the variable in both the parent and child
methods, since it may not be clear which variable you’re updating. <br />
> When you’re defining a new variable or static method in a child class, it is 
considered good coding practice to select a name that is not already used by an inherited
member. Redeclaring private methods and variables is considered less problematic,
though, because the child class does not have access to the variable in the parent class to
begin with.
