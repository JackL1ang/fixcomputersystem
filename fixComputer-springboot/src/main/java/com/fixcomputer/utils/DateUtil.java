package com.fixcomputer.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public Date dateUtilForDate(){
        Calendar instance = Calendar.getInstance();
        Date time = instance.getTime();

        return time;
    }

    public String dateUtilForString(){
        Calendar instance = Calendar.getInstance();
        Date time = instance.getTime();
        SimpleDateFormat Dateformat = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = Dateformat.format(time);
        return format;
    }

    public String dateUtilMonth(){
        Calendar instance = Calendar.getInstance();
        Date time = instance.getTime();
        SimpleDateFormat Dateformat = new SimpleDateFormat("yyyy-MM-dd");
        String format = Dateformat.format(time);
        return format;
    }


}
