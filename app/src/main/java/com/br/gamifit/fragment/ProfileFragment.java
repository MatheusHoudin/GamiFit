package com.br.gamifit.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.br.gamifit.R;
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
public class ProfileFragment extends Fragment {
    private List<Profile> profiles;

    private ProfileFragmentController profileFragmentController;

    public ProfileFragment(){
        profiles = new ArrayList<>();
        profileFragmentController = ProfileFragmentController.getProfileFragmentController(getContext());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        RecyclerView profilesListView = v.findViewById(R.id.profile_list);
        ProfileListAdapter profileListAdapter = new ProfileListAdapter(getContext(),profiles);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        profilesListView.setLayoutManager(linearLayoutManager);
        profilesListView.setAdapter(profileListAdapter);
        initializeItems();
        profileListAdapter.notifyDataSetChanged();
        return v;
    }

    private void initializeItems(){
        Gym gym = new Gym("Arena","pabf44fre");
        Gym gym1 = new Gym("Iron","pabf44fre");
        Gym gym2 = new Gym("Porra Birl","pabf44fre");

        Profile profile = new Profile(gym,null,new Progress(3));
        Profile profile1 = new Profile(gym1,null,new Progress(7));
        Profile profile2 = new Profile(gym2,null,new Progress(1));
        profiles.add(profile);
        profiles.add(profile1);
        profiles.add(profile2);
    }

}
