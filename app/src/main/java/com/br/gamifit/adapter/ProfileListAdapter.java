package com.br.gamifit.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.br.gamifit.R;
import com.br.gamifit.activity.GymProfileActivity;
import com.br.gamifit.model.Gym;
import com.br.gamifit.model.Profile;

import java.util.ArrayList;
import java.util.List;

public class ProfileListAdapter extends RecyclerView.Adapter<ProfileViewHolder> {

    private List<Profile> profilesList;
    private Context context;

    public ProfileListAdapter(Context context,List<Profile> profiles){
        this.context = context;
        this.profilesList = profiles;
    }

    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.profile_item_list,parent,false);
        ProfileViewHolder profileViewHolder = new ProfileViewHolder(view);
        return profileViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position) {

        holder.getGymName().setText(profilesList.get(position).getGym().getName());
        holder.getOffensiveDays().setText("Ofensiva: "+String.valueOf(profilesList.get(position).getProgress().getOffensiveDays())+" dias");

        holder.itemView.setOnClickListener(new HolderOnClickListener(profilesList.get(position)));
    }

    @Override
    public int getItemCount() {
        return profilesList.size();
    }

    private class HolderOnClickListener implements View.OnClickListener{
        private Profile profile;
        public HolderOnClickListener(Profile profile){
            this.profile = profile;
        }
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, GymProfileActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("profile",profile);
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
    }
}
