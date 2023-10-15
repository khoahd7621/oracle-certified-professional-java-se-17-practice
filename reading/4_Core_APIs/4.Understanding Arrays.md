# Understanding Arrays

Up to now, we’ve been referring to the String and StringBuilder classes as a “sequence
of characters.” This is true. They are implemented using an array of characters. An array is
an area of memory on the heap with space for a designated number of elements. A String is
implemented as an array with some methods that you might want to use when dealing with
characters specifically. A StringBuilder is implemented as an array where the array object
is replaced with a new, bigger array object when it runs out of space to store all the characters. 
A big difference is that an array can be of any other Java type. If we didn't want to use
a String for some reason, we could use an array of char primitives directly:

```java
char[] letters;
```

&emsp;&emsp;
This wouldn’t be very convenient because we’d lose all the special properties String
gives us, such as writing "Java". Keep in mind that letters is a reference variable and not
a primitive. The char type is a primitive. But char is what goes into the array and not the
type of the array itself. The array itself is of type char[]. You can mentally read the brackets
([]) as “array.”

&emsp;&emsp;
In other words, an array is an ordered list. It can contain duplicates. In this section, we
look at creating an array of primitives and objects, sorting, searching, varargs, and multidimensional arrays.

## 1. Creating an Array of Primitives
The most common way to create an array is shown below. It specifies the type of the
array (int) and the size (3). The brackets tell you this is an array.

```java
int[] numbers = new int[3];
```

&emsp;&emsp;
When you use this form to instantiate an array, all elements are set to the default value
for that type. As you learned in Chapter 1, the default value of an int is 0. Since numbers
is a reference variable, it points to the array object, as shown in table below. As you can see,
the default value for all the elements is 0. Also, the indexes start with 0 and count up, just as
they did for a String.

| Index | 0 | 1 | 2 |
| --- | --- | --- | --- |
| Value | 0 | 0 | 0 |

&emsp;&emsp;
Another way to create an array is to specify all the elements it should start out with:

```java
int[] moreNumbers = new int[] {42, 55, 99};
```

&emsp;&emsp;
In this example, we also create an int array of size 3. This time, we specify the initial
values of those three elements instead of using the defaults.

&emsp;&emsp;
Java recognizes that this expression is redundant. Since you are specifying the type of the
array on the left side of the equals sign, Java already knows the type. And since you are specifying 
the initial values, it already knows the size. As a shortcut, Java lets you write this:

```java
int[] moreNumbers = {42, 55, 99};
```

&emsp;&emsp;
This approach is called an anonymous array. It is anonymous because you don’t specify
the type and size.

&emsp;&emsp;
Finally, you can type the [] before or after the name, and adding a space is optional. This
means that all five of these statements do the exact same thing:

```java
int[] numAnimals;
int [] numAnimals2;
int []numAnimals3;
int numAnimals4[];
int numAnimals5 [];
```

&emsp;&emsp;
Most people use the first one. You could see any of these on the exam, though, so get used
to seeing the brackets in odd places.

> **Multiple “Arrays” in Declarations** <br />
> What types of reference variables do you think the following code creates?
> ```java
> int[] ids, types;
> ```
> The correct answer is two variables of type int[]. This seems logical enough. After all,
int a, b; created two int variables. What about this example?
> ```java
> int ids[], types;
> ```
> All we did was move the brackets, but it changed the behavior. This time we get one variable
of type int[] and one variable of type int. Java sees this line of code and thinks something
like this: “They want two variables of type int. The first one is called ids[]. This one is an
int[] called ids. The second one is just called types. No brackets, so it is a regular integer.” <br />
> 
> Needless to say, you shouldn’t write code that looks like this. But you do need to understand it for the exam.

## 2. Creating an Array with Reference Variables
You can choose any Java type to be the type of the array. This includes classes you create
yourself. Let’s take a look at a built-in type with String:

```java
String[] bugs = { "cricket", "beetle", "ladybug" };
String[] alias = bugs;
System.out.println(bugs.equals(alias)); // true
System.out.println(bugs.toString());    // [Ljava.lang.String;@160bc7c0
```

