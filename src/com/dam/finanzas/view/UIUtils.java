package com.dam.finanzas.view;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class UIUtils {

    private UIUtils() {}

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
}
