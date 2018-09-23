package com.br.gamifit.adapter;

import android.content.Context;
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

public class GymInviteListAdapter extends BaseAdapter {
    private List<User> users;
    private Context context;

    public GymInviteListAdapter(Context context, List<User> users){
        this.context = context;
        this.users = users;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(this.context, R.layout.user_item_list,null);
        final TextView txtName = v.findViewById(R.id.user_name_list);
        final int position = i;
        Button btnSendInvite = v.findViewById(R.id.btn_invite);
        txtName.setText(users.get(i).getName());

        btnSendInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,txtName.getText().toString(),Toast.LENGTH_SHORT).show();

                Gym gym = new Gym("Arena Fitness","h67886b86g68o");
                gym.sendInviteToJoin(users.get(position));
            }
        });
        return v;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int i) {
        return users.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

}
