package com.br.gamifit.controller;

import android.content.Context;
import android.support.annotation.NonNull;

import com.br.gamifit.activity.MyGymActivity;
import com.br.gamifit.model.Gym;
import com.google.zxing.integration.android.IntentIntegrator;

public class MyGymController {
    private static MyGymController myGymController;

    private Gym gym;
    private Context context;
    private MyGymActivity myGymActivity;

    private MyGymController(@NonNull MyGymActivity myGymActivity, Gym gym){
        this.myGymActivity = myGymActivity;
        this.context = myGymActivity.getApplicationContext();
        this.gym = gym;
    }

    public static MyGymController getGymController(MyGymActivity myGymActivity, Gym gym) {
        if(myGymController ==null){
            myGymController = new MyGymController(myGymActivity,gym);
        }
        return myGymController;
    }

    public void openScanCodeActivity(){
        IntentIntegrator integrator = new IntentIntegrator(myGymActivity);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setPrompt("Escaneie o código de um usuário");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();
    }
}
