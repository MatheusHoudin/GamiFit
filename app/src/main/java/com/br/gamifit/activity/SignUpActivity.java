package com.br.gamifit.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.br.gamifit.R;
import com.br.gamifit.controller.SignUpController;
import com.br.gamifit.model.User;

public class SignUpActivity extends AppCompatActivity {
    private SignUpController controller;

    private EditText txtName;
    private EditText txtEmail;
    private EditText txtPassword;
    private EditText txtRepeatedPasswod;

    private Button btnSignUp;
    private TextView txtSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        txtName = findViewById(R.id.txt_name);
        txtEmail = findViewById(R.id.txt_email);
        txtPassword = findViewById(R.id.txt_password);
        txtRepeatedPasswod = findViewById(R.id.txt_repeated_password);

        btnSignUp = findViewById(R.id.btn_create_account);
        txtSignIn = findViewById(R.id.txt_sign_in);

        controller = SignUpController.getSignUpController(this);
    }

    public void setbtnSignUpOnClickListener(View.OnClickListener onClickListener){
        btnSignUp.setOnClickListener(onClickListener);
    }

    public void openDashboardActivity(){
        Intent intent = new Intent(this,DashboardActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void openLoginActivity(){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    public EditText getTxtName() {
        return txtName;
    }

    public EditText getTxtEmail() {
        return txtEmail;
    }

    public EditText getTxtPassword() {
        return txtPassword;
    }

    public EditText getTxtRepeatedPasswod() {
        return txtRepeatedPasswod;
    }
}
