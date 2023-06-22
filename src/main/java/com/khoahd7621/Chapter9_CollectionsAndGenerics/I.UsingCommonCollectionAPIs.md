# Using common Collection APIs

A collection is a group of objects contained in a single object. The Java Collections 
Framework is a set of classes in java.util for storing collections. There are four main interfaces
in the Java Collections Framework.

- **List**: A list is an ordered collection of elements that allows duplicate entries. Elements
in a list can be accessed by an int index.
- **Set**: A set is a collection that does not allow duplicate entries.
- **Queue**: A queue is a collection that orders its elements in a specific order for processing.
A Deque is a subinterface of Queue that allows access at both ends.
- **Map**: A map is a collection that maps keys to values, with no duplicate keys allowed. The
elements in a map are key/value pairs.

&emsp;&emsp;
Figure 9.1 shows the Collection interface, its subinterfaces, and some classes that
implement the interfaces that you should know for the exam. The interfaces are shown in
rectangles, with the classes in rounded boxes. <br />

&emsp;&emsp;
Notice that Map doesn’t implement the Collection interface. It is considered part of the
Java Collections Framework even though it isn’t technically a Collection. It is a collection
(note the lowercase), though, in that it contains a group of objects. The reason maps are
treated differently is that they need different methods due to being key/value pairs.

```java
    | Collecion |
        | List |
             ArrayList
             LinkedList
        | Set |
             HashSet
             TreeSet
        | Queue |
            | Deque |
                 LinkedList 
    | Map |
         HashMap
         TreeMap
```

&emsp;&emsp;
In this section, we discuss the common methods that the Collections API provides to the
implementing classes. Many of these methods are convenience methods that could be 
implemented in other ways but make your code easier to write and read. This is why they are
convenient. <br />

&emsp;&emsp;
In this section, we use ArrayList and HashSet as our implementation classes, but they
can apply to any class that inherits the Collection interface. We cover the specific 
properties of each Collection class in the next section.

## I. Using the Diamond Operator
When constructing a Java Collections Framework, you need to specify the type that will go
inside. We could write code using generics like the following:

```java
List<Integer> list = new ArrayList<Integer>();
```

&emsp;&emsp;
You might even have generics that contain other generics, such as this:

```java
Map<Long, List<Integer>> mapLists = new HashMap<Long, List<Integer>>();
```
&emsp;&emsp;
That’s a lot of duplicate code to write! Luckily, the diamond operator (<>) is a shorthand
notation that allows you to omit the generic type from the right side of a statement when
the type can be inferred. It is called the diamond operator because <> looks like a diamond.
Compare the previous declarations with these new, much shorter versions:

```java
List<Integer> list = new ArrayList<>();
Map<Long, List<Integer>> mapOfLists = new HashMap<>();
```

&emsp;&emsp;
To the compiler, both these declarations and our previous ones are equivalent. To us,
though, the latter is a lot shorter and easier to read. <br />

&emsp;&emsp;
The diamond operator cannot be used as the type in a variable declaration. It can be
used only on the right side of an assignment operation. For example, neither of the following compiles:

```java
List<> list = new ArrayList<Integer>(); // DOES NOT COMPILE

class InvalidUse {
    void use(List<> data) {} // DOES NOT COMPILE
}
```

## II. Adding Data
The add() method inserts a new element into the Collection and returns whether it was successful. 
The method signature is as follows:

```java
public boolean add(E element)
```

&emsp;&emsp;
Remember that the Collections Framework uses generics. You will see E appear frequently.
It means the generic type that was used to create the collection. For some Collection types,
add() always returns true. For other types, there is logic as to whether the add() call was
successful. The following shows how to use this method:

```java
3: Collection<String> list = new ArrayList<>();
4: System.out.println(list.add("Sparrow")); // true
5: System.out.println(list.add("Sparrow")); // true
6:
7: Collection<String> set = new HashSet<>();
8: System.out.println(set.add("Sparrow")); // true
9: System.out.println(set.add("Sparrow")); // false
```

&emsp;&emsp;
A List allows duplicates, making the return value true each time. A Set does not
allow duplicates. On line 9, we tried to add a duplicate so that Java returns false from the
add() method.

## III. Removing Data
The remove() method removes a single matching value in the Collection and returns whether it
was successful. The method signature is as follows:

```java
public boolean remove(Object object)
```

&emsp;&emsp;
This time, the boolean return value tells us whether a match was removed. 
The following shows how to use this method:

```java
3: Collection<String> birds = new ArrayList<>();
4: birds.add("hawk");                               // [hawk]
5: birds.add("hawk");                               // [hawk, hawk]
6: System.out.println(birds.remove("cardinal"));    // false
7: System.out.println(birds.remove("hawk"));        // true
8: System.out.println(birds);                       // [hawk]
```

&emsp;&emsp;
Line 6 tries to remove an element that is not in birds. It returns false because no such
element is found. Line 7 tries to remove an element that is in birds, so it returns true.
Notice that it removes only one match.

## IV. Counting Elements
The isEmpty() and size() methods look at how many elements are in the Collection. The
method signatures are as follows:

```java
public boolean isEmpty()
public int size()
```

&emsp;&emsp;
The following shows how to use these methods:

```java
Collection<String> birds = new ArrayList<>();
System.out.println(birds.isEmpty());    // true
System.out.println(birds.size());       // 0
birds.add("hawk");                      // [hawk]
birds.add("hawk");                      // [hawk, hawk]
System.out.println(birds.isEmpty());    // false
System.out.println(birds.size());       // 2
```

