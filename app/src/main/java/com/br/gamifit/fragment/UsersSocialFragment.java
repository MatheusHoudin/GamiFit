package com.br.gamifit.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.br.gamifit.R;
import com.br.gamifit.adapter.UserListAdapter;
import com.br.gamifit.controller.SocialFragmentController;
import com.br.gamifit.model.User;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

public class UsersSocialFragment extends android.support.v4.app.Fragment {
    private SocialFragmentController controller;
    private MaterialSearchView materialSearchView;

    public UsersSocialFragment(){
        controller = SocialFragmentController.getInstance(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.users_social_fragment_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.social_ic_search);
        menuItem.setVisible(false);
        materialSearchView.setMenuItem(menuItem);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.users_social_fragment,container,false);

        RecyclerView recyclerView = view.findViewById(R.id.social_list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(controller.getInviteListAdapter());

        materialSearchView = view.findViewById(R.id.search_view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        controller.getAllUsersToInvite();
    }
}
