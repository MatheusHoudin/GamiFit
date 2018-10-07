package com.br.gamifit.controller;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;

import com.br.gamifit.activity.CreateGymActivity;
import com.br.gamifit.activity.DashboardActivity;
import com.br.gamifit.activity.InvitationActivity;
import com.br.gamifit.activity.MyGymActivity;

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

    public void openInvitesActivity(){
        Intent intent = new Intent(this.context, InvitationActivity.class);
        dashboardView.startActivity(intent);
    }

    public void openMyGymAticity(){
        Intent intent = new Intent(this.context, MyGymActivity.class);
        dashboardView.startActivity(intent);
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
