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
    static final String NOM_COL_REM = "remitente";
    static final String NOM_COL_DES = "destinatario";
    static final String NOM_COL_MONTO_TRAN = "monto";
    static final String NOM_COL_DESC_TRAN = "descripcion";

    private ConexionBBDD conBBDD;

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
            pstmt.setString(1, transferencia.getRemitente());
            pstmt.setString(2, transferencia.getDestinatario());
            pstmt.setDouble(3, transferencia.getMonto());
            pstmt.setString(4, transferencia.getDescripcion());

            // System.out.println("Ejecutando inserción: " + pstmt.toString());

            res = pstmt.executeUpdate();

            if (res > 0) {
                System.out.println("Transferencia registrada correctamente.");
            } else {
                System.out.println("No se pudo registrar la transferencia.");
            }

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

    public Object[][] obtenerTransferencias(int idUsuario) {
        List<Object[]> transferencias = new ArrayList<>();
        String nombreUsuario = obtenerNombreUsuario(idUsuario);

        String query = "SELECT remitente, destinatario, monto, descripcion FROM Transferencia " +
                       "WHERE remitente = ? OR destinatario = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = conBBDD.getConexion();
            if (con == null) {
                System.out.println("No se pudo establecer la conexión a la base de datos.");
                return new Object[0][0];
            }

            pstmt = con.prepareStatement(query);
            pstmt.setString(1, nombreUsuario);
            pstmt.setString(2, nombreUsuario);

            // System.out.println("Ejecutando consulta: " + pstmt.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                String remitente = rs.getString("remitente");
                String destinatario = rs.getString("destinatario");
                double monto = rs.getDouble("monto");
                String descripcion = rs.getString("descripcion");

                System.out.println("Transferencia encontrada: " + remitente + " -> " + destinatario + ": " + monto + " (" + descripcion + ")"); // Depuración

                transferencias.add(new Object[]{remitente, destinatario, monto, descripcion});
            }

            if (transferencias.isEmpty()) {
                System.out.println("No se encontraron transferencias para el usuario con nombre: " + nombreUsuario);
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
                System.out.println("Nombre de usuario encontrado: " + nombreUsuario);
            } else {
                System.out.println("No se encontró un usuario con ID: " + idUsuario);
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return nombreUsuario;
    }

}
