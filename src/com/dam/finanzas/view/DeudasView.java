package com.dam.finanzas.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.dam.finanzas.model.Deuda;
import com.dam.finanzas.model.bbdd.TablaDeuda;

public class DeudasView extends JPanel {
    private JTextField txtDescripcion;
    private JTextField txtMontoTotal;
    private JTextField txtMontoPendiente;
    private JTextField txtFechaVencimiento;
    private JButton btnRegistrarDeuda;
    private DefaultTableModel tableModel;
    private JTable table;
    private List<Deuda> deudasList;
    private int idUsuarioActual;

    public DeudasView(int idUsuarioActual) {
        this.idUsuarioActual = idUsuarioActual;
        deudasList = new ArrayList<>();
        initializeTableModel();
        cargarDeudasUsuario();
    }

    private void initializeTableModel() {
        String[] columnNames = {"Descripción", "Monto Total", "Monto Pendiente", "Fecha Vencimiento", "Estado"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
    }

    private void cargarDeudasUsuario() {
        TablaDeuda tablaDeuda = new TablaDeuda();
        deudasList = tablaDeuda.obtenerDeudasPorUsuario(idUsuarioActual);
        actualizarTablaDeudas();
    }

    public JPanel createDeudasPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.LIGHT_GRAY);

        JLabel titleLabel = new JLabel("Gestión de Deudas");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        inputPanel.setBackground(Color.LIGHT_GRAY);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        txtDescripcion = new JTextField("Descripción", 20);
        txtDescripcion.setForeground(Color.GRAY);
        txtDescripcion.setFont(new Font("Tahoma", Font.PLAIN, 14));
        inputPanel.add(new JLabel("Descripción:"));
        inputPanel.add(txtDescripcion);

