package com.br.gamifit.database.dao_interface;


import android.content.Context;
import android.widget.Adapter;
import android.widget.BaseAdapter;

import com.br.gamifit.model.User;
import com.google.android.gms.tasks.Task;

import java.util.List;

public interface IUserDAO {

    Exception createUserAcount(User user);
    Exception saveUser(User user);
    void getAllUsers();
    void getUser(String email);
    void getScannedUser(String scannedUserCode);
    void getUsersByTheirName(String name);

}
