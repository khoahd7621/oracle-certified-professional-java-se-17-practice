# Declaring Constructors

As you learned in Chapter 1, a constructor is a special method that matches the name of the
class and has no return type. It is called when a new instance of the class is created. For the
exam, you’ll need to know a lot of rules about constructors. In this section, we show how to
create a constructor. Then, we look at default constructors, overloading constructors, calling
parent constructors, final fields, and the order of initialization in a class.

## I. Creating a Constructor

Let’s start with a simple constructor:

```java
public class Bunny {
    public Bunny() {
        System.out.print("hop");
    }
}
```

&emsp;&emsp;
The name of the constructor, Bunny, matches the name of the class, Bunny, and there is
no return type, not even void. That makes this a constructor. Can you tell why these two are
not valid constructors for the Bunny class?

```java
public class Bunny {
    public bunny() {} // DOES NOT COMPILE
    public void Bunny() {}
}
```

&emsp;&emsp;
The first one doesn’t match the class name because Java is case-sensitive. Since it doesn’t
match, Java knows it can’t be a constructor and is supposed to be a regular method. 
However, it is missing the return type and doesn’t compile. The second method is a perfectly good
method but is not a constructor because it has a return type. <br />

&emsp;&emsp;
Like method parameters, constructor parameters can be any valid class, array, or primitive 
type, including generics, but may not include var. For example, the following does
not compile:

```java
public class Bonobo {
    public Bonobo(var food) { // DOES NOT COMPILE
    }
}
```

&emsp;&emsp;
A class can have multiple constructors, as long as each constructor has a unique constructor
signature. In this case, that means the constructor parameters must be distinct. Like
methods with the same name but different signatures, declaring multiple constructors with
different signatures is referred to as constructor overloading. The following Turtle class has
four distinct overloaded constructors:

```java
public class Turtle {
    private String name;
    public Turtle() {
        name = "John Doe";
    }
    public Turtle(int age) {}
    public Turtle(long age) {}
    public Turtle(String newName, String... favoriteFoods) {
        name = newName;
    }
}
```

&emsp;&emsp;
Constructors are used when creating a new object. This process is called instantiation
because it creates a new instance of the class. A constructor is called when we write new 
followed by the name of the class we want to instantiate. Here’s an example:

```java
new Turtle(15);
```

When Java sees the new keyword, it allocates memory for the new object. It then looks
for a constructor with a matching signature and calls it.

## II. The Default Constructor

Every class in Java has a constructor, whether you code one or not. If you don’t include
any constructors in the class, Java will create one for you without any parameters.
This Java-created constructor is called the default constructor and is added any time a class
is declared without any constructors. We often refer to it as the default no-argument 
constructor, for clarity. Here’s an example:

```java
public class Rabbit {
    public static void main(String[] args) {
        new Rabbit(); // Calls the default constructor
    }
}
```

&emsp;&emsp;
In the Rabbit class, Java sees that no constructor was coded and creates one. The
previous class is equivalent to the following, in which the default constructor is provided and
therefore not inserted by the compiler:

```java
public class Rabbit {
    public Rabbit() {}
    public static void main(String[] args) {
        new Rabbit(); // Calls the user-defined constructor
    }
}
```

&emsp;&emsp;
The default constructor has an empty parameter list and an empty body. It is fine for you
to type this in yourself. However, since it doesn’t do anything, Java is happy to generate it
for you and save you some typing. <br/>

&emsp;&emsp;
We keep saying generated. This happens during the compile step. If you look at the file
with the .java extension, the constructor will still be missing. It only makes an appearance in
the compiled file with the .class extension. <br/>

&emsp;&emsp;
For the exam, one of the most important rules you need to know is that the compiler *only
inserts the default constructor when no constructors are defined*. Which of these classes do
you think has a default constructor?

```java
public class Rabbit1 {}
public class Rabbit2 {
    public Rabbit2() {}
}
public class Rabbit3 {
    public Rabbit3(boolean b) {}
}
public class Rabbit4 {
    private Rabbit4() {}
}
```

