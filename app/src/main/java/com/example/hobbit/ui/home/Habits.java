package com.example.hobbit.ui.home;

public class Habits {
    private String name;

    public Habits() {}  // ← Required for Firebase

    public Habits(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
