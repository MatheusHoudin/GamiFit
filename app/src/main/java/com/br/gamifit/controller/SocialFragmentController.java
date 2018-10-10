package com.br.gamifit.controller;

import android.content.Context;
import android.util.Log;
import android.widget.BaseAdapter;

import com.br.gamifit.adapter.UserListAdapter;
import com.br.gamifit.dao_factory.FirebaseFactory;
import com.br.gamifit.database.UserFirebaseDAO;
import com.br.gamifit.fragment.UsersSocialFragment;
import com.br.gamifit.model.Gym;
import com.br.gamifit.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class SocialFragmentController implements Observer {
    private Context context;
    private UsersSocialFragment usersSocialFragment;
    private static SocialFragmentController socialFragmentController;

    private List<User> users;
    private UserListAdapter inviteListAdapter;


    private SocialFragmentController(UsersSocialFragment usersSocialFragment){
        this.setUsersSocialFragment(usersSocialFragment);
        this.setContext(usersSocialFragment.getContext());
        users = new ArrayList<>();
        inviteListAdapter = new UserListAdapter(this.getContext(),users);
    }

    public static SocialFragmentController getInstance(UsersSocialFragment usersSocialFragment){
        if(socialFragmentController==null){
            socialFragmentController = new SocialFragmentController(usersSocialFragment);
        }
        setUpObservable();
        return socialFragmentController;
    }

    private static void setUpObservable(){
        UserFirebaseDAO ufDAO = FirebaseFactory.getUserFirebaseDAO();
        ufDAO.addObserver(socialFragmentController);
    }

    public void sendInvite(User user){

    }

    public void getAllUsersToInvite(){
        users.clear();
        UserFirebaseDAO ufDAO = FirebaseFactory.getUserFirebaseDAO();
        ufDAO.getAllUsers();
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

    public UsersSocialFragment getUsersSocialFragment() {
        return usersSocialFragment;
    }

    public void setUsersSocialFragment(UsersSocialFragment usersSocialFragment) {
        this.usersSocialFragment = usersSocialFragment;
    }

    public UserListAdapter getInviteListAdapter() {
        return inviteListAdapter;
    }

    @Override
    public void update(Observable observable, Object o) {
        if(observable instanceof UserFirebaseDAO){
            if(o instanceof User){
                Log.i("tata","adding");
                User user = (User) o;
                users.add(user);
            }else if(o instanceof Integer){
                Log.i("tata","mee");
                if(((Integer) o).intValue()==UserFirebaseDAO.OPERATION_DONE_SUCESSFULLY.intValue()){
                    Log.i("tata","done");
                    inviteListAdapter.notifyDataSetChanged();
                }
            }
        }
    }
}
