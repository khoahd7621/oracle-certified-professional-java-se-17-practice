package com.khoahd7621.Chapter5_Methods.pond.goose;

import com.khoahd7621.Chapter5_Methods.pond.shore.Bird;

public class Gosling extends Bird {
    public void swim() {
        floatInWater();             // calling protected member
        System.out.println(text);   // calling protected member
    }

    public static void main(String[] args) {
        new Gosling().swim();
        Bird bird = new Gosling();
        // bird.floatInWater(); // DOES NOT COMPILE
    }
}

class BirdWatcherFromAfar {   // Not a subclass of Bird and in different package
    public void watchBird() {
        Bird bird = new Bird();
//        bird.floatInWater();         // DOES NOT COMPILE
//        System.out.print(bird.text); // DOES NOT COMPILE
    }
}
