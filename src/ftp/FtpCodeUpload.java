package ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import interfaces.FtpInterface;

public class FtpCodeUpload {

	private FtpInterface ftpInterface;
	private FTPClient ftpClient = new FTPClient();
	public void setUpConnection(String ftpServer, String ftpUser, String ftpPass, String ftpFolder, String directory, File singleFile) {
		 
         
         try {
   
             ftpClient.connect(ftpServer, 21);		//"qrcode-authentication.iq-joy.com"
             ftpClient.login(ftpUser, ftpPass);	
             ftpClient.enterLocalPassiveMode();
   
             boolean done = false;
             
             if(singleFile != null) {
            	 String remoteFilePath = ftpFolder + "/" + singleFile.getName();
            	 String localFilePath = singleFile.getAbsolutePath();
            	 done = uploadSingleFile(ftpClient,
					        localFilePath, remoteFilePath);
             }else
            	 done = uploadDirectory(ftpFolder, directory );
             
             if (done) {
            	 ftpInterface.imageuploaded(done, 1, singleFile.getName());
                 System.out.println("The first file is uploaded using FTP successfully.");
             }
              
         } catch (IOException ex) {
        	 ftpInterface.imageuploaded(false, 1, singleFile.getName());
             System.out.println("Error: " + ex.getMessage());
             ex.printStackTrace();
         }catch(NullPointerException l ) {
        	 ftpInterface.imageuploaded(false, 1, singleFile.getName());
             System.out.println("Error: " + l.getMessage());
             l.printStackTrace();
         }
	}

	public boolean uploadDirectory(
	        String remoteDirPath, String localParentDir){
	 
	    System.out.println("LISTING directory: " + localParentDir);
	    boolean uploaded = false;
	    File localDir = new File(localParentDir);
	    File[] subFiles = localDir.listFiles();
	    
	    if (subFiles != null && subFiles.length > 0) {
	    	int no=0;
	        for (File item : subFiles) {
	        	System.out.println("ïtem:"+item);
	            String remoteFilePath = remoteDirPath + "/" + item.getName();
	 
	 
	            if (item.isFile()) {
	                // upload the file
	                String localFilePath = item.getAbsolutePath();
	                System.out.println("About to upload the file: " + localFilePath);
	                try {
						uploaded = uploadSingleFile(ftpClient,
						        localFilePath, remoteFilePath);
						
					
		                if (uploaded) {
		                    System.out.println("UPLOADED a file to: "
		                            + remoteFilePath);
		                } else {
		                    System.out.println("COULD NOT upload the file: "
		                            + localFilePath);
		                }
		                ftpInterface.imageuploaded(uploaded, ++no, item.getName());
	                } catch (IOException e) {
						e.printStackTrace();
						ftpInterface.imageuploaded(uploaded, ++no, item.getName());
						continue;
					}
	            } 
	            
	        }
	        closeConnection();
	    }
	    return uploaded;
	}
	
	public static boolean uploadSingleFile(FTPClient ftpClient,
	        String localFilePath, String remoteFilePath) throws IOException {
	    File localFile = new File(localFilePath);
	 
	    InputStream inputStream = new FileInputStream(localFile);
	    try {
	        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
	        return ftpClient.storeFile(remoteFilePath, inputStream);
	    } finally {
	        inputStream.close();
	    }
	}

	public void closeConnection() {
		 try {
			 if(ftpClient != null)
             if (ftpClient.isConnected()) {
                 ftpClient.logout();
                 ftpClient.disconnect();
             }
              
         } catch (IOException ex) {
             ex.printStackTrace();
         }
	}
	
	public void setFtpInterface(FtpInterface ftpInterface) {
		this.ftpInterface = ftpInterface;
	}

}
