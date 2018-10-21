package com.br.gamifit.model;

import com.br.gamifit.dao_factory.FirebaseFactory;
import com.br.gamifit.database.ProfileFirebaseDAO;
import com.br.gamifit.model.exception.LessThanFortyMinutesTrainingException;
import com.br.gamifit.model.exception.MoreThanOneCheckInOnOneDayException;

import java.io.Serializable;

public class CheckInOut implements Serializable {
    private DateTime dateTime;
    private boolean isCheckIn;

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isCheckIn() {
        return isCheckIn;
    }

    public void setCheckIn(boolean checkIn) {
        isCheckIn = checkIn;
    }

    public void checkOut(DateTime checkOutDateTime, Profile profile) throws LessThanFortyMinutesTrainingException {
        if(getDateTime().compareTimeWithCheckOutDateTime(checkOutDateTime)){
            this.setCheckIn(false);
            this.setDateTime(checkOutDateTime);
            ProfileFirebaseDAO profileFirebaseDAO = FirebaseFactory.getProfileFirebaseDAO();
            profileFirebaseDAO.checkInOut(profile);
            //TODO: Make here a way to update the offensive days, but take notice whether the removeCheckInData was sucessfully or not
        }else{
            throw new LessThanFortyMinutesTrainingException();
        }
    }

    public void checkIn(DateTime checkInDateTime, Profile profile) throws MoreThanOneCheckInOnOneDayException {
        if(dateTime!=null && dateTime.compareDateWithCheckOutDateTime(checkInDateTime)){
            throw new MoreThanOneCheckInOnOneDayException();
        }else{
            this.setCheckIn(true);
            this.setDateTime(checkInDateTime);
            ProfileFirebaseDAO profileFirebaseDAO = FirebaseFactory.getProfileFirebaseDAO();
            profileFirebaseDAO.checkInOut(profile);
        }
    }
}
