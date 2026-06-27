package com.dam.finanzas.view;

import javax.swing.*;
import com.dam.finanzas.control.AppControlador;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class LoginView extends JFrame {
    private JTextField txtCorreo;
    private JPasswordField txtContraseña;
    private JButton btnIniciarSesion;
    private JButton btnCrearCuenta;

    public LoginView() {
        setTitle("Inicio de Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 700, 500);
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        JPanel contentPane = new JPanel(new GridBagLayout());
        contentPane.setBackground(new Color(44, 62, 80));
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(new Color(52, 152, 219));
        loginPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(41, 128, 185), 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        JLabel lblIconoUsuario = new JLabel("👤");
        lblIconoUsuario.setHorizontalAlignment(SwingConstants.CENTER);
        lblIconoUsuario.setFont(new Font("Arial", Font.BOLD, 48));
        lblIconoUsuario.setForeground(Color.WHITE);
        GridBagConstraints gbcIconoUsuario = new GridBagConstraints();
        gbcIconoUsuario.gridx = 0;
        gbcIconoUsuario.gridy = 0;
        gbcIconoUsuario.gridwidth = 2;
        gbcIconoUsuario.anchor = GridBagConstraints.CENTER;
        gbcIconoUsuario.insets = new Insets(10, 10, 10, 10);
        loginPanel.add(lblIconoUsuario, gbcIconoUsuario);

        txtCorreo = new JTextField("Correo", 20);
        txtCorreo.setForeground(Color.GRAY);
        txtCorreo.setFont(new Font("Tahoma", Font.PLAIN, 14));
        GridBagConstraints gbcCorreo = new GridBagConstraints();
        gbcCorreo.gridx = 0;
        gbcCorreo.gridy = 1;
        gbcCorreo.gridwidth = 2;
        gbcCorreo.fill = GridBagConstraints.HORIZONTAL;
        gbcCorreo.insets = new Insets(10, 10, 10, 10);
        loginPanel.add(txtCorreo, gbcCorreo);

        txtCorreo.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtCorreo.getText().equals("Correo")) {
                    txtCorreo.setText("");
                    txtCorreo.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtCorreo.getText().isEmpty()) {
                    txtCorreo.setText("Correo");
                    txtCorreo.setForeground(Color.GRAY);
                }
            }
        });

        txtContraseña = new JPasswordField("Contraseña", 20);
        txtContraseña.setEchoChar((char) 0);
        txtContraseña.setForeground(Color.GRAY);
        txtContraseña.setFont(new Font("Tahoma", Font.PLAIN, 14));
        GridBagConstraints gbcContraseña = new GridBagConstraints();
        gbcContraseña.gridx = 0;
        gbcContraseña.gridy = 2;
        gbcContraseña.gridwidth = 2;
        gbcContraseña.fill = GridBagConstraints.HORIZONTAL;
        gbcContraseña.insets = new Insets(10, 10, 10, 10);
        loginPanel.add(txtContraseña, gbcContraseña);

        txtContraseña.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (new String(txtContraseña.getPassword()).equals("Contraseña")) {
                    txtContraseña.setText("");
                    txtContraseña.setEchoChar('●');
                    txtContraseña.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (new String(txtContraseña.getPassword()).isEmpty()) {
                    txtContraseña.setText("Contraseña");
                    txtContraseña.setEchoChar((char) 0);
                    txtContraseña.setForeground(Color.GRAY);
                }
            }
        });

        btnIniciarSesion = new JButton("Iniciar Sesión");
        btnIniciarSesion.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnIniciarSesion.setBackground(new Color(44, 62, 80));
        btnIniciarSesion.setForeground(Color.WHITE);
        GridBagConstraints gbcIniciarSesion = new GridBagConstraints();
        gbcIniciarSesion.gridx = 0;
        gbcIniciarSesion.gridy = 3;
        gbcIniciarSesion.anchor = GridBagConstraints.CENTER;
        gbcIniciarSesion.insets = new Insets(10, 10, 10, 10);
        loginPanel.add(btnIniciarSesion, gbcIniciarSesion);

        btnCrearCuenta = new JButton("Crear Cuenta");
        btnCrearCuenta.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnCrearCuenta.setBackground(new Color(44, 62, 80));
        btnCrearCuenta.setForeground(Color.WHITE);
        GridBagConstraints gbcCrearCuenta = new GridBagConstraints();
        gbcCrearCuenta.gridx = 1;
        gbcCrearCuenta.gridy = 3;
        gbcCrearCuenta.anchor = GridBagConstraints.CENTER;
        gbcCrearCuenta.insets = new Insets(10, 10, 10, 10);
        loginPanel.add(btnCrearCuenta, gbcCrearCuenta);

        GridBagConstraints gbcLoginPanel = new GridBagConstraints();
        gbcLoginPanel.gridx = 0;
        gbcLoginPanel.gridy = 0;
        gbcLoginPanel.anchor = GridBagConstraints.CENTER;
        contentPane.add(loginPanel, gbcLoginPanel);

        setContentPane(contentPane);

        setFocusTraversalPolicy(new CustomFocusTraversalPolicy());
    }

    public void setController(AppControlador controller) {
        btnIniciarSesion.addActionListener(controller);
        btnCrearCuenta.addActionListener(controller);
    }

    public String getCorreo() {
        return txtCorreo.getText().equals("Correo") ? "" : txtCorreo.getText();
    }

    public String getContrasena() {
        return new String(txtContraseña.getPassword()).equals("Contraseña") ? "" : new String(txtContraseña.getPassword());
    }

    // Clase personalizada para evitar el foco inicial
    private class CustomFocusTraversalPolicy extends FocusTraversalPolicy {
        @Override
        public Component getDefaultComponent(Container focusCycleRoot) {
            return null;
        }

        @Override
        public Component getFirstComponent(Container focusCycleRoot) {
            return null;
        }

        @Override
        public Component getLastComponent(Container focusCycleRoot) {
            return null;
        }

        @Override
        public Component getComponentAfter(Container focusCycleRoot, Component aComponent) {
            return null;
        }

        @Override
        public Component getComponentBefore(Container focusCycleRoot, Component aComponent) {
            return null;
        }
    }
}

