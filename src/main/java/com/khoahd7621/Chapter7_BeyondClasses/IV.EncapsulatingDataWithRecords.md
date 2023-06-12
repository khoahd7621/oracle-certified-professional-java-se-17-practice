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
