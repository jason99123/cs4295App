package com.example.project;

/**
 * Created by chokilo3 on 11/18/2016.
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
