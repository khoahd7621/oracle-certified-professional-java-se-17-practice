# Sorting Data

We discussed “order” for the TreeSet and TreeMap classes. For numbers, order is 
obvious—it is numerical order. For String objects, order is defined according to the Unicode
character mapping.

> **Note**: <br />
> When working with a String, remember that numbers sort before
letters, and uppercase letters sort before lowercase letters.

&emsp;&emsp;
We use Collections.sort() in many of these examples. It returns void because the
method parameter is what gets sorted. <br />

&emsp;&emsp;
You can also sort objects that you create yourself. Java provides an interface called
Comparable. If your class implements Comparable, it can be used in data structures that
require comparison. There is also a class called Comparator, which is used to specify that
you want to use a different order than the object itself provides. <br />

&emsp;&emsp;
You can also sort objects that you create yourself. Java provides an interface called
Comparable. If your class implements Comparable, it can be used in data structures that
require comparison. There is also a class called Comparator, which is used to specify that
you want to use a different order than the object itself provides. <br />

&emsp;&emsp;
Comparable and Comparator are similar enough to be tricky. The exam likes to see
if it can trick you into mixing up the two. Don’t be confused! In this section, we discuss
Comparable first. Then, as we go through Comparator, we point out all of the differences.

## I. Creating a Comparable Class
The Comparable interface has only one method. In fact, this is the entire interface:

```java
public interface Comparable<T> {
    int compareTo(T o);
}
```

&emsp;&emsp;
The generic T lets you implement this method and specify the type of your object. This
lets you avoid a cast when implementing compareTo(). Any object can be Comparable.
For example, we have a bunch of ducks and want to sort them by name. First, we update
the class declaration to inherit Comparable<Duck>, and then we implement the
compareTo() method:

```java
import java.util.*;
public class Duck implements Comparable<Duck> {
    private String name;
    public Duck(String name) {
        this.name = name;
    }
    public String toString() { // use readable output
        return name;
    }
    public int compareTo(Duck d) {
        return name.compareTo(d.name); // sorts ascendingly by name
    }
    public static void main(String[] args) {
        var ducks = new ArrayList<Duck>();
        ducks.add(new Duck("Quack"));
        ducks.add(new Duck("Puddles"));
        Collections.sort(ducks);    // sort by name
        System.out.println(ducks);  // [Puddles, Quack]
    }
}
```

&emsp;&emsp;
Without implementing that interface, all we have is a method named compareTo(), but
it wouldn’t be a Comparable object. We could also implement Comparable<Object> or
some other class for T, but this wouldn’t be as useful for sorting a group of Duck objects.

> **Note**: <br />
> The Duck class overrides the toString() method from Object, which
we described in Chapter 8. This override provides useful output when
printing out ducks. Without this override, the output would be something
like `[Duck@70dea4e, Duck@5c647e05]` — hardly useful in seeing which
duck’s name comes first.

&emsp;&emsp;
Finally, the Duck class implements compareTo(). Since Duck is comparing objects of
type String and the String class already has a compareTo() method, it can just delegate. <br />

&emsp;&emsp;
We still need to know what the compareTo() method returns so that we can write our own.
There are three rules to know:

- The number 0 is returned when the current object is equivalent to the argument to
  compareTo().
- A negative number (less than 0) is returned when the current object is smaller than the
  argument to compareTo().
- A positive number (greater than 0) is returned when the current object is larger than the
  argument to compareTo().

&emsp;&emsp;
Let’s look at an implementation of compareTo() that compares numbers instead of
String objects:

```java
1:  public class Animal implements Comparable<Animal> {
2:      private int id;
3:      public int compareTo(Animal a) {
4:          return id - a.id; // sorts ascending by id
5:      }
6:      public static void main(String[] args) {
7:          var a1 = new Animal();
8:          var a2 = new Animal();
9:          a1.id = 5;
10:         a2.id = 7;
11:         System.out.println(a1.compareTo(a2)); // -2
12:         System.out.println(a1.compareTo(a1)); // 0
13:         System.out.println(a2.compareTo(a1)); // 2
14:     } 
15: }
```

&emsp;&emsp;
Lines 7 and 8 create two Animal objects. Lines 9 and 10 set their id values. This is not a
good way to set instance variables. It would be better to use a constructor or setter method.
Since the exam shows nontraditional code to make sure that you understand the rules, we
throw in some nontraditional code as well. <br />

