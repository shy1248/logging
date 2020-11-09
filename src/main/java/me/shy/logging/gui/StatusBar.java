package me.shy.logging.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

@SuppressWarnings("serial")
public class StatusBar extends JPanel {

	private static JToolBar statusBar;
	private static JToolBar msgBar;
	private static JLabel timeLable;
	private static JLabel allItemLabel;
	private static JLabel selectedLabel;
	private static JLabel doneLabel;
	private static JLabel textStatusLabel;
	private static JLabel statusLabel;
	private static JLabel proressLabel;

	// private JProgressBar progressBar;

	private StatusBar() {

		this.setLayout(new BorderLayout());

		statusBar = new JToolBar();
		msgBar = new JToolBar();
		timeLable = new JLabel();
		timeLable.setOpaque(true);
		timeLable.setBackground(Color.GREEN);
		timeLable.setText(" 00:00:00 ");
		allItemLabel = new JLabel();
		selectedLabel = new JLabel();
		doneLabel = new JLabel();
		textStatusLabel = new JLabel();
		textStatusLabel.setOpaque(true);
		textStatusLabel.setBackground(Color.YELLOW);
		statusLabel = new JLabel();
		proressLabel = new JLabel();
		// progressBar = new JProgressBar();
		// progressBar.setMaximumSize(new Dimension(240, 20));
		// progressBar.setStringPainted(true);
		// progressBar.setVisible(false);

		statusBar.add(statusLabel);
		statusBar.add(proressLabel);
		// statusBar.add(progressBar);
		msgBar.add(timeLable);
		msgBar.addSeparator();
		msgBar.add(allItemLabel);
		msgBar.addSeparator();
		msgBar.add(selectedLabel);
		msgBar.addSeparator();
		msgBar.add(doneLabel);
		msgBar.addSeparator();
		msgBar.add(textStatusLabel);
		msgBar.addSeparator();
		statusBar.setFloatable(false);
		msgBar.setFloatable(false);
		// this.setPreferredSize(new Dimension(600, 20));
		this.add(statusBar, BorderLayout.CENTER);
		this.add(msgBar, BorderLayout.EAST);

	}

	public static StatusBar getInstance() {
		return new StatusBar();
	}

	public static final JToolBar getStatusBar() {
		return statusBar;
	}

	public static final void setStatusBar(JToolBar statusBar) {
		StatusBar.statusBar = statusBar;
	}

	public static final JToolBar getMsgBar() {
		return msgBar;
	}

	public static final void setMsgBar(JToolBar msgBar) {
		StatusBar.msgBar = msgBar;
	}

	public static final JLabel getAllItemLabel() {
		return allItemLabel;
	}

	public static final void setAllItemJLabel(JLabel allItemLabel) {
		StatusBar.allItemLabel = allItemLabel;
	}

	public static final JLabel getSelectedLabel() {
		return selectedLabel;
	}

	public static final void setSelectedLabel(JLabel selectedLabel) {
		StatusBar.selectedLabel = selectedLabel;
	}

	public static final JLabel getDoneLabel() {
		return doneLabel;
	}

	public static final void setDoneLabel(JLabel doneLabel) {
		StatusBar.doneLabel = doneLabel;
	}

	public static final JLabel getTextStatusLabel() {
		return textStatusLabel;
	}

	public static final void setTextStatusLabel(JLabel textStatusLabel) {
		StatusBar.textStatusLabel = textStatusLabel;
	}

	public static final JLabel getStatusLabel() {
		return statusLabel;
	}

	public static final void setStatusLabel(JLabel statusLabel) {
		StatusBar.statusLabel = statusLabel;
	}

	public static final JLabel getTimeLable() {
		return timeLable;
	}

	public static final void setTimeLable(JLabel timeLable) {
		StatusBar.timeLable = timeLable;
	}

	public static final void setAllItemLabel(JLabel allItemLabel) {
		StatusBar.allItemLabel = allItemLabel;
	}

	public static final JLabel getProressLabel() {
		return proressLabel;
	}

	public static final void setProressLabel(JLabel proressLabel) {
		StatusBar.proressLabel = proressLabel;
	}

	/*
	 * public static final JProgressBar getProgressBar() { return progressBar; }
	 * 
	 * public static final void setProgressBar(JProgressBar progressBar) {
	 * StatusBar.progressBar = progressBar; }
	 */

}
