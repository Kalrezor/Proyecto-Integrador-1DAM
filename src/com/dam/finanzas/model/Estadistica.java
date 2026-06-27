package com.dam.finanzas.model;

public class Estadistica {
    private int idEstadistica;
    private int idUsuario;
    private double ingresosTotales;
    private double gastosTotales;
    private int numeroTransacciones;
    private int numeroDeudas;
    private int numeroObjetivos;
    private String fecha;

    public Estadistica(int idEstadistica, int idUsuario, double ingresosTotales, double gastosTotales, int numeroTransacciones, int numeroDeudas, int numeroObjetivos, String fecha) {
        this.idEstadistica = idEstadistica;
        this.idUsuario = idUsuario;
        this.ingresosTotales = ingresosTotales;
        this.gastosTotales = gastosTotales;
        this.numeroTransacciones = numeroTransacciones;
        this.numeroDeudas = numeroDeudas;
        this.numeroObjetivos = numeroObjetivos;
        this.fecha = fecha;
    }

    // Getters y Setters
    public int getIdEstadistica() {
        return idEstadistica;
    }

    public void setIdEstadistica(int idEstadistica) {
        this.idEstadistica = idEstadistica;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public double getIngresosTotales() {
        return ingresosTotales;
    }

    public void setIngresosTotales(double ingresosTotales) {
        this.ingresosTotales = ingresosTotales;
    }

    public double getGastosTotales() {
        return gastosTotales;
    }

    public void setGastosTotales(double gastosTotales) {
        this.gastosTotales = gastosTotales;
    }

    public int getNumeroTransacciones() {
        return numeroTransacciones;
    }

    public void setNumeroTransacciones(int numeroTransacciones) {
        this.numeroTransacciones = numeroTransacciones;
    }

    public int getNumeroDeudas() {
        return numeroDeudas;
    }

    public void setNumeroDeudas(int numeroDeudas) {
        this.numeroDeudas = numeroDeudas;
    }

    public int getNumeroObjetivos() {
        return numeroObjetivos;
    }

    public void setNumeroObjetivos(int numeroObjetivos) {
        this.numeroObjetivos = numeroObjetivos;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
