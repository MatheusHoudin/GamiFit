package com.br.gamifit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.br.gamifit.R;

public class ProfileViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private ImageView gymImage;
    private TextView gymName;
    private TextView offensiveDays;

    private Context context;

    public ProfileViewHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        gymImage = itemView.findViewById(R.id.gym_image);
        gymName = itemView.findViewById(R.id.gym_name);
        offensiveDays = itemView.findViewById(R.id.offensive_days);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(this.context,"Clicked Gym: "+this.getGymName().getText().toString(),Toast.LENGTH_SHORT).show();
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

    public Context getContext() {
        return context;
    }
}
