package com.example.myapplication.Admin.items;

import java.io.Serializable;

public class ListElementSite implements Serializable {
    public String department;
    public String province;
    public String district;
    public String address;
    public String location;
    public String ubigeo;
    public String zonetype;
    public String sitetype;
    public String name;
    public String status;
    private Double latitud;
    private Double longitud;
    public String coordenadas;
    private String fechaCreacion;
    private String imageUrl;
    private String superAsignados;


    public ListElementSite() {
    }
    public ListElementSite(String department, String name, String status, String province,
                           String district, String address,String location, String ubigeo,
                           String zonetype, String sitetype, Double latitud, Double longitud,
                           String coordenadas,String fechaCreacion, String imageUrl,String superAsignados) {

        this.department = department;
        this.province = province;
        this.district = district;
        this.address = address;
        this.location =location;
        this.ubigeo = ubigeo;
        this.zonetype = zonetype;
        this.sitetype = sitetype;
        this.name = name;
        this.latitud = latitud;
        this.longitud = longitud;
        this.status = status;
        this.coordenadas = coordenadas;
        this.fechaCreacion = fechaCreacion;
        this.imageUrl = imageUrl;
        this.superAsignados = superAsignados;
    }

    public String getSuperAsignados() {
        return superAsignados;
    }

    public void setSuperAsignados(String superAsignados) {
        this.superAsignados = superAsignados;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }
}
