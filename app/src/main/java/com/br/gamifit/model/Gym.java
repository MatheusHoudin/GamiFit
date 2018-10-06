package com.br.gamifit.model;

import com.br.gamifit.dao_factory.FirebaseFactory;
import com.br.gamifit.database.GymFirebaseDAO;
import com.br.gamifit.database.InviteFirebaseDAO;

import java.util.ArrayList;
import java.util.List;

public class Gym {
    private String name;
    private String code;
    private long latitude;
    private long longitude;

    private List<Profile> usersProfile;

    public Gym(){
        this.setUsersProfile(new ArrayList<Profile>());
    }

    public Gym(String name,String code){
        this();
        this.setName(name);
        this.setCode(code);
    }

    public Gym(String name,String code,long latitude,long longitude){
        this(name,code);
        this.setLatitude(latitude);
        this.setLongitude(longitude);
    }

    public GymInvite createInviteToJoin(User user){
        GymInvite invite = new GymInvite();
        invite.setUser(user);
        invite.setGym(this);

        return invite;
    }

    public Exception saveGym(){
        GymFirebaseDAO gymDAO = FirebaseFactory.getGymFirebaseDAO();
        return gymDAO.createGym(this);
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

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
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
