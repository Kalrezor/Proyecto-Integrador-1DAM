package com.dam.finanzas.view;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class UIUtils {

    // Paleta principal
    public static final Color BG           = new Color(245, 247, 250);
    public static final Color BG_CARD      = Color.WHITE;
    public static final Color BG_TABLE     = new Color(241, 245, 249);
    public static final Color SIDEBAR      = new Color(15, 23, 42);
    public static final Color SIDEBAR_HOVER = new Color(30, 41, 59);
    public static final Color ACCENT       = new Color(59, 130, 246);
    public static final Color ACCENT_DARK  = new Color(37, 99, 235);
    public static final Color SUCCESS      = new Color(22, 163, 74);
    public static final Color DANGER       = new Color(220, 38, 38);
    public static final Color WARNING      = new Color(217, 119, 6);
    public static final Color BORDER       = new Color(226, 232, 240);
    public static final Color TEXT         = new Color(15, 23, 42);
    public static final Color TEXT_MUTED   = new Color(100, 116, 139);

    // Fondos suaves para tarjetas de KPI
    public static final Color BG_SUCCESS = new Color(240, 253, 244);
    public static final Color BG_DANGER  = new Color(254, 242, 242);
    public static final Color BG_ACCENT  = new Color(239, 246, 255);

    private UIUtils() {}

    /** Botón redondeado con efecto hover. bg y fg son el color de fondo y texto. */
    public static JButton crearBoton(String texto, Color bg, Color fg) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2.setColor(bg.darker().darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(bg.darker());
                } else {
                    g2.setColor(bg);
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
            @Override
            protected void paintBorder(Graphics g) {}
        };
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setForeground(fg);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        return btn;
    }

    /** Ajusta anchos de columnas al contenido y reduce alto de fila. */
    public static void ajustarTabla(JTable table) {
        table.setRowHeight(22);
        table.setFillsViewportHeight(true);
        for (int col = 0; col < table.getColumnCount(); col++) {
            TableColumn tc = table.getColumnModel().getColumn(col);
            TableCellRenderer hr = table.getTableHeader().getDefaultRenderer();
            Component hc = hr.getTableCellRendererComponent(table, tc.getHeaderValue(), false, false, -1, col);
            int w = hc.getPreferredSize().width + 16;
            for (int row = 0; row < table.getRowCount(); row++) {
                Component c = table.prepareRenderer(table.getCellRenderer(row, col), row, col);
                w = Math.max(w, c.getPreferredSize().width + 12);
            }
            tc.setPreferredWidth(w);
        }
    }

    /** Aplica estilo base a un JComboBox (fondo, fuente, color de texto). */
    public static void estilizarComboBox(JComboBox<?> combo) {
        combo.setBackground(BG_CARD);
        combo.setForeground(TEXT);
        combo.setFont(new Font("Arial", Font.PLAIN, 13));
        combo.setFocusable(false);
    }

    /**
     * Renderer para los items del desplegable de cualquier JComboBox.
     * El texto se toma de value.toString(). Para tipos personalizados,
     * llama a super y luego sobreescribe setText().
     */
    public static DefaultListCellRenderer rendererComboBox() {
        return new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setFont(new Font("Arial", Font.PLAIN, 13));
                setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                setBackground(isSelected ? BG_ACCENT : BG_CARD);
                setForeground(isSelected ? ACCENT : TEXT);
                return this;
            }
        };
    }

    /**
     * Envuelve un JComboBox en un JPanel con borde UIUtils.BORDER.
     * Añade el resultado al layout; el combo sigue siendo la referencia funcional.
     */
    public static JPanel wrapComboBox(JComboBox<?> combo) {
        combo.setBorder(BorderFactory.createEmptyBorder(1, 2, 1, 2));
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(BG_CARD);
        wrapper.setBorder(BorderFactory.createLineBorder(BORDER));
        wrapper.add(combo, BorderLayout.CENTER);
        return wrapper;
    }

    /** Aplica estilos de color modernos a una JTable. */
    public static void estilizarTabla(JTable table) {
        table.setBackground(BG_CARD);
        table.setGridColor(BORDER);
        table.setSelectionBackground(new Color(219, 234, 254));
        table.setSelectionForeground(TEXT);
        JTableHeader header = table.getTableHeader();
        header.setBackground(BG_TABLE);
        header.setForeground(TEXT_MUTED);
        header.setFont(new Font("Arial", Font.BOLD, 12));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER));
    }
}
