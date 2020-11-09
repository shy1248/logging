package me.shy.logging.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import me.shy.logging.Constants;
import me.shy.logging.FileService;
import me.shy.logging.entity.BSC;
import me.shy.logging.entity.Configuration;

public class AddBscWin extends JDialog {

	/**描述：*/	
    private static final long serialVersionUID = 1L;
    private static AddBscWin win = new AddBscWin();
	private static JPanel panel1 = new JPanel();;
	private static JPanel panel2 = new JPanel();
	private static JPanel panel3 = new JPanel();
	private static JPanel panel4 = new JPanel();
	private static JLabel nameLabel = new JLabel(Constants.NAME);
	private static JTextField nameTextField = new JTextField();
	private static JLabel ipLabel = new JLabel(Constants.IP);
	private static JTextField ipTextField = new JTextField();
	private static JLabel typeLabel = new JLabel(Constants.TYPE);
	private static JComboBox<String> typeComboBox = new JComboBox<String>();
	private static JButton okButton = new JButton(Constants.OK);
	private static JButton cancelButton = new JButton(Constants.CANCLE);

	static {

		typeComboBox.addItem("FlexiBSC");
		typeComboBox.addItem("BSC3i2000");
		typeComboBox.addItem("BSC3i660");
		typeComboBox.addItem("BSC3i440");
		nameTextField.setColumns(10);
		ipTextField.setColumns(10);
		typeComboBox.setPreferredSize(new Dimension(118, 20));
		okButton.setMinimumSize(new Dimension(70, 20));
		okButton.setMaximumSize(new Dimension(70, 20));
		okButton.setPreferredSize(new Dimension(70, 20));
		cancelButton.setMinimumSize(new Dimension(70, 20));
		cancelButton.setMaximumSize(new Dimension(70, 20));
		cancelButton.setPreferredSize(new Dimension(70, 20));
		okButton.addActionListener(getInstance().new OkAction());
		cancelButton.addActionListener(getInstance().new CancelAction());
		panel1.add(nameLabel, FlowLayout.LEFT);
		panel1.add(nameTextField);
		panel2.add(ipLabel, FlowLayout.LEFT);
		panel2.add(ipTextField);
		panel3.add(typeLabel, FlowLayout.LEFT);
		panel3.add(typeComboBox);
		panel4.add(okButton);
		panel4.add(cancelButton);

	}

	private AddBscWin() {

	}

	public static AddBscWin getInstance() {
		return win;
	}

	public JDialog getAddBscWin() {
		this.setSize(220, 180);
		this.setLocation(MainWin.getInstance().getLocation().x + 240, MainWin
				.getInstance().getLocation().y + 260);
		this.setModal(true);
		this.setTitle(Constants.ADD);
		this.setLayout(new GridLayout(4, 1));
		this.add(panel1);
		this.add(panel2);
		this.add(panel3);
		this.add(panel4);
		this.setResizable(false);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/icons/create.png")));
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				nameTextField.setText(null);
				ipTextField.setText(null);
				typeComboBox.setSelectedIndex(0);
				win.dispose();
			}
		});
		return this;
	}

	private class OkAction extends AbstractAction {

		/**描述：*/	
        private static final long serialVersionUID = 1L;

        @Override
		public void actionPerformed(ActionEvent e) {

			boolean isBscExist = false;
			int index = 0;

			if (nameTextField.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(MainWin.getInstance(),
						Constants.NAME_EMPTY, Constants.ERROR_TITLE,
						JOptionPane.WARNING_MESSAGE);
				return;
			}

			if (ipTextField.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(MainWin.getInstance(),
						Constants.IP_EMPTY, Constants.ERROR_TITLE,
						JOptionPane.WARNING_MESSAGE);
				return;
			}

			BSC bsc = new BSC();
			bsc.setName(nameTextField.getText());
			bsc.setIp(ipTextField.getText());
			bsc.setType((String) typeComboBox.getSelectedItem());
			Configuration configuration = MainTable.getConfiguration();
			for (int i = 0; i < configuration.getBscs().size(); i++) {
				if (bsc.getName().equals(
						configuration.getBscs().get(i).getName())) {
					isBscExist = true;
					index = i;
				}
			}

			if (isBscExist) {
				int selected = JOptionPane.showConfirmDialog(
						MainWin.getInstance(), Constants.EXIST,
						Constants.NOTIFY_TITLE, JOptionPane.YES_NO_OPTION);
				if (selected == JOptionPane.YES_OPTION) {
					configuration.getBscs().set(index, bsc);
				} else {
					nameTextField.setText(null);
					ipTextField.setText(null);
					typeComboBox.setSelectedIndex(0);
					win.dispose();
					return;
				}
			} else {
				configuration.getBscs().add(bsc);
			}

			StatusBar.getAllItemLabel().setText(
					Constants.TOTAL + configuration.getBscs().size() + " ");
			MainTable.loadTableData(configuration);
			File file = new File(Constants.CONFIG_FILE_PATH);
			FileService.exportXML(configuration, file);
			nameTextField.setText(null);
			ipTextField.setText(null);
			typeComboBox.setSelectedIndex(0);
			win.dispose();

		}
	}

	private class CancelAction extends AbstractAction {

		/**描述：*/	
        private static final long serialVersionUID = 1L;

        @Override
		public void actionPerformed(ActionEvent e) {
			nameTextField.setText(null);
			ipTextField.setText(null);
			typeComboBox.setSelectedIndex(0);
			win.dispose();
		}
	}
}
