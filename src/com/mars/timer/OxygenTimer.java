package com.mars.timer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OxygenTimer extends AbstractTimer{
    private long delay = 15 * 60000L;

    public void pickUpOxygen() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        try {
            Date date = sdf.parse(stringFormEndTime);
            Long newDate = date.getTime() + delay/1000;
            stringFormEndTime = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new java.util.Date (newDate*1000));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}