&emsp;&emsp;
Only Rabbit1 gets a default no-argument constructor. It doesn’t have a constructor
coded, so Java generates a default no-argument constructor. Rabbit2 and Rabbit3
both have public constructors already. Rabbit4 has a private constructor. Since
these three classes have a constructor defined, the default no-argument constructor is not
inserted for you. <br />

&emsp;&emsp;
Let’s take a quick look at how to call these constructors:

```java
1:  public class RabbitsMultiply {
2:      public static void main(String[] args) {
3:          var r1 = new Rabbit1();
4:          var r2 = new Rabbit2();
5:          var r3 = new Rabbit3(true);
6:          var r4 = new Rabbit4(); // DOES NOT COMPILE
7:      } 
8: }
```

&emsp;&emsp;
Line 3 calls the generated default no-argument constructor. Lines 4 and 5 call the 
user-provided constructors. Line 6 does not compile. Rabbit4 made the constructor private so
that other classes could not call it.

> **Note**: <br/>
> Having only private constructors in a class tells the compiler not to
provide a default no-argument constructor. It also prevents other classes
from instantiating the class. This is useful when a class has only static
methods or the developer wants to have full control of all calls to create
new instances of the class.

## III. Calling Overloaded Constructors with this()
Have the basics about creating and referencing constructors? Good, because things are about
to get a bit more complicated. Since a class can contain multiple overloaded constructors,
these constructors can actually call one another. Let’s start with a simple class containing
two overloaded constructors:

```java
public class Hamster {
    private String color;
    private int weight;
    public Hamster(int weight, String color) { // First constructor
        this.weight = weight;
        this.color = color;
    }
    public Hamster(int weight) { // Second constructor
        this.weight = weight;
        color = "brown";
    }
}
```

&emsp;&emsp;
One of the constructors takes a single int parameter. The other takes an int and a
String. These parameter lists are different, so the constructors are successfully overloaded. <br />

&emsp;&emsp;
There is a bit of duplication, as this.weight is assigned the same way in both constructors. 
In programming, even a bit of duplication tends to turn into a lot of duplication as we
keep adding “just one more thing.” For example, imagine that we have five variables being
set like this.weight, rather than just one. What we really want is for the first constructor
to call the second constructor with two parameters. So, how can you have a constructor call
another constructor? You might be tempted to rewrite the first constructor as the following:

```java
public Hamster(int weight) { // Second constructor
    Hamster(weight, "brown"); // DOES NOT COMPILE
}
```

&emsp;&emsp;
This will not work. Constructors can be called only by writing new before the name of
the constructor. They are not like normal methods that you can just call. What happens if we
stick new before the constructor name?

```java
public Hamster(int weight) { // Second constructor
    new Hamster(weight, "brown"); // Compiles, but creates an extra object
}
```

&emsp;&emsp;
This attempt does compile. It doesn’t do what we want, though. When this constructor is
called, it creates a new object with the default weight and color. It then constructs a 
different object with the desired weight and color. In this manner, we end up with two objects,
one of which is discarded after it is created. That’s not what we want. We want weight and
color set on the object we are trying to instantiate in the first place. <br/>

&emsp;&emsp;
Java provides a solution: this() - yes, the same keyword we used to refer to instance
members, but with parentheses. When this() is used with parentheses, Java calls another
constructor on the same instance of the class.

```java
public Hamster(int weight) { // Second constructor
    this(weight, "brown");
}
```

Success! Now Java calls the constructor that takes two parameters, with weight and
color set as expected.

> **this vs. this()**: <br/>
> Despite using the same keyword, this and this() are very different. The first, this,
refers to an instance of the class, while the second, this(), refers to a constructor call
within the class. The exam may try to trick you by using both together, so make sure you
know which one to use and why.

&emsp;&emsp;
Calling this() has one special rule you need to know. If you choose to call it, the this() call
must be the first statement in the constructor. The side effect of this is that there can be only
one call to this() in any constructor.

```java
3:  public Hamster(int weight) {
4:      System.out.println("chew");
5:      // Set weight and default color
6:      this(weight, "brown"); // DOES NOT COMPILE
7:  }
```

&emsp;&emsp;
Even though a print statement on line 4 doesn’t change any variables, it is still a Java
statement and is not allowed to be inserted before the call to this(). The comment on line
5 is just fine. Comments aren’t considered statements and are allowed anywhere. <br/>

