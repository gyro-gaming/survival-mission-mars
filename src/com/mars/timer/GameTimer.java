package com.mars.timer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

// time calculation method
public class GameTimer {
        public static void showDifference(String dieTime){
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            String timeStamp = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss").format(Calendar.getInstance().getTime());

            // Try Block
            try {
                Date d1 = sdf.parse(timeStamp);
                Date d2 = sdf.parse(dieTime);
                long difference_In_Time = d2.getTime() - d1.getTime();
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

//    // this is the game clock / countdown timer logic
//    Timer timer = new Timer();                          // create a timer
//    TimerTask task = new Task();                        // create a task -- task.java executes shutdown
//    long delay = 60 * 60000L;       // change when done testing    --  sets the length of delay
//        timer.schedule(task, delay);                        // schedules the timer to execute task after delay
//                long start = System.currentTimeMillis()/1000;       // marks start time of game, reduces from millisecs to secs
//                long markDelay = delay/1000;                        // creates variable for delay, reduces from millisecs to secs
//                long endTime = start + markDelay;                   // marks endTime of game
//                String currentTime = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new java.util.Date (start*1000));   // formats currentTime to SDF
//                String dieTime = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new java.util.Date (endTime*1000));     // formats endTime to SDF
