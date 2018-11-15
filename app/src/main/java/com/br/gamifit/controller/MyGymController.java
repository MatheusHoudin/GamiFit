package com.br.gamifit.controller;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.br.gamifit.R;
import com.br.gamifit.activity.MyGymActivity;
import com.br.gamifit.adapter.GymUserProfileListAdapter;
import com.br.gamifit.dao_factory.FirebaseFactory;
import com.br.gamifit.database.ProfileFirebaseDAO;
import com.br.gamifit.database.UserFirebaseDAO;
import com.br.gamifit.helper.QRCodeHelper;
import com.br.gamifit.helper.ScanHelper;
import com.br.gamifit.model.Gym;
import com.br.gamifit.model.ObserverResponse;
import com.br.gamifit.model.Profile;
import com.br.gamifit.model.User;
import com.google.zxing.integration.android.IntentIntegrator;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MyGymController implements Observer{
    private static MyGymController myGymController;

    private Gym gym;
    private MyGymActivity myGymActivity;

    private List<Profile> gymUserProfileList;
    private GymUserProfileListAdapter gymUserProfileListAdapter;

    private MyGymController(){
        this.gymUserProfileList = new ArrayList<>();
        gymUserProfileListAdapter = new GymUserProfileListAdapter(gymUserProfileList,myGymActivity);
    }

    public static MyGymController getGymController() {
        if(myGymController ==null){
            myGymController = new MyGymController();
            setupObservable();
        }
        return myGymController;
    }

    public static MyGymController getMyGymController(){
        if(myGymController == null){
            myGymController = new MyGymController();
            setupObservable();
        }
        return myGymController;
    }

    private static void setupObservable(){
        FirebaseFactory.getUserFirebaseDAO().addObserver(myGymController);
        FirebaseFactory.getProfileFirebaseDAO().addObserver(myGymController);
    }

    public void getAllGymUsersProfiles(){
        gymUserProfileList.clear();
        FirebaseFactory.getProfileFirebaseDAO().getGymUserProfiles(gym.getCode());
    }

    /**
     * This method configures an QRCode reader, it sets its title,camera and so on
     * When this code is performed the front camera is opened and you can scan an QRCode
     * The result of it is sent to MyGymActivity(myGymActivity below)
     */
    public void openScanCodeActivity(){
        int backCamera = 0;
        ScanHelper scanHelper = new ScanHelper(backCamera,myGymActivity
                ,myGymActivity.getString(R.string.send_invite_to_user_scan_prompt));

        scanHelper.showScan();
    }

    public void sendInviteToUser(String scannedUserCode){
        UserFirebaseDAO userFirebaseDAO = FirebaseFactory.getUserFirebaseDAO();
        userFirebaseDAO.getScannedUser(scannedUserCode);
    }

    private void createAndSendInviteToUser(User userToInvite){
        boolean result = gym.sendInviteToJoin(userToInvite);
        if(result){
            myGymActivity.showToastMessage(myGymActivity.getString(R.string.invite_sent_succesfully));
        }else{
            myGymActivity.showToastMessage(myGymActivity.getString(R.string.invite_sent_unsuccesfully));
        }
    }

    public void showUserQRCodeBitMap(){
        QRCodeHelper qrCodeHelper = new QRCodeHelper(600,600);
        qrCodeHelper.generateUserQRCodeAlertDialog(myGymActivity,gym.getCode());
    }
    @Override
    public void update(Observable observable, Object o) {
        if(o instanceof ObserverResponse){
            ObserverResponse response = (ObserverResponse) o;
            if(observable instanceof UserFirebaseDAO){

                if(response.getMethod().equals("getScannedUser")){
                    User userToInvite = (User) response.getContent();
                    createAndSendInviteToUser(userToInvite);
                }
            }else if(observable instanceof ProfileFirebaseDAO){
                if(response.getMethod().equals("getGymUserProfiles")){
                    Log.i("update","item caught");
                    gymUserProfileList.add((Profile) response.getContent());
                    gymUserProfileListAdapter.notifyDataSetChanged();
                }
            }

        }
    }

    public GymUserProfileListAdapter getGymUserProfileListAdapter() {
        return gymUserProfileListAdapter;
    }

    public void setGym(Gym gym) {
        this.gym = gym;
    }

    public void setMyGymActivity(MyGymActivity myGymActivity) {
        this.myGymActivity = myGymActivity;
    }
}
