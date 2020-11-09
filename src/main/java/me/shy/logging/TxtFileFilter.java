package me.shy.logging;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class TxtFileFilter extends FileFilter {

	@Override
	public boolean accept(File file) {
		if (file.isDirectory())return true;   
		if (file.getName().toLowerCase().endsWith(".txt")) return true;
		return false;
	}

	@Override
	public String getDescription() {
		return Constants.TEXT_FILE;
	}
	
}
