package com.khoahd7621.Chapter1_BuildingBlocks;

public class CreatingAnObject {
    int number = 2; // Fields initializer

    // Instance initializers
    // In the meantime, you need to remember:
    // ■ Fields and instance initializer blocks are run in the order in which they appear in the file.
    // ■ The constructor runs after all fields and instance initializer blocks have run.
    {
        number = 6;
        System.out.println("Instance initializer");
    }

    // When you learned about methods, you saw braces ({}). The code between the braces
    // (sometimes called “inside the braces”) is called a code block.
    // Anywhere you see braces is code block.

    /*
    * There are two key points to note about the constructor: the name of the constructor
    * matches the name of the class, and there’s no return type.
    * */
    public CreatingAnObject() {
        number = 8;
        System.out.println("Constructor initializer");
    }

    // You may see a method like this in a class, but it’s not a constructor.
    // When you see a method name beginning with a capital letter and having a return type,
    // pay special attention to it. It is not a constructor since there’s a return type. It’s a regular
    // method that does compile but will not be called when you write new CreatingAnObject().
    public void CreatingAnObject() {
    }

    public static void main(String[] args) {
        CreatingAnObject creatingAnObject = new CreatingAnObject();
        System.out.println(creatingAnObject);
    }
}
