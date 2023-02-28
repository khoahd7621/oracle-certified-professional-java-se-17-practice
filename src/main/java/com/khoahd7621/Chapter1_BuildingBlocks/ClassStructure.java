package com.khoahd7621.Chapter1_BuildingBlocks;

// Most of the time, each Java class is defined in its own .java file. In this chapter,
// the only toplevel type is a class. A top-level type is a data structure that can be defined independently
// within a source file. For the majority of the book, we work with classes as the top-level type,
// but in Chapter 7, “Beyond Classes,” we present other top-level types, as well as nested types.
// A top-level class is often public, which means any code can call it. Interestingly, Java does
// not require that the type be public.
public class ClassStructure {
    /**
     * field
     * private : access modifier
     * String : type
     * name : name of field
     */
    private String name;

    /**
     * method
     * public : access modifier
     * String : return type
     * getName : name of method
     * return name : return value
     */
    public String getName() {
        return name;
    }

    /**
     * method
     * public : access modifier
     * void : return type
     * setName : name of method
     * String newName : parameter
     */
    public void setName(String newName) {
        name = newName;
    }

    // A single line comment begins with two forward slashes.

    /* A multiple-line comment (also known as a multiline comment) includes anything starting
     * from the symbol slash and asterisk and ending with asterisk and slash.
     * People often type an asterisk (*) at the beginning of
     * each line of a multiline comment to make it easier to read, but you don’t have to.
     */

    /**
     * Javadoc multiple-line comment
     * This comment is similar to a multiline comment, except it starts with /**. This special
     * syntax tells the Javadoc tool to pay attention to the comment. Javadoc's comments have a
     * specific structure that the Javadoc tool knows how to read.
     */
}

// If you do have a public type, it needs to match the filename.
// The declaration public class ClassStructure2 would not compile in a file named ClassStructure.java.
class ClassStructure2 {}