&emsp;&emsp;
We can call equals() because an array is an object. It returns true because of reference equality. 
The equals() method on arrays does not look at the elements of the array.
Remember, this would work even on an int[] too. The type int is a primitive; int[] is
an object. <br />

&emsp;&emsp;
The second print statement is even more interesting. What on earth is [Ljava.lang.
String;@160bc7c0? You don’t have to know this for the exam, but [L means it is an array,
java.lang.String is the reference type, and 160bc7c0 is the hash code. You’ll get different 
numbers and letters each time you run it since this is a referenc

> **Note** <br />
> Java provides a method that prints an array nicely: `Arrays.toString(bugs)` would print `[cricket, beetle, ladybug]`.

&emsp;&emsp;
The array does not allocate space for the String
objects. Instead, it allocates space for a reference to where the objects are really stored.

&emsp;&emsp;
As a quick review, what do you think this array points to?

```java
public class Names {
    String names[];
}
```

&emsp;&emsp;
You got us. It was a review of Chapter 1 and not our discussion on arrays. The answer is
null. The code never instantiated the array, so it is just a reference variable to null. Let’s
try that again: what do you think this array points to?

```java
public class Names {
    String names[] = new String[2];
}
```

&emsp;&emsp;
It is an array because it has brackets. It is an array of type String since that is the type
mentioned in the declaration. It has two elements because the length is 2. Each of those two
slots currently is null but has the potential to point to a String object.

&emsp;&emsp;
Remember casting from the previous chapter when you wanted to force a bigger type into
a smaller type? You can do that with arrays too:

```java
3: String[] strings = { "stringValue" };
4: Object[] objects = strings;
5: String[] againStrings = (String[]) objects;
6: againStrings[0] = new StringBuilder();   // DOES NOT COMPILE
7: objects[0] = new StringBuilder();        // Careful!
```

&emsp;&emsp;
Line 3 creates an array of type String. Line 4 doesn’t require a cast because Object is
a broader type than String. On line 5, a cast is needed because we are moving to a more
specific type. Line 6 doesn’t compile because a String[] only allows String objects, and
StringBuilder is not a String.

&emsp;&emsp;
Line 7 is where this gets interesting. From the point of view of the compiler, this is just
fine. A StringBuilder object can clearly go in an Object[]. The problem is that we don’t
actually have an Object[]. We have a String[] referred to from an Object[] variable.
At runtime, the code throws an ArrayStoreException. You don’t need to memorize the
name of this exception, but you do need to know that the code will throw an exception.

## 3. Using an Array
Now that you know how to create an array, let’s try accessing one:

```java
4: String[] mammals = {"monkey", "chimp", "donkey"};
5: System.out.println(mammals.length);  // 3
6: System.out.println(mammals[0]);      // monkey
7: System.out.println(mammals[1]);      // chimp
8: System.out.println(mammals[2]);      // donkey
```

&emsp;&emsp;
Line 4 declares and initializes the array. Line 5 tells us how many elements the array can
hold. The rest of the code prints the array. Notice that elements are indexed starting with 0.
This should be familiar from String and StringBuilder, which also start counting with 0. 
Those classes also counted length as the number of elements. Note that there are no
parentheses after length since it is not a method. Watch out for compiler errors like the following on the exam!

```java
4: String[] mammals = {"monkey", "chimp", "donkey"};
5: System.out.println(mammals.length()); // DOES NOT COMPILE
```

&emsp;&emsp;
To make sure you understand how length works, what do you think this prints?

```java
var birds = new String[6];
System.out.println(birds.length);
```

&emsp;&emsp;
The answer is 6. Even though all six elements of the array are null, there are still six of
them. The length attribute does not consider what is in the array; it only considers how
many slots have been allocated.

&emsp;&emsp;
It is very common to use a loop when reading from or writing to an array. This loop sets
each element of numbers to five higher than the current index:

```java
5: var numbers = new int[10];
6: for (int i = 0; i < numbers.length; i++)
7: numbers[i] = i + 5;
```

&emsp;&emsp;
The exam will test whether you are being observant by trying to access elements that are not
in the array. Can you tell why each of these throws an ArrayIndexOutOfBoundsException
for our array of size 10?

```java
numbers[10] = 3;

numbers[numbers.length] = 5;

for (int i = 0; i <= numbers.length; i++)
    numbers[i] = i + 5;
```

&emsp;&emsp;
The first one is trying to see whether you know that indexes start with 0. Since we have 10
elements in our array, this means only numbers[0] through numbers[9] are valid. The second
example assumes you are clever enough to know that 10 is invalid and disguises it by using the
length field. However, the length is always one more than the maximum valid index. Finally, the
for loop incorrectly uses <= instead of <, which is also a way of referring to that tenth element.

## 4. Sorting
Java makes it easy to sort an array by providing a sort method—or rather, a bunch of sort
methods. Just like StringBuilder allowed you to pass almost anything to append(), you
can pass almost any array to Arrays.sort(). <br />

&emsp;&emsp;
Arrays requires an import. To use it, you must have either of the following two statements in your class:
```java
import java.util.*;         // import whole package including Arrays
import java.util.Arrays;    // import just Arrays
```

&emsp;&emsp;
There is one exception, although it doesn’t come up often on the exam. You can write
java.util.Arrays every time it is used in the class instead of specifying it as an import. <br />

&emsp;&emsp;
Remember that if you are shown a code snippet, you can assume the necessary imports
are there. This simple example sorts three numbers:

```java
int[] numbers = { 6, 9, 1 };
Arrays.sort(numbers);
for (int i = 0; i < numbers.length; i++)
    System.out.print(numbers[i] + " ");
```

&emsp;&emsp;
The result is 1 6 9, as you should expect it to be. Notice that we looped through the output
to print the values in the array. Just printing the array variable directly would give the annoying
hash of [I@2bd9c3e7. Alternatively, we could have printed Arrays.toString(numbers)
instead of using the loop. That would have output [1, 6, 9]. <br />

&emsp;&emsp;
Try this again with String types:

```java
String[] strings = { "10", "9", "100" };
Arrays.sort(strings);
for (String s : strings)
    System.out.print(s + " ");
```

&emsp;&emsp;
This time the result might not be what you expect. This code outputs 10 100 9. The
problem is that String sorts in alphabetic order, and 1 sorts before 9. (Numbers sort before
letters, and uppercase sorts before lowercase.) In Chapter 9, “Collections and Generics,” you
learn how to create custom sort orders using something called a comparator. <br />

&emsp;&emsp;
Did you notice we sneaked the enhanced for loop into this example? Since we aren’t using
the index, we don’t need the traditional for loop. That won’t stop the exam creators from
using it, though, so we’ll be sure to use both to keep you sharp!

## 5. Searching
Java also provides a convenient way to search, but only if the array is already sorted.

- Binary search rule

|Scenario|Result|
|---|---|
|Target element found in sorted array|Index of match|
|Target element not found in sorted array|Negative value showing one smaller than the negative of the index, where a match needs to be inserted to preserve sorted order|
|Unsorted array|A surprise; this result is undefined|

&emsp;&emsp;
Let’s try these rules with an example:

```java
3: int[] numbers = {2,4,6,8};
4: System.out.println(Arrays.binarySearch(numbers, 2)); // 0
5: System.out.println(Arrays.binarySearch(numbers, 4)); // 1
6: System.out.println(Arrays.binarySearch(numbers, 1)); // -1
7: System.out.println(Arrays.binarySearch(numbers, 3)); // -2
8: System.out.println(Arrays.binarySearch(numbers, 9)); // -5
```

&emsp;&emsp;
Take note of the fact that line 3 is a sorted array. If it wasn’t, we couldn’t apply either of
the other rules. Line 4 searches for the index of 2. The answer is index 0. Line 5 searches for
the index of 4, which is 1.

&emsp;&emsp;
Line 6 searches for the index of 1. Although 1 isn’t in the list, the search can determine
that it should be inserted at element 0 to preserve the sorted order. Since 0 already means
something for array indexes, Java needs to subtract 1 to give us the answer of –1. Line 7
is similar. Although 3 isn’t in the list, it would need to be inserted at element 1 to preserve
the sorted order. We negate and subtract 1 for consistency, getting –1 –1, also known as –2.
Finally, line 8 wants to tell us that 9 should be inserted at index 4. We again negate and subtract 1, getting –4 –1, also known as –5. <br />

&emsp;&emsp;
What do you think happens in this example?

```java
5: int[] numbers = new int[] {3,2,1};
6: System.out.println(Arrays.binarySearch(numbers, 2));
7: System.out.println(Arrays.binarySearch(numbers, 3));
```

&emsp;&emsp;
Note that on line 5, the array isn’t sorted. This means the output will not be defined.
When testing this example, line 6 correctly gave 1 as the output. However, line 7 gave the
wrong answer. The exam creators will not expect you to know what incorrect values come
out. As soon as you see the array isn’t sorted, look for an answer choice about unpredictable output. <br />

&emsp;&emsp;
On the exam, you need to know what a binary search returns in various scenarios. Oddly,
you don’t need to know why “binary” is in the name. In case you are curious, a binary
search splits the array into two equal pieces (remember, 2 is binary) and determines which
half the target is in. It repeats this process until only one element is left.

## 6. Comparing

Java also provides methods to compare two arrays to determine which is “smaller.” First we
cover the compare() method, and then we go on to mismatch(). These methods are overloaded
to take a variety of parameters.

### &emsp;&emsp; a. Using *compare()*

There are a bunch of rules you need to know before calling compare(). Luckily, these are the
same rules you need to know in Chapter 9 when writing a Comparator. <br />

&emsp;&emsp;
First you need to learn what the return value means. You do not need to know the exact
return values, but you do need to know the following:
- A negative number means the first array is smaller than the second.
- A zero means the arrays are equal.
- A positive number means the first array is larger than the second.

&emsp;&emsp;
Here’s an example:

```java
System.out.println(Arrays.compare(new int[] {1}, new int[] {2}));
```

&emsp;&emsp;
This code prints a negative number. It should be pretty intuitive that 1 is smaller than 2,
making the first array smaller. <br />

&emsp;&emsp;
Now that you know how to compare a single value, let’s look at how to compare arrays
of different lengths:

- If both arrays are the same length and have the same values in each spot in the same
  order, return zero.
- If all the elements are the same but the second array has extra elements at the end,
  return a negative number.
- If all the elements are the same, but the first array has extra elements at the end, return a
  positive number.
- If the first element that differs is smaller in the first array, return a negative number.
- If the first element that differs is larger in the first array, return a positive number.

&emsp;&emsp;
Finally, what does smaller mean? Here are some more rules that apply here and to
compareTo(), which you see in Chapter 8, “Lambdas and Functional Interfaces”:
- null is smaller than any other value.
- For numbers, normal numeric order applies.
- For strings, one is smaller if it is a prefix of another.
- For strings/characters, numbers are smaller than letters.
- For strings/characters, uppercase is smaller than lowercase.

&emsp;&emsp; 
Table shows examples of these rules in action.

|Array 1|Array 2|Result|Reason|
|---|---|---|---|
|new int[] {1, 2}|new int[] {1}|Positive number|The first element is the same, but the first array is longer|
|new int[] {1, 2}|new int[] {1, 2}|Zero|Exact match|
|new String[] {"a"}|new String[] {"aa"}|Negative number|The first element is a substring of the second|
|new String[] {"a"}|new String[] {"A"}|Positive number|Uppercase is smaller than lowercase|
|new String[] {"a"}|new String[] {null}|Positive number|null is smaller than a letter|

&emsp;&emsp;
Finally, this code does not compile because the types are different. When comparing two
arrays, they must be the same array type.

```java
System.out.println(Arrays.compare(new int[] {1}, new String[] {"a"})); // DOES NOT COMPILE
```

### &emsp;&emsp; b. Using *mismatch()*
Now that you are familiar with compare(), it is time to learn about mismatch(). If the
arrays are equal, mismatch() returns -1. Otherwise, it returns the first index where they
differ. Can you figure out what these print?

```java
System.out.println(Arrays.mismatch(new int[] {1}, new int[] {1}));
System.out.println(Arrays.mismatch(new String[] {"a"}, new String[] {"A"}));
System.out.println(Arrays.mismatch(new int[] {1, 2}, new int[] {1}));
```

&emsp;&emsp;
In the first example, the arrays are the same, so the result is -1. In the second example,
the entries at element 0 are not equal, so the result is 0. In the third example, the entries at
element 0 are equal, so we keep looking. The element at index 1 is not equal. Or, more specifically, 
one array has an element at index 1, and the other does not. Therefore, the result is 1. <br />

&emsp;&emsp;
To make sure you understand the compare() and mismatch() methods, study
Table below. If you don’t understand why all of the values are there, please go back and study
this section again.

|Method|When arrays contain the same data| When arrays are different   |
|---|---|-----------------------------|
|equals()|true| false                       |
|compare()|0| Negative or positive number |
|mismatch()|-1| Zero or positive index      |

## 7. Using Methods with Varargs

When you’re creating an array yourself, it looks like what we’ve seen thus far. When one
is passed to your method, there is another way it can look. Here are three examples with a
main() method:

```java
public static void main(String[] args)
public static void main(String args[])
public static void main(String... args) // varargs
```

&emsp;&emsp;
The third example uses a syntax called varargs (variable arguments), which you saw in
Chapter 1. You learn how to call a method using varargs in Chapter 5, “Methods.” For
now, all you need to know is that you can use a variable defined using varargs as if it were a
normal array. For example, args.length and args[0] are legal.

## 8. Working with Multidimensional Arrays

Arrays are objects, and of course, array components can be objects. It doesn’t take much
time, rubbing those two facts together, to wonder whether arrays can hold other arrays, and
of course, they can.

### &emsp;&emsp; a. Creating a Multidimensional Array
Multiple array separators are all it takes to declare arrays with multiple dimensions. You can
locate them with the type or variable name in the declaration, just as before:

```java
int[][] vars1;              // 2D array
int vars2 [][];             // 2D array
int[] vars3[];              // 2D array
int[] vars4 [], space [][]; // a 2D AND a 3D array
```

&emsp;&emsp;
The first two examples are nothing surprising and declare a two-dimensional (2D) array.
The third example also declares a 2D array. There’s no good reason to use this style other
than to confuse readers with your code. The final example declares two arrays on the same
line. Adding up the brackets, we see that the vars4 is a 2D array and space is a 3D array.
Again, there’s no reason to use this style other than to confuse readers of your code. The
exam creators like to try to confuse you, though. Luckily, you are on to them and won’t let
this happen to you! <br />

&emsp;&emsp;
You can specify the size of your multidimensional array in the declaration if you like:

```java
String [][] rectangle = new String[3][2];
```

&emsp;&emsp;
The result of this statement is an array rectangle with three elements, each of which refers
to an array of two elements. You can think of the addressable range as [0][0] through
[2][1], but don’t think of it as a structure of addresses like [0,0] or [2,1]. <br />

&emsp;&emsp;
Now suppose we set one of these values:

```java
rectangle[0][1] = "set";
```

&emsp;&emsp;
This array is sparsely populated
because it has a lot of null values. You can see that rectangle still points to an array of
three elements and that we have three arrays of two elements. You can also follow the trail
from reference to the one value pointing to a String. You start at index 0 in the top array.
Then you go to index 1 in the next array. <br />

&emsp;&emsp;
While that array happens to be rectangular in shape, an array doesn’t need to be. Consider this one:

```java
int[][] differentSizes = {{1, 4}, {3}, {9,8,7}};
```

&emsp;&emsp;
We still start with an array of three elements. However, this time the elements in the next
level are all different sizes. One is of length 2, the next length 1, and the last length 3.
This time the array is of primitives, so they are shown as if they are in the array
themselves. <br />

&emsp;&emsp;
Another way to create an asymmetric array is to initialize just an array’s first dimension
and define the size of each array component in a separate statement:

```java
int [][] args = new int[4][];
args[0] = new int[5];
args[1] = new int[3];
```

&emsp;&emsp;
This technique reveals what you really get with Java: arrays of arrays that, properly
managed, offer a multidimensional effect.
