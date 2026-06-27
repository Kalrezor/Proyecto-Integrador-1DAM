package com.dam.finanzas.view;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import com.dam.finanzas.model.bbdd.TablaIngresos;
import com.dam.finanzas.model.bbdd.TablaGastos;
import com.dam.finanzas.model.bbdd.TablaTransferencia;
import com.dam.finanzas.model.Ingreso;
import com.dam.finanzas.model.Gasto;
import com.dam.finanzas.model.Transferencia;

public class TransaccionesView extends JPanel {
    private int idUsuarioActual;
    private MainView mainView;

    public TransaccionesView(int idUsuarioActual, MainView mainView) {
        this.idUsuarioActual = idUsuarioActual;
        this.mainView = mainView;
        initialize();
    }

    private void initialize() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(200, 200, 200));

        JLabel titleLabel = new JLabel("GESTIÓN DE TRANSACCIONES", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        JTabbedPane transaccionesTabs = new JTabbedPane();
        transaccionesTabs.addTab("Ingresos", createIngresosPanel());
        transaccionesTabs.addTab("Gastos", createGastosPanel());
        transaccionesTabs.addTab("Transferencias", createTransferenciasPanel());
        add(transaccionesTabs, BorderLayout.CENTER);
    }

    private JPanel createIngresosPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(240, 240, 240));

        GridBagConstraints gbcLabel = new GridBagConstraints();
        gbcLabel.insets = new Insets(15, 15, 15, 15);
        gbcLabel.anchor = GridBagConstraints.WEST;

        GridBagConstraints gbcField = new GridBagConstraints();
        gbcField.insets = new Insets(15, 15, 15, 15);
        gbcField.fill = GridBagConstraints.HORIZONTAL;
        gbcField.weightx = 1.0;

        gbcLabel.gridx = 0;
        gbcLabel.gridy = 0;
        panel.add(new JLabel("Cantidad:"), gbcLabel);

        JTextField cantidadField = new JTextField(20);
        gbcField.gridx = 1;
        gbcField.gridy = 0;
        panel.add(cantidadField, gbcField);

        gbcLabel.gridx = 0;
        gbcLabel.gridy = 1;
        panel.add(new JLabel("Descripción:"), gbcLabel);

        JTextField descripcionField = new JTextField(20);
        gbcField.gridx = 1;
        gbcField.gridy = 1;
        panel.add(descripcionField, gbcField);

        GridBagConstraints gbcButton = new GridBagConstraints();
        gbcButton.gridx = 0;
        gbcButton.gridy = 2;
        gbcButton.gridwidth = 2;
        gbcButton.anchor = GridBagConstraints.CENTER;
        gbcButton.insets = new Insets(25, 15, 15, 15);
        JButton registrarButton = new JButton("Registrar");
        registrarButton.addActionListener(e -> {
            try {
                double cantidad = Double.parseDouble(cantidadField.getText());
                String descripcion = descripcionField.getText();

                if (cantidad > 0) {
                    Ingreso ingreso = new Ingreso(idUsuarioActual, descripcion, cantidad, "CURRENT_DATE");
                    TablaIngresos tablaIngresos = new TablaIngresos();
                    int resultado = tablaIngresos.registrarIngreso(ingreso);

                    if (resultado > 0) {
                        JOptionPane.showMessageDialog(null, "Ingreso registrado: " + cantidad + "€", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        cantidadField.setText("");
                        descripcionField.setText("");
                        mainView.actualizarTotales();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al registrar el ingreso", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor que 0", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese una cantidad válida", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(registrarButton, gbcButton);

        return panel;
    }

    private JPanel createGastosPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(240, 240, 240));

        GridBagConstraints gbcLabel = new GridBagConstraints();
        gbcLabel.insets = new Insets(15, 15, 15, 15);
        gbcLabel.anchor = GridBagConstraints.WEST;

        GridBagConstraints gbcField = new GridBagConstraints();
        gbcField.insets = new Insets(15, 15, 15, 15);
        gbcField.fill = GridBagConstraints.HORIZONTAL;
        gbcField.weightx = 1.0;

        gbcLabel.gridx = 0;
        gbcLabel.gridy = 0;
        panel.add(new JLabel("Cantidad:"), gbcLabel);

        JTextField cantidadField = new JTextField(20);
        gbcField.gridx = 1;
        gbcField.gridy = 0;
        panel.add(cantidadField, gbcField);

        gbcLabel.gridx = 0;
        gbcLabel.gridy = 1;
        panel.add(new JLabel("Descripción:"), gbcLabel);

        JTextField descripcionField = new JTextField(20);
        gbcField.gridx = 1;
        gbcField.gridy = 1;
        panel.add(descripcionField, gbcField);

        gbcLabel.gridx = 0;
        gbcLabel.gridy = 2;
        panel.add(new JLabel("Categoría:"), gbcLabel);

        String[] categorias = {
            "Ocio y Entretenimiento",
            "Ropa y Accesorios",
            "Tecnología y Gadgets",
            "Salud y Cuidado Personal",
            "Transporte y Movilidad",
            "Comida y Supermercado",
            "Hogar y Decoración",
            "Educación y Formación"
        };
        JComboBox<String> categoriaComboBox = new JComboBox<>(categorias);
        gbcField.gridx = 1;
        gbcField.gridy = 2;
        panel.add(categoriaComboBox, gbcField);

        GridBagConstraints gbcButton = new GridBagConstraints();
        gbcButton.gridx = 0;
        gbcButton.gridy = 3;
        gbcButton.gridwidth = 2;
        gbcButton.anchor = GridBagConstraints.CENTER;
        gbcButton.insets = new Insets(25, 15, 15, 15);
        JButton registrarButton = new JButton("Registrar");
        registrarButton.addActionListener(e -> {
            try {
                double cantidad = Double.parseDouble(cantidadField.getText());
                String descripcion = descripcionField.getText();
                String categoria = (String) categoriaComboBox.getSelectedItem();

                if (cantidad > 0) {
                    if (categoria == null || categoria.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Seleccione una categoría válida", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    Gasto gasto = new Gasto(idUsuarioActual, descripcion, categoria, cantidad, "CURRENT_DATE");
                    TablaGastos tablaGastos = new TablaGastos();
                    int resultado = tablaGastos.registrarGasto(gasto);

                    if (resultado > 0) {
                        JOptionPane.showMessageDialog(null, "Gasto registrado: " + cantidad + "€ en " + categoria, "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        cantidadField.setText("");
                        descripcionField.setText("");
                        mainView.actualizarTotales();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al registrar el gasto", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor que 0", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese una cantidad válida", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(registrarButton, gbcButton);

        return panel;
    }

    private JPanel createTransferenciasPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(240, 240, 240));

        GridBagConstraints gbcLabel = new GridBagConstraints();
        gbcLabel.insets = new Insets(15, 15, 15, 15);
        gbcLabel.anchor = GridBagConstraints.WEST;

        GridBagConstraints gbcField = new GridBagConstraints();
        gbcField.insets = new Insets(15, 15, 15, 15);
        gbcField.fill = GridBagConstraints.HORIZONTAL;
        gbcField.weightx = 1.0;

        gbcLabel.gridx = 0;
        gbcLabel.gridy = 0;
        panel.add(new JLabel("Tipo de Transferencia:"), gbcLabel);

        String[] opciones = {"Enviar Dinero", "Recibir Dinero"};
        JComboBox<String> tipoTransferenciaComboBox = new JComboBox<>(opciones);
        gbcField.gridx = 1;
        gbcField.gridy = 0;
        panel.add(tipoTransferenciaComboBox, gbcField);

        gbcLabel.gridx = 0;
        gbcLabel.gridy = 1;
        panel.add(new JLabel("Destinatario/Remitente:"), gbcLabel);

        JTextField destinatarioField = new JTextField(20);
        gbcField.gridx = 1;
        gbcField.gridy = 1;
        panel.add(destinatarioField, gbcField);

        gbcLabel.gridx = 0;
        gbcLabel.gridy = 2;
        panel.add(new JLabel("Asunto:"), gbcLabel);

        JTextField asuntoField = new JTextField(20);
        gbcField.gridx = 1;
        gbcField.gridy = 2;
        panel.add(asuntoField, gbcField);

        gbcLabel.gridx = 0;
        gbcLabel.gridy = 3;
        panel.add(new JLabel("Cantidad:"), gbcLabel);

        JTextField cantidadField = new JTextField(20);
        gbcField.gridx = 1;
        gbcField.gridy = 3;
        panel.add(cantidadField, gbcField);

        GridBagConstraints gbcButton = new GridBagConstraints();
        gbcButton.gridx = 0;
        gbcButton.gridy = 4;
        gbcButton.gridwidth = 2;
        gbcButton.anchor = GridBagConstraints.CENTER;
        gbcButton.insets = new Insets(25, 15, 15, 15);
        JButton registrarButton = new JButton("Registrar");
        registrarButton.addActionListener(e -> {
            String nombreDestinatarioRemitente = destinatarioField.getText();
            String asunto = asuntoField.getText();
            try {
                double cantidad = Double.parseDouble(cantidadField.getText());

                if (cantidad > 0) {
                    String nombreUsuarioActual = obtenerNombreUsuario(idUsuarioActual);
                    String opcion = (String) tipoTransferenciaComboBox.getSelectedItem();

                    if ("Enviar Dinero".equals(opcion)) {
                        Transferencia transferencia = new Transferencia(nombreUsuarioActual, nombreDestinatarioRemitente, cantidad, asunto);
                        TablaTransferencia tablaTransferencia = new TablaTransferencia();
                        int resultado = tablaTransferencia.registrarTransferencia(transferencia);

                        if (resultado > 0) {
                            String mensaje = "Dinero enviado: " + cantidad + "€ a " + nombreDestinatarioRemitente + " por " + asunto;
                            JOptionPane.showMessageDialog(null, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
                            destinatarioField.setText("");
                            asuntoField.setText("");
                            cantidadField.setText("");
                            mainView.actualizarTotales();
                        } else {
                            JOptionPane.showMessageDialog(null, "Error al registrar la transferencia", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else if ("Recibir Dinero".equals(opcion)) {
                        Transferencia transferencia = new Transferencia(nombreDestinatarioRemitente, nombreUsuarioActual, cantidad, asunto);
                        TablaTransferencia tablaTransferencia = new TablaTransferencia();
                        int resultado = tablaTransferencia.registrarTransferencia(transferencia);

                        if (resultado > 0) {
                            String mensaje = "Dinero recibido: " + cantidad + "€ de " + nombreDestinatarioRemitente + " por " + asunto;
                            JOptionPane.showMessageDialog(null, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
                            destinatarioField.setText("");
                            asuntoField.setText("");
                            cantidadField.setText("");
                            mainView.actualizarTotales();
                        } else {
                            JOptionPane.showMessageDialog(null, "Error al registrar la transferencia", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor que 0", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese una cantidad válida", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(registrarButton, gbcButton);

        return panel;
    }

    private String obtenerNombreUsuario(int idUsuario) {
        return "Nombre del Usuario";
    }
}
