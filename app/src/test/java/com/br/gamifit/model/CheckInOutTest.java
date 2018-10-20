package com.br.gamifit.model;

import com.br.gamifit.dao_factory.FirebaseFactory;
import com.br.gamifit.database.ProfileFirebaseDAO;
import com.br.gamifit.model.exception.LessThanFortyMinutesTrainingException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({FirebaseFactory.class,FirebaseDatabase.class})
public class CheckInOutTest {

    @Test(expected = LessThanFortyMinutesTrainingException.class)
    public void checkOutBefore40Minutes() throws LessThanFortyMinutesTrainingException {

        DateTime actualDateTime = mock(DateTime.class);
        when(actualDateTime.getHour()).thenReturn(10);
        when(actualDateTime.getMinutes()).thenReturn(30);
        when(actualDateTime.getYear()).thenReturn(2018);
        when(actualDateTime.getMonth()).thenReturn(10);
        when(actualDateTime.getMonthDay()).thenReturn(20);

        DateTime lastCheckInDateTime = mock(DateTime.class);
        when(lastCheckInDateTime.getHour()).thenReturn(10);
        when(lastCheckInDateTime.getMinutes()).thenReturn(20);
        when(lastCheckInDateTime.getYear()).thenReturn(2018);
        when(lastCheckInDateTime.getMonth()).thenReturn(10);
        when(lastCheckInDateTime.getMonthDay()).thenReturn(20);

        CheckInOut checkInOut = new CheckInOut();
        checkInOut.setDateTime(lastCheckInDateTime);
        checkInOut.setCheckIn(true);

        Profile profile = mock(Profile.class);
        when(profile.getCheckInOut()).thenReturn(checkInOut);

        checkInOut.checkOut(actualDateTime,profile);
    }

    @Test
    public void checkOutAfter40Minutes() throws LessThanFortyMinutesTrainingException {
        DateTime actualDateTime = mock(DateTime.class);

        when(actualDateTime.getHour()).thenReturn(11);
        when(actualDateTime.getMinutes()).thenReturn(00);


        DateTime lastCheckInDateTime = mock(DateTime.class);
        when(lastCheckInDateTime.getHour()).thenReturn(10);
        when(lastCheckInDateTime.getMinutes()).thenReturn(00);

        CheckInOut checkInOut = new CheckInOut();
        checkInOut.setDateTime(lastCheckInDateTime);
        checkInOut.setCheckIn(true);

        Profile profile = mock(Profile.class);
        when(profile.getCode()).thenReturn("fb9--80BygobiTF7");
        when(profile.getCheckInOut()).thenReturn(checkInOut);

        PowerMockito.mockStatic(FirebaseFactory.class);
        ProfileFirebaseDAO profileFirebaseDAO = mock(ProfileFirebaseDAO.class);
        DatabaseReference databaseReference = mock(DatabaseReference.class);
        FirebaseDatabase firebaseDatabase = mock(FirebaseDatabase.class);

        PowerMockito.mockStatic(FirebaseDatabase.class);
        when(FirebaseDatabase.getInstance()).thenReturn(firebaseDatabase);
        when(firebaseDatabase.getReference()).thenReturn(databaseReference);
        when(FirebaseFactory.getProfileFirebaseDAO()).thenReturn(profileFirebaseDAO);

        when(databaseReference.child("profile/"+profile.getCode()+"/progress")).thenReturn(databaseReference);
        when(databaseReference.setValue(profile)).thenReturn(null);

        Assert.assertEquals(true,lastCheckInDateTime.compareTimeWithCheckOutDateTime(actualDateTime));

//        checkInOut.checkOut(actualDateTime,profile);
//
//        Assert.assertEquals(false,checkInOut.isCheckIn());
//        Assert.assertEquals(actualDateTime,checkInOut.getDateTime());

    }
}



