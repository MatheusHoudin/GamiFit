package com.br.gamifit.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.br.gamifit.R;
import com.br.gamifit.controller.MyGymListController;

public class MyGymListActivity extends AppCompatActivity {

    private RecyclerView myGymListRecyclerView;
    private MyGymListController myGymListController;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_gym_list);

        initHandler();

        myGymListController = MyGymListController.getMyGymListController(this);
        myGymListRecyclerView = findViewById(R.id.my_gym_list);
        myGymListRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        myGymListRecyclerView.setAdapter(myGymListController.getMyGymsListAdapter());
    }

    private void initHandler(){
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle param = msg.getData();
                Intent intent = new Intent(MyGymListActivity.this,MyGymActivity.class);
                intent.putExtras(param);
                Log.i("Handler","received");
                startActivity(intent);
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        myGymListController.getAllMyGyms();
    }

    public Handler getHandler() {
        return handler;
    }
}
