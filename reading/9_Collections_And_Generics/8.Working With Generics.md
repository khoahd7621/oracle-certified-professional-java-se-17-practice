# Working with Generics

We conclude this chapter with one of the most useful, and at times most confusing, features
in the Java language: generics. In fact, we’ve been using them extensively in the last two
chapters—the type between the <>. Why do we need generics? Imagine if we weren’t 
specifying the type of our lists and merely hoped the caller didn’t put in something that we didn’t
expect. The following does just that:

```java
14: static void printNames(List list) {
15:     for (int i = 0; i < list.size(); i++) {
16:         String name = (String) list.get(i); // ClassCastException
17:         System.out.println(name);
18:     }
19: }
20: public static void main(String[] args) {
21:     List names = new ArrayList();
22:     names.add(new StringBuilder("Webby"));
23:     printNames(names);
24: }
```

&emsp;&emsp;
This code throws a ClassCastException. Line 22 adds a StringBuilder to list.
This is legal because a non-generic list can contain anything. However, line 16 is written to
expect a specific class to be in there. It casts to a String, reflecting this assumption.
Since the assumption is incorrect, the code throws a ClassCastException that
java.lang.StringBuilder cannot be cast to java.lang.String. <br />

&emsp;&emsp;
Generics fix this by allowing you to write and use parameterized types. Since we specify
that we want an ArrayList of String objects, the compiler has enough information to
prevent this problem in the first place.

```java
List<String> names = new ArrayList<String>();
names.add(new StringBuilder("Webby")); // DOES NOT COMPILE
```

&emsp;&emsp;
Getting a compiler error is good. You’ll know right away that something is wrong rather
than hoping to discover it later.

## I. Creating Generic Classes
You can introduce generics into your own classes. The syntax for introducing a generic is to
declare a formal type parameter in angle brackets. For example, the following class named
Crate has a generic type variable declared after the name of the class:

```java
public class Crate<T> {
    private T contents;
    public T lookInCrate() {
        return contents;
    }
    public void packCrate(T contents) {
        this.contents = contents;
    }
}
```

&emsp;&emsp;
The generic type T is available anywhere within the Crate class. When you instantiate the
class, you tell the compiler what T should be for that particular instance.

> **Naming Conventions for Generics** <br />
> A type parameter can be named anything you want. The convention is to use single 
uppercase letters to make it obvious that they aren’t real class names. The following are common
letters to use:
> - E for an element
> - K for a map key
> - V for a map value
> - N for a number
> - T for a generic data type
> - S, U, V, and so forth for multiple generic types

&emsp;&emsp;
For example, suppose an Elephant class exists, and we are moving our elephant to a new
and larger enclosure in our zoo. (The San Diego Zoo did this in 2009. It was interesting seeing the large metal crate.)

```java
Elephant elephant = new Elephant();
Crate<Elephant> crateForElephant = new Crate<>();
crateForElephant.packCrate(elephant);
Elephant inNewHome = crateForElephant.lookInCrate();
```

&emsp;&emsp;
To be fair, we didn’t pack the crate so much as the elephant walked into it. 
However, you can see that the Crate class is able to deal with an Elephant without knowing
anything about it. <br />

&emsp;&emsp;
This probably doesn’t seem particularly impressive. We could have just typed in Elephant
instead of T when coding Crate. What if we wanted to create a Crate for another animal?

```java
Crate<Zebra> crateForZebra = new Crate<>();
```

&emsp;&emsp;
Now we couldn’t have simply hard-coded Elephant in the Crate class since a Zebra is
not an Elephant. However, we could have created an Animal superclass or interface and
used that in Crate. <br />

&emsp;&emsp;
Generic classes become useful when the classes used as the type parameter can have 
absolutely nothing to do with each other. For example, we need to ship our 120-pound robot to
another city:

```java
Robot joeBot = new Robot();
Crate<Robot> robotCrate = new Crate<>();
robotCrate.packCrate(joeBot);

// ship to Houston
Robot atDestination = robotCrate.lookInCrate();
```

&emsp;&emsp;
Now it is starting to get interesting. The Crate class works with any type of class.
Before generics, we would have needed Crate to use the Object class for its instance 
variable, which would have put the burden on the caller to cast the object it 
receives on emptying the crate. <br />

