package com.example.myapplication.dataHolder;

import com.example.myapplication.Admin.items.ListElementChat;
import com.example.myapplication.Admin.items.ListElementSite;
import com.example.myapplication.Admin.items.ListElementUser;

import java.util.ArrayList;

public class DataHolder {
    private ArrayList<ListElementUser> activeUsers;
    private ArrayList<ListElementUser> inactiveUsers;
    private ArrayList<ListElementSite> activeSites;
    private ArrayList<ListElementSite> inactiveSites;

    private ArrayList<ListElementChat> usuarioChatAdmin;
    private ArrayList<ListElementChat> usuarioChatSupervisor;
    private String inicio;

    private static final DataHolder instance = new DataHolder();

    // Constructor privado para evitar la creación de nuevas instancias
    private DataHolder() {
        activeUsers = new ArrayList<>();
        inactiveUsers = new ArrayList<>();
        activeSites = new ArrayList<>();
        inactiveSites = new ArrayList<>();

        usuarioChatAdmin = new ArrayList<>();
        usuarioChatSupervisor = new ArrayList<>();
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

    public ArrayList<ListElementChat> getUsuarioChatAdmin() {
        return usuarioChatAdmin;
    }

    public void setUsuarioChatAdmin(ArrayList<ListElementChat> usuarioChatAdmin) {
        this.usuarioChatAdmin = usuarioChatAdmin;
    }

    public ArrayList<ListElementChat> getUsuarioChatSupervisor() {
        return usuarioChatSupervisor;
    }

    public void setUsuarioChatSupervisor(ArrayList<ListElementChat> usuarioChatSupervisor) {
        this.usuarioChatSupervisor = usuarioChatSupervisor;
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

