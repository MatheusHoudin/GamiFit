package com.br.gamifit.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.br.gamifit.R;
import com.br.gamifit.dao_factory.FirebaseFactory;
import com.br.gamifit.database.InviteFirebaseDAO;
import com.br.gamifit.database.ProfileFirebaseDAO;
import com.br.gamifit.model.GymInvite;
import com.br.gamifit.model.ObserverResponse;
import com.google.android.gms.common.data.DataBufferObserver;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ReceivedInviteListAdapter extends BaseAdapter implements Observer {
    private List<GymInvite> gymInvites;
    private Context context;

    public ReceivedInviteListAdapter(Context context,List<GymInvite> invites){
        this.context = context;
        this.gymInvites = invites;
        FirebaseFactory.getProfileFirebaseDAO().addObserver(this);
    }
    @Override
    public int getCount() {
        return gymInvites.size();
    }

    @Override
    public Object getItem(int i) {
        return gymInvites.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.invite_item_list,null);
        TextView txtName = v.findViewById(R.id.txt_invite_name);
        Button btnAcceptInvite = v.findViewById(R.id.btn_accept_invite);
        Button btnRejectInvite = v.findViewById(R.id.btn_reject_invite);
        txtName.setText(gymInvites.get(i).getGym().getName());
        btnAcceptInvite.setOnClickListener(setBtnAcceptInviteOnClickListener(i));
        btnRejectInvite.setOnClickListener(setBtnRejectInviteOnClickListener(i));
        return v;
    }

    private View.OnClickListener setBtnAcceptInviteOnClickListener(int inviteIndex){
        final GymInvite invite = gymInvites.get(inviteIndex);
        View.OnClickListener btnAcceptInviteOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                invite.acceptInvite();
            }
        };
        return btnAcceptInviteOnClickListener;
    }

    private View.OnClickListener setBtnRejectInviteOnClickListener(int inviteIndex){
        final GymInvite invite = gymInvites.get(inviteIndex);
        View.OnClickListener btnRejectInviteOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeInvite(invite);
                //Toast.makeText(context,context.getString(R.string.operation_failed),Toast.LENGTH_SHORT).show();
            }
        };
        return btnRejectInviteOnClickListener;
    }

    private void removeInvite(GymInvite invite){
        InviteFirebaseDAO inviteFirebaseDAO = FirebaseFactory.getInviteFirebaseDAO();
        inviteFirebaseDAO.delete(invite);
        gymInvites.remove(invite);
        this.notifyDataSetChanged();
    }

    @Override
    public void update(Observable observable, Object o) {
        if(observable instanceof ProfileFirebaseDAO){
            if(o instanceof ObserverResponse){
                ObserverResponse response = (ObserverResponse) o;
                switch (response.getMethod()){
                    case "createProfile":
                        GymInvite invite = (GymInvite) response.getContent();
                        handleAcceptInvite(invite);
                        break;

                }
            }

        }
    }

    private void handleAcceptInvite(GymInvite invite){
        if(invite!=null){
            removeInvite(invite);
            Toast.makeText(context,context.getString(R.string.create_gym_profile_sucess_part1)
                            +invite.getGym().getName()+context.getString(R.string.create_gym_profile_sucess_part2)
                    ,Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,context.getString(R.string.operation_failed),Toast.LENGTH_SHORT).show();
        }
    }
}