package com.br.gamifit.dao_factory;

import com.br.gamifit.database.GymFirebaseDAO;
import com.br.gamifit.database.InviteFirebaseDAO;
import com.br.gamifit.database.ProfileFirebaseDAO;
import com.br.gamifit.database.UserFirebaseDAO;

public class FirebaseFactory {
    private static UserFirebaseDAO userFirebaseDAO;
    private static InviteFirebaseDAO inviteFirebaseDAO;
    private static ProfileFirebaseDAO profileFirebaseDAO;
    private static GymFirebaseDAO gymFirebaseDAO;

    public static GymFirebaseDAO getGymFirebaseDAO() {
        if(gymFirebaseDAO==null){
            gymFirebaseDAO = new GymFirebaseDAO();
        }
        return gymFirebaseDAO;
    }

    public static ProfileFirebaseDAO getProfileFirebaseDAO() {
        if(profileFirebaseDAO==null){
            profileFirebaseDAO = new ProfileFirebaseDAO();
        }
        return profileFirebaseDAO;
    }

    public static UserFirebaseDAO getUserFirebaseDAO(){
        if(userFirebaseDAO==null){
            userFirebaseDAO = new UserFirebaseDAO();
        }
        return userFirebaseDAO;
    }

    public static InviteFirebaseDAO getInviteFirebaseDAO(){
        if(inviteFirebaseDAO==null){
            inviteFirebaseDAO = new InviteFirebaseDAO();
        }
        return inviteFirebaseDAO;
    }
}