&emsp;&emsp;
Lines 3–5 show one way to compare two int values. We could have used
Integer.compare(id, a.id) instead. Be sure you can recognize both approaches.

> **Note**: <br />
> Remember that id - a.id sorts in ascending order, and a.id - id
sorts in descending order.

&emsp;&emsp;
Lines 11–13 confirm that we’ve implemented compareTo() correctly. Line 11 compares
a smaller id to a larger one, and therefore it prints a negative number. Line 12 compares 
animals with the same id, and therefore it prints 0. Line 13 compares a larger id to a smaller
one, and therefore it returns a positive number.

### &emsp;&emsp; A. Casting the *compareTo()* Argument

When dealing with legacy code or code that does not use generics, the compareTo() method
requires a cast since it is passed an Object.

```java
public class LegacyDuck implements Comparable {
    private String name;
    public int compareTo(Object obj) {
        LegacyDuck d = (LegacyDuck) obj; // cast because no generics
         return name.compareTo(d.name);
    }
}
```

&emsp;&emsp;
Since we don’t specify a generic type for Comparable, Java assumes that we want an
Object, which means that we have to cast to LegacyDuck before accessing instance variables on it.

### &emsp;&emsp; B. Checking for *null*
When working with Comparable and Comparator in this chapter, we tend to assume the
data has values, but this is not always the case. When writing your own compare methods,
you should check the data before comparing it if it is not validated ahead of time.

```java
public class MissingDuck implements Comparable<MissingDuck> {
    private String name;
    public int compareTo(MissingDuck quack) {
        if (quack == null)
            throw new IllegalArgumentException("Poorly formed duck!");
        if (this.name == null && quack.name == null)
            return 0;
        else if (this.name == null) return -1;
        else if (quack.name == null) return 1;
        else return name.compareTo(quack.name);
    }
}
```

&emsp;&emsp;
This method throws an exception if it is passed a null MissingDuck object. What
about the ordering? If the name of a duck is null, it’s sorted first.

### &emsp;&emsp; C. Keeping *compareTo()* and *equals()* Consistent
If you write a class that implements Comparable, you introduce new business logic for determining 
equality. The compareTo() method returns 0 if two objects are equal, while your equals()
method returns true if two objects are equal. A natural ordering that uses compareTo() is said
to be consistent with equals if, and only if, x.equals(y) is true whenever x.compareTo(y) equals 0. <br />

&emsp;&emsp;
Similarly, x.equals(y) must be false whenever x.compareTo(y) is not 0. You are strongly
encouraged to make your Comparable classes consistent with equals because not all collection
classes behave predictably if the compareTo() and equals() methods are not consistent. <br />

&emsp;&emsp;
For example, the following Product class defines a compareTo() method that is not
consistent with equals:

```java
public class Product implements Comparable<Product> {
    private int id;
    private String name;
    public int hashCode() { return id; }
    public boolean equals(Object obj) {
        if(!(obj instanceof Product)) return false;
        var other = (Product) obj;
        return this.id == other.id;
    }
    public int compareTo(Product obj) {
        return this.name.compareTo(obj.name);
    }
}
```

&emsp;&emsp;
You might be sorting Product objects by name, but names are not unique. The
compareTo() method does not have to be consistent with equals. One way to fix that is to
use a Comparator to define the sort elsewhere. <br />

&emsp;&emsp;
Now that you know how to implement Comparable objects, you get to look at a
Comparator and focus on the differences.

## II. Comparing Data with a Comparator
Sometimes you want to sort an object that did not implement Comparable, or you want to
sort objects in different ways at different times. Suppose that we add weight to our Duck
class. We now have the following:

```java
1:  import java.util.ArrayList;
2:  import java.util.Collections;
3:  import java.util.Comparator;
4:
5:  public class Duck implements Comparable<Duck> {
6:      private String name;
7:      private int weight;
8:
9:      // Assume getters/setters/constructors provided
10:
11:     public String toString() { return name; }
12:
13:     public int compareTo(Duck d) {
14:         return name.compareTo(d.name);
15:     }
16:
17:     public static void main(String[] args) {
18:         Comparator<Duck> byWeight = new Comparator<Duck>() {
19:             public int compare(Duck d1, Duck d2) {
20:                 return d1.getWeight() - d2.getWeight();
21:             }
22:         };
23:         var ducks = new ArrayList<Duck>();
24:         ducks.add(new Duck("Quack", 7));
25:         ducks.add(new Duck("Puddles", 10));
26:         Collections.sort(ducks);
27:         System.out.println(ducks); // [Puddles, Quack]
28:         Collections.sort(ducks, byWeight);
29:         System.out.println(ducks); // [Quack, Puddles]
30:     }
31: }
```

