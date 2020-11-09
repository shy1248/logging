package me.shy.logging.event;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

import me.shy.logging.Constants;
import me.shy.logging.FileService;
import me.shy.logging.Logging;
import me.shy.logging.TxtFileFilter;
import me.shy.logging.gui.ComTextPane;

public class OpenFileAction extends AbstractAction {

	/**描述：*/	
    private static final long serialVersionUID = 1L;

    public OpenFileAction() {
		super("Open", new ImageIcon(Toolkit.getDefaultToolkit().getImage(
				Logging.class.getResource("/icons/open_1.png"))));
		putValue(Action.SHORT_DESCRIPTION, Constants.OPEN);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		File commandfile = FileService.fileOpen(new TxtFileFilter());
		if (null != commandfile) {
			ComTextPane.readFile(commandfile);
			ComTextPane.updateConfiguration();
		}
	}

}
