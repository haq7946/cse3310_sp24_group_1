package uta.cse3310;

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
                if (countdown == 0) {
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

    public static void main(String[] args) {
        Clock myClock = new Clock();  // Create a Clock object
        myClock.startCountdown();     // Start the countdown
    }
}