&emsp;&emsp;
First, notice that this program imports java.util.Comparator on line 3. We don’t
always show imports since you can assume they are present if not shown. Here, we do show
the import to call attention to the fact that Comparable and Comparator are in different
packages: java.lang and java.util, respectively. That means Comparable can be used
without an import statement, while Comparator cannot. <br />

&emsp;&emsp;
The Duck class itself can define only one compareTo() method. In this case, name was
chosen. If we want to sort by something else, we have to define that sort order outside the
compareTo() method using a separate class or lambda expression.

&emsp;&emsp;
Lines 18–22 of the main() method show how to define a Comparator using an inner class.
On lines 26–29, we sort without the Comparator and then with the Comparator to see the
difference in output. <br />

&emsp;&emsp;
Comparator is a functional interface since there is only one abstract method to implement.
This means that we can rewrite the Comparator on lines 18–22 using a lambda expression, as
shown here: <br />

```java
Comparator<Duck> byWeight = (d1, d2) -> d1.getWeight() - d2.getWeight();
```

&emsp;&emsp;
Alternatively, we can use a method reference and a helper method to specify that we want
to sort by weight.

```java
Comparator<Duck> byWeight = Comparator.comparing(Duck::getWeight);
```

&emsp;&emsp;
In this example, Comparator.comparing() is a static interface method that creates a
Comparator given a lambda expression or method reference. Convenient, isn’t it?

> **Is *Comparable* a Functional Interface?** <br />
> We said that Comparator is a functional interface because it has a single abstract method.
Comparable is also a functional interface since it also has a single abstract method. However, 
using a lambda for Comparable would be silly. The point of Comparable is to implement it inside 
the object being compared.

## III. Comparing Comparable and Comparator
There are several differences between Comparable and Comparator. We’ve listed them for
you in Table 9.10.

> **Table 9.10** Comparison of Comparable and Comparator
> 
> |Difference|Comparable|Comparator|
> |:---|:---|:---|
> |Package name |java.lang |java.util|
> |Interface must be implemented by class comparing? |Yes |No|
> |Method name in interface |compareTo() |compare()|
> |Number of parameters |1 |2|
> |Common to declare using a lambda |No |Yes|

&emsp;&emsp;
Memorize this table—really. The exam will try to trick you by mixing up the two and 
seeing if you can catch it. Do you see why this doesn’t compile?

```java
var byWeight = new Comparator<Duck>() { // DOES NOT COMPILE
    public int compareTo(Duck d1, Duck d2) {
        return d1.getWeight()-d2.getWeight();
    }
};
```

&emsp;&emsp;
The method name is wrong. A Comparator must implement a method named
compare(). Pay special attention to method names and the number of parameters when you
see Comparator and Comparable in questions.

## IV. Comparing Multiple Fields
When writing a Comparator that compares multiple instance variables, the code gets a little
messy. Suppose that we have a Squirrel class, as shown here:

```java
public class Squirrel {
    private int weight;
    private String species;
    // Assume getters/setters/constructors provided
}
```

&emsp;&emsp;
We want to write a Comparator to sort by species name. If two squirrels are from the
same species, we want to sort the one that weighs the least first. We could do this with code
that looks like this:

```java
public class MultiFieldComparator implements Comparator<Squirrel> {
    public int compare(Squirrel s1, Squirrel s2) {
        int result = s1.getSpecies().compareTo(s2.getSpecies());
        if (result != 0) return result;
        return s1.getWeight() - s2.getWeight();
    }
}
```

&emsp;&emsp;
This works assuming no species’ names are null. It checks one field. If they don’t match,
we are finished sorting. If they do match, it looks at the next field. This isn’t easy to read,
though. It is also easy to get wrong. Changing != to == breaks the sort completely. <br />

&emsp;&emsp;
Alternatively, we can use method references and build the Comparator. This code 
represents logic for the same comparison:

