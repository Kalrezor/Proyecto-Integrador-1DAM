package com.dam.finanzas.model;

public class Transferencia {
    private int idRemitente;
    private int idDestinatario;
    private double monto;
    private String descripcion;

    public Transferencia(int idRemitente, int idDestinatario, double monto, String descripcion) {
        this.idRemitente = idRemitente;
        this.idDestinatario = idDestinatario;
        this.monto = monto;
        this.descripcion = descripcion;
    }

    public int getIdRemitente() {
        return idRemitente;
    }

    public void setIdRemitente(int idRemitente) {
        this.idRemitente = idRemitente;
    }

    public int getIdDestinatario() {
        return idDestinatario;
    }

    public void setIdDestinatario(int idDestinatario) {
        this.idDestinatario = idDestinatario;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}