package me.shy.logging.gui;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;

import me.shy.logging.Constants;

public class TextPopupMenu implements MouseListener {

	private JTextPane textPane = ComTextPane.getInstance();
	private JPopupMenu pop = null;
	private JMenuItem clear = null;
	private JMenuItem copy = null;
	private JMenuItem paste = null;
	private JMenuItem cut = null;

	public TextPopupMenu() {
		super();
		init();
	}

	private void init() {
		pop = new JPopupMenu();
		pop.add(clear = new JMenuItem(Constants.CLEAR));
		pop.add(copy = new JMenuItem(Constants.COPY));
		pop.add(paste = new JMenuItem(Constants.PAST));
		pop.add(cut = new JMenuItem(Constants.CUT));
		copy.setAccelerator(KeyStroke.getKeyStroke('C', InputEvent.CTRL_MASK));
		paste.setAccelerator(KeyStroke.getKeyStroke('V', InputEvent.CTRL_MASK));
		cut.setAccelerator(KeyStroke.getKeyStroke('X', InputEvent.CTRL_MASK));

		clear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				action(e);
			}
		});

		copy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				action(e);
			}
		});

		paste.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				action(e);
			}
		});

		cut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				action(e);
			}
		});

		textPane.add(pop);
	}

	public void action(ActionEvent e) {
		String str = e.getActionCommand();
		if (str.equals(copy.getText())) {
			textPane.copy();
		} else if (str.equals(paste.getText())) {
			textPane.paste();
		} else if (str.equals(cut.getText())) {
			textPane.cut();
		} else if (str.equals(clear.getText())) {
			textPane.setText("");
		}
	}

	public JPopupMenu getPop() {
		return pop;
	}

	public void setPop(JPopupMenu pop) {
		this.pop = pop;
	}

	public boolean isCanClear() {
		boolean b = false;
		if (!ComTextPane.getInstance().getText().trim().equals("")) {
			b = true;
		}
		return b;
	}

	public boolean isClipboardString() {
		boolean b = false;
		Clipboard clipboard = textPane.getToolkit().getSystemClipboard();
		Transferable content = clipboard.getContents(this);
		if (content != null) {
			try {
				if (content.getTransferData(DataFlavor.stringFlavor) instanceof String) {
					b = true;
				}
			} catch (UnsupportedFlavorException e) {
				Constants.LOGGER.error(e.getMessage());
			} catch (IOException e) {
				Constants.LOGGER.error(e.getMessage());
			}
		}

		return b;
	}

	public boolean isCanCopy() {
		boolean b = false;
		int start = textPane.getSelectionStart();
		int end = textPane.getSelectionEnd();
		if (start != end) {
			b = true;
		}
		return b;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			clear.setEnabled(isCanClear());
			copy.setEnabled(isCanCopy());
			paste.setEnabled(isClipboardString());
			cut.setEnabled(isCanCopy());
			pop.show(textPane, e.getX(), e.getY());
		}
	}

}
