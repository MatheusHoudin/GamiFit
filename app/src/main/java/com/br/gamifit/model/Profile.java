package com.br.gamifit.model;

import com.br.gamifit.dao_factory.FirebaseFactory;
import com.br.gamifit.database.ProfileFirebaseDAO;
import com.br.gamifit.model.exception.LessThanFortyMinutesTrainingException;
import com.br.gamifit.model.exception.MoreThanOneCheckInOnOneDayException;

import org.threeten.bp.LocalDateTime;

import java.io.Serializable;
import java.util.Observable;

public class Profile extends Observable implements Serializable{
    private User user;
    private Gym gym;
    private String code;
    private Progress progress;
    private CheckInOut checkInOut;
    private boolean active;

    public Profile(){}

    public Profile(Gym gym,User user,Progress progress){
        this.setGym(gym);
        this.setUser(user);
        this.setProgress(progress);
        this.setActive(true);
    }

    public boolean save(){
        ProfileFirebaseDAO profileFirebaseDAO = FirebaseFactory.getProfileFirebaseDAO();
        return profileFirebaseDAO.createProfile(this);
    }

    public boolean updateOffensiveDaysNumber(){
        ProfileFirebaseDAO profileFirebaseDAO = FirebaseFactory.getProfileFirebaseDAO();
        return profileFirebaseDAO.updateOffensiveDays(this);
    }

    public Progress getProgress() {
        return progress;
    }

    public void setProgress(Progress progress) {
        this.progress = progress;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Gym getGym() {
        return gym;
    }

    public void setGym(Gym gym) {
        this.gym = gym;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public CheckInOut getCheckInOut() {
        return checkInOut;
    }

    public void setCheckInOut(CheckInOut checkInOut) {
        this.checkInOut = checkInOut;
    }

    @Override
    public boolean equals(Object obj) {
        return this.hashCode()==obj.hashCode();
    }

    @Override
    public int hashCode() {
        return gym.hashCode()+user.hashCode();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean verifyScannedCodeIsTheSameAsItsGym(String scannedCode){
        return gym.getCode().equals(scannedCode);
    }

    public void checkInCheckOut() throws  MoreThanOneCheckInOnOneDayException {
        DateTime actualDateTime = createActualDateTime();
        if(checkInOut!=null){
            if(checkInOut.isCheckIn()){
                if(checkInOut.getDateTime().compareDateWithCheckOutDateTime(actualDateTime)){

                }else{
                    checkInOut.checkIn(actualDateTime,this);
                }
            }
        }else{
            checkInOut.checkIn(actualDateTime,this);
        }
    }

    //TODO: See here if the function is updating the offensive days
    private void handleCheckOut(DateTime actualDateTime) throws LessThanFortyMinutesTrainingException {
        try {
            checkInOut.checkOut(actualDateTime,this);
            updateOffensiveDaysNumber();
        } catch (LessThanFortyMinutesTrainingException e) {
            throw e;
        }
    }

    public DateTime createActualDateTime(){
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTime actualDateTime = new DateTime();
        actualDateTime.setHour(localDateTime.getHour());
        actualDateTime.setMinutes(localDateTime.getMinute());
        actualDateTime.setMonthDay(localDateTime.getDayOfMonth());
        actualDateTime.setMonth(localDateTime.getMonthValue());
        actualDateTime.setYear(localDateTime.getYear());
        return actualDateTime;
    }


}
