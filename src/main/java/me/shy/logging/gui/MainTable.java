package me.shy.logging.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import me.shy.logging.Constants;
import me.shy.logging.FileService;
import me.shy.logging.entity.BSC;
import me.shy.logging.entity.Configuration;

public class MainTable {
	private static JTable table = new JTable();
	private static final MyCheckBoxRenderer CHECK = new MyCheckBoxRenderer();
	private static DefaultTableModel model;
	private static List<BSC> bscs = null;
	private static Configuration configuration = FileService.initlConfig();
	private static TablePopupMenu popupMenu = new TablePopupMenu();
	private static TableHeaderLisenter headerLisenter;
	private static int counter = 0;

	public static JTable getInstance() {
		initlTableData(configuration);
		setStyle();
		return table;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void setStyle() {

		DefaultTableColumnModel columnModel = (DefaultTableColumnModel) table
				.getColumnModel();
		columnModel.getColumn(0).setMinWidth(22);
		columnModel.getColumn(0).setMaxWidth(22);
		columnModel.getColumn(1).setMinWidth(90);
		columnModel.getColumn(1).setMaxWidth(90);
		columnModel.getColumn(2).setMinWidth(100);
		columnModel.getColumn(2).setMaxWidth(100);
		columnModel.getColumn(3).setMinWidth(75);
		columnModel.getColumn(3).setMaxWidth(75);
		columnModel.getColumn(4).setMinWidth(100);
		columnModel.getColumn(4).setMaxWidth(100);
		TableColumn firstColumn = table.getColumnModel().getColumn(0);
		firstColumn.setCellRenderer(table.getDefaultRenderer(Boolean.class));
		firstColumn.setCellEditor(table.getDefaultEditor(Boolean.class));

		/*DefaultTableCellRenderer fontColoRenderer = new DefaultTableCellRenderer() {
			private final long serialVersionUID = 1L;

			public void setValue(Object value) {
				String contant = (value instanceof String ? value.toString()
						: "");
				if (contant.contains("error")
						|| contant.contains("unreachable")
						|| contant.contains("failed") || contant.contains("错误")
						|| contant.contains("不可达") || contant.contains("失败")){
					setForeground(Color.red);
				} else {
					setForeground(Color.black);
				}
				setText(value == null ? "" : value.toString());
			}
		};
		columnModel.getColumn(5).setCellRenderer(fontColoRenderer);*/
		table.setShowGrid(false);
		table.setIntercellSpacing(new Dimension(0, 0));
		table.getTableHeader().setReorderingAllowed(false);
		table.setRowSelectionAllowed(true);
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.getColumn("").setHeaderRenderer(CHECK);
		headerLisenter = new MainTable().new TableHeaderLisenter();
		RowSorter sorter = new TableRowSorter(model);
		table.setRowSorter(sorter);

		StatusBar.getStatusLabel().setText(Constants.READY);
		StatusBar.getAllItemLabel()
				.setText(Constants.TOTAL + bscs.size() + " ");
		StatusBar.getSelectedLabel()
				.setText(Constants.SELECTED + counter + " ");
		StatusBar.getDoneLabel().setText(Constants.FINISHED + ": 0 ");
		StatusBar.getTextStatusLabel().setText(Constants.UNLOCK);
		MainTable.unLock();

	}

	public static void loadTableData(Configuration configuration) {
		model.setRowCount(0);
		initlTableData(configuration);

	}

	public static void initlTableData(Configuration configuration) {
		bscs = configuration.getBscs();
		if (null == bscs) {
			bscs = new ArrayList<BSC>();
		}
		MyTableModel.setBscs(bscs);
		model = MyTableModel.getInstance();
		table.setModel(model);
	}

	public static void repaintTableHeader() {
		counter = 0;
		if (bscs.size() != 0) {
			for (int i = 0; i < bscs.size(); i++) {
				if (!bscs.get(i).isSelected()) {
					CHECK.setSelected(false);
				} else {
					counter += 1;
				}
			}
			if (counter == table.getRowCount()) {
				CHECK.setSelected(true);
			}
		} else {
			CHECK.setSelected(false);
		}

		table.getTableHeader().repaint();
		StatusBar.getSelectedLabel().setText(Constants.SELECTED + counter);
	}

	public static void clearMsg() {
		for (int i = 0; i < table.getModel().getRowCount(); i++) {
			table.getModel().setValueAt("", i, 4);
			table.getModel().setValueAt("", i, 5);
		}
	}

	public static void lock() {
		table.setSelectionBackground(Color.WHITE);
		table.setSelectionForeground(Color.BLACK);
		table.getTableHeader().removeMouseListener(headerLisenter);
		table.setEnabled(false);
		table.removeMouseListener(popupMenu);
	}

	public static void unLock() {
		table.setSelectionBackground(SystemColor.textHighlight);
		table.setSelectionForeground(Color.WHITE);
		table.getTableHeader().addMouseListener(headerLisenter);
		table.setEnabled(true);
		table.addMouseListener(popupMenu);
	}

	public static final JTable getTable() {
		return table;
	}

	public static final void setTable(JTable table) {
		MainTable.table = table;
	}

	public static final List<BSC> getBscs() {
		return bscs;
	}

	public static final void setBscs(List<BSC> bscs) {
		MainTable.bscs = bscs;
	}

	public static final Configuration getConfiguration() {
		return configuration;
	}

	public static final void setConfiguration(Configuration configuration) {
		MainTable.configuration = configuration;
	}

	private class TableHeaderLisenter extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			int columnIndex = table.columnAtPoint(e.getPoint());
			if (columnIndex == 0) {
				boolean b = !CHECK.isSelected();
				CHECK.setSelected(b);
				table.getTableHeader().repaint();
				for (int i = 0; i < table.getRowCount(); i++) {
					table.getModel().setValueAt(b, i, 0);
					configuration.getBscs().get(i).setSelected(b);
					if (b) {
						StatusBar.getSelectedLabel().setText(
								Constants.SELECTED + table.getRowCount());
					} else {
						StatusBar.getSelectedLabel().setText(
								Constants.SELECTED + " 0");
					}
				}
			}
		}
	}

}
