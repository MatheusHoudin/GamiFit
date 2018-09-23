package com.br.gamifit.controller;

import android.content.Context;

public class ProfileFragmentController {
    private Context context;
    private static ProfileFragmentController profileFragmentController;

    private ProfileFragmentController(Context context){
        this.setContext(context);
    }

    public static ProfileFragmentController getProfileFragmentController(Context context) {
        if(profileFragmentController==null){
            profileFragmentController = new ProfileFragmentController(context);
        }
        return profileFragmentController;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }
}
