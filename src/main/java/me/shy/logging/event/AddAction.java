package me.shy.logging.event;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

import me.shy.logging.Constants;
import me.shy.logging.Logging;
import me.shy.logging.gui.AddBscWin;

public class AddAction extends AbstractAction {

	/**描述：*/	
    private static final long serialVersionUID = 1L;

	public AddAction() {
		super("Add", new ImageIcon(Toolkit.getDefaultToolkit().getImage(
				Logging.class.getResource("/icons/create.png"))));
		putValue(Action.SHORT_DESCRIPTION, Constants.ADD);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		AddBscWin.getInstance().getAddBscWin().setVisible(true);
		;
	}

}
