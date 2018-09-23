package com.br.gamifit.controller;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

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

    private MenuItem.OnMenuItemClickListener invitesMenuItemClickListener = new MenuItem.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            Intent intent = new Intent(dashboardView, InvitationActivity.class);
            dashboardView.startActivity(intent);
            return true;
        }
    };

    public MenuItem.OnMenuItemClickListener getInvitesMenuItemClickListener() {
        return invitesMenuItemClickListener;
    }
}