```java
Comparator<Squirrel> c = Comparator.comparing(Squirrel::getSpecies)
    .thenComparingInt(Squirrel::getWeight);
```

&emsp;&emsp;
This time, we chain the methods. First, we create a Comparator on species ascending.
Then, if there is a tie, we sort by weight. We can also sort in descending order. Some methods
on Comparator, like thenComparingInt(), are default methods. <br />

&emsp;&emsp;
Suppose we want to sort in descending order by species. 

```java
var c = Comparator.comparing(Squirrel::getSpecies).reversed();
```

&emsp;&emsp;
Table 9.11 shows the helper methods you should know for building a Comparator.
We’ve omitted the parameter types to keep you focused on the methods. They use many of
the functional interfaces you learned about in the previous chapter.

> **Table 9.11** Helper static methods for building a Comparator
>
> |Method|Description|
> |:---|:---|
> |comparing(function) |Compare by results of function that returns any Object (or primitive autoboxed into Object).|
> |comparingDouble(function) |Compare by results of function that returns double.|
> |comparingInt(function) |Compare by results of function that returns int.|
> |comparingLong(function) |Compare by results of function that returns long.|
> |naturalOrder() |Sort using order specified by the Comparable implementation on object itself.|
> |reverseOrder() |Sort using reverse of order specified by Comparable implementation on object itself.|

&emsp;&emsp;
Table 9.12 shows the methods that you can chain to a Comparator to further specify
its behavior.

> **Table 9.12** Helper default methods for building a Comparator
> 
> |Method|Description|
> |:---|:---|
> |reversed() |Reverse order of chained Comparator.|
> |thenComparing(function) |If previous Comparator returns 0, use this comparator that returns Object or can be autoboxed into one.|
> |thenComparingDouble(function) |If previous Comparator returns 0, use this comparator that returns double. Otherwise, return value from previous Comparator.|
> |thenComparingInt(function) |If previous Comparator returns 0, use this comparator that returns int. Otherwise, return value from previous Comparator.|
> |thenComparingLong(function) |If previous Comparator returns 0, use this comparator that returns long. Otherwise, return value from previous Comparator.|

> **Note**: <br />
> You’ve probably noticed by now that we often ignore null values in
checking equality and comparing objects. This works fine for the exam. In
the real world, though, things aren’t so neat. You will have to decide how
to handle null values or prevent them from being in your object.

## V. Sorting and Searching
Now that you’ve learned all about Comparable and Comparator, we can finally do
something useful with them, like sorting. The Collections.sort() method uses the
compareTo() method to sort. It expects the objects to be sorted to be Comparable.

```java
2:  public class SortRabbits {
3:      static record Rabbit(int id) {}
4:      public static void main(String[] args) {
5:          List<Rabbit> rabbits = new ArrayList<>();
6:          rabbits.add(new Rabbit(3));
7:          rabbits.add(new Rabbit(1));
8:          Collections.sort(rabbits); // DOES NOT COMPILE
9:      } 
10: }
```

&emsp;&emsp;
Java knows that the Rabbit record is not Comparable. It knows sorting will fail, so it
doesn’t even let the code compile. You can fix this by passing a Comparator to sort().
Remember that a Comparator is useful when you want to specify sort order without using a
compareTo() method.

```java
8:          Comparator<Rabbit> c = (r1, r2) -> r1.id - r2.id;
9:          Collections.sort(rabbits, c);
10:         System.out.println(rabbits); // [Rabbit[id=1], Rabbit[id=3]]
```

&emsp;&emsp;
Suppose you want to sort the rabbits in descending order. You could change the Comparator
to r2.id - r1.id. Alternatively, you could reverse the contents of the list afterward:

```java
8:          Comparator<Rabbit> c = (r1, r2) -> r1.id - r2.id;
9:          Collections.sort(rabbits, c);
10:         Collections.reverse(rabbits);
11:         System.out.println(rabbits); // [Rabbit[id=3], Rabbit[id=1]]
```

&emsp;&emsp;
The sort() and binarySearch() methods allow you to pass in a Comparator object
when you don’t want to use the natural order.

