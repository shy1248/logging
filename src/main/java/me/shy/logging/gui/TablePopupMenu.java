package me.shy.logging.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

import me.shy.logging.Constants;
import me.shy.logging.entity.Configuration;

public class TablePopupMenu extends MouseAdapter {

	private static Configuration configuration = MainTable.getConfiguration();
	private static final JTable TABLE = MainTable.getTable();
	private static JPopupMenu menu = new JPopupMenu();
	private static JMenuItem selectItem = new JMenuItem(Constants.SELECT);
	private static JMenuItem unSelectItem = new JMenuItem(Constants.UNSELECT);
	private static JMenuItem selectAllItem = new JMenuItem(Constants.SELECT_ALL);
	private static JMenuItem selectZeroItem = new JMenuItem(Constants.SELECT_ZERO);

	public static JPopupMenu getMenu() {

		selectItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < TABLE.getRowCount(); i++) {
					if (TABLE.isRowSelected(i)) {
						TABLE.getModel().setValueAt(true, i, 0);
						configuration.getBscs().get(i).setSelected(true);
					}
				}
				MainTable.repaintTableHeader();
			}
		});

		unSelectItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < TABLE.getRowCount(); i++) {
					if (TABLE.isRowSelected(i)) {
						TABLE.getModel().setValueAt(false, i, 0);
						configuration.getBscs().get(i).setSelected(false);
					}
				}
				MainTable.repaintTableHeader();
			}
		});

		selectAllItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < TABLE.getRowCount(); i++) {
					TABLE.getModel().setValueAt(true, i, 0);
					configuration.getBscs().get(i).setSelected(true);
				}
				MainTable.repaintTableHeader();
			}
		});

		selectZeroItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < TABLE.getRowCount(); i++) {
					TABLE.getModel().setValueAt(false, i, 0);
					configuration.getBscs().get(i).setSelected(false);
				}
				MainTable.repaintTableHeader();
			}
		});

		menu.add(selectItem);
		menu.add(unSelectItem);
		menu.add(selectAllItem);
		menu.add(selectZeroItem);
		return menu;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			JTable table = (JTable) e.getComponent();

			int row = table.rowAtPoint(e.getPoint());
			if (row == -1) {
				return;
			}

			int[] rows = table.getSelectedRows();
			boolean isSelected = false;

			for (int r : rows) {
				if (row == r) {
					isSelected = true;
					break;
				}
			}

			if (!isSelected) {
				table.setRowSelectionInterval(row, row);
			}

			getMenu().show(table, e.getX(), e.getY());
		}

		if (e.getButton() == MouseEvent.BUTTON1) {
			JTable table = (JTable) e.getComponent();
			int columnIndex = table.columnAtPoint(e.getPoint());
			int rowIndex = table.rowAtPoint(e.getPoint());
			if (columnIndex == 0) {

				if ((Boolean) table.getModel().getValueAt(rowIndex, 0) == true) {
					configuration.getBscs().get(rowIndex).setSelected(true);
				} else {
					configuration.getBscs().get(rowIndex).setSelected(false);
				}
				MainTable.repaintTableHeader();
			}
		}

	}
}
