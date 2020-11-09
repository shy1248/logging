package me.shy.logging.gui;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class MyCheckBoxRenderer extends JCheckBox implements TableCellRenderer {

	/**描述：*/	
    private static final long serialVersionUID = 1L;

    public MyCheckBoxRenderer() {
		this.setBorderPainted(false);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		return this;
	}

}
