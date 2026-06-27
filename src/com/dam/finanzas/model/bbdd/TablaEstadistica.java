package com.dam.finanzas.model.bbdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dam.finanzas.model.Estadistica;
import com.dam.finanzas.model.ObjetivoFinanciero;

public class TablaEstadistica {

    static final String NOM_TABLA_EST = "Estadistica";
    static final String NOM_COL_ID_EST = "id_estadistica";
    static final String NOM_COL_ID_USER = "id_usuario";
    static final String NOM_COL_ING_TOT = "ingresos_totales";
    static final String NOM_COL_GAS_TOT = "gastos_totales";
    static final String NOM_COL_NUM_TRAN = "numero_transacciones";
    static final String NOM_COL_NUM_DEU = "numero_deudas";
    static final String NOM_COL_NUM_OBJ = "numero_objetivos";
    static final String NOM_COL_FECHA = "fecha";

    private ConexionBBDD conBBDD;

    public TablaEstadistica() {
        conBBDD = new ConexionBBDD();
    }

    public int registrarEstadistica(Estadistica estadistica) {
        int res = 0;
        String query = "INSERT INTO " + NOM_TABLA_EST + "(" + NOM_COL_ID_USER
                + ", " + NOM_COL_ING_TOT + ", " + NOM_COL_GAS_TOT
                + ", " + NOM_COL_NUM_TRAN + ", " + NOM_COL_NUM_DEU + ", " + NOM_COL_NUM_OBJ
                + ", " + NOM_COL_FECHA + ") VALUES (?, ?, ?, ?, ?, ?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = conBBDD.getConexion();
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, estadistica.getIdUsuario());
            pstmt.setDouble(2, estadistica.getIngresosTotales());
            pstmt.setDouble(3, estadistica.getGastosTotales());
            pstmt.setInt(4, estadistica.getNumeroTransacciones());
            pstmt.setInt(5, estadistica.getNumeroDeudas());
            pstmt.setInt(6, estadistica.getNumeroObjetivos());
            pstmt.setString(7, estadistica.getFecha());

            res = pstmt.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
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

        return res;
    }

    public Estadistica obtenerEstadisticasPorUsuario(int idUsuario) {
        Estadistica estadistica = null;
        String query = "SELECT * FROM " + NOM_TABLA_EST + " WHERE " + NOM_COL_ID_USER + " = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = conBBDD.getConexion();
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, idUsuario);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                estadistica = new Estadistica(
                    rs.getInt(NOM_COL_ID_EST),
                    rs.getInt(NOM_COL_ID_USER),
                    rs.getDouble(NOM_COL_ING_TOT),
                    rs.getDouble(NOM_COL_GAS_TOT),
                    rs.getInt(NOM_COL_NUM_TRAN),
                    rs.getInt(NOM_COL_NUM_DEU),
                    rs.getInt(NOM_COL_NUM_OBJ),
                    rs.getString(NOM_COL_FECHA)
                );
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

        return estadistica;
    }

    public double obtenerTotalIngresos(int idUsuario) {
        TablaIngresos tablaIngresos = new TablaIngresos();
        return tablaIngresos.obtenerTotalIngresos(idUsuario);
    }

    public double obtenerTotalGastos(int idUsuario) {
        TablaGastos tablaGastos = new TablaGastos();
        return tablaGastos.obtenerTotalGastos(idUsuario);
    }

    public int obtenerNumeroTransacciones(int idUsuario) {
        int count = 0;
        String query = "SELECT COUNT(*) AS count FROM Transferencia WHERE id_origen = ? OR id_destino = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = conBBDD.getConexion();
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, idUsuario);
            pstmt.setInt(2, idUsuario);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt("count");
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

        return count;
    }

    public int obtenerNumeroDeudas(int idUsuario) {
        TablaDeuda tablaDeuda = new TablaDeuda();
        return tablaDeuda.obtenerDeudasPorUsuario(idUsuario).size();
    }

    public int obtenerNumeroObjetivos(int idUsuario) {
        TablaObjetivoFinanciero tablaObjetivoFinanciero = new TablaObjetivoFinanciero();
        return tablaObjetivoFinanciero.obtenerObjetivosPorUsuario(idUsuario).size();
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
                    rs.getString("tiempo_estimado"),
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
