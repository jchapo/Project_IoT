package com.example.myapplication.Admin.dataHolder;

import com.example.myapplication.Admin.items.ListElementSite;
import com.example.myapplication.Admin.items.ListElementUser;

import java.util.ArrayList;

public class DataHolder {
    private ArrayList<ListElementUser> activeUsers;
    private ArrayList<ListElementUser> inactiveUsers;
    private ArrayList<ListElementSite> activeSites;
    private ArrayList<ListElementSite> inactiveSites;
    private String inicio;

    private static final DataHolder instance = new DataHolder();

    // Constructor privado para evitar la creación de nuevas instancias
    private DataHolder() {
        activeUsers = new ArrayList<>();
        inactiveUsers = new ArrayList<>();
        activeSites = new ArrayList<>();
        inactiveSites = new ArrayList<>();
        inicio = new String();
    }
    public static DataHolder getInstance() {
        return instance;
    }
    public ArrayList<ListElementUser> getActiveUsers() {
        return activeUsers;
    }

    public void setActiveUsers(ArrayList<ListElementUser> activeUsers) {
        this.activeUsers = activeUsers;
    }

    public ArrayList<ListElementUser> getInactiveUsers() {
        return inactiveUsers;
    }

    public void setInactiveUsers(ArrayList<ListElementUser> inactiveUsers) {
        this.inactiveUsers = inactiveUsers;
    }

    public ArrayList<ListElementSite> getActiveSites() {
        return activeSites;
    }

    public void setActiveSites(ArrayList<ListElementSite> activeSites) {
        this.activeSites = activeSites;
    }

    public ArrayList<ListElementSite> getInactiveSites() {
        return inactiveSites;
    }

    public void setInactiveSites(ArrayList<ListElementSite> inactiveSites) {
        this.inactiveSites = inactiveSites;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }
}

