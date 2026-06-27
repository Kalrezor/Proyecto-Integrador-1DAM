package com.dam.finanzas.model.bbdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBBDD {
    private String url;
    private String driver;

    public ConexionBBDD() {
        url = "jdbc:sqlite:BBDD/Prueba1PIntegrador.db";
        driver = "org.sqlite.JDBC";
    }

    public Connection getConexion() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection con = DriverManager.getConnection(url);
        return con;
    }
}
