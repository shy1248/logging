package me.shy.logging.event;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;

import me.shy.logging.Constants;
import me.shy.logging.Finished;
import me.shy.logging.Logging;
import me.shy.logging.UpdataProgress;
import me.shy.logging.entity.BSC;
import me.shy.logging.entity.Configuration;
import me.shy.logging.gui.ComTextPane;
import me.shy.logging.gui.ConfigWin;
import me.shy.logging.gui.MainTable;
import me.shy.logging.gui.MainWin;
import me.shy.logging.gui.MenuBar;
import me.shy.logging.gui.StatusBar;
import me.shy.logging.gui.ToolBar;
import me.shy.logging.gui.UpdataTaskTime;
import me.shy.logging.telnet.MyTelnetClient;

public class RunAction extends AbstractAction implements Runnable, Finished,
		UpdataProgress {

	/**描述：*/	
    private static final long serialVersionUID = 1L;
    private Configuration configuration;
	private List<String> commandStrings;
	private List<File> logs;
	private int totalTask;
	private int taskCounter;
	private int progress;
	private Timer timer;

	public RunAction() {
		super("Run", new ImageIcon(Toolkit.getDefaultToolkit().getImage(
				Logging.class.getClass().getResource("/icons/run.png"))));
		putValue(Action.SHORT_DESCRIPTION, Constants.RUN);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		configuration = MainTable.getConfiguration();
		String bscUserNameQuestion = configuration.getSetting()
				.getBscUserNameQuestion();
		String bscUserNameAnswer = configuration.getSetting()
				.getBscUserNameAnswer();
		String bscPasswordQuestion = configuration.getSetting()
				.getBscPasswordQuestion();
		String bscPasswordAnswer = configuration.getSetting()
				.getBscPasswordAnswer();
		String logPath = configuration.getSetting().getLogPath();
		List<BSC> bscs = configuration.getBscs();
		boolean isHasTask = false;
		logs = new ArrayList<File>();
		commandStrings = ComTextPane.getCommandStrings();

		MainTable.clearMsg();

		if (bscUserNameQuestion.equals("") || bscUserNameAnswer.equals("")
				|| bscPasswordQuestion.equals("")
				|| bscPasswordAnswer.equals("") || logPath.equals("")) {
			JOptionPane.showMessageDialog(MainWin.getInstance(),
					Constants.CONFIG_NOT_COMPLETE, Constants.ERROR_TITLE,
					JOptionPane.ERROR_MESSAGE);
			ConfigWin.getInstance().getConfigDialog().setVisible(true);
			return;
		}

		if (ComTextPane.getCommandStrings() == null) {
			JOptionPane.showMessageDialog(MainWin.getInstance(),
					Constants.NO_COMMAND, Constants.ERROR_TITLE,
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		taskCounter = 0;
		progress = 0;
		totalTask = 0;

		for (int i = 0; i < bscs.size(); i++) {
			if (bscs.get(i).isSelected()) {
				isHasTask = true;
				totalTask = totalTask + 1;
			}
		}

		if (!isHasTask) {
			JOptionPane.showMessageDialog(null, Constants.NO_TASK,
					Constants.ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
			return;
		}

		ComTextPane.lock();
		MainTable.lock();
		ToolBar.setRunningStatus();
		MenuBar.setRunningStatus();
		timer = new Timer();
		timer.schedule(new UpdataTaskTime(), 0, 1000);
		// StatusBar.getStatusLabel().setText("Running...    ");
		StatusBar.getSelectedLabel().setText(
				Constants.SELECTED + totalTask + " ");
		StatusBar.getDoneLabel().setText(Constants.FINISHED + ": 0");
		StatusBar.getTextStatusLabel().setBackground(Color.RED);
		StatusBar.getTextStatusLabel().setText(Constants.LOCK);
		// progressBar = StatusBar.getProgressBar();
		// progressBar.setVisible(true);
		// progressBar.setValue(0);
		// progressBar.setMaximum(totalTask * commands.size());

		Thread thread = new Thread(this);
		thread.start();

	}

	@Override
	public void run() {

		// start = System.currentTimeMillis();
		int size = configuration.getSetting().getThreadNumber();
		List<BSC> bscs = configuration.getBscs();
		MyTelnetClient myTelnetClient = null;
		ExecutorService executor = Executors.newFixedThreadPool(size);

		for (int i = 0; i < bscs.size(); i++) {
			if (bscs.get(i).isSelected()) {
				BSC bsc = bscs.get(i);
				myTelnetClient = new MyTelnetClient();
				myTelnetClient.setBsc(bsc);
				myTelnetClient.setBscId(i);
				myTelnetClient.setFinisher(this);
				myTelnetClient.setUpdatater(this);
				executor.execute(myTelnetClient);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					Constants.LOGGER.error(e.getMessage());
				}
			}
		}
		executor.shutdown();
	}

	public void mergeLogs(List<File> logs) {

		if (configuration.getSetting().getIsSingleFile() == 1) {

			StatusBar.getProressLabel().setText(Constants.MERGE_LOGS);

			String logPath = configuration.getSetting().getLogPath();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			File file = new File(logPath + "/" + format.format(new Date())
					+ ".log");

			File[] files = new File(logPath).listFiles();
			for (int i = 0; i < files.length; i++) {
				if (file.exists()) {
					file.delete();
				}
			}

			for (int i = 0; i < logs.size(); i++) {
				try {
					File logFile = logs.get(i);
					if (logFile.exists()) {
						List<String> lines = FileUtils.readLines(logs.get(i));
						FileUtils.writeLines(file, lines, true);
						logs.get(i).delete();
					}
				} catch (IOException e) {
					Constants.LOGGER.error(e.getMessage());
				}
			}

		}
	}

	@Override
	public synchronized void Finish(String logFilePath) {

		logs.add(new File(logFilePath));
		taskCounter = taskCounter + 1;
		StatusBar.getDoneLabel().setText(
				Constants.FINISHED + ": " + taskCounter + " ");
		if (taskCounter >= totalTask) {

			// end = System.currentTimeMillis();
			// System.out.println(end -start);

			// progressBar.setVisible(false);
			mergeLogs(logs);
			timer.cancel();
			StatusBar.getStatusLabel().setText(Constants.READY);
			StatusBar.getProressLabel().setText("");
			MainTable.unLock();
			ComTextPane.unLock();
			ToolBar.setNormalStatus();
			MenuBar.setNormalStatus();
			StatusBar.getTextStatusLabel().setBackground(Color.YELLOW);
			StatusBar.getTextStatusLabel().setText(Constants.UNLOCK);
		}

	}

	@Override
	public synchronized void updata(int increment) {
		progress = progress + increment;
		int i = progress * 100 / (totalTask * commandStrings.size());
		StatusBar.getProressLabel().setText("  " + i + Constants.HAS_DONE);
		// progressBar.setValue(progress);
	}
}
