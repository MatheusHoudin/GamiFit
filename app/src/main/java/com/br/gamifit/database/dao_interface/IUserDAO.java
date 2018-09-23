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
    void getAllUsers(List<User> usersList, BaseAdapter listAdapter);
    void getUser(String email);
    void getUsersByTheirName(List<User> usersList,BaseAdapter adapter,String name);

}
