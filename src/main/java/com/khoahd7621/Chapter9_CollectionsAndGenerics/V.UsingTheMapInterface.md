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
