package com.clubdeportivo.model;

public class TipoCuota {

    private int id;
    private String nombre;
    private double precio;
    private int maxInvitacionesMes;

    public TipoCuota(int id, String nombre, double precio, int maxInvitacionesMes) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.maxInvitacionesMes = maxInvitacionesMes;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getMaxInvitacionesMes() {
        return maxInvitacionesMes;
    }

    @Override
    public String toString() {
        return nombre;
    }
}