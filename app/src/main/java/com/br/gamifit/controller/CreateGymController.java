package com.br.gamifit.controller;

import android.content.Intent;
import android.view.View;

import com.br.gamifit.activity.CreateGymActivity;

public class CreateGymController {
    private static CreateGymController createGymController;
    private CreateGymActivity createGymActivity;

    private CreateGymController(CreateGymActivity createGymActivity){
        this.createGymActivity=createGymActivity;
        this.createGymActivity.setBtnLocalizationOnClickListener(btnLocalization);

    }

    public static CreateGymController getCreateGymController(CreateGymActivity createGymActivity) {
        if(createGymController==null){
            createGymController = new CreateGymController(createGymActivity);
        }
        return createGymController;
    }

    public void verifyHasLatitudeAndLongitudeValues(){
        Intent intent = createGymActivity.getIntent();
        if(intent.getBundleExtra("longitude")!=null && intent.getBundleExtra("longitude")!=null){
            //TODO: Study more about Bundle and define its title in order to get the content in it
        }
    }

    private View.OnClickListener btnLocalization = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            createGymActivity.openMapsActivity();
        }
    };
}
