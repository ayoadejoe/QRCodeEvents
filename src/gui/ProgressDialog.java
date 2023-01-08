package gui;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JProgressBar;
import java.awt.BorderLayout;
import java.awt.Toolkit;

public class ProgressDialog extends JDialog {

	private JProgressBar progressBar = new JProgressBar();
	public ProgressDialog() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("chipcode_underlay2.png"));
		setAlwaysOnTop(true);
		setUndecorated(true);
		setBounds(100, 100, 450, 47);
		setLocationRelativeTo(getParent());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(getContentPane(), popupMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Close");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProgressDialog.this.dispose();
			}
		});
		popupMenu.add(mntmNewMenuItem);
		
		progressBar.setStringPainted(true);
		getContentPane().add(progressBar, BorderLayout.CENTER);

		setVisible(true);
	}
	
	public void setMaximum(int max) {
		progressBar.setMaximum(max);
	}
	
	public void setProgress(int progress, String status) {
		progressBar.setValue(progress);
		progressBar.setString(status);
	}
	
	public void disposePage() {
		ProgressDialog.this.dispose();
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
