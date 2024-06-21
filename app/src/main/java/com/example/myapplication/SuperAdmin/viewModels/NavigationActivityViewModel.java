package com.example.myapplication.SuperAdmin.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.Admin.items.ListElementSite;
import com.example.myapplication.Admin.items.ListElementUser;
import com.example.myapplication.SuperAdmin.list.ListElementSuperAdminUser;
import com.example.myapplication.Supervisor.objetos.ListElementEquiposNuevo;

import java.util.ArrayList;

public class NavigationActivityViewModel extends ViewModel {

    private MutableLiveData<ArrayList<ListElementSuperAdminUser>> adminUser = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ListElementSuperAdminUser>> supervisorUser = new MutableLiveData<>();

    public MutableLiveData<ArrayList<ListElementSuperAdminUser>> getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(MutableLiveData<ArrayList<ListElementSuperAdminUser>> adminUser) {
        this.adminUser = adminUser;
    }

    public MutableLiveData<ArrayList<ListElementSuperAdminUser>> getSupervisorUser() {
        return supervisorUser;
    }

    public void setSupervisorUser(MutableLiveData<ArrayList<ListElementSuperAdminUser>> supervisorUser) {
        this.supervisorUser = supervisorUser;
    }
}
