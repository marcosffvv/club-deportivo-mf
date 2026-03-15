package com.clubdeportivo.model;

import java.time.LocalDate;

public class Socio {

    private int idSocio;
    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;
    private LocalDate fechaAlta;
    private boolean activo;
    private int idTipoCuota;

    public Socio() {
    }

    public Socio(int idSocio, String nombre, String apellidos, String email,
                 String telefono, LocalDate fechaAlta, boolean activo, int idTipoCuota) {
        this.idSocio = idSocio;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
        this.fechaAlta = fechaAlta;
        this.activo = activo;
        this.idTipoCuota = idTipoCuota;
    }

    public int getIdSocio() {
        return idSocio;
    }

    public void setIdSocio(int idSocio) {
        this.idSocio = idSocio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getIdTipoCuota() {
        return idTipoCuota;
    }

    public void setIdTipoCuota(int idTipoCuota) {
        this.idTipoCuota = idTipoCuota;
    }
}