&emsp;&emsp;
In addition to Crate not needing to know about the objects that go into it, those objects
don’t need to know about Crate. We aren’t requiring the objects to implement an interface
named Crateable or the like. A class can be put in the Crate without any changes at all.

> **Tip**: <br />
> Don’t worry if you can’t think of a use for generic classes of your own.
Unless you are writing a library for others to reuse, generics hardly show
up in the class definitions you write. You’ve already seen them frequently
in the code you call, such as functional interfaces and collections.

&emsp;&emsp;
Generic classes aren’t limited to having a single type parameter. This class shows two
generic parameters:

```java
public class SizeLimitedCrate<T, U> {
    private T contents;
    private U sizeLimit;
    public SizeLimitedCrate(T contents, U sizeLimit) {
        this.contents = contents;
        this.sizeLimit = sizeLimit;
    } 
}
```

&emsp;&emsp;
T represents the type that we are putting in the crate. U represents the unit that we are
using to measure the maximum size for the crate. To use this generic class, we can write the
following:

```java
Elephant elephant = new Elephant();
Integer numPounds = 15_000;
SizeLimitedCrate<Elephant, Integer> c1
    = new SizeLimitedCrate<>(elephant, numPounds);
```

&emsp;&emsp;
Here we specify that the type is Elephant, and the unit is Integer. We also throw in a
reminder that numeric literals can contain underscores.

## II. Understanding Type Erasure
Specifying a generic type allows the compiler to enforce proper use of the generic type. For
example, specifying the generic type of Crate as Robot is like replacing the T in the Crate class
with Robot. However, this is just for compile time. <br />

&emsp;&emsp;
Behind the scenes, the compiler replaces all references to T in Crate with Object. In other
words, after the code compiles, your generics are just Object types. The Crate class looks like
the following at runtime:

```java
public class Crate {
    private Object contents;
    public Object lookInCrate() {
        return contents;
    }
    public void packCrate(Object contents) {
        this.contents = contents;
    }
}
```

&emsp;&emsp;
This means there is only one class file. There aren’t different copies for different 
parameterized types. (Some other languages work that way.) This process of removing the generics
syntax from your code is referred to as type erasure. Type erasure allows your code to be
compatible with older versions of Java that do not contain generics. <br />

&emsp;&emsp;
The compiler adds the relevant casts for your code to work with this type of erased class.
For example, you type the following:

```java
Robot r = crate.lookInCrate();
```

&emsp;&emsp;
The compiler turns it into the following:

```java
Robot r = (Robot) crate.lookInCrate();
```

&emsp;&emsp;
In the following sections, we look at the implications of generics for method
declarations.

### &emsp;&emsp; A. Overloading a Generic Method
Only one of these two methods is allowed in a class because type erasure will reduce both
sets of arguments to (List input):

```java
public class LongTailAnimal {
    protected void chew(List<Object> input) {}
    protected void chew(List<Double> input) {} // DOES NOT COMPILE
}
```

&emsp;&emsp;
For the same reason, you also can’t overload a generic method from a parent class.

```java
public class LongTailAnimal {
    protected void chew(List<Object> input) {}
}

public class Anteater extends LongTailAnimal {
    protected void chew(List<Double> input) {} // DOES NOT COMPILE
}
```

&emsp;&emsp;
Both of these examples fail to compile because of type erasure. In the compiled form, the
generic type is dropped, and it appears as an invalid overloaded method. Now, let’s look at
a subclass:

```java
public class Anteater extends LongTailAnimal {
    protected void chew(List<Object> input) {}
    protected void chew(ArrayList<Double> input) {}
}
```

&emsp;&emsp;
The first chew() method compiles because it uses the same generic type in the overridden
method as the one defined in the parent class. The second chew() method compiles as well.
However, it is an overloaded method because one of the method arguments is a List and
the other is an ArrayList. When working with generic methods, it’s important to consider
the underlying type.

### &emsp;&emsp; B. Returning Generic Types
When you’re working with overridden methods that return generics, the return values must
be covariant. In terms of generics, this means that the return type of the class or interface
declared in the overriding method must be a subtype of the class defined in the parent class.
The generic parameter type must match its parent’s type exactly. <br />

