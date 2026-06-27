package com.dam.finanzas.view;

import javax.swing.*;
import java.awt.*;
import com.dam.finanzas.model.Usuario;
import com.dam.finanzas.model.SesionUsuario;
import com.dam.finanzas.model.bbdd.TablaUsuario;

public class PerfilView extends JPanel {

    private int idUsuarioActual;
    private MainView mainView;
    private JLabel lblNombreActual;
    private JLabel lblCorreoActual;

    public PerfilView(int idUsuarioActual, MainView mainView) {
        this.idUsuarioActual = idUsuarioActual;
        this.mainView = mainView;
        setLayout(new BorderLayout());
        setBackground(Color.LIGHT_GRAY);
        initialize();
    }

    private void initialize() {
        JLabel titleLabel = new JLabel("Perfil de Usuario");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.LIGHT_GRAY);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 60, 20, 60));

        contentPanel.add(createInfoCard());
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(createCambiarNombreCard());
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(createCambiarContrasenaCard());
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(createSalirCard());
        contentPanel.add(Box.createVerticalStrut(20));

        JScrollPane scroll = new JScrollPane(contentPanel);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);
    }

    private JPanel createInfoCard() {
        JPanel card = new JPanel(new BorderLayout(15, 0));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        JLabel avatarLabel = new JLabel("👤");
        avatarLabel.setFont(new Font("Arial", Font.PLAIN, 48));
        avatarLabel.setHorizontalAlignment(SwingConstants.CENTER);
        avatarLabel.setPreferredSize(new Dimension(65, 65));
        card.add(avatarLabel, BorderLayout.WEST);

        TablaUsuario tablaUsuario = new TablaUsuario();
        Usuario usuario = tablaUsuario.obtenerUsuarioPorId(idUsuarioActual);
        String nombre = usuario != null ? usuario.getNombre() : "—";
        String correo = usuario != null ? usuario.getCorreo() : "—";

        JPanel infoPanel = new JPanel(new GridLayout(3, 1, 0, 4));
        infoPanel.setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("Datos de la cuenta");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));

        lblNombreActual = new JLabel("Nombre:  " + nombre);
        lblNombreActual.setFont(new Font("Arial", Font.PLAIN, 13));

        lblCorreoActual = new JLabel("Correo:  " + correo);
        lblCorreoActual.setFont(new Font("Arial", Font.PLAIN, 13));

        infoPanel.add(lblTitulo);
        infoPanel.add(lblNombreActual);
        infoPanel.add(lblCorreoActual);
        card.add(infoPanel, BorderLayout.CENTER);

        return card;
    }

    private JPanel createCambiarNombreCard() {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Cambiar nombre"),
            BorderFactory.createEmptyBorder(8, 15, 12, 15)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 160));

        GridBagConstraints gbcL = new GridBagConstraints();
        gbcL.anchor = GridBagConstraints.WEST;
        gbcL.insets = new Insets(6, 5, 6, 12);

        GridBagConstraints gbcF = new GridBagConstraints();
        gbcF.fill = GridBagConstraints.HORIZONTAL;
        gbcF.weightx = 1.0;
        gbcF.insets = new Insets(6, 0, 6, 5);

        gbcL.gridx = 0; gbcL.gridy = 0;
        card.add(new JLabel("Nuevo nombre:"), gbcL);

        JTextField nombreField = new JTextField(28);
        gbcF.gridx = 1; gbcF.gridy = 0;
        card.add(nombreField, gbcF);

        JLabel lblMensaje = new JLabel(" ");
        lblMensaje.setFont(new Font("Arial", Font.ITALIC, 12));
        GridBagConstraints gbcMsg = new GridBagConstraints();
        gbcMsg.gridx = 0; gbcMsg.gridy = 1; gbcMsg.gridwidth = 2;
        gbcMsg.anchor = GridBagConstraints.WEST;
        gbcMsg.insets = new Insets(0, 5, 4, 5);
        card.add(lblMensaje, gbcMsg);

        JButton btnGuardar = new JButton("Guardar nombre");
        GridBagConstraints gbcBtn = new GridBagConstraints();
        gbcBtn.gridx = 0; gbcBtn.gridy = 2; gbcBtn.gridwidth = 2;
        gbcBtn.anchor = GridBagConstraints.CENTER;
        gbcBtn.insets = new Insets(4, 0, 4, 0);
        card.add(btnGuardar, gbcBtn);

        btnGuardar.addActionListener(e -> {
            String nuevoNombre = nombreField.getText().trim();
            if (nuevoNombre.isEmpty()) {
                mostrarMensaje(lblMensaje, "El nombre no puede estar vacío.", false);
                return;
            }
            if (nuevoNombre.length() > 50) {
                mostrarMensaje(lblMensaje, "El nombre no puede superar los 50 caracteres.", false);
                return;
            }
            String nombreActual = SesionUsuario.getInstancia().getNombreUsuario();
            if (nuevoNombre.equalsIgnoreCase(nombreActual)) {
                mostrarMensaje(lblMensaje, "El nombre nuevo es igual al actual.", false);
                return;
            }

            int res = new TablaUsuario().actualizarNombre(idUsuarioActual, nuevoNombre.toLowerCase());
            if (res > 0) {
                SesionUsuario.getInstancia().setNombreUsuario(nuevoNombre.toLowerCase());
                lblNombreActual.setText("Nombre:  " + nuevoNombre.toLowerCase());
                mainView.actualizarNombreEnHome(nuevoNombre.toLowerCase());
                nombreField.setText("");
                mostrarMensaje(lblMensaje, "Nombre actualizado correctamente.", true);
            } else {
                mostrarMensaje(lblMensaje, "Error al guardar el nombre. Inténtalo de nuevo.", false);
            }
        });

        return card;
    }

    private JPanel createCambiarContrasenaCard() {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Cambiar contraseña"),
            BorderFactory.createEmptyBorder(8, 15, 12, 15)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));

        GridBagConstraints gbcL = new GridBagConstraints();
        gbcL.anchor = GridBagConstraints.WEST;
        gbcL.insets = new Insets(6, 5, 6, 12);

        GridBagConstraints gbcF = new GridBagConstraints();
        gbcF.fill = GridBagConstraints.HORIZONTAL;
        gbcF.weightx = 1.0;
        gbcF.insets = new Insets(6, 0, 6, 5);

        gbcL.gridx = 0; gbcL.gridy = 0;
        card.add(new JLabel("Contraseña actual:"), gbcL);
        JPasswordField fieldActual = new JPasswordField(28);
        gbcF.gridx = 1; gbcF.gridy = 0;
        card.add(fieldActual, gbcF);

        gbcL.gridx = 0; gbcL.gridy = 1;
        card.add(new JLabel("Nueva contraseña:"), gbcL);
        JPasswordField fieldNueva = new JPasswordField(28);
        gbcF.gridx = 1; gbcF.gridy = 1;
        card.add(fieldNueva, gbcF);

        gbcL.gridx = 0; gbcL.gridy = 2;
        card.add(new JLabel("Confirmar contraseña:"), gbcL);
        JPasswordField fieldConfirmar = new JPasswordField(28);
        gbcF.gridx = 1; gbcF.gridy = 2;
        card.add(fieldConfirmar, gbcF);

        JLabel lblMensaje = new JLabel(" ");
        lblMensaje.setFont(new Font("Arial", Font.ITALIC, 12));
        GridBagConstraints gbcMsg = new GridBagConstraints();
        gbcMsg.gridx = 0; gbcMsg.gridy = 3; gbcMsg.gridwidth = 2;
        gbcMsg.anchor = GridBagConstraints.WEST;
        gbcMsg.insets = new Insets(0, 5, 4, 5);
        card.add(lblMensaje, gbcMsg);

        JButton btnGuardar = new JButton("Guardar contraseña");
        GridBagConstraints gbcBtn = new GridBagConstraints();
        gbcBtn.gridx = 0; gbcBtn.gridy = 4; gbcBtn.gridwidth = 2;
        gbcBtn.anchor = GridBagConstraints.CENTER;
        gbcBtn.insets = new Insets(4, 0, 4, 0);
        card.add(btnGuardar, gbcBtn);

        btnGuardar.addActionListener(e -> {
            String actual = new String(fieldActual.getPassword());
            String nueva = new String(fieldNueva.getPassword());
            String confirmar = new String(fieldConfirmar.getPassword());

            if (actual.isEmpty() || nueva.isEmpty() || confirmar.isEmpty()) {
                mostrarMensaje(lblMensaje, "Rellena todos los campos.", false);
                return;
            }
            if (nueva.length() < 6) {
                mostrarMensaje(lblMensaje, "La nueva contraseña debe tener al menos 6 caracteres.", false);
                return;
            }
            if (!nueva.equals(confirmar)) {
                mostrarMensaje(lblMensaje, "Las contraseñas nuevas no coinciden.", false);
                return;
            }
            if (nueva.equals(actual)) {
                mostrarMensaje(lblMensaje, "La nueva contraseña debe ser distinta a la actual.", false);
                return;
            }

            int res = new TablaUsuario().actualizarContrasena(idUsuarioActual, actual, nueva);
            if (res > 0) {
                fieldActual.setText("");
                fieldNueva.setText("");
                fieldConfirmar.setText("");
                mostrarMensaje(lblMensaje, "Contraseña actualizada correctamente.", true);
            } else if (res == -1) {
                mostrarMensaje(lblMensaje, "La contraseña actual no es correcta.", false);
            } else {
                mostrarMensaje(lblMensaje, "Error al guardar. Inténtalo de nuevo.", false);
            }
        });

        return card;
    }

    private JPanel createSalirCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(15, 25, 15, 25)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        JButton btnSalir = new JButton("Cerrar sesión");
        btnSalir.setFont(new Font("Arial", Font.BOLD, 14));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setBackground(new Color(180, 30, 30));
        btnSalir.setOpaque(true);
        btnSalir.setBorderPainted(false);
        btnSalir.setFocusPainted(false);
        btnSalir.addActionListener(e -> System.exit(0));
        card.add(btnSalir, BorderLayout.CENTER);

        return card;
    }

    private void mostrarMensaje(JLabel label, String mensaje, boolean exito) {
        label.setText(mensaje);
        label.setForeground(exito ? new Color(0, 130, 0) : new Color(180, 0, 0));
    }
}
