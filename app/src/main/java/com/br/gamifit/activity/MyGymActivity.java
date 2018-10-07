package com.br.gamifit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.br.gamifit.R;
import com.br.gamifit.adapter.GymPageAdapter;
import com.br.gamifit.controller.MyGymController;
import com.br.gamifit.model.Gym;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MyGymActivity extends AppCompatActivity {
    private MyGymController myGymController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_gym);
        TabLayout tabLayout = findViewById(R.id.gym_tab_layout);
        Toolbar toolbar = findViewById(R.id.gym_toolbar);
//        ViewPager viewPager = findViewById(R.id.gym_view_pager);
        setSupportActionBar(toolbar);
//        PagerAdapter pagerAdapter = new GymPageAdapter(getSupportFragmentManager(),1);
//        viewPager.setAdapter(pagerAdapter);
        TextView mTextMessage = findViewById(R.id.message);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Gym gym = (Gym) bundle.getSerializable("gym");

        myGymController = MyGymController.getGymController(this,gym);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_gym_profile,menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.action_qrcode_gym:
                myGymController.openScanCodeActivity();
        }
        return super.onOptionsItemSelected(item);
    }
}
