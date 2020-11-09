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
import me.shy.logging.FileService;
import me.shy.logging.Logging;
import me.shy.logging.XmlFileFilter;
import me.shy.logging.gui.MainWin;

public class ImportAction extends AbstractAction {

	/**描述：*/	
    private static final long serialVersionUID = 1L;

    public ImportAction() {
		super("Import", new ImageIcon(Toolkit.getDefaultToolkit().getImage(
				Logging.class.getResource("/icons/import.png"))));
		putValue(Action.SHORT_DESCRIPTION, Constants.IMPORT);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		File configFile = FileService.fileOpen(new XmlFileFilter());

		if (null != configFile) {
			if (null != FileService.importXML(configFile)) {

				int selected = JOptionPane.showConfirmDialog(
						MainWin.getInstance(), Constants.RESTART,
						Constants.NOTIFY_TITLE, JOptionPane.YES_NO_OPTION);
				if (selected != JOptionPane.YES_OPTION) {
					return;
				}

				// for exe
				FileService.fileCopy(configFile, new File(
						Constants.CONFIG_FILE_PATH));
				try {
					String cmd = "\"" + Logging.getExeFilePath() + "\"";
					ProcessBuilder processBuilder = new ProcessBuilder(cmd);
					processBuilder.start();
					// Runtime.getRuntime().exec(cmd);
				} catch (IOException e1) {
					Constants.LOGGER.error(e1.getMessage());
				}

				/*
				 * for jar try { FileService.fileCopy(configFile, new File(
				 * Constants.CONFIG_FILE_PATH)); String path =
				 * URLDecoder.decode((this.getClass()
				 * .getProtectionDomain().getCodeSource()
				 * .getLocation().toString()), "utf-8"); String targetFileName =
				 * path.substring(path .lastIndexOf("/") + 1); ProcessBuilder
				 * processBuilder = new ProcessBuilder("java", "-jar",
				 * targetFileName); processBuilder.directory(new
				 * File(path.substring(6, path.lastIndexOf("/"))));
				 * processBuilder.start(); } catch (IOException e1) {
				 * Constants.LOGGER.error(e1.getMessage()); }
				 */
				System.exit(0);
			}
		}
	}
}
