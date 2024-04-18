package com.example.myapplication.Admin.items;

import java.io.Serializable;

public class ListElementSite implements Serializable {
    public String department;
    public String province;
    public String district;
    public String address;
    public String ubigeo;
    public String zonetype;
    public String sitetype;

    public String name;
    public String status;

    private double latitud;

    private double longitud;

    public String coordenadas;



    public ListElementSite(String department, String name, String status, String province,
                           String district, String address, String ubigeo,
                           String zonetype, String sitetype, double latitud, double longitud, String coordenadas) {
        this.department = department;
        this.province = province;
        this.district = district;
        this.address = address;
        this.ubigeo = ubigeo;
        this.zonetype = zonetype;
        this.sitetype = sitetype;
        this.name = name;
        this.latitud = latitud;
        this.longitud = longitud;
        this.status = status;
        this.coordenadas = coordenadas;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUbigeo() {
        return ubigeo;
    }

    public void setUbigeo(String ubigeo) {
        this.ubigeo = ubigeo;
    }

    public String getZonetype() {
        return zonetype;
    }

    public void setZonetype(String zonetype) {
        this.zonetype = zonetype;
    }

    public String getSitetype() {
        return sitetype;
    }

    public void setSitetype(String sitetype) {
        this.sitetype = sitetype;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }
}
