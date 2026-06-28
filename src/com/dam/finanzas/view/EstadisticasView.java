package com.dam.finanzas.view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import com.dam.finanzas.model.Deuda;
import com.dam.finanzas.model.ObjetivoFinanciero;
import com.dam.finanzas.model.bbdd.TablaDeuda;
import com.dam.finanzas.model.bbdd.TablaGastos;
import com.dam.finanzas.model.bbdd.TablaIngresos;
import com.dam.finanzas.model.bbdd.TablaObjetivoFinanciero;
import com.dam.finanzas.model.bbdd.TablaTransferencia;

import java.awt.*;
import java.util.List;

public class EstadisticasView extends JPanel {
    private MainView mainView;
    private int idUsuarioActual;
    private DefaultTableModel objetivosTableModel;
    private DefaultTableModel deudasTableModel;
    private DefaultTableModel transferenciasTableModel;
    private JTable objetivosTable;
    private JTable deudasTable;
    private JTable transferenciasTable;
    private JLabel ingresosValueLabel;
    private JLabel gastosValueLabel;
    private JLabel beneficioNetoValueLabel;

    public EstadisticasView(int idUsuarioActual, MainView mainView) {
        this.idUsuarioActual = idUsuarioActual;
        this.mainView = mainView;
        setLayout(new BorderLayout());
        setBackground(UIUtils.BG);
        initialize();
    }

