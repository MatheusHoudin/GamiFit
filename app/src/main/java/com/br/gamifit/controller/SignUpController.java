package com.br.gamifit.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.br.gamifit.R;
import com.br.gamifit.activity.SignUpActivity;
import com.br.gamifit.dao_factory.FirebaseFactory;
import com.br.gamifit.database.OperationResult;
import com.br.gamifit.database.UserFirebaseDAO;
import com.br.gamifit.helper.MyPreferences;
import com.br.gamifit.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Observable;
import java.util.Observer;

public class SignUpController implements Observer {
    private static SignUpController signUpController;
    private Context context;
    private SignUpActivity activity;

    private User user;

    private SignUpController(){
    }

    public static SignUpController getSignUpController() {
        if(signUpController==null){
            signUpController = new SignUpController();
            setUpObservable();
        }

        return signUpController;
    }

    private static void setUpObservable(){
        FirebaseFactory.getUserFirebaseDAO().addObserver(signUpController);
        Log.i("debugando",String.valueOf(FirebaseFactory.getUserFirebaseDAO().countObservers()));
    }

    public View.OnClickListener getBtnSignUpOnClickListener() {
        return btnSignUpOnClickListener;
    }

    private View.OnClickListener btnSignUpOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String name = activity.getTxtName().getText().toString();
            String email = activity.getTxtEmail().getText().toString();
            String password = activity.getTxtPassword().getText().toString();
            String repeatedPassword = activity.getTxtRepeatedPasswod().getText().toString();
            Log.i("debugando","onClick");
            createUser(name,email,password,repeatedPassword);
        }

    };

    public void createUser(String name,String email,String password,String repeatedPassword){
        if(validatePasswordIsEqualToRepeatedPassword(password,repeatedPassword)){

            user = new User(name,email,password);
            String token = FirebaseInstanceId.getInstance().getToken();
            user.setToken(token);
            Log.i("debugando","createUser");
            user.createUserAccount();

        }else{
            activity.showToastMessage(activity.getString(R.string.passwords_are_not_the_same));
        }
    }

    public boolean validatePasswordIsEqualToRepeatedPassword(String password,String repeatedPassword){
        Log.i("debugando","validatePasswoerd");
        return password.equals(repeatedPassword);
    }

    public void logInCreatedUser(){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(user.getEmail(),user.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    MyPreferences preferences = new MyPreferences(context);
                    preferences.saveUserData(user.getName(),user.getEmail(),user.getPassword(),user.getCode());

                    activity.showToastMessage(activity.getString(R.string.account_created_successfully));
                    Log.i("debugando","sucess");
                    activity.openDashboardActivity();
                }else{
                    //TODO: Change this message, the user even doesnt know what is preferencias
                    Toast.makeText(activity,"Erro ao salvar preferencias",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void update(Observable observable, Object o) {
        if(o instanceof OperationResult) {
            OperationResult operationResult = (OperationResult) o;
            if(operationResult.getOperationCode() == UserFirebaseDAO.OPERATION_CREATE_USER_ACCOUNT){
                if(operationResult.getCaughtException()==null){
                    Log.i("debugando","saving user");
                    user.saveUser();
                }else{
                    handleAuthExceptions(operationResult.getCaughtException());
                }
            }else if(operationResult.getOperationCode() == UserFirebaseDAO.OPERATION_SAVE_USER){
                if(operationResult.getCaughtException()==null){
                    Log.i("debugando","logincreated");
                    logInCreatedUser();
                }else{
                    handleDatabaseExceptions(operationResult.getCaughtException());
                }
            }
        }
    }

    private void handleAuthExceptions(Exception caughtException){
        try {
            throw caughtException;
        }catch(FirebaseAuthWeakPasswordException e){
            activity.showToastMessage(activity.getString(R.string.weak_password));
        }catch(FirebaseAuthInvalidCredentialsException e){
            activity.showToastMessage(activity.getString(R.string.invalid_email));
        }catch(FirebaseAuthUserCollisionException e){
            e.printStackTrace();
            activity.showToastMessage(activity.getString(R.string.user_account_with_this_email_already_exists));
        }catch(Exception e){
            activity.showToastMessage(activity.getString(R.string.unexpected_problem));
        }
    }

    private void handleDatabaseExceptions(Exception caughtException){
        try {
            throw caughtException;
        }catch(DatabaseException e){
            activity.showToastMessage(activity.getString(R.string.database_is_off));
        } catch (Exception e) {
            activity.showToastMessage(activity.getString(R.string.unexpected_problem));
        }
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setActivity(SignUpActivity activity) {
        this.activity = activity;
    }
}
