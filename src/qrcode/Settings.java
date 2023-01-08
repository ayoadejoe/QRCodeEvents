package qrcode;

import java.util.prefs.Preferences;

public class Settings {
	
	private Preferences prefs;
	private String dirID = "directories";
	private String toEmail, domain, fromEmail, fromPassword;
	private String directories;
	private String serverAddress, addressJson;
	private String ftpUsername, ftpPassword, ftpDomain, ftpfolder;

	
	
	public Settings() {
		
		System.out.println("Parent:"+this.getClass().getName());
		prefs = Preferences.userRoot().node(this.getClass().getName());
		directories = prefs.get(dirID, "Home"); 
		
		toEmail = prefs.get("toeml", "admin@iq-joy.com"); 
		domain = prefs.get("domID", "mail.iq-joy.com"); 
		fromEmail = prefs.get("fromID", "admin@iq-joy.com");
		fromPassword = prefs.get("pwdID", "xyz"); 
		
		serverAddress = prefs.get("server", "http://qrcode-authentication.iq-joy.com/afcqrcode/getcodekeys.php?pass=John3:16");
		addressJson = prefs.get("addresses", "ayoade adetunji");
		ftpUsername = prefs.get("ftpuser", "afcqrcode@qrcode-authentication.iq-joy.com");
		ftpPassword = prefs.get("ftpPassword", "Victorious100%");
		ftpDomain = prefs.get("ftpDomain", "qrcode-authentication.iq-joy.com");
		ftpfolder =  prefs.get("ftpfolder", "/");
	}
	
	
	void updateEmailSettings(String toEmail, String domain, String fromEmail, String fromPassword) {
		prefs.put("toeml", toEmail); 
		prefs.put("domID", domain); 
		prefs.put("fromID", fromEmail);
		prefs.put("pwdID", fromPassword); 
	}
	
	void updateHomeSettings(String directory) {
		prefs.put(dirID, directory);
	}


	public Preferences getPrefs() {
		return prefs;
	}


	public String getToEmail() {
		return toEmail;
	}


	public String getDomain() {
		return domain;
	}


	public String getFromEmail() {
		return fromEmail;
	}


	public String getFromPassword() {
		return fromPassword;
	}


	public String getDirectories() {
		return directories;
	}


	public String getServerAddress() {
		return serverAddress;
	}


	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}


	public String getAddressJson() {
		return addressJson;
	}


	public void setAddressJson(String addressJson) {
		this.addressJson = addressJson;
	}


	public String getFtpUsername() {
		return ftpUsername;
	}


	public void setFtpUsername(String ftpUsername) {
		this.ftpUsername = ftpUsername;
	}


	public String getFtpPassword() {
		return ftpPassword;
	}


	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}


	public String getFtpDomain() {
		return ftpDomain;
	}


	public void setFtpDomain(String ftpDomain) {
		this.ftpDomain = ftpDomain;
	}


	public String getFtpfolder() {
		return ftpfolder;
	}


	public void setFtpfolder(String ftpfolder) {
		this.ftpfolder = ftpfolder;
	}
	
	

}
