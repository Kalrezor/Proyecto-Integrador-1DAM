package com.dam.finanzas.model;

public class Transferencia {
    private String remitente;
    private String destinatario;
    private double monto;
    private String descripcion;

    public Transferencia(String remitente, String destinatario, double monto, String descripcion) {
        this.remitente = remitente;
        this.destinatario = destinatario;
        this.monto = monto;
        this.descripcion = descripcion;
    }

    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
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