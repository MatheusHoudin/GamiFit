package com.br.gamifit.activity;

import android.content.Intent;
import android.net.Uri;
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
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.PlacesOptions;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.List;
import java.util.Locale;

public class CreateGymActivity extends AppCompatActivity {
    private CreateGymController createGymController;

    private Button btnLocalization;
    private Button btnCreateGym;

    private EditText gymName;
    private EditText gymAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_gym);
        btnLocalization = findViewById(R.id.btnLocalization);
        btnCreateGym = findViewById(R.id.btnCreateGym);
        gymName = findViewById(R.id.txt_gym_name);
        gymAddress = findViewById(R.id.txt_selected_place);

        Toolbar toolbar = findViewById(R.id.create_gym_toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        createGymController = CreateGymController.getCreateGymController(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CreateGymController.PLACE_PICKER_REQUEST){
            if(resultCode == RESULT_OK){
                Place place = PlacePicker.getPlace(this,data);
                createGymController.setGymPlace(place);
                gymAddress.setText(place.getAddress());
            }
        }
    }

    public EditText getGymName() {
        return gymName;
    }
    public void setBtnCreateGymOnClickListener(View.OnClickListener btnCreateGymOnClickListener){
        this.btnCreateGym.setOnClickListener(btnCreateGymOnClickListener);
    }

    public void setBtnLocalizationOnClickListener(View.OnClickListener btnLocalizationOnClickListener){
        this.btnLocalization.setOnClickListener(btnLocalizationOnClickListener);
    }
}
