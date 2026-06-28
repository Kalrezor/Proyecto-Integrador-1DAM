package com.dam.finanzas.view;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.Map;
import com.dam.finanzas.model.bbdd.TablaIngresos;
import com.dam.finanzas.model.bbdd.TablaGastos;
import com.dam.finanzas.model.bbdd.TablaTransferencia;
import com.dam.finanzas.model.bbdd.TablaUsuario;
import com.dam.finanzas.model.Ingreso;
import com.dam.finanzas.model.Gasto;
import com.dam.finanzas.model.Transferencia;
import com.dam.finanzas.model.Usuario;
import java.util.List;

public class TransaccionesView extends JPanel {
    private final int idUsuarioActual;
    private final MainView mainView;

    public TransaccionesView(int idUsuarioActual, MainView mainView) {
        this.idUsuarioActual = idUsuarioActual;
        this.mainView = mainView;
        initialize();
    }

    private void initialize() {
        setLayout(new BorderLayout());
        setBackground(UIUtils.BG);

        // Cabecera estilo home
        JPanel headerPanel = new JPanel(new BorderLayout(0, 2));
        headerPanel.setBackground(UIUtils.BG_CARD);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, UIUtils.BORDER),
            BorderFactory.createEmptyBorder(10, 16, 10, 16)));

        JLabel titleLabel = new JLabel("Gestión de Transacciones");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(UIUtils.TEXT);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(titleLabel, BorderLayout.NORTH);

        JLabel subtitleLabel = new JLabel("Registra tus ingresos, gastos y transferencias");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        subtitleLabel.setForeground(UIUtils.TEXT_MUTED);
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(subtitleLabel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);

        // Panel de contenido con CardLayout
        CardLayout cardLayout = new CardLayout();
        JPanel contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(UIUtils.BG);
        contentPanel.add(createIngresosPanel(), "INGRESOS");
        contentPanel.add(createGastosPanel(), "GASTOS");
        contentPanel.add(createTransferenciasPanel(), "TRANSFERENCIAS");

        // Barra de botones de ancho completo
        JPanel tabBar = new JPanel(new GridLayout(1, 3, 0, 0));
        tabBar.setBackground(UIUtils.BG_CARD);
        tabBar.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, UIUtils.BORDER));

        JButton btnIngresos     = crearTabBoton("Ingresos");
        JButton btnGastos       = crearTabBoton("Gastos");
        JButton btnTransfer     = crearTabBoton("Transferencias");

        JButton[] tabs = {btnIngresos, btnGastos, btnTransfer};
        String[]  keys = {"INGRESOS", "GASTOS", "TRANSFERENCIAS"};

        for (int i = 0; i < tabs.length; i++) {
            final int idx = i;
            tabs[i].addActionListener(e -> {
                cardLayout.show(contentPanel, keys[idx]);
                for (JButton t : tabs) activarTab(t, false);
                activarTab(tabs[idx], true);
            });
            tabBar.add(tabs[i]);
        }
        activarTab(btnIngresos, true);

        JPanel northWrapper = new JPanel(new BorderLayout());
        northWrapper.setBackground(UIUtils.BG_CARD);
        northWrapper.add(headerPanel, BorderLayout.NORTH);
        northWrapper.add(tabBar, BorderLayout.SOUTH);

        add(northWrapper, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }

    private JButton crearTabBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setForeground(UIUtils.TEXT_MUTED);
        btn.setBackground(UIUtils.BG_CARD);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(true);
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(0, 40));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent e) {
                if (!UIUtils.ACCENT.equals(btn.getForeground()))
                    btn.setBackground(UIUtils.BG);
            }
            @Override public void mouseExited(java.awt.event.MouseEvent e) {
                if (!UIUtils.ACCENT.equals(btn.getForeground()))
                    btn.setBackground(UIUtils.BG_CARD);
            }
        });
        return btn;
    }

    private void activarTab(JButton btn, boolean activo) {
        if (activo) {
            btn.setForeground(UIUtils.ACCENT);
            btn.setBackground(UIUtils.BG_CARD);
            btn.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, UIUtils.ACCENT));
        } else {
            btn.setForeground(UIUtils.TEXT_MUTED);
            btn.setBackground(UIUtils.BG_CARD);
            btn.setBorder(null);
        }
    }

    private JPanel createIngresosPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(UIUtils.BG);

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
        JButton registrarButton = UIUtils.crearBoton("Registrar Ingreso", UIUtils.SUCCESS, Color.WHITE);
        registrarButton.addActionListener(e -> {
            try {
                double cantidad = Double.parseDouble(cantidadField.getText());
                String descripcion = descripcionField.getText();

                if (cantidad > 0) {
                    Ingreso ingreso = new Ingreso(idUsuarioActual, descripcion, cantidad, LocalDate.now().toString());
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
        panel.setBackground(UIUtils.BG);

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
        UIUtils.estilizarComboBox(categoriaComboBox);
        categoriaComboBox.setRenderer(UIUtils.rendererComboBox());
        gbcField.gridx = 1;
        gbcField.gridy = 2;
        panel.add(UIUtils.wrapComboBox(categoriaComboBox), gbcField);

        GridBagConstraints gbcButton = new GridBagConstraints();
        gbcButton.gridx = 0;
        gbcButton.gridy = 3;
        gbcButton.gridwidth = 2;
        gbcButton.anchor = GridBagConstraints.CENTER;
        gbcButton.insets = new Insets(25, 15, 15, 15);
        JButton registrarButton = UIUtils.crearBoton("Registrar Gasto", UIUtils.DANGER, Color.WHITE);
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

                    Gasto gasto = new Gasto(idUsuarioActual, descripcion, categoria, cantidad, LocalDate.now().toString());
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
        panel.setBackground(UIUtils.BG);

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
        UIUtils.estilizarComboBox(tipoTransferenciaComboBox);
        tipoTransferenciaComboBox.setRenderer(UIUtils.rendererComboBox());
        gbcField.gridx = 1;
        gbcField.gridy = 0;
        panel.add(UIUtils.wrapComboBox(tipoTransferenciaComboBox), gbcField);

        gbcLabel.gridx = 0;
        gbcLabel.gridy = 1;
        panel.add(new JLabel("Destinatario/Remitente:"), gbcLabel);

        List<Usuario> usuarios = new TablaUsuario().obtenerTodosUsuariosExcepto(idUsuarioActual);
        JComboBox<Usuario> destinatarioComboBox = new JComboBox<>();
        for (Usuario u : usuarios) destinatarioComboBox.addItem(u);
        UIUtils.estilizarComboBox(destinatarioComboBox);
        destinatarioComboBox.setRenderer(new javax.swing.DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(javax.swing.JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 13));
                setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                setBackground(isSelected ? UIUtils.BG_ACCENT : UIUtils.BG_CARD);
                setForeground(isSelected ? UIUtils.ACCENT : UIUtils.TEXT);
                if (value instanceof Usuario u) setText(u.getNombre());
                return this;
            }
        });
        gbcField.gridx = 1;
        gbcField.gridy = 1;
        panel.add(UIUtils.wrapComboBox(destinatarioComboBox), gbcField);

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
        JButton registrarButton = UIUtils.crearBoton("Registrar Transferencia", UIUtils.ACCENT, Color.WHITE);
        registrarButton.addActionListener(e -> {
            Usuario usuarioSeleccionado = (Usuario) destinatarioComboBox.getSelectedItem();
            if (usuarioSeleccionado == null) {
                JOptionPane.showMessageDialog(null, "No hay usuarios disponibles", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String asunto = asuntoField.getText();
            try {
                double cantidad = Double.parseDouble(cantidadField.getText());

                if (cantidad > 0) {
                    int idOtroUsuario = usuarioSeleccionado.getIdUsuario();
                    String nombreOtroUsuario = usuarioSeleccionado.getNombre();
                    String opcion = (String) tipoTransferenciaComboBox.getSelectedItem();
                    Transferencia transferencia;
                    String mensaje;

                    if ("Enviar Dinero".equals(opcion)) {
                        transferencia = new Transferencia(idUsuarioActual, idOtroUsuario, cantidad, asunto);
                        mensaje = "Dinero enviado: " + cantidad + "€ a " + nombreOtroUsuario;
                    } else {
                        transferencia = new Transferencia(idOtroUsuario, idUsuarioActual, cantidad, asunto);
                        mensaje = "Dinero recibido: " + cantidad + "€ de " + nombreOtroUsuario;
                    }

                    int resultado = new TablaTransferencia().registrarTransferencia(transferencia);

                    if (resultado > 0) {
                        JOptionPane.showMessageDialog(null, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        asuntoField.setText("");
                        cantidadField.setText("");
                        mainView.actualizarTotales();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al registrar la transferencia", "Error", JOptionPane.ERROR_MESSAGE);
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
}
