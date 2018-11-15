package com.br.gamifit.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;

import com.br.gamifit.activity.CreateGymActivity;
import com.br.gamifit.activity.DashboardActivity;
import com.br.gamifit.activity.InvitationActivity;
import com.br.gamifit.activity.LoginActivity;
import com.br.gamifit.activity.MyGymListActivity;
import com.br.gamifit.helper.MyPreferences;
import com.br.gamifit.helper.QRCodeHelper;
import com.br.gamifit.model.User;
import com.google.firebase.auth.FirebaseAuth;

public class DashboardController {
    private DashboardActivity dashboardView;
    private Context context;

    private static DashboardController dashboardController;

    public DashboardController(DashboardActivity activity){
        this.dashboardView = activity;
        this.context = this.dashboardView.getApplicationContext();
    }

    public static DashboardController getDashboardController(DashboardActivity activity) {
        if(dashboardController==null){
            dashboardController = new DashboardController(activity);
        }
        return dashboardController;
    }

    public void showUserQRCodeBitMap(){
        User user = new MyPreferences(dashboardView).getUser();
        QRCodeHelper qrCodeHelper = new QRCodeHelper(600,600);
        qrCodeHelper.generateUserQRCodeAlertDialog(dashboardView,user.getCode());
    }

    public void openInvitesActivity(){
        Intent intent = new Intent(this.context, InvitationActivity.class);
        dashboardView.startActivity(intent);
    }

    public void openMyGymAticity(){
        Intent intent = new Intent(this.context, MyGymListActivity.class);
        dashboardView.startActivity(intent);
    }

    public void signOut(){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        MyPreferences myPreferences = new MyPreferences(dashboardView);
        myPreferences.clearData();
        openLoginActivity();

    }

    private void openLoginActivity(){
        Intent intent = new Intent(this.context, LoginActivity.class);
        dashboardView.startActivity(intent);
        dashboardView.finish();
    }

    public void openCreateGymActivity(){
        Intent intent = new Intent(this.context, CreateGymActivity.class);
        dashboardView.startActivity(intent);
    }

    private View.OnClickListener floatingButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            openCreateGymActivity();
        }
    };

    public View.OnClickListener getFloatingButtonOnClickListener() {
        return floatingButtonOnClickListener;
    }
}
