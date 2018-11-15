package com.br.gamifit.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.br.gamifit.R;
import com.br.gamifit.activity.CreateGymActivity;
import com.br.gamifit.dao_factory.FirebaseFactory;
import com.br.gamifit.database.GymFirebaseDAO;
import com.br.gamifit.helper.MyPreferences;
import com.br.gamifit.model.Gym;
import com.br.gamifit.model.Localization;
import com.br.gamifit.model.ObserverResponse;
import com.br.gamifit.model.User;
import com.br.gamifit.model.exception.InvalidGymDataException;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.Observable;
import java.util.Observer;

public class CreateGymController implements Observer {
    private static CreateGymController createGymController;
    private CreateGymActivity createGymActivity;
    public static final int PLACE_PICKER_REQUEST = 1;

    private Gym gym;
    private Place gymPlace;

    private CreateGymController(CreateGymActivity createGymActivity){
        this.createGymActivity=createGymActivity;
        this.createGymActivity.setBtnCreateGymOnClickListener(btnCreateGymOnClickListener);
        this.createGymActivity.setBtnLocalizationOnClickListener(btnLocalizationOnClickListener);
    }

    public static CreateGymController getCreateGymController(CreateGymActivity createGymActivity) {
        if(createGymController==null) {
            createGymController = new CreateGymController(createGymActivity);
            setUpObservable();
        }
        return createGymController;
    }

    private static void setUpObservable(){
        FirebaseFactory.getGymFirebaseDAO().addObserver(createGymController);
    }

    private View.OnClickListener btnCreateGymOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            createGym();
        }
    };

    private View.OnClickListener btnLocalizationOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            accessLocalization();
        }
    };

    private boolean verifyHasAllDataToMakeTheGym(){
        String gymName = createGymActivity.getGymName().getText().toString();
        double latitude = gymPlace.getLatLng().latitude;
        double longitude = gymPlace.getLatLng().longitude;
        String address = gymPlace.getAddress().toString();

        Localization localization = new Localization(latitude,longitude,address);
        try{
            this.gym = new Gym(gymName,localization);
        }catch(InvalidGymDataException e){
            createGymActivity.showToastMessage(e.getMessage());
            return false;
        }catch (Exception e) {
            createGymActivity.showToastMessage(createGymActivity.getString(R.string.unexpected_error_message));
            return false;
        }
        return true;
    }

    public void createGym(){
        if(verifyHasAllDataToMakeTheGym()){
            User user = new MyPreferences(createGymActivity).getUser();
            gym.setGymOwner(user);
            gym.saveGym();
        }
    }

    public void accessLocalization(){
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            createGymActivity.startActivityForResult(builder.build(createGymActivity), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    public void setGymPlace(Place gymPlace) {
        this.gymPlace = gymPlace;
    }

    @Override
    public void update(Observable observable, Object o) {
        if(observable instanceof GymFirebaseDAO){
            if(o instanceof ObserverResponse){
                ObserverResponse response = (ObserverResponse) o;
                switch (response.getMethod()){
                    case "createGym":
                        handleCreateGymResult((Exception) response.getContent());
                        break;
                }
            }
        }
    }

    private void handleCreateGymResult(Exception exception){
        if(exception==null){
            createGymActivity.showToastMessage(createGymActivity.getString(R.string.gym_created_sucessfully));
            createGymActivity.finish();
        }else{
            createGymActivity.showToastMessage(createGymActivity.getString(R.string.gym_created_not_sucessfully));
        }
    }
}
