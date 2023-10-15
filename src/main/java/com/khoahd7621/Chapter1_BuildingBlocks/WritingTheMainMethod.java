package com.khoahd7621.Chapter1_BuildingBlocks;

public class WritingTheMainMethod {

    // The main method is the entry point of the program
    public static void main(String[] args) {
        System.out.println("Hello World");
        System.out.println(args[0]);
        System.out.println(args[1]);
    }

    // Rule:
    // Each file can contain only one public class.
    // The filename must match the class name, including case, and have a .java extension.
    // If the Java class is an entry point for the program, it must contain a valid main() method.

    /*
    * We arrive at the main() method’s parameter list, represented as an array of
    * java.lang.String objects. You can use any valid variable name along with any of these
    * three formats:
    * 1. String[] args
    * 2. String options[]
    * 3. String... friends
    * */

    /*
    * Optional Modifiers in main() Methods
    * While most modifiers, such as public and static, are required for main() methods,
    * there are some optional modifiers allowed.
    * public final static void main(final String[] args) {}
    * In this example, both final modifiers are optional, and the main() method is a valid
    * entry point with or without them.
    * */

    /*
    * Passing Parameters to a Java Program
    * The main() method’s parameter list is the only way to pass parameters to a Java program.
    * To run it, type this in the command line:
      javac WritingTheMainMethod.java
      java WritingTheMainMethod Bronx Zoo
    * The output is what you might expect:
      Hello World
      Bronx
      Zoo
    * */

    /*
    * Single-File Source-Code
    * If you get tired of typing both javac and java every time you want to try a code example,
    * there’s a shortcut. You can instead run
      java WritingTheMainMethod.java Bronx Zoo
    * There is a key difference here. When compiling first, you omitted the .java extension
    * when running java. When skipping the explicit compilation step, you include this
    * extension. This feature is called launching single-file source-code programs and is useful for
    * testing or for small programs. The name cleverly tells you that it is designed for when your
    * program is one file.
    * */
}
