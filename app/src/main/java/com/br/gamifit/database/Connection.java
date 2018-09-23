package com.br.gamifit.database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Connection {

    public static DatabaseReference getDatabaseReference(){ return FirebaseDatabase.getInstance().getReference(); }

}
