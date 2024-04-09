package com.example.myapplication.Admin.items;

public class ListElement {
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ListElement(String color, String name, String user, String status) {
        this.color = color;
        this.name = name;
        this.user = user;
        this.status = status;
    }

    public String color;
    public String name;
    public String user;
    public String status;

}
