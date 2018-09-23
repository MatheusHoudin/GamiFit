package com.br.gamifit.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.br.gamifit.R;
import com.br.gamifit.model.GymInvite;
import com.google.android.gms.common.data.DataBufferObserver;

import java.util.List;

public class ReceivedInviteListAdapter extends BaseAdapter{
    private List<GymInvite> gymInvites;
    private Context context;

    public ReceivedInviteListAdapter(Context context,List<GymInvite> invites){
        this.context = context;
        this.gymInvites = invites;
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
                if(invite.acceptInvite()){
                    removeInvite(invite);
                    Toast.makeText(context,"Perfil na "+invite.getGym().getName()+" criado com sucesso",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context,"Não foi possível completar a operação",Toast.LENGTH_SHORT).show();
                }
            }
        };
        return btnAcceptInviteOnClickListener;
    }

    private View.OnClickListener setBtnRejectInviteOnClickListener(int inviteIndex){
        final GymInvite invite = gymInvites.get(inviteIndex);
        View.OnClickListener btnRejectInviteOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(invite.rejectInvite()){
                    removeInvite(invite);
                }
            }
        };
        return btnRejectInviteOnClickListener;
    }

    private void removeInvite(GymInvite invite){
        gymInvites.remove(invite);
        this.notifyDataSetChanged();
    }
}