package me.shy.logging.gui;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;

import me.shy.logging.Constants;
import me.shy.logging.Logging;
import me.shy.logging.event.ExitAction;
import me.shy.logging.event.ExportAction;
import me.shy.logging.event.ImportAction;
import me.shy.logging.event.OpenFileAction;
import me.shy.logging.event.SaveFileAction;

public class MenuItem {
	private static List<JMenuItem> fileMenuItems = new ArrayList<JMenuItem>();
	private static List<JMenuItem> settingMenuItems = new ArrayList<JMenuItem>();
	private static List<JMenuItem> helpMenuItems = new ArrayList<JMenuItem>();
	private static JMenuItem openItem;
	private static JMenuItem saveItem;
	private static JMenuItem importItem;
	private static JMenuItem exportItem;
	private static JMenuItem exitItem;
	private static JMenuItem configurationItem;
	private static JMenuItem aboutItem;

	static {
		openItem = new JMenuItem(Constants.OPEN_FILE + "     ");
		openItem.addActionListener(new OpenFileAction());
		saveItem = new JMenuItem(Constants.SAVE_FILE + "     ");
		saveItem.addActionListener(new SaveFileAction());
		importItem = new JMenuItem(Constants.IMPORT_FILE + "     ");
		importItem.addActionListener(new ImportAction());
		exportItem = new JMenuItem(Constants.EXPORT_FILE + "     ");
		exportItem.addActionListener(new ExportAction());
		exitItem = new JMenuItem(Constants.EXIT_FILE + "     ");
		exitItem.addActionListener(new ExitAction());
		configurationItem = new JMenuItem(Constants.CONFIG + "      ",
				new ImageIcon(Toolkit.getDefaultToolkit().getImage(
						Logging.class.getResource("/icons/setting.png"))));
		configurationItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ConfigWin.getInstance().getConfigDialog().setVisible(true);
			}
		});
		aboutItem = new JMenuItem(Constants.ABOUT + "     ", new ImageIcon(
				Toolkit.getDefaultToolkit().getImage(
						Logging.class.getClass().getResource(
								"/icons/help.png"))));

		aboutItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AboutWin.getInstance().getAboutWin().setVisible(true);
			}
		});
		fileMenuItems.add(openItem);
		fileMenuItems.add(saveItem);
		fileMenuItems.add(importItem);
		fileMenuItems.add(exportItem);
		fileMenuItems.add(exitItem);
		settingMenuItems.add(configurationItem);
		helpMenuItems.add(aboutItem);
	}

	public static final List<JMenuItem> getFileMenuItems() {
		return fileMenuItems;
	}

	public static final void setFileMenuItems(List<JMenuItem> fileMenuItems) {
		MenuItem.fileMenuItems = fileMenuItems;
	}

	public static final List<JMenuItem> getSettingMenuItems() {
		return settingMenuItems;
	}

	public static final void setSettingMenuItems(
			List<JMenuItem> settingMenuItems) {
		MenuItem.settingMenuItems = settingMenuItems;
	}

	public static final List<JMenuItem> getHelpMenuItems() {
		return helpMenuItems;
	}

	public static final void setHelpMenuItems(List<JMenuItem> helpMenuItems) {
		MenuItem.helpMenuItems = helpMenuItems;
	}

	public static final JMenuItem getOpenItem() {
		return openItem;
	}

	public static final void setOpenItem(JMenuItem openItem) {
		MenuItem.openItem = openItem;
	}

	public static final JMenuItem getSaveItem() {
		return saveItem;
	}

	public static final void setSaveItem(JMenuItem saveItem) {
		MenuItem.saveItem = saveItem;
	}

	public static final JMenuItem getImportItem() {
		return importItem;
	}

	public static final void setImportItem(JMenuItem importItem) {
		MenuItem.importItem = importItem;
	}

	public static final JMenuItem getExportItem() {
		return exportItem;
	}

	public static final void setExportItem(JMenuItem exportItem) {
		MenuItem.exportItem = exportItem;
	}

	public static final JMenuItem getExitItem() {
		return exitItem;
	}

	public static final void setExitItem(JMenuItem exitItem) {
		MenuItem.exitItem = exitItem;
	}

	public static final JMenuItem getConfigurationItem() {
		return configurationItem;
	}

	public static final void setConfigurationItem(JMenuItem configurationItem) {
		MenuItem.configurationItem = configurationItem;
	}

	public static final JMenuItem getAboutItem() {
		return aboutItem;
	}

	public static final void setAboutItem(JMenuItem aboutItem) {
		MenuItem.aboutItem = aboutItem;
	}

}
