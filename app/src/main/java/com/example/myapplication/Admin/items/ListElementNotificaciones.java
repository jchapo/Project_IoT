package com.example.myapplication.Admin.items;

import java.io.Serializable;

public class ListElementNotificaciones implements Serializable {
    public String accion;
    public String descripcionResumida;
    public String fecha;


    public ListElementNotificaciones(String accion, String descripcionResumida, String fecha) {
        this.accion = accion;
        this.descripcionResumida = descripcionResumida;
        this.fecha = fecha;
    }



    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getDescripcionResumida() {
        return descripcionResumida;
    }

    public void setDescripcionResumida(String descripcionResumida) {
        this.descripcionResumida = descripcionResumida;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

}
