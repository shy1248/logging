package me.shy.logging.gui;

import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import me.shy.logging.Constants;

public class MenuBar {
	private List<JMenu> menus = new ArrayList<JMenu>();

	public JMenuBar getMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu(Constants.FILE);
		JMenu sittingMenu = new JMenu(Constants.SETTING);
		JMenu helpMenu = new JMenu(Constants.HELP);

		for (JMenuItem item : MenuItem.getFileMenuItems()) {
			fileMenu.add(item);
		}
		fileMenu.insertSeparator(2);
		fileMenu.insertSeparator(5);
		for (JMenuItem item : MenuItem.getSettingMenuItems()) {
			sittingMenu.add(item);
		}
		for (JMenuItem item : MenuItem.getHelpMenuItems()) {
			helpMenu.add(item);
		}

		menus.add(fileMenu);
		menus.add(sittingMenu);
		menus.add(helpMenu);
		for (JMenu menu : menus) {
			menuBar.add(menu);
			menu.setMargin(new Insets(0, 12, 0, 12));
		}
		return menuBar;
	}

	public static void setRunningStatus() {
		MenuItem.getOpenItem().setEnabled(false);
		MenuItem.getImportItem().setEnabled(false);
		MenuItem.getExportItem().setEnabled(false);
		MenuItem.getConfigurationItem().setEnabled(false);
	}

	public static void setNormalStatus() {
		MenuItem.getOpenItem().setEnabled(true);
		MenuItem.getImportItem().setEnabled(true);
		MenuItem.getExportItem().setEnabled(true);
		MenuItem.getConfigurationItem().setEnabled(true);
	}
}
