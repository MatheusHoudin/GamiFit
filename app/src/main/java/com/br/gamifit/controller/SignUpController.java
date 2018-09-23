package com.br.gamifit.controller;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.br.gamifit.activity.SignUpActivity;
import com.br.gamifit.dao_factory.FirebaseFactory;
import com.br.gamifit.database.UserFirebaseDAO;
import com.br.gamifit.helper.MyPreferences;
import com.br.gamifit.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpController {
    private static SignUpController signUpController;
    private Context context;
    private SignUpActivity activity;

    private User user;

    private SignUpController(SignUpActivity activity){
        this.context = activity.getApplicationContext();
        this.activity = activity;

        activity.setbtnSignUpOnClickListener(btnSignUpOnClickListener);
    }

    public static SignUpController getSignUpController(SignUpActivity activity) {
        if(signUpController==null){
            signUpController = new SignUpController(activity);
        }
        return signUpController;
    }

    private View.OnClickListener btnSignUpOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String name = activity.getTxtName().getText().toString();
            String email = activity.getTxtEmail().getText().toString();
            String password = activity.getTxtPassword().getText().toString();
            String repeatedPassword = activity.getTxtRepeatedPasswod().getText().toString();
            if(createUser(name,email,password,repeatedPassword)){
                if(logInCreatedUser()){
                    activity.openDashboardActivity();
                }else{
                    activity.openLoginActivity();
                }
            }
        }
    };

    public boolean createUser(String name,String email,String password,String repeatedPassword){
        if(validatePasswordIsEqualToRepeatedPassword(password,repeatedPassword)){

            user = new User(name,email,password);

            Exception authException = user.createUserAccount();

            if(authException==null){
                Exception databaseException = user.saveUser();
                if(databaseException==null){
                    return true;
                }else{
                    try{
                        throw databaseException;
                    }catch(DatabaseException e){
                        Toast.makeText(context,"Base de dados está inacessível",Toast.LENGTH_SHORT).show();
                    }catch(Exception e){
                        Toast.makeText(context,"Erro ao salvar usuário",Toast.LENGTH_SHORT).show();
                    }finally {
                        return false;
                    }
                }
            }else{
                try{
                    throw authException;
                }catch(FirebaseAuthWeakPasswordException e){

                    Toast.makeText(context,"Senha muito fraca, inclua letras maiúsculas,minúscu" +
                            "las e caracteres especiais",Toast.LENGTH_SHORT).show();

                }catch(FirebaseAuthInvalidCredentialsException e){

                    Toast.makeText(context,"Email inválido",Toast.LENGTH_SHORT).show();

                }catch(FirebaseAuthUserCollisionException e){
                    Toast.makeText(context,"Já existe um usuário cadastrado com este email",Toast.LENGTH_SHORT).show();
                }catch(Exception e){
                    Toast.makeText(context,"Erro ao cadastrar usuário",Toast.LENGTH_SHORT).show();
                }finally {
                    return false;
                }
            }

        }else{
            Toast.makeText(context,"Senhas não coincidem",Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean validatePasswordIsEqualToRepeatedPassword(String password,String repeatedPassword){
        return password.equals(repeatedPassword);
    }

    public boolean logInCreatedUser(){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        boolean result = firebaseAuth.signInWithEmailAndPassword(user.getEmail(),user.getPassword())
                .isSuccessful();
        if(result){
            MyPreferences preferences = MyPreferences.getMyPreferences(context);
            preferences.saveUserData(user.getName(),user.getEmail(),user.getPassword(),user.getCode());
            return true;
        }else{
            return false;
        }

    }
}
