package com.example.myapplication.Admin.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.Admin.items.ListElementSite;
import com.example.myapplication.Admin.items.ListElementUser;

import java.util.ArrayList;
import java.util.List;

public class NavigationActivityViewModel extends ViewModel {

    private MutableLiveData<ArrayList<ListElementUser>> activeUsers = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ListElementSite>> activeSites = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ListElementUser>> inactiveUsers = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ListElementSite>> inactiveSites = new MutableLiveData<>();

    public MutableLiveData<ArrayList<ListElementSite>> getActiveSites() {
        return activeSites;
    }

    public void setActiveSites(MutableLiveData<ArrayList<ListElementSite>> activeSites) {
        this.activeSites = activeSites;
    }

    public MutableLiveData<ArrayList<ListElementSite>> getInactiveSites() {
        return inactiveSites;
    }

    public void setInactiveSites(MutableLiveData<ArrayList<ListElementSite>> inactiveSites) {
        this.inactiveSites = inactiveSites;
    }

    public MutableLiveData<ArrayList<ListElementUser>> getActiveUsers() {
        return activeUsers;
    }

    public void setActiveUsers(MutableLiveData<ArrayList<ListElementUser>> activeUsers) {
        this.activeUsers = activeUsers;
    }

    public MutableLiveData<ArrayList<ListElementUser>> getInactiveUsers() {
        return inactiveUsers;
    }

    public void setInactiveUsers(MutableLiveData<ArrayList<ListElementUser>> inactiveUsers) {
        this.inactiveUsers = inactiveUsers;
    }
}
