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
        this.progress.setOffensiveDays(this.progress.getOffensiveDays()+1);
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

    /**
     * @throws MoreThanOneCheckInOnOneDayException
     * @throws LessThanFortyMinutesTrainingException
     * This method is responsible for the check in and check out logic, it checks whether the check in
     * is already made, then if yes, it calls handleCheckInCheckOutWhenCheckInIsAlreadyMade to handle
     * this situation, otherwise if check out is already made it calls handleCheckInCheckOutWhenCheckOutIsAlreadyMade,
     * if its the first time the user is trying to do check in or check out it will initialize this.checkInOut and
     * make the first check in.
     */
    public void checkInCheckOut() throws MoreThanOneCheckInOnOneDayException, LessThanFortyMinutesTrainingException {
        DateTime actualDateTime = createActualDateTime();
        if(checkInOut!=null){
            if(checkInOut.isCheckIn()){
                handleCheckInCheckOutWhenCheckInIsAlreadyMade(actualDateTime);
            }else{
                handleCheckInCheckOutWhenCheckOutIsAlreadyMade(actualDateTime);
            }
        }else{
            handleFirstUserCheckIn(actualDateTime);
        }
    }

    /**
     * @param actualDateTime
     * @throws MoreThanOneCheckInOnOneDayException
     * It handles the first user check in on the profile, initializing the variable checkInOut in order
     * for it to be saved on the database and to be caught the next time the user logs in the profile
     */
    private void handleFirstUserCheckIn(DateTime actualDateTime) throws MoreThanOneCheckInOnOneDayException {
        checkInOut = new CheckInOut();
        checkInOut.checkIn(actualDateTime,this);
    }

    /**
     * @param actualDateTime
     * @throws LessThanFortyMinutesTrainingException
     * @throws MoreThanOneCheckInOnOneDayException
     * It handles when there is already a check in on this profile and the user tries to scan the Gym QRCode,
     * it will see if the user is trying to do a check out, therefore it has to be on the same last check in date and time,
     * otherwise the user will have to do a new check in, because its another day.
     */
    private void handleCheckInCheckOutWhenCheckInIsAlreadyMade(DateTime actualDateTime) throws LessThanFortyMinutesTrainingException, MoreThanOneCheckInOnOneDayException {
        if(checkInOut.getDateTime().compareDateWithCheckOutDateTime(actualDateTime)){
            handleCheckOut(actualDateTime);
        }else{
            checkInOut.checkIn(actualDateTime,this);
        }
    }

    /**
     * @param actualDateTime
     * @throws MoreThanOneCheckInOnOneDayException
     * It handles when there is already a check out on this profile and the user tries to scan the Gym QRCode,
     * it will see if the user is trying to do a check in, therefore it has to be on at least one day next to the
     * last check in day, otherwise if the user is trying to do a check in on the same day as the last check out
     * it will throw MoreThanOneCheckInOnOneDayException, because the user is allowed to do a check in and check
     * out once a day.
     */
    private void handleCheckInCheckOutWhenCheckOutIsAlreadyMade(DateTime actualDateTime) throws MoreThanOneCheckInOnOneDayException {
        if(checkInOut.getDateTime().compareDateWithCheckOutDateTime(actualDateTime)){
            throw new MoreThanOneCheckInOnOneDayException();
        }else{
            checkInOut.checkIn(actualDateTime,this);
        }
    }

    /**
     * @param actualDateTime
     * @throws LessThanFortyMinutesTrainingException
     * It makes the check out function works, it saves the check out on the database and update the user's offensive days
     * by calling the updateOffensiveDaysNumber() profile method.
     */
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