> ### **Reviewing binarySearch()**
> The binarySearch() method requires a sorted List.
> ```java
> 11:   List<Integer> list = Arrays.asList(6,9,1,8);
> 12:   Collections.sort(list); // [1, 6, 8, 9]
> 13:   System.out.println(Collections.binarySearch(list, 6)); // 1
> 14:   System.out.println(Collections.binarySearch(list, 3)); // -2
> ```
> Line 12 sorts the List so we can call binary search properly. Line 13 prints the index
at which a match is found. Line 14 prints one less than the negated index of where the
requested value would need to be inserted. The number 3 would need to be inserted at
index 1 (after the number 1 but before the number 6). Negating that gives us −1, and 
subtracting 1 gives us −2.

&emsp;&emsp;
There is a trick in working with binarySearch(). What do you think the following outputs?

```java
3:  var names = Arrays.asList("Fluffy", "Hoppy");
4:  Comparator<String> c = Comparator.reverseOrder();
5:  var index = Collections.binarySearch(names, "Hoppy", c);
6:  System.out.println(index);
```

&emsp;&emsp;
The answer happens to be -1. Before you panic, you don’t need to know that the
answer is -1. You do need to know that the answer is not defined. Line 3 creates a list,
`[Fluffy, Hoppy]`. This list happens to be sorted in ascending order. Line 4 creates a 
Comparator that reverses the natural order. Line 5 requests a binary search in descending order.
Since the list is not in that order, we don’t meet the precondition for doing a search. <br />

&emsp;&emsp;
While the result of calling binarySearch() on an improperly sorted list is undefined, 
sometimes you can get lucky. For example, search starts in the middle of an odd-numbered list. If
you happen to ask for the middle element, the index returned will be what you expect. <br />

&emsp;&emsp;
Earlier in the chapter, we talked about collections that require classes to implement
Comparable. Unlike sorting, they don’t check that you have implemented Comparable at
compile time. <br />

&emsp;&emsp;
Going back to our Rabbit that does not implement Comparable, we try to add it to
a TreeSet:

```java
2:  public class UseTreeSet {
3:      static class Rabbit{ int id; }
4:      public static void main(String[] args) {
5:          Set<Duck> ducks = new TreeSet<>();
6:          ducks.add(new Duck("Puddles"));
7:
8:          Set<Rabbit> rabbits = new TreeSet<>();
9:          rabbits.add(new Rabbit()); // ClassCastException
10:     } 
11: }
```

&emsp;&emsp;
Line 6 is fine. Duck does implement Comparable. TreeSet is able to sort it into the
proper position in the set. Line 9 is a problem. When TreeSet tries to sort it, Java 
discovers the fact that Rabbit does not implement Comparable. Java throws an exception
that looks like this:

```java
Exception in thread "main" java.lang.ClassCastException:
    class Rabbit cannot be cast to class java.lang.Comparable
```

&emsp;&emsp;
It may seem weird for this exception to be thrown when the first object is added to the
set. After all, there is nothing to compare yet. Java works this way for consistency. <br />

&emsp;&emsp;
Just like searching and sorting, you can tell collections that require sorting that you want
to use a specific Comparator. For example:

```java
8:  Set<Rabbit> rabbits = new TreeSet<>((r1, r2) -> r1.id - r2.id);
9:  rabbits.add(new Rabbit());
```

&emsp;&emsp;
Now Java knows that you want to sort by id, and all is well. A Comparator is a 
helpful object. It lets you separate sort order from the object to be sorted. Notice that line
9 in both of the previous examples is the same. It’s the declaration of the TreeSet that
has changed.

## VI. Sorting a List
While you can call Collections.sort(list), you can also sort directly on the list object.

```java
3:  List<String> bunnies = new ArrayList<>();
4:  bunnies.add("long ear");
5:  bunnies.add("floppy");
6:  bunnies.add("hoppy");
7:  System.out.println(bunnies); // [long ear, floppy, hoppy]
8:  bunnies.sort((b1, b2) -> b1.compareTo(b2));
9:  System.out.println(bunnies); // [floppy, hoppy, long ear]
```

&emsp;&emsp;
On line 8, we sort the list alphabetically. The sort() method takes a Comparator that
provides the sort order. Remember that Comparator takes two parameters and returns an
int. If you need a review of what the return value of a compare() operation means, check
the Comparator section in this chapter or the “Comparing” section in Chapter 4, “Core
APIs.” This is really important to memorize! <br />

&emsp;&emsp;
There is not a sort method on Set or Map. Both of those types are unordered, so it
wouldn’t make sense to sort them.
