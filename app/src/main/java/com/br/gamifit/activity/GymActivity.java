package com.br.gamifit.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.br.gamifit.R;
import com.br.gamifit.adapter.ProfilePageAdapter;

public class GymActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym);
        TabLayout tabLayout = findViewById(R.id.gym_tab_layout);
        Toolbar toolbar = findViewById(R.id.gym_toolbar);
        ViewPager viewPager = findViewById(R.id.gym_view_pager);
        setSupportActionBar(toolbar);

        

        PagerAdapter pagerAdapter = new ProfilePageAdapter(getSupportFragmentManager(), 1);
        viewPager.setAdapter(pagerAdapter);
        TextView mTextMessage = findViewById(R.id.message);
    }

}
