package com.br.gamifit.model;

import com.br.gamifit.dao_factory.FirebaseFactory;
import com.br.gamifit.database.GymFirebaseDAO;
import com.br.gamifit.database.InviteFirebaseDAO;
import com.br.gamifit.model.exception.InvalidGymDataException;
import com.google.android.gms.location.places.Place;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Gym implements Serializable{
    private String code;
    private String name;
    private Localization localization;
    private User gymOwner;

    public static final float MAXIMUM_DISTANCE_TO_CHECKINOUT = 0.030f;

    private List<Profile> usersProfile;

    public Gym(){
        this.setUsersProfile(new ArrayList<Profile>());
    }

    public Gym(String name,String code){
        this();
        this.setName(name);
        this.setCode(code);
    }

    public Gym(String name,String code,Localization localization){
        this(name,code);
        this.setLocalization(localization);
    }

    public Gym(String name,Localization localization)throws InvalidGymDataException{
        this();
        this.setName(name);
        this.setLocalization(localization);
    }

    public GymInvite createInviteToJoin(User user){
        GymInvite invite = new GymInvite();
        invite.setUser(user);
        invite.setGym(this);
        return invite;
    }

    public void saveGym(){
        GymFirebaseDAO gymDAO = FirebaseFactory.getGymFirebaseDAO();
        gymDAO.createGym(this);
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

    public void setName(String name) throws InvalidGymDataException{
        if(name!=null && !name.isEmpty()){
            this.name = name;
        }else{
            throw new InvalidGymDataException("Nome da academia é inválido",name);
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setLocalization(Localization localization) throws InvalidGymDataException {
        if(localization!=null){
            this.localization = localization;
        }else{
            throw new InvalidGymDataException("Localização fornecida é inválida",localization);
        }
    }

    public Localization getLocalization() {
        return localization;
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

    public User getGymOwner() {
        return gymOwner;
    }

    public void setGymOwner(User gymOwner) {
        this.gymOwner = gymOwner;
    }
}
