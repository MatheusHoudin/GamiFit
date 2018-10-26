package com.br.gamifit.controller;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.br.gamifit.R;
import com.br.gamifit.activity.GymProfileActivity;
import com.br.gamifit.helper.ScanHelper;
import com.br.gamifit.model.Gym;
import com.br.gamifit.model.Localization;
import com.br.gamifit.model.Profile;
import com.br.gamifit.model.exception.LessThanFortyMinutesTrainingException;
import com.br.gamifit.model.exception.MoreThanOneCheckInOnOneDayException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.Observable;
import java.util.Observer;

public class GymProfileController implements Observer {
    private static GymProfileController gymProfileController;

    private Profile profile;
    private GymProfileActivity gymProfileActivity;
    public static final int REQUEST_LOCATION_SERVICES = 1;

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

    /**
     * It opens the scan code camera using the back camera of a device in order to read a Gym QRCode.
     * When a QRCode is read its data is sent to the gymProfileActivity object and there it uses
     * the retrieved code to do check in or check out.
     */
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

    /**
     * @param scannedUserCode
     * This method handle the check in and check out logic calling other methods. It uses the
     * scannedUserCode to verify whether this code is the same as the gym one related to this
     * profile, if so it processes the check in/out using the user's current location to compare with
     * the gym one using
     */
    public void handleCheckInCheckOut(String scannedUserCode){
        if(profile.verifyScannedCodeIsTheSameAsItsGym(scannedUserCode)){
            FusedLocationProviderClient locationProviderClient = LocationServices.getFusedLocationProviderClient(gymProfileActivity);
            if(checkAccessFineCoarsePermission()){
                locationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(location!=null){
                            Localization localization = createLocalizationWithLatAndLong(location);
                            if(localization.calculateDistance(profile.getGym().getLocalization())<=Gym.MAXIMUM_DISTANCE_TO_CHECKINOUT){
                                checkInOut();
                            }else{
                                gymProfileActivity.showToastMessage(gymProfileActivity.getString(R.string.user_is_far_away_from_gym));
                            }
                        }else{
                            gymProfileActivity.showToastMessage(gymProfileActivity.getString(R.string.could_not_retrieve_user_location));
                        }
                    }
                });
            }
        }else{
            gymProfileActivity.showToastMessage(gymProfileActivity.getString(R.string.scanned_code_not_equals_to_gym_code));
        }
    }

    /**
     * This method calls the Profile method called checkInCheckOut() which is responsible for handling
     * the logic to check in and check out on a gym. That method may throw a LessThanFortyMinutesTrainingException
     * or MoreThanOneCheckInOnOneDayException, therefore this method sends a personalized message to the user
     * whenever one of those Exceptions is thrown.
     */
    private void checkInOut(){
        try {
            profile.checkInCheckOut();
        } catch (LessThanFortyMinutesTrainingException e) {
            gymProfileActivity.showToastMessage(gymProfileActivity.getString(R.string.exercising_for_less_than_forty_minutes));
        } catch (MoreThanOneCheckInOnOneDayException e) {
            gymProfileActivity.showToastMessage(gymProfileActivity.getString(R.string.trying_to_checkin_twice_a_day));
        }
    }

    /**
     * @param location
     * @return localization
     * It receives a Location object, which is a classe from the google location API, it makes a
     * Localization object using Latitude and Longitude from the google class.
     */
    private Localization createLocalizationWithLatAndLong(Location location){
        Localization localization = new Localization();
        localization.setLongitude(location.getLongitude());
        localization.setLatitude(location.getLatitude());
        return localization;
    }

    /**
     * @return boolean
     * Since the ACCESS_COARSE_LOCATION and ACCESS_FINE_LOCATION is required to retrieve the user's
     * location we verify here whether they are already allowed, if so, this method returns true,
     * otherwise we request the user to allow the permissions
     */
    public boolean checkAccessFineCoarsePermission(){
        if(ContextCompat.checkSelfPermission(gymProfileActivity,Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(gymProfileActivity,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            return true;
        }else{
            ActivityCompat.requestPermissions(gymProfileActivity,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_SERVICES);
            return false;
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        if(observable instanceof Profile){
            this.updateOffensiveDaysCount();
        }
    }
}
