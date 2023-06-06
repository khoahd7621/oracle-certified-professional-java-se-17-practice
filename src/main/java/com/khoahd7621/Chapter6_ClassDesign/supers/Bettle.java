package com.khoahd7621.Chapter6_ClassDesign.supers;

public class Bettle extends Insect {
    protected int numberOfLegs = 6;
    short age = 3;

    public void printData() {
        System.out.println(this.label);
        System.out.println(super.label);
        System.out.println(this.age);
//        System.out.println(super.age);
        System.out.println(numberOfLegs);
    }

    public static void main(String[] n) {
        new Bettle().printData();
    }
}
