package com.dam.finanzas.view;

import javax.swing.*;
import java.awt.*;

public class GuiaView extends JPanel {

    public GuiaView() {
        setLayout(new BorderLayout());
        setBackground(Color.LIGHT_GRAY);
        initialize();
    }

    private void initialize() {
        JLabel titleLabel = new JLabel("Guía de Uso");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Arial", Font.BOLD, 13));

        tabs.addTab("Inicio", createTab(
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
            "  • Muestra el gasto del mes desglosado por categoría.\n\n" +
            "Toda la información se actualiza automáticamente al registrar cualquier dato nuevo."
        ));

        tabs.addTab("Transacciones", createTab(
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
            "  • Introduce el nombre del otro usuario tal como está registrado en la app.\n" +
            "  • El usuario debe existir en la aplicación para que la transferencia se procese.\n" +
            "  • Añade un asunto y la cantidad.\n\n" +
            "Al registrar cualquier movimiento, el Inicio se actualiza de forma inmediata."
        ));

        tabs.addTab("Deudas", createTab(
            "Deudas — Gestionar deudas pendientes",
            "Esta sección permite llevar un control detallado de tus deudas.\n\n" +
            "AL REGISTRAR UNA DEUDA debes indicar:\n" +
            "  • Descripción: concepto o acreedor de la deuda.\n" +
            "  • Monto total: importe total de la deuda.\n" +
            "  • Monto pendiente: lo que queda por pagar.\n" +
            "  • Fecha de vencimiento: fecha límite de pago.\n" +
            "  • Estado: EN PROGRESO (deuda activa) o FINALIZADO (deuda saldada).\n\n" +
            "Las deudas con estado EN PROGRESO aparecen en el panel de Inicio\n" +
            "ordenadas por días restantes, con código de color para identificar\n" +
            "rápidamente las más urgentes.\n\n" +
            "El historial completo de deudas está disponible en la sección Estadísticas.\n\n" +
            "Consejo: actualiza el monto pendiente y el estado conforme vayas\n" +
            "pagando la deuda para mantener los datos al día."
        ));

        tabs.addTab("Objetivos", createTab(
            "Objetivos — Planificar metas de ahorro",
            "La sección de Objetivos te ayuda a definir y hacer seguimiento de tus metas financieras.\n\n" +
            "AL CREAR UN OBJETIVO debes indicar:\n" +
            "  • Descripción: nombre de la meta (p. ej. \"Vacaciones en verano\").\n" +
            "  • Costo total: cantidad de dinero que necesitas en total.\n" +
            "  • Ahorro mensual sugerido: cuánto ahorrar cada mes para alcanzarla.\n" +
            "  • Tiempo necesario: estimación del tiempo para conseguirlo.\n" +
            "  • Estado: En progreso (activo) o Cumplido (meta alcanzada).\n\n" +
            "Los objetivos En progreso aparecen en el panel de Inicio para\n" +
            "tenerlos siempre visibles sin necesidad de entrar aquí.\n\n" +
            "El historial completo de objetivos, incluyendo los Cumplidos,\n" +
            "está disponible en la sección Estadísticas."
        ));

        tabs.addTab("Estadísticas", createTab(
            "Estadísticas — Visión global acumulada",
            "La pantalla de Estadísticas ofrece una visión completa de todas tus finanzas\n" +
            "sin filtro de fecha, desde que empezaste a usar la aplicación.\n\n" +
            "RESUMEN\n" +
            "  • Ingresos totales: suma de todos los ingresos registrados.\n" +
            "  • Gastos totales: suma de todos los gastos acumulados.\n\n" +
            "HISTORIAL COMPLETO\n" +
            "  • Transferencias: todas las transferencias en las que has participado.\n" +
            "  • Deudas: lista completa con estado actual de cada deuda.\n" +
            "  • Objetivos: todas las metas con costo, ahorro mensual, tiempo estimado y estado.\n\n" +
            "A diferencia del Inicio, que muestra solo el mes en curso, Estadísticas\n" +
            "acumula el historial total desde el primer uso de la aplicación.\n\n" +
            "Los datos se actualizan automáticamente al registrar cualquier movimiento."
        ));

        add(tabs, BorderLayout.CENTER);
    }

    private JScrollPane createTab(String titulo, String contenido) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
        panel.add(lblTitulo, BorderLayout.NORTH);

        JTextArea textArea = new JTextArea(contenido);
        textArea.setFont(new Font("Arial", Font.PLAIN, 13));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setBackground(Color.WHITE);
        textArea.setForeground(new Color(40, 40, 40));
        textArea.setBorder(null);
        panel.add(textArea, BorderLayout.CENTER);

        JScrollPane scroll = new JScrollPane(panel);
        scroll.setBorder(null);
        return scroll;
    }
}