&emsp;&emsp;
Given the following declaration for the Mammal class, which of the two subclasses,
Monkey and Goat, compile?

```java
public class Mammal {
    public List<CharSequence> play() { ... }
    public CharSequence sleep() { ... }
}

public class Monkey extends Mammal {
    public ArrayList<CharSequence> play() { ... }
}

public class Goat extends Mammal {
    public List<String> play() { ... } // DOES NOT COMPILE
    public String sleep() { ... }
}
```

&emsp;&emsp;
The Monkey class compiles because ArrayList is a subtype of List. The play() method
in the Goat class does not compile, though. For the return types to be covariant, the generic
type parameter must match. Even though String is a subtype of CharSequence, it does not
exactly match the generic type defined in the Mammal class. Therefore, this is considered an
invalid override. <br />

&emsp;&emsp;
Notice that the sleep() method in the Goat class does compile since String is a subtype of
CharSequence. This example shows that covariance applies to the return type, just not the
generic parameter type. <br />

&emsp;&emsp;
For the exam, it might be helpful for you to apply type erasure to questions involving
generics to ensure that they compile properly. Once you’ve determined which methods are
overridden and which are being overloaded, work backward, making sure the generic types
match for overridden methods. And remember, generic methods cannot be overloaded by
changing the generic parameter type only.

## III. Implementing Generic Interfaces
Just like a class, an interface can declare a formal type parameter. For example, the following
Shippable interface uses a generic type as the argument to its ship() method:

```java
public interface Shippable<T> {
    void ship(T t);
}
```

&emsp;&emsp;
There are three ways a class can approach implementing this interface. The first is to
specify the generic type in the class. The following concrete class says that it deals only with
robots. This lets it declare the ship() method with a Robot parameter:

```java
class ShippableRobotCrate implements Shippable<Robot> {
    public void ship(Robot t) { }
}
```

&emsp;&emsp;
The next way is to create a generic class. The following concrete class allows the caller to
specify the type of the generic:

```java
class ShippableAbstractCrate<U> implements Shippable<U> {
    public void ship(U t) { }
}
```

&emsp;&emsp;
In this example, the type parameter could have been named anything, including T. We
used U in the example to avoid confusion about what T refers to. The exam won’t mind 
trying to confuse you by using the same type parameter name. <br />

&emsp;&emsp;
The final way is to not use generics at all. This is the old way of writing code. It generates
a compiler warning about Shippable being a raw type, but it does compile. Here the ship()
method has an Object parameter since the generic type is not defined:

```java
class ShippableCrate implements Shippable {
    public void ship(Object t) { }
}
```

> ### **What You Can’t Do with Generic Types**
> There are some limitations on what you can do with a generic type. These aren’t on the
exam, but it will be helpful to refer to this scenario when you are writing practice programs
and run into one of these situations. <br />
> Most of the limitations are due to type erasure. Oracle refers to types whose information is
fully available at runtime as reifiable. Reifiable types can do anything that Java allows. 
Non-reifiable types have some limitations. <br />
> Here are the things that you can’t do with generics (and by “can’t,” we mean without 
resorting to contortions like passing in a class object):
> - **Call a constructor**: Writing new T() is not allowed because at runtime, it would be
    new Object().
> - **Create an array of that generic type**: This one is the most annoying, but it makes sense
    because you’d be creating an array of Object values.
> - **Call instanceof**: This is not allowed because at runtime List<Integer> and
    List<String> look the same to Java, thanks to type erasure.
> - **Use a primitive type as a generic type parameter**: This isn’t a big deal because you can
    use the wrapper class instead. If you want a type of int, just use Integer.
> - **Create a static variable as a generic type parameter**: This is not allowed because the
    type is linked to the instance of the class.

## IV. Writing Generic Methods

Up until this point, you’ve seen formal type parameters declared on the class or interface
level. It is also possible to declare them on the method level. This is often useful for static
methods since they aren’t part of an instance that can declare the type. However, it is also
allowed on non-static methods. <br />

&emsp;&emsp;
In this example, both methods use a generic parameter:

