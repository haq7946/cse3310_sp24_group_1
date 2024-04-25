package uta.cse3310;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.lang.Comparable;
import java.util.Collections;
public class Clock {
    private int countdown;
    private final int INITIAL_TIME = 30;  // 30 seconds for the timer
    private boolean suddenDeathMode;

    public Clock() {
        this.countdown = INITIAL_TIME;
        this.suddenDeathMode = false;
    }

    public int getTimer() {
        return this.countdown;
    }

    public void setTimer(int time) {
        this.countdown = time;
    }

    public boolean checkSuddenDeath() {
        return this.suddenDeathMode;
    }

    public void setSuddenDeath(boolean suddenDeath) {
        this.suddenDeathMode = suddenDeath;
    }

    public void startCountdown() {
        new Thread(() -> {
            try {
                while (countdown > 0 && !suddenDeathMode) {
                    Thread.sleep(1000);
                    countdown--;
                    System.out.println("Time left: " + countdown + " seconds");
                }
                if (suddenDeathMode) {
                    System.out.println("Sudden death mode activated.");
                } 
                else if (countdown == 0) {
                    System.out.println("Timer ended.");
                }
            } catch (InterruptedException e) {
                System.out.println("Timer was interrupted.");
            }
        }).start();
    }

    public void resetTimer() {
        this.countdown = INITIAL_TIME;
    }
    public void wordFound() {
        this.resetTimer();
    }

    public static void main(String[] args) {
        Clock clock = new Clock();
    
        // Start the countdown
        clock.startCountdown();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    
        clock.wordFound();  // Reset the timer when a word is found
    
        // Keep the program running until the countdown finishes
        try {
            Thread.sleep(clock.getTimer() * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}