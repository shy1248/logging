package me.shy.logging.event;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

import org.apache.commons.io.FileUtils;

import me.shy.logging.Constants;
import me.shy.logging.FileService;
import me.shy.logging.Logging;
import me.shy.logging.TxtFileFilter;
import me.shy.logging.gui.ComTextPane;

/**
 * @author : Yu Shuibo
 * @fileName : me.shy.log.event.SaveFileAction.java
 * 
 * 
 * 
 *           date | author | version | May 29, 2014 | Yu Shuibo | 1.0 |
 * 
 * @describe :
 * 
 *           ALL RIGHTS RESERVED,COPYRIGHT(C) FCH LIMITED 2014
 */

public class SaveFileAction extends AbstractAction {

	/**描述：*/	
    private static final long serialVersionUID = 1L;

    public SaveFileAction() {
		super("Save", new ImageIcon(Toolkit.getDefaultToolkit().getImage(
				Logging.class.getResource("/icons/save.png"))));
		putValue(Action.SHORT_DESCRIPTION, Constants.SAVE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		File file = FileService.fileSave(new TxtFileFilter());
		if (null != file) {
			try {
				FileUtils.writeStringToFile(file, ComTextPane.getInstance()
						.getText().replaceAll("\n", "\r\n"));
			} catch (IOException e1) {
				Constants.LOGGER.error(e1.getMessage());
			}
		}
	}

}
