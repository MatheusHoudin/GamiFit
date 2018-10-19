package com.br.gamifit.model;

import com.br.gamifit.model.exception.LessThanFortyMinutesTrainingException;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CheckInTest {

    @Test(expected = LessThanFortyMinutesTrainingException.class)
    public void checkOutBefore40Minutes(){
        CheckInOut checkIn = new CheckInOut();

        Profile profile = mock(Profile.class);
        when(profile.getCheckIn()).thenReturn(checkIn);

//        DateTime dateTime = new DateTime()
//        checkIn.checkOut();

    }
}
