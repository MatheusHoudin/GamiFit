package com.br.gamifit.model;

import com.br.gamifit.dao_factory.FirebaseFactory;
import com.br.gamifit.database.InviteFirebaseDAO;
import com.br.gamifit.database.dao_interface.IUserDAO;
import com.google.firebase.database.Exclude;

import java.util.Observable;

public class User extends Observable {
    private String name;
    private String email;
    private String password;
    private String code;

    public User(){}

    public User(String name,String email,String password){
        this.setName(name);
        this.setEmail(email);
        this.setPassword(password);
    }

    public User(String name,String code,String email,String password){
        this.setName(name);
        this.setCode(code);
        this.setEmail(email);
        this.setPassword(password);
    }

    public GymInvite createInviteToJoin(Gym gym){
        GymInvite invite= new GymInvite();
        invite.setUser(this);
        invite.setGym(gym);
        return  invite;
    }

    public Exception createUserAccount(){
        IUserDAO userDAO = FirebaseFactory.getUserFirebaseDAO();
        Exception caughtException = userDAO.createUserAcount(this);
        notifyObservers(caughtException);
        return  caughtException;
    }

    public Exception saveUser(){
        IUserDAO userDAO = FirebaseFactory.getUserFirebaseDAO();
        Exception caughtException = userDAO.saveUser(this);
        notifyObservers(caughtException);
        return caughtException;
    }

    public boolean sendInviteToJoin(Gym gym){
        GymInvite generatedInvite = createInviteToJoin(gym);
        InviteFirebaseDAO inviteFirebaseDAO = FirebaseFactory.getInviteFirebaseDAO();

        return inviteFirebaseDAO.createInvite(generatedInvite);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return code;
    }

    @Override
    public boolean equals(Object obj) {
        return this.hashCode()==obj.hashCode();
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }
}
