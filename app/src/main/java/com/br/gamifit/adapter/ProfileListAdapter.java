package com.br.gamifit.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.br.gamifit.R;
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
        return new ProfileViewHolder(LayoutInflater.from(context).inflate(R.layout.profile_item_list,null));
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position) {
        holder.getGymName().setText(profilesList.get(position).getGym().getName());
        // holder.getOffensiveDays().setText(profilesList.get(position).getProgress().getOffensiveDays());
    }

    @Override
    public int getItemCount() {
        return profilesList.size();
    }
}
