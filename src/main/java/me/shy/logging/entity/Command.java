package me.shy.logging.entity;

public class Command {
	private String commandString;
	
	public boolean isCommand(String line){
		if(line.startsWith("Z") && line.endsWith(";")){
			return true;
		}
		return false;		
	}

	public final String getCommandString() {
		return commandString;
	}

	public final void setCommandString(String commandString) {
		this.commandString = commandString;
	}

	
	
}
