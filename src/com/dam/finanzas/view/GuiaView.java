package com.dam.finanzas.view;

import javax.swing.*;
import java.awt.*;

public class GuiaView extends JPanel {

    public GuiaView() {
        setLayout(new BorderLayout());
        setBackground(UIUtils.BG);
        initialize();
    }

    private void initialize() {
        // Cabecera estilo home
        JPanel headerPanel = new JPanel(new BorderLayout(0, 2));
        headerPanel.setBackground(UIUtils.BG_CARD);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, UIUtils.BORDER),
            BorderFactory.createEmptyBorder(10, 16, 10, 16)));

        JLabel titleLabel = new JLabel("Guía de Uso");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(UIUtils.TEXT);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(titleLabel, BorderLayout.NORTH);

        JLabel subtitleLabel = new JLabel("Todo lo que necesitas saber para usar FlowTrack");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        subtitleLabel.setForeground(UIUtils.TEXT_MUTED);
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(subtitleLabel, BorderLayout.CENTER);

        // Contenido con CardLayout
        CardLayout cardLayout = new CardLayout();
        JPanel contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(UIUtils.BG);

        String[] keys   = {"INICIO", "TRANSACCIONES", "DEUDAS", "OBJETIVOS", "ESTADISTICAS", "PERFIL"};
        String[] labels = {"Inicio", "Transacciones", "Deudas", "Objetivos", "Estadísticas", "Perfil"};

        contentPanel.add(createTab(
            "Inicio — Resumen del mes actual",
            "La pantalla de Inicio muestra un resumen de tu situación financiera en el mes en curso.\n\n" +
            "PANEL SUPERIOR\n" +
            "  • Ingresos: total de ingresos registrados este mes.\n" +
            "  • Gastos: total de gastos del mes actual.\n" +
            "  • Beneficio Neto: diferencia entre ingresos y gastos.\n\n" +
            "PANEL CENTRAL\n" +
            "  • Transferencias recientes: últimas transferencias en las que has participado.\n" +
            "  • Deudas próximas a vencer: deudas activas ordenadas por urgencia.\n" +
            "    - Rojo: vence en 7 días o menos.\n" +
            "    - Naranja: vence en 8 a 30 días.\n" +
            "  • Objetivos activos: metas financieras en progreso con el ahorro mensual necesario.\n\n" +
            "PANEL DERECHO\n" +
            "  • Botón de usuario (esquina superior): despliega acceso rápido a tu perfil\n" +
            "    y la opción de cerrar sesión.\n" +
            "  • Gastos por categoría: desglose del gasto del mes actual por tipo.\n\n" +
            "Toda la información se actualiza automáticamente al registrar cualquier dato nuevo."
        ), "INICIO");

        contentPanel.add(createTab(
            "Transacciones — Registrar movimientos",
            "Desde esta sección puedes registrar tres tipos de movimientos económicos.\n\n" +
            "INGRESOS\n" +
            "  • Introduce la cantidad y una descripción del ingreso.\n" +
            "  • La fecha se registra automáticamente con el día de hoy.\n\n" +
            "GASTOS\n" +
            "  • Introduce la cantidad, una descripción y selecciona la categoría.\n" +
            "  • Categorías disponibles: Ocio y Entretenimiento, Ropa y Accesorios,\n" +
            "    Tecnología y Gadgets, Salud y Cuidado Personal, Transporte y Movilidad,\n" +
            "    Comida y Supermercado, Hogar y Decoración, Educación y Formación.\n\n" +
            "TRANSFERENCIAS\n" +
            "  • Selecciona si envías o recibes dinero.\n" +
            "  • Elige el destinatario o remitente desde el desplegable: muestra\n" +
            "    automáticamente todos los usuarios registrados en la aplicación\n" +
            "    excepto tu propia cuenta.\n" +
            "  • Añade un asunto y la cantidad.\n\n" +
            "Al registrar cualquier movimiento, el Inicio se actualiza de forma inmediata."
        ), "TRANSACCIONES");

        contentPanel.add(createTab(
            "Deudas — Gestionar deudas pendientes",
            "Esta sección permite llevar un control detallado de tus deudas.\n\n" +
            "AL REGISTRAR UNA DEUDA debes indicar:\n" +
            "  • Descripción: concepto o acreedor de la deuda.\n" +
            "  • Monto total: importe total de la deuda.\n" +
            "  • Monto pendiente: lo que queda por pagar en este momento.\n" +
            "  • Fecha de vencimiento: fecha límite de pago (formato dd-MM-yyyy).\n" +
            "  • Toda deuda se registra automáticamente como EN PROGRESO.\n\n" +
            "EDITAR UNA DEUDA\n" +
            "  • Selecciona una deuda de la tabla y pulsa «Editar Deuda».\n" +
            "  • Introduce el nuevo monto pendiente.\n" +
            "  • Si el monto pendiente llega a 0, el estado cambia a FINALIZADO\n" +
            "    de forma automática.\n\n" +
            "Las deudas EN PROGRESO aparecen en el panel de Inicio ordenadas\n" +
            "por días restantes, con código de color para identificar las más urgentes.\n\n" +
            "El historial completo está disponible en la sección Estadísticas."
        ), "DEUDAS");

        contentPanel.add(createTab(
            "Objetivos — Planificar metas de ahorro",
            "La sección de Objetivos te ayuda a definir y hacer seguimiento de tus metas financieras.\n\n" +
            "AL CREAR UN OBJETIVO debes indicar:\n" +
            "  • Descripción: nombre de la meta (p. ej. \"Vacaciones en verano\").\n" +
            "  • Costo total: cantidad de dinero que necesitas en total.\n" +
            "  • Ahorro mensual sugerido: cuánto ahorrar cada mes para alcanzarla.\n" +
            "  • Tiempo necesario: estimación del tiempo para conseguirlo.\n" +
            "  • Estado inicial: En progreso.\n\n" +
            "MARCAR UN OBJETIVO COMO CUMPLIDO\n" +
            "  • Selecciona el objetivo en la tabla y pulsa «Marcar como Cumplido».\n" +
            "  • El estado cambia a Cumplido y deja de aparecer en el panel de Inicio.\n\n" +
            "Los objetivos En progreso aparecen en el Inicio para tenerlos\n" +
            "siempre visibles. El historial completo, incluidos los Cumplidos,\n" +
            "está disponible en Estadísticas."
        ), "OBJETIVOS");

        contentPanel.add(createTab(
            "Estadísticas — Visión global acumulada",
            "La pantalla de Estadísticas ofrece una visión completa de todas tus finanzas\n" +
            "sin filtro de fecha, desde que empezaste a usar la aplicación.\n\n" +
            "RESUMEN\n" +
            "  • Ingresos totales: suma de todos los ingresos registrados.\n" +
            "  • Gastos totales: suma de todos los gastos acumulados.\n" +
            "  • Beneficio Neto: diferencia entre ingresos y gastos totales.\n\n" +
            "HISTORIAL COMPLETO\n" +
            "  • Transferencias: todas las operaciones de envío y recepción de dinero.\n" +
            "  • Deudas: lista completa con monto total, pendiente, fecha y estado.\n" +
            "  • Objetivos: todas las metas con costo, ahorro mensual, tiempo estimado y estado.\n\n" +
            "A diferencia del Inicio —que muestra solo el mes en curso— Estadísticas\n" +
            "acumula el historial total desde el primer uso de la aplicación.\n\n" +
            "Los datos se actualizan automáticamente al registrar cualquier movimiento."
        ), "ESTADISTICAS");

        contentPanel.add(createTab(
            "Perfil — Gestión de tu cuenta",
            "La sección de Perfil te permite consultar y actualizar los datos de tu cuenta.\n\n" +
            "DATOS DE LA CUENTA\n" +
            "  • Muestra tu nombre de usuario y correo electrónico actuales.\n\n" +
            "CAMBIAR NOMBRE\n" +
            "  • Introduce el nuevo nombre en el campo y pulsa «Guardar nombre».\n" +
            "  • El nombre no puede estar vacío ni superar 50 caracteres.\n" +
            "  • El cambio se refleja de inmediato en toda la aplicación.\n\n" +
            "CAMBIAR CONTRASEÑA\n" +
            "  • Introduce tu contraseña actual para verificar tu identidad.\n" +
            "  • Escribe la nueva contraseña (mínimo 6 caracteres) y confírmala.\n" +
            "  • La nueva contraseña debe ser distinta a la actual.\n\n" +
            "CERRAR SESIÓN\n" +
            "  • Cierra la aplicación. También puedes cerrar sesión desde el botón\n" +
            "    de usuario en el panel derecho de la pantalla de Inicio."
        ), "PERFIL");

        // Barra de pestañas de ancho completo
        JPanel tabBar = new JPanel(new GridLayout(1, 6, 0, 0));
        tabBar.setBackground(UIUtils.BG_CARD);
        tabBar.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, UIUtils.BORDER));

        JButton[] tabBtns = new JButton[6];
        for (int i = 0; i < 6; i++) {
            tabBtns[i] = crearTabBoton(labels[i]);
            final int idx = i;
            tabBtns[i].addActionListener(e -> {
                cardLayout.show(contentPanel, keys[idx]);
                for (JButton b : tabBtns) activarTab(b, false);
                activarTab(tabBtns[idx], true);
            });
            tabBar.add(tabBtns[i]);
        }
        activarTab(tabBtns[0], true);

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

    private JScrollPane createTab(String titulo, String contenido) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(UIUtils.BG_CARD);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 15));
        lblTitulo.setForeground(UIUtils.TEXT);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
        panel.add(lblTitulo, BorderLayout.NORTH);

        JTextArea textArea = new JTextArea(contenido);
        textArea.setFont(new Font("Arial", Font.PLAIN, 13));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setBackground(UIUtils.BG_CARD);
        textArea.setForeground(UIUtils.TEXT);
        textArea.setBorder(null);
        panel.add(textArea, BorderLayout.CENTER);

        JScrollPane scroll = new JScrollPane(panel);
        scroll.setBorder(null);
        return scroll;
    }
}
