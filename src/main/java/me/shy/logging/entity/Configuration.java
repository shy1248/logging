package me.shy.logging.entity;

import java.util.List;

import me.shy.logging.gui.ConfigWin;

public class Configuration {
	private List<BSC> bscs;
	private List<Command> commands;
	private Setting setting;
	private ConfigWin configWin;
	
	public final List<BSC> getBscs() {
		return bscs;
	}
	public final void setBscs(List<BSC> bscs) {
		this.bscs = bscs;
	}
	public final List<Command> getCommands() {
		return commands;
	}
	public final void setCommands(List<Command> commands) {
		this.commands = commands;
	}
	public final Setting getSetting() {
		return setting;
	}
	public final void setSetting(Setting setting) {
		this.setting = setting;
	}
	public final ConfigWin getConfigWin() {
		return configWin;
	}
	public final void setConfigWin(ConfigWin configWin) {
		this.configWin = configWin;
	}
	
	
}
