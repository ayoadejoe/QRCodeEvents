package gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import database.DatabaseData;
import ftp.FtpCodeUpload;
import interfaces.NavigationInterface;
import qrcode.CreateCodeLogo;
import qrcode.Settings;
import java.awt.Desktop;

public class RegistrationPanel extends JPanel {
	
	protected JTextField txtFirstnameLastname;
	protected JTextField txtCompanyEmail;
	protected JTextField txtrCompanyName;
	protected JTextField txtJobTitle;
	protected JTextField txtMobileNo;
	protected JComboBox comboIndustry;
	protected JTextField txtOtherIndustry;
	protected JComboBox comboAttendance;
	protected JComboBox comboPassType;
	protected JButton btnNewButton;
	protected JButton btnNewButton_1;
	protected String[] details = new String[11];
	protected Map <String, String> data = new HashMap<>();
	protected DatabaseData databaseData = new DatabaseData();
	protected JComboBox comboCountry;
	protected JProgressBar formfillBar;
	protected int progress = 0;
	protected NavigationInterface navigationInterface;
	protected JLabel qrLabel;
	protected JLabel lblNewLabel_2;
	protected Settings settings = new Settings();
	protected String serverAddress = settings.getFtpDomain();
	protected FtpCodeUpload codeUpload = new FtpCodeUpload();
	protected CreateCodeLogo createCode = new CreateCodeLogo();
	protected final String DIR = "qrcodes/";
	protected String filename;
	protected JButton saveQRCode;
	protected String[] industryModel;
	
