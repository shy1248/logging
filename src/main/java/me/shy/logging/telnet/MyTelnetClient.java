package me.shy.logging.telnet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import org.apache.commons.net.telnet.TelnetClient;

import me.shy.logging.Constants;
import me.shy.logging.Finished;
import me.shy.logging.UpdataProgress;
import me.shy.logging.entity.BSC;
import me.shy.logging.entity.Configuration;
import me.shy.logging.gui.ComTextPane;
import me.shy.logging.gui.MainTable;

public class MyTelnetClient implements Runnable {

	private Finished Finisher;
	private UpdataProgress updatater;
	private Configuration configuration = MainTable.getConfiguration();
	private FileWriter fileWriter = null;
	private BSC bsc = null;
	private int bscId = 0;
	private List<String> commandStrings = ComTextPane.getCommandStrings();
	private boolean isHasRouter = true;
	private int currentCmdId = 0;

	private TelnetClient telnetClient;
	private BufferedOutputStream bos = null;
	private BufferedInputStream bis = null;

	private String host = configuration.getSetting().getRouterIP();
	private String routerUsernameQuestion = configuration.getSetting()
			.getRouterUsernameQuestion().trim() + " ";
	private String routerUsernameAnswer = configuration.getSetting()
			.getRouterUsernameAnswer();
	private String routerPasswordQuestion = configuration.getSetting()
			.getRouterPasswordQuestion().trim() + " ";
	private String routerPasswordAnswer = configuration.getSetting()
			.getRouterPasswordAnswer();
	private String devicePrompt = configuration.getSetting().getRouterPrompt().trim();
	private String bscUsernameQuestion = configuration.getSetting()
			.getBscUserNameQuestion().trim() + " \b ";
	private String bscUsernameAnswer = configuration.getSetting()
			.getBscUserNameAnswer();
	private String bscPasswordQuestion = configuration.getSetting()
			.getBscPasswordQuestion().trim() + " \b ";
	private String bscPasswordAnswer = configuration.getSetting()
			.getBscPasswordAnswer();
	private int errorStop = configuration.getSetting().getErrorStop();

	private int port = 23;
	boolean isCmdHasError = false;
	private String commandPrompt = ">\r\n<  ";
	private String syntaxError = "/*** SYNTAX ERROR ***/";
	private String semanticError = "/*** SEMANTIC ERROR ***/";
	private String commandExecuteFailed = "/***";
	private String exit = "ZZZZ";

	@Override
	public void run() {
		
//		String passwdExpired = "/*** YOUR PASSWORD HAS EXPIRED ***/";

		try {
			fileWriter = new FileWriter(createLogFile());

		} catch (IOException e1) {
			Constants.LOGGER.error(e1.getMessage());
		}

		try {

			telnetClient = new TelnetClient("VT220");

			if (!host.trim().equals("")) {
				telnetClient.connect(host, port);
				isHasRouter = true;
			} else {
				telnetClient.connect(bsc.getIp(), port);
				isHasRouter = false;
			}

			telnetClient.setConnectTimeout(3000);
			bos = new BufferedOutputStream(telnetClient.getOutputStream());
			bis = new BufferedInputStream(telnetClient.getInputStream());

			if (isHasRouter && !routerUsernameQuestion.trim().equals("")) {
				if (null != reader(routerUsernameQuestion)) {
					showMassage(true, "Router" + Constants.USERNAME + ": "
							+ routerUsernameAnswer);
					bos.write((routerUsernameAnswer + "\r\n").getBytes());
					bos.flush();
				}
			}

			if (isHasRouter && !routerPasswordQuestion.trim().equals("")) {
				if (null != reader(routerPasswordQuestion)) {
					showMassage(true, "Router" + Constants.PASSWORD + ": "
							+ routerPasswordAnswer);
					bos.write((routerPasswordAnswer + "\r\n").getBytes());
					bos.flush();
				}
			}

			if (isHasRouter && !devicePrompt.trim().equals("")) {
				if (null != reader(devicePrompt)) {
					bos.write((bsc.getName() + "\r\n").getBytes());
					bos.flush();
				}
			}

			if (null != reader(bscUsernameQuestion)) {
				showMassage(true, "BSC" + Constants.USERNAME + ": "
						+ bscUsernameAnswer);
				bos.write((bscUsernameAnswer + "\r\n").getBytes());
				bos.flush();
			}

			if (null != reader(bscPasswordQuestion)) {
				showMassage(true, "BSC" + Constants.PASSWORD + ": "
						+ bscPasswordAnswer);
				bos.write((bscPasswordAnswer + "\r\n").getBytes());
				bos.flush();
			}


			if (null != reader(commandPrompt)) {
				for (int i = 0; i < commandStrings.size(); i++) {
					currentCmdId = i;
					String currentCommand = commandFilter(commandStrings.get(i));
					showMassage(true, Constants.COMMAND + ": " + currentCommand);
					if (commandStrings.get(i).contains(exit)) {
						bos.write((currentCommand + "\r\n").getBytes());
						bos.flush();
						reader(devicePrompt);
						updatater.updata(commandStrings.size() - i);
						break;
					} else {
						String resultSet = exeCommand(currentCommand);
						if (resultSet != null) {
							if (errorStop == 1) {
								if (resultSet.contains(commandExecuteFailed)) {
									showMassage(false, Constants.COMMAND + ": "
											+ currentCommand + " "
											+ Constants.ERROR_TITLE);
									isCmdHasError = true;
									updatater.updata(commandStrings.size() - i);
									break;
								}
							}
						} else {
							isCmdHasError = true;
							return;
						}
					}
					updatater.updata(1);
				}

				if (isCmdHasError == false) {
					showMassage(false, Constants.FINISHED + "!");
				}

				disconnect(telnetClient);
			}

		} catch (UnknownHostException e) {
			Constants.LOGGER.error(e.getMessage());
			updatater.updata(commandStrings.size());
			showMassage(false, Constants.IP_UNREACHABLE);
		} catch (SocketException e) {
			Constants.LOGGER.error(e.getMessage());
			updatater.updata(commandStrings.size());
			showMassage(false, Constants.SOCKET_ERROR);
		} catch (IOException e) {
			Constants.LOGGER.error(e.getMessage());
			updatater.updata(commandStrings.size());
			showMassage(false, Constants.IO_ERROR);
		} finally {
			Finisher.Finish(getLogFilePath());
			disconnect(telnetClient);
		}
	}