```java
public class Handler {
    public static <T> void prepare(T t) {
        System.out.println("Preparing " + t);
    }
    public static <T> Crate<T> ship(T t) {
        System.out.println("Shipping " + t);
        return new Crate<T>();
    }
}
```

&emsp;&emsp;
The method parameter is the generic type T. Before the return type, we declare the formal
type parameter of <T>. In the ship() method, we show how you can use the generic 
parameter in the return type, Crate<T>, for the method. <br />

&emsp;&emsp;
Unless a method is obtaining the generic formal type parameter from the 
class/interface, it is specified immediately before the return type of the method. This can lead to some
interesting-looking code!

```java
2:  public class More {
3:      public static <T> void sink(T t) { }
4:      public static <T> T identity(T t) { return t; }
5:      public static T noGood(T t) { return t; } // DOES NOT COMPILE
6: }
```

&emsp;&emsp;
Line 3 shows the formal parameter type immediately before the return type of void. Line
4 shows the return type being the formal parameter type. It looks weird, but it is correct.
Line 5 omits the formal parameter type and therefore does not compile.

> ### **Optional Syntax for Invoking a Generic Method**
> You can call a generic method normally, and the compiler will try to figure out which
one you want. Alternatively, you can specify the type explicitly to make it obvious what
the type is. <br />
> ```java
> Box.<String>ship("package");
> Box.<String[]>ship(args);
> ```
> It is up to you whether this makes things clearer. You should at least be aware that this
syntax exists.

&emsp;&emsp;
When you have a method declare a generic parameter type, it is independent of the class
generics. Take a look at this class that declares a generic T at both levels:

```java
1:  public class TrickyCrate<T> {
2:      public <T> T tricky(T t) {
3:          return t;
4:      }
5: }
```

&emsp;&emsp;
See if you can figure out the type of T on lines 1 and 2 when we call the code as follows:

```java
10: public static String crateName() {
11:     TrickyCrate<Robot> crate = new TrickyCrate<>();
12:     return crate.tricky("bot");
13: }
```

&emsp;&emsp;
Clearly, “T is for tricky.” Let’s see what is happening. On line 1, T is Robot because that
is what gets referenced when constructing a Crate. On line 2, T is String because that is
what is passed to the method. When you see code like this, take a deep breath and write
down what is happening so you don’t get confused.

## V. Creating a Generic Record
Generics can also be used with records. This record takes a single generic type parameter:

```java
public record CrateRecord<T>(T contents) {
    @Override
    public T contents() {
        if (contents == null)
            throw new IllegalStateException("missing contents");
        return contents; 
    }
}
```

&emsp;&emsp;
This works the same way as classes. You can create a record of the robot!

```java
Robot robot = new Robot();
CrateRecord<Robot> record = new CrateRecord<>(robot);
```

This is convenient. Now we have an immutable, generic record!

## VI. Bounding Generic Types
By now, you might have noticed that generics don’t seem particularly useful since they are
treated as Objects and, therefore, don’t have many methods available. Bounded wildcards
solve this by restricting what types can be used in a wildcard. A bounded parameter type is a
generic type that specifies a bound for the generic. Be warned that this is the hardest section
in the chapter, so don’t feel bad if you have to read it more than once. <br />

&emsp;&emsp;
A wildcard generic type is an unknown generic type represented with a question mark (?).
You can use generic wildcards in three ways, as shown in Table 9.13. This section looks at
each of these three wildcard types.

> **Table 9.13** Types of bounds
> 
> |Type of bound |Syntax |Example|
> |:---|:---|:---|
> |Unbounded wildcard |? |List<?> a = new ArrayList<String>();|
> |Wildcard with upper bound |? extends type |List<? extends Exception> a = new ArrayList<RuntimeException>();|
> |Wildcard with lower bound |? super type |List<? super Exception> a = new ArrayList<Object>();|

### &emsp;&emsp; A. Creating Unbounded Wildcards
An unbounded wildcard represents any data type. You use ? when you want to specify that
any type is okay with you. Let’s suppose that we want to write a method that looks through
a list of any type. 

```java
public static void printList(List<Object> list) {
    for (Object x: list)
    System.out.println(x);
}
public static void main(String[] args) {
    List<String> keywords = new ArrayList<>();
    keywords.add("java");
    printList(keywords); // DOES NOT COMPILE
}
```

