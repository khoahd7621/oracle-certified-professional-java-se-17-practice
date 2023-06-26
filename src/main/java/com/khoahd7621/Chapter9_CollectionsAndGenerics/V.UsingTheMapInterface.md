# Using the Map Interface

You use a Map when you want to identify values by a key. For example, when you use the
contact list in your phone, you look up “George” rather than looking through each phone
number in turn. <br />

&emsp;&emsp;
You can envision a Map as shown in Figure 9.8. You don’t need to know the names of the
specific interfaces that the different maps implement, but you do need to know that TreeMap
is sorted.

> **Figure 9.8**: Example of a Map
>
> |Key|Value|
> |---|-----|
> |George|555-555-5555|
> |May|777-777-7777|

&emsp;&emsp;
The main thing that all Map classes have in common is that they have keys and values.
Beyond that, they each offer different functionality. We look at the implementations you
need to know and the available methods.

> ### **Map.of() and Map.copyOf()**
> Just like List and Set, there is a factory method to create a Map. You pass any number of
pairs of keys and values.
> ```java
> Map.of("key1", "value1", "key2", "value2");
> ```
> Unlike List and Set, this is less than ideal. Passing keys and values is harder to read
because you have to keep track of which parameter is which. Luckily, there is a better way.
Map also provides a method that lets you supply key/value pairs.
> ```java
>   Map.ofEntries(
>       Map.entry("key1", "value1"),
>       Map.entry("key2", "value2"));
> ```
> Now we can’t forget to pass a value. If we leave out a parameter, the entry() method
won’t compile. Conveniently, Map.copyOf(map) works just like the List and Set interface copyOf() methods.

## I. Comparing *Map* Implementations

&emsp;&emsp;
A HashMap stores the keys in a hash table. This means that it uses the hashCode() method of
the keys to retrieve their values more efficiently. <br />

&emsp;&emsp;
The main benefit is that adding elements and retrieving the element by key both have
constant time. The trade-off is that you lose the order in which you inserted the elements.
Most of the time, you aren’t concerned with this in a map anyway. If you were, you could
use LinkedHashMap, but that’s not in scope for the exam. <br />

&emsp;&emsp;
A TreeMap stores the keys in a sorted tree structure. The main benefit is that the keys are
always in sorted order. Like a TreeSet, the trade-off is that adding and checking whether a
key is present takes longer as the tree grows larger.

## II. Working with *Map* Methods
Given that Map doesn’t extend Collection, more methods are specified on the Map interface.
Since there are both keys and values, we need generic type parameters for both. The class
uses K for key and V for value. The methods you need to know for the exam are in Table 9.6.
Some of the method signatures are simplified to make them easier to understand.

> **Table 9.6**: Map Methods
> 
> | Method                                                  |Description|
> |---------------------------------------------------------|-----------|
> | `public void clear()`                                   |Removes all keys and values from map.|
> | `public boolean containsKey(Object key)`                |Returns whether key is in the map.|
> | `public boolean containsValue(Object value)`            |Returns whether value is in the map.|
> | `public Set<Map.Entry<K, V>> entrySet()`                |Returns a Set of all key/value pairs.|
> | `public void forEach(BiConsumer<K key, V value>)`       |Loops through each key/value pair.|
> | `public V get(Object key)`                              |Returns value mapped by key or null if none is maped.|
> | `public V getOrDefault(Object key, V defaultValue)`     |Returns value mapped by key or defaultValue if none is mapped.|
> | `public boolean isEmpty()`                              |Returns whether the map is empty.|
> | `public Set<K> keySet()`                                |Returns a Set of all keys.|
> | `public V merge(K key, V value, Function(<V, V, V> func))` |Sets value if key not set. Runs function if key is set, to determine new value. Removes if value is null.|
> | `public V put(K key, V value)`                          |Adds or replaces key/value pair. Returns previous value or null if none.|
> |`public void putIfAbsent(K key, V value)`                |Adds value if key not present and returns null. Otherwise, returns existing value.|
> |`public V remove(Object key)`                            |Removes and returns value mapped to key. Returns null if none.|
> |`public V replace (K key, V value)`                      |Replaces value for given key if key is set. Returns original value or null if none.|
> |`public void replaceAll(BiFunction<K, V, V> func)`       |Replaces each value with results of function.|
> |`public int size()`                                      |Returns number of entries (key/value pairs) in map.|
> |`public Collection<V> values()`                          |Returns a Collection of all values.|

