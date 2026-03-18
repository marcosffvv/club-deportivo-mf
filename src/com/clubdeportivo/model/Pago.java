package com.clubdeportivo.model;

import java.time.LocalDate;

public class Pago {

    private int idPago;
    private int idSocio;
    private int mes;
    private int anio;
    private LocalDate fechaPago;
    private double importe;

    public Pago() {}

    public Pago(int idSocio, int mes, int anio, LocalDate fechaPago, double importe) {
        this.idSocio = idSocio;
        this.mes = mes;
        this.anio = anio;
        this.fechaPago = fechaPago;
        this.importe = importe;
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public int getIdSocio() {
        return idSocio;
    }

    public void setIdSocio(int idSocio) {
        this.idSocio = idSocio;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }
}