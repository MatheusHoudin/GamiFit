package com.br.gamifit.controller;

import android.os.Bundle;
import android.util.Log;

import com.br.gamifit.activity.MyGymListActivity;
import com.br.gamifit.adapter.MyGymsListAdapter;
import com.br.gamifit.dao_factory.FirebaseFactory;
import com.br.gamifit.database.GymFirebaseDAO;
import com.br.gamifit.helper.MyPreferences;
import com.br.gamifit.model.Gym;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MyGymListController implements Observer{
    private static MyGymListController myGymListController;

    private MyGymListActivity myGymListActivity;
    private List<Gym> myGymsList;
    private MyGymsListAdapter myGymsListAdapter;

    private MyGymListController(MyGymListActivity myGymListActivity){
        this.myGymListActivity = myGymListActivity;
        this.myGymsList = new ArrayList<>();
        myGymsListAdapter = new MyGymsListAdapter(myGymsList,this.myGymListActivity.getApplicationContext()
                ,myGymListActivity.getHandler());
    }

    private static void setUpObservable(){
        GymFirebaseDAO gymFirebaseDAO = FirebaseFactory.getGymFirebaseDAO();
        gymFirebaseDAO.addObserver(myGymListController);
    }

    public void getAllMyGyms(){
        String loggedUserCode = new MyPreferences(myGymListActivity).getUserCode();
        GymFirebaseDAO gymFirebaseDAO = FirebaseFactory.getGymFirebaseDAO();
        gymFirebaseDAO.getAllMyGyms(loggedUserCode);
    }

    public static MyGymListController getMyGymListController(MyGymListActivity myGymListActivity) {
        if(myGymListController==null){
            myGymListController = new MyGymListController(myGymListActivity);
            setUpObservable();
        }
        return myGymListController;
    }

    public MyGymsListAdapter getMyGymsListAdapter() {
        return myGymsListAdapter;
    }

    @Override
    public void update(Observable observable, Object o) {
        if(observable instanceof GymFirebaseDAO){
            if(o instanceof Gym){
                myGymsList.add((Gym) o);
                //TODO: See if we can optmize here, just by receiving an information that says all the data is loaded, and after it we can call the myGymListAdapter.notifyDataSerChanged()
                myGymsListAdapter.notifyDataSetChanged();
            }
        }

    }

    public List<Gym> getMyGymsList() {
        return myGymsList;
    }
}
