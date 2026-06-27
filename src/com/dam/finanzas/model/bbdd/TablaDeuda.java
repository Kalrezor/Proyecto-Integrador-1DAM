package com.dam.finanzas.model.bbdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.dam.finanzas.model.Deuda;

public class TablaDeuda {

    static final String NOM_TABLA_DEU = "Deuda";
    static final String NOM_COL_ID_DEU = "id_deuda";
    static final String NOM_COL_ID_USER = "id_usuario";
    static final String NOM_COL_MONTO_TOT_DEU = "monto_total";
    static final String NOM_COL_MONTO_PEN_DEU = "monto_pendiente";
    static final String NOM_COL_FECHA_VENC_TRAN = "fecha_vencimiento";
    static final String NOM_COL_DESC_DEU = "descripcion";
    static final String NOM_COL_ESTADO_DEU = "estado";

    private ConexionBBDD conBBDD;

    public TablaDeuda() {
        conBBDD = new ConexionBBDD();
    }

    public int registrarDeuda(Deuda deuda) {
        int res = 0;
        String query = "INSERT INTO " + NOM_TABLA_DEU + "(" + NOM_COL_ID_USER
                + ", " + NOM_COL_MONTO_TOT_DEU + ", " + NOM_COL_MONTO_PEN_DEU + ", " + NOM_COL_FECHA_VENC_TRAN
                + ", " + NOM_COL_DESC_DEU + ", " + NOM_COL_ESTADO_DEU + ") VALUES (?, ?, ?, ?, ?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = conBBDD.getConexion();
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, deuda.getIdUsuario());
            pstmt.setDouble(2, deuda.getMontoTotal());
            pstmt.setDouble(3, deuda.getMontoPendiente());
            pstmt.setString(4, deuda.getFechaVencimiento());
            pstmt.setString(5, deuda.getDescripcion());
            pstmt.setString(6, deuda.getEstado());

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

    public int actualizarDeuda(Deuda deuda) {
        int res = 0;
        String query = "UPDATE " + NOM_TABLA_DEU + " SET " + NOM_COL_MONTO_PEN_DEU + " = ?, " + NOM_COL_ESTADO_DEU + " = ? WHERE " + NOM_COL_ID_DEU + " = ?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = conBBDD.getConexion();
            pstmt = con.prepareStatement(query);
            pstmt.setDouble(1, deuda.getMontoPendiente());
            pstmt.setString(2, deuda.getEstado());
            pstmt.setInt(3, deuda.getIdDeuda());

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

    public List<Deuda> obtenerDeudasPorUsuario(int idUsuario) {
        List<Deuda> deudas = new ArrayList<>();
        String query = "SELECT * FROM " + NOM_TABLA_DEU + " WHERE " + NOM_COL_ID_USER + " = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = conBBDD.getConexion();
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, idUsuario);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Deuda deuda = new Deuda(
                    rs.getInt(NOM_COL_ID_DEU),
                    rs.getInt(NOM_COL_ID_USER),
                    rs.getDouble(NOM_COL_MONTO_TOT_DEU),
                    rs.getDouble(NOM_COL_MONTO_PEN_DEU),
                    rs.getString(NOM_COL_FECHA_VENC_TRAN),
                    rs.getString(NOM_COL_DESC_DEU),
                    rs.getString(NOM_COL_ESTADO_DEU)
                );
                deudas.add(deuda);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
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
        
        return deudas;
    }

}
