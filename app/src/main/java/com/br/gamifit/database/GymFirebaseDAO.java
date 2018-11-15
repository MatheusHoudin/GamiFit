package com.br.gamifit.database;

import android.support.annotation.NonNull;

import com.br.gamifit.dao_factory.FirebaseFactory;
import com.br.gamifit.database.dao_interface.IGymDAO;
import com.br.gamifit.model.Gym;
import com.br.gamifit.model.ObserverResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Observable;

public class
GymFirebaseDAO extends Observable implements IGymDAO {

    public void createGym(Gym gym){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        String generatedCode = database.child("gym").push().getKey();
        gym.setCode(generatedCode);

        database.child("gym").child(gym.getCode()).setValue(gym).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                ObserverResponse observerResponse = new ObserverResponse("createGym",task.getException());
                setChanged();
                notifyObservers(observerResponse);
            }
        });
    }

    public void getAllMyGyms(String userCode){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child("gym").orderByChild("gymOwner/code").equalTo(userCode).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    for(DataSnapshot data:dataSnapshot.getChildren()){
                        Gym gym = data.getValue(Gym.class);
                        setChanged();
                        notifyObservers(gym);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
