package me.shy.logging.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Locale;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import me.shy.logging.Constants;
import me.shy.logging.FileService;
import me.shy.logging.Logging;
import me.shy.logging.entity.Configuration;
import me.shy.logging.entity.Setting;

public class ConfigWin extends JDialog {
	private static final long serialVersionUID = 1L;
	private static String defaultLanguage = Locale.getDefault().getLanguage();
	private static final ConfigWin configWin = new ConfigWin();
	private static JPanel bscPanel;
	private static JPanel routerPanel;
	private static JPanel logPathPanel;
	private static JPanel otherPanel;
	private static JPanel bottomPanel;
	private static JPanel downPanel;
	private static JLabel bscUserNameQLabel;
	private static JLabel bscUserNameALabel;
	private static JLabel bscPasswordQLabel;
	private static JLabel bscPasswordALabel;
	private static JTextField bscUserNameQField;
	private static JTextField bscUserNameAField;
	private static JTextField bscPasswordQField;
	private static JPasswordField bscPasswordAField;
	private static JLabel routerIpLabel;
	private static JLabel promptLabel;
	private static JLabel routerUserNameQLabel;
	private static JLabel routerUserNameALabel;
	private static JLabel routerPasswordQLabel;
	private static JLabel routerPasswordALabel;
	private static JTextField routerIpField;
	private static JTextField promptField;
	private static JTextField routerUsernameQField;
	private static JTextField routerUsernameAField;
	private static JTextField routerPasswordQField;
	private static JPasswordField routerPasswordAField;
	private static JTextField pathField;
	private static JCheckBox logCheckBox;
	private static JButton browseButton;
	private static JCheckBox failCheckBox;
	private static JLabel threadlJLabel;
	private static JSpinner threadSpinner;
	private static JButton okButton;
	private static JButton cancelButton;
	private static JCheckBox showCheckBox;
	private static Configuration configuration;
	private static final int fieldSize = 12;
	private static final Dimension labelSzie = new Dimension(60, 20);
	private static JLabel blankLabel;
	static {
		bscPanel = new JPanel();
		bscPanel.setBorder(BorderFactory.createTitledBorder("BSC"));
		bscUserNameQLabel = new JLabel(Constants.USERNAME + "(Q):");
		bscUserNameQLabel.setPreferredSize(labelSzie);
		bscUserNameALabel = new JLabel(Constants.USERNAME + "(A):");
		bscUserNameALabel.setPreferredSize(labelSzie);
		bscPasswordQLabel = new JLabel(Constants.PASSWORD + "(Q):");
		bscPasswordQLabel.setPreferredSize(labelSzie);
		bscPasswordALabel = new JLabel(Constants.PASSWORD + "(A):");
		bscPasswordALabel.setPreferredSize(labelSzie);
		bscUserNameQField = new JTextField();
		bscUserNameQField.setColumns(fieldSize);
		bscUserNameAField = new JTextField();
		bscUserNameAField.setColumns(fieldSize);
		bscPasswordQField = new JTextField();
		bscPasswordQField.setColumns(fieldSize);
		bscPasswordAField = new JPasswordField();
		bscPasswordAField.setColumns(fieldSize);
		bscPasswordAField.copy();
		bscPasswordAField.cut();
		bscPasswordAField.setEchoChar('*');
		bscPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 3));
		bscPanel.setPreferredSize(new Dimension(350, 60));
		bscPanel.add(bscUserNameQLabel);
		bscPanel.add(bscUserNameQField);
		bscPanel.add(bscUserNameALabel);
		bscPanel.add(bscUserNameAField);
		bscPanel.add(bscPasswordQLabel);
		bscPanel.add(bscPasswordQField);
		bscPanel.add(bscPasswordALabel);
		bscPanel.add(bscPasswordAField);

		routerPanel = new JPanel();
		routerPanel.setBorder(BorderFactory.createTitledBorder("Router(OMC)"));
		routerIpLabel = new JLabel("Router IP:");
		routerIpLabel.setPreferredSize(labelSzie);
		promptLabel = new JLabel("Prompt:");
		promptLabel.setPreferredSize(labelSzie);
		routerUserNameQLabel = new JLabel(Constants.USERNAME + "(Q):");
		routerUserNameQLabel.setPreferredSize(labelSzie);
		routerUserNameALabel = new JLabel(Constants.USERNAME + "(A):");
		routerUserNameALabel.setPreferredSize(labelSzie);
		routerPasswordQLabel = new JLabel(Constants.PASSWORD + "(Q):");
		routerPasswordQLabel.setPreferredSize(labelSzie);
		routerPasswordALabel = new JLabel(Constants.PASSWORD + "(A):");
		routerPasswordALabel.setPreferredSize(labelSzie);
		routerIpField = new JTextField();
		routerIpField.setColumns(fieldSize);
		promptField = new JTextField();
		promptField.setColumns(fieldSize);
		routerUsernameQField = new JTextField();
		routerUsernameQField.setColumns(fieldSize);
		routerUsernameAField = new JTextField();
		routerUsernameAField.setColumns(fieldSize);
		routerPasswordQField = new JTextField();
		routerPasswordQField.setColumns(fieldSize);
		routerPasswordAField = new JPasswordField();
		routerPasswordAField.setColumns(fieldSize);
		routerPasswordAField.copy();
		routerPasswordAField.cut();
		routerPasswordAField.setEchoChar('*');
		routerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 3));
		routerPanel.setPreferredSize(new Dimension(350, 90));
		routerPanel.add(routerIpLabel);
		routerPanel.add(routerIpField);
		routerPanel.add(promptLabel);
		routerPanel.add(promptField);
		routerPanel.add(routerUserNameQLabel);
		routerPanel.add(routerUsernameQField);
		routerPanel.add(routerUserNameALabel);
		routerPanel.add(routerUsernameAField);
		routerPanel.add(routerPasswordQLabel);
		routerPanel.add(routerPasswordQField);
		routerPanel.add(routerPasswordALabel);
		routerPanel.add(routerPasswordAField);

		logPathPanel = new JPanel();
		logPathPanel.setBorder(BorderFactory.createTitledBorder("LogPath"));
		pathField = new JTextField();
		if(defaultLanguage.equals("zh")){
			pathField.setColumns(32);
		}else {
			pathField.setColumns(30);
		}
		
		browseButton = new JButton(Constants.BROWSE);
		browseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser parseDir = new JFileChooser();
				parseDir.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				parseDir.setAcceptAllFileFilterUsed(false);
				int result = parseDir.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					pathField.setText(parseDir.getSelectedFile().toString());
				}
			}
		});
		logCheckBox = new JCheckBox(Constants.SINGLE_LOG);
		logPathPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 3));
		logPathPanel.setPreferredSize(new Dimension(350, 60));
		logPathPanel.add(pathField);
		logPathPanel.add(browseButton);
		logPathPanel.add(logCheckBox);

		otherPanel = new JPanel();
		otherPanel.setBorder(BorderFactory.createTitledBorder(Constants.OTHER));
		failCheckBox = new JCheckBox(Constants.STOP);
