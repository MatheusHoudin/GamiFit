package com.br.gamifit.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.br.gamifit.R;
import com.br.gamifit.adapter.GymInviteListAdapter;
import com.br.gamifit.controller.SocialFragmentController;
import com.br.gamifit.model.Gym;
import com.br.gamifit.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class InviteActivity extends AppCompatActivity {
    private GymInviteListAdapter inviteListAdapter;

    private Gym gym;

    private SocialFragmentController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);

        controller = SocialFragmentController.getInstance(getApplicationContext());

        ListView listView = findViewById(R.id.list_users);
        List<User> users = new ArrayList<>();
        inviteListAdapter = new GymInviteListAdapter(getApplicationContext(), users);
        listView.setAdapter(inviteListAdapter);
        findAllUsers();
        User user = new User("Firmino","ma@gmail.com","firmo");
        User user1 = new User("Joao","joa@gmail.com","spmkf");
        User user2 = new User("Perla","per@gmail.com","maiggsm435a");
        User user3 = new User("maio","mai@gmail.com","maiyhsma");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        user.setCode(databaseReference.child("user").push().getKey());
        databaseReference.child("user").push().setValue(user);
        user1.setCode(databaseReference.child("user").push().getKey());
        databaseReference.child("user").push().setValue(user1);
        user2.setCode(databaseReference.child("user").push().getKey());
        databaseReference.child("user").push().setValue(user2);
        user3.setCode(databaseReference.child("user").push().getKey());
        databaseReference.child("user").push().setValue(user3);
    }

    private AdapterView.OnItemClickListener onClickItemInvite = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            User user = (User) inviteListAdapter.getItem(i);
            controller.sendInvite(user);
        }
    };

    public void findAllUsers(){
       // users = controller.getAllUsersToInvite();
        inviteListAdapter.notifyDataSetChanged();
    }


}
