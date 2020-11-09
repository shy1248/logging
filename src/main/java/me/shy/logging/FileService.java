package me.shy.logging;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.prefs.Preferences;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import org.apache.commons.io.FileUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import me.shy.logging.entity.BSC;
import me.shy.logging.entity.Command;
import me.shy.logging.entity.Configuration;
import me.shy.logging.entity.Setting;
import me.shy.logging.gui.MainWin;

/**
 * @author Yu Shuibo
 * @version 1.0,2014/04/21
 * 
 */
public class FileService {

	private static JFileChooser jfc = null;

	private final static String LOGGING = "logging";
	private final static String BSCS = "bscs";
	private final static String CMDS = "cmds";
	private final static String BSC = "bsc";
	private final static String BSC_NAME = "bsc_name";
	private final static String BSC_IP = "bsc_ip";
	private final static String BSC_TYPE = "bsc_type";
	private final static String CMD = "cmd";
	private final static String COMMAND = "command";
	private final static String SETTING = "setting";
	private final static String BSC_USERNAME_QUESTION = "bsc_username_question";
	private final static String BSC_USERNAME_ANSWER = "bsc_username_answer";
	private final static String BSC_PASSWORD_QUESTION = "bsc_password_question";
	private final static String BSC_PASSWORD_ANSWER = "bsc_password_answer";
	private final static String ROUTER_IP = "router_ip";
	private final static String ROUTER_PROMPT = "router_prompt";
	private final static String ROUTER_USERNAME_QUESTION = "router_username_question";
	private final static String ROUTER_USERNAME_ANSWER = "router_username_answer";
	private final static String ROUTER_PASSWORD_QUESTION = "router_password_question";
	private final static String ROUTER_PASSWORD_ANSWER = "router_password_answer";
	private final static String LOG_PATH = "log_path";
	private final static String IS_SINGLE = "is_single";
	private final static String THREAD_NUMBER = "thread_number";
	private final static String IS_ERROR_STOP = "is_error_stop";

	public static File fileOpen(FileFilter filter) {

		File file;
		String key = null;
		key = "lastPath";
		Preferences pref = Preferences.userRoot().node("/bsctoolkit");
		String lastPath = pref.get(key, "");
		if (!lastPath.equals("")) {
			jfc = new JFileChooser(lastPath);
		} else {
			jfc = new JFileChooser();
		}
	
		jfc.setMultiSelectionEnabled(false);
		jfc.setDialogTitle(Constants.OPEN_FILE);
		jfc.setFileFilter(filter);
		jfc.setFileHidingEnabled(false);

		int result = jfc.showOpenDialog(MainWin.getInstance());
		if (result == JFileChooser.APPROVE_OPTION) {
			file = jfc.getSelectedFile();
			pref.put(key, file.getPath());
			return file;
		}
		return null;
	}

	public static File fileSave(FileFilter filter) {

		File file;
		String key = null;

		key = "lastPath";
		Preferences pref = Preferences.userRoot().node("/bsctoolkit");
		String lastPath = pref.get(key, "");
		if (!lastPath.equals("")) {
			jfc = new JFileChooser(lastPath);
		} else {
			jfc = new JFileChooser();
		}
		jfc.setMultiSelectionEnabled(false);
		jfc.setDialogTitle(Constants.SAVE_FILE);
		jfc.setFileFilter(filter);
		int result = jfc.showSaveDialog(MainWin.getInstance());
		if (result == JFileChooser.APPROVE_OPTION) {
			file = jfc.getSelectedFile();
			if (file.exists()) {
				file.delete();
			}
			pref.put(key, file.getPath());
			return file;
		} else {

			return null;
		}

	}

