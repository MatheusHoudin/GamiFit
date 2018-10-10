package com.br.gamifit.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.br.gamifit.R;
import com.br.gamifit.activity.GymProfileActivity;
import com.br.gamifit.activity.MyGymActivity;
import com.br.gamifit.model.Gym;

import java.util.List;

public class MyGymsListAdapter extends RecyclerView.Adapter<GymViewHolder> {
    private List<Gym> myGymsList;
    private Context context;

    private Handler handler;
    public MyGymsListAdapter(List<Gym> myGymsList,Context context,Handler handler){
        this.myGymsList = myGymsList;
        this.context = context;
        this.handler = handler;
    }

    @NonNull
    @Override
    public GymViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_gym_item_list,parent,false);
        GymViewHolder gymViewHolder = new GymViewHolder(view);

        return gymViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GymViewHolder holder, int position) {
        holder.getGymName().setText(myGymsList.get(position).getName());
        holder.getGymLocalization().setText(myGymsList.get(position).getLocalization().getAddress());

        holder.itemView.setOnClickListener(new GymViewHolderOnClickListener(myGymsList.get(position),this.handler));
    }

    @Override
    public int getItemCount() {
        return myGymsList.size();
    }

    private class GymViewHolderOnClickListener implements View.OnClickListener{
        private Gym gym;
        private final int BUNDLE_TO_MYGYMACTIVITY = 1;
        private Handler handler;

        public GymViewHolderOnClickListener(Gym gym,Handler handler){
            this.gym = gym;
            this.handler = handler;
        }

        /**
         * @param view
         * When an Gym object view from the list is clicked a message containing the gym object which
         * was clicked is sent to MyGymListActivity's Handler and there it performs an action
         */
        @Override
        public void onClick(View view) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("gym",gym);
            Message message = new Message();
            message.setData(bundle);
            message.what = BUNDLE_TO_MYGYMACTIVITY;
            Log.i("Handler","Sent");
            this.handler.sendMessage(message);
        }
    }
}
