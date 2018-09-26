package com.br.gamifit.database;

import android.support.annotation.NonNull;
import android.util.Log;

import com.br.gamifit.database.dao_interface.IInviteDAO;
import com.br.gamifit.model.GymInvite;
import com.google.android.gms.common.data.DataBufferObserver;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Observable;

public class InviteFirebaseDAO extends Observable {

    public boolean createInvite(GymInvite invite) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        String generatedCode = reference.child("invite").push().getKey();
        invite.setCode(generatedCode);
        return reference.child("invite").child(invite.getCode()).setValue(invite).isSuccessful();
    }

    public void getUserInvites(String userCode) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("invite").orderByChild("user/code").equalTo(userCode).addValueEventListener(
                new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    for(DataSnapshot data:dataSnapshot.getChildren()){
                        GymInvite gymInvite = data.getValue(GymInvite.class);
                        Log.i("invite",gymInvite.getGym().getName());
                        setChanged();
                        notifyObservers(gymInvite);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public boolean delete(GymInvite gymInvite){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        return reference.child("invite").child(gymInvite.getCode()).removeValue().isSuccessful();
    }

}
