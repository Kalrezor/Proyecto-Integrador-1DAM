package com.dam.finanzas.model.bbdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dam.finanzas.model.Ingreso;

public class TablaIngresos {

    static final String NOM_TABLA_ING = "Ingreso";
    static final String NOM_COL_ID_ING = "id_ingreso";
    static final String NOM_COL_ID_USER = "id_usuario";
    static final String NOM_COL_DESC_ING = "descripcion";
    static final String NOM_COL_MONTO_ING = "monto";
    static final String NOM_COL_FECHA_ING = "fecha";

    private ConexionBBDD conBBDD;

    public TablaIngresos() {
        conBBDD = new ConexionBBDD();
    }

    public int registrarIngreso(Ingreso ingreso) {
        int res = 0;
        String query = "INSERT INTO " + NOM_TABLA_ING + "(" + NOM_COL_ID_USER
                + ", " + NOM_COL_DESC_ING + ", " + NOM_COL_MONTO_ING + ", " + NOM_COL_FECHA_ING + ") VALUES (?, ?, ?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = conBBDD.getConexion();
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, ingreso.getIdUsuario());
            pstmt.setString(2, ingreso.getDescripcion());
            pstmt.setDouble(3, ingreso.getMonto());
            pstmt.setString(4, ingreso.getFecha());

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

    public double obtenerTotalIngresos(int idUsuario) {
        double total = 0;
        String query = "SELECT SUM(monto) AS total FROM Ingreso WHERE id_usuario = ?";

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

}
