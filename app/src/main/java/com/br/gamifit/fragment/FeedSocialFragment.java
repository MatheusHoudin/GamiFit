package com.br.gamifit.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.br.gamifit.R;
import com.br.gamifit.controller.SocialFragmentController;
import com.br.gamifit.model.Gym;
import com.br.gamifit.model.User;

import java.util.ArrayList;
import java.util.List;

public class FeedSocialFragment extends Fragment {
    private List<User> users;



    private SocialFragmentController controller;


    public FeedSocialFragment() {
        Gym gym = new Gym("Arena Fitness","f9r4fn4o4");
        controller = SocialFragmentController.getInstance(getContext(),new User());
        users = new ArrayList<>();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.social_fragment, container, false);

        return view;
    }

}