&emsp;&emsp;
There’s one last rule for overloaded constructors that you should be aware of. Consider
the following definition of the Gopher class:

```java
public class Gopher {
    public Gopher(int dugHoles) {
        this(5); // DOES NOT COMPILE
    }
}
```

&emsp;&emsp;
The compiler is capable of detecting that this constructor is calling itself infinitely. This is
often referred to as a cycle and is similar to the infinite loops that we discussed in Chapter 3,
“Making Decisions.” Since the code can never terminate, the compiler stops and reports this
as an error. Likewise, this also does not compile:

```java
public class Gopher {
    public Gopher() {
        this(5); // DOES NOT COMPILE
    }
    public Gopher(int dugHoles) {
        this(); // DOES NOT COMPILE
    }
}
```

&emsp;&emsp;
In this example, the constructors call each other, and the process continues infinitely. Since
the compiler can detect this, it reports an error. <br/>

&emsp;&emsp;
Here we summarize the rules you should know about constructors that we covered in this
section. Study them well!

- A class can contain many overloaded constructors, provided the signature for each is distinct.
- The compiler inserts a default no-argument constructor if no constructors are declared.
- If a constructor calls this(), then it must be the first line of the constructor.
- Java does not allow cyclic constructor calls.

## IV. Calling Parent Constructors with *super()*
Congratulations: you’re well on your way to becoming an expert in using constructors!
There’s one more set of rules we need to cover, though, for calling constructors in the parent
class. After all, how do instance members of the parent class get initialized? <br />

&emsp;&emsp;
The first statement of every constructor is a call to a parent constructor using super() or
another constructor in the class using this(). Read the previous sentence twice to make sure
you remember it. It’s really important!

> **Note**: <br/>
> For simplicity in this section, we often refer to super() and this() to
refer to any parent or overloaded constructor call, even those that take
arguments.

&emsp;&emsp;
Let’s take a look at the Animal class and its subclass Zebra and see how their 
constructors can be properly written to call one another:

```java
public class Animal {
    private int age;
    public Animal(int age) {
        super(); // Refers to constructor in java.lang.Object
        this.age = age;
    }
}

public class Zebra extends Animal {
    public Zebra(int age) {
        super(age); // Refers to constructor in Animal
    }
    public Zebra() {
        this(4); // Refers to constructor in Zebra with int argument
    }
}
```

&emsp;&emsp;
In the Animal class, the first statement of the constructor is a call to the parent 
constructor defined in java.lang.Object, which takes no arguments. In the second class,
Zebra, the first statement of the first constructor is a call to Animal’s constructor, which
takes a single argument. The Zebra class also includes a second no-argument constructor
that doesn’t call super() but instead calls the other constructor within the Zebra class
using this(4).

> **super vs. super()**: <br/>
> Like this and this(), super and super() are unrelated in Java. The first, super, is
used to reference members of the parent class, while the second, super(), calls a parent 
constructor. Anytime you see the keyword super on the exam, make sure it is being
used properly

&emsp;&emsp;
Like calling this(), calling super() can only be used as the first statement of the constructor.
For example, the following two class definitions will not compile:

```java
public class Zoo {
    public Zoo() {
        System.out.println("Zoo created");
        super(); // DOES NOT COMPILE
    }
}

public class Zoo {
    public Zoo() {
        super();
        System.out.println("Zoo created");
        super(); // DOES NOT COMPILE
    }
}
```

&emsp;&emsp;
The first class will not compile because the call to the parent constructor must be the first
statement of the constructor. In the second code snippet, super() is the first statement of
the constructor, but it is also used as the third statement. Since super() can only be called
once as the first statement of the constructor, the code will not compile. <br/>

&emsp;&emsp;
If the parent class has more than one constructor, the child class may use any valid parent
constructor in its definition, as shown in the following example:

```java
public class Animal {
    private int age;
    private String name;
    public Animal(int age, String name) {
        super();
        this.age = age;
        this.name = name;
    }
    public Animal(int age) {
        super();
        this.age = age;
        this.name = null;
    }
}
public class Gorilla extends Animal {
    public Gorilla(int age) {
        super(age, "Gorilla"); // Calls the first Animal constructor
    }
    public Gorilla() {
        super(5); // Calls the second Animal constructor
    }
}
```