&emsp;&emsp;
While Table 9.6 is a pretty long list of methods, don’t worry; many of the names are
straightforward. Also, many exist as a convenience. For example, containsKey() can be
replaced with a get() call that checks if the result is null. Which one you use is up to you.

## III. Calling Basic Methods
Let’s start out by comparing the same code with two Map types. First up is HashMap:

```java
Map<String, String> map = new HashMap<>();
map.put("koala", "bamboo");
map.put("lion", "meat");
map.put("giraffe", "leaf");
String food = map.get("koala"); // bamboo
for (String key: map.keySet())
    System.out.print(key + ","); // koala,giraffe,lion,
```

&emsp;&emsp;
Here we use the put() method to add key/value pairs to the map and get() to get a
value given a key. We also use the keySet() method to get all the keys. <br />

&emsp;&emsp;
Java uses the hashCode() of the key to determine the order. The order here happens not
to be sorted order or the order in which we typed the values. Now let’s look at TreeMap:

```java
Map<String, String> map = new TreeMap<>();
map.put("koala", "bamboo");
map.put("lion", "meat");
map.put("giraffe", "leaf");
String food = map.get("koala"); // bamboo
for (String key: map.keySet())
    System.out.print(key + ","); // giraffe,koala,lion,
```

&emsp;&emsp;
TreeMap sorts the keys as we would expect. If we called values() instead of keySet(),
the order of the values would correspond to the order of the keys. <br />

&emsp;&emsp;
With our same map, we can try some boolean checks:

```java
System.out.println(map.contains("lion"));       // DOES NOT COMPILE
System.out.println(map.containsKey("lion"));    // true
System.out.println(map.containsValue("lion"));  // false
System.out.println(map.size()); // 3
map.clear();
System.out.println(map.size()); // 0
System.out.println(map.isEmpty()); // true
```

&emsp;&emsp;
The first line is a little tricky. The contains() method is on the Collection interface
but not the Map interface. The next two lines show that keys and values are checked 
separately. We can see that there are three key/value pairs in our map. Then we clear out 
the contents of the map and see that there are zero elements and it is empty. <br />

&emsp;&emsp;
In the following sections, we show Map methods you might not be as familiar with.

## IV. Iterating through a Map
You saw the forEach() method earlier in the chapter. Note that it works a little differently on
a Map. This time, the lambda used by the forEach() method has two parameters: the key and
the value. Let’s look at an example, shown here:

```java
Map<Integer, Character> map = new HashMap<>();
map.put(1, 'a');
map.put(2, 'b');
map.put(3, 'c');
map.forEach((k, v) -> System.out.println(v));
```

&emsp;&emsp;
The lambda has both the key and value as the parameters. It happens to print out the
value but could do anything with the key and/or value. Interestingly, since we don’t care
about the key, this particular code could have been written with the values() method and
a method reference instead.

```java
map.values().forEach(System.out::println);
```

&emsp;&emsp;
Another way of going through all the data in a map is to get the key/value pairs in a Set.
Java has a static interface inside Map called Entry. It provides methods to get the key and
value of each pair.

```java
map.entrySet().forEach(e ->
    System.out.println(e.getKey() + " " + e.getValue()));
```

## V. Getting Values Safely
The get() method returns null if the requested key is not in the map. Sometimes you
prefer to have a different value returned. Luckily, the getOrDefault() method makes this
easy. Let’s compare the two methods:

```java
3: Map<Character, String> map = new HashMap<>();
4: map.put('x', "spot");
5: System.out.println("X marks the " + map.get('x'));
6: System.out.println("X marks the " + map.getOrDefault('x', ""));
7: System.out.println("Y marks the " + map.get('y'));
8: System.out.println("Y marks the " + map.getOrDefault('y', ""));
```

&emsp;&emsp;
This code prints the following:

```java
X marks the spot
X marks the spot
Y marks the null
Y marks the
```

&emsp;&emsp;
As you can see, lines 5 and 6 have the same output because get() and getOrDefault()
behave the same way when the key is present. They return the value mapped by that key.
Lines 7 and 8 give different output, showing that get() returns null when the key is not
present. By contrast, getOrDefault() returns the empty string we passed as a parameter.

## VI. Replacing Values
These methods are similar to the List version, except a key is involved:

```java
21: Map<Integer, Integer> map = new HashMap<>();
22: map.put(1, 2);
23: map.put(2, 4);
24: Integer original = map.replace(2, 10); // 4
25: System.out.println(map); // {1=2, 2=10}
26: map.replaceAll((k, v) -> k + v);
27: System.out.println(map); // {1=3, 2=12}
```

