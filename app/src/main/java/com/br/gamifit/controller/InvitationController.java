package com.br.gamifit.controller;

import android.content.Context;
import android.util.Log;

import com.br.gamifit.activity.InvitationActivity;
import com.br.gamifit.adapter.ReceivedInviteListAdapter;
import com.br.gamifit.dao_factory.FirebaseFactory;
import com.br.gamifit.database.InviteFirebaseDAO;
import com.br.gamifit.helper.MyPreferences;
import com.br.gamifit.model.GymInvite;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class InvitationController implements Observer {

    private InvitationActivity invitationActivity;
    private Context context;

    private ReceivedInviteListAdapter adapter;
    private List<GymInvite> invites;

    private static InvitationController invitationController;

    public InvitationController(InvitationActivity activity){
        invitationActivity = activity;
        context = invitationActivity.getApplicationContext();
        invites = new ArrayList<>();
        adapter = new ReceivedInviteListAdapter(context,invites);

        activity.getListViewInvites().setAdapter(adapter);
    }

    public static InvitationController getInvitationController(InvitationActivity activity) {
        if(invitationController==null){
            invitationController = new InvitationController(activity);
        }
        setUpObservable();
        invitationController.findMyInvites();
        return invitationController;
    }

    private static void setUpObservable(){
        InviteFirebaseDAO inviteFirebaseDAO = FirebaseFactory.getInviteFirebaseDAO();
        inviteFirebaseDAO.addObserver(invitationController);
    }

    public void findMyInvites(){
        String loggedUserCode = MyPreferences.getMyPreferences(context).getUserCode();
        InviteFirebaseDAO inviteFirebaseDAO = FirebaseFactory.getInviteFirebaseDAO();
        Log.i("logged code",loggedUserCode);
        invites.clear();
        inviteFirebaseDAO.getUserInvites(loggedUserCode);
    }

    public ReceivedInviteListAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void update(Observable observable, Object o) {
        if(o instanceof GymInvite){
            invites.add((GymInvite) o);
            adapter.notifyDataSetChanged();
        }
    }
}
