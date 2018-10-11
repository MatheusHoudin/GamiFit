package com.br.gamifit.model;

import com.br.gamifit.dao_factory.FirebaseFactory;
import com.br.gamifit.database.ProfileFirebaseDAO;
import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Profile implements Serializable{
    private User user;
    private Gym gym;
    private String code;
    private Progress progress;
    private boolean checkInOut; // True == Check In is done
    private boolean active;

    public Profile(){}

    public Profile(Gym gym,User user,Progress progress){
        this.setGym(gym);
        this.setUser(user);
        this.setProgress(progress);
        this.setActive(true);
    }

    public boolean save(){
        ProfileFirebaseDAO profileFirebaseDAO = FirebaseFactory.getProfileFirebaseDAO();
        return profileFirebaseDAO.createProfile(this);
    }

    public Progress getProgress() {
        return progress;
    }

    public void setProgress(Progress progress) {
        this.progress = progress;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Gym getGym() {
        return gym;
    }

    public void setGym(Gym gym) {
        this.gym = gym;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object obj) {
        return this.hashCode()==obj.hashCode();
    }

    @Override
    public int hashCode() {
        return gym.hashCode()+user.hashCode();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Exclude
    public boolean isCheckInOut() {
        return checkInOut;
    }

    public void setCheckInOut(boolean checkInOut) {
        this.checkInOut = checkInOut;
        if(!checkInOut){
            this.progress.setOffensiveDays(this.getProgress().getOffensiveDays()+1);
        }
    }
}
