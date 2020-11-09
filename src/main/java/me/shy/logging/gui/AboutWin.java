package me.shy.logging.gui;

import me.shy.logging.Constants;
import me.shy.logging.Logging;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class AboutWin extends JDialog {
	private static final long serialVersionUID = 1L;
	private static AboutWin aboutWin = new AboutWin();
	private static JPanel infoPanel;
	private static JPanel imagePanel;
	private static JPanel buttonPanel;
	private static JEditorPane infoEditorPane;
	private static JButton okButton;

	static {
		infoPanel = new JPanel();
		imagePanel = new JPanel() {

			/**描述：*/	
            private static final long serialVersionUID = 1L;

            protected void paintComponent(Graphics g) {
				ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit()
						.getImage(
								Logging.class
										.getResource("/icons/images.jpg")));
				Image img = icon.getImage();
				g.drawImage(img, this.getLocation().x + 20,
						this.getLocation().y + 20, icon.getIconWidth() / 3,
						icon.getIconHeight() / 3, icon.getImageObserver());
			}
		};
		infoPanel.setLayout(new BorderLayout());
		buttonPanel = new JPanel();
		infoEditorPane = new JEditorPane();
		infoEditorPane.setBackground(SystemColor.control);
		infoEditorPane.setContentType("text/html");
		infoEditorPane
				.setText("<html><font size= 5>Logging Version 1.07</font>"
						+ "<br/>"
						+ "&nbsp;<font size=3>Java<sup><font size=1>TM</font></sup>&nbsp;Application"
						+ "<br/>"
						+ "(C)&nbsp;&nbsp;CopyRight 2014.05"
						+ "<br/>"
						+ "This product includes software developed by Yu Shuibo, team NSN."
						+ "<br/>"
						+ "<a href= 'mailto:yushuibo2010@139.com'>Email:yushuibo2010@139.com</a></font></html>");
		infoEditorPane.addHyperlinkListener(new HyperlinkListener() {
			@Override
			public void hyperlinkUpdate(HyperlinkEvent e) {
				if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
					Desktop desktop = null;
					if (Desktop.isDesktopSupported()) {
						desktop = Desktop.getDesktop();
						try {
							desktop.mail(e.getURL().toURI());
						} catch (IOException e1) {
							Constants.LOGGER.error(e1.getMessage());
						} catch (URISyntaxException e1) {
							Constants.LOGGER.error(e1.getMessage());
						}
					} else {
						JOptionPane.showMessageDialog(MainWin.getInstance(),
								Constants.MAIL_NOT_SUP, Constants.ERROR_TITLE,
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		infoEditorPane.setEditable(false);
		infoEditorPane.setPreferredSize(new Dimension(240, 160));
		okButton = new JButton(Constants.OK);
		okButton.addActionListener(AboutWin.getInstance().new OkAction());
		infoPanel.add(infoEditorPane, BorderLayout.EAST);
		infoPanel.add(imagePanel, BorderLayout.CENTER);
		buttonPanel.add(okButton);
	}

	private AboutWin() {

	}

	public static AboutWin getInstance() {
		return aboutWin;
	}

	public JDialog getAboutWin() {

		this.setSize(350, 200);
		this.setLocation(MainWin.getInstance().getLocation().x + 175, MainWin
				.getInstance().getLocation().y + 250);
		this.setModal(true);
		this.setTitle(Constants.ABOUT);
		this.setLayout(new BorderLayout());
		this.add(infoPanel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
		this.setResizable(false);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
			}
		});
		return this;
	}

	private class OkAction extends AbstractAction {

		/**描述：*/	
        private static final long serialVersionUID = 1L;

        @Override
		public void actionPerformed(ActionEvent e) {
			aboutWin.setVisible(false);
		}

	}
}
