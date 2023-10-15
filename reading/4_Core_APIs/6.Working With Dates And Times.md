# Working with Date and Times

Java provides a number of APIs for working with dates and times. There’s also an old java.
util.Date class, but it is not on the exam. You need an import statement to work with the
modern date and time classes. To use it, add this import to your program:

```java
import java.time.*; // import time classes
```

> **Day vs. Date** <br />
> In American English, the word date is used to represent two different concepts. Sometimes,
it is the month/day/year combination when something happened, such as January 1, 2000.
Sometimes, it is the day of the month, such as “Today’s date is the 6th.” <br />
> That’s right; the words day and date are often used as synonyms. Be alert to this on
the exam, especially if you live someplace where people are more precise about this
distinction.

## I. Creating Dates and Times
In the real world, we usually talk about dates and time zones as if the other person is located
near us. For example, if you say to me, “I’ll call you at 11:00 on Tuesday morning,” we
assume that 11:00 means the same thing to both of us. But if I live in New York and you live
in California, we need to be more specific. California is three hours earlier than New York
because the states are in different time zones. You would instead say, “I’ll call you at 11:00
EST (Eastern Standard Time) on Tuesday morning.” <br />

&emsp;&emsp;
When working with dates and times, the first thing to do is to decide how much
information you need. The exam gives you four choices:

- **LocalDate**: Contains just a date—no time and no time zone. A good example of LocalDate is your birthday this year. It is your birthday for a full day, regardless of what time it is.
- **LocalTime**: Contains just a time—no date and no time zone. A good example of LocalTime is midnight. It is midnight at the same time every day.
- **LocalDateTime**: Contains both a date and time but no time zone. A good example of
  LocalDateTime is “the stroke of midnight on New Year’s Eve.” Midnight on January 2
  isn’t nearly as special, making the date relatively unimportant, and clearly an hour after
  midnight isn’t as special either.
- **ZonedDateTime**: Contains a date, time, and time zone. A good example of
  ZonedDateTime is “a conference call at 9:00 a.m. EST.” If you live in California,
  you’ll have to get up really early since the call is at 6:00 a.m. local time!

&emsp;&emsp;
You obtain date and time instances using a static method:

```java
System.out.println(LocalDate.now());
System.out.println(LocalTime.now());
System.out.println(LocalDateTime.now());
System.out.println(ZonedDateTime.now());
```

&emsp;&emsp;
Each of the four classes has a static method called now(), which gives the current date
and time. Your output is going to depend on the date/time when you run it and where you
live. The authors live in the United States, making the output look like the following when
run on October 25 at 9:13 a.m.:

```java
2021–10–25
09:13:07.768
2021–10–25T09:13:07.768
2021–10–25T09:13:07.769–05:00[America/New_York]
```

&emsp;&emsp;
The key is the type of information in the output. The first line contains only a date and
no time. The second contains only a time and no date. The time displays hours, minutes, seconds, 
and fractional seconds. The third contains both a date and a time. The output uses T
to separate the date and time when converting LocalDateTime to a String. Finally, the
fourth adds the time zone offset and time zone. New York is four time zones away from
Greenwich Mean Time (GMT). <br />

&emsp;&emsp;
*Greenwich Mean Time* is a time zone in Europe that is used as time zone zero when discussing 
offsets. You might have also heard of Coordinated Universal Time, which is a time
zone standard. It is abbreviated as UTC, as a compromise between the English and French
names. (That’s not a typo. UTC isn’t actually the proper acronym in either language!) UTC
uses the same time zone zero as GMT. <br />

&emsp;&emsp;
First, let’s try to figure out how far apart these moments are in time. Notice how India
has a half-hour offset, not a full hour. To approach a problem like this, you subtract the time
zone from the time. This gives you the GMT equivalent of the time:

```java
2022–06–20T06:50+05:30[Asia/Kolkata] // GMT 2022–06–20 01:20
2022–06–20T07:50-05:00[US/Eastern]   // GMT 2022–06–20 12:50
```

