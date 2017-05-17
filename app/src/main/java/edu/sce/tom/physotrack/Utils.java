package edu.sce.tom.physotrack;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class Utils {

    //constants
    public static final String SMILE_EXP = "Smile";
    public static final String KISS_EXP = "Kiss";
    public static final String NATURAL_EXP = "Blankly";
    public static final String EYEBROWRAISED_EXP = "EyebrowRaised";
    public static final String EYECLOSED_EXP = "EyesClosed";
    public static final String UPPERLIPRAISED_EXP = "Rabbit";





    //returns todays date as a string
    public static String todaysDateToString() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date today = Calendar.getInstance().getTime();
        return df.format(today);
    }

    //takes a string date and returns a date object
    public static Date stringToDate(String string) {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = format.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    //Convert a date to string
    public static String dateToString(Date date) {
        if(date!=null){
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            return df.format(date);
        }
        else
            return todaysDateToString();
    }



}
