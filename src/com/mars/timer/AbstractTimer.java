package com.mars.timer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public abstract class AbstractTimer {
    protected String stringFormStartTime;
    protected String stringFormEndTime;
    protected long currentTimeInMillisecond;
    protected long delay;

    public AbstractTimer() {
        // this is the game clock / countdown timer logic
        Timer timer = new Timer();                          // create a timer
        TimerTask task = new Task();                        // create a task -- task.java executes shutdown
        setCurrentTime();
        timer.schedule(task, delay);                        // schedules the timer to execute task after delay
        long endTime = currentTimeInMillisecond + delay;                   // marks endTime of game
        stringFormEndTime = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new java.util.Date (endTime)); // formats endTime to SDF
    }

    public void setCurrentTime(){
        currentTimeInMillisecond = System.currentTimeMillis();
    }

    public long printCurrentTime(){
        System.out.println(currentTimeInMillisecond);
        return currentTimeInMillisecond;
    }

    public void showDifference(){
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        // Try Block
        try {
            Date date = sdf.parse(stringFormEndTime);
            long difference_In_Time = date.getTime() - currentTimeInMillisecond;
            long difference_In_Hours = (difference_In_Time / (1000 * 60 * 60)) % 12;
            long difference_In_Minutes = (difference_In_Time / (1000 * 60)) % 60;
            long difference_In_Seconds = (difference_In_Time / 1000) % 60;
            System.out.println("Time remaining: " + difference_In_Hours + " hrs, " +
                    difference_In_Minutes + " mins, " +
                    difference_In_Seconds + " secs");
        }
        // Catch the Exception
        catch (ParseException e) {
            e.printStackTrace();
        }
    }
}