package com.br.gamifit.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.br.gamifit.R;
import com.br.gamifit.controller.MyGymController;
import com.br.gamifit.helper.ItemOffsetDecoration;
import com.br.gamifit.model.Gym;


public class GymUserProfileListFragment extends Fragment {
    private MyGymController myGymController;

    public GymUserProfileListFragment(){
        myGymController = MyGymController.getMyGymController();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gym_user_profile_list,container,false);
        RecyclerView recyclerView = v.findViewById(R.id.gym_user_profile_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        ItemOffsetDecoration itemOffsetDecoration = new ItemOffsetDecoration(getContext(),R.dimen.item_offset_decoration);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(itemOffsetDecoration);
        recyclerView.setAdapter(myGymController.getGymUserProfileListAdapter());
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        myGymController.getAllGymUsersProfiles();
    }
}
