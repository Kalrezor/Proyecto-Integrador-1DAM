package com.dam.finanzas.model.bbdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dam.finanzas.model.ObjetivoFinanciero;

public class TablaObjetivoFinanciero {

    static final String NOM_TABLA_OBJ = "ObjetivoFinanciero";
    static final String NOM_COL_ID_OBJ = "id_objetivo";
    static final String NOM_COL_ID_USER = "id_usuario";
    static final String NOM_COL_DESC_OBJ = "descripcion";
    static final String NOM_COL_COST_OBJ = "costo";
    static final String NOM_COL_AHORRO_MENSUAL_OBJ = "ahorro_mensual_sugerido";
    static final String NOM_COL_TIEMPO_NECESARIO = "tiempo_necesario";
    static final String NOM_COL_ESTADO_OBJ = "estado";

    private ConexionBBDD conBBDD;

    public TablaObjetivoFinanciero() {
        conBBDD = new ConexionBBDD();
    }

    public int registrarObjetivoFinanciero(ObjetivoFinanciero objetivo) {
        int res = 0;
        String query = "INSERT INTO ObjetivoFinanciero (id_usuario, descripcion, costo, ahorro_mensual_sugerido, tiempo_necesario, estado) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = conBBDD.getConexion();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setInt(1, objetivo.getIdUsuario());
            pstmt.setString(2, objetivo.getDescripcion());
            pstmt.setDouble(3, objetivo.getCosto());
            pstmt.setDouble(4, objetivo.getAhorroMensualSugerido());
            pstmt.setString(5, objetivo.getTiempoNecesario());
            pstmt.setString(6, objetivo.getEstado());

            res = pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public int actualizarObjetivoFinanciero(ObjetivoFinanciero objetivo) {
        int res = 0;
        String query = "UPDATE ObjetivoFinanciero SET descripcion = ?, costo = ?, ahorro_mensual_sugerido = ?, tiempo_necesario = ?, estado = ? WHERE id_objetivo = ?";

        try (Connection con = conBBDD.getConexion();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, objetivo.getDescripcion());
            pstmt.setDouble(2, objetivo.getCosto());
            pstmt.setDouble(3, objetivo.getAhorroMensualSugerido());
            pstmt.setString(4, objetivo.getTiempoNecesario());
            pstmt.setString(5, objetivo.getEstado());
            pstmt.setInt(6, objetivo.getIdObjetivo());

            res = pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public List<ObjetivoFinanciero> obtenerObjetivosPorUsuario(int idUsuario) {
        List<ObjetivoFinanciero> objetivos = new ArrayList<>();
        String query = "SELECT * FROM ObjetivoFinanciero WHERE id_usuario = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = conBBDD.getConexion();
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, idUsuario);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ObjetivoFinanciero objetivo = new ObjetivoFinanciero(
                    rs.getInt("id_objetivo"),
                    rs.getInt("id_usuario"),
                    rs.getString("descripcion"),
                    rs.getDouble("costo"),
                    rs.getDouble("ahorro_mensual_sugerido"),
                    rs.getString("tiempo_necesario"),
                    rs.getString("estado")
                );
                objetivos.add(objetivo);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return objetivos;
    }

}
