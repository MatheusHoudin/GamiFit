package com.br.gamifit.model;

import com.br.gamifit.dao_factory.FirebaseFactory;
import com.br.gamifit.database.InviteFirebaseDAO;
import com.br.gamifit.database.ProfileFirebaseDAO;

import java.io.Serializable;

public class GymInvite implements Serializable {
    private String code;
    private Gym gym;
    private User user;
    private boolean active;

    public GymInvite(){}

    public GymInvite(User user,Gym gym){
        this.setGym(gym);
        this.setUser(user);
    }

    public GymInvite(User user,Gym gym,String code){
        this(user,gym);
        this.setCode(code);
    }

    public boolean acceptInvite() {
        Progress progress = new Progress(0);
        Profile profile = new Profile(gym,user,progress);

        return profile.save();
    }

    public boolean rejectInvite() {
        InviteFirebaseDAO inviteFirebaseDAO = FirebaseFactory.getInviteFirebaseDAO();
        return inviteFirebaseDAO.delete(this);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Gym getGym() {
        return gym;
    }

    public void setGym(Gym gym) {
        this.gym = gym;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object obj) {
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        //TODO: use the FirebaseAuth id instead of name
        return getGym().hashCode() + getUser().hashCode();
    }
}