//		failCheckBox.setPreferredSize(new Dimension(320, 20));
		threadlJLabel = new JLabel(Constants.THREAD_NUM);
		threadSpinner = new JSpinner();
		threadSpinner.setPreferredSize(new Dimension(36, 20));
		threadSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				JSpinner spinner = (JSpinner) e.getSource();
				if ((Integer) spinner.getValue() <= 1) {
					spinner.setValue(1);
				} else if ((Integer) spinner.getValue() >= 49) {
					spinner.setValue(49);
				}
			}
		});
		otherPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 3));
		otherPanel.setPreferredSize(new Dimension(350, 32));
		otherPanel.add(failCheckBox);
		blankLabel = new JLabel();
		if(defaultLanguage.equals("zh")){
			blankLabel.setPreferredSize(new Dimension(180, 20));
		}else {
			blankLabel.setPreferredSize(new Dimension(60, 20));
		}
		
		otherPanel.add(blankLabel);
		otherPanel.add(threadlJLabel);
		otherPanel.add(threadSpinner);

		bottomPanel = new JPanel();
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
		bottomPanel.setPreferredSize(new Dimension(350, 10));
		bottomPanel.add(bscPanel);
		bottomPanel.add(routerPanel);
		bottomPanel.add(logPathPanel);
		bottomPanel.add(otherPanel);
		showCheckBox = new JCheckBox(Constants.SHOW_PASSWD);
