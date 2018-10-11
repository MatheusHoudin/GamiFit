package com.br.gamifit.controller;

import com.br.gamifit.R;
import com.br.gamifit.activity.GymProfileActivity;
import com.br.gamifit.dao_factory.FirebaseFactory;
import com.br.gamifit.database.ProfileFirebaseDAO;
import com.br.gamifit.helper.MyPreferences;
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
        IntentIntegrator integrator = new IntentIntegrator(gymProfileActivity);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setPrompt("Check in / Check out");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();
    }

    public void handleCheckInCheckOut(String scannedUserCode){
        boolean checkInOut = profile.isCheckInOut();
        if(!checkInOut){
            profile.setCheckInOut(true);
            gymProfileActivity.showMessage("Check in efetuado com sucesso");
        }else{
            gymProfileActivity.showMessage("Check out efetuado com sucesso");
            profile.setCheckInOut(false);
            ProfileFirebaseDAO profileFirebaseDAO = FirebaseFactory.getProfileFirebaseDAO();
            profileFirebaseDAO.updateOffensiveDays(profile);
        }
    }
}
