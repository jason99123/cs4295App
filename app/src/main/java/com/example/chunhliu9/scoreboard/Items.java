package com.example.chunhliu9.scoreboard;

/**
 * Created by chunhliu9 on 11/11/2016.
 */
public class Items {

    private String name;
    private String score;

    public Items() {
    }

    public Items(String name, String score) {

        this.name = name;
        this.score = score;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