    private void initialize() {
        JPanel headerPanel = new JPanel(new BorderLayout(0, 2));
        headerPanel.setBackground(UIUtils.BG_CARD);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, UIUtils.BORDER),
            BorderFactory.createEmptyBorder(10, 16, 10, 16)));
        JLabel titleLabel = new JLabel("Estadísticas Financieras");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(UIUtils.TEXT);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(titleLabel, BorderLayout.NORTH);
        JLabel subtitleLabel = new JLabel("Visión global acumulada de tus finanzas");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        subtitleLabel.setForeground(UIUtils.TEXT_MUTED);
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(subtitleLabel, BorderLayout.CENTER);

        JPanel estadisticasContainerPanel = new JPanel(new BorderLayout());
        estadisticasContainerPanel.setBackground(UIUtils.BG);

        JPanel finanzasPanel = createFinanzasPanel();
        estadisticasContainerPanel.add(finanzasPanel, BorderLayout.CENTER);

        JPanel northWrapper = new JPanel(new BorderLayout());
        northWrapper.setBackground(UIUtils.BG);
        northWrapper.add(headerPanel, BorderLayout.NORTH);
        northWrapper.add(estadisticasContainerPanel, BorderLayout.CENTER);
        add(northWrapper, BorderLayout.NORTH);

        JPanel tablesPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        tablesPanel.setBackground(UIUtils.BG);
        tablesPanel.setBorder(BorderFactory.createEmptyBorder(12, 16, 16, 16));

        JPanel transferenciasPanel = createTransferenciasPanel();
        tablesPanel.add(transferenciasPanel);

        JPanel deudasPanel = createDeudasPanel();
        tablesPanel.add(deudasPanel);

        JPanel objetivosPanel = createObjetivosPanel();
        tablesPanel.add(objetivosPanel);

        add(tablesPanel, BorderLayout.CENTER);
    }

    public void actualizarTablaObjetivos() {
        objetivosTableModel.setRowCount(0);
        TablaObjetivoFinanciero tablaObjetivoFinanciero = new TablaObjetivoFinanciero();
        List<ObjetivoFinanciero> objetivosList = tablaObjetivoFinanciero.obtenerObjetivosPorUsuario(idUsuarioActual);

        for (ObjetivoFinanciero objetivo : objetivosList) {
            Object[] rowData = {
                objetivo.getDescripcion(),
                String.format("%.2f €", objetivo.getCosto()),
                String.format("%.2f €", objetivo.getAhorroMensualSugerido()),
                objetivo.getTiempoNecesario(),
                objetivo.getEstado()
            };
            objetivosTableModel.addRow(rowData);
        }
        UIUtils.ajustarTabla(objetivosTable);
    }

    public void actualizarTotales() {
        TablaIngresos tablaIngresos = new TablaIngresos();
        double totalIngresos = tablaIngresos.obtenerTotalIngresos(idUsuarioActual);

        TablaGastos tablaGastos = new TablaGastos();
        double totalGastos = tablaGastos.obtenerTotalGastos(idUsuarioActual);

        double beneficioNeto = totalIngresos - totalGastos;

        ingresosValueLabel.setText(String.format("%.2f €", totalIngresos));
        gastosValueLabel.setText(String.format("%.2f €", totalGastos));
        beneficioNetoValueLabel.setText(String.format("%.2f €", beneficioNeto));
    }

    private JPanel createObjetivosPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(UIUtils.BG);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(UIUtils.BORDER), "Objetivos"));

        String[] objetivosColumnNames = {"Descripción", "Costo Total", "Ahorro Mensual Sugerido", "Tiempo Necesario", "Estado"};
        objetivosTableModel = new DefaultTableModel(objetivosColumnNames, 0);
        objetivosTable = new JTable(objetivosTableModel);
        DefaultTableCellRenderer centerRendObj = new DefaultTableCellRenderer();
        centerRendObj.setHorizontalAlignment(SwingConstants.CENTER);
        objetivosTable.setDefaultRenderer(Object.class, centerRendObj);
        objetivosTable.getTableHeader().setReorderingAllowed(false);
        objetivosTable.getTableHeader().setResizingAllowed(false);
        objetivosTable.setRowHeight(22);
        objetivosTable.setFillsViewportHeight(true);
        UIUtils.estilizarTabla(objetivosTable);

        JScrollPane objetivosScrollPane = new JScrollPane(objetivosTable);
        panel.add(objetivosScrollPane, BorderLayout.CENTER);

        actualizarTablaObjetivos();

        return panel;
    }

    private JPanel createFinanzasPanel() {
        JPanel finanzasPanel = new JPanel(new GridLayout(1, 3, 12, 0));
        finanzasPanel.setBackground(UIUtils.BG);
        finanzasPanel.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));

        // Ingresos card
        JPanel ingresosPanel = new JPanel(new BorderLayout());
        ingresosPanel.setBackground(UIUtils.BG_SUCCESS);
        ingresosPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIUtils.SUCCESS, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        JLabel ingresosLabel = new JLabel("Ingresos Totales");
        ingresosLabel.setFont(new Font("Arial", Font.BOLD, 12));
        ingresosLabel.setForeground(UIUtils.SUCCESS);
        ingresosLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ingresosValueLabel = new JLabel("0,00 €");
        ingresosValueLabel.setFont(new Font("Arial", Font.BOLD, 15));
        ingresosValueLabel.setForeground(UIUtils.SUCCESS);
        ingresosValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ingresosPanel.add(ingresosLabel, BorderLayout.NORTH);
        ingresosPanel.add(ingresosValueLabel, BorderLayout.CENTER);

        // Gastos card
        JPanel gastosPanel = new JPanel(new BorderLayout());
        gastosPanel.setBackground(UIUtils.BG_DANGER);
        gastosPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIUtils.DANGER, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        JLabel gastosLabel = new JLabel("Gastos Totales");
        gastosLabel.setFont(new Font("Arial", Font.BOLD, 12));
        gastosLabel.setForeground(UIUtils.DANGER);
        gastosLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gastosValueLabel = new JLabel("0,00 €");
        gastosValueLabel.setFont(new Font("Arial", Font.BOLD, 15));
        gastosValueLabel.setForeground(UIUtils.DANGER);
        gastosValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gastosPanel.add(gastosLabel, BorderLayout.NORTH);
        gastosPanel.add(gastosValueLabel, BorderLayout.CENTER);

        // Beneficio Neto card
        JPanel beneficioPanel = new JPanel(new BorderLayout());
        beneficioPanel.setBackground(UIUtils.BG_ACCENT);
        beneficioPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIUtils.ACCENT, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        JLabel beneficioLabel = new JLabel("Beneficio Neto");
        beneficioLabel.setFont(new Font("Arial", Font.BOLD, 12));
        beneficioLabel.setForeground(UIUtils.ACCENT);
        beneficioLabel.setHorizontalAlignment(SwingConstants.CENTER);
        beneficioNetoValueLabel = new JLabel("0,00 €");
        beneficioNetoValueLabel.setFont(new Font("Arial", Font.BOLD, 15));
        beneficioNetoValueLabel.setForeground(UIUtils.ACCENT);
        beneficioNetoValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        beneficioPanel.add(beneficioLabel, BorderLayout.NORTH);
        beneficioPanel.add(beneficioNetoValueLabel, BorderLayout.CENTER);

        finanzasPanel.add(ingresosPanel);
        finanzasPanel.add(gastosPanel);
        finanzasPanel.add(beneficioPanel);

        return finanzasPanel;
    }

    private JPanel createTransferenciasPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(UIUtils.BG);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(UIUtils.BORDER), "Transferencias"));

        String[] transferenciasColumnNames = {"Remitente", "Destinatario", "Monto"};
        transferenciasTableModel = new DefaultTableModel(transferenciasColumnNames, 0);
        transferenciasTable = new JTable(transferenciasTableModel);
        DefaultTableCellRenderer centerRendTr = new DefaultTableCellRenderer();
        centerRendTr.setHorizontalAlignment(SwingConstants.CENTER);
        transferenciasTable.setDefaultRenderer(Object.class, centerRendTr);
        transferenciasTable.getTableHeader().setReorderingAllowed(false);
        transferenciasTable.getTableHeader().setResizingAllowed(false);
        transferenciasTable.setRowHeight(22);
        transferenciasTable.setFillsViewportHeight(true);
        UIUtils.estilizarTabla(transferenciasTable);

        JScrollPane transferenciasScrollPane = new JScrollPane(transferenciasTable);
        panel.add(transferenciasScrollPane, BorderLayout.CENTER);

        actualizarTablaTransferencias();

        return panel;
    }

    private JPanel createDeudasPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(UIUtils.BG);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(UIUtils.BORDER), "Deudas"));

        String[] deudasColumnNames = {"Descripción", "Monto Total", "Monto Pendiente", "Fecha Vencimiento", "Estado"};
        deudasTableModel = new DefaultTableModel(deudasColumnNames, 0);
        deudasTable = new JTable(deudasTableModel);
        DefaultTableCellRenderer centerRendDeu = new DefaultTableCellRenderer();
        centerRendDeu.setHorizontalAlignment(SwingConstants.CENTER);
        deudasTable.setDefaultRenderer(Object.class, centerRendDeu);
        deudasTable.getTableHeader().setReorderingAllowed(false);
        deudasTable.getTableHeader().setResizingAllowed(false);
        deudasTable.setRowHeight(22);
        deudasTable.setFillsViewportHeight(true);
        UIUtils.estilizarTabla(deudasTable);
        JScrollPane deudasScrollPane = new JScrollPane(deudasTable);
        panel.add(deudasScrollPane, BorderLayout.CENTER);

        actualizarTablaDeudas();

        return panel;
    }

    public void actualizarTablaDeudas() {
        deudasTableModel.setRowCount(0);
        TablaDeuda tablaDeuda = new TablaDeuda();
        List<Deuda> deudasList = tablaDeuda.obtenerDeudasPorUsuario(idUsuarioActual);
        for (Deuda deuda : deudasList) {
            deudasTableModel.addRow(new Object[]{
                deuda.getDescripcion(),
                String.format("%.2f €", deuda.getMontoTotal()),
                String.format("%.2f €", deuda.getMontoPendiente()),
                deuda.getFechaVencimiento(),
                deuda.getEstado()
            });
        }
        UIUtils.ajustarTabla(deudasTable);
    }

    public void actualizarTablaTransferencias() {
        transferenciasTableModel.setRowCount(0);
        TablaTransferencia tablaTransferencia = new TablaTransferencia();
        for (Object[] fila : tablaTransferencia.obtenerTransferencias(idUsuarioActual)) {
            transferenciasTableModel.addRow(new Object[]{
                fila[0], fila[1], String.format("%.2f €", ((Number) fila[2]).doubleValue())
            });
        }
        UIUtils.ajustarTabla(transferenciasTable);
    }

    public JPanel createEstadisticasPanel() {
        return this;
    }
}
