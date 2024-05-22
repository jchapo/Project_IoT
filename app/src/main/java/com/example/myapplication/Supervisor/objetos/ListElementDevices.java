package com.example.myapplication.Supervisor.objetos;

import java.io.Serializable;

public class ListElementDevices implements Serializable {
    public String sku;
    public String serie;
    public String marca;
    public String tipo;
    public String fechaIngreso;
    public String descripcion;
    public String name;
    public String status;

    public ListElementDevices(String sku, String serie, String marca, String tipo, String fechaIngreso, String descripcion, String name, String status) {
        this.sku = sku;
        this.serie = serie;
        this.marca = marca;
        this.tipo = tipo;
        this.fechaIngreso = fechaIngreso;
        this.descripcion = descripcion;
        this.name = name;
        this.status = status;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return tipo;
    }

    public void setModelo(String tipo) {
        this.tipo = tipo;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
