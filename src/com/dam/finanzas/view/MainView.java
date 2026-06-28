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
    private final Map<String, Double> gastosMap;
    private final int idUsuarioActual;

    private JLabel ingresosValueLabel;
    private JLabel gastosValueLabel;
    private JLabel beneficioNetoValueLabel;

    private EstadisticasView estadisticasView;
    private DefaultTableModel homeTransferenciasTableModel;
    private JTable homeTransferenciasTable;
    private JLabel homeWelcomeLabel;
    private JLabel homeUserNameLabel;
    private JLabel homeAvatarLabel;
    private JButton activeSidebarBtn = null;
    private JButton[] sidebarBtns;
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

        estadisticasView = new EstadisticasView(idUsuarioActual);
        contentPanel.add(estadisticasView.createEstadisticasPanel(), "ESTADISTICAS");

        contentPanel.add(new GuiaView(), "GUIA");
        contentPanel.add(new PerfilView(idUsuarioActual, this), "PERFIL");

        getContentPane().add(contentPanel, BorderLayout.CENTER);

        actualizarTotales();
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel(new GridBagLayout());
        sidebar.setOpaque(true);
        sidebar.setBackground(UIUtils.SIDEBAR);
        sidebar.setPreferredSize(new Dimension(170, 600));

        // Logo
        JPanel logoPanel = new JPanel(new BorderLayout());
        logoPanel.setBackground(UIUtils.SIDEBAR);
        logoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, UIUtils.SIDEBAR_HOVER),
            BorderFactory.createEmptyBorder(14, 16, 14, 16)));
        JLabel logoLabel = new JLabel("FlowTrack");
        logoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        logoLabel.setForeground(UIUtils.ACCENT);
        logoPanel.add(logoLabel, BorderLayout.CENTER);
        GridBagConstraints gbcLogo = new GridBagConstraints();
        gbcLogo.fill = GridBagConstraints.HORIZONTAL;
        gbcLogo.gridx = 0; gbcLogo.gridy = 0;
        gbcLogo.weightx = 1.0; gbcLogo.weighty = 0;
        sidebar.add(logoPanel, gbcLogo);

        String[] labels = {"Inicio", "Transacciones", "Deudas", "Objetivos", "Estadísticas", "Perfil", "Guía"};
        String[] cards  = {"HOME", "TRANSACCIONES", "DEUDAS", "OBJETIVOS", "ESTADISTICAS", "PERFIL", "GUIA"};
        sidebarBtns = new JButton[labels.length];

        for (int i = 0; i < labels.length; i++) {
            sidebarBtns[i] = new JButton(labels[i]);
            configureButton(sidebarBtns[i]);
            final int idx = i;
            sidebarBtns[i].addActionListener(e -> {
                cardLayout.show(contentPanel, cards[idx]);
                setSidebarActive(sidebarBtns[idx]);
            });
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.BOTH;
            gbc.anchor = GridBagConstraints.NORTH;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            gbc.gridy = i + 1;
            gbc.insets = new Insets(5, 10, 5, 10);
            sidebar.add(sidebarBtns[i], gbc);
        }

        setSidebarActive(sidebarBtns[0]);
        return sidebar;
    }

    private void setSidebarActive(JButton btn) {
        if (sidebarBtns != null) {
            for (JButton b : sidebarBtns) {
                b.setBackground(UIUtils.SIDEBAR);
                b.setBorderPainted(false);
            }
        }
        btn.setBackground(UIUtils.SIDEBAR_HOVER);
        btn.setBorderPainted(true);
        btn.setBorder(BorderFactory.createMatteBorder(0, 3, 0, 0, UIUtils.ACCENT));
        activeSidebarBtn = btn;
    }

    private void configureButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setBackground(UIUtils.SIDEBAR);
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(130, 130));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent e) {
                if (button != activeSidebarBtn)
                    button.setBackground(UIUtils.SIDEBAR_HOVER);
            }
            @Override public void mouseExited(java.awt.event.MouseEvent e) {
                if (button != activeSidebarBtn)
                    button.setBackground(UIUtils.SIDEBAR);
            }
        });
    }

    private JPanel createHomePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(UIUtils.BG);

        // Cabecera del home
        JPanel headerPanel = new JPanel(new BorderLayout(0, 2));
        headerPanel.setBackground(UIUtils.BG_CARD);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, UIUtils.BORDER),
            BorderFactory.createEmptyBorder(10, 16, 10, 16)));

        homeWelcomeLabel = new JLabel("Bienvenido a FlowTrack");
        homeWelcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        homeWelcomeLabel.setForeground(UIUtils.TEXT);
        homeWelcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(homeWelcomeLabel, BorderLayout.NORTH);

        JLabel userSubtitleLabel = new JLabel("Hola, " + SesionUsuario.getInstancia().getNombreUsuario());
        userSubtitleLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        userSubtitleLabel.setForeground(UIUtils.TEXT_MUTED);
        userSubtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(userSubtitleLabel, BorderLayout.CENTER);

        panel.add(headerPanel, BorderLayout.NORTH);

        JPanel datosFinancierosPanel = new JPanel(new BorderLayout());
        datosFinancierosPanel.setBorder(BorderFactory.createEmptyBorder(6, 6, 4, 6));
        datosFinancierosPanel.setBackground(UIUtils.BG);

        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        String mesActual = fechaActual.format(formatter);

        JLabel mesActualLabel = new JLabel(mesActual.substring(0, 1).toUpperCase() + mesActual.substring(1));
        mesActualLabel.setFont(new Font("Arial", Font.BOLD, 13));
        mesActualLabel.setForeground(UIUtils.TEXT_MUTED);
        mesActualLabel.setHorizontalAlignment(SwingConstants.CENTER);
        datosFinancierosPanel.add(mesActualLabel, BorderLayout.NORTH);

        JPanel finanzasPanel = new JPanel(new GridLayout(1, 3, 8, 0));
        finanzasPanel.setBackground(UIUtils.BG);
        finanzasPanel.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));

        JPanel ingresosPanel = new JPanel(new BorderLayout(0, 2));
        ingresosPanel.setBackground(UIUtils.BG_SUCCESS);
        ingresosPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(187, 247, 208)),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)));
        JLabel ingresosLabel = new JLabel("Ingresos");
        ingresosLabel.setFont(new Font("Arial", Font.BOLD, 11));
        ingresosLabel.setForeground(UIUtils.SUCCESS);
        ingresosLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ingresosValueLabel = new JLabel("0,00 €");
        ingresosValueLabel.setFont(new Font("Arial", Font.BOLD, 17));
        ingresosValueLabel.setForeground(UIUtils.SUCCESS);
        ingresosValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ingresosPanel.add(ingresosLabel, BorderLayout.NORTH);
        ingresosPanel.add(ingresosValueLabel, BorderLayout.CENTER);

        JPanel gastosPanel = new JPanel(new BorderLayout(0, 2));
        gastosPanel.setBackground(UIUtils.BG_DANGER);
        gastosPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(254, 202, 202)),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)));
        JLabel gastosLabel = new JLabel("Gastos");
        gastosLabel.setFont(new Font("Arial", Font.BOLD, 11));
        gastosLabel.setForeground(UIUtils.DANGER);
        gastosLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gastosValueLabel = new JLabel("0,00 €");
        gastosValueLabel.setFont(new Font("Arial", Font.BOLD, 17));
        gastosValueLabel.setForeground(UIUtils.DANGER);
        gastosValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gastosPanel.add(gastosLabel, BorderLayout.NORTH);
        gastosPanel.add(gastosValueLabel, BorderLayout.CENTER);

        JPanel beneficioNetoPanel = new JPanel(new BorderLayout(0, 2));
        beneficioNetoPanel.setBackground(UIUtils.BG_ACCENT);
        beneficioNetoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(191, 219, 254)),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)));
        JLabel beneficioNetoLabel = new JLabel("Beneficio Neto");
        beneficioNetoLabel.setFont(new Font("Arial", Font.BOLD, 11));
        beneficioNetoLabel.setForeground(UIUtils.ACCENT);
        beneficioNetoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        beneficioNetoValueLabel = new JLabel("0,00 €");
        beneficioNetoValueLabel.setFont(new Font("Arial", Font.BOLD, 17));
        beneficioNetoValueLabel.setForeground(UIUtils.ACCENT);
        beneficioNetoValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        beneficioNetoPanel.add(beneficioNetoLabel, BorderLayout.NORTH);
        beneficioNetoPanel.add(beneficioNetoValueLabel, BorderLayout.CENTER);

        finanzasPanel.add(ingresosPanel);
        finanzasPanel.add(gastosPanel);
        finanzasPanel.add(beneficioNetoPanel);

        datosFinancierosPanel.add(finanzasPanel, BorderLayout.CENTER);

        // 3 tablas apiladas verticalmente
        JPanel tablesPanel = new JPanel(new GridLayout(3, 1, 0, 8));
        tablesPanel.setBackground(UIUtils.BG);
        tablesPanel.setBorder(BorderFactory.createEmptyBorder(4, 8, 8, 8));

        // Transferencias recientes
        JPanel transferenciasSubPanel = new JPanel(new BorderLayout());
        transferenciasSubPanel.setBackground(UIUtils.BG_CARD);
        transferenciasSubPanel.setBorder(BorderFactory.createLineBorder(UIUtils.BORDER));
        JPanel secHdrTr = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 6));
        secHdrTr.setBackground(UIUtils.BG_CARD);
        secHdrTr.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, UIUtils.BORDER));
        JLabel secTitleTr = new JLabel("Transferencias recientes");
        secTitleTr.setFont(new Font("Arial", Font.BOLD, 12));
        secTitleTr.setForeground(UIUtils.TEXT);
        secTitleTr.setBorder(BorderFactory.createMatteBorder(0, 3, 0, 0, UIUtils.ACCENT));
        secHdrTr.add(secTitleTr);
        transferenciasSubPanel.add(secHdrTr, BorderLayout.NORTH);
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
        UIUtils.estilizarTabla(homeTransferenciasTable);
        transferenciasSubPanel.add(new JScrollPane(homeTransferenciasTable), BorderLayout.CENTER);
        tablesPanel.add(transferenciasSubPanel);

        // Deudas próximas a vencer
        JPanel deudasHomePanel = new JPanel(new BorderLayout());
        deudasHomePanel.setBackground(UIUtils.BG_CARD);
        deudasHomePanel.setBorder(BorderFactory.createLineBorder(UIUtils.BORDER));
        JPanel secHdrDeu = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 6));
        secHdrDeu.setBackground(UIUtils.BG_CARD);
        secHdrDeu.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, UIUtils.BORDER));
        JLabel secTitleDeu = new JLabel("Deudas próximas a vencer");
        secTitleDeu.setFont(new Font("Arial", Font.BOLD, 12));
        secTitleDeu.setForeground(UIUtils.TEXT);
        secTitleDeu.setBorder(BorderFactory.createMatteBorder(0, 3, 0, 0, UIUtils.DANGER));
        secHdrDeu.add(secTitleDeu);
        deudasHomePanel.add(secHdrDeu, BorderLayout.NORTH);
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
        UIUtils.estilizarTabla(homeDeudasTable);
        deudasHomePanel.add(new JScrollPane(homeDeudasTable), BorderLayout.CENTER);
        tablesPanel.add(deudasHomePanel);

        // Objetivos activos
        JPanel objetivosHomePanel = new JPanel(new BorderLayout());
        objetivosHomePanel.setBackground(UIUtils.BG_CARD);
        objetivosHomePanel.setBorder(BorderFactory.createLineBorder(UIUtils.BORDER));
        JPanel secHdrObj = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 6));
        secHdrObj.setBackground(UIUtils.BG_CARD);
        secHdrObj.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, UIUtils.BORDER));
        JLabel secTitleObj = new JLabel("Objetivos activos");
        secTitleObj.setFont(new Font("Arial", Font.BOLD, 12));
        secTitleObj.setForeground(UIUtils.TEXT);
        secTitleObj.setBorder(BorderFactory.createMatteBorder(0, 3, 0, 0, UIUtils.SUCCESS));
        secHdrObj.add(secTitleObj);
        objetivosHomePanel.add(secHdrObj, BorderLayout.NORTH);
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
        UIUtils.estilizarTabla(homeObjetivosTable);
        objetivosHomePanel.add(new JScrollPane(homeObjetivosTable), BorderLayout.CENTER);
        tablesPanel.add(objetivosHomePanel);

        // Panel derecho - Gastos por categoría (grid 2 columnas, compacto)
        JPanel rightPanel = new JPanel(new BorderLayout(0, 4));
        rightPanel.setBackground(UIUtils.BG_CARD);
        rightPanel.setPreferredSize(new Dimension(170, 0));
        rightPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 1, 0, 0, UIUtils.BORDER),
            BorderFactory.createEmptyBorder(8, 8, 8, 6)));

        String nombreUsuario = SesionUsuario.getInstancia().getNombreUsuario();
        String inicialUsuario = (nombreUsuario != null && !nombreUsuario.isEmpty())
            ? nombreUsuario.substring(0, 1).toUpperCase() : "?";

        homeAvatarLabel = new JLabel(inicialUsuario) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(UIUtils.ACCENT);
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 14));
                FontMetrics fm = g2.getFontMetrics();
                String t = getText();
                int x = (getWidth() - fm.stringWidth(t)) / 2;
                int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                g2.drawString(t, x, y);
                g2.dispose();
            }
        };
        homeAvatarLabel.setPreferredSize(new Dimension(30, 30));
        homeAvatarLabel.setOpaque(false);

        homeUserNameLabel = new JLabel(nombreUsuario);
        homeUserNameLabel.setFont(new Font("Arial", Font.BOLD, 12));
        homeUserNameLabel.setForeground(UIUtils.TEXT);

        JLabel chevron = new JLabel("▼");
        chevron.setFont(new Font("Arial", Font.PLAIN, 9));
        chevron.setForeground(UIUtils.TEXT_MUTED);

        JPanel userButton = new JPanel(new BorderLayout(6, 0));
        userButton.setBackground(UIUtils.BG_CARD);
        userButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIUtils.BORDER, 1),
            BorderFactory.createEmptyBorder(5, 6, 5, 6)));
        userButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        userButton.add(homeAvatarLabel, BorderLayout.WEST);
        userButton.add(homeUserNameLabel, BorderLayout.CENTER);
        userButton.add(chevron, BorderLayout.EAST);

        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.setBackground(UIUtils.BG_CARD);
        popupMenu.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIUtils.BORDER, 1),
            BorderFactory.createEmptyBorder(4, 0, 4, 0)));

        // Item: Mi perfil
        JMenuItem itemPerfil = new JMenuItem("  Mi perfil");
        itemPerfil.setFont(new Font("Arial", Font.PLAIN, 13));
        itemPerfil.setForeground(UIUtils.TEXT);
        itemPerfil.setBackground(UIUtils.BG_CARD);
        itemPerfil.setOpaque(true);
        itemPerfil.setBorderPainted(false);
        itemPerfil.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
        itemPerfil.addChangeListener(e ->
            itemPerfil.setBackground(itemPerfil.getModel().isArmed() ? UIUtils.BG_ACCENT : UIUtils.BG_CARD));
        itemPerfil.addActionListener(e -> cardLayout.show(contentPanel, "PERFIL"));
        popupMenu.add(itemPerfil);

        // Separador
        javax.swing.JSeparator sep = new javax.swing.JSeparator();
        sep.setForeground(UIUtils.BORDER);
        popupMenu.add(sep);

        // Item: Cerrar sesión
        JMenuItem itemSalir = new JMenuItem("  Cerrar sesión");
        itemSalir.setFont(new Font("Arial", Font.BOLD, 13));
        itemSalir.setForeground(UIUtils.DANGER);
        itemSalir.setBackground(UIUtils.BG_CARD);
        itemSalir.setOpaque(true);
        itemSalir.setBorderPainted(false);
        itemSalir.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
        itemSalir.addChangeListener(e ->
            itemSalir.setBackground(itemSalir.getModel().isArmed() ? UIUtils.BG_DANGER : UIUtils.BG_CARD));
        itemSalir.addActionListener(e -> System.exit(0));
        popupMenu.add(itemSalir);

        userButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(java.awt.event.MouseEvent e) {
                popupMenu.show(userButton, 0, userButton.getHeight());
            }
            @Override public void mouseEntered(java.awt.event.MouseEvent e) {
                userButton.setBackground(UIUtils.BG);
            }
            @Override public void mouseExited(java.awt.event.MouseEvent e) {
                userButton.setBackground(UIUtils.BG_CARD);
            }
        });

        rightPanel.add(userButton, BorderLayout.NORTH);

        JPanel gastosContainer = new JPanel();
        gastosContainer.setLayout(new BoxLayout(gastosContainer, BoxLayout.Y_AXIS));
        gastosContainer.setBackground(UIUtils.BG_CARD);

        JLabel tiposGastosLabel = new JLabel("Gastos por categoría");
        tiposGastosLabel.setFont(new Font("Arial", Font.BOLD, 12));
        tiposGastosLabel.setForeground(UIUtils.TEXT);
        tiposGastosLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        tiposGastosLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, UIUtils.BORDER),
            BorderFactory.createEmptyBorder(0, 0, 6, 0)));
        gastosContainer.add(tiposGastosLabel);
        gastosContainer.add(Box.createVerticalStrut(8));

        Font catNameFont = new Font("Arial", Font.PLAIN, 12);
        Font catValFont  = new Font("Arial", Font.BOLD, 12);
        JPanel gastosGrid = new JPanel(new GridLayout(8, 2, 4, 10));
        gastosGrid.setBackground(UIUtils.BG_CARD);
        gastosGrid.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel l1 = new JLabel("Ocio"); l1.setFont(catNameFont); l1.setForeground(UIUtils.TEXT_MUTED);
        lblOcio = new JLabel("0,00 €"); lblOcio.setFont(catValFont); lblOcio.setForeground(UIUtils.TEXT); lblOcio.setHorizontalAlignment(SwingConstants.RIGHT);
        gastosGrid.add(l1); gastosGrid.add(lblOcio);
        JLabel l2 = new JLabel("Ropa"); l2.setFont(catNameFont); l2.setForeground(UIUtils.TEXT_MUTED);
        lblRopa = new JLabel("0,00 €"); lblRopa.setFont(catValFont); lblRopa.setForeground(UIUtils.TEXT); lblRopa.setHorizontalAlignment(SwingConstants.RIGHT);
        gastosGrid.add(l2); gastosGrid.add(lblRopa);
        JLabel l3 = new JLabel("Tecnología"); l3.setFont(catNameFont); l3.setForeground(UIUtils.TEXT_MUTED);
        lblTecno = new JLabel("0,00 €"); lblTecno.setFont(catValFont); lblTecno.setForeground(UIUtils.TEXT); lblTecno.setHorizontalAlignment(SwingConstants.RIGHT);
        gastosGrid.add(l3); gastosGrid.add(lblTecno);
        JLabel l4 = new JLabel("Salud"); l4.setFont(catNameFont); l4.setForeground(UIUtils.TEXT_MUTED);
        lblSalud = new JLabel("0,00 €"); lblSalud.setFont(catValFont); lblSalud.setForeground(UIUtils.TEXT); lblSalud.setHorizontalAlignment(SwingConstants.RIGHT);
        gastosGrid.add(l4); gastosGrid.add(lblSalud);
        JLabel l5 = new JLabel("Transporte"); l5.setFont(catNameFont); l5.setForeground(UIUtils.TEXT_MUTED);
        lblTransporte = new JLabel("0,00 €"); lblTransporte.setFont(catValFont); lblTransporte.setForeground(UIUtils.TEXT); lblTransporte.setHorizontalAlignment(SwingConstants.RIGHT);
        gastosGrid.add(l5); gastosGrid.add(lblTransporte);
        JLabel l6 = new JLabel("Comida"); l6.setFont(catNameFont); l6.setForeground(UIUtils.TEXT_MUTED);
        lblComida = new JLabel("0,00 €"); lblComida.setFont(catValFont); lblComida.setForeground(UIUtils.TEXT); lblComida.setHorizontalAlignment(SwingConstants.RIGHT);
        gastosGrid.add(l6); gastosGrid.add(lblComida);
        JLabel l7 = new JLabel("Hogar"); l7.setFont(catNameFont); l7.setForeground(UIUtils.TEXT_MUTED);
        lblHogar = new JLabel("0,00 €"); lblHogar.setFont(catValFont); lblHogar.setForeground(UIUtils.TEXT); lblHogar.setHorizontalAlignment(SwingConstants.RIGHT);
        gastosGrid.add(l7); gastosGrid.add(lblHogar);
        JLabel l8 = new JLabel("Educación"); l8.setFont(catNameFont); l8.setForeground(UIUtils.TEXT_MUTED);
        lblEduca = new JLabel("0,00 €"); lblEduca.setFont(catValFont); lblEduca.setForeground(UIUtils.TEXT); lblEduca.setHorizontalAlignment(SwingConstants.RIGHT);
        gastosGrid.add(l8); gastosGrid.add(lblEduca);

        gastosContainer.add(gastosGrid);
        rightPanel.add(gastosContainer, BorderLayout.CENTER);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(UIUtils.BG);

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
            homeTransferenciasTableModel.addRow(new Object[]{
                fila[0], fila[1], String.format("%.2f €", ((Number) fila[2]).doubleValue())
            });
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
        homeUserNameLabel.setText(nuevoNombre);
        String inicial = (nuevoNombre != null && !nuevoNombre.isEmpty())
            ? nuevoNombre.substring(0, 1).toUpperCase() : "?";
        homeAvatarLabel.setText(inicial);
        homeAvatarLabel.repaint();
    }

    public Map<String, Double> getGastosMap() {
        return gastosMap;
    }
}
