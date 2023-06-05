package com.khoahd7621.Chapter5_Methods.pond.shore;

public class Bird {
    protected String text = "floating";
    protected void floatInWater() {
        System.out.print(text);
    }
}

class BirdWatcher {
    public void watchBird() {
        Bird bird = new Bird();
        bird.floatInWater();         // protected access is ok
        System.out.print(bird.text); // protected access is ok
    }
}
