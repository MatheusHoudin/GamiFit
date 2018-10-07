package com.br.gamifit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.br.gamifit.R;

public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private ImageView userImage;
    private TextView userName;
    private Button btnInvite;

    private Context context;

    public UserViewHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        userImage = itemView.findViewById(R.id.user_list_img);
        userName = itemView.findViewById(R.id.user_name_list);
        btnInvite = itemView.findViewById(R.id.btn_user_invite);
        itemView.setOnClickListener(this);
    }

    public Button getBtnInvite() {
        return btnInvite;
    }

    public ImageView getUserImage() {
        return userImage;
    }

    public TextView getUserName() {
        return userName;
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(this.context,userName.getText().toString(),Toast.LENGTH_LONG).show();
    }
}
