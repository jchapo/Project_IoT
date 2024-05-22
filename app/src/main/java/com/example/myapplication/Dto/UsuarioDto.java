package com.example.myapplication.Dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class UsuarioDto {
    private Integer idUsuario;
    private String contrasenia;
    private String contaseniaHashed;
    private String correo;
    private String telefono;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private Integer idRoles;
    private Integer idImagenPerfil;
    private Integer empresaTecnicoSup;
    private Integer estadoTecnico;
    private Integer estadoActivo;
    private Integer genero;
    private String dni;
    private Integer primerInicio;
    private LocalDate fechaCreacion;
    private String direccionUsuario;

    public String getDireccionUsuario() {
        return direccionUsuario;
    }

    public void setDireccionUsuario(String direccionUsuario) {
        this.direccionUsuario = direccionUsuario;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getContaseniaHashed() {
        return contaseniaHashed;
    }

    public void setContaseniaHashed(String contaseniaHashed) {
        this.contaseniaHashed = contaseniaHashed;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getIdRoles() {
        return idRoles;
    }

    public void setIdRoles(Integer idRoles) {
        this.idRoles = idRoles;
    }

    public Integer getIdImagenPerfil() {
        return idImagenPerfil;
    }

    public void setIdImagenPerfil(Integer idImagenPerfil) {
        this.idImagenPerfil = idImagenPerfil;
    }

    public Integer getEmpresaTecnicoSup() {
        return empresaTecnicoSup;
    }

    public void setEmpresaTecnicoSup(Integer empresaTecnicoSup) {
        this.empresaTecnicoSup = empresaTecnicoSup;
    }

    public Integer getEstadoTecnico() {
        return estadoTecnico;
    }

    public void setEstadoTecnico(Integer estadoTecnico) {
        this.estadoTecnico = estadoTecnico;
    }

    public Integer getEstadoActivo() {
        return estadoActivo;
    }

    public void setEstadoActivo(Integer estadoActivo) {
        this.estadoActivo = estadoActivo;
    }

    public Integer getGenero() {
        return genero;
    }

    public void setGenero(Integer genero) {
        this.genero = genero;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Integer getPrimerInicio() {
        return primerInicio;
    }

    public void setPrimerInicio(Integer primerInicio) {
        this.primerInicio = primerInicio;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
