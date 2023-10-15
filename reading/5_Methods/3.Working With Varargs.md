# Working with Varargs

As mentioned in Chapter 4, a method may use a varargs parameter (variable argument) as if
it is an array. Creating a method with a varargs parameter is a bit more complicated. In fact,
calling such a method may not use an array at all.

## I. Creating Methods with Varargs
There are a number of important rules for creating a method with a varargs parameter. <br />
**Rules for Creating a Method with a Varargs Parameter**
1. A method can have at most one varargs parameter.
2. If a method contains a varargs parameter, it must be the last parameter in the list.

&emps;&emps;
Given these rules, can you identify why each of these does or doesn’t compile? (Yes, there
is a lot of practice in this chapter. You have to be really good at identifying valid and invalid
methods for the exam.)

```java
public class VisitAttractions {
    public void walk1(int... steps) {}
    public void walk2(int start, int... steps) {}
    public void walk3(int... steps, int start) {}    // DOES NOT COMPILE
    public void walk4(int... start, int... steps) {} // DOES NOT COMPILE
}
```

## II. Calling Methods with Varargs
When calling a method with a varargs parameter, you have a choice. You can pass in an
array, or you can list the elements of the array and let Java create it for you. Given our
previous walk1() method, which takes a varargs parameter, we can call it one of two ways:

```java
// Pass an array
int[] data = new int[] {1, 2, 3};
walk1(data);

// Pass a list of values
walk1(1, 2, 3);
```

&emps;&emps;
Regardless of which one you use to call the method, the method will receive an array containing the elements. 
We can reinforce this with the following example: 

```java
public void walk1(int... steps) {
    int[] step2 = steps; // Not necessary, but shows steps is of type int[]
    System.out.print(step2.length);
}
```

&emps;&emps;
You can even omit the varargs values in the method call, and Java will create an array of
length zero for you.

```java
walk1();
```

## III. Accessing Elements of a Vararg
Accessing a varargs parameter is just like accessing an array. It uses array indexing. Here’s
an example:

```java
16: public static void run(int... steps) {
17:     System.out.print(steps[1]);
18: }
19: public static void main(String[] args) {
20:     run(11, 77); // 77
21: }
```

&emps;&emps;
Line 20 calls a varargs method with two parameters. When the method is called, it sees an
array of size 2. Since indexes are zero-based, 77 is printed.

## IV. Using Varargs with Other Method Parameters
Finally! You get to do something other than identify whether method declarations are valid.
Instead, you get to look at method calls. Can you figure out why each method call outputs
what it does? For now, feel free to ignore the static modifier in the walkDog() method declaration; 
we cover that later in the chapter

```java
1: public class DogWalker {
2:      public static void walkDog(int start, int... steps) {
3:          System.out.println(steps.length);
4:      }
5:      public static void main(String[] args) {
6:          walkDog(1); // 0
7:          walkDog(1, 2); // 1
8:          walkDog(1, 2, 3); // 2
9:          walkDog(1, new int[] {4, 5}); // 2
10:     } 
11: }
```

&emps;&emps;
Line 6 passes 1 as start but nothing else. This means Java creates an array of length 0
for steps. Line 7 passes 1 as start and one more value. Java converts this one value to
an array of length 1. Line 8 passes 1 as start and two more values. Java converts these
two values to an array of length 2. Line 9 passes 1 as start and an array of length 2
directly as steps. <br />

&emps;&emps;
You’ve seen that Java will create an empty array if no parameters are passed for a vararg.
However, it is still possible to pass null explicitly. The following snippet does compile:

```java
walkDog(1, null); // Triggers NullPointerException in walkDog()
```

&emps;&emps;
Since null isn’t an int, Java treats it as an array reference that happens to be null. It
just passes on the null array object to walkDog(). Then the walkDog() method throws an
exception because it tries to determine the length of null.
