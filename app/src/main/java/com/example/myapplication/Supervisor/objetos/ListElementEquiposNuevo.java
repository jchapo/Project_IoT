package com.example.myapplication.Supervisor.objetos;

import java.io.Serializable;
import java.util.Date;

public class ListElementEquiposNuevo implements Serializable {

    private String nameEquipo;
    private String marca;
    private String modelo;
    private String tipoEquipo;
    private String descripcionEquipo;
    private String status;
    private String idSitio;
    private String sku;
    private String fecha_ingreso;

    private String imagenEquipo;

    public ListElementEquiposNuevo(String nameEquipo, String marca, String modelo, String tipoEquipo,
                                   String descripcionEquipo, String status, String idSitio, String sku, String fecha_ingreso,
                                   String imagenEquipo) {
        this.nameEquipo = nameEquipo;
        this.marca = marca;
        this.modelo = modelo;
        this.tipoEquipo = tipoEquipo;
        this.descripcionEquipo = descripcionEquipo;
        this.status = status;
        this.idSitio = idSitio;
        this.sku = sku;
        this.fecha_ingreso = fecha_ingreso;
        this.imagenEquipo = imagenEquipo;

    }

    public String getNameEquipo() {
        return nameEquipo;
    }

    public void setNameEquipo(String nameEquipo) {
        this.nameEquipo = nameEquipo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getTipoEquipo() {
        return tipoEquipo;
    }

    public void setTipoEquipo(String tipoEquipo) {
        this.tipoEquipo = tipoEquipo;
    }

    public String getDescripcionEquipo() {
        return descripcionEquipo;
    }

    public void setDescripcionEquipo(String descripcionEquipo) {
        this.descripcionEquipo = descripcionEquipo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdSitio() {
        return idSitio;
    }

    public void setIdSitio(String idSitio) {
        this.idSitio = idSitio;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(String fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public String getImagenEquipo() {
        return imagenEquipo;
    }

    public void setImagenEquipo(String imagenEquipo) {
        this.imagenEquipo = imagenEquipo;
    }
}
