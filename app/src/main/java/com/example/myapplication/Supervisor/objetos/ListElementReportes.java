package com.example.myapplication.Supervisor.objetos;

import java.io.Serializable;

public class ListElementReportes implements Serializable {

    private String nombre_reporte;

    private String sitio;

    private String fecha_creacion;

    private String fecha_final;

    private String supervisor_creador;
    private String equipo_objetivo;
    private String descripcion;

    public ListElementReportes(String nombre_reporte, String sitio, String fecha_creacion, String fecha_final, String supervisor_creador, String equipo_objetivo, String descripcion) {
        this.nombre_reporte = nombre_reporte;
        this.sitio = sitio;
        this.fecha_creacion = fecha_creacion;
        this.fecha_final = fecha_final;
        this.supervisor_creador = supervisor_creador;
        this.equipo_objetivo = equipo_objetivo;
        this.descripcion = descripcion;
    }


    public String getNombre_reporte() {
        return nombre_reporte;
    }

    public void setNombre_reporte(String nombre_reporte) {
        this.nombre_reporte = nombre_reporte;
    }



    public String getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(String fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public String getFecha_final() {
        return fecha_final;
    }

    public void setFecha_final(String fecha_final) {
        this.fecha_final = fecha_final;
    }

    public String getSupervisor_creador() {
        return supervisor_creador;
    }

    public void setSupervisor_creador(String supervisor_creador) {
        this.supervisor_creador = supervisor_creador;
    }

    public String getSitio() {
        return sitio;
    }

    public void setSitio(String sitio) {
        this.sitio = sitio;
    }

    public String getEquipo_objetivo() {
        return equipo_objetivo;
    }

    public void setEquipo_objetivo(String equipo_objetivo) {
        this.equipo_objetivo = equipo_objetivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
