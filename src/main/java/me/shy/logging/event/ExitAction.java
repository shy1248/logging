package me.shy.logging.event;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import me.shy.logging.Constants;
import me.shy.logging.Logging;
import me.shy.logging.gui.MainWin;
import me.shy.logging.gui.ToolBar;

public class ExitAction extends AbstractAction {

	/**描述：*/	
    private static final long serialVersionUID = 1L;

    public ExitAction() {
		super("Exit", new ImageIcon(Toolkit.getDefaultToolkit().getImage(
				Logging.class.getClass().getResource("/icons/exit.png"))));
		putValue(Action.SHORT_DESCRIPTION, Constants.EXIT);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!ToolBar.getRunAction().isEnabled()) {
			int selected = JOptionPane.showConfirmDialog(MainWin.getInstance(),
					Constants.EXIT_TASK, Constants.NOTIFY_TITLE,
					JOptionPane.YES_NO_OPTION);
			if (selected == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		} else {
			System.exit(0);
		}
	}
}
