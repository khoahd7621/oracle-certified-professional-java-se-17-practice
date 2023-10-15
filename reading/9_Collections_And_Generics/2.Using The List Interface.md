# Using the List Interface

Now that you’re familiar with some common Collection interface methods, let’s move on
to specific interfaces. You use a list when you want an ordered collection that can contain
duplicate entries. For example, a list of names may contain duplicates, as two animals can
have the same name. Items can be retrieved and inserted at specific positions in the list based
on an int index, much like an array. Unlike an array, though, many List implementations can
change in size after they are declared. <br />

&emsp;&emsp;
Lists are commonly used because there are many situations in programming where you
need to keep track of a list of objects. For example, you might make a list of what you want
to see at the zoo: first, see the lions, because they go to sleep early; second, see the pandas,
because there is a long line later in the day; and so forth. <br />

&emsp;&emsp;
Figure 9.2 shows how you can envision a List. Each element of the List has an index,
and the indexes begin with zero.

> **Figure 9.2** Example of a List
> 
> | Ordered index | Data   |
> |---------------|--------|
> | 0             | Lions  |
> | 1             | Pandas |
> | 2             | Tigers |
> | ...           | ...    |

&emsp;&emsp;
Sometimes you don’t care about the order of elements in a list. List is like the “go to”
data type. When we make a shopping list before going to the store, the order of the list 
happens to be the order in which we thought of the items. We probably aren’t attached to that
particular order, but it isn’t hurting anything. <br />

&emsp;&emsp;
While the classes implementing the List interface have many methods, you need to know
only the most common ones. Conveniently, these methods are the same for all of the 
implementations that might show up on the exam. <br />

&emsp;&emsp;
The main thing all List implementations have in common is that they are ordered and
allow duplicates. Beyond that, they each offer different functionality. We look at the 
implementations that you need to know and the available methods.

> ### **Note**: <br />
> Pay special attention to which names are classes and which are 
interfaces. The exam may ask you which is the best class or which is the best
interface for a scenario.

## I. Comparing List Implementations
An ArrayList is like a resizable array. When elements are added, the ArrayList automatically
grows. When you aren’t sure which collection to use, use an ArrayList. <br />

&emsp;&emsp;
The main benefit of an ArrayList is that you can look up any element in constant time.
Adding or removing an element is slower than accessing an element. This makes an ArrayList
a good choice when you are reading more often than (or the same amount as) writing to the
ArrayList. <br />

&emsp;&emsp;
A LinkedList is special because it implements both List and Deque. It has all the methods
of a List. It also has additional methods to facilitate adding or removing from the beginning
and/or end of the list. <br />

&emsp;&emsp;
The main benefits of a LinkedList are that you can access, add to, and remove from the
beginning and end of the list in constant time. The trade-off is that dealing with an arbitrary
index takes linear time. This makes a LinkedList a good choice when you’ll be using it
as Deque. As you saw in Figure 9.1, a LinkedList implements both the List and Deque
interfaces.

## II. Creating a List with a Factory
When you create a List of type ArrayList or LinkedList, you know the type. There are
a few special methods where you get a List back but don’t know the type. These methods
let you create a List including data in one line using a factory method. This is convenient,
especially when testing. Some of these methods return an immutable object. As we saw in
Chapter 6, “Class Design,” an immutable object cannot be changed or modified. Table 9.1
summarizes these three lists.

> **Table 9.1** Factory Methods for Creating Lists
> 
> |Method| Description                                                         |Can add elements?|Can replace elements?|Can delete elements?|
> |------|---------------------------------------------------------------------|-----------------|---------------------|--------------------|
> |Arrays.asList(varargs)| Returns a fixed-size list backed by an array.                       |No|Yes|No|
> |List.of(varargs)| Returns an immutable list.                                          |No|No|No|
> |List.copyOf(Collection)| Returns an immutable list with copy of original collection's values |No|No|No|

&emsp;&emsp;
Let’s take a look at an example of these three methods:

```java
16: String[] array = new String[] {"a", "b", "c"};
17: List<String> asList = Arrays.asList(array); // [a, b, c]
18: List<String> of = List.of(array);           // [a, b, c]
19: List<String> copy = List.copyOf(asList);    // [a, b, c]
20:
21: array[0] = "z";
22:
23: System.out.println(asList);                 // [z, b, c]
24: System.out.println(of);                     // [a, b, c]
25: System.out.println(copy);                   // [a, b, c]
26:
27: asList.set(0, "x");
28: System.out.println(Arrays.toString(array)); // [x, b, c]
29:
30: copy.add("y");                              // UnsupportedOperationException
```

&emsp;&emsp;
Line 17 creates a List that is backed by an array. Line 21 changes the array, and line
23 reflects that change. Lines 27 and 28 show the other direction where changing the List
updates the underlying array. Lines 18 and 19 create an immutable List. Line 30 shows it is
immutable by throwing an exception when trying to add a value. All three lists would throw
an exception when adding or removing a value. The of and copy lists would also throw one
on trying to update an element.

## III. Creating a List with a Constructor
Most Collections have two constructors that you need to know for the exam. The following
shows them for LinkedList:

```java
var linked1 = new LinkedList<String>();
var linked2 = new LinkedList<String>(linked1);
```

&emsp;&emsp;
The first says to create an empty LinkedList containing all the defaults. The second tells
Java that we want to make a copy of another LinkedList. Granted, linked1 is empty in
this example, so it isn’t particularly interesting. <br />

&emsp;&emsp;
ArrayList has an extra constructor you need to know. We now show the three constructors:

```java
var list1 = new ArrayList<String>();
var list2 = new ArrayList<String>(list1);
var list3 = new ArrayList<String>(10);
```

&emsp;&emsp;
The first two are the common constructors you need to know for all Collections. The
final example says to create an ArrayList containing a specific number of slots, but again
not to assign any. You can think of this as the size of the underlying array.

> ### **Using *var* with ArrayList**: <br />
> Consider this code, which mixes var and generics:
> ```java
> var strings = new ArrayList<String>();
> strings.add("a");
> for (String s: strings) { }
> ```
> The type of var is ArrayList<String>. This means you can add a String or loop
through the String objects. What if we use the diamond operator with var?
> ```java
> var list = new ArrayList<>();
> ```
> Believe it or not, this does compile. The type of the var is ArrayList&lt;Object&gt;. Since
there isn’t a type specified for the generic, Java has to assume the ultimate superclass. This
is a bit silly and unexpected, so please don’t write it. But if you see it on the exam, you’ll
know what to expect. Now can you figure out why this doesn’t compile?
> ```java
> var list = new ArrayList<>();
> list.add("a");
> for (String s: list) { } // DOES NOT COMPILE
> ```
> The type of var is ArrayList&lt;Object&gt;. Since there isn’t a type in the diamond operator,
Java has to assume the most generic option it can. Therefore, it picks Object, the ultimate
superclass. Adding a String to the list is fine. You can add any subclass of Object. 
However, in the loop, we need to use the Object type rather than String.

## IV. Working with List Methods
The methods in the List interface are for working with indexes. In addition to the inherited
Collection methods, the method signatures that you need to know are in Table 9.2.

> **Table 9.2** List Methods
> 
> |Method| Description                                                                                           |
> |------|-------------------------------------------------------------------------------------------------------|
> |public boolean add(E element)| Adds element to end (available on all Collection APIs)                                                |
> |public void add(int index, E element)| Adds element at index and moves the rest toward the end.                                              |
> |public E get(int index)| Returns element at index.                                                                             |
> |public E remove(int index)| Removes element at index and moves the rest toward the front.                                         |
> |public default void replaceAll(UnaryOperator<E> o)| Replaces each element with the result of operator                                                     |
> |public E set(int index, E e)| Replaces element at index and returns original. Throws IndexOutOfBoundsException if index is invalid. |
> |public default void sort(Comparator<? super E> c)| Sorts list. We cover this later in the chapter in the "Sorting Data" section.                         |