&emsp;&emsp;
Remember that you need to add when subtracting a negative number. After converting to
GMT, you can see that the U.S. Eastern time is 11 and a half hours behind the Kolkata time.

> **Time Zone Rules** <br />
> The time zone offset can be listed in different ways: +02:00, GMT+2, and
UTC+2 all mean the same thing. You might see any of them on the exam.

&emsp;&emsp;
If you have trouble remembering this, try to memorize one example where the time
zones are a few zones apart, and remember the direction. In the United States, most
people know that the East Coast is three hours ahead of the West Coast. And most people
know that Asia is ahead of Europe. Just don’t cross time zone zero in the example that
you choose to remember. The calculation works the same way, but it isn’t as great a
memory aid.

> **Wait, I Don’t Live in the United States** <br />
> The exam recognizes that exam takers live all over the world, and it will not ask you about
the details of U.S. date and time formats. That said, our examples do use U.S. date and time
formats, as will the questions on the exam. Just remember that the month comes before
the date. Also, Java tends to use a 24-hour clock even though the United States uses a 12-
hour clock with a.m./p.m.

&emsp;&emsp;
Now that you know how to create the current date and time, let’s look at other specific
dates and times. To begin, let’s create just a date with no time. Both of these examples create
the same date:

```java
var date1 = LocalDate.of(2022, Month.JANUARY, 20);
var date2 = LocalDate.of(2022, 1, 20);
```

&emsp;&emsp;
Both pass in the year, month, and date. Although it is good to use the Month constants (to
make the code easier to read), you can pass the int number of the month directly. Just use
the number of the month the same way you would if you were writing the date in real life.
The method signatures are as follows:

```java
public static LocalDate of(int year, int month, int dayOfMonth)
public static LocalDate of(int year, Month month, int dayOfMonth)
```

> **Note:** <br />
> Up to now, we’ve been continually telling you that Java counts starting
with 0. Well, months are an exception. For months in the new date and
time methods, Java counts starting from 1, just as we humans do.

&emsp;&emsp;
When creating a time, you can choose how detailed you want to be. You can specify just
the hour and minute, or you can include the number of seconds. You can even include nanoseconds
if you want to be very precise. (A nanosecond is a billionth of a second, although
you probably won’t need to be that specific.)

```java
var time1 = LocalTime.of(6, 15);            // hour and minute
var time2 = LocalTime.of(6, 15, 30);        // + seconds
var time3 = LocalTime.of(6, 15, 30, 200);   // + nanoseconds
```

&emsp;&emsp;
These three times are all different but within a minute of each other. The method signatures are as follows:

```java
public static LocalTime of(int hour, int minute)
public static LocalTime of(int hour, int minute, int second)
public static LocalTime of(int hour, int minute, int second, int nanos)
```

&emsp;&emsp;
You can combine dates and times into one object:

```java
var dateTime1 = LocalDateTime.of(2022, Month.JANUARY, 20, 6, 15, 30);
var dateTime2 = LocalDateTime.of(date1, time1);
```

&emsp;&emsp;
The first line of code shows how you can specify all of the information about the
LocalDateTime right in the same line. The second line of code shows how you can create 
LocalDate and LocalTime objects separately first and then combine them to create a
LocalDateTime object. <br />

&emsp;&emsp;
There are a lot of method signatures since there are more combinations. The following
method signatures use integer values:

```java
public static LocalDateTime of(int year, int month, int dayOfMonth, int hour, int minute)
public static LocalDateTime of(int year, int month, int dayOfMonth, int hour, int minute, int second)
public static LocalDateTime of(int year, int month, int dayOfMonth, int hour, int minute, int second, int nanos)
```

&emsp;&emsp;
Others take a Month reference:

```java
public static LocalDateTime of(int year, Month month, int dayOfMonth, int hour, int minute)
public static LocalDateTime of(int year, Month month, int dayOfMonth, int hour, int minute, int second)
public static LocalDateTime of(int year, Month month, int dayOfMonth, int hour, int minute, int second, int nanos)
```

