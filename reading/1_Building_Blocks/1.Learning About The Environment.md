# Learning about the Environment

The Java environment consists of understanding a number of technologies. In the following
sections, we go over the key terms and acronyms you need to know and then discuss what
software you need to study for the exam.

## I. Major Components of Java
The *Java Development Kit* (JDK) contains the minimum software you need to do Java
development. Key commands include:

- javac: Converts .java source files into .class bytecode
- java: Executes the program
- jar: Packages files together
- javadoc: Generates documentation

The **javac** program generates instructions in a special format called `bytecode` that
the java command can run. Then java launches the *Java Virtual Machine* (JVM) before
running the code. The JVM knows how to run bytecode on the actual machine it is on. You
can think of the JVM as a special magic box on your machine that knows how to run your
`.class` file within your particular operating system and hardware.

> #### Where Did the JRE Go?
> In Java 8 and earlier, you could download a Java Runtime Environment (JRE) instead of the
full JDK. The JRE was a subset of the JDK that was used for running a program but could
not compile one. Now, people can use the full JDK when running a Java program. Alternatively, 
developers can supply an executable that contains the required pieces that would
have been in the JRE. <br /><br />
> When writing a program, there are common pieces of functionality and algorithms that
developers need. Luckily, we do not have to write each of these ourselves. Java comes with
a large suite of *application programming interfaces* (APIs) that you can use. For example,
there is a `StringBuilder` class to create a large `String` and a method in `Collections`
to sort a list. When writing a program, it is helpful to determine what pieces of your 
assignment can be accomplished by existing APIs.

&emsp;&emsp;
You might have noticed that we said the JDK contains the minimum software you need.
Many developers use an *integrated development environment* (IDE) to make writing and
running code easier. While we do not recommend using one while studying for the exam, it
is still good to know that they exist. Common Java IDEs include Eclipse, IntelliJ IDEA, and
Visual Studio Code.

## II. Downloading a JDK
Every six months, Oracle releases a new version of Java. Java 17 came out in September 2021. 
This means that Java 17 will not be the latest version when you download the JDK to 
study for the exam. However, you should still use Java 17 to study with since this is a Java
17 exam. The rules and behavior can change with later versions of Java. You wouldn’t want
to get a question wrong because you studied with a different version of Java! <br />

&emsp;&emsp;
You can download Oracle’s JDK on the Oracle website, using the same account you use
to register for the exam. There are many JDKs available, the most popular of which, besides
Oracle’s JDK, is OpenJDK. <br />

&emsp;&emsp;
Many versions of Java include *preview* features that are off by default but that you can
enable. Preview features are not on the exam. To avoid confusion about when a feature was
added to the language, we will say “was officially introduced in” to denote when it was
moved out of preview.

> #### Check Your Version of Java
> Before we go any further, please take this opportunity to ensure that you have the right version of Java on your path.
> ```bash
> javac -version
> java -version
> ```
> Both of these commands should include version number 17.
