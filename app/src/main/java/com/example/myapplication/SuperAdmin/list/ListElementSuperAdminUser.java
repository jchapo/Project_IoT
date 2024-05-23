package com.example.myapplication.SuperAdmin.list;

import java.io.Serializable;

public class ListElementSuperAdminUser implements Serializable {
    public String name;
    public String lastname;
    public String user;
    public String status;
    public String dni;
    public String mail;
    public String phone;
    public String address;
    private Integer primerInicio;
    private String fechaCreacion;

    public ListElementSuperAdminUser() {
    }
    public ListElementSuperAdminUser(String dni, String name, String lastname, String user, String status, String mail, String phone, String address, Integer primerInicio, String fechaCreacion) {
        this.dni = dni;
        this.name = name;
        this.lastname = lastname;
        this.user = user;
        this.status = status;
        this.mail = mail;
        this.phone = phone;
        this.address = address;
        this.primerInicio = primerInicio;
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getPrimerInicio() {
        return primerInicio;
    }

    public void setPrimerInicio(Integer primerInicio) {
        this.primerInicio = primerInicio;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
