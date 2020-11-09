package me.shy.logging.gui;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextPane;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import org.apache.commons.io.FileUtils;

import me.shy.logging.Constants;
import me.shy.logging.FileService;
import me.shy.logging.entity.Command;
import me.shy.logging.entity.Configuration;

public class ComTextPane {

	private static Configuration configuration = MainTable.getConfiguration();
	private static JTextPane textPane = new JTextPane();
	private static TextPopupMenu popupMenu = new TextPopupMenu();
	private static List<Command> commands = null;

	static {

		textPane.addMouseListener(popupMenu);
		textPane.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent ke) {
				super.keyTyped(ke);
				setStyle();
				updateConfiguration();
			}
		});

		initlTextPane();
	}

	private ComTextPane() {

	}

	public static JTextPane getInstance() {
		return textPane;
	}

	public static void initlTextPane() {

		StringBuilder builder = new StringBuilder();
		if (null == (commands = configuration.getCommands())) {
			commands = new ArrayList<Command>();
			textPane.setText("");
		} else {
			for (Command command : commands) {
				String commandString = command.getCommandString() + "\n";
				builder.append(commandString);
			}
			textPane.setText(builder.toString());
			setStyle();
		}
	}

	public static void loadCommands(Configuration configuration) {
		initlTextPane();
	}

	public static void readFile(File file) {
		if (file != null) {
			try {
				textPane.setText(FileUtils.readFileToString(file, "gb2312")
						.replaceAll("\r\n", "\n").trim() + "\n");
				setStyle();
			} catch (IOException e) {
				Constants.LOGGER.error(e.getMessage());
			}
		}
	}

	public static void updateConfiguration() {
		configuration = MainTable.getConfiguration();
		configuration.getCommands().clear();
		String[] lines = textPane.getText().split("\n");
		if (lines != null) {
			for (String str : lines) {
				Command cammand = new Command();
				cammand.setCommandString(str);
				configuration.getCommands().add(cammand);
			}
		}
		File file = new File(Constants.CONFIG_FILE_PATH);
		FileService.exportXML(configuration, file);
	}

	public static void lock() {
		textPane.setEditable(false);
		textPane.setBackground(Color.CYAN);
		textPane.removeMouseListener(popupMenu);
	}

	public static void unLock() {
		textPane.setEditable(true);
		textPane.setBackground(Color.WHITE);
		textPane.addMouseListener(popupMenu);
	}

	public static List<String> getCommandStrings() {
		List<String> commandStrings = new ArrayList<String>();
		if (!textPane.getText().trim().equals("")) {
			String[] lines = textPane.getText().trim().split("\n");
			for (int i = 0; i < lines.length; i++) {
				String line = lines[i];
				if (!line.trim().equals("")) {
					if (line.contains("//")) {
						String newLine = line.substring(0, line.indexOf("//"));
						if (newLine != null && !newLine.trim().equals("")) {
							commandStrings.add(newLine);
						}
					} else if (line.contains("'")) {
						String newLine = line.substring(0, line.indexOf("'"));
						if (newLine != null && !newLine.trim().equals("")) {
							commandStrings.add(newLine);
						}
					} else {
						commandStrings.add(line);
					}
				}
			}
			return commandStrings;
		}
		return null;
	}

	public static void setStyle() {
		DefaultStyledDocument styledDocument = (DefaultStyledDocument) textPane
				.getDocument();
		textPane.setDocument(styledDocument);

		SimpleAttributeSet annotationAttributeSet = new SimpleAttributeSet();
		StyleConstants.setForeground(annotationAttributeSet, new Color(70, 180,
				70));
		StyleConstants.setItalic(annotationAttributeSet, true);
		StyleConstants.setFontSize(annotationAttributeSet, 12);

		SimpleAttributeSet normalAttributeSet = new SimpleAttributeSet();
		StyleConstants.setForeground(normalAttributeSet, Color.BLACK);
		StyleConstants.setItalic(normalAttributeSet, false);
		StyleConstants.setFontSize(normalAttributeSet, 12);

		int offset = 0;
		if (!textPane.getText().equals("")) {
			String[] lines = textPane.getText().split("\n");
			for (int i = 0; i < lines.length; i++) {
				String line = lines[i];
				offset = offset + line.length() + 1;
				if (!line.equals("")) {
					if (line.contains("//")) {
						String annotation = line.substring(line.indexOf("//"),
								line.length());
						styledDocument.setCharacterAttributes(offset
								- annotation.length() - 1, annotation.length(),
								annotationAttributeSet, false);
					} else if (line.contains("'")) {
						String annotation = line.substring(line.indexOf("'"),
								line.length());
						styledDocument.setCharacterAttributes(offset
								- annotation.length() - 1, annotation.length(),
								annotationAttributeSet, false);
					} else {
						styledDocument.setCharacterAttributes(
								offset - line.length() - 1, line.length(),
								normalAttributeSet, false);
					}
				}
			}
		}
	}
}
