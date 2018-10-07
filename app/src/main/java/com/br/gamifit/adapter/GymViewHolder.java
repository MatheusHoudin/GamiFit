package com.br.gamifit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.br.gamifit.R;

public class GymViewHolder extends RecyclerView.ViewHolder {
    private ImageView gymImage;
    private TextView gymName;
    private TextView gymLocalization;

    public GymViewHolder(View itemView) {
        super(itemView);
        gymImage = itemView.findViewById(R.id.my_gym_image);
        gymName = itemView.findViewById(R.id.my_gym_name);
        gymLocalization = itemView.findViewById(R.id.my_localization_gym);
    }

    public ImageView getGymImage() {
        return gymImage;
    }

    public TextView getGymLocalization() {
        return gymLocalization;
    }

    public TextView getGymName() {
        return gymName;
    }
}
