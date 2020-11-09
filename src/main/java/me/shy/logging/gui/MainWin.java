package me.shy.logging.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import me.shy.logging.Constants;

public class MainWin extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final MainWin mainWin = new MainWin();
	private static JSplitPane pane;
	private static JTextPane textArea;
	private static JTable table;

	private MainWin() {

	}

	public static MainWin getInstance() {
		return mainWin;
	}

	private void IntiGlobalFont(Font font){
		FontUIResource fontUIResource = new FontUIResource(font);
		for(Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();){
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof FontUIResource) {
				UIManager.put(key, fontUIResource);
			}
		}
	}
	
	public void setStyle() {

		IntiGlobalFont(new Font("Microsoft Yahei Font", Font.PLAIN, 12));
		
		this.setPreferredSize(new Dimension(700, 700));
		this.setMinimumSize(getPreferredSize());
		// this.setResizable(false);
		int frameWidth = this.getPreferredSize().width;
		int frameHeight = this.getPreferredSize().height;
		this.setSize(frameWidth, frameHeight);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = this.getSize();
		if (frameSize.height > screenSize.height)
			frameSize.height = screenSize.height;
		if (frameSize.width > screenSize.width)
			frameSize.width = screenSize.width;
		this.setLocation((screenSize.width - frameSize.width) / 2,
				(screenSize.height - frameSize.height) / 2);
		this.setTitle("Logging1.07-64bit");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(
				this.getClass().getResource("/icons/loggingicon.png")));
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addItems();
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				if (!ToolBar.getRunAction().isEnabled()) {
					int selected = JOptionPane.showConfirmDialog(
							MainWin.getInstance(), Constants.EXIT_TASK,
							Constants.NOTIFY_TITLE, JOptionPane.YES_NO_OPTION);
					if (selected == JOptionPane.YES_OPTION) {
						System.exit(1);
					}
				} else {
					System.exit(1);
				}
			}
		});
		this.setVisible(true);
	}

	public void addItems() {
		this.setJMenuBar(new MenuBar().getMenuBar());
		this.add(new ToolBar().getInstance(), BorderLayout.NORTH);
		pane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
		pane.setDividerLocation(180);
		this.add(pane, BorderLayout.CENTER);
		this.add(StatusBar.getInstance(), BorderLayout.SOUTH);
		textArea = ComTextPane.getInstance();
		JScrollPane textScrollPane = new JScrollPane(textArea);
		textScrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		pane.add(textScrollPane, JSplitPane.TOP);
		table = MainTable.getInstance();
		JScrollPane tableJScrollPane = new JScrollPane();
		tableJScrollPane.setViewportView(table);
		tableJScrollPane.getViewport().setBackground(Color.WHITE);
		tableJScrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		pane.add(tableJScrollPane, JSplitPane.BOTTOM);
	}

}
