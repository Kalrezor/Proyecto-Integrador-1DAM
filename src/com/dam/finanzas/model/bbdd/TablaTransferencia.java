package com.dam.finanzas.model.bbdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dam.finanzas.model.Transferencia;

public class TablaTransferencia {

    static final String NOM_TABLA_TRAN = "Transferencia";
    static final String NOM_COL_ID_TRAN = "id_transferencia";
    static final String NOM_COL_REM = "id_remitente";
    static final String NOM_COL_DES = "id_destinatario";
    static final String NOM_COL_MONTO_TRAN = "monto";
    static final String NOM_COL_DESC_TRAN = "descripcion";

    private final ConexionBBDD conBBDD;

    public TablaTransferencia() {
        conBBDD = new ConexionBBDD();
    }

    public int registrarTransferencia(Transferencia transferencia) {
        int res = 0;
        String query = "INSERT INTO " + NOM_TABLA_TRAN + "(" + NOM_COL_REM
                + ", " + NOM_COL_DES + ", " + NOM_COL_MONTO_TRAN + ", " + NOM_COL_DESC_TRAN + ") VALUES (?, ?, ?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = conBBDD.getConexion();
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, transferencia.getIdRemitente());
            pstmt.setInt(2, transferencia.getIdDestinatario());
            pstmt.setDouble(3, transferencia.getMonto());
            pstmt.setString(4, transferencia.getDescripcion());

            res = pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return res;
    }

    public Object[][] obtenerTransferencias(int idUsuario) {
        List<Object[]> transferencias = new ArrayList<>();

        String query = "SELECT u1.nombre AS remitente, u2.nombre AS destinatario, t.monto, t.descripcion " +
                       "FROM Transferencia t " +
                       "JOIN Usuario u1 ON t.id_remitente = u1.id_usuario " +
                       "JOIN Usuario u2 ON t.id_destinatario = u2.id_usuario " +
                       "WHERE t.id_remitente = ? OR t.id_destinatario = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = conBBDD.getConexion();
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, idUsuario);
            pstmt.setInt(2, idUsuario);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                transferencias.add(new Object[]{
                    rs.getString("remitente"),
                    rs.getString("destinatario"),
                    rs.getDouble("monto"),
                    rs.getString("descripcion")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        Object[][] data = new Object[transferencias.size()][4];
        for (int i = 0; i < transferencias.size(); i++) {
            data[i] = transferencias.get(i);
        }
        return data;
    }

    public String obtenerNombreUsuario(int idUsuario) {
        String nombreUsuario = "Usuario Desconocido";
        String query = "SELECT nombre FROM Usuario WHERE id_usuario = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = conBBDD.getConexion();
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, idUsuario);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                nombreUsuario = rs.getString("nombre");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return nombreUsuario;
    }

    public int obtenerIdPorNombre(String nombre) {
        int idUsuario = -1;
        String query = "SELECT id_usuario FROM Usuario WHERE nombre = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = conBBDD.getConexion();
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, nombre.toLowerCase());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                idUsuario = rs.getInt("id_usuario");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return idUsuario;
    }
}
