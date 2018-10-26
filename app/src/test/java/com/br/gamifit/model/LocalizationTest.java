package com.br.gamifit.model;

import org.junit.Assert;
import org.junit.Test;

import java.text.DecimalFormat;

public class LocalizationTest {

    @Test
    public void calculateDistance(){
        Localization localization = new Localization();
        localization.setLongitude(-4.830873);
        localization.setLatitude(-37.781289);
        Localization otherLocalization = new Localization();
        otherLocalization.setLongitude(-4.831532);
        otherLocalization.setLatitude(-37.781043);

        Assert.assertEquals(0.0641218463381177,localization.calculateDistance(otherLocalization),0);
        // The three values after the dot means meters, 0.0641218463381177 for example has a distance of 64 meters
    }
}
