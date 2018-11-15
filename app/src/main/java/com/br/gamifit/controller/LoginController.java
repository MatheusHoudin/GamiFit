package com.br.gamifit.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.br.gamifit.R;
import com.br.gamifit.activity.LoginActivity;
import com.br.gamifit.activity.SignUpActivity;
import com.br.gamifit.dao_factory.FirebaseFactory;
import com.br.gamifit.database.UserFirebaseDAO;
import com.br.gamifit.helper.MyNotificationManager;
import com.br.gamifit.helper.MyPreferences;
import com.br.gamifit.model.ObserverResponse;
import com.br.gamifit.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.collection.LLRBNode;

import java.util.Observable;
import java.util.Observer;

public class LoginController implements Observer{

    private static LoginController loginController;
    private Context context;
    private LoginActivity loginView;

    private LoginController(LoginActivity loginActivity){
        this.loginView = loginActivity;
        this.context = loginView.getApplicationContext();
    }

    public static LoginController getLoginController(LoginActivity loginActivity) {
        if(loginController==null){
            loginController = new LoginController(loginActivity);
        }
        setUpObservable();
        return loginController;
    }

    public void setLoginView(LoginActivity loginView) {
        this.loginView = loginView;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private static void setUpObservable(){
        UserFirebaseDAO userFirebaseDAO = FirebaseFactory.getUserFirebaseDAO();
        userFirebaseDAO.addObserver(loginController);
    }

    public void checkUserAlreadyLoggedIn(){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null){
            loginView.openDashboardActivity();
        }
    }


    public void loginUserWithEmailAndPassword(){
        String email = loginView.getTxtEmail().getText().toString();
        String password = loginView.getTxtPassword().getText().toString();
        if(isValidEmailAndPassword(email,password)){
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(loginOnCompleteListener);
        }else{
            showErrorUserEmailOrPasswordInvalid();
        }
    }

    private OnCompleteListener<AuthResult> loginOnCompleteListener = new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful()){
                final String email = task.getResult().getUser().getEmail();
                task.getResult().getUser().getIdToken(true).addOnSuccessListener(new OnSuccessListener<GetTokenResult>() {
                    @Override
                    public void onSuccess(GetTokenResult getTokenResult) {
                        MyPreferences myPreferences = new MyPreferences(context);
                        myPreferences.saveUserToken(getTokenResult.getToken());
                        Log.i("caught email",email);
                        FirebaseFactory.getUserFirebaseDAO().getUser(email);

                    }
                });
            }else{
                try {
                    throw task.getException();
                } catch (FirebaseAuthInvalidUserException e) {
                    Toast.makeText(context,context.getString(R.string.invalid_email_message),Toast.LENGTH_SHORT).show();
                }catch (FirebaseAuthInvalidCredentialsException e){
                    Toast.makeText(context,context.getString(R.string.invalid_password_message),Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(context,context.getString(R.string.connection_error),Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    private void showErrorUserEmailOrPasswordInvalid(){
        if(loginView.getTxtEmail().getText().toString().equals("")){
            warnUpEditTextEmpty(loginView.getTxtEmail());
        }
        if(loginView.getTxtPassword().getText().toString().equals("")){
            warnUpEditTextEmpty(loginView.getTxtPassword());
        }
    }

    private void warnUpEditTextEmpty(EditText editText){
        editText.requestFocus();
        editText.setHintTextColor(Color.RED);
    }

    private void handleUserPreferencesSaving(User caughtUser){
        if(caughtUser!=null) {
            String userName = caughtUser.getName();
            String userPassword = caughtUser.getPassword();
            String userEmail = caughtUser.getEmail();
            String userCode = caughtUser.getCode();

            MyPreferences myPreferences = new MyPreferences(context);
            FirebaseFactory.getUserFirebaseDAO().updateUserToken(myPreferences.getUserToken(),caughtUser);
            myPreferences.clearData();
            boolean commitResult = myPreferences.saveUserData(userName, userEmail,
                    userPassword, userCode);
            handleResultLogin(commitResult);
        }else{
            warnUpUserAccountDoesNotExist();
        }
    }

    private void warnUpUserAccountDoesNotExist(){
        Toast.makeText(loginView.getApplicationContext(),context.getString(R.string.user_or_password_incorrect)
                ,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void update(Observable observable, Object o) {
        if(o instanceof ObserverResponse){
            ObserverResponse response = (ObserverResponse) o;
            if(response.getMethod().equals("getUser")){

                User caughtUser = (User) response.getContent();
                Log.i("updateUserrre",caughtUser.getName());
                handleUserPreferencesSaving(caughtUser);
            }
        }
    }

    private void handleResultLogin(boolean commitResult){
        if(commitResult){
            loginView.openDashboardActivity();

        }else{
            Toast.makeText(context,context.getString(R.string.was_not_possible_to_log_in),Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidEmailAndPassword(String email,String password){
        return !(email.equals("") || password.equals(""));
    }

    public View.OnClickListener btnLoginOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            loginUserWithEmailAndPassword();
        }
    };

    public View.OnClickListener btnSignUpOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            loginView.openSignUpActivity();
        }
    };

    public View.OnClickListener getBtnLoginOnClickListener() {
        return btnLoginOnClickListener;
    }

    public View.OnClickListener getBtnSignUpOnClickListener() {
        return btnSignUpOnClickListener;
    }
}