	public String reader(String pattern) {

		String temp = "";
		String resultString = "";
		byte[] buffer = new byte[1024];
		int contantLength = 0;
		try {
			while ((contantLength = bis.read(buffer)) != -1) {
				temp = resultString;
				resultString = new String(buffer, 0, contantLength);
				fileWriter.write(resultString.replaceAll(" ", ""));
				fileWriter.flush();

				if (contantLength < pattern.length()) {
					resultString = temp + resultString;
				}

				if (resultString.endsWith(pattern)) {
					return resultString;
				} else if (isHasRouter
						&& !pattern.equals(routerPasswordQuestion)
						&& resultString.endsWith(routerPasswordQuestion)) {
					showMassage(false, Constants.ROUTER_AUTHEN_FAILED);
					interruptted();
					disconnect(telnetClient);
					break;
				} else if (isHasRouter && !pattern.equals(devicePrompt)
						&& resultString.endsWith(devicePrompt)) {
					showMassage(false, Constants.CONNECT_FAILED);
					interruptted();
					disconnect(telnetClient);
					break;
				} else if (!pattern.equals(bscUsernameQuestion)
						&& resultString.endsWith(bscUsernameQuestion)) {
					showMassage(false, Constants.LOGIN_FAILED);
					interruptted();
					break;
				} else if (resultString.contains(syntaxError)
						|| resultString.contains(semanticError)) {
					showMassage(false, Constants.CMD_EXE_FAILED);
					interruptted();
					disconnect(telnetClient);
					break;
				}
			}
		} catch (IOException e) {
			Constants.LOGGER.error(e.getMessage());
		}
		return null;
	}

	public String exeCommand(String command) {

		bos = new BufferedOutputStream(telnetClient.getOutputStream());
		try {
			bos.write((command + "\r\n").getBytes());
			bos.flush();
			return reader(commandPrompt);
		} catch (IOException e) {
			Constants.LOGGER.error(e.getMessage());
		}

		return null;
	}

	public void interruptted() throws IOException {
		updatater.updata(commandStrings.size() - currentCmdId);
	}

	private void showMassage(boolean isConnect, String message) {

		DefaultTableModel model = (DefaultTableModel) MainTable.getTable()
				.getModel();
		if (isConnect == true) {
			bsc.setConnectionInfro(Constants.CONNECT);
		} else {
			bsc.setConnectionInfro(Constants.DISCONNECT);
		}
		bsc.setCurrectCmd(message);
		model.setValueAt(bsc.getConnectionInfro(), bscId, 4);
		model.setValueAt(bsc.getCurrectCmd(), bscId, 5);
	}

	public String commandFilter(String sourceCommand) {
		String tagCommand = null;
		if (sourceCommand.contains("<MAX>")) {
			if ("FlexiBSC".equals(bsc.getType())) {
				tagCommand = sourceCommand.replace("<MAX>", "3000");
				return tagCommand;
			}
			if ("BSC3i2000".equals(bsc.getType())) {
				tagCommand = sourceCommand.replace("<MAX>", "2000");
				return tagCommand;
			}
			if ("BSC3i660".equals(bsc.getType())) {
				tagCommand = sourceCommand.replace("<MAX>", "660");
				return tagCommand;
			}
			if ("BSC3i440".equals(bsc.getType())) {
				tagCommand = sourceCommand.replace("<MAX>", "440");
				return tagCommand;
			}
		}
		return sourceCommand;
	}

	public File createLogFile() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String logDirectory = configuration.getSetting().getLogPath();
		File logDirectoryFile = new File(logDirectory);
		if(!logDirectoryFile.exists()){
			logDirectoryFile.mkdirs();
		}
		String logPath = null;
		format = new SimpleDateFormat("yyyyMMdd");
		logPath = logDirectory + "/" + format.format(new Date()) + "_"
				+ bsc.getName() + ".log";
		File logFile = new File(logPath);
		return logFile;
	}

	public String getLogFilePath() {
		return createLogFile().getPath();
	}

	public void disconnect(TelnetClient telnetClient) {
		try {
			if(null != fileWriter){
				fileWriter.close();
			}
			
			if (telnetClient.isConnected()) {
				telnetClient.disconnect();
			}
		} catch (IOException e) {
			Constants.LOGGER.error(e.getMessage());
		}
	}

	public BSC getBsc() {
		return bsc;
	}

	public void setBsc(BSC bsc) {
		this.bsc = bsc;
	}

	public int getBscId() {
		return bscId;
	}

	public void setBscId(int bscId) {
		this.bscId = bscId;
	}

	public final UpdataProgress getUpdatater() {
		return updatater;
	}

	public final void setUpdatater(UpdataProgress updatater) {
		this.updatater = updatater;
	}

	public final Finished getFinisher() {
		return Finisher;
	}

	public final void setFinisher(Finished finisher) {
		Finisher = finisher;
	}
}
