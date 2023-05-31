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

