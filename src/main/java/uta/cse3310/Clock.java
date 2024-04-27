package uta.cse3310;

public class Clock
{
    public long countdown;
    private final int INITIAL_TIME = 30;  // 30 seconds for the timer
    //private boolean suddenDeathMode;


    public Clock(long end) {
        this.countdown = end;
        //this.suddenDeathMode = suddenDeath;
    }

    //Dunno how to use this, sorry -Bryan
    public void startCountdown(Runnable gameEnd) {
        new Thread(() -> {
            try {
                    while(countdown > 0)
                    {
                    Thread.sleep(countdown * 1000); //  1000 ms/sec
                    gameEnd.run(); // end the game
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
}
    // public static void main(String[] args) {
    //     Clock clock = new Clock();
    
    //     // Start the countdown
    //     clock.startCountdown();
    //     try {
    //         Thread.sleep(5000);
    //     } catch (InterruptedException e) {
    //         e.printStackTrace();
    //     }
    
    //     clock.wordFound();  // Reset the timer when a word is found
    
    //     // Keep the program running until the countdown finishes
    //     try {
    //         Thread.sleep(clock.getTimer() * 1000);
    //     } catch (InterruptedException e) {
    //         e.printStackTrace();
    //     }
    // }
