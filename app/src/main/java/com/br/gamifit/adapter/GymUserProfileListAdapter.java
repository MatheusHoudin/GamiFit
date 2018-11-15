package com.br.gamifit.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.br.gamifit.R;
import com.br.gamifit.model.Profile;

import java.util.List;

public class GymUserProfileListAdapter extends RecyclerView.Adapter<GymUserProfileViewHolder> {
    private List<Profile> profiles;
    private Context context;

    public GymUserProfileListAdapter(List<Profile> profiles,Context context){
        this.profiles = profiles;
        this.context = context;
    }

    @NonNull
    @Override
    public GymUserProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gym_user_item_list,parent,false);
        GymUserProfileViewHolder viewHolder = new GymUserProfileViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GymUserProfileViewHolder holder, int position) {
        Log.i("Caught","onBindViewHolder");
        holder.getUserGymProfileName().setText(this.profiles.get(position).getUser().getName());
        holder.getUserGymProfileOffensiveDays().setText(String.valueOf(this.profiles.get(position).getProgress().getOffensiveDays()));
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }
}