&emsp;&emsp;
Wait. What’s wrong? A String is a subclass of an Object. This is true. However,
List<String> cannot be assigned to List<Object>. We know, it doesn’t sound logical.
Java is trying to protect us from ourselves with this one. Imagine if we could write code
like this:

```java
4: List<Integer> numbers = new ArrayList<>();
5: numbers.add(Integer.valueOf(42));
6: List<Object> objects = numbers; // DOES NOT COMPILE
7: objects.add("forty two");
8: System.out.println(numbers.get(1));
```

&emsp;&emsp;
On line 4, the compiler promises us that only Integer objects will appear in numbers.
If line 6 compiled, line 7 would break that promise by putting a String in there since
numbers and objects are references to the same object. Good thing the compiler prevents this. <br />

&emsp;&emsp;
Going back to printing a list, we cannot assign a List<String> to a List<Object>.
That’s fine; we don’t need a List<Object>. What we really need is a List of “whatever.”
That’s what List<?> is. The following code does what we expect:

```java
public static void printList(List<?> list) {
    for (Object x: list)
    System.out.println(x);
}
public static void main(String[] args) {
    List<String> keywords = new ArrayList<>();
    keywords.add("java");
    printList(keywords);
}
```

&emsp;&emsp;
The printList() method takes any type of list as a parameter. The keywords 
variable is of type List<String>. We have a match! List<String> is a list of anything.
“Anything” just happens to be a String here. <br />

&emsp;&emsp;
Finally, let’s look at the impact of var. Do you think these two statements are
equivalent?

```java
List<?> x1 = new ArrayList<>();
var x2 = new ArrayList<>();
```

&emsp;&emsp;
They are not. There are two key differences. First, x1 is of type List, while x2 is of type
ArrayList. Additionally, we can only assign x2 to a List<Object>. These two variables
do have one thing in common. Both return type Object when calling the get() method.

### &emsp;&emsp; B. Creating Upper-Bounded Wildcards

Let’s try to write a method that adds up the total of a list of numbers. We’ve established that
a generic type can’t just use a subclass.

```java
ArrayList<Number> list = new ArrayList<Integer>(); // DOES NOT COMPILE
```

&emsp;&emsp;
Instead, we need to use a wildcard:

```java
List<? extends Number> list = new ArrayList<Integer>();
```

&emsp;&emsp;
The upper-bounded wildcard says that any class that extends Number or Number itself
can be used as the formal parameter type:

```java
public static long total(List<? extends Number> list) {
    long count = 0;
    for (Number number: list)
        count += number.longValue();
    return count;
}
```

&emsp;&emsp;
Remember how we kept saying that type erasure makes Java think that a generic type
is an Object? That is still happening here. Java converts the previous code to something
equivalent to the following:

```java
public static long total(List list) {
    long count = 0;
    for (Object obj: list) {
        Number number = (Number) obj;
        count += number.longValue();
    }
    return count;
}
```

&emsp;&emsp;
Something interesting happens when we work with upper bounds or unbounded 
wildcards. The list becomes logically immutable and therefore cannot be modified. Technically,
you can remove elements from the list, but the exam won’t ask about this.

```java
2:  static class Sparrow extends Bird { }
3:  static class Bird { }
4:
5:  public static void main(String[] args) {
6:      List<? extends Bird> birds = new ArrayList<Bird>();
7:      birds.add(new Sparrow()); // DOES NOT COMPILE
8:      birds.add(new Bird()); // DOES NOT COMPILE
9:  }
```

&emsp;&emsp;
The problem stems from the fact that Java doesn’t know what type List<? extends
Bird> really is. It could be List<Bird> or List<Sparrow> or some other generic type
that hasn’t even been written yet. Line 7 doesn’t compile because we can’t add a Sparrow
to List<? extends Bird>, and line 8 doesn’t compile because we can’t add a Bird to
List<Sparrow>. From Java’s point of view, both scenarios are equally possible, so neither
is allowed. <br />

&emsp;&emsp;
Now let’s try an example with an interface. We have an interface and two classes that
implement it.

```java
interface Flyer { void fly(); }
class HangGlider implements Flyer { public void fly() {} }
class Goose implements Flyer { public void fly() {} }
```

