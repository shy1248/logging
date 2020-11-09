package me.shy.logging.gui;

import java.util.TimerTask;

import me.shy.logging.Constants;

/**
 * @author : Yu Shuibo
 * @fileName : me.shy.logging.gui.UpdataTaskTime.java
 * 
 * 
 * 
 *           date | author | version | Jul 8, 2014 | Yu Shuibo | 1.0 |
 * 
 * @describe :
 * 
 *           ALL RIGHTS RESERVED,COPYRIGHT(C) FCH LIMITED 2014
 */

public class UpdataTaskTime extends TimerTask {

	private String running = Constants.RUNNING;

	private long ss = 0;
	private long mm = 0;
	private long hh = 0;

	@Override
	public void run() {
		ss = ss + 1;
		if (ss >= 60) {
			mm = mm + 1;
			ss = 0;
		}
		if (mm >= 60) {
			hh = hh + 1;
			mm = 0;
		}

		String seconds;
		String minutes;
		String hours;
		if (ss < 10) {
			seconds = "0" + ss;
		} else {
			seconds = "" + ss;
		}
		if (mm < 10) {
			minutes = "0" + mm;
		} else {
			minutes = "" + mm;
		}
		if (hh < 10) {
			hours = "0" + hh;
		} else {
			hours = "" + hh;
		}

		StatusBar.getTimeLable().setText(
				" " + hours + ":" + minutes + ":" + seconds + " ");
		if (ss % 4 == 0) {
			StatusBar.getStatusLabel().setText(" " + running + "      ");
		} else if (ss % 4 == 1) {
			StatusBar.getStatusLabel().setText(" " + running + ".     ");
		} else if (ss % 4 == 2) {
			StatusBar.getStatusLabel().setText(" " + running + "..    ");
		} else if (ss % 4 == 3) {
			StatusBar.getStatusLabel().setText(" " + running + "...   ");
		}
	}

}
