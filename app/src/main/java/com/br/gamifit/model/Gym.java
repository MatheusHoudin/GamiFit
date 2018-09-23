package com.br.gamifit.model;

import com.br.gamifit.dao_factory.FirebaseFactory;
import com.br.gamifit.database.InviteFirebaseDAO;

import java.util.ArrayList;
import java.util.List;

public class Gym {
    private String name;
    private String code;
    private List<Profile> usersProfile;

    public Gym(){
        this.setUsersProfile(new ArrayList<Profile>());
    }

    public Gym(String name,String code){
        this();
        this.setName(name);
        this.setCode(code);
    }

    public GymInvite createInviteToJoin(User user){
        GymInvite invite = new GymInvite();
        invite.setUser(user);
        invite.setGym(this);

        return invite;
    }

    public boolean sendInviteToJoin(User userToInvitate){
        GymInvite generatedInvite = createInviteToJoin(userToInvitate);
        InviteFirebaseDAO inviteFirebaseDAO = FirebaseFactory.getInviteFirebaseDAO();

        return inviteFirebaseDAO.createInvite(generatedInvite);
    }

    public List<Profile> getUsersProfile() {
        return usersProfile;
    }

    public void setUsersProfile(List<Profile> usersProfile) {
        this.usersProfile = usersProfile;
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
