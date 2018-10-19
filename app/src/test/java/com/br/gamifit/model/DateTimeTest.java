package com.br.gamifit.model;

import junit.framework.Assert;

import org.junit.Test;

public class DateTimeTest {

    @Test
    public void equalsDateTest(){
        DateTime checkInTime = new DateTime(14,40,2018,10,17);
        DateTime checkOutTime = new DateTime(14,40,2018,10,17);

        Assert.assertEquals(true,checkInTime.compareDateWithCheckOutDateTime(checkOutTime));
    }

    @Test
    public void notEqualsDateTest(){
        DateTime checkInTime = new DateTime(14,40,2018,10,17);
        DateTime checkOutTime = new DateTime(14,40,2018,10,18);

        Assert.assertEquals(false,checkInTime.compareDateWithCheckOutDateTime(checkOutTime));
    }

    @Test
    public void pastFortyOneMinutesFromCheckInTime(){
        DateTime checkInTime = new DateTime(14,40,2018,10,17);
        DateTime checkOutTime = new DateTime(15,21,2018,10,17);

        Assert.assertEquals(true,checkInTime.compareTimeWithCheckOutDateTime(checkOutTime));
    }

    @Test
    public void pastFortyMinutesFromCheckInTime(){
        DateTime checkInTime = new DateTime(14,40,2018,10,17);
        DateTime checkOutTime = new DateTime(15,20,2018,10,17);

        Assert.assertEquals(true,checkInTime.compareTimeWithCheckOutDateTime(checkOutTime));
    }

    @Test
    public void pastThirtyNineMinutesFromCheckInTime(){
        DateTime checkInTime = new DateTime(14,40,2018,10,17);
        DateTime checkOutTime = new DateTime(15,19,2018,10,17);

        Assert.assertEquals(false,checkInTime.compareTimeWithCheckOutDateTime(checkOutTime));
    }

    @Test
    public void pastOneDayFromCheckInTime(){
        DateTime checkInTime = new DateTime(14,40,2018,10,17);
        DateTime checkOutTime = new DateTime(14,40,2018,10,18);

        Assert.assertEquals(false,checkInTime.compareDateWithCheckOutDateTime(checkOutTime));
    }

    @Test
    public void pastOneMonthFromCheckInTime(){
        DateTime checkInTime = new DateTime(14,40,2018,10,17);
        DateTime checkOutTime = new DateTime(14,40,2018,11,17);

        Assert.assertEquals(false,checkInTime.compareDateWithCheckOutDateTime(checkOutTime));
    }

    @Test
    public void pastOneYearFromCheckInTime(){
        DateTime checkInTime = new DateTime(14,40,2018,11,17);
        DateTime checkOutTime = new DateTime(14,40,2019,11,17);

        Assert.assertEquals(false,checkInTime.compareDateWithCheckOutDateTime(checkOutTime));
    }
}
