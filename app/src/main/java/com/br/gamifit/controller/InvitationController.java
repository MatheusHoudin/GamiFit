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
        return invitationController;
    }

    private static void setUpObservable(){
        InviteFirebaseDAO inviteFirebaseDAO = FirebaseFactory.getInviteFirebaseDAO();
        inviteFirebaseDAO.addObserver(invitationController);
    }

    public void findMyInvites(){
        String loggedUserCode = MyPreferences.getMyPreferences(context).getUserCode();
        InviteFirebaseDAO inviteFirebaseDAO = FirebaseFactory.getInviteFirebaseDAO();
        //TODO: Analize here what happens when the line below is executed, maybe we want it to execute, because it'll clean the list before loading all the itens again
//        invites.clear();
        inviteFirebaseDAO.getUserInvites(loggedUserCode);
    }

    public ReceivedInviteListAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void update(Observable observable, Object o) {
        if(o instanceof GymInvite){
            invites.add((GymInvite) o);
            //TODO: See if we can optmize here, just by receiving an information that says all the data is loaded, and after it we can call the adapter.notifyDataSerChanged()
            adapter.notifyDataSetChanged();
        }
    }
}
