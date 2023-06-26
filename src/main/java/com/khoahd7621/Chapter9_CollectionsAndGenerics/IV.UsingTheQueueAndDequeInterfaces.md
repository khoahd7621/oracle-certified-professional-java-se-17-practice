# Using the Queue and Deque Interfaces

You use a Queue when elements are added and removed in a specific order. You can think of
a queue as a line. For example, when you want to enter a stadium and someone is waiting in
line, you get in line behind that person. And if you are British, you get in the queue behind
that person, making this really easy to remember! This is a FIFO (first-in, first-out) queue. <br />

&emsp;&emsp;
A Deque (double-ended queue), often pronounced “deck,” is different from a regular
queue in that you can insert and remove elements from both the front (head) and back (tail).
Think, “Dr. Woodie Flowers, come right to the front! You are the only one who gets this 
special treatment. Everyone else will have to start at the back of the line.

&emsp;&emsp;
You can envision a double-ended queue as shown in Figure 9.5.

> **Figure 9.5** Example of a Deque
> ```java
> Front (head) --> (Rover)--(Spot)--(Bella) <-- Back (tail)
> ```

&emsp;&emsp;
Supposing we are using this as a FIFO queue. Rover is first, which means he was first to
arrive. Bella is last, which means she was last to arrive and has the longest wait remaining.
All queues have specific requirements for adding and removing the next element. Beyond
that, they each offer different functionality. We look at the implementations you need to
know and the available methods.

## I. Comparing Deque Implementations
You saw LinkedList earlier in the List section. In addition to being a list, it is a Deque.
The main benefit of a LinkedList is that it implements both the List and Deque
interfaces. The trade-off is that it isn’t as efficient as a “pure” queue. You can use the
ArrayDeque class if you don’t need the List methods.

## II. Working with Queue and Deque Methods

The Queue interface contains six methods, shown in Table 9.3. There are three pieces of
functionality and versions of the methods that throw an exception or use the return type,
such as null, for all information. We’ve bolded the ones that throw an exception when
something goes wrong, like trying to read from an empty Queue.

> **Table 9.3** Queue Methods
> 
> | Functionality   | Methods                                                  |
> |:----------------|:---------------------------------------------------------|
> | Add to back     | **public boolean add(E e)**<br />public boolean offer(E e) |
> | Read from front | **public E element()**<br />public E peek()                  |
> |Get and remove from front| **public E remove**()<br />public E poll()                   |

&emsp;&emsp;
Let’s show a simple queue example:

```java
4: Queue<Integer> queue = new LinkedList<>();
5: queue.add(10);
6: queue.add(4);
7: System.out.println(queue.remove());  // 10
8: System.out.println(queue.peek());    // 4
```

&emsp;&emsp;
Lines 5 and 6 add elements to the queue. Line 7 asks the first element waiting the longest
to come off the queue. Line 8 checks for the next entry in the queue while leaving it in place. <br />

&emsp;&emsp;
Next, we move on to the Deque interface. Since the Deque interface supports double-ended 
queues, it inherits all Queue methods and adds more so that it is clear if we are
working with the front or back of the queue. Table 9.4 shows the methods when using it as a
double-ended queue.

> **Table 9.4** Deque Methods
> 
> | Functionality   | Methods |
> |:----------------|:--------|
> |Add to front| public void addFirst(E e)<br />public boolean offerFirst(E e)|
> |Add to back| public void addLast(E e)<br />public boolean offerLast(E e)|
> |Read from front| public E getFirst()<br />public E peekFirst()|
> |Read from back| public E getLast()<br />public E peekLast()|
> |Get and remove from front| public E removeFirst()<br />public E pollFirst()|
> |Get and remove from back| public E removeLast()<br />public E pollLast()|

&emsp;&emsp;
Let’s try an example that works with both ends of the queue:

```java
Deque<Integer> deque = new LinkedList<>();
```

&emsp;&emsp;
This is more complicated, so we use Figure 9.6 to show what the queue looks like at each
step of the code. <br />

&emsp;&emsp;
Lines 13 and 14 successfully add an element to the front and back of the queue, 
respectively. Some queues are limited in size, which would cause offering an element to the queue
to fail. You won’t encounter a scenario like that on the exam. Line 15 looks at the first
element in the queue, but it does not remove it. Lines 16 and 17 remove the elements from
the queue, one from each end. This results in an empty queue. Lines 18 and 19 try to look at
the first element of the queue, which results in null.

> **Figure 9.6** Example of a Deque
> ```java
> 13: deque.offerFirst(10); // true (10)
> 14: deque.offerLast(4);   // true (10)-(4)
> 15: deque.peekFirst();    // 10   (10)-(4)
> 16: deque.pollFirst();    // 10   (4)
> 17: deque.pollLast();     // 4
> 18: deque.pollFirst();    // null
> 19: deque.peekFirst();    // null
> ```

&emsp;&emsp;
In addition to FIFO queues, there are LIFO (last-in, first-out) queues, which are 
commonly referred to as stacks. Picture a stack of plates. You always add to or remove from the
top of the stack to avoid a mess. Luckily, we can use the same double-ended queue 
implementations. Different methods are used for clarity, as shown in Table 9.5.

> **Table 9.5** Using a Deque as a stack
> 
> | Functionality   | Methods |
> |:----------------|:--------|
> |Add to the front/top|public void push(E e)|
> |Remove from the front/top|public E pop()|
> |Get first element|public E peek()|

&emsp;&emsp;
Let’s try another one using the Deque as a stack:

```java
Deque<Integer> stack = new ArrayDeque<>();
```

&emsp;&emsp;
This time, Figure 9.7 shows what the stack looks like at each step of the code. Lines 13
and 14 successfully put an element on the front/top of the stack. The remaining code looks
at the front as well. <br />

&emsp;&emsp;
When using a Deque, it is really important to determine if it is being used as a FIFO
queue, a LIFO stack, or a double-ended queue. To review, a FIFO queue is like a line of 
people. You get on in the back and off in the front. A LIFO stack is like a stack of plates. You
put the plate on the top and take it off the top. A double-ended queue uses both ends.

> **Figure 9.7** Working with a stack
> ```java
> 13: stack.push(10); //    (10)
> 14: stack.push(4);  //    (4)-(10)
> 15: stack.peek(); // 4    (4)-(10)
> 16: stack.poll(); // 4    (10)
> 17: stack.poll(); // 10
> 18: stack.peek(); // null
> ```
