package com.br.gamifit.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.br.gamifit.R;
import com.br.gamifit.controller.LoginController;
import com.br.gamifit.dao_factory.FirebaseFactory;
import com.br.gamifit.model.User;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Observable;
import java.util.Observer;

public class LoginActivity extends AppCompatActivity{

    private EditText txtEmail;
    private EditText txtPassword;
    private Button btnLogin;
    private Button btnCreateAcount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnCreateAcount = findViewById(R.id.btn_create_account);
        btnLogin = findViewById(R.id.btn_login);
        txtEmail = findViewById(R.id.txt_email);
        txtPassword = findViewById(R.id.txt_password);

        LoginController loginController = LoginController.getLoginController(this);

    }

    public void setBtnLoginOnClickListener(View.OnClickListener onClickListener){
        this.btnLogin.setOnClickListener(onClickListener);
    }

    public void setBtnCreateAcountOnClickListener(View.OnClickListener onClickListener){
        this.btnCreateAcount.setOnClickListener(onClickListener);
    }


    public void openSignUpActivity(){
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(intent);
    }

    public void openDashboardActivity(){
        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
        startActivity(intent);
        this.finish();
    }

    public EditText getTxtEmail() {
        return txtEmail;
    }

    public EditText getTxtPassword() {
        return txtPassword;
    }

    public LoginActivity() {}
}
