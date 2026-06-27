package com.dam.finanzas.view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.table.TableCellRenderer;
import com.dam.finanzas.model.Deuda;
import com.dam.finanzas.model.ObjetivoFinanciero;
import com.dam.finanzas.model.SesionUsuario;
import com.dam.finanzas.model.bbdd.TablaDeuda;
import com.dam.finanzas.model.bbdd.TablaIngresos;
import com.dam.finanzas.model.bbdd.TablaGastos;
import com.dam.finanzas.model.bbdd.TablaObjetivoFinanciero;
import com.dam.finanzas.model.bbdd.TablaTransferencia;

public class MainView extends JFrame {
    private JPanel sidebar;
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private Map<String, Double> gastosMap;
    private int idUsuarioActual;

    private JLabel ingresosValueLabel;
    private JLabel gastosValueLabel;
    private JLabel beneficioNetoValueLabel;

    private EstadisticasView estadisticasView;
    private DefaultTableModel homeTransferenciasTableModel;
    private JTable homeTransferenciasTable;
    private JLabel homeWelcomeLabel;
    private JLabel homeUserNameLabel;
    private DefaultTableModel homeDeudasTableModel;
    private JTable homeDeudasTable;
    private DefaultTableModel homeObjetivosTableModel;
    private JTable homeObjetivosTable;
	private JLabel lblOcio;
	private JLabel lblRopa;
	private JLabel lblTecno;
	private JLabel lblSalud;
	private JLabel lblTransporte;
	private JLabel lblComida;
	private JLabel lblHogar;
	private JLabel lblEduca;

    public MainView(int idUsuarioActual) {
        this.idUsuarioActual = idUsuarioActual;
        setTitle("Finanzas Personales");
        setSize(862, 601);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        gastosMap = new HashMap<>();
        initComponents();
    }

