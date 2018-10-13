package com.br.gamifit.controller;

import com.br.gamifit.R;
import com.br.gamifit.activity.GymProfileActivity;
import com.br.gamifit.dao_factory.FirebaseFactory;
import com.br.gamifit.database.ProfileFirebaseDAO;
import com.br.gamifit.helper.MyPreferences;
import com.br.gamifit.helper.ScanHelper;
import com.br.gamifit.model.Profile;
import com.google.zxing.integration.android.IntentIntegrator;

public class GymProfileController {
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
        return gymProfileController;
    }

    public void openScanCodeActivity(){
        int backCamera = 0;
        ScanHelper scanHelper = new ScanHelper(backCamera,gymProfileActivity,gymProfileActivity
                .getString(R.string.checkin_checkout_scan_prompt));

        scanHelper.showScan();
    }

    public void handleCheckInCheckOut(String scannedUserCode){
        if(profile.verifyScannedCodeIsTheSameAsItsGym(scannedUserCode)){
            boolean checkInOut = profile.isCheckInOut();
            if(!checkInOut){
                handleCheckIn();
            }else{
                handleCheckOut();
            }
        }else{
            gymProfileActivity.showToastMessage(gymProfileActivity.getString(R.string.scanned_code_not_equals_to_gym_code));
        }
    }

    private void handleCheckOut(){
        gymProfileActivity.showToastMessage(gymProfileActivity.getString(R.string.successful_checkout));
        //TODO: Make something here that shows the user that he/she achieved the day goal(plus one offensive day)
        profile.setCheckInOut(false);
        profile.updateOffensiveDaysNumber();
    }

    private void handleCheckIn(){
        profile.setCheckInOut(true);
        gymProfileActivity.showToastMessage(gymProfileActivity.getString(R.string.successful_checkin));
    }
}
