package com.example.myapplication.Admin.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.Admin.items.ListElementChat;
import com.example.myapplication.Admin.items.ListElementSite;
import com.example.myapplication.Admin.items.ListElementUser;
import com.example.myapplication.Supervisor.objetos.ListElementEquiposNuevo;
import com.example.myapplication.Supervisor.objetos.ListElementReportes;

import java.util.ArrayList;
import java.util.List;

public class NavigationActivityViewModel extends ViewModel {

    private MutableLiveData<ArrayList<ListElementUser>> activeUsers = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ListElementSite>> activeSites = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ListElementEquiposNuevo>> activeEquipments = new MutableLiveData<>();

    private MutableLiveData<ArrayList<ListElementChat>> usuarioChatAdmin = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ListElementChat>> usuarioChatSupervisor = new MutableLiveData<>();

    private MutableLiveData<ArrayList<ListElementUser>> inactiveUsers = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ListElementSite>> inactiveSites = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ListElementEquiposNuevo>> inactiveEquipments = new MutableLiveData<>();

    private final MutableLiveData<List<ListElementReportes>> activeReports = new MutableLiveData<>();
    private final MutableLiveData<List<ListElementReportes>> resolvedReports = new MutableLiveData<>();

    public LiveData<List<ListElementReportes>> getActiveReports() {
        return activeReports;
    }

    public LiveData<List<ListElementReportes>> getResolvedReports() {
        return resolvedReports;
    }

    public void setActiveReports(List<ListElementReportes> reports) {
        activeReports.setValue(reports);
    }

    public void setResolvedReports(List<ListElementReportes> reports) {
        resolvedReports.setValue(reports);
    }

    private MutableLiveData<String> inicio = new MutableLiveData<>();

    public MutableLiveData<String> getInicio() {
        return inicio;
    }

    public void setInicio(MutableLiveData<String> inicio) {
        this.inicio = inicio;
    }

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



    public MutableLiveData<ArrayList<ListElementChat>> getUsuarioChatAdmin() {
        return usuarioChatAdmin;
    }

    public void setUsuarioChatAdmin(MutableLiveData<ArrayList<ListElementChat>> usuarioChatAdmin) {
        this.usuarioChatAdmin = usuarioChatAdmin;
    }


    public MutableLiveData<ArrayList<ListElementChat>> getUsuarioChatSupervisor() {
        return usuarioChatSupervisor;
    }

    public void setUsuarioChatSupervisor(MutableLiveData<ArrayList<ListElementChat>> usuarioChatSupervisor) {
        this.usuarioChatSupervisor = usuarioChatSupervisor;
    }



    public MutableLiveData<ArrayList<ListElementUser>> getInactiveUsers() {
        return inactiveUsers;
    }

    public void setInactiveUsers(MutableLiveData<ArrayList<ListElementUser>> inactiveUsers) {
        this.inactiveUsers = inactiveUsers;
    }

    public MutableLiveData<ArrayList<ListElementEquiposNuevo>> getActiveEquipments() {
        return activeEquipments;
    }

    public void setActiveEquipments(MutableLiveData<ArrayList<ListElementEquiposNuevo>> activeEquipments) {
        this.activeEquipments = activeEquipments;
    }

    public MutableLiveData<ArrayList<ListElementEquiposNuevo>> getInactiveEquipments() {
        return inactiveEquipments;
    }

    public void setInactiveEquipments(MutableLiveData<ArrayList<ListElementEquiposNuevo>> inactiveEquipments) {
        this.inactiveEquipments = inactiveEquipments;
    }


}
