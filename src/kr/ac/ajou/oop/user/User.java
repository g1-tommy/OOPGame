package kr.ac.ajou.oop.user;

import java.io.Serializable;

@SuppressWarnings("serial")
public class User implements Serializable {

    private int score;
    private int level;
    private String name;
    private boolean isGameOver;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        if(score < 0) System.out.println("Score cannot be negative.");
        else this.score = score;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        if(level < 0) System.out.println("Level cannot be negative.");
        else this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name.length() <= 0) System.out.println("Plz type your name.");
        else this.name = name;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }
    
}
