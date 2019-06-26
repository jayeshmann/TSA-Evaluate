package com.tsa.exam.phoneProperties;

/**
 * Created by Akhil Tripathi on 11/15/2016.
 */

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class CurrentDateAndTime {
       public String getDateTime(){
           Date date= new Date();
           Calendar cal = Calendar.getInstance();
           cal.setTime(date);
           int month = cal.get(Calendar.MONTH);
           int year=cal.get(Calendar.YEAR);
           int day=cal.get(Calendar.DATE);
           return(year+"-"+(month+1)+"-"+day);
    }

    public String getTime(){

        Date date= new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("IST"));
        cal.setTime(date);
        int hours = cal.get(Calendar.HOUR_OF_DAY);
        int minutes=cal.get(Calendar.MINUTE);
        int seconds=cal.get(Calendar.SECOND);
        String meridian="AM";
        if(hours>12)
            meridian="PM";
        return(hours+":"+(minutes)+":"+seconds+" "+meridian);
    }

    public long getTimeInMillSecond(){
        Date date= new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        long time = cal.getTimeInMillis();
        return time;
    }
}
