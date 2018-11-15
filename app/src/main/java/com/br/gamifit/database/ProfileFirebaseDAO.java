package com.br.gamifit.database;

import android.support.annotation.NonNull;
import android.util.Log;

import com.br.gamifit.model.Gym;
import com.br.gamifit.model.GymInvite;
import com.br.gamifit.model.ObserverResponse;
import com.br.gamifit.model.Profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

public class ProfileFirebaseDAO extends Observable {

    private final int UPDATE_OFFENSIVE_DAYS_SUCESSFULL = 1;

    public void createProfile(Profile profile, final GymInvite invite){
        DatabaseReference firebaseReference = FirebaseDatabase.getInstance().getReference();
        profile.setCode(firebaseReference.child("profile").push().getKey());
        firebaseReference.child("profile").child(profile.getCode()).setValue(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                ObserverResponse response;
                if(task.isSuccessful()){
                    response = new ObserverResponse("createProfile",invite);
                }else{
                    response = new ObserverResponse("createProfile",null);
                }
                setChanged();
                notifyObservers(response);
            }
        });
    }

    public void getAllMyProfiles(String userCode){
        DatabaseReference firebaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseReference.child("profile").orderByChild("user/code").equalTo(userCode).addValueEventListener(
                new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    for(DataSnapshot data:dataSnapshot.getChildren()){
                        Profile profile = data.getValue(Profile.class);
                        Log.i("Profile",profile.getGym().getName());
                        setChanged();
                        notifyObservers(profile);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getGymUserProfiles(String gymCode){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Log.i("Caught",gymCode);
        databaseReference.child("profile").orderByChild("gym/code").equalTo(gymCode).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    Log.i("Caught","caught");
                    Profile profile = data.getValue(Profile.class);
                    ObserverResponse response = new ObserverResponse("getGymUserProfiles",profile);
                    setChanged();
                    notifyObservers(response);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public boolean updateOffensiveDays(Profile profile){
        DatabaseReference firebaseReference = FirebaseDatabase.getInstance().getReference();
        return firebaseReference.child("profile/"+profile.getCode()+"/progress")
                .setValue(profile.getProgress()).isSuccessful();
    }

    public boolean checkInOut(Profile profile){
        DatabaseReference firebaseReference = FirebaseDatabase.getInstance().getReference();
        return firebaseReference.child("profile/"+profile.getCode()+"/checkInOut").setValue(profile.getCheckInOut()).isSuccessful();
    }
}