&emsp;&emsp;
At the beginning, birds has a size of 0 and is empty. It has a capacity that is greater than 0.
After we add elements, the size becomes positive, and it is no longer empty.

## V. Clearing the Collection

The clear() method provides an easy way to discard all elements of the Collection. The method
signature is as follows:

```java
public void clear()
```

&emsp;&emsp;
The following shows how to use this method:

```java
Collection<String> birds = new ArrayList<>();
birds.add("hawk");                      // [hawk]
birds.add("hawk");                      // [hawk, hawk]
System.out.println(birds.isEmpty());    // false
System.out.println(birds.size());       // 2
birds.clear();                          // []
System.out.println(birds.isEmpty());    // true
System.out.println(birds.size());       // 0
```

&emsp;&emsp;
After calling clear(), birds is back to being an empty ArrayList of size 0.

## VI. Check Contents

The contains() method checks whether a certain value is in the Collection. The method
signature is as follows:

```java
public boolean contains(Object object)
```

&emsp;&emsp;
The following shows how to use this method:

```java
Collection<String> birds = new ArrayList<>();
birds.add("hawk");                           // [hawk]
System.out.println(birds.contains("hawk"));  // true
System.out.println(birds.contains("robin")); // false
```

&emsp;&emsp;
The contains() method calls equals() on elements of the ArrayList to see whether
there are any matches.

## VII. Removing with Conditions

The removeIf() method removes all elements that match a condition. We can specify what
should be deleted using a block of code or even a method reference. <br />

&emsp;&emsp;
The method signature looks like the following. (We explain what the ? super means in
the “Working with Generics” section later in this chapter.)

```java
public boolean removeIf(Predicate<? super E> filter)
```

It uses a Predicate, which takes one parameter and returns a boolean. Let’s take a
look at an example:

```java
4: Collection<String> list = new ArrayList<>();
5: list.add("Magician");
6: list.add("Assistant");
7: System.out.println(list);    // [Magician, Assistant]
8: list.removeIf(s -> s.startsWith("A"));
9: System.out.println(list);    // [Magician]
```

&emsp;&emsp;
Line 8 shows how to remove all of the String values that begin with the letter A. This
allows us to make the Assistant disappear. Let’s try an example with a method reference:

```java
11: Collection<String> set = new HashSet<>();
12: set.add("Wand");
13: set.add("");
14: set.removeIf(String::isEmpty);  // s -> s.isEmpty()
15: System.out.println(set);        // [Wand]
```

&emsp;&emsp;
On line 14, we remove any empty String objects from set. The comment on that line
shows the lambda equivalent of the method reference. Line 15 shows that the removeIf()
method successfully removed one element from list.

## VIII. Iterating
There’s a forEach() method that you can call on a Collection instead of writing a loop. It uses a
Consumer that takes a single parameter and doesn’t return anything. The method signature is
as follows:

```java
public void forEach(Consumer<? super T> action)
```

&emsp;&emsp;
Cats like to explore, so let’s print out two of them using both method references
and lambdas:

```java
Collection<String> cats = List.of("Annie", "Ripley");
cats.forEach(System.out::println);
cats.forEach(c -> System.out.println(c));
```

&emsp;&emsp;
The cats have discovered how to print their names. Now they have more time to play
(as do we)!

> **Other Iteration Approaches** <br />
> There are other ways to iterate through a Collection. For example, in Chapter 3, “Making
Decisions,” you saw how to loop through a list using an enhanced for loop.
> ```java
>   for (String element: coll)
>       System.out.println(element);
> ```
> You may see another older approach used.
> ```java
>   Iterator<String> iter = coll.iterator();
>       while(iter.hasNext()) {
>       String string = iter.next();
>       System.out.println(string);
>   }
> ```
> Pay attention to the difference between these techniques. The hasNext() method checks
whether there is a next value. In other words, it tells you whether next() will execute
without throwing an exception. The next() method actually moves the Iterator to the
next element.

## IX. Determining Equality
There is a custom implementation of equals() so you can compare two Collections to compare
the type and contents. The implementation will vary. For example, ArrayList checks order,
while HashSet does not.

```java
boolean equals(Object object)
```

&emsp;&emsp;
The following shows an example:

```java
23: var list1 = List.of(1, 2);
24: var list2 = List.of(2, 1);
25: var set1 = Set.of(1, 2);
26: var set2 = Set.of(2, 1);
27:
28: System.out.println(list1.equals(list2)); // false
29: System.out.println(set1.equals(set2));   // true
30: System.out.println(list1.equals(set1));  // false
```

&emsp;&emsp;
Line 28 prints false because the elements are in a different order, and a List cares
about order. By contrast, line 29 prints true because a Set is not sensitive to order. Finally,
line 30 prints false because the types are different.

> **Unboxing nulls** <br />
> Java protects us from many problems with Collections. However, it is still possible to
write a NullPointerException:
> ```java
> 3: var heights = new ArrayList<Integer>();
> 4: heights.add(null);
> 5: int h = heights.get(0); // NullPointerException
> ```
> On line 4, we add a null to the list. This is legal because a null reference can be assigned
to any reference variable. On line 5, we try to unbox that null to an int primitive. This is a
problem. Java tries to get the int value of null. Since calling any method on null gives a
NullPointerException, that is just what we get. Be careful when you see null in 
relation to autoboxing.
