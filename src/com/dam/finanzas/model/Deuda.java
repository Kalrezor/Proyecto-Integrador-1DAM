package com.dam.finanzas.model;

public class Deuda {
    private int idDeuda;
    private int idUsuario;
    private double montoTotal;
    private double montoPendiente;
    private String fechaVencimiento;
    private String descripcion;
    private String estado;

    public Deuda(int idDeuda, int idUsuario, double montoTotal, double montoPendiente, String fechaVencimiento, String descripcion, String estado) {
        this.idDeuda = idDeuda;
        this.idUsuario = idUsuario;
        this.montoTotal = montoTotal;
        this.montoPendiente = montoPendiente;
        this.fechaVencimiento = fechaVencimiento;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public Deuda(int idUsuario, double montoTotal, double montoPendiente, String fechaVencimiento, String descripcion, String estado) {
        this(0, idUsuario, montoTotal, montoPendiente, fechaVencimiento, descripcion, estado);
    }

    public int getIdDeuda() {
        return idDeuda;
    }

    public void setIdDeuda(int idDeuda) {
        this.idDeuda = idDeuda;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public double getMontoPendiente() {
        return montoPendiente;
    }

    public void setMontoPendiente(double montoPendiente) {
        this.montoPendiente = montoPendiente;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
