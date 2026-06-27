package com.dam.finanzas.model.bbdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dam.finanzas.model.Usuario;

public class TablaUsuario {

    static final String NOM_TABLA_USER = "Usuario";
    static final String NOM_COL_ID_USER = "id_usuario";
    static final String NOM_COL_NOM = "nombre";
    static final String NOM_COL_EMAIL = "correo";
    static final String NOM_COL_PASW = "contraseña";

    private ConexionBBDD conBBDD;

    public TablaUsuario() {
        conBBDD = new ConexionBBDD();
    }

    public int registrarUsuario(Usuario usuarioReg) {
        int res = 0;
        String query = "INSERT INTO " + NOM_TABLA_USER + "(" + NOM_COL_NOM
                + ", " + NOM_COL_EMAIL + ", " + NOM_COL_PASW + ") VALUES (?, ?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = conBBDD.getConexion();
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, usuarioReg.getNombre().toLowerCase());
            pstmt.setString(2, usuarioReg.getCorreo());
            pstmt.setString(3, usuarioReg.getContrasena());

            res = pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage().contains("UNIQUE")) {
                res = -1;
            }

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
    
    public boolean autenticarUsuario(String correo, String contrasena) {
        String query = "SELECT * FROM Usuario WHERE correo = ? AND contraseña = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = conBBDD.getConexion();
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, correo);
            pstmt.setString(2, contrasena);
            rs = pstmt.executeQuery();

            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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
    }

    public Usuario obtenerUsuarioPorCorreo(String correo) {
        Usuario usuario = null;
        String query = "SELECT " + NOM_COL_ID_USER + ", " + NOM_COL_NOM + ", " + NOM_COL_PASW + " FROM " + NOM_TABLA_USER
                + " WHERE " + NOM_COL_EMAIL + " = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rslt = null;

        try {
            con = conBBDD.getConexion();
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, correo);
            rslt = pstmt.executeQuery();

            if (rslt.next()) {
                usuario = new Usuario(rslt.getInt(NOM_COL_ID_USER), rslt.getString(NOM_COL_NOM), correo, rslt.getString(NOM_COL_PASW));
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                if (rslt != null) {
                    rslt.close();
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

        return usuario;
    }
}