//		showCheckBox.setPreferredSize(new Dimension(150, 20));
		showCheckBox.setMargin(new Insets(5, 0, 5, 0));
		showCheckBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				JCheckBox checkBox = (JCheckBox) e.getSource();
				if (!checkBox.isSelected()) {
					bscPasswordAField.setEchoChar('*');
					routerPasswordAField.setEchoChar('*');
				} else {
					bscPasswordAField.setEchoChar('\0');
					routerPasswordAField.setEchoChar('\0');
				}
			}
		});
		okButton = new JButton(Constants.OK);
		okButton.addActionListener(getInstance().new OkAction());
		okButton.setMargin(new Insets(5, 8, 5, 8));
		cancelButton = new JButton(Constants.CANCLE);
		cancelButton.addActionListener(getInstance().new CancelAction());
		cancelButton.setMargin(new Insets(5, 8, 5, 8));
		okButton.setMinimumSize(new Dimension(70, 20));
		okButton.setMaximumSize(new Dimension(70, 20));
		okButton.setPreferredSize(new Dimension(70, 20));
		cancelButton.setMinimumSize(new Dimension(70, 20));
		cancelButton.setMaximumSize(new Dimension(70, 20));
		cancelButton.setPreferredSize(new Dimension(70, 20));
		downPanel = new JPanel();
		downPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		downPanel.add(showCheckBox);
		blankLabel = new JLabel();
		if(defaultLanguage.equals("zh")){
			blankLabel.setPreferredSize(new Dimension(220, 20));
		}else {
			blankLabel.setPreferredSize(new Dimension(175, 20));
		}
		downPanel.add(blankLabel);
		downPanel.add(okButton);
		downPanel.add(cancelButton);
		configuration = FileService.initlConfig();
		initlConfigWin(configuration);
	}

	private ConfigWin() {
	}

	public static ConfigWin getInstance() {
		return configWin;
	}

	public JDialog getConfigDialog() {

		this.setSize(460, 450);
		this.setLocation(MainWin.getInstance().getLocation().x + 120, MainWin
				.getInstance().getLocation().y + 125);
		this.setModal(true);
		this.setTitle(Constants.CONFIG);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(
				Logging.class.getResource("/icons/setting.png")));
		this.setLayout(new BorderLayout());
		this.add(bottomPanel, BorderLayout.CENTER);
		this.add(downPanel, BorderLayout.SOUTH);
		this.setResizable(false);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
			}
		});
		return this;
	}

	public static void initlConfigWin(Configuration configuration) {
		bscUserNameQField.setText(configuration.getSetting()
				.getBscUserNameQuestion());
		bscUserNameAField.setText(configuration.getSetting()
				.getBscUserNameAnswer());
		bscPasswordQField.setText(configuration.getSetting()
				.getBscPasswordQuestion());
		bscPasswordAField.setText(configuration.getSetting()
				.getBscPasswordAnswer());
		routerIpField.setText(configuration.getSetting().getRouterIP());
		promptField.setText(configuration.getSetting().getRouterPrompt());
		routerUsernameQField.setText(configuration.getSetting()
				.getRouterUsernameQuestion());
		routerUsernameAField.setText(configuration.getSetting()
				.getRouterUsernameAnswer());
		routerPasswordQField.setText(configuration.getSetting()
				.getRouterPasswordQuestion());
		routerPasswordAField.setText(configuration.getSetting()
				.getRouterPasswordAnswer());
		pathField.setText(configuration.getSetting().getLogPath());
		int singleFile = configuration.getSetting().getIsSingleFile();
		if (singleFile == 0) {
			logCheckBox.setSelected(false);
		} else {
			logCheckBox.setSelected(true);
		}
		int errorStop = configuration.getSetting().getErrorStop();
		if (errorStop == 1) {
			failCheckBox.setSelected(true);
		} else {
			failCheckBox.setSelected(false);
		}
		threadSpinner.setValue(configuration.getSetting().getThreadNumber());
	}

	public String showPassword(JPasswordField passwordField) {
		String password = passwordField.getPassword().toString();
		return password;
	}

	public static Configuration getConfiguration() {
		return configuration;
	}

	public static void setConfiguration(Configuration configuration) {
		ConfigWin.configuration = configuration;
	}

	private class OkAction extends AbstractAction {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Setting setting = new Setting();
			setting.setBscUserNameQuestion(bscUserNameQField.getText());
			setting.setBscUserNameAnswer(bscUserNameAField.getText());
			setting.setBscPasswordQuestion(bscPasswordQField.getText());
			setting.setBscPasswordAnswer(new String(bscPasswordAField
					.getPassword()));
			setting.setRouterIP(routerIpField.getText());
			setting.setRouterPrompt(promptField.getText());
			setting.setRouterUsernameQuestion(routerUsernameQField.getText());
			setting.setRouterUsernameAnswer(routerUsernameAField.getText());
			setting.setRouterPasswordQuestion(routerPasswordQField.getText());
			setting.setRouterPasswordAnswer(new String(routerPasswordAField
					.getPassword()));
			setting.setLogPath(pathField.getText());
			boolean isSingleFile = logCheckBox.isSelected();
			if (isSingleFile) {
				setting.setIsSingleFile(1);
			} else {
				setting.setIsSingleFile(0);
			}
			setting.setThreadNumber((Integer) threadSpinner.getValue());
			boolean isSelected = failCheckBox.isSelected();
			if (isSelected) {
				setting.setErrorStop(1);
			} else {
				setting.setErrorStop(0);
			}

			Configuration configuration = MainTable.getConfiguration();
			configuration.setSetting(setting);
			MainTable.setConfiguration(configuration);
			MainTable.loadTableData(configuration);
			File file = new File(Constants.CONFIG_FILE_PATH);
			FileService.exportXML(configuration, file);
			configWin.setVisible(false);
		}
	}

	private class CancelAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			configWin.setVisible(false);
		}
	}
}
