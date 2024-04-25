package uta.cse3310;

public class ClockTest {
    public static void main(String[] args) {
        // Create a clock that counts down from 30 seconds
        Clock gameClock = new Clock(30);

        // Define what happens when the countdown ends
        Runnable gameEnd = () -> System.out.println("The game has ended!");

        // Start the countdown
        gameClock.startCountdown(gameEnd);

        // Wait for a few seconds to demonstrate the countdown
        try {
            Thread.sleep(5000); // Wait for 5 seconds
        } catch (InterruptedException e) {
            System.out.println("Main thread was interrupted.");
        }

        // This should print out before the game ends if the countdown is longer than the sleep duration
        System.out.println("Main thread continues running...");

        // After the countdown, we should see "The game has ended!" printed to the console
    }
}
