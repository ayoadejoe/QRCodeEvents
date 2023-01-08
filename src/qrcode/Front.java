package qrcode;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.swing.JPanel;

import database.GetAddressList;
import database.JSonConverter;
import ftp.FtpCodeUpload;
import interfaces.AddressInterface;
import interfaces.CreateCodeInterface;
import interfaces.FrontCountInterface;

public class Front {
	public Front() {
	}

	private static final long serialVersionUID = 1L;
	private CreateCodeInterface createCodeInterface;
	private FrontCountInterface qrCountInterface;
	private AddressInterface addressInterface;
	private CreateCodeLogo createCode = new CreateCodeLogo();;
	private GetAddressList getAddressList = new GetAddressList();
	private Settings settings = new Settings();
	private JSonConverter jSonConverter = new JSonConverter();
	private Map<String, Object> addressesKey = new TreeMap<>();
	private Map<String, Object> errorkeys = new TreeMap<>();
	private String key, name;
	private String dir = "qrcodes/";
	private String serverAddress = settings.getFtpDomain();
	private FtpCodeUpload codeUpload = new FtpCodeUpload();
	private int noCompleted=0;
	
	
	public int records(JSonConverter jSonConverter){
		addressesKey = jSonConverter.getAllocated();
		return addressesKey.size();
	}
	
	
	public void qrCodeGenerate() {
		Iterator<Map.Entry<String, Object>> entry = addressesKey.entrySet().iterator();
		noCompleted= 0;
		
		createCode.setCodeGenerationCompleted((x)->{
			++noCompleted;
			qrCountInterface.count(noCompleted, "QR Code generation in progress");
			if(x) {
				if(entry.hasNext()) {
					Entry<String, Object> detail = entry.next();
					key = detail.getKey();
					List<String> parts = (List<String>) detail.getValue();
					StringBuilder name = new StringBuilder(parts.get(0)+"_"+parts.get(1)+"_"+parts.get(2));
					String address = "http://"+serverAddress+"/afcqrcode/sectionlog.php?subx="+key+"&pac=109*";
					createCode.generate(address, name.toString());
				}
			}else {
				errorkeys.put(key, name);
				System.out.println(key+" Error");
			}
			});
		
		
		
		if(entry.hasNext()) {
				Entry<String, Object> detail = entry.next();
				key = detail.getKey();
				System.out.println(key);
				List<String> parts = (List<String>) detail.getValue();
				System.out.println(parts);
				StringBuilder name = new StringBuilder(parts.get(0)+"_"+parts.get(1)+"_"+parts.get(2));
				//name.append(".png");
				
				String address = "http://"+serverAddress+"/afcqrcode/sectionlog.php?subx="+key+"&pac=109*";
				qrCountInterface.count(noCompleted, "QR Code generation starting");
				System.out.println(address);
				System.out.println(name);
				createCode.generate(address, name.toString());
			}
		
	}
	
	public int transferNo() {
		File localDir = new File(dir);
	    File[] subFiles = localDir.listFiles();
	    int totalFiles = subFiles.length;
	    return totalFiles;
	}
	
	public void transfer(int totalFiles) {
		codeUpload.setFtpInterface((z,y, x)->{
			if(z) {
				System.out.println(x+" uploaded");
				System.out.println(y+"/"+totalFiles+" completed");
				qrCountInterface.count(y, x);
			}else System.out.println(x+" was not uploaded");
		});
		codeUpload.setUpConnection(serverAddress, settings.getFtpUsername(), settings.getFtpPassword(), settings.getFtpfolder(), dir, null);
			
	}
	
	
	public void setFrontCountInterface(FrontCountInterface qrCountInterface) {
		this.qrCountInterface = qrCountInterface;
	}


}
