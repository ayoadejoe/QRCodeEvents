package qrcode;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

import database.JSonConverter;

public class QRCodeUpload extends JDialog {

	private JProgressBar progressBar = new JProgressBar();
	private JTextArea updateArea = new JTextArea();
	private Front front;
	
	public QRCodeUpload(JSonConverter jSonConverter) {
		Runnable runner = ()->{
		setIconImage(Toolkit.getDefaultToolkit().getImage("chipcode_underlay2.png"));
		setAlwaysOnTop(true);
		setTitle("QR Code Generation and Upload");
		setBounds(100, 100, 450, 200);
		setLocationRelativeTo(getParent());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(getContentPane(), popupMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Close");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				QRCodeUpload.this.dispose();
			}
		});
		popupMenu.add(mntmNewMenuItem);
		
		progressBar.setStringPainted(true);
		getContentPane().add(progressBar, BorderLayout.CENTER);
		
		DefaultCaret caret = (DefaultCaret)updateArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		updateArea.setWrapStyleWord(true);
		updateArea.setText("Updates:  ");
		updateArea.setRows(6);
		updateArea.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(updateArea);
		getContentPane().add(scrollPane, BorderLayout.SOUTH);
		
		setVisible(true);
		};
		
		Runnable runnable = ()->{
			front = new Front();
			int totalRecords = front.records(jSonConverter);
			
			boolean proceed = JOptionPane.showConfirmDialog(progressBar, totalRecords+" records found. Proceed?")==0;
			
			if(proceed) {
				updateArea.append(totalRecords+" records found!\n");
				updateArea.append(" \n");
				
				progressBar.setMaximum(totalRecords);
				
				front.setFrontCountInterface((x,y)->{
					progressBar.setValue(x);
					updateArea.append(x+" of "+totalRecords+" completed\n");
					if(x==totalRecords) {
						progressBar.setString("QR Code Generation completed");
						updateArea.append("QR Code Generation completed\n");
						transfer();
					}
					progressBar.revalidate();
					QRCodeUpload.this.revalidate();
				});
	
				front.qrCodeGenerate();
			}else transfer();
		};
		
		Thread a = new Thread(runner);
		Thread b = new Thread(runnable);
		
		a.start();
		if(a.isAlive()) {
			b.start();
		}
	}
	
	private void transfer() {
		System.out.println("transfer");
		Runnable finalrun = ()->{
			int totaltransfer = front.transferNo();
			progressBar.setMaximum(totaltransfer);
			updateArea.append("About to transfer...\n");
			boolean proceed = JOptionPane.showConfirmDialog(progressBar, totaltransfer
					+" qr codes would be transferred. This may take a while depending on your network speed. Proceed?")==0;
			if(proceed) {
				front.setFrontCountInterface((x,y)->{
					progressBar.setValue(x);
					updateArea.append(x+" of "+totaltransfer+" transferred | Code for: "+y+" \n");
					if(x==totaltransfer) {
						progressBar.setString("QR Code transfer completed");
						updateArea.append("QR Code transfer completed\n");
					}
					progressBar.revalidate();
					QRCodeUpload.this.revalidate();
				});
	
				front.transfer(totaltransfer);
			}
			
		};
		
		Thread c = new Thread(finalrun);
		c.start();
	}

	public void setMaximum(int max) {
		progressBar.setMaximum(max);
	}
	
	public void setProgress(int progress, String status) {
		progressBar.setValue(progress);
		progressBar.setString(status);
	}
	
	public void disposePage() {
		QRCodeUpload.this.dispose();
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
