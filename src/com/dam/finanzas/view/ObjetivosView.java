package com.dam.finanzas.view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import com.dam.finanzas.model.ObjetivoFinanciero;
import com.dam.finanzas.model.bbdd.TablaObjetivoFinanciero;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

public class ObjetivosView extends JPanel {
    private EstadisticasView estadisticasView;
    private List<ObjetivoFinanciero> objetivosList;
    private DefaultTableModel tableModel;
    private JTable table;
    private final int idUsuarioActual;
    private final TablaObjetivoFinanciero tablaObjetivoFinanciero;

    public void setEstadisticasView(EstadisticasView estadisticasView) {
        this.estadisticasView = estadisticasView;
    }

    public ObjetivosView(int idUsuarioActual) {
        this.idUsuarioActual = idUsuarioActual;
        this.tablaObjetivoFinanciero = new TablaObjetivoFinanciero();
        objetivosList = new ArrayList<>();
        initializeTableModel();
        cargarObjetivos();
    }

    private void initializeTableModel() {
        String[] columnNames = {"Descripción", "Costo Total", "Ahorro Mensual Sugerido", "Tiempo Necesario", "Estado"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.setRowHeight(22);
        table.setFillsViewportHeight(true);
        UIUtils.estilizarTabla(table);
    }

    private void cargarObjetivos() {
        objetivosList = tablaObjetivoFinanciero.obtenerObjetivosPorUsuario(idUsuarioActual);
        updateTable();
    }

    public JPanel createObjetivosPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(UIUtils.BG);

        // Cabecera estilo home
        JPanel headerPanel = new JPanel(new BorderLayout(0, 2));
        headerPanel.setBackground(UIUtils.BG_CARD);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, UIUtils.BORDER),
            BorderFactory.createEmptyBorder(10, 16, 10, 16)));
        JLabel titleLabel = new JLabel("Gestión de Objetivos");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(UIUtils.TEXT);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(titleLabel, BorderLayout.NORTH);
        JLabel subtitleLabel = new JLabel("Planifica y realiza seguimiento de tus metas financieras");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        subtitleLabel.setForeground(UIUtils.TEXT_MUTED);
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(subtitleLabel, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.setBackground(UIUtils.BG);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(12, 16, 8, 16));

        JTextField descripcionField = new JTextField("Descripción", 20);
        descripcionField.setForeground(Color.GRAY);
        descripcionField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        inputPanel.add(new JLabel("Descripción:"));
        inputPanel.add(descripcionField);

        descripcionField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (descripcionField.getText().equals("Descripción")) {
                    descripcionField.setText("");
                    descripcionField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (descripcionField.getText().isEmpty()) {
                    descripcionField.setText("Descripción");
                    descripcionField.setForeground(Color.GRAY);
                }
            }
        });

        JTextField costoField = new JTextField("Costo Total (€)", 20);
        costoField.setForeground(Color.GRAY);
        costoField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        inputPanel.add(new JLabel("Costo Total (€):"));
        inputPanel.add(costoField);

        costoField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (costoField.getText().equals("Costo Total (€)")) {
                    costoField.setText("");
                    costoField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (costoField.getText().isEmpty()) {
                    costoField.setText("Costo Total (€)");
                    costoField.setForeground(Color.GRAY);
                }
            }
        });

        JTextField ingresosMensualesField = new JTextField("Ingresos Mensuales (€)", 20);
        ingresosMensualesField.setForeground(Color.GRAY);
        ingresosMensualesField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        inputPanel.add(new JLabel("Ingresos Mensuales (€):"));
        inputPanel.add(ingresosMensualesField);

        ingresosMensualesField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (ingresosMensualesField.getText().equals("Ingresos Mensuales (€)")) {
                    ingresosMensualesField.setText("");
                    ingresosMensualesField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (ingresosMensualesField.getText().isEmpty()) {
                    ingresosMensualesField.setText("Ingresos Mensuales (€)");
                    ingresosMensualesField.setForeground(Color.GRAY);
                }
            }
        });

        JTextField gastosMensualesField = new JTextField("Gastos Mensuales (€)", 20);
        gastosMensualesField.setForeground(Color.GRAY);
        gastosMensualesField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        inputPanel.add(new JLabel("Gastos Mensuales (€):"));
        inputPanel.add(gastosMensualesField);

        gastosMensualesField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (gastosMensualesField.getText().equals("Gastos Mensuales (€)")) {
                    gastosMensualesField.setText("");
                    gastosMensualesField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (gastosMensualesField.getText().isEmpty()) {
                    gastosMensualesField.setText("Gastos Mensuales (€)");
                    gastosMensualesField.setForeground(Color.GRAY);
                }
            }
        });

        JTextField ahorroMensualDeseadoField = new JTextField("Ahorro Mensual Deseado (€)", 20);
        ahorroMensualDeseadoField.setForeground(Color.GRAY);
        ahorroMensualDeseadoField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        inputPanel.add(new JLabel("Ahorro Mensual Deseado (€):"));
        inputPanel.add(ahorroMensualDeseadoField);

        ahorroMensualDeseadoField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (ahorroMensualDeseadoField.getText().equals("Ahorro Mensual Deseado (€)")) {
                    ahorroMensualDeseadoField.setText("");
                    ahorroMensualDeseadoField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (ahorroMensualDeseadoField.getText().isEmpty()) {
                    ahorroMensualDeseadoField.setText("Ahorro Mensual Deseado (€)");
                    ahorroMensualDeseadoField.setForeground(Color.GRAY);
                }
            }
        });

        JButton addButton = UIUtils.crearBoton("Agregar Objetivo", UIUtils.ACCENT, Color.WHITE);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String descripcion = descripcionField.getText();
                    String costoText = costoField.getText();
                    String ingresosMensualesText = ingresosMensualesField.getText();
                    String gastosMensualesText = gastosMensualesField.getText();
                    String ahorroMensualDeseadoText = ahorroMensualDeseadoField.getText();

                    if (descripcion.isEmpty() || descripcion.equals("Descripción") ||
                        costoText.isEmpty() || costoText.equals("Costo Total (€)") ||
                        ingresosMensualesText.isEmpty() || ingresosMensualesText.equals("Ingresos Mensuales (€)") ||
                        gastosMensualesText.isEmpty() || gastosMensualesText.equals("Gastos Mensuales (€)") ||
                        ahorroMensualDeseadoText.isEmpty() || ahorroMensualDeseadoText.equals("Ahorro Mensual Deseado (€)")) {

                        JOptionPane.showMessageDialog(panel, "Por favor complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    double costo = Double.parseDouble(costoText);
                    double ingresosMensuales = Double.parseDouble(ingresosMensualesText);
                    double gastosMensuales = Double.parseDouble(gastosMensualesText);
                    double ahorroMensualDeseado = Double.parseDouble(ahorroMensualDeseadoText);

                    if (ingresosMensuales < 0 || gastosMensuales < 0 || ahorroMensualDeseado < 0 || costo < 0) {
                        JOptionPane.showMessageDialog(panel, "Los montos no pueden ser negativos.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    double ahorroMensualDisponible = ingresosMensuales - gastosMensuales;

                    if (ahorroMensualDeseado > ahorroMensualDisponible) {
                        JOptionPane.showMessageDialog(panel, "El ahorro mensual deseado excede el margen disponible.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    int mesesNecesarios = (int) Math.ceil(costo / ahorroMensualDeseado);
                    int anos = mesesNecesarios / 12;
                    int meses = mesesNecesarios % 12;

                    String tiempoNecesario = anos > 0 ? String.format("%d años y %d meses", anos, meses) : String.format("%d meses", meses);

                    ObjetivoFinanciero nuevoObjetivo = new ObjetivoFinanciero(
                        0,
                        idUsuarioActual,
                        descripcion,
                        costo,
                        ahorroMensualDeseado,
                        tiempoNecesario,
                        "En progreso"
                    );

                    tablaObjetivoFinanciero.registrarObjetivoFinanciero(nuevoObjetivo);
                    cargarObjetivos();

                    if (estadisticasView != null) {
                        estadisticasView.actualizarTablaObjetivos();
                    }

                    JOptionPane.showMessageDialog(
                        panel,
                        String.format("Con tus finanzas actuales, tardarás aproximadamente %s en cumplir este objetivo.", tiempoNecesario),
                        "Tiempo Estimado",
                        JOptionPane.INFORMATION_MESSAGE
                    );

                    descripcionField.setText("Descripción");
                    descripcionField.setForeground(Color.GRAY);
                    costoField.setText("Costo Total (€)");
                    costoField.setForeground(Color.GRAY);
                    ingresosMensualesField.setText("Ingresos Mensuales (€)");
                    ingresosMensualesField.setForeground(Color.GRAY);
                    gastosMensualesField.setText("Gastos Mensuales (€)");
                    gastosMensualesField.setForeground(Color.GRAY);
                    ahorroMensualDeseadoField.setText("Ahorro Mensual Deseado (€)");
                    ahorroMensualDeseadoField.setForeground(Color.GRAY);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(panel, "Por favor, introduzca valores numéricos válidos para los montos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton completeButton = UIUtils.crearBoton("Marcar como Cumplido", UIUtils.SUCCESS, Color.WHITE);
        completeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(panel, "Selecciona un objetivo para marcar como cumplido.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ObjetivoFinanciero objetivo = objetivosList.get(selectedRow);
                objetivo.setEstado("Cumplido");
                tablaObjetivoFinanciero.actualizarObjetivoFinanciero(objetivo);
                cargarObjetivos();

                if (estadisticasView != null) {
                    estadisticasView.actualizarTablaObjetivos();
                }
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 8));
        buttonPanel.setBackground(UIUtils.BG);
        buttonPanel.add(addButton);
        buttonPanel.add(completeButton);

        JPanel formSection = new JPanel(new BorderLayout());
        formSection.setBackground(UIUtils.BG);
        formSection.add(inputPanel, BorderLayout.CENTER);
        formSection.add(buttonPanel, BorderLayout.SOUTH);

        JPanel topSection = new JPanel(new BorderLayout());
        topSection.setBackground(UIUtils.BG);
        topSection.add(headerPanel, BorderLayout.NORTH);
        topSection.add(formSection, BorderLayout.CENTER);
        panel.add(topSection, BorderLayout.NORTH);

        JPanel tableWrapper = new JPanel(new BorderLayout());
        tableWrapper.setBackground(UIUtils.BG);
        tableWrapper.setBorder(BorderFactory.createEmptyBorder(6, 14, 14, 14));
        tableWrapper.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(tableWrapper, BorderLayout.CENTER);

        return panel;
    }

    private void updateTable() {
        tableModel.setRowCount(0);
        for (ObjetivoFinanciero objetivo : objetivosList) {
            Object[] rowData = {
                objetivo.getDescripcion(),
                String.format("%.2f €", objetivo.getCosto()),
                String.format("%.2f €", objetivo.getAhorroMensualSugerido()),
                objetivo.getTiempoNecesario(),
                objetivo.getEstado()
            };
            tableModel.addRow(rowData);
        }
        UIUtils.ajustarTabla(table);
    }
}