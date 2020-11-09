package me.shy.logging.gui;

import javax.swing.JToolBar;

import me.shy.logging.event.AddAction;
import me.shy.logging.event.DeleteAction;
import me.shy.logging.event.ExitAction;
import me.shy.logging.event.ExportAction;
import me.shy.logging.event.ImportAction;
import me.shy.logging.event.OpenConfigAction;
import me.shy.logging.event.OpenFileAction;
import me.shy.logging.event.OpenLogAction;
import me.shy.logging.event.RunAction;
import me.shy.logging.event.SaveFileAction;

public class ToolBar extends JToolBar {
	/**描述：*/	
    private static final long serialVersionUID = 1L;
    private static RunAction runAction = new RunAction();
	private static ExitAction exitAction = new ExitAction();
	private static OpenFileAction openFileAction = new OpenFileAction();
	private static SaveFileAction saveFileAction = new SaveFileAction();
	private static AddAction addAction = new AddAction();
	private static DeleteAction deleteAction = new DeleteAction();
	private static ImportAction importAction = new ImportAction();
	private static ExportAction exportAction = new ExportAction();
	private static OpenLogAction openLogAction = new OpenLogAction();
	private static OpenConfigAction openConfigAction = new OpenConfigAction();

	public JToolBar getInstance() {

		this.add(new Separator());
		this.add(runAction);
		this.add(new Separator());
		this.add(addAction);
		this.add(deleteAction);
		this.add(new Separator());
		this.add(saveFileAction);
		this.add(openFileAction);
		this.add(new Separator());
		this.add(importAction);
		this.add(exportAction);
		// this.add(new Separator());
		this.add(openLogAction);
		this.add(openConfigAction);
		this.add(new Separator());
		this.add(exitAction);
		// this.setBorder(null);
		this.setFloatable(false);
		this.setRollover(true);
		// this.setMargin(new Insets(20, 20, 20, 20));
		this.setVisible(true);
		return this;
	}

	public static void setRunningStatus() {
		runAction.setEnabled(false);
		addAction.setEnabled(false);
		deleteAction.setEnabled(false);
		openFileAction.setEnabled(false);
		importAction.setEnabled(false);
	}

	public static void setNormalStatus() {
		runAction.setEnabled(true);
		addAction.setEnabled(true);
		deleteAction.setEnabled(true);
		openFileAction.setEnabled(true);
		importAction.setEnabled(true);
	}

	public static final RunAction getRunAction() {
		return runAction;
	}

	public static final void setRunAction(RunAction runAction) {
		ToolBar.runAction = runAction;
	}

	public static final ExitAction getExitAction() {
		return exitAction;
	}

	public static final void setExitAction(ExitAction exitAction) {
		ToolBar.exitAction = exitAction;
	}

	public static final OpenFileAction getOpenFileAction() {
		return openFileAction;
	}

	public static final void setOpenFileAction(OpenFileAction openFileAction) {
		ToolBar.openFileAction = openFileAction;
	}

	public static final SaveFileAction getSaveFileAction() {
		return saveFileAction;
	}

	public static final void setSaveFileAction(SaveFileAction saveFileAction) {
		ToolBar.saveFileAction = saveFileAction;
	}

	public static final AddAction getAddAction() {
		return addAction;
	}

	public static final void setAddAction(AddAction addAction) {
		ToolBar.addAction = addAction;
	}

	public static final DeleteAction getDeleteAction() {
		return deleteAction;
	}

	public static final void setDeleteAction(DeleteAction deleteAction) {
		ToolBar.deleteAction = deleteAction;
	}

	public static final ImportAction getImportAction() {
		return importAction;
	}

	public static final void setImportAction(ImportAction importAction) {
		ToolBar.importAction = importAction;
	}

	public static final ExportAction getExportAction() {
		return exportAction;
	}

	public static final void setExportAction(ExportAction exportAction) {
		ToolBar.exportAction = exportAction;
	}

	public static final OpenLogAction getOpenLogAction() {
		return openLogAction;
	}

	public static final void setOpenLogAction(OpenLogAction openLogAction) {
		ToolBar.openLogAction = openLogAction;
	}

	public static final OpenConfigAction getOpenConfigAction() {
		return openConfigAction;
	}

	public static final void setOpenConfigAction(
			OpenConfigAction openConfigAction) {
		ToolBar.openConfigAction = openConfigAction;
	}

}