&emsp;&emsp;
The following statements demonstrate most of these methods for working with a List:

```java
3: List<String> list = new ArrayList<>();
4: list.add("SD");                  // [SD]
5: list.add(0, "NY");               // [NY,SD]
6: list.set(1, "FL");               // [NY,FL]
7: System.out.println(list.get(0)); // NY
8: list.remove("NY");               // [FL]
9: list.remove(0);                  // []
10: list.set(0, "?");               // IndexOutOfBoundsException
```

&emsp;&emsp;
On line 3, list starts out empty. Line 4 adds an element to the end of the list. Line 5
adds an element at index 0 that bumps the original index 0 to index 1. Notice how the
ArrayList is now automatically one larger. Line 6 replaces the element at index 1 with a
new value. <br />

&emsp;&emsp;
Line 7 uses the get() method to print the element at a specific index. Line 8 removes the
element matching NY. Finally, line 9 removes the element at index 0, and list is empty again. <br />

&emsp;&emsp;
Line 10 throws an IndexOutOfBoundsException because there are no elements in
the List. Since there are no elements to replace, even index 0 isn’t allowed. If line 10 were
moved up between lines 4 and 5, the call would succeed. <br />

&emsp;&emsp;
The output would be the same if you tried these examples with LinkedList. Although the
code would be less efficient, it wouldn’t be noticeable until you had very large lists. <br />

&emsp;&emsp;
Now let’s take a look at the replaceAll() method. It uses a UnaryOperator that takes one
parameter and returns a value of the same type:

```java
var numbers = Arrays.asList(1, 2, 3);
numbers.replaceAll(x -> x * 2);
System.out.println(numbers); // [2, 4, 6]
```

&emsp;&emsp;
This lambda doubles the value of each element in the list. The replaceAll() method
calls the lambda on each element of the list and replaces the value at that index.

> ### **Overloaded *remove()* Methods** <br />
> We’ve now seen two overloaded remove() methods. The one from Collection removes
an object that matches the parameter. By contrast, the one from List removes an element
at a specified index. <br />
> This gets tricky when you have an Integer type. What do you think the following prints?
> ```java
> 31: var list = new LinkedList<Integer>();
> 32: list.add(3);
> 33: list.add(2);
> 34: list.add(1);
> 35: list.remove(2);
> 36: list.remove(Integer.valueOf(2));
> 37: System.out.println(list);
> ```
> The correct answer is [3]. Let’s look at how we got there. At the end of line 34, we have
[3, 2, 1]. Line 35 passes a primitive, which means we are requesting deletion of the
element at index 2. This leaves us with [3, 2]. Then line 36 passes an Integer object,
which means we are deleting the value 2. That brings us to [3]. <br />
> Since calling remove() with an int uses the index, an index that doesn’t exist
will throw an exception. For example, list.remove(100) throws an
IndexOutOfBoundsException.

## V. Converting from List to an Array
Since an array can be passed as a vararg, Table 9.1 covered how to convert an array to a List.
You should also know how to do the reverse. Let’s start with turning a List into an array:

```java
13: List<String> list = new ArrayList<>();
14: list.add("hawk");
15: list.add("robin");
16: Object[] objectArray = list.toArray();
17: String[] stringArray = list.toArray(new String[0]);
18: list.clear();
19: System.out.println(objectArray.length); // 2
20: System.out.println(stringArray.length); // 2
```

&emsp;&emsp;
Line 16 shows that a List knows how to convert itself to an array. The only problem is
that it defaults to an array of class Object. This isn’t usually what you want. Line 17 
specifies the type of the array and does what we want. The advantage of specifying a size of 0
for the parameter is that Java will create a new array of the proper size for the return value.
If you like, you can suggest a larger array to be used instead. If the List fits in that array, it
will be returned. Otherwise, a new array will be created. <br />

&emsp;&emsp;
Also, notice that line 18 clears the original List. This does not affect either array. The array
is a newly created object with no relationship to the original List. It is simply a copy.
