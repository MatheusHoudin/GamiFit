package com.br.gamifit.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.br.gamifit.activity.CreateGymActivity;
import com.br.gamifit.helper.MyPreferences;
import com.br.gamifit.model.Gym;
import com.br.gamifit.model.Localization;
import com.br.gamifit.model.User;
import com.br.gamifit.model.exception.InvalidGymDataException;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

public class CreateGymController {
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
        if(createGymController==null){
            createGymController = new CreateGymController(createGymActivity);
        }
        return createGymController;
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
            Toast.makeText(createGymActivity.getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
            return false;
        }catch (Exception e) {
            Toast.makeText(createGymActivity.getApplicationContext(), "Um erro inesperado ocorreu", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public void createGym(){
        if(verifyHasAllDataToMakeTheGym()){
            User user = MyPreferences.getMyPreferences(createGymActivity.getApplicationContext()).getUser();
            gym.setGymOwner(user);
            Exception exception = gym.saveGym();
            if(exception==null){
                Toast.makeText(createGymActivity.getApplicationContext(),"Academia cadastrada com sucesso",Toast.LENGTH_LONG).show();
                createGymActivity.finish();
            }else{
                Toast.makeText(createGymActivity.getApplicationContext(),"Não foi possível " +
                        "cadastrar esta academia",Toast.LENGTH_LONG).show();
            }
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
}
