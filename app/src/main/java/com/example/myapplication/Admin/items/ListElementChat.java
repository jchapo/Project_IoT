package com.example.myapplication.Admin.items;

import java.io.Serializable;

public class ListElementChat implements Serializable {
    private String usuarioChatName;
    private String role;
    private String id; // Añadir este campo

    public ListElementChat() {
    }

    // Constructor, getters y setters
    public ListElementChat(String usuarioChatName, String role, String id) {
        this.usuarioChatName = usuarioChatName;
        this.role = role;
        this.id = id;
    }

    public String getUsuarioChatName() {
        return usuarioChatName;
    }

    public void setUsuarioChatName(String usuarioChatName) {
        this.usuarioChatName = usuarioChatName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