&emsp;&emsp;
Finally, one takes an existing LocalDate and LocalTime:

```java
public static LocalDateTime of(LocalDate date, LocalTime time)
```

&emsp;&emsp;
In order to create a ZonedDateTime, we first need to get the desired time zone. We will
use US/Eastern in our examples:

```java
var zone = ZoneId.of("US/Eastern");
var zoned1 = ZonedDateTime.of(2022, 1, 20, 6, 15, 30, 200, zone);
var zoned2 = ZonedDateTime.of(date1, time1, zone);
var zoned3 = ZonedDateTime.of(dateTime1, zone);
```

&emsp;&emsp;
We start by getting the time zone object. Then we use one of three approaches to create
the ZonedDateTime. The first passes all of the fields individually. We don’t recommend this
approach—there are too many numbers, and it is hard to read. A better approach is to pass
a LocalDate object and a LocalTime object, or a LocalDateTime object.

&emsp;&emsp;
Although there are other ways of creating a ZonedDateTime, you only need to know three
for the exam:

```java
public static ZonedDateTime of(int year, int month, int dayOfMonth, int hour, int minute, int second, int nanos, ZoneId zone)
public static ZonedDateTime of(LocalDate date, LocalTime time, ZoneId zone)
public static ZonedDateTime of(LocalDateTime dateTime, ZoneId zone)
```

&emsp;&emsp;
Notice that there isn’t an option to pass in the Month enum. Also, we did not use a constructor 
in any of the examples. The date and time classes have private constructors along
with static methods that return instances. This is known as the factory pattern. The exam
creators may throw something like this at you:

```java
var d = new LocalDate(); // DOES NOT COMPILE
```

&emsp;&emsp;
Don’t fall for this. You are not allowed to construct a date or time object directly.
Another trick is what happens when you pass invalid numbers to of(), for example:

```java
var d = LocalDate.of(2022, Month.JANUARY, 32) // DateTimeException
```

&emsp;&emsp;
You don’t need to know the exact exception that’s thrown, but it’s a clear one:

```java
java.time.DateTimeException: Invalid value for DayOfMonth (valid values 1-28/31): 32
```

## II. Manipulating Dates and Times

Adding to a date is easy. The date and time classes are immutable. Remember to assign the
results of these methods to a reference variable so they are not lost.

```java
12: var date = LocalDate.of(2022, Month.JANUARY, 20);
13: System.out.println(date); // 2022–01–20
14: date = date.plusDays(2);
15: System.out.println(date); // 2022–01–22
16: date = date.plusWeeks(1);
17: System.out.println(date); // 2022–01–29
18: date = date.plusMonths(1);
19: System.out.println(date); // 2022–02–28
20: date = date.plusYears(5);
21: System.out.println(date); // 2027–02–28
```

&emsp;&emsp;
This code is nice because it does just what it looks like. We start out with January 20, 2022. On line 14, 
we add two days to it and reassign it to our reference variable. On line 16,
we add a week. This method allows us to write clearer code than plusDays(7). Now date
is January 29, 2022. On line 18, we add a month. This would bring us to February 29, 2022.
However, 2022 is not a leap year. (2020 and 2024 are leap years.) Java is smart enough to
realize that February 29, 2022 does not exist, and it gives us February 28, 2022, instead.
Finally, line 20 adds five years.

> **Note**: <br />
> February 29 exists only in a leap year. Leap years are years that are a multiple of 4 or 400, 
> but not other multiples of 100. For example, 2000 and 2016 are leap years, but 2100 is not.

&emsp;&emsp;
There are also nice, easy methods to go backward in time. This time, let’s work with LocalDateTime:

