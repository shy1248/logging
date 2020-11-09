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
import me.shy.logging.gui.MainWin;

public class OpenConfigAction extends AbstractAction {

	/**描述：*/	
    private static final long serialVersionUID = 1L;

    public OpenConfigAction() {
		super("ConfigFile", new ImageIcon(Toolkit.getDefaultToolkit().getImage(
				Logging.class.getClass().getResource("/icons/config.png"))));
		putValue(Action.SHORT_DESCRIPTION, Constants.OPEN_CONFIG_FILE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		try {
			File file = new File(Constants.CONFIG_FILE_PATH);
			java.awt.Desktop.getDesktop().open(file);
		} catch (IOException e1) {
			Constants.LOGGER.error(e1.getMessage());
		} catch (IllegalArgumentException e1) {
			Constants.LOGGER.error(e1.getMessage());
			JOptionPane.showMessageDialog(MainWin.getInstance(),
					Constants.CONFIG_FILE_NOT_FOUND, Constants.ERROR_TITLE,
					JOptionPane.ERROR_MESSAGE);
		}
	}

}
