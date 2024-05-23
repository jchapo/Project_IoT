package com.example.myapplication.Admin.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.Admin.items.ListAdapterUser;
import com.example.myapplication.Admin.items.ListElementUser;

import java.util.List;

public class NavigationActivityViewModel extends ViewModel {
    private MutableLiveData<List<ListElementUser>> activeUsersLiveData = new MutableLiveData<>();
    private MutableLiveData<List<ListElementUser>> inactiveUsersLiveData = new MutableLiveData<>();

    public MutableLiveData<List<ListElementUser>> getActiveUsersLiveData() {
        return activeUsersLiveData;
    }

    public void setActiveUsersLiveData(MutableLiveData<List<ListElementUser>> activeUsersLiveData) {
        this.activeUsersLiveData = activeUsersLiveData;
    }

    public MutableLiveData<List<ListElementUser>> getInactiveUsersLiveData() {
        return inactiveUsersLiveData;
    }

    public void setInactiveUsersLiveData(MutableLiveData<List<ListElementUser>> inactiveUsersLiveData) {
        this.inactiveUsersLiveData = inactiveUsersLiveData;
    }
}
