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
import com.br.gamifit.helper.MyPreferences;
import com.br.gamifit.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
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
        this.loginView.setBtnLoginOnClickListener(btnLoginOnClickListener);
        this.loginView.setBtnCreateAcountOnClickListener(btnSignUpOnClickListener);
        FirebaseAuth.getInstance().signOut();
        //TODO: Check whether the user data is saved on MyPreferences or not when the method checckUserAlreadyLoggedIn() is called
        //this.checkUserAlreadyLoggedIn();
    }

    public static LoginController getLoginController(LoginActivity loginActivity) {
        if(loginController==null){
            loginController = new LoginController(loginActivity);
        }
        setUpObservable();
        return loginController;
    }

    private static void setUpObservable(){
        UserFirebaseDAO userFirebaseDAO = FirebaseFactory.getUserFirebaseDAO();
        userFirebaseDAO.addObserver(loginController);
    }

    public void checkUserAlreadyLoggedIn(){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null) loginView.openDashboardActivity();
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
                String email = task.getResult().getUser().getEmail();
                FirebaseFactory.getUserFirebaseDAO().getUser(email);
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

            MyPreferences myPreferences = MyPreferences.getMyPreferences(context);

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
        if(o instanceof User){
            User caughtUser = (User) o;
            handleUserPreferencesSaving(caughtUser);
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

    private View.OnClickListener btnLoginOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            loginUserWithEmailAndPassword();
        }
    };

    private View.OnClickListener btnSignUpOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            loginView.openSignUpActivity();
        }
    };
}
