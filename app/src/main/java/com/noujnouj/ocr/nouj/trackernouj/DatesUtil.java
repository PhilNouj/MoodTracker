package com.noujnouj.ocr.nouj.trackernouj;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class DatesUtil {

    public DatesUtil() {
    }

    /*
    Get today's date - LocalDate format
    */
    @TargetApi(Build.VERSION_CODES.O)
    public LocalDate getTodayDate() {
        LocalDate date;
        date = LocalDate.now();
        return date;
    }

    /*
    Convert today's date in a String format
    */
    public String getTodayStr() {
        return getTodayDate().toString();
    }

    /*
    Check if 2 dates are identical - String format
    @param date1, date2 The 2 dates to compare in String format
    */
    public boolean compareDates (String date1, String date2) {
        if (date1.equals(date2)) {
            return true;
        } else {
            return false;
        }
    }

    /*
    Convert a String date to a LocalDate date
    @param dateStr The date in String format
    */
    @RequiresApi(api = Build.VERSION_CODES.O)
        public LocalDate convertDateStrToLocalDate(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateStr, formatter);
        return localDate;
    }

    /*
    Calculates the number of days between 2 dates - LocalDate format
    @param date1, date2 The 2 dates to compare in LocalDate format
    */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public int daysBetweenDates(LocalDate date1, LocalDate date2) {
        Period period = Period.between(date1, date2);
        int diff = period.getDays();
        return diff;
    }
}
