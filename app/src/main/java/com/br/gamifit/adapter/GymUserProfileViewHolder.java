package com.br.gamifit.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.br.gamifit.R;

public class GymUserProfileViewHolder extends RecyclerView.ViewHolder {
    private ImageView userGymProfileImg;
    private TextView userGymProfileName;
    private TextView userGymProfileOffensiveDays;

    public GymUserProfileViewHolder(View itemView) {
        super(itemView);
        this.userGymProfileImg = itemView.findViewById(R.id.user_gym_profile_img);
        this.userGymProfileName = itemView.findViewById(R.id.user_gym_profile_name);
        this.userGymProfileOffensiveDays = itemView.findViewById(R.id.user_gym_profile_offensive_days);
    }

    public ImageView getUserGymProfileImg() {
        return userGymProfileImg;
    }

    public TextView getUserGymProfileName() {
        return userGymProfileName;
    }

    public TextView getUserGymProfileOffensiveDays() {
        return userGymProfileOffensiveDays;
    }

    public void setUserGymProfileImg(ImageView userGymProfileImg) {
        this.userGymProfileImg = userGymProfileImg;
    }

    public void setUserGymProfileName(TextView userGymProfileName) {
        this.userGymProfileName = userGymProfileName;
    }

    public void setUserGymProfileOffensiveDays(TextView userGymProfileOffensiveDays) {
        this.userGymProfileOffensiveDays = userGymProfileOffensiveDays;
    }
}
