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
import me.shy.logging.XmlFileFilter;
import me.shy.logging.entity.Command;
import me.shy.logging.entity.Configuration;
import me.shy.logging.gui.ComTextPane;
import me.shy.logging.gui.MainTable;

@SuppressWarnings("serial")
public class ExportAction extends AbstractAction {

	public ExportAction() {
		super("Export", new ImageIcon(Toolkit.getDefaultToolkit().getImage(
				Logging.class.getResource("/icons/export.png"))));
		putValue(Action.SHORT_DESCRIPTION, Constants.EXPORT);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		File file = FileService.fileSave(new XmlFileFilter());
		if (file != null) {
			Configuration configuration = MainTable.getConfiguration();
			configuration.getCommands().clear();
			String[] commandStrings = ComTextPane.getInstance().getText()
					.split("\n");
			if (commandStrings != null) {
				for (String str : commandStrings) {
					Command cammand = new Command();
					cammand.setCommandString(str);
					configuration.getCommands().add(cammand);
				}
			}
			FileService.exportXML(configuration, file);
			FileService.fileCopy(file, new File(Constants.CONFIG_FILE_PATH));
		}
	}

}
