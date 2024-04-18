package com.example.myapplication.Admin.items;

import java.io.Serializable;

public class ListElementChat implements Serializable {
    public String usuario;
    public String mensaje;
    public String fecha;


    public ListElementChat(String usuario, String mensaje, String fecha) {
        this.usuario = usuario;
        this.mensaje = mensaje;
        this.fecha = fecha;
    }



    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

}
