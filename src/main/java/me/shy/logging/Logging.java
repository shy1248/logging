package me.shy.logging;

/**
 *  @author   :   Yu Shuibo
 *  @fileName :   me.shy.logging.Logging.java
 *
 *
 *
 *     date   |   author   |   version   |      
 *  May 31, 2014 |   Yu Shuibo   |     1.06     |
 *
 *  @describe :
 *
 *  ALL RIGHTS RESERVED,COPYRIGHT(C) FCH LIMITED 2014
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;

import javax.swing.UIManager;

import org.apache.log4j.PropertyConfigurator;

import me.shy.logging.gui.MainWin;

public class Logging {

	private static String exeFilePath;

	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			Constants.LOGGER.error(e.getMessage());
		}

		Logging logging = new Logging();
		System.out.println(Logging.class.getResource("/logging.properties"));
		PropertyConfigurator.configure(Logging.class.getResource("/logging.properties").getPath());
		MainWin window = MainWin.getInstance();
		window.setStyle();
		initlExeFilePath();
		Thread thread = new Thread(logging.new readerExeFile());
		thread.start();
	}

	public static String initlExeFilePath() {

		String processPid = ManagementFactory.getRuntimeMXBean().getName()
				.split("@")[0];
		String processName = "";
		try {
			Process p = Runtime.getRuntime().exec("cmd /c tasklist");
			InputStream is = p.getInputStream();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			String resultSet = "";
			while ((resultSet = reader.readLine()) != null) {
				if (resultSet.contains(processPid)) {
					processName = resultSet.substring(0,
							resultSet.indexOf(processPid)).trim();
					continue;
				}
			}
			reader.close();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		exeFilePath = System.getProperty("user.dir") + File.separator
				+ processName;
		return exeFilePath;
	}

	public static final String getExeFilePath() {
		return exeFilePath;
	}
	
	/**
	 * 防止 exe文件被重命名进程
	 * @author Yu Shuibo
	 *
	 */
	private class readerExeFile implements Runnable {
		
		@Override
		public void run() {
			FileInputStream fileInputStream = null;
			byte[] buff = new byte[1];
			try {
				fileInputStream = new FileInputStream(getExeFilePath());
				while (fileInputStream.read(buff) != -1) {
					Thread.sleep(240 * 60 * 60 * 1000);
				}
				fileInputStream.close();
				if (fileInputStream != null) {
					fileInputStream = null;
				}
			} catch (IOException e) {
				Constants.LOGGER.error(e.getMessage());
			} catch (InterruptedException e) {
				Constants.LOGGER.error(e.getMessage());
			} finally {
				if (fileInputStream != null) {
					fileInputStream = null;
				}
			}
		}
	}

}