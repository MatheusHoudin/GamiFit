package com.br.gamifit.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.br.gamifit.R;
import com.br.gamifit.model.Gym;
import com.br.gamifit.model.User;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserViewHolder> {
    private List<User> users;
    private Context context;

    public UserListAdapter(Context context, List<User> users){
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.user_item_list,parent,false),context);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.getUserName().setText(users.get(position).getName());
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

}