```java
22: var date = LocalDate.of(2024, Month.JANUARY, 20);
23: var time = LocalTime.of(5, 15);
24: var dateTime = LocalDateTime.of(date, time);
25: System.out.println(dateTime); // 2024–01–20T05:15
26: dateTime = dateTime.minusDays(1);
27: System.out.println(dateTime); // 2024–01–19T05:15
28: dateTime = dateTime.minusHours(10);
29: System.out.println(dateTime); // 2024–01–18T19:15
30: dateTime = dateTime.minusSeconds(30);
31: System.out.println(dateTime); // 2024–01–18T19:14:30
```

&emsp;&emsp;
Line 25 prints the original date of January 20, 2024, at 5:15 a.m. Line 26 subtracts a full
day, bringing us to January 19, 2024, at 5:15 a.m. Line 28 subtracts 10 hours, showing that
the date will change if the hours cause it to adjust, and it brings us to January 18, 2024, at
19:15 (7:15 p.m.). Finally, line 30 subtracts 30 seconds. You can see that all of a sudden, the
display value starts showing seconds. Java is smart enough to hide the seconds and nanoseconds 
when we aren’t using them. <br />

&emsp;&emsp;
It is common for date and time methods to be chained. For example, without the print
statements, the previous example could be rewritten as follows:

```java
var date = LocalDate.of(2024, Month.JANUARY, 20);
var time = LocalTime.of(5, 15);
var dateTime = LocalDateTime.of(date, time)
        .minusDays(1).minusHours(10).minusSeconds(30);
```

&emsp;&emsp;
When you have a lot of manipulations to make, this chaining comes in handy. There are
two ways that the exam creators can try to trick you. What do you think this prints?

```java
var date = LocalDate.of(2024, Month.JANUARY, 20);
date.plusDays(10);
System.out.println(date);
```

&emsp;&emsp;
It prints January 20, 2024. Adding 10 days was useless because the program ignored the
result. Whenever you see immutable types, pay attention to make sure that the return value
of a method call isn’t ignored. The exam also may test to see if you remember what each of
the date and time objects includes. Do you see what is wrong here?

```java
var date = LocalDate.of(2024, Month.JANUARY, 20);
date = date.plusMinutes(1);     // DOES NOT COMPILE
```

&emsp;&emsp;
LocalDate does not contain time. This means that you cannot add minutes to it. This
can be tricky in a chained sequence of addition/subtraction operations, so make sure that
you know which methods in Table below can be called on which types.

| -                              |Can call on LocalDate?|Can call on LocalTime?|Can call on LocalDateTime or ZonedDateTime?|
|--------------------------------|---|---|---|
| plusYears() <br/> minusYears() |Yes|No|Yes|
| plusMonths() <br/> minusMonths()|Yes|No|Yes|
| plusWeeks() <br/> minusWeeks()|Yes|No|Yes|
| plusDays() <br/> minusDays()|Yes|No|Yes|
| plusHours() <br/> minusHours()|No|Yes|Yes|
| plusMinutes() <br/> minusMinutes()|No|Yes|Yes|
| plusSeconds() <br/> minusSeconds()|No|Yes|Yes|
| plusNanos() <br/> minusNanos()|No|Yes|Yes|

## III. Working with Periods

Now you know enough to do something fun with dates! Our zoo performs animal enrichment
activities to give the animals something enjoyable to do. The head zookeeper has
decided to switch the toys every month. This system will continue for three months to see
how it works out.

```java
public static void main(String[] args) {
    var start = LocalDate.of(2022, Month.JANUARY, 1);
    var end = LocalDate.of(2022, Month.MARCH, 30);
    performAnimalEnrichment(start, end);
}
private static void performAnimalEnrichment(LocalDate start, LocalDate end) {
    var upTo = start;
    while (upTo.isBefore(end)) { // check if still before end
        System.out.println("give new toy: " + upTo);
        upTo = upTo.plusMonths(1); // add a month
    } 
}
```

&emsp;&emsp;
This code works fine. It adds a month to the date until it hits the end date. The problem
is that this method can’t be reused. Our zookeeper wants to try different schedules to see
which works best.

