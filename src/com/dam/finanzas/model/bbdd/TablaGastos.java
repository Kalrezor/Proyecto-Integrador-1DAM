package com.dam.finanzas.model.bbdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.dam.finanzas.model.Gasto;

public class TablaGastos {

    static final String NOM_TABLA_GAS = "Gasto";
    static final String NOM_COL_ID_GAS = "id_gasto";
    static final String NOM_COL_ID_USER = "id_usuario";
    static final String NOM_COL_DESC_GAS = "descripcion";
    static final String NOM_COL_CAT_GAS = "categoria";
    static final String NOM_COL_MONTO_GAS = "monto";
    static final String NOM_COL_FECHA_GAS = "fecha";

    private ConexionBBDD conBBDD;

    public TablaGastos() {
        conBBDD = new ConexionBBDD();
    }

    public int registrarGasto(Gasto gasto) {
        int res = 0;
        String query = "INSERT INTO " + NOM_TABLA_GAS + "(" + NOM_COL_ID_USER
                + ", " + NOM_COL_DESC_GAS + ", " + NOM_COL_CAT_GAS + ", " + NOM_COL_MONTO_GAS + ", " + NOM_COL_FECHA_GAS + ") VALUES (?, ?, ?, ?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = conBBDD.getConexion();
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, gasto.getIdUsuario());
            pstmt.setString(2, gasto.getDescripcion());
            pstmt.setString(3, gasto.getCategoria());
            pstmt.setDouble(4, gasto.getMonto());
            pstmt.setString(5, gasto.getFecha());

            res = pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return res;
    }

    public double obtenerTotalGastos(int idUsuario) {
        double total = 0;
        String query = "SELECT SUM(monto) AS total FROM Gasto WHERE id_usuario = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = conBBDD.getConexion();
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, idUsuario);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                total = rs.getDouble("total");
            }
        } catch (Exception e) {
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

        return total;
    }

    public Map<String, Double> obtenerTotalGastosPorCategoria(int idUsuario) {
        Map<String, Double> totalPorCategoria = new HashMap<>();
        String query = "SELECT categoria, SUM(monto) AS total FROM Gasto WHERE id_usuario = ? GROUP BY categoria";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = conBBDD.getConexion();
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, idUsuario);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String categoria = rs.getString("categoria");
                double total = rs.getDouble("total");
                totalPorCategoria.put(categoria, total);
            }
        } catch (Exception e) {
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

        return totalPorCategoria;
    }
} 