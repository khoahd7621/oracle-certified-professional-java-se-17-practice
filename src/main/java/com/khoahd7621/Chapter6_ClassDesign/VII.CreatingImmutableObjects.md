# Creating Immutable Objects

As you might remember from Chapter 4, an immutable object is one that cannot change
state after it is created. The immutable objects pattern is an object-oriented design pattern in
which an object cannot be modified after it is created. <br />

&emsp;&emsp;
Immutable objects are helpful when writing secure code because you don’t have to worry
about the values changing. They also simplify code when dealing with concurrency since
immutable objects can be easily shared between multiple threads.

## I. Creating an Immutable Class
Although there are a variety of techniques for writing an immutable class, you should be
familiar with a common strategy for making a class immutable:

1. Mark the class as final or make all of the constructors private.
2. Mark all the instance variables private and final.
3. Don’t define any setter methods.
4. Don’t allow referenced mutable objects to be modified.
5. Use a constructor to set all properties of the object, making a copy if needed.

&emsp;&emsp;
The first rule prevents anyone from creating a mutable subclass. The second and third
rules ensure that callers don’t make changes to instance variables and are the hallmarks of
good encapsulation, a topic we discuss along with records in Chapter 7. <br />

&emsp;&emsp;
The fourth rule for creating immutable objects is subtle. Basically, it means you shouldn’t
expose an accessor (or getter) method for mutable instance fields. Can you see why the 
following creates a mutable object?

```java
import java.util.*;
public final class Animal { // Not an immutable object declaration
    private final ArrayList<String> favoriteFoods;
    public Animal() {
        this.favoriteFoods = new ArrayList<String>();
        this.favoriteFoods.add("Apples");
    }
    public List<String> getFavoriteFoods() {
        return favoriteFoods;
    } 
}
```

&emsp;&emsp;
We carefully followed the first three rules, but unfortunately, a malicious caller could still
modify our data:

```java
var zebra = new Animal();
System.out.println(zebra.getFavoriteFoods()); // [Apples]
        
zebra.getFavoriteFoods().clear();
zebra.getFavoriteFoods().add("Chocolate Chip Cookies");
System.out.println(zebra.getFavoriteFoods()); // [Chocolate Chip Cookies]
```

&emsp;&emsp;
Oh no! Zebras should not eat Chocolate Chip Cookies! It’s not an immutable object
if we can change its contents! If we don’t have a getter for the favoriteFoods object, how
do callers access it? Simple: by using delegate or wrapper methods to read the data.

```java
import java.util.*;
public final class Animal { // An immutable object declaration
    private final List<String> favoriteFoods;
    public Animal() {
        this.favoriteFoods = new ArrayList<String>();
        this.favoriteFoods.add("Apples");
    }
    public int getFavoriteFoodsCount() {
        return favoriteFoods.size();
    }
    public String getFavoriteFoodsItem(int index) {
        return favoriteFoods.get(index);
    }
}
```

&emsp;&emsp;
In this improved version, the data is still available. However, it is a true immutable object
because the mutable variable cannot be modified by the caller.

> **Copy on Read Accessor Methods**:
> Besides delegating access to any private mutable objects, another approach is to make a
copy of the mutable object any time it is requested.
> ```java
>   public ArrayList<String> getFavoriteFoods() {
>       return new ArrayList<String>(this.favoriteFoods);
>   }
> ```

Of course, changes in the copy won’t be reflected in the original, but at least the original is
protected from external changes. This can be an expensive operation if called frequently by
the caller.

## II. Performing a Defensive Copy
So, what’s this about the fifth and final rule for creating immutable objects? In designing our
class, let’s say we want a rule that the data for favoriteFoods is provided by the caller and that
it always contains at least one element. This rule is often called an invariant; it is true any
time we have an instance of the object.

```java
import java.util.*;
public final class Animal { // Not an immutable object declaration
    private final ArrayList<String> favoriteFoods;
    public Animal(ArrayList<String> favoriteFoods) {
        if (favoriteFoods == null || favoriteFoods.size() == 0)
            throw new RuntimeException("favoriteFoods is required");
        this.favoriteFoods = favoriteFoods;
    }
    public int getFavoriteFoodsCount() {
        return favoriteFoods.size();
    }
    public String getFavoriteFoodsItem(int index) {
        return favoriteFoods.get(index);
    }
}
```

&emsp;&emsp;
To ensure that favoriteFoods is provided, we validate it in the constructor and throw
an exception if it is not provided. So is this immutable? Not quite! A malicious caller might
be tricky and keep their own secret reference to our favoriteFoods object, which they can
modify directly.

```java
var favorites = new ArrayList<String>();
favorites.add("Apples");

var zebra = new Animal(favorites); // Caller still has access to favorites
System.out.println(zebra.getFavoriteFoodsItem(0)); // [Apples]
        
favorites.clear();
favorites.add("Chocolate Chip Cookies");
System.out.println(zebra.getFavoriteFoodsItem(0)); // [Chocolate Chip Cookies]
```

&emsp;&emsp;
Whoops! It seems like Animal is not immutable anymore, since its contents can
change after it is created. The solution is to make a copy of the list object containing the
same elements.

```java
public Animal(List<String> favoriteFoods) {
    if (favoriteFoods == null || favoriteFoods.size() == 0)
        throw new RuntimeException("favoriteFoods is required");
    this.favoriteFoods = new ArrayList<String>(favoriteFoods);
}
```

&emsp;&emsp;
The copy operation is called a defensive copy because the copy is being made in case
other code does something unexpected. It’s the same idea as defensive driving: prevent a
problem before it exists. With this approach, our Animal class is once again immutable.