> **Note**: <br />
> LocalDate and LocalDateTime have a method to convert themselves
into long values, equivalent to the number of milliseconds that have
passed since January 1, 1970, referred to as the epoch. What’s special
about this date? That’s what Unix started using for date standards, so
Java reused it.

&emsp;&emsp;
Luckily, Java has a Period class that we can pass in. This code does the same thing as the
previous example:

```java
public static void main(String[] args) {
    var start = LocalDate.of(2022, Month.JANUARY, 1);
    var end = LocalDate.of(2022, Month.MARCH, 30);
    var period = Period.ofMonths(1); // create a period
    performAnimalEnrichment(start, end, period);
}
private static void performAnimalEnrichment(LocalDate start, LocalDate end, Period period) { // uses the generic period
    var upTo = start;
    while (upTo.isBefore(end)) {
        System.out.println("give new toy: " + upTo);
        upTo = upTo.plus(period); // adds the period
    }
}
```

&emsp;&emsp;
The method can add an arbitrary period of time that is passed in. This allows us to reuse
the same method for different periods of time as our zookeeper changes their mind.
There are five ways to create a Period class:

```java
var annually = Period.ofYears(1);           // every 1 year
var quarterly = Period.ofMonths(3);         // every 3 months
var everyThreeWeeks = Period.ofWeeks(3);    // every 3 weeks
var everyOtherDay = Period.ofDays(2);       // every 2 days
var everyYearAndAWeek = Period.of(1, 0, 7); // every year and 7 days
```

&emsp;&emsp;
There’s one catch. You cannot chain methods when creating a Period. The following
code looks like it is equivalent to the everyYearAndAWeek example, but it’s not. Only the
last method is used because the Period.of methods are static methods.

```java
var wrong = Period.ofYears(1).ofWeeks(1); // every week
```

&emsp;&emsp;
This tricky code is really like writing the following:

```java
var wrong = Period.ofYears(1);
wrong = Period.ofWeeks(1);
```

&emsp;&emsp;
This is clearly not what you intended! That’s why the of() method allows you to pass in
the number of years, months, and days. They are all included in the same period. You will
get a compiler warning about this. Compiler warnings tell you that something is wrong or
suspicious without failing compilation. <br />

&emsp;&emsp;
The of() method takes only years, months, and days. The ability to use another factory
method to pass weeks is merely a convenience. As you might imagine, the actual period is
stored in terms of years, months, and days. When you print out the value, Java displays any
non-zero parts using the format shown below:

```java
System.out.println(Period.of(1,2,3));

// output: P1Y2M3D
// P is for period
// 1Y is 1 year
// 2M is 2 months
// 3D is 3 days
```

&emsp;&emsp;
As you can see, the P always starts out the String to show it is a period measure. Then
come the number of years, number of months, and number of days. If any of these are zero,
they are omitted. <br />

&emsp;&emsp;
Can you figure out what this outputs?

```java
System.out.println(Period.ofMonths(3));
```

&emsp;&emsp;
The output is P3M. Remember that Java omits any measures that are zero. The last thing
to know about Period is what objects it can be used with. Let’s look at some code:

```java
3: var date = LocalDate.of(2022, 1, 20);
4: var time = LocalTime.of(6, 15);
5: var dateTime = LocalDateTime.of(date, time);
6: var period = Period.ofMonths(1);
7: System.out.println(date.plus(period));       // 2022–02–20
8: System.out.println(dateTime.plus(period));   // 2022–02–20T06:15
9: System.out.println(time.plus(period));       // Exception
```

&emsp;&emsp;
Lines 7 and 8 work as expected. They add a month to January 20, 2022, giving us February 20, 2022. 
The first has only the date, and the second has both the date and time. <br />

&emsp;&emsp;
Line 9 attempts to add a month to an object that has only a time. This won’t work. Java
throws an UnsupportedTemporalTypeException and complains that we attempted to
use an Unsupported unit: Months. <br />

&emsp;&emsp;
As you can see, you have to pay attention to the type of date and time objects every place
you see them.

## IV. Working with Durations

