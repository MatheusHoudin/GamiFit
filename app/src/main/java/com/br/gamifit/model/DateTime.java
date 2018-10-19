package com.br.gamifit.model;

import android.annotation.TargetApi;

import org.threeten.bp.LocalDateTime;

import java.util.Date;

public class DateTime {
    private int hour;
    private int minutes;
    private int year;
    private int month;
    private int monthDay;

    private final int MINIMUM_TRAINING_MINUTES = 40;
    private final int MINUTES_PER_HOUR = 60;

    public DateTime(){}

    public DateTime(int hour, int minutes, int year, int month, int monthDay) {
        this.hour = hour;
        this.minutes = minutes;
        this.year = year;
        this.month = month;
        this.monthDay = monthDay;
    }

    /**
     * @param checkOutTime
     * @return true whether the checkOutDate is the same as the checkIn date or not
     */
    public boolean compareDateWithCheckOutDateTime(DateTime checkOutTime){
        return this.monthDay==checkOutTime.getMonthDay() && this.month==checkOutTime.getMonth()
                && this.year==checkOutTime.getYear();
    }

    /**
     * Check if the user's check out time is acceptable to gain +1 offensive day, it requires at least
     * the check out time to be 40 minutes after the check in time
     */
    public boolean compareTimeWithCheckOutDateTime(DateTime checkOutTime){
        if(this.hour==checkOutTime.getHour()){
            int trainingTime = checkOutTime.getMinutes() - this.minutes;
            return trainingTime >= MINIMUM_TRAINING_MINUTES;
        }else if(this.hour<checkOutTime.getHour()){
            int minutesLeftToCheckInHour = MINUTES_PER_HOUR - this.minutes;
            int trainingTime = minutesLeftToCheckInHour + checkOutTime.getMinutes();
            return trainingTime >= MINIMUM_TRAINING_MINUTES;
        }else{
            return false;
        }
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getMonthDay() {
        return monthDay;
    }

    public void setMonthDay(int monthDay) {
        this.monthDay = monthDay;
    }

}
