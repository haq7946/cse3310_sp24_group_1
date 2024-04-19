package uta.cse3310;
import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;
import java.util.ArrayList;

public class ClockTest extends TestCase {
    Clock clock = new Clock();
    public void testClockInitialization() {
        System.out.println("Initial timer value: " + clock.getTimer());
        assertTrue("Initial timer value should be 30 seconds", clock.getTimer() == 30);
        assertFalse(clock.checkSuddenDeath());
    }

    public void testTimerCountdownAndReset() throws InterruptedException {
        clock.startCountdown();
        // Wait for 5 seconds before simulating the word found
        Thread.sleep(5000);
        // Log the timer value right before resetting
        System.out.println("Timer value before word found: " + clock.getTimer());
        clock.wordFound();
        // Log the timer value after resetting
        System.out.println("Timer reset after word found. New timer value: " + clock.getTimer());
        // Check if the timer was correctly reset
        assertEquals("Timer should be reset to 30 seconds", 30, clock.getTimer());
    }
    
    
    public void testSuddenDeathMode() {
        // Activate sudden death mode
        clock.setSuddenDeath(true);
        // Log the status after setting sudden death mode
        System.out.println("Sudden death mode activated: " + clock.checkSuddenDeath());
        // Assert that sudden death mode is correctly set
        assertTrue("Sudden death mode should be true", clock.checkSuddenDeath());
    }
    
}