You’ve probably noticed by now that a Period is a day or more of time. There is also
Duration, which is intended for smaller units of time. For Duration, you can specify the
number of days, hours, minutes, seconds, or nanoseconds. And yes, you could pass 365 days
to make a year, but you really shouldn’t—that’s what Period is for. <br />

&emsp;&emsp;
Conveniently, Duration works roughly the same way as Period, except it is used with
objects that have time. Duration is output beginning with PT, which you can think of as a
period of time. A Duration is stored in hours, minutes, and seconds. The number of seconds
includes fractional seconds. <br />

&emsp;&emsp;
We can create a Duration using a number of different granularities:

```java
var daily = Duration.ofDays(1);                 // PT24H
var hourly = Duration.ofHours(1);               // PT1H
var everyMinute = Duration.ofMinutes(1);        // PT1M
var everyTenSeconds = Duration.ofSeconds(10);   // PT10S
var everyMilli = Duration.ofMillis(1);          // PT0.001S
var everyNano = Duration.ofNanos(1);            // PT0.000000001S
```

&emsp;&emsp;
Duration doesn’t have a factory method that takes multiple units like Period does. If
you want something to happen every hour and a half, you specify 90 minutes. <br />

&emsp;&emsp;
Duration includes another more generic factory method. It takes a number and a
TemporalUnit. The idea is, say, something like “5 seconds.” However, TemporalUnit is an
interface. At the moment, there is only one implementation named ChronoUnit. <br />

&emsp;&emsp;
The previous example could be rewritten like this:

```java
var daily = Duration.of(1, ChronoUnit.DAYS);
var hourly = Duration.of(1, ChronoUnit.HOURS);
var everyMinute = Duration.of(1, ChronoUnit.MINUTES);
var everyTenSeconds = Duration.of(10, ChronoUnit.SECONDS);
var everyMilli = Duration.of(1, ChronoUnit.MILLIS);
var everyNano = Duration.of(1, ChronoUnit.NANOS);
```

&emsp;&emsp;
ChronoUnit also includes some convenient units such as ChronoUnit.HALF_DAYS to represent 12 hours.

> **ChronoUnit for Differences** <br />
> ChronoUnit is a great way to determine how far apart two Temporal values are.
Temporal includes LocalDate, LocalTime, and so on. ChronoUnit is in the java.time.temporal package.
> ```java
> var one = LocalTime.of(5, 15);
> var two = LocalTime.of(6, 30);
> var date = LocalDate.of(2016, 1, 20);
> System.out.println(ChronoUnit.HOURS.between(one, two));    // 1
> System.out.println(ChronoUnit.MINUTES.between(one, two));  // 75
> System.out.println(ChronoUnit.MINUTES.between(one, date)); // DateTimeException
> ```
> The first print statement shows that between truncates rather than rounds. The second
shows how easy it is to count in different units. Just change the ChronoUnit type. The last
reminds us that Java will throw an exception if we mix up what can be done on date vs. time objects. <br />
> Alternatively, you can truncate any object with a time element. For example:
> ```java
> LocalTime time = LocalTime.of(3, 12, 45);
> System.out.println(time);         // 03:12:45
> LocalTime truncated = time.truncatedTo(ChronoUnit.MINUTES);
> System.out.println(truncated);    // 03:12
> ```
> This example zeroes out any fields smaller than minutes. In our case, it gets rid of the seconds.

&emsp;&emsp;
Using a Duration works the same way as using a Period. For example:

```java
7: var date = LocalDate.of(2022, 1, 20);
8: var time = LocalTime.of(6, 15);
9: var dateTime = LocalDateTime.of(date, time);
10: var duration = Duration.ofHours(6);
11: System.out.println(dateTime.plus(duration)); // 2022–01–20T12:15
12: System.out.println(time.plus(duration)); // 12:15
13: System.out.println(
14:     date.plus(duration)); // UnsupportedTemporalTypeException
```