&emsp;&emsp;
Line 24 replaces the value for key 2 and returns the original value. Line 26 calls a
function and sets the value of each element of the map to the result of that function. In our
case, we added the key and value together.

## VII. Putting if Absent
The putIfAbsent() method sets a value in the map but skips it if the value is already set to a
non-null value.

```java
Map<String, String> favorites = new HashMap<>();
favorites.put("Jenny", "Bus Tour");
favorites.put("Tom", null);
favorites.putIfAbsent("Jenny", "Tram");
favorites.putIfAbsent("Sam", "Tram");
favorites.putIfAbsent("Tom", "Tram");
System.out.println(favorites); // {Tom=Tram, Jenny=Bus Tour, Sam=Tram}
```

&emsp;&emsp;
As you can see, Jenny’s value is not updated because one was already present. Sam
wasn’t there at all, so he was added. Tom was present as a key but had a null value. Therefore, he was added as well.

## VIII. Merging Data
The merge() method adds logic of what to choose. Suppose we want to choose the ride with
the longest name. We can write code to express this by passing a mapping function to the
merge() method:

```java
11: BiFunction<String, String, String> mapper = (v1, v2)
12:     -> v1.length() > v2.length() ? v1 : v2;
13:
14: Map<String, String> favorites = new HashMap<>();
15: favorites.put("Jenny", "Bus Tour");
16: favorites.put("Tom", "Tram");
17:
18: String jenny = favorites.merge("Jenny", "Skyride", mapper);
19: String tom = favorites.merge("Tom", "Skyride", mapper);
20:
21: System.out.println(favorites);  // {Tom=Skyride, Jenny=Bus Tour}
22: System.out.println(jenny);      // Bus Tour
23: System.out.println(tom);        // Skyride
```

&emsp;&emsp;
The code on lines 11 and 12 takes two parameters and returns a value. Our implementation 
returns the one with the longest name. Line 18 calls this mapping function, and it sees
that Bus Tour is longer than Skyride, so it leaves the value as Bus Tour. Line 19 calls this
mapping function again. This time, Tram is shorter than Skyride, so the map is updated.
Line 21 prints out the new map contents. Lines 22 and 23 show that the result is returned
from merge(). <br />

&emsp;&emsp;
The merge() method also has logic for what happens if null values or missing keys are
involved. In this case, it doesn’t call the BiFunction at all, and it simply uses the new value.

```java
BiFunction<String, String, String> mapper =
    (v1, v2) -> v1.length()> v2.length() ? v1 : v2;
Map<String, String> favorites = new HashMap<>();
favorites.put("Sam", null);
favorites.merge("Tom", "Skyride", mapper);
favorites.merge("Sam", "Skyride", mapper);
System.out.println(favorites);  // {Tom=Skyride, Sam=Skyride}
```

&emsp;&emsp;
Notice that the mapping function isn’t called. If it were, we’d have a
NullPointerException. The mapping function is used only when there are two actual
values to decide between. <br />

&emsp;&emsp;
The final thing to know about merge() is what happens when the mapping function is
called and returns null. The key is removed from the map when this happens:

```java
BiFunction<String, String, String> mapper = (v1, v2) -> null;
Map<String, String> favorites = new HashMap<>();
favorites.put("Jenny", "Bus Tour");
favorites.put("Tom", "Bus Tour");

favorites.merge("Jenny", "Skyride", mapper);
favorites.merge("Sam", "Skyride", mapper);
System.out.println(favorites);  // {Tom=Bus Tour, Sam=Skyride}
```

&emsp;&emsp;
Tom was left alone since there was no merge() call for that key. Sam was added since
that key was not in the original list. Jenny was removed because the mapping function
returned null. <br />

&emsp;&emsp;
Table 9.7 shows all of these scenarios as a reference.

> **Table 9.7** Behavior of the merge() method
> 
> | If the requested key ________ | And mapping function returns ________ | Then:                                        |
> | :--- | :--- |:---------------------------------------------|
> |Has a null value in map|N/A (mapping function is not called)| Update key’s value in map with value parameter|
> |Has a non-null value in map|null|Remove key from map|
> |Has a non-null value in map|A non-null value|Set key to mapping function result|
> |Is not in map|N/A (mapping function not called)|Add key with value parameter top map directly without calling mapping function|
