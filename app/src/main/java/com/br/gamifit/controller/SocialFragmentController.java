package com.br.gamifit.controller;

import android.content.Context;
import android.widget.BaseAdapter;

import com.br.gamifit.dao_factory.FirebaseFactory;
import com.br.gamifit.database.UserFirebaseDAO;
import com.br.gamifit.model.Gym;
import com.br.gamifit.model.User;

import java.util.List;

public class SocialFragmentController {
    private Context context;
    private static SocialFragmentController controller;

    private User user;

    private SocialFragmentController(Context context, User user){
        this.setContext(context);
        this.setUser(user);
    }

    public static SocialFragmentController getInstance(Context context, User user){
        if(controller==null){
            controller = new SocialFragmentController(context,user);
        }
        return controller;
    }

    public void sendInvite(User user){

    }

    public void getAllUsersToInvite(List<User> usersList, BaseAdapter listAdapter){
        UserFirebaseDAO ufDAO = FirebaseFactory.getUserFirebaseDAO();
        ufDAO.getAllUsers(usersList,listAdapter);
    }

    public User getLoggedUser(){
        UserFirebaseDAO ufDAO = FirebaseFactory.getUserFirebaseDAO();
        //User user = ufDAO.getUser();
        return  null;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