&emsp;&emsp;
We also have two methods that use it. One just lists the interface, and the other uses an
upper bound.

```java
private void anyFlyer(List<Flyer> flyer) {}
private void groupOfFlyers(List<? extends Flyer> flyer) {}
```

&emsp;&emsp;
Note that we used the keyword extends rather than implements. Upper bounds are
like anonymous classes in that they use extends regardless of whether we are working with
a class or an interface. <br />

&emsp;&emsp;
You already learned that a variable of type List<Flyer> can be passed to either method.
A variable of type List<Goose> can be passed only to the one with the upper bound. This
shows a benefit of generics. Random flyers don’t fly together. We want our groupOfFlyers()
method to be called only with the same type. Geese fly together but don’t fly with hang gliders.

### &emsp;&emsp; C. Creating Lower-Bounded Wildcards
Let’s try to write a method that adds a string "quack" to two lists:

```java
List<String> strings = new ArrayList<String>();
strings.add("tweet");

List<Object> objects = new ArrayList<Object>(strings);
addSound(strings);
addSound(objects);
```

&emsp;&emsp;
The problem is that we want to pass a List<String> and a List<Object> to the same
method. First, make sure you understand why the first three examples in Table 9.14 do not
solve this problem.

> **Table 9.14**: Why we need a lower bound
> 
> |static void addSound(____ list) {list.add("quack");}|Method compiles|Can pass a List<String>|Can pass a List<Object>|
> |:---|:---|:---|:---|
> |List<?>|No (unbounded generics are immutable)|Yes|Yes|
> |List<? extends Object>|No (upper-bounded generics are immutable)|Yes|Yes|
> |List<Object>|Yes|No (with generics, must pass exact match)|Yes|
> |List<? super String>|Yes|Yes|Yes|

&emsp;&emsp;
To solve this problem, we need to use a lower bound.

```java
public static void addSound(List<? super String> list) {
    list.add("quack");
}
```

&emsp;&emsp;
With a lower bound, we are telling Java that the list will be a list of String objects or a
list of some objects that are a superclass of String. Either way, it is safe to add a String to
that list. <br />

&emsp;&emsp;
Just like generic classes, you probably won’t use this in your code unless you are writing
code for others to reuse. Even then, it would be rare. But it’s on the exam, so now is the time
to learn it!

> ### **Understanding Generic Supertypes**
> When you have subclasses and superclasses, lower bounds can get tricky.
> ```java
> 3:    List<? super IOException> exceptions = new ArrayList<Exception>();
> 4:    exceptions.add(new Exception()); // DOES NOT COMPILE
> 5:    exceptions.add(new IOException());
> 6:    exceptions.add(new FileNotFoundException());
> ```
> Line 3 references a List that could be List&lt;IOException&gt; or List&lt;Exception&gt; or
List&lt;Object&gt;. Line 4 does not compile because we could have a List&lt;IOException&gt;,
and an Exception object wouldn’t fit in there. <br />
> Line 5 is fine. IOException can be added to any of those types. Line 6 is also fine.
FileNotFoundException can also be added to any of those three types. This is tricky
because FileNotFoundException is a subclass of IOException, and the keyword says
super. Java says, “Well, FileNotFoundException also happens to be an IOException,
so everything is fine.”

## VII. Putting It All Together
At this point, you know everything that you need to know to ace the exam questions on
generics. It is possible to put these concepts together to write some really confusing code,
which the exam likes to do. <br />

&emsp;&emsp;
This section is going to be difficult to read. It contains the hardest questions that you
could possibly be asked about generics. The exam questions will probably be easier to read
than these. We want you to encounter the really tough ones here so that you are ready
for the exam. In other words, don’t panic. Take it slow, and reread the code a few times.
You’ll get it.

### &emsp;&emsp; A. Combining Generic Declarations
Let’s try an example. First, we declare three classes that the example will use:

```java
class A {}
class B extends A {}
class C extends B {}
```

&emsp;&emsp;
Ready? Can you figure out why these do or don’t compile? Also, try to figure out
what they do.

```java
6:  List<?> list1 = new ArrayList<A>();
7:  List<? extends A> list2 = new ArrayList<A>();
8:  List<? super A> list3 = new ArrayList<A>();
```

