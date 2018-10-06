package com.br.gamifit.database;

import com.br.gamifit.dao_factory.FirebaseFactory;
import com.br.gamifit.database.dao_interface.IGymDAO;
import com.br.gamifit.model.Gym;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GymFirebaseDAO implements IGymDAO {

    public Exception createGym(Gym gym){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        String generatedCode = database.child("gym").push().getKey();
        gym.setCode(generatedCode);

        return database.child("gym").child(gym.getCode()).setValue(gym).getException();
    }
}