        txtDescripcion.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtDescripcion.getText().equals("Descripción")) {
                    txtDescripcion.setText("");
                    txtDescripcion.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtDescripcion.getText().isEmpty()) {
                    txtDescripcion.setText("Descripción");
                    txtDescripcion.setForeground(Color.GRAY);
                }
            }
        });

        txtMontoTotal = new JTextField("Monto Total", 20);
        txtMontoTotal.setForeground(Color.GRAY);
        txtMontoTotal.setFont(new Font("Tahoma", Font.PLAIN, 14));
        inputPanel.add(new JLabel("Monto Total (€):"));
        inputPanel.add(txtMontoTotal);

        txtMontoTotal.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtMontoTotal.getText().equals("Monto Total")) {
                    txtMontoTotal.setText("");
                    txtMontoTotal.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtMontoTotal.getText().isEmpty()) {
                    txtMontoTotal.setText("Monto Total");
                    txtMontoTotal.setForeground(Color.GRAY);
                }
            }
        });

        txtMontoPendiente = new JTextField("Monto Pendiente", 20);
        txtMontoPendiente.setForeground(Color.GRAY);
        txtMontoPendiente.setFont(new Font("Tahoma", Font.PLAIN, 14));
        inputPanel.add(new JLabel("Monto Pendiente (€):"));
        inputPanel.add(txtMontoPendiente);

        txtMontoPendiente.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtMontoPendiente.getText().equals("Monto Pendiente")) {
                    txtMontoPendiente.setText("");
                    txtMontoPendiente.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtMontoPendiente.getText().isEmpty()) {
                    txtMontoPendiente.setText("Monto Pendiente");
                    txtMontoPendiente.setForeground(Color.GRAY);
                }
            }
        });

        txtFechaVencimiento = new JTextField("Fecha Vencimiento (dd-MM-yyyy)", 20);
        txtFechaVencimiento.setForeground(Color.GRAY);
        txtFechaVencimiento.setFont(new Font("Tahoma", Font.PLAIN, 14));
        inputPanel.add(new JLabel("Fecha Vencimiento:"));
        inputPanel.add(txtFechaVencimiento);

        txtFechaVencimiento.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtFechaVencimiento.getText().equals("Fecha Vencimiento (dd-MM-yyyy)")) {
                    txtFechaVencimiento.setText("");
                    txtFechaVencimiento.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtFechaVencimiento.getText().isEmpty()) {
                    txtFechaVencimiento.setText("Fecha Vencimiento (dd-MM-yyyy)");
                    txtFechaVencimiento.setForeground(Color.GRAY);
                }
            }
        });

        btnRegistrarDeuda = new JButton("Registrar Deuda");
        btnRegistrarDeuda.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnRegistrarDeuda.setBackground(new Color(44, 62, 80));
        btnRegistrarDeuda.setForeground(Color.WHITE);
        inputPanel.add(btnRegistrarDeuda);

        btnRegistrarDeuda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarDeuda();
            }
        });

        JButton editButton = new JButton("Editar Deuda");
        editButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
        editButton.setBackground(new Color(44, 62, 80));
        editButton.setForeground(Color.WHITE);
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarDeuda(table);
            }
        });

        inputPanel.add(editButton);

        panel.add(inputPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        table.getTableHeader().setReorderingAllowed(false);

        return panel;
    }

    private void registrarDeuda() {
        String descripcion = txtDescripcion.getText();
        String montoTotalStr = txtMontoTotal.getText();
        String montoPendienteStr = txtMontoPendiente.getText();
        String fechaVencimiento = txtFechaVencimiento.getText();

        // Validar que todos los campos estén completos
        if (descripcion.isEmpty() || descripcion.equals("Descripción") ||
            montoTotalStr.isEmpty() || montoTotalStr.equals("Monto Total") ||
            montoPendienteStr.isEmpty() || montoPendienteStr.equals("Monto Pendiente") ||
            fechaVencimiento.isEmpty() || fechaVencimiento.equals("Fecha Vencimiento (dd-MM-yyyy)")) {

            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar que los campos de monto sean numéricos y no negativos
        try {
            double montoTotal = Double.parseDouble(montoTotalStr);
            double montoPendiente = Double.parseDouble(montoPendienteStr);

            if (montoTotal < 0 || montoPendiente < 0) {
                JOptionPane.showMessageDialog(null, "Los montos no pueden ser negativos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese valores numéricos válidos para los montos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar el formato de la fecha
        String[] dateValidation = isValidDateFormat(fechaVencimiento);
        if (dateValidation[0].equals("0")) {
            JOptionPane.showMessageDialog(null, "La fecha de vencimiento debe estar en formato dd-MM-yyyy o dd/MM/yyyy.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (dateValidation[0].equals("-1")) {
            JOptionPane.showMessageDialog(null, "La fecha de vencimiento no puede ser anterior a la fecha actual (" + dateValidation[1] + ").", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Si todas las validaciones pasan, proceder con el registro de la deuda
        try {
            double montoTotal = Double.parseDouble(montoTotalStr);
            double montoPendiente = Double.parseDouble(montoPendienteStr);

            Deuda nuevaDeuda = new Deuda(idUsuarioActual, montoTotal, montoPendiente, fechaVencimiento, descripcion, "EN PROGRESO");
            TablaDeuda tablaDeuda = new TablaDeuda();
            int resultado = tablaDeuda.registrarDeuda(nuevaDeuda);

            if (resultado > 0) {
                cargarDeudasUsuario();
                JOptionPane.showMessageDialog(null, "Deuda registrada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(null, "Error al registrar la deuda", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error inesperado al procesar los montos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String[] isValidDateFormat(String dateStr) {
        String[] formats = { "dd-MM-yyyy", "dd/MM/yyyy" };
        SimpleDateFormat currentDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String currentDateStr = currentDateFormat.format(new Date());

        for (String format : formats) {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
            sdf.setLenient(false);

            try {
                java.util.Date inputDate = sdf.parse(dateStr);
                java.util.Date currentDate = new java.util.Date();

                if (inputDate.before(currentDate)) {
                    return new String[]{"-1", currentDateStr}; // La fecha es anterior a la actual
                }

                return new String[]{"1", currentDateStr}; // La fecha es válida y no ha pasado
            } catch (ParseException e) {
                // Ignorar y probar con el siguiente formato
            }
        }

        return new String[]{"0", currentDateStr}; // La fecha no es válida
    }

    private void limpiarCampos() {
        txtDescripcion.setText("Descripción");
        txtDescripcion.setForeground(Color.GRAY);
        txtMontoTotal.setText("Monto Total");
        txtMontoTotal.setForeground(Color.GRAY);
        txtMontoPendiente.setText("Monto Pendiente");
        txtMontoPendiente.setForeground(Color.GRAY);
        txtFechaVencimiento.setText("Fecha Vencimiento (dd-MM-yyyy)");
        txtFechaVencimiento.setForeground(Color.GRAY);
    }

    private void editarDeuda(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Selecciona una deuda para editar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Deuda deudaSeleccionada = deudasList.get(selectedRow);

        String nuevoMontoPendienteStr = JOptionPane.showInputDialog(
            null,
            "Ingrese el nuevo monto pendiente:",
            "Editar Monto Pendiente",
            JOptionPane.PLAIN_MESSAGE
        );

        if (nuevoMontoPendienteStr != null) {
            try {
                double nuevoMontoPendiente = Double.parseDouble(nuevoMontoPendienteStr);
                deudaSeleccionada.setMontoPendiente(nuevoMontoPendiente);
                deudaSeleccionada.setEstado(nuevoMontoPendiente > 0 ? "EN PROGRESO" : "FINALIZADO");

                TablaDeuda tablaDeuda = new TablaDeuda();
                int resultado = tablaDeuda.actualizarDeuda(deudaSeleccionada);

                if (resultado > 0) {
                    cargarDeudasUsuario();
                    JOptionPane.showMessageDialog(null, "Deuda actualizada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Error al actualizar la deuda", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor, ingresa un valor válido para el monto pendiente.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void actualizarTablaDeudas() {
        tableModel.setRowCount(0);
        for (Deuda deuda : deudasList) {
            Object[] rowData = {
                deuda.getDescripcion(),
                String.format("%.2f €", deuda.getMontoTotal()),
                String.format("%.2f €", deuda.getMontoPendiente()),
                deuda.getFechaVencimiento(),
                deuda.getEstado()
            };
            tableModel.addRow(rowData);
        }
    }
}
