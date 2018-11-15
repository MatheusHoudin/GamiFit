package com.br.gamifit.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.br.gamifit.R;
import com.br.gamifit.adapter.GymUserProfileListAdapter;
import com.br.gamifit.adapter.ProfileListAdapter;
import com.br.gamifit.controller.ProfileFragmentController;
import com.br.gamifit.model.Gym;
import com.br.gamifit.model.Profile;
import com.br.gamifit.model.Progress;
import com.br.gamifit.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileListFragment extends Fragment {

    private ProfileFragmentController profileFragmentController;


    public ProfileListFragment(){
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        profileFragmentController = ProfileFragmentController.getProfileFragmentController(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        RecyclerView profilesListView = v.findViewById(R.id.profile_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        profilesListView.setLayoutManager(linearLayoutManager);
        profilesListView.setAdapter(profileFragmentController.getProfileListAdapter());
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        profileFragmentController.getAllMyProfiles();
    }
}
