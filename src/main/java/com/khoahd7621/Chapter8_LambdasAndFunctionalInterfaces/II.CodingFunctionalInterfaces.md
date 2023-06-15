# Coding Functional Interfaces

Earlier in the chapter, we declared the CheckTrait interface, which has exactly one method
for implementers to write. Lambdas have a special relationship with such interfaces. In fact,
these interfaces have a name. A functional interface is an interface that contains a single
abstract method. Your friend Sam can help you remember this because it is officially known
as a single abstract method (SAM) rule.

# I. Defining a Functional Interface
Let’s take a look at an example of a functional interface and a class that implements it:

```java
@FunctionalInterface
public interface Sprint {
    public void sprint(int speed);
}

public class Tiger implements Sprint {
    public void sprint(int speed) {
        System.out.println("Animal is sprinting fast! " + speed);
    }
}
```

&emsp;&emsp;
In this example, the Sprint interface is a functional interface because it contains exactly
one abstract method, and the Tiger class is a valid class that implements the interface.

> **The @FunctionalInterface Annotation** <br />
> The @FunctionalInterface annotation tells the compiler that you intend for the code to
be a functional interface. If the interface does not follow the rules for a functional interface,
the compiler will give you an error.
> ```java
>   @FunctionalInterface
>   public interface Dance { // DOES NOT COMPILE
>       void move();
>       void rest();
>   }
> ```
> Java includes @FunctionalInterface on some, but not all, functional interfaces. This
annotation means the authors of the interface promise it will be safe to use in a lambda
in the future. However, just because you don’t see the annotation doesn’t mean it’s not a
functional interface. Remember that having exactly one abstract method is what makes it a
functional interface, not the annotation.

&emsp;&emsp;
Consider the following four interfaces. Given our previous Sprint functional interface,
which of the following are functional interfaces?

```java
public interface Dash extends Sprint {}

public interface Skip extends Sprint {
    void skip();
}

public interface Sleep {
    private void snore() {}
    default int getZzz() { return 1; }
}

public interface Climb {
    void reach();
    default void fall() {}
    static int getBackUp() { return 100; }
    private static boolean checkHeight() { return true; }
}
```

&emsp;&emsp;
All four of these are valid interfaces, but not all of them are functional interfaces. The
Dash interface is a functional interface because it extends the Sprint interface and inherits
the single abstract method sprint(). The Skip interface is not a valid functional 
interface because it has two abstract methods: the inherited sprint() method and the declared
skip() method. <br />

&emsp;&emsp;
The Sleep interface is also not a valid functional interface. Neither snore() nor getZzz()
meets the criteria of a single abstract method. Even though default methods function like
abstract methods, in that they can be overridden in a class implementing the interface, they
are insufficient for satisfying the single abstract method requirement.

&emsp;&emsp;
Finally, the Climb interface is a functional interface. Despite defining a slew of methods, it
contains only one abstract method: reach().

# II. Adding Object Methods
All classes inherit certain methods from Object. For the exam, you should know the following Object method signatures:

- public String toString()
- public boolean equals(Object)
- public int hashCode()

&emsp;&emsp;
We bring this up now because there is one exception to the single abstract method rule
that you should be familiar with. If a functional interface includes an abstract method with
the same signature as a public method found in Object, *those methods do not count
toward the single abstract method test.* The motivation behind this rule is that any class that
implements the interface will inherit from Object, as all classes do, and therefore always
implement these methods.

> **Note**: <br />
> Since Java assumes all classes extend from Object, you also cannot
declare an interface method that is incompatible with Object. For
example, declaring an abstract method int toString() in an 
interface would not compile since Object’s version of the method returns a String.

&emsp;&emsp;
Let’s take a look at an example. Is the Soar class a functional interface?

```java
public interface Soar {
    abstract String toString();
}
```

&emsp;&emsp;
It is not. Since toString() is a public method implemented in Object, it does not
count toward the single abstract method test. On the other hand, the following 
implementation of Dive is a functional interface:

```java
public interface Dive {
    String toString();
    public boolean equals(Object o);
    public abstract int hashCode();
    public void dive();
}
```

&emsp;&emsp;
The dive() method is the single abstract method, while the others are not counted since
they are public methods defined in the Object class. <br />

&emsp;&emsp;
Be wary of examples that resemble methods in the Object class but are not 
actually defined in the Object class. Do you see why the following is not a valid functional
interface?

```java
public interface Hibernate {
    String toString();
    public boolean equals(Hibernate o);
    public abstract int hashCode();
    public void rest();
}
```

Despite looking a lot like our Dive interface, the Hibernate interface uses
equals(Hibernate) instead of equals(Object). Because this does not match the
method signature of the equals(Object) method defined in the Object class, this 
interface is counted as containing two abstract methods: equals(Hibernate) and rest().
