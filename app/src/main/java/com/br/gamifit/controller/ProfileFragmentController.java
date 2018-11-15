package com.br.gamifit.controller;

import android.content.Context;
import android.util.Log;

import com.br.gamifit.adapter.ProfileListAdapter;
import com.br.gamifit.dao_factory.FirebaseFactory;
import com.br.gamifit.database.ProfileFirebaseDAO;
import com.br.gamifit.fragment.ProfileListFragment;
import com.br.gamifit.helper.MyPreferences;
import com.br.gamifit.model.Profile;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ProfileFragmentController implements Observer{
    private Context context;
    private ProfileListFragment profileListFragment;

    private List<Profile> profileList;
    private ProfileListAdapter profileListAdapter;
    private static ProfileFragmentController profileFragmentController;

    private ProfileFragmentController(ProfileListFragment profileListFragment){
        this.setContext(profileListFragment.getContext());
        this.setProfileListFragment(profileListFragment);

        profileList = new ArrayList<>();
        profileListAdapter = new ProfileListAdapter(profileListFragment.getContext(),profileList);
    }

    public static ProfileFragmentController getProfileFragmentController(ProfileListFragment profileListFragment) {
        if(profileFragmentController==null){
            profileFragmentController = new ProfileFragmentController(profileListFragment);
        }
        setupObservable();
        return profileFragmentController;
    }

    private static void setupObservable(){
        FirebaseFactory.getProfileFirebaseDAO().addObserver(profileFragmentController);
    }
    public void getAllMyProfiles(){
        profileList.clear();
        ProfileFirebaseDAO profileFirebaseDAO = FirebaseFactory.getProfileFirebaseDAO();
        String loggedUserCode = new MyPreferences(context).getUserCode();
        profileFirebaseDAO.getAllMyProfiles(loggedUserCode);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setProfileListFragment(ProfileListFragment profileListFragment) {
        this.profileListFragment = profileListFragment;
    }

    @Override
    public void update(Observable observable, Object o) {
        if(o instanceof Profile){
            profileList.add((Profile) o);
            //TODO: See if we can optmize here, just by receiving an information that says all the data is loaded, and after it we can call the profileListAdapter.notifyDataSerChanged()
            profileListAdapter.notifyDataSetChanged();
        }
    }

    public ProfileListAdapter getProfileListAdapter() {
        return profileListAdapter;
    }
}
