package me.shy.logging.event;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import me.shy.logging.Constants;
import me.shy.logging.Logging;
import me.shy.logging.entity.Configuration;
import me.shy.logging.gui.MainTable;
import me.shy.logging.gui.MainWin;

public class OpenLogAction extends AbstractAction {

	/**描述：*/	
    private static final long serialVersionUID = 1L;

    public OpenLogAction() {
		super("Open", new ImageIcon(Toolkit.getDefaultToolkit().getImage(
				Logging.class.getResource("/icons/open.png"))));
		putValue(Action.SHORT_DESCRIPTION, Constants.OPEN_LOG_DIR);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Configuration configuration = MainTable.getConfiguration();
		File file = new File(configuration.getSetting().getLogPath());
		try {
			java.awt.Desktop.getDesktop().open(file);
		} catch (IOException e1) {
			Constants.LOGGER.error(e1.getMessage());
		} catch (IllegalArgumentException e1) {
			Constants.LOGGER.error(e1.getMessage());
			JOptionPane.showMessageDialog(MainWin.getInstance(),
					Constants.LOG_DIR_NO_SET, Constants.ERROR_TITLE,
					JOptionPane.ERROR_MESSAGE);
		}
	}

}