    private void initComponents() {
        getContentPane().setLayout(new BorderLayout());

        sidebar = createSidebar();
        getContentPane().add(sidebar, BorderLayout.WEST);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(Color.LIGHT_GRAY);

        JPanel homePanel = createHomePanel();
        contentPanel.add(homePanel, "HOME");

        TransaccionesView transaccionesView = new TransaccionesView(idUsuarioActual, this);
        contentPanel.add(transaccionesView, "TRANSACCIONES");

        DeudasView deudasView = new DeudasView(idUsuarioActual);
        JPanel deudasPanel = deudasView.createDeudasPanel();
        contentPanel.add(deudasPanel, "DEUDAS");

        ObjetivosView objetivosView = new ObjetivosView(idUsuarioActual);
        JPanel objetivosPanel = objetivosView.createObjetivosPanel();
        contentPanel.add(objetivosPanel, "OBJETIVOS");

        estadisticasView = new EstadisticasView(idUsuarioActual, this);
        contentPanel.add(estadisticasView.createEstadisticasPanel(), "ESTADISTICAS");

        contentPanel.add(new GuiaView(), "GUIA");
        contentPanel.add(new PerfilView(idUsuarioActual, this), "PERFIL");

        getContentPane().add(contentPanel, BorderLayout.CENTER);

        actualizarTotales();
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel(new GridBagLayout());
        sidebar.setOpaque(true);
        sidebar.setBackground(new Color(44, 62, 80));
        sidebar.setPreferredSize(new Dimension(150, 600));

        JPanel paddingPanel = new JPanel();
        paddingPanel.setOpaque(false);
        GridBagConstraints gbcPadding = new GridBagConstraints();
        gbcPadding.gridy = 0;
        gbcPadding.weighty = 0.1;
        sidebar.add(paddingPanel, gbcPadding);

        JButton btnInicio = new JButton("Inicio");
        configureButton(btnInicio);
        GridBagConstraints gbcInicio = new GridBagConstraints();
        gbcInicio.fill = GridBagConstraints.BOTH;
        gbcInicio.anchor = GridBagConstraints.NORTH;
        gbcInicio.weightx = 1.0;
        gbcInicio.weighty = 1.0;
        gbcInicio.gridy = 1;
        gbcInicio.insets = new Insets(5, 10, 5, 10);
        sidebar.add(btnInicio, gbcInicio);

        btnInicio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "HOME");
                System.out.println("Inicio");
            }
        });

        JButton btnTransacciones = new JButton("Transacciones");
        configureButton(btnTransacciones);
        GridBagConstraints gbcTransacciones = new GridBagConstraints();
        gbcTransacciones.fill = GridBagConstraints.BOTH;
        gbcTransacciones.anchor = GridBagConstraints.NORTH;
        gbcTransacciones.weightx = 1.0;
        gbcTransacciones.weighty = 1.0;
        gbcTransacciones.gridy = 2;
        gbcTransacciones.insets = new Insets(5, 10, 5, 10);
        sidebar.add(btnTransacciones, gbcTransacciones);

        btnTransacciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "TRANSACCIONES");
                System.out.println("Transacciones");
            }
        });

        JButton btnDeudas = new JButton("Deudas");
        configureButton(btnDeudas);
        GridBagConstraints gbcDeudas = new GridBagConstraints();
        gbcDeudas.fill = GridBagConstraints.BOTH;
        gbcDeudas.anchor = GridBagConstraints.NORTH;
        gbcDeudas.weightx = 1.0;
        gbcDeudas.weighty = 1.0;
        gbcDeudas.gridy = 3;
        gbcDeudas.insets = new Insets(5, 10, 5, 10);
        sidebar.add(btnDeudas, gbcDeudas);

        btnDeudas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "DEUDAS");
                System.out.println("Deudas");
            }
        });

        JButton btnObjetivos = new JButton("Objetivos");
        configureButton(btnObjetivos);
        GridBagConstraints gbcObjetivos = new GridBagConstraints();
        gbcObjetivos.fill = GridBagConstraints.BOTH;
        gbcObjetivos.anchor = GridBagConstraints.NORTH;
        gbcObjetivos.weightx = 1.0;
        gbcObjetivos.weighty = 1.0;
        gbcObjetivos.gridy = 4;
        gbcObjetivos.insets = new Insets(5, 10, 5, 10);
        sidebar.add(btnObjetivos, gbcObjetivos);

        btnObjetivos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "OBJETIVOS");
                System.out.println("Objetivos");
            }
        });

        JButton btnEstadisticas = new JButton("Estadísticas");
        configureButton(btnEstadisticas);
        GridBagConstraints gbcEstadisticas = new GridBagConstraints();
        gbcEstadisticas.fill = GridBagConstraints.BOTH;
        gbcEstadisticas.anchor = GridBagConstraints.NORTH;
        gbcEstadisticas.weightx = 1.0;
        gbcEstadisticas.weighty = 1.0;
        gbcEstadisticas.gridy = 5;
        gbcEstadisticas.insets = new Insets(5, 10, 5, 10);
        sidebar.add(btnEstadisticas, gbcEstadisticas);

        btnEstadisticas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "ESTADISTICAS");
                System.out.println("Estadísticas");
            }
        });

        JButton btnGuia = new JButton("Guía");
        configureButton(btnGuia);
        GridBagConstraints gbcGuia = new GridBagConstraints();
        gbcGuia.fill = GridBagConstraints.BOTH;
        gbcGuia.anchor = GridBagConstraints.NORTH;
        gbcGuia.weightx = 1.0;
        gbcGuia.weighty = 1.0;
        gbcGuia.gridy = 6;
        gbcGuia.insets = new Insets(5, 10, 5, 10);
        sidebar.add(btnGuia, gbcGuia);

        btnGuia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "GUIA");
            }
        });

        JButton btnPerfil = new JButton("Perfil");
        configureButton(btnPerfil);
        GridBagConstraints gbcPerfil = new GridBagConstraints();
        gbcPerfil.fill = GridBagConstraints.BOTH;
        gbcPerfil.anchor = GridBagConstraints.NORTH;
        gbcPerfil.weightx = 1.0;
        gbcPerfil.weighty = 1.0;
        gbcPerfil.gridy = 7;
        gbcPerfil.insets = new Insets(5, 10, 5, 10);
        sidebar.add(btnPerfil, gbcPerfil);

        btnPerfil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "PERFIL");
            }
        });

        return sidebar;
    }

    private void configureButton(JButton button) {
        button.setFont(new Font("Tahoma", Font.PLAIN, 15));
        button.setBackground(new Color(44, 62, 80));
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(130, 130));
    }

    private JPanel createHomePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.LIGHT_GRAY);

        homeWelcomeLabel = new JLabel("¡Hola, " + SesionUsuario.getInstancia().getNombreUsuario() + "!");
        homeWelcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        homeWelcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(homeWelcomeLabel, BorderLayout.NORTH);

        JPanel datosFinancierosPanel = new JPanel(new BorderLayout());
        datosFinancierosPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        datosFinancierosPanel.setBackground(Color.LIGHT_GRAY);

        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM");
        String mesActual = fechaActual.format(formatter);

        JLabel mesActualLabel = new JLabel(mesActual);
        mesActualLabel.setFont(new Font("Arial", Font.BOLD, 14));
        mesActualLabel.setHorizontalAlignment(SwingConstants.CENTER);
        datosFinancierosPanel.add(mesActualLabel, BorderLayout.NORTH);

        JPanel finanzasPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        finanzasPanel.setBackground(new Color(192, 192, 192));

        JPanel ingresosPanel = new JPanel(new BorderLayout());
        ingresosPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JLabel ingresosLabel = new JLabel("Ingresos");
        ingresosLabel.setFont(new Font("Arial", Font.BOLD, 14));
        ingresosLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ingresosValueLabel = new JLabel("0 €");
        ingresosValueLabel.setFont(new Font("Arial", Font.BOLD, 14));
        ingresosValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ingresosPanel.add(ingresosLabel, BorderLayout.NORTH);
        ingresosPanel.add(ingresosValueLabel, BorderLayout.CENTER);

        JPanel gastosPanel = new JPanel(new BorderLayout());
        gastosPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JLabel gastosLabel = new JLabel("Gastos");
        gastosLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gastosLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gastosValueLabel = new JLabel("0 €");
        gastosValueLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gastosValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gastosPanel.add(gastosLabel, BorderLayout.NORTH);
        gastosPanel.add(gastosValueLabel, BorderLayout.CENTER);

        JPanel beneficioNetoPanel = new JPanel(new BorderLayout());
        beneficioNetoPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JLabel beneficioNetoLabel = new JLabel("Beneficio Neto");
        beneficioNetoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        beneficioNetoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        beneficioNetoValueLabel = new JLabel("0 €");
        beneficioNetoValueLabel.setFont(new Font("Arial", Font.BOLD, 14));
        beneficioNetoValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        beneficioNetoPanel.add(beneficioNetoLabel, BorderLayout.NORTH);
        beneficioNetoPanel.add(beneficioNetoValueLabel, BorderLayout.CENTER);

        finanzasPanel.add(ingresosPanel);
        finanzasPanel.add(gastosPanel);
        finanzasPanel.add(beneficioNetoPanel);

        datosFinancierosPanel.add(finanzasPanel, BorderLayout.CENTER);

        // 3 tablas apiladas verticalmente
        JPanel tablesPanel = new JPanel(new GridLayout(3, 1, 0, 5));
        tablesPanel.setBackground(new Color(192, 192, 192));
        tablesPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));

        // Transferencias recientes
        JPanel transferenciasSubPanel = new JPanel(new BorderLayout());
        transferenciasSubPanel.setBackground(new Color(192, 192, 192));
        transferenciasSubPanel.setBorder(BorderFactory.createTitledBorder("Transferencias recientes"));
        String[] columnNamesTransferencias = {"Remitente", "Destinatario", "Monto"};
        homeTransferenciasTableModel = new DefaultTableModel(columnNamesTransferencias, 0);
        homeTransferenciasTable = new JTable(homeTransferenciasTableModel);
        DefaultTableCellRenderer centerRend = new DefaultTableCellRenderer();
        centerRend.setHorizontalAlignment(SwingConstants.CENTER);
        homeTransferenciasTable.setDefaultRenderer(Object.class, centerRend);
        homeTransferenciasTable.getTableHeader().setReorderingAllowed(false);
        homeTransferenciasTable.getTableHeader().setResizingAllowed(false);
        homeTransferenciasTable.setRowHeight(22);
        homeTransferenciasTable.setFillsViewportHeight(true);
        transferenciasSubPanel.add(new JScrollPane(homeTransferenciasTable), BorderLayout.CENTER);
        tablesPanel.add(transferenciasSubPanel);

        // Deudas próximas a vencer
        JPanel deudasHomePanel = new JPanel(new BorderLayout());
        deudasHomePanel.setBackground(new Color(192, 192, 192));
        deudasHomePanel.setBorder(BorderFactory.createTitledBorder("Deudas próximas a vencer"));
        String[] deudasCols = {"Descripción", "Pendiente", "Vence", "Días"};
        homeDeudasTableModel = new DefaultTableModel(deudasCols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        homeDeudasTable = new JTable(homeDeudasTableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    Object val = getModel().getValueAt(row, 3);
                    String diasStr = val != null ? val.toString() : "";
                    if ("Vencida".equals(diasStr)) {
                        c.setForeground(new Color(160, 0, 0));
                    } else {
                        try {
                            int dias = Integer.parseInt(diasStr.replaceAll("[^0-9]", ""));
                            if (dias <= 7) c.setForeground(new Color(160, 0, 0));
                            else if (dias <= 30) c.setForeground(new Color(180, 100, 0));
                            else c.setForeground(Color.BLACK);
                        } catch (NumberFormatException ex) {
                            c.setForeground(Color.BLACK);
                        }
                    }
                }
                return c;
            }
        };
        DefaultTableCellRenderer centerRendD = new DefaultTableCellRenderer();
        centerRendD.setHorizontalAlignment(SwingConstants.CENTER);
        homeDeudasTable.setDefaultRenderer(Object.class, centerRendD);
        homeDeudasTable.getTableHeader().setReorderingAllowed(false);
        homeDeudasTable.getTableHeader().setResizingAllowed(false);
        homeDeudasTable.setRowHeight(22);
        homeDeudasTable.setFillsViewportHeight(true);
        deudasHomePanel.add(new JScrollPane(homeDeudasTable), BorderLayout.CENTER);
        tablesPanel.add(deudasHomePanel);

        // Objetivos activos
        JPanel objetivosHomePanel = new JPanel(new BorderLayout());
        objetivosHomePanel.setBackground(new Color(192, 192, 192));
        objetivosHomePanel.setBorder(BorderFactory.createTitledBorder("Objetivos activos"));
        String[] objetivosCols = {"Descripción", "Ahorro/mes", "Tiempo estimado"};
        homeObjetivosTableModel = new DefaultTableModel(objetivosCols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        homeObjetivosTable = new JTable(homeObjetivosTableModel);
        DefaultTableCellRenderer centerRendO = new DefaultTableCellRenderer();
        centerRendO.setHorizontalAlignment(SwingConstants.CENTER);
        homeObjetivosTable.setDefaultRenderer(Object.class, centerRendO);
        homeObjetivosTable.getTableHeader().setReorderingAllowed(false);
        homeObjetivosTable.getTableHeader().setResizingAllowed(false);
        homeObjetivosTable.setRowHeight(22);
        homeObjetivosTable.setFillsViewportHeight(true);
        objetivosHomePanel.add(new JScrollPane(homeObjetivosTable), BorderLayout.CENTER);
        tablesPanel.add(objetivosHomePanel);

        // Panel derecho - Gastos por categoría (grid 2 columnas, compacto)
        JPanel rightPanel = new JPanel(new BorderLayout(0, 4));
        rightPanel.setBackground(Color.LIGHT_GRAY);
        rightPanel.setPreferredSize(new Dimension(170, 0));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 4));

        JPanel rightHeader = new JPanel(new BorderLayout(6, 0));
        rightHeader.setBackground(Color.LIGHT_GRAY);
        JLabel userIcon = new JLabel("👤");
        userIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        homeUserNameLabel = new JLabel(SesionUsuario.getInstancia().getNombreUsuario());
        homeUserNameLabel.setFont(new Font("Arial", Font.BOLD, 13));
        rightHeader.add(userIcon, BorderLayout.WEST);
        rightHeader.add(homeUserNameLabel, BorderLayout.CENTER);
        rightPanel.add(rightHeader, BorderLayout.NORTH);

        JPanel gastosContainer = new JPanel();
        gastosContainer.setLayout(new BoxLayout(gastosContainer, BoxLayout.Y_AXIS));
        gastosContainer.setBackground(Color.LIGHT_GRAY);

        JLabel tiposGastosLabel = new JLabel("Tipos de Gastos");
        tiposGastosLabel.setFont(new Font("Arial", Font.BOLD, 13));
        tiposGastosLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        gastosContainer.add(tiposGastosLabel);
        gastosContainer.add(Box.createVerticalStrut(6));

        Font catFont = new Font("Arial", Font.BOLD, 12);
        JPanel gastosGrid = new JPanel(new GridLayout(8, 2, 4, 12));
        gastosGrid.setBackground(Color.LIGHT_GRAY);
        gastosGrid.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel l1 = new JLabel("Ocio"); l1.setFont(catFont);
        lblOcio = new JLabel("0,00 €"); lblOcio.setFont(catFont); lblOcio.setHorizontalAlignment(SwingConstants.RIGHT);
        gastosGrid.add(l1); gastosGrid.add(lblOcio);
        JLabel l2 = new JLabel("Ropa"); l2.setFont(catFont);
        lblRopa = new JLabel("0,00 €"); lblRopa.setFont(catFont); lblRopa.setHorizontalAlignment(SwingConstants.RIGHT);
        gastosGrid.add(l2); gastosGrid.add(lblRopa);
        JLabel l3 = new JLabel("Tecnología"); l3.setFont(catFont);
        lblTecno = new JLabel("0,00 €"); lblTecno.setFont(catFont); lblTecno.setHorizontalAlignment(SwingConstants.RIGHT);
        gastosGrid.add(l3); gastosGrid.add(lblTecno);
        JLabel l4 = new JLabel("Salud"); l4.setFont(catFont);
        lblSalud = new JLabel("0,00 €"); lblSalud.setFont(catFont); lblSalud.setHorizontalAlignment(SwingConstants.RIGHT);
        gastosGrid.add(l4); gastosGrid.add(lblSalud);
        JLabel l5 = new JLabel("Transporte"); l5.setFont(catFont);
        lblTransporte = new JLabel("0,00 €"); lblTransporte.setFont(catFont); lblTransporte.setHorizontalAlignment(SwingConstants.RIGHT);
        gastosGrid.add(l5); gastosGrid.add(lblTransporte);
        JLabel l6 = new JLabel("Comida"); l6.setFont(catFont);
        lblComida = new JLabel("0,00 €"); lblComida.setFont(catFont); lblComida.setHorizontalAlignment(SwingConstants.RIGHT);
        gastosGrid.add(l6); gastosGrid.add(lblComida);
        JLabel l7 = new JLabel("Hogar"); l7.setFont(catFont);
        lblHogar = new JLabel("0,00 €"); lblHogar.setFont(catFont); lblHogar.setHorizontalAlignment(SwingConstants.RIGHT);
        gastosGrid.add(l7); gastosGrid.add(lblHogar);
        JLabel l8 = new JLabel("Educación"); l8.setFont(catFont);
        lblEduca = new JLabel("0,00 €"); lblEduca.setFont(catFont); lblEduca.setHorizontalAlignment(SwingConstants.RIGHT);
        gastosGrid.add(l8); gastosGrid.add(lblEduca);

        gastosContainer.add(gastosGrid);
        rightPanel.add(gastosContainer, BorderLayout.CENTER);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.LIGHT_GRAY);

        centerPanel.add(datosFinancierosPanel, BorderLayout.NORTH);
        centerPanel.add(tablesPanel, BorderLayout.CENTER);

        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(rightPanel, BorderLayout.EAST);

        return panel;
    }

    public void actualizarTotales() {
        int mes = LocalDate.now().getMonthValue();
        int año = LocalDate.now().getYear();

        TablaIngresos tablaIngresos = new TablaIngresos();
        double totalIngresos = tablaIngresos.obtenerTotalIngresosMes(idUsuarioActual, mes, año);

        TablaGastos tablaGastos = new TablaGastos();
        double totalGastos = tablaGastos.obtenerTotalGastosMes(idUsuarioActual, mes, año);
        Map<String, Double> totalPorCategoria = tablaGastos.obtenerTotalGastosPorCategoriaMes(idUsuarioActual, mes, año);

        double beneficioNeto = totalIngresos - totalGastos;

        ingresosValueLabel.setText(String.format("%.2f €", totalIngresos));
        gastosValueLabel.setText(String.format("%.2f €", totalGastos));
        beneficioNetoValueLabel.setText(String.format("%.2f €", beneficioNeto));

        actualizarTotalesPorCategoria(totalPorCategoria);

        homeTransferenciasTableModel.setRowCount(0);
        for (Object[] fila : new TablaTransferencia().obtenerTransferencias(idUsuarioActual)) {
            homeTransferenciasTableModel.addRow(fila);
        }
        UIUtils.ajustarTabla(homeTransferenciasTable);

        actualizarPanelInferior();

        estadisticasView.actualizarTotales();
        estadisticasView.actualizarTablaObjetivos();
        estadisticasView.actualizarTablaDeudas();
        estadisticasView.actualizarTablaTransferencias();
    }

    private void actualizarPanelInferior() {
        homeDeudasTableModel.setRowCount(0);
        List<Deuda> deudas = new TablaDeuda().obtenerDeudasPorUsuario(idUsuarioActual);
        deudas.stream()
            .filter(d -> "EN PROGRESO".equals(d.getEstado()))
            .sorted(Comparator.comparingLong(d -> diasHastaVencimiento(d.getFechaVencimiento())))
            .limit(5)
            .forEach(d -> {
                long dias = diasHastaVencimiento(d.getFechaVencimiento());
                String diasStr = dias >= 0 ? dias + " días" : "Vencida";
                homeDeudasTableModel.addRow(new Object[]{
                    d.getDescripcion(),
                    String.format("%.2f €", d.getMontoPendiente()),
                    d.getFechaVencimiento(),
                    diasStr
                });
            });
        UIUtils.ajustarTabla(homeDeudasTable);

        homeObjetivosTableModel.setRowCount(0);
        List<ObjetivoFinanciero> objetivos = new TablaObjetivoFinanciero().obtenerObjetivosPorUsuario(idUsuarioActual);
        objetivos.stream()
            .filter(o -> "En progreso".equals(o.getEstado()))
            .limit(5)
            .forEach(o -> homeObjetivosTableModel.addRow(new Object[]{
                o.getDescripcion(),
                String.format("%.2f €/mes", o.getAhorroMensualSugerido()),
                o.getTiempoNecesario()
            }));
        UIUtils.ajustarTabla(homeObjetivosTable);
    }

    private long diasHastaVencimiento(String fechaStr) {
        for (String formato : new String[]{"dd-MM-yyyy", "dd/MM/yyyy"}) {
            try {
                LocalDate fecha = LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern(formato));
                return ChronoUnit.DAYS.between(LocalDate.now(), fecha);
            } catch (Exception ignored) {}
        }
        return Long.MAX_VALUE;
    }

    private void actualizarTotalesPorCategoria(Map<String, Double> totalPorCategoria) {
        lblOcio.setText(String.format("%.2f €", totalPorCategoria.getOrDefault("Ocio y Entretenimiento", 0.0)));
        lblRopa.setText(String.format("%.2f €", totalPorCategoria.getOrDefault("Ropa y Accesorios", 0.0)));
        lblTecno.setText(String.format("%.2f €", totalPorCategoria.getOrDefault("Tecnología y Gadgets", 0.0)));
        lblSalud.setText(String.format("%.2f €", totalPorCategoria.getOrDefault("Salud y Cuidado Personal", 0.0)));
        lblTransporte.setText(String.format("%.2f €", totalPorCategoria.getOrDefault("Transporte y Movilidad", 0.0)));
        lblComida.setText(String.format("%.2f €", totalPorCategoria.getOrDefault("Comida y Supermercado", 0.0)));
        lblHogar.setText(String.format("%.2f €", totalPorCategoria.getOrDefault("Hogar y Decoración", 0.0)));
        lblEduca.setText(String.format("%.2f €", totalPorCategoria.getOrDefault("Educación y Formación", 0.0)));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            int idUsuarioActual = 1;
            MainView frame = new MainView(idUsuarioActual);
            frame.setVisible(true);
        });
    }

    public void actualizarNombreEnHome(String nuevoNombre) {
        homeWelcomeLabel.setText("¡Hola, " + nuevoNombre + "!");
        homeUserNameLabel.setText(nuevoNombre);
    }

    public Map<String, Double> getGastosMap() {
        return gastosMap;
    }
}
