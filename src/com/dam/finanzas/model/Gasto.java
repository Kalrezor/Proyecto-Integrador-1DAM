package com.dam.finanzas.model;

public class Gasto {
    private int idUsuario;
    private String descripcion;
    private String categoria;
    private double monto;
    private String fecha;

    public Gasto(int idUsuario, String descripcion, String categoria, double monto, String fecha) {
        this.idUsuario = idUsuario;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.monto = monto;
        this.fecha = fecha;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
