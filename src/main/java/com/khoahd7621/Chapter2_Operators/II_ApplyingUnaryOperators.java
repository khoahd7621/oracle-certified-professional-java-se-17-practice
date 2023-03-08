package com.khoahd7621.Chapter2_Operators;

public class II_ApplyingUnaryOperators {
    // Operator                 Examples                Description
    // Logical complement       !a                      Inverts a boolean’s logical value
    // Bitwise complement       ~b                      Inverts all 0s and 1s in a number
    // Plus                     +c                      Indicates a number is positive, although numbers are
    //                                                  assumed to be positive in Java unless accompanied by a
    //                                                  negative unary operator
    // Negation or minus        -d                      Indicates a literal number is negative or negates an
    //                                                  expression
    // Increment                ++e, f++                Increments a value by 1
    // Decrement                --f, h--                Decrements a value by 1
    // Cast                     (String)i               Casts a value to a specific type

    // Complement and Negation Operators
    // 1. The logical complement operator (!) flips the value of a boolean expression.
    //      boolean isAnimalAsleep = false;
    //      System.out.print(isAnimalAsleep); // false
    //      isAnimalAsleep = !isAnimalAsleep;
    //      System.out.print(isAnimalAsleep); // true
    //
    // 2. The bitwise complement operator (~), which flips all the 0s and 1s in a number.
    //    It can only be applied to integer numeric types such as byte, short, char, int, and long.
    //      int value = 3; // Stored as 0011
    //      int complement = ~value; // Stored as 1100
    //      System.out.println(value); // 3
    //      System.out.println(complement); // -4
    //    Relax! You don’t need to know how to do complicated bit arithmetic on the exam, as
    //    long as you remember this rule: to find the bitwise complement of a number, multiply it by
    //    negative one and then subtract one.
    //      System.out.println(-1 * value - 1); // -4
    //      System.out.println(-1 * complement - 1); // 3
    //
    // 3. the negation operator (-) reverses the sign of a numeric expression, as shown in these statements:
    //      double zooTemperature = 1.21;
    //      System.out.println(zooTemperature); // 1.21
    //      zooTemperature = -zooTemperature;
    //      System.out.println(zooTemperature); // -1.21
    //      zooTemperature = -(-zooTemperature);
    //      System.out.println(zooTemperature); // -1.21

    // Note: Keep an eye out for questions on the exam that use numeric values (such
    //  as 0 or 1) with boolean expressions. Unlike in some other programming
    //  languages, in Java, 1 and true are not related in any way, just as 0 and
    //  false are not related.

    // Increment and Decrement Operators
    // Operator         Example     Description
    // Pre-increment    ++w         Increases the value by 1 and returns the new value
    // Pre-decrement    --x         Decreases the value by 1 and returns the new value
    // Post-increment   y++         Increases the value by 1 and returns the original value
    // Post-decrement   z--         Decreases the value by 1 and returns the original value

    // Note: For the exam, it is critical that you know the difference between expressions like
    // parkAttendance++ and ++parkAttendance. The increment and decrement operators will be in
    // multiple questions, and confusion about which value is returned could cause you to lose a
    // lot of points on the exam.
}
