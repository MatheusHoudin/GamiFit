package com.br.gamifit.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.br.gamifit.R;
import com.br.gamifit.controller.CreateGymController;

public class CreateGymActivity extends AppCompatActivity {
    private Button btnLocalization;
    private Button btnCreateGym;

    private EditText txtLatitude;
    private EditText txtLongitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_gym);
        btnLocalization = findViewById(R.id.btnLocalization);
        btnCreateGym = findViewById(R.id.btnCreateGym);
        txtLatitude = findViewById(R.id.txt_latitude);
        txtLongitude = findViewById(R.id.txt_longitude);
        Toolbar toolbar = findViewById(R.id.create_gym_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Toast.makeText(this,txtLatitude.getText().toString(),Toast.LENGTH_LONG).show();
        Toast.makeText(this,txtLongitude.getText().toString(),Toast.LENGTH_LONG).show();
        CreateGymController createGymController = CreateGymController.getCreateGymController(this);
        createGymController.verifyHasLatitudeAndLongitudeValues();
    }

    public void openMapsActivity(){
        Intent intent = new Intent(this,MapsActivity.class);
        startActivity(intent);
    }

    public void setBtnLocalizationOnClickListener(View.OnClickListener btnLocalizationOnClickListener){
        this.btnLocalization.setOnClickListener(btnLocalizationOnClickListener);
    }

    public void setBtnCreateGymOnClickListener(View.OnClickListener btnCreateGymOnClickListener){
        this.btnCreateGym.setOnClickListener(btnCreateGymOnClickListener);
    }

    public EditText getTxtLatitude() {
        return txtLatitude;
    }

    public EditText getTxtLongitude() {
        return txtLongitude;
    }
}
