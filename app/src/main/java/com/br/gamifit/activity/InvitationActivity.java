package com.br.gamifit.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

import com.br.gamifit.R;
import com.br.gamifit.adapter.ReceivedInviteListAdapter;
import com.br.gamifit.controller.InvitationController;
import com.br.gamifit.model.Gym;
import com.br.gamifit.model.GymInvite;
import com.br.gamifit.model.User;

import java.util.ArrayList;
import java.util.List;

public class InvitationActivity extends AppCompatActivity {

    private ListView listViewInvites;

    private InvitationController invitationController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation);

        listViewInvites = findViewById(R.id.invitation_list);

        Toolbar toolbar = findViewById(R.id.invitation_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        invitationController = InvitationController.getInvitationController();
        invitationController.setContext(getApplicationContext());
        invitationController.setInvitationActivity(this);
        invitationController.initListAdapter();
        this.listViewInvites.setAdapter(invitationController.getAdapter());
    }


    @Override
    protected void onStart() {
        super.onStart();
        invitationController.findMyInvites();
    }
}