	public static Configuration importXML(File file) {
		List<BSC> bscs = new ArrayList<BSC>();
		List<Command> commands = new ArrayList<Command>();
		Setting setting = new Setting();
		Configuration configuration = new Configuration();
		SAXReader saxReader = new SAXReader();
		Document document;

		try {
			if (null != file) {
				document = saxReader.read(file);
				Element root = document.getRootElement();
				for (Iterator<?> iterator = root.elementIterator(); iterator
						.hasNext();) {
					Element firstLayer = (Element) iterator.next();
					for (Iterator<?> iteratorElement = firstLayer
							.elementIterator(); iteratorElement.hasNext();) {
						Element sencendLayer = (Element) iteratorElement.next();
						if (sencendLayer.getName().equals(BSC)) {
							Attribute nameAttribute = sencendLayer
									.attribute(BSC_NAME);
							Attribute ipAttribute = sencendLayer
									.attribute(BSC_IP);
							Attribute typeAttribute = sencendLayer
									.attribute(BSC_TYPE);
							BSC bsc = new BSC();
							bsc.setName(nameAttribute.getValue());
							bsc.setIp(ipAttribute.getValue());
							bsc.setType(typeAttribute.getValue());
							bscs.add(bsc);
						}
						if (sencendLayer.getName().equals(CMD)) {
							Attribute commandAttribute = sencendLayer
									.attribute(COMMAND);
							Command command = new Command();
							command.setCommandString(commandAttribute
									.getValue());
							commands.add(command);
						}
						if (sencendLayer.getName()
								.equals(BSC_USERNAME_QUESTION)) {
							setting.setBscUserNameQuestion(sencendLayer
									.getText());
						} else if (sencendLayer.getName().equals(
								BSC_USERNAME_ANSWER)) {
							setting.setBscUserNameAnswer(sencendLayer.getText());
						} else if (sencendLayer.getName().equals(
								BSC_PASSWORD_QUESTION)) {
							setting.setBscPasswordQuestion(sencendLayer
									.getText());
						} else if (sencendLayer.getName().equals(
								BSC_PASSWORD_ANSWER)) {
							setting.setBscPasswordAnswer(sencendLayer.getText());
						} else if (sencendLayer.getName().equals(ROUTER_IP)) {
							setting.setRouterIP(sencendLayer.getText());
						} else if (sencendLayer.getName().equals(ROUTER_PROMPT)) {
							setting.setRouterPrompt(sencendLayer.getText());
						} else if (sencendLayer.getName().equals(
								ROUTER_USERNAME_QUESTION)) {
							setting.setRouterUsernameQuestion(sencendLayer
									.getText());
						} else if (sencendLayer.getName().equals(
								ROUTER_USERNAME_ANSWER)) {
							setting.setRouterUsernameAnswer(sencendLayer
									.getText());
						} else if (sencendLayer.getName().equals(
								ROUTER_PASSWORD_QUESTION)) {
							setting.setRouterPasswordQuestion(sencendLayer
									.getText());
						} else if (sencendLayer.getName().equals(
								ROUTER_PASSWORD_ANSWER)) {
							setting.setRouterPasswordAnswer(sencendLayer
									.getText());
						} else if (sencendLayer.getName().equals(LOG_PATH)) {
							setting.setLogPath(sencendLayer.getText());
						} else if (sencendLayer.getName().equals(IS_SINGLE)) {
							setting.setIsSingleFile(Integer
									.parseInt(sencendLayer.getText()));
						} else if (sencendLayer.getName().equals(THREAD_NUMBER)) {
							setting.setThreadNumber(Integer
									.parseInt(sencendLayer.getText()));
						} else if (sencendLayer.getName().equals(IS_ERROR_STOP)) {
							setting.setErrorStop(Integer.parseInt(sencendLayer
									.getText()));
						}
					}
				}

			}
			configuration.setBscs(bscs);
			configuration.setCommands(commands);
			configuration.setSetting(setting);

		} catch (DocumentException e) {
			Constants.LOGGER.error(e.getMessage());
			JOptionPane.showMessageDialog(MainWin.getInstance(),
					Constants.PROFILES_FORMAT_ERROR, Constants.ERROR_TITLE,
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
		return configuration;

	}

	public static void exportXML(Configuration configuration, File file) {

		Document doc = DocumentHelper.createDocument();
		Element rootEle = doc.addElement(LOGGING);
		Element firstLayerBsc = rootEle.addElement(BSCS);

		for (BSC bsc : configuration.getBscs()) {
			Element sencendLayerBsc = firstLayerBsc.addElement(BSC);
			sencendLayerBsc.addAttribute(BSC_NAME, bsc.getName());
			sencendLayerBsc.addAttribute(BSC_IP, bsc.getIp());
			sencendLayerBsc.addAttribute(BSC_TYPE, bsc.getType());
		}

		Element firstLayerCmd = rootEle.addElement(CMDS);
		for (Command command : configuration.getCommands()) {
			Element sencendLayerCmd = firstLayerCmd.addElement(CMD);
			sencendLayerCmd.addAttribute(COMMAND, command.getCommandString());
		}

		Element firstLayerSetting = rootEle.addElement(SETTING);
		Element sencendLayerSettingBscUsernameQuestion = firstLayerSetting
				.addElement(BSC_USERNAME_QUESTION);
		sencendLayerSettingBscUsernameQuestion.setText(configuration
				.getSetting().getBscUserNameQuestion());
		Element sencendLayerSettingBscUsernameAnswer = firstLayerSetting
				.addElement(BSC_USERNAME_ANSWER);
		sencendLayerSettingBscUsernameAnswer.setText(configuration.getSetting()
				.getBscUserNameAnswer());
		Element sencendLayerSettingBscPasswordQuestion = firstLayerSetting
				.addElement(BSC_PASSWORD_QUESTION);
		sencendLayerSettingBscPasswordQuestion.setText(configuration
				.getSetting().getBscPasswordQuestion());
		Element sencendLayerSettingBscPasswordAnswer = firstLayerSetting
				.addElement(BSC_PASSWORD_ANSWER);
		sencendLayerSettingBscPasswordAnswer.setText(configuration.getSetting()
				.getBscPasswordAnswer());
		Element sencendLayerSettingRouterip = firstLayerSetting
				.addElement(ROUTER_IP);
		sencendLayerSettingRouterip.setText(configuration.getSetting()
				.getRouterIP());
		Element sencendLayerSettingRouterprompt = firstLayerSetting
				.addElement(ROUTER_PROMPT);
		sencendLayerSettingRouterprompt.setText(configuration.getSetting()
				.getRouterPrompt());
		Element sencendLayerSettingRouterUsernameQuestion = firstLayerSetting
				.addElement(ROUTER_USERNAME_QUESTION);
		sencendLayerSettingRouterUsernameQuestion.setText(configuration
				.getSetting().getRouterUsernameQuestion());
		Element sencendLayerSettingRouterUsernameAnswer = firstLayerSetting
				.addElement(ROUTER_USERNAME_ANSWER);
		sencendLayerSettingRouterUsernameAnswer.setText(configuration
				.getSetting().getRouterUsernameAnswer());
		Element sencendLayerSettingRouterPasswordQuestion = firstLayerSetting
				.addElement(ROUTER_PASSWORD_QUESTION);
		sencendLayerSettingRouterPasswordQuestion.setText(configuration
				.getSetting().getRouterPasswordQuestion());
		Element sencendLayerSettingRouterPasswordAnswer = firstLayerSetting
				.addElement(ROUTER_PASSWORD_ANSWER);
		sencendLayerSettingRouterPasswordAnswer.setText(configuration
				.getSetting().getRouterPasswordAnswer());
		Element sencendLayerSettingLogpath = firstLayerSetting
				.addElement(LOG_PATH);
		sencendLayerSettingLogpath.setText(configuration.getSetting()
				.getLogPath());
		Element sencendLayerSingleFile = firstLayerSetting
				.addElement(IS_SINGLE);
		sencendLayerSingleFile.setText(configuration.getSetting()
				.getIsSingleFile() + "");
		Element sencendLayerSettingThreadnum = firstLayerSetting
				.addElement(THREAD_NUMBER);
		sencendLayerSettingThreadnum.setText(configuration.getSetting()
				.getThreadNumber() + "");
		Element sencendLayerSettingErrstop = firstLayerSetting
				.addElement(IS_ERROR_STOP);
		sencendLayerSettingErrstop.setText(configuration.getSetting()
				.getErrorStop() + "");

		try {
			if (null == file) {
				return;
			}
			FileOutputStream out = new FileOutputStream(file);
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setTrimText(false);
			format.setEncoding("GBK");
			XMLWriter writer = new XMLWriter(out, format);
			writer.write(doc);
			writer.close();
		} catch (IOException e) {
			Constants.LOGGER.error(e.getMessage());
			JOptionPane.showMessageDialog(MainWin.getInstance(),
					Constants.EXPORT_FILE_FAILED + "\n" + e.getMessage(),
					Constants.ERROR_TITLE, JOptionPane.WARNING_MESSAGE);
		}

	}

	public static void createXML() {

		Document doc = DocumentHelper.createDocument();
		Element rootEle = doc.addElement(LOGGING);
		rootEle.addElement(BSCS);
		rootEle.addElement(CMDS);
		Element firstLayerSetting = rootEle.addElement(SETTING);
		Element sencendLayerSettingBscUsernameQuestion = firstLayerSetting
				.addElement(BSC_USERNAME_QUESTION);
		sencendLayerSettingBscUsernameQuestion.setText("");
		Element sencendLayerSettingBscUsernameAnswer = firstLayerSetting
				.addElement(BSC_USERNAME_ANSWER);
		sencendLayerSettingBscUsernameAnswer.setText("");
		Element sencendLayerSettingBscPasswordQuestion = firstLayerSetting
				.addElement(BSC_PASSWORD_QUESTION);
		sencendLayerSettingBscPasswordQuestion.setText("");
		Element sencendLayerSettingBscPasswordAnswer = firstLayerSetting
				.addElement(BSC_PASSWORD_ANSWER);
		sencendLayerSettingBscPasswordAnswer.setText("");
		Element sencendLayerSettingRouterip = firstLayerSetting
				.addElement(ROUTER_IP);
		sencendLayerSettingRouterip.setText("");
		Element sencendLayerSettingRouterprompt = firstLayerSetting
				.addElement(ROUTER_PROMPT);
		sencendLayerSettingRouterprompt.setText("");
		Element sencendLayerSettingRouterUserNameQuestion = firstLayerSetting
				.addElement(ROUTER_USERNAME_QUESTION);
		sencendLayerSettingRouterUserNameQuestion.setText("");
		Element sencendLayerSettingRouterUserNameAnswer = firstLayerSetting
				.addElement(ROUTER_USERNAME_ANSWER);
		sencendLayerSettingRouterUserNameAnswer.setText("");
		Element sencendLayerSettingRouterPasswordQuestion = firstLayerSetting
				.addElement(ROUTER_PASSWORD_QUESTION);
		sencendLayerSettingRouterPasswordQuestion.setText("");
		Element sencendLayerSettingRouterPasswordAnswer = firstLayerSetting
				.addElement(ROUTER_PASSWORD_ANSWER);
		sencendLayerSettingRouterPasswordAnswer.setText("");
		Element sencendLayerSettingLogpath = firstLayerSetting
				.addElement(LOG_PATH);
		sencendLayerSettingLogpath.setText("");
		Element sencendLayerSingleFile = firstLayerSetting
				.addElement(IS_SINGLE);
		sencendLayerSingleFile.setText("0");
		Element sencendLayerSettingThreadnum = firstLayerSetting
				.addElement(THREAD_NUMBER);
		sencendLayerSettingThreadnum.setText("8");
		Element sencendLayerSettingErrstop = firstLayerSetting
				.addElement(IS_ERROR_STOP);
		sencendLayerSettingErrstop.setText("0");

		FileOutputStream out;
		try {
			out = new FileOutputStream(Constants.CONFIG_FILE_PATH);
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setTrimText(false);
			format.setEncoding("GBK");
			XMLWriter writer = new XMLWriter(out, format);
			writer.write(doc);
			writer.close();
		} catch (IOException e) {
			Constants.LOGGER.error(e.getMessage());
		}
	}

	public static Configuration initlConfig() {
		File configFile;
		Configuration configuration = null;
		try {

			if (false == new File(Constants.CONFIG_FILE_PATH).exists()) {
				createXML();
			}
			configFile = new File(Constants.CONFIG_FILE_PATH);
			configuration = importXML(configFile);
		} catch (NullPointerException e) {
			// Logging.logger.error(e.getMessage());
		}
		return configuration;

	}

	public static Configuration loadConfig(File configFile) {
		Configuration configuration = importXML(configFile);
		return configuration;
	}

	public static void fileCopy(File sourceFile, File targetFile) {
		if (sourceFile != null) {
			try {
				FileUtils.copyFile(sourceFile, targetFile);
			} catch (IOException e) {
				Constants.LOGGER.error(e.getMessage());
			}
		}
	}

}
