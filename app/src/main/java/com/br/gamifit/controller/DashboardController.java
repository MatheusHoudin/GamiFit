package com.br.gamifit.controller;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;

import com.br.gamifit.activity.CreateGymActivity;
import com.br.gamifit.activity.DashboardActivity;
import com.br.gamifit.activity.InvitationActivity;

public class DashboardController {
    private DashboardActivity dashboardView;
    private Context context;

    private static DashboardController dashboardController;

    public DashboardController(DashboardActivity activity){
        dashboardView = activity;
        this.context = activity.getApplicationContext();
    }

    public static DashboardController getDashboardController(DashboardActivity activity) {
        if(dashboardController==null){
            dashboardController = new DashboardController(activity);
        }
        return dashboardController;
    }

    public void openInvitesActivity(){
        Intent intent = new Intent(dashboardView, InvitationActivity.class);
        dashboardView.startActivity(intent);
    }

    public void openCreateGymActivity(){
        Intent intent = new Intent(dashboardView, CreateGymActivity.class);
        dashboardView.startActivity(intent);
    }

    private View.OnClickListener floatingButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            openCreateGymActivity();
        }
    };

}
