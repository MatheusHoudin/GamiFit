package com.br.gamifit.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.br.gamifit.R;
import com.br.gamifit.activity.GymProfileActivity;

public class ProfileViewHolder extends RecyclerView.ViewHolder{
    private ImageView gymImage;
    private TextView gymName;
    private TextView offensiveDays;

    public ProfileViewHolder(View itemView) {
        super(itemView);
        gymImage = itemView.findViewById(R.id.gym_image);
        gymName = itemView.findViewById(R.id.gym_name);
        offensiveDays = itemView.findViewById(R.id.offensive_days);
    }


    public ImageView getGymImage() {
        return gymImage;
    }

    public TextView getGymName() {
        return gymName;
    }

    public TextView getOffensiveDays() {
        return offensiveDays;
    }
}