&emsp;&emsp;
Line 6 creates an ArrayList that can hold instances of class A. It is stored in a variable
with an unbounded wildcard. Any generic type can be referenced from an unbounded 
wildcard, making this okay. <br />

&emsp;&emsp;
Line 7 tries to store a list in a variable declaration with an upper-bounded wildcard.
This is okay. You can have ArrayList&lt;A&gt;, ArrayList&lt;B&gt;, or ArrayList&lt;C&gt; stored
in that reference. Line 8 is also okay. This time, you have a lower-bounded wildcard. The
lowest type you can reference is A. Since that is what you have, it compiles. <br />

&emsp;&emsp;
Did you get those right? Let’s try a few more.

```java
9:  List<? extends B> list4 = new ArrayList<A>(); // DOES NOT COMPILE
10: List<? super B> list5 = new ArrayList<A>();
11: List<?> list6 = new ArrayList<? extends A>(); // DOES NOT COMPILE
```

&emsp;&emsp;
Line 9 has an upper-bounded wildcard that allows ArrayList&lt;B&gt; or ArrayList&lt;C&gt;
to be referenced. Since you have ArrayList&lt;A&gt; that is trying to be referenced, the code
does not compile. Line 10 has a lower-bounded wildcard, which allows a reference to
ArrayList&lt;A&gt;, ArrayList&lt;B&gt;, or ArrayList&lt;Object&gt;. <br />

&emsp;&emsp;
Finally, line 11 allows a reference to any generic type since it is an unbounded wildcard. The
problem is that you need to know what that type will be when instantiating the ArrayList.
It wouldn’t be useful anyway, because you can’t add any elements to that ArrayList.

### &emsp;&emsp; B. Passing Generic Arguments
Now on to the methods. Same question: try to figure out why they don’t compile or what
they do. We will present the methods one at a time because there is more to think about.

```java
<T> T first(List<? extends T> list) {
    return list.get(0);
}
```

&emsp;&emsp;
The first method, first(), is a perfectly normal use of generics. It uses a method-specific
type parameter, T. It takes a parameter of List&lt;T>, or some subclass of T, and it returns a
single object of that T type. For example, you could call it with a List&lt;String> parameter
and have it return a String. Or you could call it with a List&lt;Number> parameter and have
it return a Number. Or well, you get the idea. <br />

&emsp;&emsp;
Given that, you should be able to see what is wrong with this one:

```java
<T> <? extends T> second(List<? extends T> list) { // DOES NOT COMPILE
    return list.get(0);
}
```

&emsp;&emsp;
The next method, second(), does not compile because the return type isn’t actually a
type. You are writing the method. You know what type it is supposed to return. You don’t
get to specify this as a wildcard. <br />

&emsp;&emsp;
Now be careful—this one is extra tricky:

```java
<B extends A> B third(List<B> list) {
    return new B(); // DOES NOT COMPILE
}
```

&emsp;&emsp;
This method, third(), does not compile. &lt;B extends A> says that you want to use B
as a type parameter just for this method and that it needs to extend the A class. 
Coincidentally, B is also the name of a class. Well, it isn’t a coincidence. It’s an evil trick. Within the
scope of the method, B can represent class A, B, or C, because all extend the A class. Since B
no longer refers to the B class in the method, you can’t instantiate it. <br />

&emsp;&emsp;
After that, it would be nice to get something straightforward.

```java
void fourth(List<? super B> list) {}
```

&emsp;&emsp;
We finally get a method, fourth(), that is a normal use of generics. You can pass the
type List&lt;B>, List&lt;A>, or List&lt;Object>. <br />

&emsp;&emsp;
Finally, can you figure out why this example does not compile?

```java
<X> void fifth(List<X super B> list) { // DOES NOT COMPILE
}
```

&emsp;&emsp;
This last method, fifth(), does not compile because it tries to mix a method-specific
type parameter with a wildcard. A wildcard must have a ? in it. <br />

&emsp;&emsp;
Phew. You made it through generics. It’s the hardest topic in this chapter (and why we
covered it last!). Remember that it’s okay if you need to go over this material a few times to
get your head around it.
