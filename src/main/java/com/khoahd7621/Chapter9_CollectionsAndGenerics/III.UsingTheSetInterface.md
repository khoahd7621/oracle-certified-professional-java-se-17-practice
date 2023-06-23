# Using the Set Interface

You use a Set when you don’t want to allow duplicate entries. For example, you might want
to keep track of the unique animals that you want to see at the zoo. You aren’t concerned
with the order in which you see these animals, but there isn’t time to see them more than
once. You just want to make sure you see the ones that are important to you and remove
them from the set of outstanding animals to see after you see them. <br />

&emsp;&emsp;
The main thing that all Set implementations have in common 
is that they do not allow duplicates. We look at each implementation
that you need to know for the exam and how to write code using Set.

## I. Comparing Set Implementations
A HashSet stores its elements in a hash table, which means the keys are a hash and the
values are an Object. This means that the HashSet uses the hashCode() method of the
objects to retrieve them more efficiently. Remember that a valid hashCode() doesn’t mean
every object will get a unique value, but the method is often written so that hash values are
spread out over a large range to reduce collisions. <br />

&emsp;&emsp;
The main benefit is that adding elements and checking whether an element is in the set
both have constant time. The trade-off is that you lose the order in which you inserted the
elements. Most of the time, you aren’t concerned with this in a Set anyway, making HashSet
the most common set. <br />

&emsp;&emsp;
A TreeSet stores its elements in a sorted tree structure. The main benefit is that the set is
always in sorted order. The trade-off is that adding and checking whether an element exists
takes longer than with a HashSet, especially as the tree grows larger. <br />

&emsp;&emsp;
Figure 9.4 shows how you can envision HashSet and TreeSet being stored. HashSet is
more complicated in reality, but this is fine for the purpose of the exam.

> **FIGURE 9.4** Examples of a HashSet and TreeSet
> 
> ##### HashSet
> |hashCode() value|Data|
> |---------------|----|
> |−995544615|pandas|
> |...|...|
> |−761039044|zebras|
> |...|...|
> |102978519|bears|
> 
> #### TreeSet
> &emsp;&emsp;&ensp;(pandas) <br />
> &emsp;&emsp;&ensp;/&emsp;&emsp;&emsp;&ensp;\ <br />
> &emsp;(lions)&emsp;(zebras)

&emsp;&emsp;
For the exam, you don’t need to know how to create a hash or tree set 
(the implementation can be complex). Phew! You just need to know how to use them!

## II. Working with Set Methods
Like a List, you can create an immutable Set in one line or make a copy of an existing one.

```java
Set<Character> letters = Set.of('z', 'o', 'o');
Set<Character> copy = Set.copyOf(letters);
```

&emsp;&emsp;
Those are the only extra methods you need to know for the Set interface for the exam!
You do have to know how sets behave with respect to the traditional Collection methods.
You also have to know the differences between the types of sets. Let’s start with HashSet:

```java
3: Set<Integer> set = new HashSet<>();
4: boolean b1 = set.add(66); // true
5: boolean b2 = set.add(10); // true
6: boolean b3 = set.add(66); // false
7: boolean b4 = set.add(8);  // true
8: set.forEach(System.out::println);
```

&emsp;&emsp;
This code prints three lines:

```java
66
8
10
```

&emsp;&emsp;
The add() methods should be straightforward. They return true unless the Integer
is already in the set. Line 6 returns false, because we already have 66 in the set, and a set
must preserve uniqueness. Line 8 prints the elements of the set in an arbitrary order. In this
case, it happens not to be sorted order or the order in which we added the elements. <br />

&emsp;&emsp;
Remember that the equals() method is used to determine equality. The hashCode()
method is used to know which bucket to look in so that Java doesn’t have to look through
the whole set to find out whether an object is there. The best case is that hash codes are
unique and Java has to call equals() on only one object. The worst case is that all implementations return the same hashCode() and Java has to call equals() on every element of
the set anyway. <br />

&emsp;&emsp;
Now let’s look at the same example with TreeSet:

```java
3: Set<Integer> set = new TreeSet<>();
4: boolean b1 = set.add(66); // true
5: boolean b2 = set.add(10); // true
6: boolean b3 = set.add(66); // false
7: boolean b4 = set.add(8);  // true
8: set.forEach(System.out::println);
```

&emsp;&emsp;
This time, the code prints the following:

```java
8
10
66
```

&emsp;&emsp;
The elements are printed out in their natural sorted order. Numbers implement the
Comparable interface in Java, which is used for sorting. Later in the chapter, you learn how
to create your own Comparable objects.