&emsp;&emsp;
In this example, the first child constructor takes one argument, age, and calls the parent
constructor, which takes two arguments, age and name. The second child constructor takes
no arguments, and it calls the parent constructor, which takes one argument, age. In this
example, notice that the child constructors are not required to call matching parent 
constructors. Any valid parent constructor is acceptable as long as the appropriate input parameters to the parent constructor are provided.

## V. Understanding Compiler Enhancements
Wait a second: we said the first line of every constructor is a call to either this() or super(), but
we’ve been creating classes and constructors throughout this book, and we’ve rarely done
either. How did these classes compile? <br />

&emsp;&emsp;
The answer is that the Java compiler automatically inserts a call to the no-argument 
constructor super() if you do not explicitly call this() or super() as the first line of a constructor.
For example, the following three class and constructor definitions are equivalent, because the
compiler will automatically convert them all to the last example:

```java
public class Donkey {}

public class Donkey {
    public Donkey() {}
}

public class Donkey {
    public Donkey() {
        super();
    }
}
```

Make sure you understand the differences between these three Donkey class definitions
and why Java will automatically convert them all to the last definition. While reading the
next section, keep in mind the process the Java compiler performs.

## VI. Default Constructor Tips and Tricks
We’ve presented a lot of rules so far, and you might have noticed something. Let’s say we
have a class that doesn’t include a no-argument constructor. What happens if we define a
subclass with no constructors, or a subclass with a constructor that doesn’t include a super()
reference?

```java
public class Mammal {
    public Mammal(int age) {}
}

public class Seal extends Mammal {} // DOES NOT COMPILE

public class Elephant extends Mammal {
    public Elephant() {} // DOES NOT COMPILE
}
```

&emsp;&emsp;
The answer is that neither subclass compiles. Since Mammal defines a constructor, the
compiler does not insert a no-argument constructor. The compiler will insert a default 
no-argument constructor into Seal, though, but it will be a simple implementation that just
calls a nonexistent parent default constructor.

```java
public class Seal extends Mammal {
    public Seal() {
        super(); // DOES NOT COMPILE
    }
}
```

&emsp;&emsp;
Likewise, Elephant will not compile for similar reasons. The compiler doesn’t see a call
to super() or this() as the first line of the constructor so it inserts a call to a nonexistent
no-argument super() automatically.

```java
public class Elephant extends Mammal {
    public Elephant() {
        super(); // DOES NOT COMPILE
    }
}
```

&emsp;&emsp;
In these cases, the compiler will not help, and you must create at least one constructor in
your child class that explicitly calls a parent constructor via the super() command.

```java
public class Seal extends Mammal {
    public Seal() {
        super(6); // Explicit call to parent constructor
    }
}

public class Elephant extends Mammal {
    public Elephant() {
        super(4); // Explicit call to parent constructor
    }
}
```

&emsp;&emsp;
Subclasses may include no-argument constructors even if their parent classes do not. For
example, the following compiles because Elephant includes a no-argument constructor:

```java
public class AfricanElephant extends Elephant {}
```

&emsp;&emsp;
It’s a lot to take in, we know. For the exam, you should be able to spot right away why
classes such as our first Seal and Elephant implementations did not compile.

> **super() Always Refers to the Most Direct Parent**: <br/>
> A class may have multiple ancestors via inheritance. In our previous example,
AfricanElephant is a subclass of Elephant, which in turn is a subclass of Mammal. For
constructors, though, super() always refers to the most direct parent. In this example,
calling super() inside the AfricanElephant class always refers to the Elephant class
and never to the Mammal class.

We conclude this section by adding three constructor rules to your skill set:
- The first line of every constructor is a call to a parent constructor using super() or an
  overloaded constructor using this().
- If the constructor does not contain a this() or super() reference, then the compiler
  automatically inserts super() with no arguments as the first line of the constructor.
- If a constructor calls super(), then it must be the first line of the constructor.

Congratulations: you’ve learned everything we can teach you about declaring 
constructors. Next, we move on to initialization and discuss how to use constructors.
