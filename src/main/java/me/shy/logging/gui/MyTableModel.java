package me.shy.logging.gui;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import me.shy.logging.entity.BSC;

public class MyTableModel extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String[] columns = new String[] { "", "BSC_NAME",
			"IP_ADDRESS", "BSC_TYPE", "CONNECTION", "MESSAGE" };
	private static List<BSC> bscs = null;
	@SuppressWarnings("serial")
	private static DefaultTableModel tableModel = new DefaultTableModel(
			new Object[][] {}, columns) {
		@SuppressWarnings("unused")
		private final long serialVersionUID = 1L;

		public boolean isCellEditable(int row, int column) {
			if (column == 0) {
				return true;
			} else {
				return false;
			}
		}
	};

	private MyTableModel() {

	}

	public synchronized static DefaultTableModel getInstance() {
		initlTableModel();
		return tableModel;

	}

	public static void initlTableModel() {

		Object[] rowData = new Object[columns.length];
		for (int i = 0; i < bscs.size(); i++) {
			rowData[0] = bscs.get(i).isSelected() ? true : false;
			rowData[1] = bscs.get(i).getName();
			rowData[2] = bscs.get(i).getIp();
			rowData[3] = bscs.get(i).getType();
			rowData[4] = bscs.get(i).getConnectionInfro();
			rowData[5] = bscs.get(i).getCurrectCmd();
			tableModel.addRow(rowData);
		}

	}

	public static final List<BSC> getBscs() {
		return bscs;
	}

	public static final void setBscs(List<BSC> bscs) {
		MyTableModel.bscs = bscs;
	}
}