&emsp;&emsp;
Line 11 shows that we can add hours to a LocalDateTime, since it contains a time. Line
12 also works, since all we have is a time. Line 13 fails because we cannot add hours to an
object that does not contain a time. <br />

&emsp;&emsp;
Let’s try that again, but add 23 hours this time.

```java
7: var date = LocalDate.of(2022, 1, 20);
8: var time = LocalTime.of(6, 15);
9: var dateTime = LocalDateTime.of(date, time);
10: var duration = Duration.ofHours(23);
11: System.out.println(dateTime.plus(duration)); // 2022–01–21T05:15
12: System.out.println(time.plus(duration)); // 05:15
13: System.out.println(
14:     date.plus(duration)); // UnsupportedTemporalTypeException
```

&emsp;&emsp;
This time we see that Java moves forward past the end of the day. Line 11 goes to
the next day since we pass midnight. Line 12 doesn’t have a day, so the time just wraps
around—just like on a real clock.

## V. Period vs. Duration
Remember that Period and Duration are not equivalent. This example shows a Period and
Duration of the same length:

```java
var date = LocalDate.of(2022, 5, 25);
var period = Period.ofDays(1);
var days = Duration.ofDays(1);

System.out.println(date.plus(period)); // 2022–05–26
System.out.println(date.plus(days)); // Unsupported unit: Seconds
```

&emsp;&emsp;
Since we are working with a LocalDate, we are required to use Period. Duration
has time units in it, even if we don’t see them, and they are meant only for objects with
time. Make sure that you can fill in Table below to identify which objects can use Period
and Duration.

- Table: Where to use Duration and Period

|-|Can use with Period?|Can use with Duration?|
|---|---|---|
|LocalDate|Yes|No|
|LocalTime|No|Yes|
|LocalDateTime|Yes|Yes|
|ZonedDateTime|Yes|Yes|

## VI. Working with Instants
The Instant class represents a specific moment in time in the GMT time zone. Suppose that
you want to run a timer:

```java
var now = Instant.now();
// do something time consuming
var later = Instant.now();

var duration = Duration.between(now, later);
System.out.println(duration.toMillis()); // Returns number milliseconds
```

&emsp;&emsp;
In our case, the “something time consuming” was just over a second, and the program
printed out 1025. <br />

&emsp;&emsp;
If you have a ZonedDateTime, you can turn it into an Instant:

```java
var date = LocalDate.of(2022, 5, 25);
var time = LocalTime.of(11, 55, 00);
var zone = ZoneId.of("US/Eastern");
var zonedDateTime = ZonedDateTime.of(date, time, zone);
var instant = zonedDateTime.toInstant(); // 2022–05–25T15:55:00Z
System.out.println(zonedDateTime);       // 2022–05–25T11:55–04:00[US/Eastern]
System.out.println(instant);             // 2022–05–25T15:55:00Z
```

&emsp;&emsp;
The last two lines represent the same moment in time. The ZonedDateTime includes
a time zone. The Instant gets rid of the time zone and turns it into an Instant of
time in GMT. <br />

&emsp;&emsp;
You cannot convert a LocalDateTime to an Instant. Remember that an Instant is a
point in time. A LocalDateTime does not contain a time zone, and it is therefore not universally 
recognized around the world as the same moment in time

## VII. Accounting for Daylight Saving Time
Some countries observe daylight saving time. This is where the clocks are adjusted by an
hour twice a year to make better use of the sunlight. Not all countries participate, and those
that do use different weekends for the change. You only have to work with U.S. daylight
saving time on the exam, and that’s what we describe here. <br />

&emsp;&emsp;
The question will let you know if a date/time mentioned falls on a weekend when the
clocks are scheduled to be changed. If it is not mentioned in a question, you can assume that
it is a normal weekend. The act of moving the clock forward or back occurs at 2:00 a.m.,
which falls very early Sunday morning. <br />

