package me.shy.logging.event;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import me.shy.logging.Constants;
import me.shy.logging.FileService;
import me.shy.logging.Logging;
import me.shy.logging.entity.BSC;
import me.shy.logging.entity.Configuration;
import me.shy.logging.gui.MainTable;
import me.shy.logging.gui.MainWin;
import me.shy.logging.gui.StatusBar;

public class DeleteAction extends AbstractAction {

	/**描述：*/	
    private static final long serialVersionUID = 1L;

    public DeleteAction() {
		super("Delete", new ImageIcon(Toolkit.getDefaultToolkit().getImage(
				Logging.class.getResource("/icons/delete.png"))));
		putValue(Action.SHORT_DESCRIPTION, Constants.DELETE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		boolean isHasSelected = false;
		JTable table = MainTable.getTable();
		int rowCount = table.getRowCount();
		Configuration configuration = MainTable.getConfiguration();
		for (int i = 0; i < rowCount; i++) {
			if ((Boolean) table.getModel().getValueAt(i, 0)) {
				isHasSelected = true;
			}
		}

		if (!isHasSelected) {
			JOptionPane.showMessageDialog(MainWin.getInstance(),
					Constants.NO_SELECTED_BSC, Constants.NOTIFY_TITLE,
					JOptionPane.ERROR_MESSAGE);
		} else {
			int selected = JOptionPane.showConfirmDialog(MainWin.getInstance(),
					Constants.DEL_SELECTED_BSC, Constants.NOTIFY_TITLE,
					JOptionPane.YES_NO_OPTION);
			if (selected == JOptionPane.YES_OPTION) {
				for (int i = 0; i < rowCount; i++) {
					if ((Boolean) table.getModel().getValueAt(i, 0)) {
						String bscName = (String) table.getValueAt(i, 1);
						String bscIp = (String) table.getValueAt(i, 2);
						String bscType = (String) table.getValueAt(i, 3);
						List<BSC> allBSC = configuration.getBscs();
						for (int j = 0; j < allBSC.size(); j++) {
							BSC bsc = allBSC.get(j);
							if (bsc.getName().equals(bscName)
									&& bsc.getIp().equals(bscIp)
									&& bsc.getType().equals(bscType)) {
								allBSC.remove(bsc);
							}
						}
					}
				}
				StatusBar.getAllItemLabel().setText(
						Constants.TOTAL + configuration.getBscs().size() + " ");
				MainTable.loadTableData(configuration);
				MainTable.repaintTableHeader();
				File file = new File(Constants.CONFIG_FILE_PATH);
				FileService.exportXML(configuration, file);
			}
		}
	}
}