	public RegistrationPanel(String ID) {
		
		setBorder(new CompoundBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Register New Participants", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)), null));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 16, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 44, 0, 0, 0, 0, 0, 0, 0, 0, 0, 29, 0, 34, 0, 109, 43, 70, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0, 0.0, 1.0, 1.0, 0.0, 0.0, 1.0, 1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		formfillBar = new JProgressBar();
		formfillBar.setStringPainted(true);
		formfillBar.setMaximum(11);
		GridBagConstraints gbc_formfillBar = new GridBagConstraints();
		gbc_formfillBar.fill = GridBagConstraints.HORIZONTAL;
		gbc_formfillBar.gridwidth = 2;
		gbc_formfillBar.insets = new Insets(0, 0, 5, 5);
		gbc_formfillBar.gridx = 10;
		gbc_formfillBar.gridy = 1;
		add(formfillBar, gbc_formfillBar);
		
		JLabel idLabel = new JLabel(ID);
		GridBagConstraints gbc_idLabel = new GridBagConstraints();
		gbc_idLabel.anchor = GridBagConstraints.WEST;
		gbc_idLabel.gridwidth = 10;
		gbc_idLabel.insets = new Insets(0, 0, 5, 5);
		gbc_idLabel.gridx = 0;
		gbc_idLabel.gridy = 1;
		add(idLabel, gbc_idLabel);
		
		data.put("ID", ID);
		details[9] = ID;
		idLabel.setText("   ID >>> "+ID);
		
		txtFirstnameLastname = new JTextField();
		txtFirstnameLastname.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtFirstnameLastname.setText("");
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(txtFirstnameLastname.getText().length()>5 && 
						!txtFirstnameLastname.getText().equals("Enter Firstname Lastname")) {
					details[0] = txtFirstnameLastname.getText();
					String[] name = details[0].split(" ");
					data.put("FirstName", name[0]);
					data.put("LastName", name[1]);
					progress = data.size(); formfillBar.setValue(progress);
				}else {
					JOptionPane.showMessageDialog(txtFirstnameLastname, "Enter Your first name and lastname");
					txtFirstnameLastname.setText("Enter Firstname Lastname");
					formfillBar.setValue(--progress);
				}
			}
		});
		
		
		txtFirstnameLastname.setHorizontalAlignment(SwingConstants.CENTER);
		txtFirstnameLastname.setText("Enter Firstname Lastname");
		txtFirstnameLastname.setToolTipText("Firstname Lastname");
		GridBagConstraints gbc_txtFirstnameLastname = new GridBagConstraints();
		gbc_txtFirstnameLastname.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFirstnameLastname.gridwidth = 10;
		gbc_txtFirstnameLastname.insets = new Insets(0, 0, 5, 5);
		gbc_txtFirstnameLastname.gridx = 0;
		gbc_txtFirstnameLastname.gridy = 2;
		add(txtFirstnameLastname, gbc_txtFirstnameLastname);
		txtFirstnameLastname.setColumns(10);
		
		txtCompanyEmail = new JTextField();
		txtCompanyEmail.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtCompanyEmail.setText("");
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(txtCompanyEmail.getText().contains("@") && 
						!txtCompanyEmail.getText().equals("Company email")) {
					details[1] = txtCompanyEmail.getText();
					data.put("Email", txtCompanyEmail.getText());
					progress = data.size(); formfillBar.setValue(progress);
				}else {
					JOptionPane.showMessageDialog(txtCompanyEmail, "Enter Company email");
					txtCompanyEmail.setText("Company email");
					formfillBar.setValue(--progress);
				}
			}
		});
		txtCompanyEmail.setHorizontalAlignment(SwingConstants.CENTER);
		txtCompanyEmail.setText("Company email");
		txtCompanyEmail.setToolTipText("Company Email");
		GridBagConstraints gbc_txtCompanyEmail = new GridBagConstraints();
		gbc_txtCompanyEmail.insets = new Insets(0, 0, 5, 5);
		gbc_txtCompanyEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCompanyEmail.gridwidth = 2;
		gbc_txtCompanyEmail.gridx = 10;
		gbc_txtCompanyEmail.gridy = 2;
		add(txtCompanyEmail, gbc_txtCompanyEmail);
		txtCompanyEmail.setColumns(10);
		
		txtrCompanyName = new JTextField();
		txtrCompanyName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtrCompanyName.setText("");
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(txtrCompanyName.getText().length()>5 && 
						!txtrCompanyName.getText().equals("Company name")) {
					details[2] = txtrCompanyName.getText();
					data.put("Company", txtrCompanyName.getText());
					progress = data.size(); formfillBar.setValue(progress);
				}else {
					JOptionPane.showMessageDialog(txtrCompanyName, "Enter Company name");
					txtrCompanyName.setText("Company name");
					formfillBar.setValue(--progress);
				}
			}
		});
		txtrCompanyName.setHorizontalAlignment(SwingConstants.CENTER);
		txtrCompanyName.setText("Company name");
		GridBagConstraints gbc_txtrCompanyName = new GridBagConstraints();
		gbc_txtrCompanyName.gridwidth = 9;
		gbc_txtrCompanyName.insets = new Insets(0, 0, 5, 5);
		gbc_txtrCompanyName.fill = GridBagConstraints.BOTH;
		gbc_txtrCompanyName.gridx = 0;
		gbc_txtrCompanyName.gridy = 4;
		add(txtrCompanyName, gbc_txtrCompanyName);
		
		txtJobTitle = new JTextField();
		txtJobTitle.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtJobTitle.setText("");
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(txtJobTitle.getText().length()>5 && 
						!txtJobTitle.getText().equals("Job Title")) {
					details[3] = txtJobTitle.getText();
					data.put("JobTitle", txtJobTitle.getText());
					progress = data.size(); formfillBar.setValue(progress);
				}else {
					JOptionPane.showMessageDialog(txtJobTitle, "Job Title");
					txtJobTitle.setText("Job Title");
					formfillBar.setValue(--progress);
				}
			}
		});
		txtJobTitle.setHorizontalAlignment(SwingConstants.CENTER);
		txtJobTitle.setText("Job Title");
		GridBagConstraints gbc_txtJobTitle = new GridBagConstraints();
		gbc_txtJobTitle.insets = new Insets(0, 0, 5, 5);
		gbc_txtJobTitle.gridwidth = 3;
		gbc_txtJobTitle.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtJobTitle.gridx = 9;
		gbc_txtJobTitle.gridy = 4;
		add(txtJobTitle, gbc_txtJobTitle);
		txtJobTitle.setColumns(10);
		
		txtMobileNo = new JTextField();
		txtMobileNo.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtMobileNo.setText("");
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(txtMobileNo.getText().length()>5 && 
						!txtMobileNo.getText().equals("Mobile No.")) {
					details[4] = txtMobileNo.getText();
					data.put("MobileNumber", txtMobileNo.getText());
					progress = data.size(); formfillBar.setValue(progress);
				}else {
					JOptionPane.showMessageDialog(txtMobileNo, "Mobile No.");
					txtMobileNo.setText("Mobile No.");
					formfillBar.setValue(--progress);
				}
			}
		});
		txtMobileNo.setText("Mobile No.");
		txtMobileNo.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_txtMobileNo = new GridBagConstraints();
		gbc_txtMobileNo.gridwidth = 8;
		gbc_txtMobileNo.insets = new Insets(0, 0, 5, 5);
		gbc_txtMobileNo.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMobileNo.gridx = 0;
		gbc_txtMobileNo.gridy = 6;
		add(txtMobileNo, gbc_txtMobileNo);
		txtMobileNo.setColumns(10);
		
		comboIndustry = new JComboBox();
		comboIndustry.addItemListener(e->{
			String selected = (String)comboIndustry.getSelectedItem();
			
			if(!selected.equals("-Industry-")) {
				details[5] = selected;
				data.put("Industry", selected);
				System.out.println("selected:"+selected);
				progress = data.size(); formfillBar.setValue(progress);
			}else {
				//comboIndustry.setSelectedIndex(0);
				formfillBar.setValue(--progress);
			}
		});
		
		industryModel = new String[]{"-Industry-", "Media",
				"Finance and Investors (DFis/Banks/...", "Government", 
				"Private Sector (Industry/Tech/Conglomera...", 
				"Professional Services (Legal/Advisory etc.)", "Project Developers (IPP/EPCs etc.)", 
				"Others (Please Specify)"};
		comboIndustry.setModel(new DefaultComboBoxModel(industryModel));
		GridBagConstraints gbc_comboIndustry = new GridBagConstraints();
		gbc_comboIndustry.insets = new Insets(0, 0, 5, 5);
		gbc_comboIndustry.gridwidth = 4;
		gbc_comboIndustry.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboIndustry.gridx = 8;
		gbc_comboIndustry.gridy = 6;
		add(comboIndustry, gbc_comboIndustry);
		
		txtOtherIndustry = new JTextField();
		txtOtherIndustry.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtOtherIndustry.setText("");
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(txtOtherIndustry.getText().length()>5) {
					details[6] = txtOtherIndustry.getText();
					data.put("OtherIndustry", txtOtherIndustry.getText());
					progress = data.size(); formfillBar.setValue(progress);
				}else {
					txtOtherIndustry.setText("Other Industry");
					formfillBar.setValue(--progress);
				}
			}
			
		});
		txtOtherIndustry.setHorizontalAlignment(SwingConstants.CENTER);
		txtOtherIndustry.setText("Other Industry");
		GridBagConstraints gbc_txtOtherIndustry = new GridBagConstraints();
		gbc_txtOtherIndustry.gridwidth = 7;
		gbc_txtOtherIndustry.insets = new Insets(0, 0, 5, 5);
		gbc_txtOtherIndustry.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtOtherIndustry.gridx = 0;
		gbc_txtOtherIndustry.gridy = 8;
		add(txtOtherIndustry, gbc_txtOtherIndustry);
		txtOtherIndustry.setColumns(10);
		
		comboAttendance = new JComboBox();
		comboAttendance.addItemListener(e->{
			String selected = (String)comboAttendance.getSelectedItem();
			System.out.println(selected);
			if(!selected.equals("-Attendance-")) {
				details[7] = selected;
				data.put("AttendingInPerson", selected);
				progress = data.size(); formfillBar.setValue(progress);
			}else {
				//comboAttendance.setSelectedIndex(0);
				formfillBar.setValue(--progress);
			}
		});
		comboAttendance.setModel(new DefaultComboBoxModel(new String[] {"-Attendance-", "Attending in-person", "Virtual"}));
		GridBagConstraints gbc_comboAttendance = new GridBagConstraints();
		gbc_comboAttendance.insets = new Insets(0, 0, 5, 5);
		gbc_comboAttendance.gridwidth = 4;
		gbc_comboAttendance.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboAttendance.gridx = 8;
		gbc_comboAttendance.gridy = 8;
		add(comboAttendance, gbc_comboAttendance);
		
	
		
		comboCountry = new JComboBox();
		comboCountry.setModel(new DefaultComboBoxModel(new String[] {"-Country-", "Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua & Deps", "Argentina", "Armenia", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bhutan", "Bolivia", "Bosnia Herzegovina", "Botswana", "Brazil", "Brunei", "Bulgaria", "Burkina", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Central African Rep", "Chad", "Chile", "China", "Colombia", "Comoros", "Congo", "Congo {Democratic Rep}", "Costa Rica", "Croatia", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Fiji", "Finland", "France", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Greece", "Grenada", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Honduras", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland {Republic}", "Israel", "Italy", "Ivory Coast", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Korea North", "Korea South", "Kosovo", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Macedonia", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius", "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro", "Morocco", "Mozambique", "Myanmar, {Burma}", "Namibia", "Nauru", "Nepal", "Netherlands", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Qatar", "Romania", "Russian Federation", "Rwanda", "St Kitts & Nevis", "St Lucia", "Saint Vincent & the Grenadines", "Samoa", "San Marino", "Sao Tome & Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Sudan", "Spain", "Sri Lanka", "Sudan", "Suriname", "Swaziland", "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Togo", "Tonga", "Trinidad & Tobago", "Tunisia", "Turkey", "Turkmenistan", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City", "Venezuela", "Vietnam", "Yemen", "Zambia", "Zimbabwe"}));
		GridBagConstraints gbc_comboCountry = new GridBagConstraints();
		gbc_comboCountry.gridwidth = 7;
		gbc_comboCountry.insets = new Insets(0, 0, 5, 5);
		gbc_comboCountry.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboCountry.gridx = 0;
		gbc_comboCountry.gridy = 10;
		add(comboCountry, gbc_comboCountry);
	
		comboCountry.addItemListener(w->{
			String selected = (String)comboCountry.getSelectedItem();
			System.out.println(selected);
			if(!selected.equals("-Country-")) {
				details[10] = selected;
				data.put("Country", selected);
				progress = data.size(); formfillBar.setValue(progress);
			}else {
				//comboCountry.setSelectedIndex(0);
				formfillBar.setValue(--progress);
			}
		});
		
		comboPassType = new JComboBox();
		comboPassType.addItemListener(w->{
			String selected = (String)comboPassType.getSelectedItem();
			System.out.println(selected);
			if(!selected.equals("-Pass Type-")) {
				details[8] = selected;
				data.put("PassType", selected);
				progress = data.size(); formfillBar.setValue(progress);
			}else {
				//comboPassType.setSelectedIndex(0);
				formfillBar.setValue(--progress);
			}
		});
		
		comboPassType.setModel(new DefaultComboBoxModel(new String[] {"-Pass Type-", "Delegate", "Organiser", "VIP", "Media"}));
		GridBagConstraints gbc_comboPassType = new GridBagConstraints();
		gbc_comboPassType.gridwidth = 4;
		gbc_comboPassType.insets = new Insets(0, 0, 5, 5);
		gbc_comboPassType.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboPassType.gridx = 8;
		gbc_comboPassType.gridy = 10;
		add(comboPassType, gbc_comboPassType);
		
		btnNewButton = new JButton("Register & Generate QR-Code");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegistrationPanel.this.databaseData.setRegistrationInterface(w->{
					if(w) {
						formfillBar.setValue(11);
						System.out.println("Done");
						JOptionPane.showMessageDialog(RegistrationPanel.this, "Submitted! - Generating QR-Code");
						
						String name = ID+"_"+data.get("FirstName")+"_"+data.get("LastName");
						filename = name+".png";
						createCode.setCodeGenerationCompleted((x)->{
							System.out.println("very "+x);
							if(x) {
								qrLabel.setIcon(new ImageIcon(DIR+name+".png"));
								qrLabel.setVisible(true);
								saveQRCode.setVisible(true);
								RegistrationPanel.this.revalidate();
							}else {
								
								System.out.println(" Error");
							}
							});
						String address = "http://"+serverAddress+"/afcqrcode/sectionlog.php?subx="+ID+"&pac=109*";
						
						System.out.println(address);
						System.out.println(name);
						createCode.generate(address, name);
						
						//navigationInterface.navigate(w, "Pane closed");
						
					}else {
						formfillBar.setValue(10);
						formfillBar.setString("Submission Unsuccessful");
						navigationInterface.navigate(w, "Pane not close");
						JOptionPane.showMessageDialog(btnNewButton, "Check network connection");
					}
				});
				
				if(data.size()>=9) {
					databaseData.submit(data);
					System.out.println("Submitting");
				}else JOptionPane.showMessageDialog(btnNewButton, "Fill the missing information and submit again");
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton.gridwidth = 10;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 12;
		add(btnNewButton, gbc_btnNewButton);
		
		btnNewButton_1 = new JButton("Clear");
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton_1.gridwidth = 3;
		gbc_btnNewButton_1.gridx = 10;
		gbc_btnNewButton_1.gridy = 12;
		add(btnNewButton_1, gbc_btnNewButton_1);
		
		lblNewLabel_2 = new JLabel(".");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 8;
		gbc_lblNewLabel_2.gridy = 13;
		add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		qrLabel = new JLabel("");
		qrLabel.setIcon(new ImageIcon("demo.png"));
		GridBagConstraints gbc_qrLabel = new GridBagConstraints();
		gbc_qrLabel.gridheight = 2;
		gbc_qrLabel.gridwidth = 13;
		gbc_qrLabel.insets = new Insets(0, 0, 5, 0);
		gbc_qrLabel.gridx = 0;
		gbc_qrLabel.gridy = 14;
		add(qrLabel, gbc_qrLabel);
		qrLabel.setVisible(false);
		
		saveQRCode = new JButton("Save & Open QR-Code");
		saveQRCode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File localDir = new File(DIR);
			    File[] subFiles = localDir.listFiles();
			    int totalFiles = subFiles.length;
			    
				codeUpload.setFtpInterface((z,y, x)->{
					if(z) {
						System.out.println(x+" uploaded");
						if (Desktop.isDesktopSupported()) {
						    try {
								Desktop.getDesktop().open(new File(DIR+x));
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}else System.out.println(x+" was not uploaded");
				});
				codeUpload.setUpConnection(serverAddress, settings.getFtpUsername(), settings.getFtpPassword(), settings.getFtpfolder(), DIR, new File(filename) );
			}
		});
		GridBagConstraints gbc_saveQRCode = new GridBagConstraints();
		gbc_saveQRCode.gridwidth = 13;
		gbc_saveQRCode.insets = new Insets(0, 0, 5, 0);
		gbc_saveQRCode.gridx = 0;
		gbc_saveQRCode.gridy = 16;
		add(saveQRCode, gbc_saveQRCode);
		saveQRCode.setVisible(false);
		
		setVisible(true);
	}
	


	public void setNavigationInterface(NavigationInterface navigationInterface) {
		this.navigationInterface = navigationInterface;
	}

}