&emsp;&emsp;
Figure below shows what happens with the clocks. When we change our clocks in March,
time springs forward from 1:59 a.m. to 3:00 a.m. When we change our clocks in November,
time falls back, and we experience the hour from 1:00 a.m. to 1:59 a.m. twice. Children
learn this as “Spring forward in the spring, and fall back in the fall.”

- Figure: Daylight saving time

| Normal day            | March changeover    | November changeover              |
|-----------------------|---------------------|----------------------------------|
| 1:00 a.m. – 1:59 a.m. | 1:00 a.m. – 1:59 a.m. | 1:00 a.m. – 1:59 a.m. (first time) |
| 2:00 a.m. – 3:00 a.m.   | 3:00 a.m. – 4:00 a.m. | 1:00 a.m. – 1:59 a.m. (again)      |
| 3:00 a.m. – 4:00 a.m.   | -                   |2:00 a.m. – 4:00 a.m.|

&emsp;&emsp;
For example, on March 13, 2022, we move our clocks forward an hour and jump from
2:00 a.m. to 3:00 a.m. This means that there is no 2:30 a.m. that day. If we wanted to know
the time an hour later than 1:30, it would be 3:30.

```java
var date = LocalDate.of(2022, Month.MARCH, 13);
var time = LocalTime.of(1, 30);
var zone = ZoneId.of("US/Eastern");
var dateTime = ZonedDateTime.of(date, time, zone);
System.out.println(dateTime);   // 2022–03-13T01:30-05:00[US/Eastern]
System.out.println(dateTime.getHour());  // 1
System.out.println(dateTime.getOffset());   // -05:00
dateTime = dateTime.plusHours(1);
System.out.println(dateTime);   // 2022–03-13T03:30-04:00[US/Eastern]
System.out.println(dateTime.getHour()); // 3
System.out.println(dateTime.getOffset());   // -04:00
```

&emsp;&emsp;
Notice that two things change in this example. The time jumps from 1:30 to 3:30. The
UTC offset also changes. Remember when we calculated GMT time by subtracting the time
zone from the time? You can see that we went from 6:30 GMT (1:30 minus –5:00) to 7:30
GMT (3:30 minus –4:00). This shows that the time really did change by one hour from
GMT’s point of view. We printed the hour and offset fields separately for emphasis. <br />

&emsp;&emsp;
Similarly, in November, an hour after the initial 1:30 a.m. is also 1:30 a.m. because at
2:00 a.m. we repeat the hour. This time, try to calculate the GMT time yourself for all three
times to confirm that we really do move only one hour at a time.

```java
var date = LocalDate.of(2022, Month.NOVEMBER, 6);
var time = LocalTime.of(1, 30);
var zone = ZoneId.of("US/Eastern");
var dateTime = ZonedDateTime.of(date, time, zone);
System.out.println(dateTime); // 2022-11-06T01:30-04:00[US/Eastern]
        
dateTime = dateTime.plusHours(1);
System.out.println(dateTime); // 2022-11-06T01:30-05:00[US/Eastern]
        
dateTime = dateTime.plusHours(1);
System.out.println(dateTime); // 2022-11-06T02:30-05:00[US/Eastern]
```

&emsp;&emsp;
Did you get it? We went from 5:30 GMT to 6:30 GMT, to 7:30 GMT.
Finally, trying to create a time that doesn’t exist just rolls forward:

```java
var date = LocalDate.of(2022, Month.MARCH, 13);
var time = LocalTime.of(2, 30);
var zone = ZoneId.of("US/Eastern");
var dateTime = ZonedDateTime.of(date, time, zone);
System.out.println(dateTime); // 2022–03–13T03:30–04:00[US/Eastern]
```

&emsp;&emsp;
Java is smart enough to know that there is no 2:30 a.m. that night and switches over to
the appropriate GMT offset. <br />
&emsp;&emsp;
Yes, it is annoying that Oracle expects you to know this even if you aren’t in the United
States - or for that matter, in a part of the United States that doesn’t follow daylight saving
time. The exam creators are in the United States, and they decided that everyone needs to
know how U.S. time zones work.
