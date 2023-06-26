# Comparing Collection Types

We conclude this section with a review of all the collection classes. Make sure that you can
fill in Table 9.8 to compare the four collection types from memory.

> **Table 9.8** Java Collections Framework types
> 
> |Type|Can contain duplicate elements?|Elements always ordered?|Has keys and values?|Must add/remove in specific order?|
> |---|---|---|---|---|
> |List |Yes |Yes (by index) |No |No|
> |Map |Yes (for values) |No |Yes |No|
> |Queue |Yes |Yes (retrieved in defined order)|No |Yes|
> |Set |No |No |No |No|

&emsp;&emsp;
Additionally, make sure you can fill in Table 9.9 to describe the types on the exam.

> **Table 9.9** Collection attributes
> 
> |Type|Java Collections Framework interface|Sorted?|Calls hashCode?|Calls compareTo?|
> |---|---|---|---|---|
> |ArrayDeque |Deque |No |No |No|
> |ArrayList |List |No |No |No|
> |HashMap |Map |No |Yes |No|
> |HashSet |Set |No |Yes |No|
> |LinkedList |List, Deque |No |No |No|
> |TreeMap |Map |Yes |No |Yes|
> |TreeSet |Set |Yes |No |Yes|

&emsp;&emsp;
Next, the exam expects you to know which data structures allow null values. The data
structures that involve sorting do not allow null values. <br />

&emsp;&emsp;
Finally, the exam expects you to be able to choose the right collection type given a
description of a problem. We recommend first identifying which type of collection the
question is asking about. Figure out whether you are looking for a list, map, queue, or set.
This lets you eliminate a number of answers. Then you can figure out which of the remaining
choices is the best answer.

> ### **Older Collections**
> There are a few collections that are no longer on the exam but that you might come across
in older code. All three were early Java data structures you could use with threads:
> - Vector: Implements List.
> - Hashtable: Implements Map.
> - Stack: Implements Queue.
>
> These classes are rarely used anymore, as there are much better concurrent alternatives
that we cover in Chapter 13.
