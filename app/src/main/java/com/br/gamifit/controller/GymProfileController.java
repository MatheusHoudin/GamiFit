package com.br.gamifit.controller;

import android.util.Log;

import com.br.gamifit.R;
import com.br.gamifit.activity.GymProfileActivity;
import com.br.gamifit.helper.ScanHelper;
import com.br.gamifit.model.Profile;
import com.br.gamifit.model.exception.LessThanFortyMinutesTrainingException;
import com.br.gamifit.model.exception.MoreThanOneCheckInOnOneDayException;

import java.util.Observable;
import java.util.Observer;

public class GymProfileController implements Observer {
    private static GymProfileController gymProfileController;

    private Profile profile;
    private GymProfileActivity gymProfileActivity;

    private GymProfileController(Profile profile,GymProfileActivity gymProfileActivity){
        this.profile = profile;
        this.gymProfileActivity = gymProfileActivity;
    }

    public static GymProfileController getGymProfileController(GymProfileActivity gymProfileActivity,Profile profile) {
        if(gymProfileController==null){
            gymProfileController = new GymProfileController(profile,gymProfileActivity);
        }
        profile.addObserver(gymProfileController);
        return gymProfileController;
    }

    public void openScanCodeActivity(){
        int backCamera = 0;
        ScanHelper scanHelper = new ScanHelper(backCamera,gymProfileActivity,gymProfileActivity
                .getString(R.string.checkin_checkout_scan_prompt));

        scanHelper.showScan();
    }

    public void updateOffensiveDaysCount(){
        Log.i("COUNTTT:NDAYS",String.valueOf(profile.getProgress().getOffensiveDays()));
        gymProfileActivity.setCount(String.valueOf(profile.getProgress().getOffensiveDays()));
    }

    //TODO: Increase the offensive days based on the check out
    public void handleCheckInCheckOut(String scannedUserCode){
        if(profile.verifyScannedCodeIsTheSameAsItsGym(scannedUserCode)){
            try {
                profile.checkInCheckOut();
            } catch (LessThanFortyMinutesTrainingException e) {
                gymProfileActivity.showToastMessage(gymProfileActivity.getString(R.string.exercising_for_less_than_forty_minutes));
            } catch (MoreThanOneCheckInOnOneDayException e) {
                gymProfileActivity.showToastMessage(gymProfileActivity.getString(R.string.trying_to_checkin_twice_a_day));
            }
        }else{
            gymProfileActivity.showToastMessage(gymProfileActivity.getString(R.string.scanned_code_not_equals_to_gym_code));
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        if(observable instanceof Profile){
            this.updateOffensiveDaysCount();
        }
    }
}
