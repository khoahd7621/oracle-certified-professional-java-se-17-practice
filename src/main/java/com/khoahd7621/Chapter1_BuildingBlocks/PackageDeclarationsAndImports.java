package com.khoahd7621.Chapter1_BuildingBlocks;

import java.util.Random; // import tells us where to find Random

public class PackageDeclarationsAndImports {
    public static void main(String[] args) {
        Random r = new Random();
        System.out.println(r.nextInt(10)); // A number 0-9
    }

    /*
     * Packages
     * Package names are hierarchical like the mail as well. The postal service starts with the
     * top level, looking at your country first. You start reading a package name at the beginning
     * too. For example, if it begins with java, this means it came with the JDK. If it starts with
     * something else, it likely shows where it came from using the website name in reverse. For
     * example, com.wiley.javabook tells us the code is associated with the wiley.com website or organization.
     * After the website name, you can add whatever you want. For example,
     * com.wiley.java.my.name also came from wiley.com. Java calls more detailed packages
     * child packages. The package com.wiley.javabook is a child package of com.wiley. You
     * can tell because it’s longer and thus more specific.
     * */

    /*
    * Wildcards
    * Classes in the same package are often imported together. You can use a shortcut to import all
    * the classes in a package.
    *
    * import java.util.*; // imports java.util.Random among other things
    * public class NumberPicker {
    *       public static void main(String[] args) {
    *           Random r = new Random();
    *           System.out.println(r.nextInt(10));
    *       }
    * }
    *
    * In this example, we imported java.util.Random and a pile of other classes. The * is
    * a wildcard that matches all classes in the package. Every class in the java.util package
    * is available to this program when Java compiles it. The import statement doesn't bring in
    * child packages, fields, or methods; it imports only classes directly under the package.
    *
    * ** You might think that including so many classes slows down your program execution, but
    * it doesn't. The compiler figures out what’s actually needed. Which approach you choose is
    * personal preference—or team preference, if you are working with others on a team. Listing
    * the classes used makes the code easier to read, especially for new programmers. Using the
    * wildcard can shorten the import list.
    * */

    /*
    * Redundant Imports
    * Wait a minute! We’ve been referring to System without an import every time we printed
    * text, and Java found it just fine. There’s one special package in the Java world called
    * java.lang. This package is special in that it is automatically imported. You can type this
    * package in an import statement, but you don’t have to.
    * */

    /*
    * Naming Conflicts
    * One of the reasons for using packages is so that class names don’t have to be unique across
    * all of Java. This means you’ll sometimes want to import a class that can be found in multiple places.
    * A common example of this is the Date class. Java provides implementations of
    * java.util.Date and java.sql.Date.
    *
    * ** If You Really Need to Use Two Classes with the Same Name
    * Sometimes you really do want to use Date from two different packages. When this happens,
    * you can pick one to use in the import statement and use the other’s fully qualified
    * class name. Or you can drop both import statements and always use the fully qualified
    * class name.
    *   public class Conflicts {
    *       java.util.Date date;
    *       java.sql.Date sqlDate;
    *   }
    * */

    /*
    * Ordering Elements in a Class
    * Element                    | Example               | Required?     | Where does it go?
    * Package declaration        | package abc;          | No            | First line in the file (excluding
    *                                                                     comments or blank lines)
    * Import statements          | import java.util.*;   | No            | Immediately after the package (if present)
    * Top-level type declaration | public class A {}     | Yes           | Immediately after the import statements (if present)
    * Field declarations         | int value;            | No            | Any top-level element within a class
    * Method declarations        | void method() {}      | No            | Any top-level element within a class
    * */

}
