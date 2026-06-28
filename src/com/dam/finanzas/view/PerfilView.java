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
        setBackground(UIUtils.BG);
        initialize();
    }

    private void initialize() {
        JPanel headerPanel = new JPanel(new BorderLayout(0, 2));
        headerPanel.setBackground(UIUtils.BG_CARD);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, UIUtils.BORDER),
            BorderFactory.createEmptyBorder(10, 16, 10, 16)));
        JLabel titleLabel = new JLabel("Perfil de Usuario");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(UIUtils.TEXT);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(titleLabel, BorderLayout.NORTH);
        JLabel subtitleLabel = new JLabel("Gestiona tu cuenta y preferencias");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        subtitleLabel.setForeground(UIUtils.TEXT_MUTED);
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(subtitleLabel, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(UIUtils.BG);
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
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scroll, BorderLayout.CENTER);
    }

    private JPanel createInfoCard() {
        JPanel card = new JPanel(new BorderLayout(15, 0));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIUtils.BORDER),
            BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        TablaUsuario tablaUsuario = new TablaUsuario();
        Usuario usuario = tablaUsuario.obtenerUsuarioPorId(idUsuarioActual);
        String nombre = usuario != null ? usuario.getNombre() : "—";
        String correo = usuario != null ? usuario.getCorreo() : "—";

        String inicial = (nombre != null && !nombre.isEmpty() && !nombre.equals("—"))
            ? nombre.substring(0, 1).toUpperCase() : "?";
        JPanel avatarPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(UIUtils.ACCENT);
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 26));
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(inicial)) / 2;
                int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                g2.drawString(inicial, x, y);
                g2.dispose();
            }
        };
        avatarPanel.setPreferredSize(new Dimension(62, 62));
        avatarPanel.setOpaque(false);
        card.add(avatarPanel, BorderLayout.WEST);

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
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(UIUtils.BORDER), "Cambiar nombre"),
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

        JButton btnGuardar = UIUtils.crearBoton("Guardar nombre", UIUtils.ACCENT, Color.WHITE);
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
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(UIUtils.BORDER), "Cambiar contraseña"),
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

        JButton btnGuardar = UIUtils.crearBoton("Guardar contraseña", UIUtils.ACCENT, Color.WHITE);
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
            BorderFactory.createLineBorder(UIUtils.BORDER),
            BorderFactory.createEmptyBorder(15, 25, 15, 25)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        JButton btnSalir = UIUtils.crearBoton("Cerrar sesión", UIUtils.DANGER, Color.WHITE);
        btnSalir.addActionListener(e -> System.exit(0));
        card.add(btnSalir, BorderLayout.CENTER);

        return card;
    }

    private void mostrarMensaje(JLabel label, String mensaje, boolean exito) {
        label.setText(mensaje);
        label.setForeground(exito ? new Color(0, 130, 0) : new Color(180, 0, 0));
    }
}
