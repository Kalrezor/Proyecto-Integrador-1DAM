package com.dam.finanzas.model;

public class ObjetivoFinanciero {
    private int idObjetivo;
    private int idUsuario;
    private String descripcion;
    private double costo;
    private double ahorroMensualSugerido;
    private String tiempoNecesario;
    private String estado;

    public ObjetivoFinanciero(int idObjetivo, int idUsuario, String descripcion, double costo, double ahorroMensualSugerido, String tiempoNecesario, String estado) {
        this.idObjetivo = idObjetivo;
        this.idUsuario = idUsuario;
        this.descripcion = descripcion;
        this.costo = costo;
        this.ahorroMensualSugerido = ahorroMensualSugerido;
        this.tiempoNecesario = tiempoNecesario;
        this.estado = estado;
    }

    // Getters y Setters
    public int getIdObjetivo() {
        return idObjetivo;
    }

    public void setIdObjetivo(int idObjetivo) {
        this.idObjetivo = idObjetivo;
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

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public double getAhorroMensualSugerido() {
        return ahorroMensualSugerido;
    }

    public void setAhorroMensualSugerido(double ahorroMensualSugerido) {
        this.ahorroMensualSugerido = ahorroMensualSugerido;
    }

    public String getTiempoNecesario() {
        return tiempoNecesario;
    }

    public void setTiempoNecesario(String tiempoNecesario) {
        this.tiempoNecesario = tiempoNecesario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
