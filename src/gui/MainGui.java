package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.json.JSONException;

import database.GetAddressList;
import database.JSonConverter;
import qrcode.QRCodeUpload;
import qrcode.Settings;

public class MainGui extends JFrame{

	private static final long serialVersionUID = -6354937885036816186L;
	private JMenuBar menuBar = new JMenuBar();
	private RegistrationPanel registrationPanel;
	private JSplitPane splitPane;
	
	private  GetAddressList getAddressList = new GetAddressList();
	private  Settings settings = new Settings();
	private  JSonConverter jSonConverter = new JSonConverter();
	private  Map<String, Object> addressesKey = new TreeMap<>();
	private  List<String> unallocatedIDs = new ArrayList<>();
	private String ID;
	private EditParticipant editPanel;
	private ViewParticipant viewParticipant;
	
	public MainGui(JSonConverter jSonConverter, String iD, Map<String, Object> addressesKey){
		setIconImage(Toolkit.getDefaultToolkit().getImage("255617_Adetunji_Ayoade.png"));
		getContentPane().setBackground(new Color(240, 255, 240));
		BorderLayout borderLayout = (BorderLayout) getContentPane().getLayout();
		borderLayout.setVgap(5);
		borderLayout.setHgap(5);
		
		editPanel = new EditParticipant(jSonConverter);
		
		JPanel topPanel = new JPanel();
		topPanel.setBackground(new Color(224, 255, 255));
		//getContentPane().add(topPanel, BorderLayout.WEST);
		topPanel.setLayout(new GridLayout(3, 2, 1, 1));
		
		this.ID = iD;
		
		JButton registerParticipant = new JButton("Register Participant");
		registerParticipant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(registrationPanel != null) {
					 removeComponents();
					 //splitPane.setVisible(false);
					 registrationPanel = null;
				}else {
					refreshParameters("RegistrationPanel");
				}
			}
		});
		topPanel.add(registerParticipant);
		
		JButton btnNewButton_4 = new JButton("Edit Participants");
		btnNewButton_4.addActionListener(c->{
				if(editPanel != null) {
					 removeComponents();
					 refreshParameters("null");
					 splitPane.setRightComponent(editPanel.makeUI());
					 editPanel.setEditParticipant(d->{
							if(editPanel != null) {
								 removeComponents();
								 editPanel = null;
								// refreshParameters("null");
								 editPanel = new EditParticipant(jSonConverter);
								 splitPane.setRightComponent(d);
								 
							}else {
								 editPanel = null;
							}
						});
				}else {
					 editPanel = null;
				}
			
		});
		topPanel.add(btnNewButton_4);
		
		editPanel.setEditParticipant(d->{
			if(editPanel != null) {
				 removeComponents();
				 editPanel = null;
				// refreshParameters("null");
				 editPanel = new EditParticipant(jSonConverter);
				 splitPane.setRightComponent(d);
				 
			}else {
				 editPanel = null;
			}
		});
		
		JButton viewButton = new JButton("View Participant");
		viewButton.addActionListener(w->{
			if(viewParticipant == null) {
				viewParticipant = new ViewParticipant(jSonConverter);
				 removeComponents();
				 splitPane.setRightComponent(viewParticipant.makeUI());
			}else {
				viewParticipant = null;
			}
		});
		topPanel.add(viewButton);
		
		JButton uploadQR = new JButton("Upload QR-Codes");
		uploadQR.addActionListener(o->{
			QRCodeUpload qrCodeUpload = new QRCodeUpload(jSonConverter);
		});
		topPanel.add(uploadQR);
		
		JButton btnNewButton_2 = new JButton("Email QR-Codes");
		topPanel.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Settings");
		topPanel.add(btnNewButton_3);
		
		
		getContentPane().add(menuBar, BorderLayout.NORTH);
		
		registrationPanel = new RegistrationPanel(iD);
		registrationPanel.setNavigationInterface((x,y)->{
			if(x) {
				if(registrationPanel != null) {
					 removeComponents();
					 registrationPanel = null;
				}else {
					refreshParameters("RegistrationPanel");
				}
				
			}else System.out.println(y);
		});
		
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		fileMenu.addSeparator();
		
		JMenuItem mntmNewMenuItem = new JMenuItem("View Registration List");
		fileMenu.add(mntmNewMenuItem);
		
		JMenu settingsMenu = new JMenu("Settings");
		menuBar.add(settingsMenu);
		
		JMenuItem newSettings = new JMenuItem("Enter New");
		settingsMenu.add(newSettings);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("About");
		menuBar.add(mntmNewMenuItem_2);
		
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				topPanel, registrationPanel);
		
		getContentPane().add(splitPane, BorderLayout.CENTER);
		
		JLabel lblNewLabel = new JLabel("Register New Participants");
		setTitle("ChipCode's QR Authentication App");
		setSize(1000, 800);
		setVisible(true);
		setLocationRelativeTo(getParent());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}

	protected void removeComponents() {
		JComponent pane = splitPane;
		Component[] comp = pane.getComponents();
		for(Component component:comp) {
			if(component instanceof JPanel) {
				JPanel panel = (JPanel) component;
				if(panel.getClass() != JPanel.class) {
					System.out.println(panel.getClass()+" instance of "+panel);
					panel.setVisible(false);
					splitPane.remove(panel);
				}
			}
			System.out.println(component.getClass());
		}
	}
	
	private void refreshParameters(String refreshComponent) {
		getAddressList.setAddressInterface(a->{
			addressesKey = jSonConverter.iDs(a);
			unallocatedIDs = jSonConverter.getUnallocatedIDs();
			
			if(addressesKey.size()>0) {
    			MainGui.this.ID = unallocatedIDs.get(0);
    			//System.out.println("New ID:"+MainGui.this.ID);
    			if(refreshComponent.equals("RegistrationPanel")) {
    				registrationPanel = new RegistrationPanel(MainGui.this.ID);
					splitPane.setRightComponent(registrationPanel);
    			}
			}
			
		});
		
		try {
			getAddressList.readJsonFromUrl(settings.getServerAddress());
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(e.getClass() == ConnectException.class) {
				JOptionPane.showMessageDialog(null, "Connection timed out: Check your network");
			}
		}
	}

}
