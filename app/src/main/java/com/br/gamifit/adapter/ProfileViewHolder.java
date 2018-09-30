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
        Log.i("teste",gymName.getText().toString());
        offensiveDays = itemView.findViewById(R.id.offensive_days);
        itemView.setOnClickListener(this);
    }
    //TODO:Fix the bug here when you try to access the text on the gymName Component
    @Override
    public void onClick(View view) {
        Toast.makeText(context,"Clicked Gym: "+this.gymName.getText().toString(),Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, GymProfileActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("GymName",this.gymName.getText().toString());
        intent.putExtras(bundle);

        context.startActivity(intent);
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
